/*
 * Copyright 2007 Philip Jones, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the mydas project, including source code,
 * downloads and documentation, please see:
 *
 * http://code.google.com/p/mydas/
 *
 */

package be.proteomics.pprIA.das;

import uk.ac.ebi.mydas.controller.CacheManager;
import uk.ac.ebi.mydas.controller.DataSourceConfiguration;
import uk.ac.ebi.mydas.datasource.AnnotationDataSource;
import uk.ac.ebi.mydas.exceptions.BadReferenceObjectException;
import uk.ac.ebi.mydas.exceptions.DataSourceException;
import uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException;
import uk.ac.ebi.mydas.model.DasAnnotatedSegment;
import uk.ac.ebi.mydas.model.DasFeature;
import uk.ac.ebi.mydas.model.DasType;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;
import java.io.BufferedInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Created Using IntelliJ IDEA.
 * Date: 18-Jul-2007
 * Time: 16:51:37
 *
 * @author Phil Jones, EMBL-EBI, pjones@ebi.ac.uk
 *
 * NOTE TO DATA SOURCE DEVELOPERS:
 *
 * This template is based upon the AnnotationDataSource interface,
 * there are however three other interfaces available that may be
 * more appropriate for your needs, described here:
 *
 * <a href="http://code.google.com/p/mydas/wiki/HOWTO_WritePluginIntro">
 *     Writing a MyDas Data Source - Selecting the Best Inteface
 * </a> 
 */
public class PprDasServer implements AnnotationDataSource {

    CacheManager cacheManager = null;
    ServletContext svCon;
    Map<String, String> globalParameters;
    DataSourceConfiguration config;
    private String userName = "guest";
    private Connection iConn;
    private DataSource datasource = null;
    private UniprotSequence uniprotSeq = null;


    /**
     * This method is called by the MydasServlet class at Servlet initialisation.
     * <p/>
     * The AnnotationDataSource is passed the servletContext, a handle to globalParameters in the
     * form of a Map &lt;String, String&gt; and a DataSourceConfiguration object.
     * <p/>
     * The latter two parameters contain all of the pertinent information in the
     * ServerConfig.xml file relating to the server as a whole and specifically to
     * this data source.  This mechanism allows the datasource author to set up
     * required configuration in one place, including AnnotationDataSource specific configuration.
     * <p/>
     * <bold>It is highly desirable for the implementation to test itself in this init method and throw
     * a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     * and read a record.</bold>
     *
     * @param servletContext   being the ServletContext of the servlet container that the
     *                         Mydas servlet is running in.
     * @param globalParameters being a Map &lt;String, String&gt; of keys and values
     *                         as defined in the ServerConfig.xml file.
     * @param dataSourceConfig containing the pertinent information frmo the ServerConfig.xml
     *                         file for this datasource, including (optionally) a Map of datasource specific configuration.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable
     *          for the implementation to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     */
    public void init(ServletContext servletContext, Map<String, String> globalParameters, DataSourceConfiguration dataSourceConfig) throws DataSourceException {
        this.svCon = servletContext;
        this.globalParameters = globalParameters;
        this.config = dataSourceConfig;

        //get database connection
        try {
            //Create a datasource for pooled connections.
            datasource = (DataSource) svCon.getAttribute("DBPool");
            iConn = this.getConnection();
        } catch (Exception e) {
            try {
                throw new ServletException(e.getMessage());
            } catch (ServletException e1) {
                e1.printStackTrace();  
            }
        }

    }

    /**
     * This method is called when the DAS server is shut down and should be used
     * to clean up resources such as database connections as required.
     */
    public void destroy() {
    }

