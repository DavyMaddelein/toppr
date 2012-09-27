package be.proteomics.pprIA.servlet.search;

import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.PerformedSearches;
import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.general.SearchOptions;
import be.proteomics.pprIA.general.util.NavigationProteinBar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Vector;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 15-mei-2008
 * Time: 8:42:02
 * To change this template use File | Settings | File Templates.
 */
public class ProteinCompilationServlet extends HttpServlet {
    private String userName;
    private int proteinCount = 0;
    private int siteCount = 0;
    private Connection iConn;
    private DataSource datasource = null;
    private int sessionNumber;
    private Vector usedTreatments;
    private Vector uniquePeptide = new Vector();
    private PerformedSearches searches;

    public void init() throws ServletException {
        try {
            //Create a datasource for pooled connections.
            datasource = (DataSource) getServletContext().getAttribute("DBPool");
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            usedTreatments = new Vector();
            //Find the username
            HttpSession session = req.getSession(true);
            userName = (String) session.getValue("userName");
            if (userName == null) {
                userName = "guest";
            }
            //get connection from connection pool
            iConn = this.getConnection();
            //get performed searches and sessionNumber
            sessionNumber = -1;
            boolean newCompilation = false;
            searches = (PerformedSearches) session.getValue("searches");
            if (searches == null) {
                searches = new PerformedSearches();
                sessionNumber = 0;
            } else {
                //try to find to correct session
                int sessionCount = searches.getSessionCount();
                for (int i = 0; i < sessionCount; i++) {
                    if (searches.getCompilation(i)) {
                        sessionNumber = i;
                        i = sessionCount;
                    }
                }
                if (sessionNumber == -1) {
                    sessionNumber = sessionCount;
                    newCompilation = true;
                }
            }
            //find the protein to add
            String protein = req.getParameter("protein");

            // check if the protein is already in the compilation
            boolean alreadyIn = false;
            if (!newCompilation) {
                String[] params = searches.getSearchParameters(sessionNumber);
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equalsIgnoreCase(protein)) {
                        alreadyIn = true;
                    }
                }
            }

