package be.proteomics.pprIA.update.db_store;

import com.compomics.ppr.db.accessors.Peptide_location;
import com.compomics.ppr.db.accessors.Protein;
import be.proteomics.ppr.general.ProcessingSite;
import be.proteomics.ppr.general.ProteinFasta;
import be.proteomics.pprIA.general.protein_info.SecondaryStructurePrediction;
import be.proteomics.pprIA.general.protein_info.SwissprotSecStructure;
import be.proteomics.pprIA.general.protein_info.finder.SecStruPredictor;
import com.compomics.util.protein.AASequenceImpl;
import com.compomics.util.protein.Header;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 08-Jun-2010
 * Time: 14:03:36
 * To change this template use File | Settings | File Templates.
 */
public class StoreNewPeptideLocationAndProtein {
    private Connection iConnPpr = null;
    private com.compomics.util.protein.Protein iProt;
    private String iPeptide = "";
    private String iAccessions;
    private Long iGroupID = Long.MIN_VALUE;
    private Long iTaxonomy = Long.MIN_VALUE;
    private com.compomics.ppr.db.accessors.Protein iProtein;
    private String spaccession;
    private String iLocation;
    private PrintWriter iOutFile;


    public StoreNewPeptideLocationAndProtein(Connection aConn, com.compomics.util.protein.Protein aProt, String aPeptide, Long aGroupID, Long aTaxonomy, PrintWriter lOutFile, String lLocation, boolean lOnlyNewProteins) throws SQLException {
        this.iConnPpr = aConn;
        this.iPeptide = aPeptide;
        this.iGroupID = aGroupID;
        this.iTaxonomy = aTaxonomy;
        this.iOutFile = lOutFile;
        this.iLocation = lLocation;
        this.iProt = aProt;
        Header head = iProt.getHeader();
        ProteinFasta prote = new ProteinFasta(head.getAbbreviatedFASTAHeader());
        iAccessions = prote.getSpAccession();
        if (!iAccessions.equalsIgnoreCase("error")) {
            iProtein = com.compomics.ppr.db.accessors.Protein.getProteinFromSPaccession(iConnPpr, iAccessions);
            //create a protein if it doesn't exists
            Long proteinID;
            boolean lNewProtein = false;
            if (iProtein == null) {
                lNewProtein = true;
                spaccession = iAccessions;
                AASequenceImpl aa = iProt.getSequence();
                String sequence = aa.getSequence();
                String name = prote.getProteinName();
                String entry_name = prote.getEntryName();
                //get secondary structure annotated by swissprot
                SwissprotSecStructure spSec = new SwissprotSecStructure(spaccession, sequence);
                String SpSecStructure = spSec.getPrediction();
                //Get secondary structure prediction
                SecStruPredictor secPred = new SecStruPredictor(spaccession, sequence, iLocation);
                SecondaryStructurePrediction iSecPred = secPred.getSecPred();
                String secStrucPred = iSecPred.getPredictions();
                HashMap aParams = new HashMap(3);
                aParams.put(Protein.SPACCESSION, spaccession);
                aParams.put(Protein.SEQUENCE, sequence);
                aParams.put(Protein.NAME, name);
                aParams.put(Protein.ENTRY_NAME, entry_name);
                aParams.put(Protein.SEC_STRUCTURE_PREDICTION, secStrucPred);
                aParams.put(Protein.SEC_STRUCTURE_SWISSPROT, SpSecStructure);
                aParams.put(Protein.INFO_FOUND, 0);

                iProtein = new Protein(aParams);
                iProtein.persist(iConnPpr);
                iOutFile.write("Saved protein with SpAccssion " + spaccession + "\n");

                //new ExtraProteinInfoStorer(iConnPpr, iProtein);
                Object[] generatedKey = iProtein.getGeneratedKeys();
                proteinID = (Long) generatedKey[0];
            } else {
                proteinID = iProtein.getProteinid();
            }

            boolean lStoreNewPeptideLocation = true;
            if (lOnlyNewProteins) {
                //only peptide_location linked to new proteins will be stored
                lStoreNewPeptideLocation = lNewProtein;
            }
            if (lStoreNewPeptideLocation) {
                //create peptide_locations
                String seq = iProtein.getSequence();
                ProcessingSite site = new ProcessingSite(seq, iPeptide);
                ProcessingSite[] sites = site.getProcessingSite();
                for (int j = 0; j < sites.length; j++) {
                    String startString = java.lang.String.valueOf(sites[j].getStart());
                    String endString = java.lang.String.valueOf(sites[j].getEnd());
                    Long start = java.lang.Long.valueOf(startString);
                    Long end = java.lang.Long.valueOf(endString);
                    PreparedStatement prep = iConnPpr.prepareStatement("select position from peptide_group where groupid = " + iGroupID);
                    ResultSet rs = prep.executeQuery();
                    boolean lNterm = true;
                    long lProcessingLocation = start;
                    while(rs.next()) {
                        if(rs.getString(1).equalsIgnoreCase("C")){
                            lNterm = false;
                            lProcessingLocation = end + 1;
                        }
                    }
                    rs.close();
                    prep.close();

                    Peptide_location.insertNewlocation(iConnPpr, iGroupID, proteinID, start, end, lProcessingLocation);
                    iOutFile.write("Saved a peptide location for the protein with SpAccession " + iAccessions + "\n");
                }
            }
        }
    }
}