    /**
     * This method returns a List of DasAnnotatedSegment objects, describing the annotated segment and the features
     * of the segmentId passed in as argument.
     *
     * @param segmentId being the reference of the segment requested in the DAS request (not including
     *                  start and stop coordinates)
     *                  <p/>
     *                  If your datasource implements only this interface,
     *                  the MydasServlet will handle restricting the features returned to
     *                  the start / stop coordinates in the request and you will only need to
     *                  implement this method to return Features.  If on the other hand, your data source
     *                  includes massive segments, you may wish to implement the
     *                  {@link uk.ac.ebi.mydas.datasource.RangeHandlingAnnotationDataSource}
     *                  interface.  It will then be the responsibility of your AnnotationDataSource plugin to
     *                  restrict the features returned for the requested range.
     * @return A DasAnnotatedSegment object.  This describes the segment that is annotated, limited
     *         to the information required for the /DASGFF/GFF/SEGMENT element.  References a Collection of
     *         DasFeature objects.   Note that this is a basic Collection - this gives you complete control over the details
     *         of the Collection type - so you can create your own comparators etc.
     * @throws uk.ac.ebi.mydas.exceptions.BadReferenceObjectException
     *          in the event that your server does not include information about this segment.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable
     *          for the implementation to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     */
    public DasAnnotatedSegment getFeatures(String segmentId) throws BadReferenceObjectException, DataSourceException {
        ProteinResult[] proteinResults = getProteinResult(segmentId);
         DasAnnotatedSegment segment = null;
        //check if there is a result for the segmentId
        if(proteinResults.length != 0){
            ProteinResult result = proteinResults[0];
            uniprotSeq = readSequenceUrl("http://www.ebi.ac.uk/das-srv/uniprot/das/uniprot/sequence?segment=" + result.getSpAccession());
            String sequence = result.getSequence();
            String version = uniprotSeq.compareSequence(sequence);
            java.util.Collection<uk.ac.ebi.mydas.model.DasFeature> features = new Vector();
            Vector sites = result.getProcessingSites();
            for(int i = 0 ;i<sites.size() ; i++){
                ProcessingSiteDas siteDas = (ProcessingSiteDas) sites.get(i);
                String site = siteDas.getStart() + "-" + siteDas.getPreSite() + "!" + siteDas.getPostSite() + "-" + siteDas.getEnd();
                Vector notes = new Vector();
                String mascotInfo = "Mascot version !!! score !!! threshold !!!";
                notes.add(mascotInfo);

                DasFeature feature = new DasFeature(siteDas.getMeropsid(), siteDas.getTreatment(), site ,"PPR", "Processing Site", "Ms/Ms", "N-term COFRADIC", siteDas.getPostion(), siteDas.getPostion(), 0.0 ,null,null, notes , null, null, null);
                features.add(feature);
            }
            segment = new DasAnnotatedSegment(segmentId, 1, sequence.length(), version, null , features);

        } else {

        }
        return segment;
    }

    /**
     * This method is used to implement the DAS types command.  (See <a href="http://biodas.org/documents/spec.html#types">
     * DAS 1.53 Specification : types command</a>.  This method should return a Collection containing <b>all</b> the
     * types described by the data source (one DasType object for each type ID).
     * <p/>
     * For some data sources it may be desirable to populate this Collection from a configuration file or to
     *
     * @return a Collection of DasType objects - one for each type id described by the data source.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable
     *          for the implementation to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     */
    public Collection<DasType> getTypes() throws DataSourceException {
        return null;
    }

