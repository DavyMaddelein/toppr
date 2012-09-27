package be.proteomics.pprIA.servlet.search;

import be.proteomics.pprIA.search.PerformedSearches;
import be.proteomics.pprIA.search.FoundProtein;
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
import java.util.Vector;
import java.util.HashMap;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 5-jun-2008
 * Time: 15:18:51
 * To change this template use File | Settings | File Templates.
 */

public class SiteSearchServlet extends HttpServlet {
    private String userName;
    private int proteinCount = 0;
    private int siteCount = 0;
    private Connection iConn;
    private DataSource datasource = null;
    private Vector uniquePeptide = new Vector();
    private int sessionNumber;
    Vector usedTreatments;
    private String pre = "";
    private String post = "";
    private String[] preArray;
    private String[] postArray;
    private int preLength = 0;
    private int postLength = 0;

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
            PerformedSearches searches = (PerformedSearches) session.getValue("searches");
            if (searches == null) {
                searches = new PerformedSearches();
            }
            //find the site parameters
            pre = "";
            post = "";
            preLength = 0;
            postLength = 0;
            uniquePeptide = new Vector();
            pre = req.getParameter("pre");
            post = req.getParameter("post");
            String searchSite = pre + " " + post;
            preLength = pre.length();
            for (int c = 0; c < pre.length(); c++) {
                if (pre.charAt(c) == '/') {
                    preLength = preLength - 4;
                }
            }
            postLength = post.length();
            for (int c = 0; c < post.length(); c++) {
                if (post.charAt(c) == '/') {
                    postLength = postLength - 4;
                }
            }
            // make regexp arrays
            preArray = new String[preLength];
            postArray = new String[postLength];
            int count = 0;
            for (int m = 0; m < pre.length(); m++) {
                if (pre.charAt(m) != '[') {
                    preArray[count] = String.valueOf(pre.charAt(m));
                } else {
                    preArray[count] = pre.substring(m, pre.indexOf(']', m) + 1);
                    m = m + 4;
                }
                count = count + 1;
            }
            count = 0;
            for (int m = 0; m < post.length(); m++) {
                if (post.charAt(m) != '[') {
                    postArray[count] = String.valueOf(post.charAt(m));
                } else {
                    postArray[count] = post.substring(m, post.indexOf(']', m) + 1);
                    m = m + 4;
                }
                count = count + 1;
            }

            String preMysql = pre.replace("*", "[A-Z]");
            String postMysql = post.replace("*", "[A-Z]");
            // make a sql query based on the requested parameters

            String sqlQuery = "select r.proteinid from peptide as p, peptide_location as l, protein as r where r.proteinid = l.l_proteinid ";
            if (pre.length() != 0) {
                sqlQuery = sqlQuery + "AND SUBSTR(r.sequence, l.location - " + preLength + ", " + preLength + ") REGEXP '" + preMysql + "'";
            }
            if (post.length() != 0) {
                sqlQuery = sqlQuery + "AND SUBSTR(r.sequence, l.location , " + postLength + ") REGEXP '" + postMysql + "'";
            }
            sqlQuery = sqlQuery + " and p.l_groupid = l.l_groupid and p.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))" +
                    " group by r.proteinid";

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
            String[] params = new String[2];
            params[0] = "Processing site search: ";
            params[1] = searchSite;
            String[] searchedTreatments = new String[usedTreatments.size()];
            usedTreatments.toArray(searchedTreatments);
            searches.addSearch(result, params, false, false, false, proteinCount, siteCount, searchedTreatments, uniquePeptide);


            proteinCount = 0;
            siteCount = 0;
            session.putValue("searches", searches);
            session.putValue("session", -1);
            String url = "/searchResult.jsp";
            proteinCount = 0;
            siteCount = 0;
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);
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
    }

    private FoundProtein[] findProteinResults(ResultSet aRs, ResultSet aaRs) {
        String groupIds = "";
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


                boolean use = true;


                if (use) {

                    boolean newProtein = false;
                    //create protein result
                    if (lastProteinId == id) {
                        result = (FoundProtein) results.get(results.size() - 1);
                    } else {
                        result = new FoundProtein(seqProt, accession, entry, description);
                        newProtein = true;
                        usedTreatmentsInProtein = new Vector();
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

                    //check if the site matches the searched profile
                    boolean matchSite = true;
                    try {
                        String preMatch = seqProt.substring(pos - preLength, pos);
                        String postMatch = seqProt.substring(pos, pos + postLength);
                        for (int m = 0; m < preLength; m++) {
                            if (preArray[m].equalsIgnoreCase("*")) {
                                // no problemo
                            } else {
                                if (preArray[m].startsWith("[")) {
                                    char char1 = preArray[m].charAt(1);
                                    char char2 = preArray[m].charAt(3);

                                    if (preMatch.charAt(m) == char1 || preMatch.charAt(m) == char2) {
                                        // no problemo
                                    } else {
                                        matchSite = false;
                                        m = preLength;
                                    }
                                } else {
                                    if (preArray[m].equalsIgnoreCase(String.valueOf(preMatch.charAt(m)))) {
                                        // no problemo
                                    } else {
                                        matchSite = false;
                                        m = preLength;
                                    }
                                }
                            }
                        }
                        for (int m = 0; m < postLength; m++) {
                            if (postArray[m].equalsIgnoreCase("*")) {
                                // no problemo
                            } else {
                                if (postArray[m].startsWith("[")) {
                                    char char1 = postArray[m].charAt(1);
                                    char char2 = postArray[m].charAt(3);
                                    if (postMatch.charAt(m) == char1 || postMatch.charAt(m) == char2) {
                                        // no problemo
                                    } else {
                                        matchSite = false;
                                        m = postLength;
                                    }
                                } else {
                                    if (postArray[m].equalsIgnoreCase(String.valueOf(postMatch.charAt(m)))) {
                                        // no problemo
                                    } else {
                                        matchSite = false;
                                        m = postLength;
                                    }
                                }
                            }
                        }
                        //only add treatments to the used treatment list, if the site matches the profile
                        if (matchSite) {
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
                            //add peptide_groupid to the unique peptide vector
                            boolean foundGroupid = false;
                            for (int q = 0; q < uniquePeptide.size(); q++) {
                                if (groupId == (Integer) uniquePeptide.get(q)) {
                                    foundGroupid = true;
                                }
                            }
                            if (!foundGroupid) {
                                uniquePeptide.add(groupId);
                            }
                        }
                    } catch (StringIndexOutOfBoundsException s) {
                        matchSite = false;
                    }
                    if (matchSite) {
                        siteCount = siteCount + 1;
                    }
                    result.addProcessingSite(new FoundProcessingSite(treatments, pos, proSite, proSite2, startSite, endSite, type, (Integer) idsCount.get(groupId), matchSite, peptide));
                    lastProteinId = id;

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