            if (!alreadyIn) {
                // make a sql query based on the requested parameters
                String sqlQuery = "select p.proteinid from protein as p where p.spaccession  = '" + protein + "'";
                //find the proteins for the given parameters
                PreparedStatement prep = null;
                prep = iConn.prepareStatement(sqlQuery);
                ResultSet rs = prep.executeQuery();
                PreparedStatement prep2 = null;
                prep2 = iConn.prepareStatement("Select l.l_groupid, count(l.l_groupid) from peptide_location as l group by l.l_groupid");
                ResultSet rs2 = prep2.executeQuery();
                FoundProtein[] result = this.findProteinResults(rs2, rs);
                rs.close();
                prep.close();
                rs2.close();
                prep2.close();
                if (newCompilation) {
                    String[] params = new String[2];
                    params[0] = "User protein compilation";
                    params[1] = protein;
                    String[] searchedTreatments = new String[usedTreatments.size()];
                    usedTreatments.toArray(searchedTreatments);
                    searches.addSearch(result, params, false, false, true, proteinCount, siteCount, searchedTreatments, uniquePeptide);
                } else {
                    //set found proteins
                    FoundProtein[] proteinsInComp = searches.getFoundProteins(sessionNumber);
                    Vector addVector = new Vector();
                    for (int i = 0; i < proteinsInComp.length; i++) {
                        addVector.add(proteinsInComp[i]);
                    }
                    for (int i = 0; i < result.length; i++) {
                        addVector.add(result[i]);
                    }
                    proteinsInComp = new FoundProtein[addVector.size()];
                    addVector.toArray(proteinsInComp);
                    searches.setFoundProteins(sessionNumber, proteinsInComp);

                    //set protein, processing count and unique peptide vector
                    searches.setProteinCount(sessionNumber, proteinCount + searches.getProteinCount(sessionNumber));
                    searches.setProcessingSiteCount(sessionNumber, siteCount + searches.getProcessingSiteCount(sessionNumber));

                    Vector compilationUniques = searches.getUniquePeptide(sessionNumber);
                    for (int p = 0; p < uniquePeptide.size(); p++) {
                        boolean found = false;
                        for (int q = 0; q < compilationUniques.size(); q++) {
                            if (((String) uniquePeptide.get(p)).equalsIgnoreCase((String) compilationUniques.get(q))) {
                                found = true;
                            }
                        }
                        if (!found) {
                            compilationUniques.add(uniquePeptide.get(p));
                        }
                    }
                    searches.setUniquePeptide(sessionNumber, compilationUniques);
                    //set used treatments
                    String[] treats = searches.getSearchedTreatments(sessionNumber);
                    Vector treatsCollection = new Vector();
                    for (int i = 0; i < treats.length; i++) {
                        treatsCollection.add(treats[i]);
                    }
                    for (int i = 0; i < usedTreatments.size(); i++) {
                        boolean found = false;
                        for (int j = 0; j < treatsCollection.size(); j++) {
                            if (((String) usedTreatments.get(i)).equalsIgnoreCase((String) treatsCollection.get(j))) {
                                found = true;
                            }
                        }
                        if (!found) {
                            treatsCollection.add(usedTreatments.get(i));
                        }
                    }
                    treats = new String[treatsCollection.size()];
                    treatsCollection.toArray(treats);
                    searches.setSearchedTreatments(sessionNumber, treats);
                    //set searched parameters
                    String[] params = searches.getSearchParameters(sessionNumber);
                    Vector paraVector = new Vector();
                    for (int i = 0; i < params.length; i++) {
                        paraVector.add(params[i]);
                    }
                    paraVector.add(protein);
                    params = new String[paraVector.size()];
                    paraVector.toArray(params);
                    searches.setSearchParameters(sessionNumber, params);
                }

                session.putValue("searches", searches);
                proteinCount = 0;
                siteCount = 0;
                uniquePeptide = new Vector();
            }
        }
        catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
        finally {
            try {
                if (iConn != null) iConn.close();
            } catch (SQLException e) {
            }
        }

        String text = "";

        FoundProtein[] proteins = searches.getFoundProteins(sessionNumber);
        text = proteins[0].getSpAccession() + "<img src=\"images/del.jpg\" align=\"top\" style=\"cursor:pointer;\" onclick=\"deleteProtein('" + proteins[0].getSpAccession() + "')\">";
        for (int p = 1; p < proteins.length; p++) {
            text = text + ", " + proteins[p].getSpAccession() + "<img src=\"images/del.jpg\" align=\"top\" style=\"cursor:pointer;\" onclick=\"deleteProtein('" + proteins[p].getSpAccession() + "')\">";

        }
        String result = text;

        // set up the response
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        // write out the response string
        res.getWriter().write(result);

    }

    private FoundProtein[] findProteinResults(ResultSet aRs, ResultSet aaRs) {
        String proteinsIds = "";
        HashMap idsCount = new HashMap(3);
        ResultSet ids = aRs;
        ResultSet proteins = aaRs;
        Vector results = new Vector();

        try {
            while (ids.next()) {
                idsCount.put(ids.getInt(1), ids.getInt(2));
            }
            while (proteins.next()) {
                proteinsIds = proteinsIds + proteins.getString(1) + ",";
            }
            if (proteinsIds.length() != 0) {
                proteinsIds = proteinsIds.substring(0, proteinsIds.lastIndexOf(","));
            }
            if (proteinsIds.equalsIgnoreCase("")) {
                proteinsIds = "0";
            }
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("select r.proteinid, r.spaccession, r.sequence, r.entry_name, r.name, l.start, l.end, l.l_groupid ,group_concat(distinct t.scientific_name separator ' , ') as treatments ,c.description as type, g.peptide_sequence, l.location as loc\n" +
                    "from peptide as p, peptide_location as l, protein as r, project as j, cofradic as c, peptide_treatment_and_inhibitor as a, treatment as t, peptide_group as g\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_groupid in (select i.l_groupid from peptide_location as i where i.l_proteinid in (" + proteinsIds + ")))\n" +
                    "and p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))\n" +
                    "and p.l_projectid = j.projectid and j.l_cofradicid = c.cofradicid and p.l_groupid = l.l_groupid and l.l_groupid = g.groupid and l.l_proteinid = r.proteinid and r.proteinid in (" + proteinsIds + ") and a.l_identificationid = p.identificationid and a.l_treatmentid = t.treatmentid group by loc, r.spaccession order by r.spaccession, loc");

            ResultSet iRs = prep.executeQuery();

            long lastProteinId = Long.MIN_VALUE;
            Vector usedTreatmentsInProtein = new Vector();
            while (iRs.next()) {
                FoundProtein result;

                long id = iRs.getLong("proteinid");
                String accession = iRs.getString("spaccession");
                String seqProt = iRs.getString("sequence");
                String entry = iRs.getString("entry_name");
                String description = iRs.getString("name");
                int start = iRs.getInt("start");
                int end = iRs.getInt("end");
                int loc = iRs.getInt("loc");
                loc = loc - 1;
                int groupId = iRs.getInt("l_groupid");
                String treatmentsString = iRs.getString("treatments");
                String type = iRs.getString("type");
                String[] treatments = treatmentsString.split(" , ");
                String peptide = iRs.getString("peptide_sequence");


                //add peptide_groupid to the unique peptide vector
                boolean foundGroupid = false;
                for (int q = 0; q < uniquePeptide.size(); q++) {
                    if (peptide.equalsIgnoreCase((String) uniquePeptide.get(q))) {
                        foundGroupid = true;
                    }
                }
                if (!foundGroupid) {
                    uniquePeptide.add(peptide);
                }

                boolean newProtein = false;
                //create protein result
                if (lastProteinId == id) {
                    result = (FoundProtein) results.get(results.size() - 1);
                } else {
                    result = new FoundProtein(seqProt, accession, entry, description);
                    newProtein = true;
                    usedTreatments = new Vector();
                    proteinCount = proteinCount + 1;
                }

                int pos = loc;
                String proSite = "";
                String proSite2 = "";
                int startSite = 0;
                int endSite = 0;
                if ((pos - 15) < 0) {
                    proSite = seqProt.substring(0, pos);
                    proSite2 = seqProt.substring(pos, pos + 15);
                    int length = proSite.length();
                    int diffInt = 15 - length;
                    for (int l = 0; l < diffInt; l++) {
                        proSite = " " + proSite;
                    }
                    startSite = 0;
                    endSite = pos + 15;
                } else {
                    if ((pos + 15) > seqProt.length()) {
                        proSite = seqProt.substring(pos - 15, pos);
                        proSite2 = seqProt.substring(pos);
                        int length = proSite2.length();
                        int diffInt = 15 - length;
                        for (int l = 0; l < diffInt; l++) {
                            proSite2 = proSite2 + " ";
                        }
                        startSite = pos - 15;
                        endSite = seqProt.length();
                    } else {
                        proSite = seqProt.substring(pos - 15, pos);
                        proSite2 = seqProt.substring(pos, pos + 15);
                        startSite = pos - 15;
                        endSite = pos + 15;
                    }
                }
                result.addProcessingSite(new FoundProcessingSite(treatments, pos, proSite, proSite2, startSite, endSite, type, (Integer) idsCount.get(groupId), true, peptide));
                lastProteinId = id;
                siteCount = siteCount + 1;

                //add the treatments to the found treatment vector
                for (int p = 0; p < treatments.length; p++) {
                    boolean found = false;
                    for (int q = 0; q < usedTreatmentsInProtein.size(); q++) {
                        if (treatments[p].equalsIgnoreCase((String) usedTreatmentsInProtein.get(q))) {
                            found = true;
                        }
                    }
                    if (!found) {
                        usedTreatmentsInProtein.add(treatments[p]);
                    }
                }
                for (int p = 0; p < treatments.length; p++) {
                    boolean found = false;
                    for (int q = 0; q < usedTreatments.size(); q++) {
                        if (treatments[p].equalsIgnoreCase((String) usedTreatments.get(q))) {
                            found = true;
                        }
                    }
                    if (!found) {
                        usedTreatments.add(treatments[p]);
                    }
                }
                result.setUsedTreatments(usedTreatmentsInProtein);
                if (newProtein) {
                    //get pdb accessions
                    PreparedStatement prepPdb = null;
                    prepPdb = iConn.prepareStatement("select b.pdbaccession from pdb as b , protein as p where b.l_proteinid = p.proteinid and p.SPaccession = ?");
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

            }


        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        FoundProtein[] proteinsFound = new FoundProtein[results.size()];
        results.toArray(proteinsFound);
        //get for every protein the navigation protein and the sites in sequence 
        for (int i = 0; i < proteinsFound.length; i++) {
            FoundProtein protein = proteinsFound[i];
            Vector sites = protein.getProcessingSites();
            NavigationProteinBar bar = new NavigationProteinBar(sites, protein);
            protein.setNavigationProtein(bar.getNavigationBar());
            protein.setSitesInSequence(this.getSitesInSequence(protein));

        }
        return proteinsFound;
    }

    public String makeNavigationProtein(Vector sites, FoundProtein protein) {
        String html = "";
        int[] sitesLocation = new int[sites.size()];
        Vector treatments = new Vector();
        for (int i = 0; i < sites.size(); i++) {
            FoundProcessingSite site = (FoundProcessingSite) sites.get(i);
            sitesLocation[i] = site.getPosition();
            treatments.add(site.getTreatments());
        }

        //sort
        int value;
        int position;
        for (int i = 0; i < sitesLocation.length; i++) {
            value = sitesLocation[i];
            String[] valueTreat = (String[]) treatments.get(i);
            position = i;
            while ((position > 0) && (sitesLocation[position - 1] < value)) {
                sitesLocation[position] = sitesLocation[position - 1];
                treatments.set(position, treatments.get(position - 1));
                position--;
            }
            sitesLocation[position] = value;
            treatments.set(position, valueTreat);

        }

        double seqLength = protein.getSequence().length();
        double figAaLength = 924.0 / seqLength;
        Vector pictureElements = new Vector();
        for (int i = 0; i < sitesLocation.length; i++) {
            if (i == 0) {
                int length = (int) ((figAaLength * (seqLength - sitesLocation[i])) - 1); //-3 to make the splice site picture a bit bigger
                String link = "noLink";
                SearchOptions.PictureElement element = new SearchOptions.PictureElement(length, "normale", link, "normale.JPG", 0, 0);
                pictureElements.add(element);
            }
            if (sitesLocation.length == 1 || i == (sitesLocation.length - 1)) {
                int length = 5;
                String link = "noLink";
                String[] treat = (String[]) treatments.get(i);
                String treatment = "";
                for (int j = 0; j < treat.length - 1; j++) {
                    treatment = treatment + treat[j] + ", ";
                }
                treatment = treatment + treat[(treat.length - 1)];
                SearchOptions.PictureElement element;
                if (treat.length == 1) {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site.jpg", sitesLocation[i], sitesLocation[i]);
                } else {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site2.jpg", sitesLocation[i], sitesLocation[i]);
                }
                element.setTreatments(treat);
                element.setPosition(sitesLocation[i] - 1);
                pictureElements.add(element);

                int lengthNorm = (int) ((figAaLength * sitesLocation[i]) - 5);
                String linka = "noLink";
                SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, "normale", linka, "normale.JPG", 0, 0);
                pictureElements.add(elementNorm);
            } else {

                int length = 5;
                String link = "noLink";
                String[] treat = (String[]) treatments.get(i);
                String treatment = "";
                for (int j = 0; j < treat.length - 1; j++) {
                    treatment = treatment + treat[j] + ", ";
                }
                treatment = treatment + treat[(treat.length - 1)];
                SearchOptions.PictureElement element;
                if (treat.length == 1) {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site.jpg", sitesLocation[i], sitesLocation[i]);
                } else {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site2.jpg", sitesLocation[i], sitesLocation[i]);
                }

                element.setTreatments(treat);
                element.setPosition(sitesLocation[i] - 1);
                pictureElements.add(element);

                int lengthNorm = (int) ((figAaLength * (sitesLocation[i] - sitesLocation[i + 1])) - 5);
                if (lengthNorm > 0) {
                    String linka = "noLink";
                    SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, "normale", linka, "normale.JPG", 0, 0);
                    pictureElements.add(elementNorm);
                }


            }
        }

        for (int i = 0; i < pictureElements.size(); i++) {
            SearchOptions.PictureElement ele = (SearchOptions.PictureElement) pictureElements.get(i);
            if (!ele.getName().equalsIgnoreCase("normale")) {
                String eleInfo = "Treatment: " + ele.getName() + " Site: " + ele.getStart();
                String picture = "<img style=\"cursor:pointer;\" src =\"images/" + ele.getPicture() + "\" onmouseover=\"replaceProteinInfo('" + protein.getSpAccession() + "','" + eleInfo + "')\"";
                picture = picture + "\" onclick=\"";
                for (int j = 0; j < ele.getTreatments().length; j++) {
                    picture = picture + "showHidePosition('" + protein.getSpAccession() + "_" + ele.getTreatments()[j] + "." + ele.getPosition() + "','img'),";
                }
                picture = picture.substring(0, picture.lastIndexOf(",")) + "\"";
                picture = picture + " width =\"" + ele.getLength() + "\" height =\"46\"/>";
                for (int j = 0; j < ele.getTreatments().length; j++) {
                    picture = picture + "<span id=\"" + protein.getSpAccession() + "_" + ele.getTreatments()[j] + "." + ele.getPosition() + ".Navigator\"style=\"display:none;\">none</span>";
                }
                html = picture + html;
            } else {
                html = "<img src =\"images/" + ele.getPicture() + "\" width =\"" + ele.getLength() + "\" height =\"46\"/>" + html;
            }
        }
        html = "<img src=\"images/start.JPG\" height =\"46\"/>" + html + "<img src=\"images/end.JPG\" height =\"46\"/>";
        return html;
    }

    public String getSitesInSequence(FoundProtein protein) {
        // check which sites are coupled to which treatements
        Vector proteinSites = protein.getProcessingSites();
        Vector usedTreatments = protein.getUsedTreatments();
        boolean[][] siteForTreatment = new boolean[proteinSites.size()][usedTreatments.size()];

        //set all to false
        for (int k = 0; k < usedTreatments.size(); k++) {
            for (int l = 0; l < proteinSites.size(); l++) {
                siteForTreatment[l][k] = false;
            }
        }

        for (int k = 0; k < usedTreatments.size(); k++) {
            for (int l = 0; l < proteinSites.size(); l++) {
                FoundProcessingSite site = (FoundProcessingSite) proteinSites.get(l);
                String[] treatmensFotThisSite = site.getTreatments();
                for (int m = 0; m < treatmensFotThisSite.length; m++) {
                    if (treatmensFotThisSite[m].equalsIgnoreCase((String) usedTreatments.get(k))) {
                        siteForTreatment[l][k] = true;
                    }
                }
            }
        }
        //make an array of (processing site)positions
        int[] positions = new int[proteinSites.size()];
        for (int i = 0; i < positions.length; i++) {
            FoundProcessingSite site = (FoundProcessingSite) proteinSites.get(i);
            positions[i] = site.getPosition();
        }

        String sequence = protein.getSequence();
        //put a space every 10th position
        int substractor = sequence.length();
        StringBuffer seq = new StringBuffer();
        seq.append(sequence);
        int spaceInsertPosition = 0;
        while (substractor >= 0) {
            substractor = substractor - 10;
            spaceInsertPosition = spaceInsertPosition + 1;
        }
        for (int i = 0; i < spaceInsertPosition; i++) {
            seq.insert((i) * 10 + i, " ");
        }
        String sequence10 = seq.toString();

        //sort the sites array
        int position;
        int value;
        //also sort the booleans that say if a site is found with a treatment
        boolean[] positionForTreatment;
        for (int i = 0; i < positions.length; i++) {
            value = positions[i];
            position = i;
            int length = siteForTreatment[i].length;
            positionForTreatment = new boolean[length];
            for (int m = 0; m < siteForTreatment[i].length; m++) {
                positionForTreatment[m] = siteForTreatment[i][m];
            }

            while ((position > 0) && (positions[position - 1] < value)) {
                positions[position] = positions[position - 1];
                for (int j = 0; j < usedTreatments.size(); j++) {
                    siteForTreatment[position][j] = siteForTreatment[position - 1][j];
                }
                position--;
            }
            positions[position] = value;
            siteForTreatment[position] = positionForTreatment;
        }


        for (int j = 0; j < positions.length; j++) {
            int insertPositionPlusSpaces = (positions[j] - 1) / 10;

            String sub1 = sequence10.substring(0, positions[j] + insertPositionPlusSpaces + 1);
            String sub2 = sequence10.substring(positions[j] + insertPositionPlusSpaces + 1);
            String insert = "";
            for (int k = 0; k < usedTreatments.size(); k++) {
                if (siteForTreatment[j][k]) {
                    insert = insert + "<img style=\"display:none\" title=\"" + usedTreatments.get(k) + " (position: " + positions[j] + ")" + "\"id=\"" + protein.getSpAccession() + "_" + usedTreatments.get(k) + "." + (positions[j] - 1) + "\" src =\"images/scissorsSmall" + k + ".jpg\" align=\"middle\">";
                }
            }
            sequence10 = sub1 + insert + sub2;


        }
        return sequence10;
    }

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}