    /**
     * <b>For some Datasources, especially ones with many entry points, this method may be hard or impossible
     * to implement.  If this is the case, you should just throw an {@link uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException} as your
     * implementation of this method, so that a suitable error HTTP header
     * (X-DAS-Status: 501 Unimplemented feature) is returned to the DAS client as
     * described in the DAS 1.53 protocol.</b><br/><br/>
     * <p/>
     * This method is used by the features command when no segments are included, but feature_id and / or
     * group_id filters have been included, to meet the following specification:<br/><br/>
     * <p/>
     * "<b>feature_id</b> (zero or more; new in 1.5)<br/>
     * Instead of, or in addition to, <b>segment</b> arguments, you may provide one or more <b>feature_id</b>
     * arguments, whose values are the identifiers of particular features.  If the server supports this operation,
     * it will translate the feature ID into the segment(s) that strictly enclose them and return the result in
     * the <i>features</i> response.  It is possible for the server to return multiple segments if the requested
     * feature is present in multiple locations.
     * <b>group_id</b> (zero or more; new in 1.5)<br/>
     * The <b>group_id</b> argument, is similar to <b>feature_id</b>, but retrieves segments that contain
     * the indicated feature group."  (Direct quote from the DAS 1.53 specification, available from
     * <a href="http://biodas.org/documents/spec.html#features">http://biodas.org/documents/spec.html#features</a>.)
     * <p/>
     * Note that if segments are included in the request, this method is not used, so feature_id and group_id
     * filters accompanying a list of segments will work correctly, even if your implementation of this method throws an
     * {@link uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException}.
     *
     * @param featureIdCollection a Collection&lt;String&gt; of feature_id values included in the features command / request.
     *                            May be a <code>java.util.Collections.EMPTY_LIST</code> but will <b>not</b> be null.
     * @param groupIdCollection   a Collection&lt;String&gt; of group_id values included in the features command / request.
     *                            May be a <code>java.util.Collections.EMPTY_LIST</code> but will <b>not</b> be null.
     * @return A Collection of {@link uk.ac.ebi.mydas.model.DasAnnotatedSegment} objects. These describe the segments that is annotated, limited
     *         to the information required for the /DASGFF/GFF/SEGMENT element.  Each References a Collection of
     *         DasFeature objects.   Note that this is a basic Collection - this gives you complete control over the details
     *         of the Collection type - so you can create your own comparators etc.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable for the
     *          implementation to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     * @throws uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException
     *          Throw this if you cannot
     *          provide a working implementation of this method.
     */
    public Collection<DasAnnotatedSegment> getFeatures(Collection<String> featureIdCollection, Collection<String> groupIdCollection) throws UnimplementedFeatureException, DataSourceException {
        return null;
    }

    /**
     * This method allows the DAS server to report a total count for a particular type
     * for all annotations across the entire data source.  If it is not possible to retrieve this value from your dsn, you
     * should return <code>null</code>.
     *
     * @param type containing the information needed to retrieve the type count
     *             (type id and optionally the method id and category id.  Note that the last two may
     *             be null, which needs to be taken into account by the implementation.)
     * @return The total count <i>across the entire data source</i> (not
     *         just for one segment) for the specified type.  If it is not possible to determine
     *         this count, this method should return <code>null</code>.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable for the
     *          implementation to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     */
    public Integer getTotalCountForType(DasType type) throws DataSourceException {
        return null;
    }

    /**
     * The mydas DAS server implements caching within the server.  This method passes your datasource a reference
     * to a {@link uk.ac.ebi.mydas.controller.CacheManager} object.  To implement this method, you should simply retain a reference to this object.
     * In your code you can then make use of this object to manipulate caching in the mydas servlet.
     * <p/>
     * At present the {@link uk.ac.ebi.mydas.controller.CacheManager} class provides you with a single method public void emptyCache() that
     * you can call if (for example) the underlying data source has changed.
     *
     * @param cacheManager a reference to a {@link uk.ac.ebi.mydas.controller.CacheManager} object that the data source can use to empty
     *                     the cache for this data source.
     */
    public void registerCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * This method returns a URL, based upon a request built as part of the DAS 'link' command.
     * The nature of this URL is entirely up to the data source implementor.
     * <p/>
     * The mydas servlet will redirect to the URL provided.  This command is intended for use in an internet browser,
     * so the URL returned should be a valid internet address.  The page can return content of any MIME type and
     * is intended to be 'human readable' rather than material for consumption by a DAS client.
     * <p/>
     * The link command takes two mandatory
     * arguments:
     * <ul>
     * <li>
     * a 'field' parameter which is limited to one of five valid values.  This method is guaranteed
     * to be called with the 'field' parameter set to one of these values (any other request will be handled as
     * an error by the mydas DAS server servlet.)  The 'field' parameter will be one of the five static String constants
     * that are members of the AnnotationDataSource interface.
     * </li>
     * <li>
     * an 'id' field.  Again, this will be validated by the mydas servlet to ensure that it
     * is a non-null, non-zero length String.
     * </li>
     * <ul>
     * See <a href="http://biodas.org/documents/spec.html#feature_linking">DAS 1.53 Specification: Linking to a Feature</a>
     * for details.
     * <p/>
     * If your data source does not implement this method, an UnimplementedFeatureException should be thrown.
     *
     * @param field one of 'feature', 'type', 'method', 'category' or 'target' as documented in the DAS 1.53
     *              specification
     * @param id    being the ID of the indicated annotation field
     * @return a valid URL.
     * @throws uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException
     *          in the event that the DAS data source
     *          does not implement the link command
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable for the implementation
     *          to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     */
    public URL getLinkURL(String field, String id) throws UnimplementedFeatureException, DataSourceException {
        return null;
    }

    //get the connection from the datasource
    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

    private ProteinResult[] getProteinResult(String swissprotId){
        ProteinResult[] result = new ProteinResult[0];
        try{
            String query = "select * from protein as p where p.spaccession = '" + swissprotId + "' AND p.proteinid IN (select l_proteinid from peptide_location as l where l.l_groupid in (select d.l_groupid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))))";
            PreparedStatement prep = null;
            prep = iConn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            result = this.getProteinResutls(rs);
            rs.close();
            prep.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

     private ProteinResult[] getProteinResutls(ResultSet aRs) throws SQLException {
        Vector results = new Vector();
        ResultSet iRs = aRs;

            while (iRs.next()) {
                ProteinResult result;
                Long id = iRs.getLong("proteinid");
                String accession = iRs.getString("SPaccession");
                StringBuffer seq = new StringBuffer();
                seq.append(iRs.getString("sequence"));
                String seqProt = iRs.getString("sequence");
                String entry = iRs.getString("entry_name");
                String description = iRs.getString("name");
                //create protein result
                result = new ProteinResult(seqProt, accession, entry, description);

                //find processing sites
                PreparedStatement prep = null;
                prep = iConn.prepareStatement("Select l.location , l.peptide_locationid from peptide_location as l where l.l_proteinid in (select p.proteinid from protein as p where p.spaccession = ?) and l.l_groupid in (select d.l_groupid from peptide as d where d.l_projectid in (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))))");
                prep.setString(1, accession);
                ResultSet rs = prep.executeQuery();
                Vector sitesV = new Vector();
                Vector locV = new Vector();
                while (rs.next()) {
                    sitesV.add(rs.getInt(1));
                    locV.add(rs.getString(2));
                }
                Integer[] sites = new Integer[sitesV.size()];
                sitesV.toArray(sites);
                String[] loc = new String[locV.size()];
                locV.toArray(loc);
                rs.close();
                prep.close();

                for (int i = 0; i < loc.length; i++) {
                    PreparedStatement prepLoc = null;
                    prepLoc = iConn.prepareStatement("select t.scientific_name, t.treatmentid, t.meropsid from treatment as t where t.treatmentid in (Select i.l_treatmentid from peptide_treatment_and_inhibitor as i where l_identificationid in (Select identificationid from peptide as d where d.l_groupid in (select l.l_groupid  from peptide_location as l where l.peptide_locationid = ? )and d.l_projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))))");
                    prepLoc.setString(1, loc[i]);
                    ResultSet rsLoc = prepLoc.executeQuery();
                    while (rsLoc.next()) {
                        int pos = sites[i] - 1;
                        String proSite = "";
                        String proSite2 = "";
                        int start = 0;
                        int end = 0;
                        if ((pos - 5) < 0) {
                            proSite = seqProt.substring(0, pos);
                            proSite2 = seqProt.substring(pos, pos + 5);
                            int length = proSite.length();
                            int diffInt = 5 - length;
                            for (int l = 0; l < diffInt; l++) {
                                proSite = " " + proSite;
                            }
                            start = 0;
                            end = pos + 5;
                        } else {
                            if ((pos + 5) > seqProt.length()) {
                                proSite = seqProt.substring(pos - 5, pos);
                                proSite2 = seqProt.substring(pos);
                                int length = proSite.length();
                                int diffInt = 5 - length;
                                for (int l = 0; l < diffInt; l++) {
                                    proSite2 = proSite2 + " ";
                                }
                                start = pos - 5;
                                end = seqProt.length();
                            } else {
                                proSite = seqProt.substring(pos - 5, pos);
                                proSite2 = seqProt.substring(pos, pos + 5);
                                start = pos - 5;
                                end = pos + 5;
                            }
                        }


                        result.addProcessingSite(new ProcessingSiteDas(rsLoc.getString(1), rsLoc.getInt(2), pos, proSite, proSite2, start, end, rsLoc.getString(3)));
                    }
                    rsLoc.close();
                    prepLoc.close();

                }
                //get pdb accessions
                PreparedStatement prepPdb = null;
                prepPdb = iConn.prepareStatement("select b.pdbid from pdb_accession as b , protein as p where b.l_proteinid = p.proteinid and p.SPaccession = ?");
                prepPdb.setString(1, accession);
                ResultSet rsPdb = prepPdb.executeQuery();
                Vector pdbAccV = new Vector();
                while (rsPdb.next()) {
                    pdbAccV.add(rsPdb.getString(1));
                }
                if (pdbAccV.size() > 0) {
                    String[] pdbAccessions = new String[pdbAccV.size()];
                    pdbAccV.toArray(pdbAccessions);
                    result.setPdbAccession(pdbAccessions);
                }

                rsPdb.close();
                prepPdb.close();
                // get homologene accessions
                PreparedStatement prepHomo = null;
                prepHomo = iConn.prepareStatement("select h.homologeneid from homologene_accession as h , protein as p where h.l_proteinid = p.proteinid and p.SPaccession = ?");
                prepHomo.setString(1, accession);
                ResultSet rsHomo = prepHomo.executeQuery();
                Vector homoAccV = new Vector();
                while (rsHomo.next()) {
                    homoAccV.add(rsHomo.getString(1));
                }
                String[] homoAccessions = new String[homoAccV.size()];
                homoAccV.toArray(homoAccessions);
                result.setHomolgeneAccession(homoAccessions);
                rsHomo.close();
                prepHomo.close();

                results.add(result);

            }

        ProteinResult[] proteins = new ProteinResult[results.size()];
        results.toArray(proteins);
        return proteins;
    }

    //read the sequence and the version from the uniprot das server
    public UniprotSequence readSequenceUrl(String aUrl){
        String sequence = null;
        String version = null;
        UniprotSequence seq = null;
        try {
            URL myURL=new URL(aUrl);
            StringBuilder input = new StringBuilder();
    	    HttpURLConnection c = (HttpURLConnection)myURL.openConnection();
	        BufferedInputStream in = new BufferedInputStream(c.getInputStream());
	        Reader r = new InputStreamReader(in);

	        int i;
	        while ((i = r.read()) != -1) {
	    	    input.append((char) i);
	        }

            String inputString = input.toString();
            sequence = inputString.substring(inputString.indexOf(">",inputString.indexOf("<SEQUENCE")) + 1 , inputString.indexOf("</SEQ"));
            version = inputString.substring(inputString.indexOf("version=\"",inputString.indexOf("<SEQUENCE")) + 11, inputString.indexOf("\">",inputString.indexOf("<SEQUENCE")));
            seq = new UniprotSequence(sequence , version);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return seq;
    }
}
