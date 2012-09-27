package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.PerformedSearches;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 6-mei-2008
 * Time: 8:20:35
 * To change this template use File | Settings | File Templates.
 */
public class SecondaryStructurePredictionServlet extends HttpServlet {


    private Connection iConn;
    private DataSource datasource = null;


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
            //get session
            HttpSession session = req.getSession(true);
            //get searches, session, proteins, protein number and site number
            PerformedSearches searches = (PerformedSearches) session.getValue("searches");
            String ses = req.getParameter("session");
            int sessionid = Integer.valueOf(ses);
            FoundProtein[] proteins = searches.getFoundProteins(sessionid);
            String pro = req.getParameter("protein");
            FoundProtein protein = null;

            for( int p = 0; p<proteins.length;  p ++){
                if(proteins[p].getSpAccession().equalsIgnoreCase(pro)){
                     protein = proteins[p];
                }
            }
            Vector sites = protein.getProcessingSites();
            Vector positions = new Vector();
            for (int i = 0; i < sites.size(); i++) {
                FoundProcessingSite site = (FoundProcessingSite) sites.get(i);
                positions.add(site.getPosition());
            }


            String swissProtId = protein.getSpAccession();
            //get connection from connection pool
            iConn = this.getConnection();

            //find the proteins for the given parameters
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("select * from protein where spaccession =?");
            prep.setString(1, swissProtId);
            ResultSet rs = prep.executeQuery();
            String sequence = "";
            String prediction = "";
            String swissprotSecStruc = "";
            while (rs.next()) {
                sequence = rs.getString("sequence");
                prediction = rs.getString("sec_structure_prediction");
                swissprotSecStruc = rs.getString("sec_structure_swissprot");
            }
            prep.close();
            rs.close();

            String html = setSecondaryStructureOnSiteSequence(getSitesInSequence(protein).trim(), prediction);
            // set up the response
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            // write out the response string
            res.getWriter().write(html);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                if (iConn != null) iConn.close();
            } catch (SQLException e) {
            }
        }

    }



    public String getSitesInSequence(FoundProtein protein){
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
                if (siteForTreatment[j][k]){
                    insert = insert + "<img style=\"display:none\" title=\"" + usedTreatments.get(k) + " (position: " + positions[j] + ")" + "\"id=\"" + protein.getSpAccession()+"_" + usedTreatments.get(k) + "." + (positions[j] - 1) + "\" src =\"images/scissorsSmall" + k + ".jpg\" align=\"middle\">";
                }
            }
            sequence10 = sub1 + insert + sub2;


        }
        return sequence10;
    }

    public String setSecondaryStructureOnSiteSequence(String lSiteSequence, String lSecondaryStructure){
        boolean inImgElement = false;
        int outOfImgCounter = 0;
        String result = "";
        for(int s = 0; s<lSiteSequence.length();s ++){
            if(inImgElement){
                if(lSiteSequence.charAt(s) == '>'){
                    inImgElement = false;
                }
                result = result + lSiteSequence.charAt(s);
            } else {
                if(lSiteSequence.charAt(s) == '<'){
                    inImgElement = true;
                    result = result + lSiteSequence.charAt(s);
                }  else if( lSiteSequence.charAt(s) != ' ') {
                    String lStructureClass = "";
                    if (lSecondaryStructure.charAt(outOfImgCounter) == '-') {
                        lStructureClass = "coil";
                    }
                    if (lSecondaryStructure.charAt(outOfImgCounter) == 'E') {
                        lStructureClass = "beta";
                    }
                    if (lSecondaryStructure.charAt(outOfImgCounter) == 'T') {
                        lStructureClass = "turn";
                    }
                    if (lSecondaryStructure.charAt(outOfImgCounter) == 'H') {
                        lStructureClass = "alpha";
                    }
                    result = result + "<div class=\"" + lStructureClass + "\">"+lSiteSequence.charAt(s)+"</div>";
                    outOfImgCounter = outOfImgCounter + 1;
                } else {
                    if(outOfImgCounter%40 == 0){
                        result = result  +"<div> </div>";
                    } else {
                        result = result  +"<span>&nbsp</span>";
                        //result = result  +lSiteSequence.charAt(s);
                    }

                }
            }

        }
        return result;
    }


    public String getSequence10(String sequenceNormal) {
        int substractor = sequenceNormal.length();
        int spaceInsertPosition = 0;
        while (substractor >= 0) {
            substractor = substractor - 10;
            spaceInsertPosition = spaceInsertPosition + 1;
        }
        String sequence = "";
        for (int j = 0; j < spaceInsertPosition; j++) {
            if (j == spaceInsertPosition - 1) {
                int start = j * 10;
                sequence = sequence + sequenceNormal.substring(start);
            } else {
                int start = j * 10;
                int end = start + 10;
                sequence = sequence + sequenceNormal.substring(start, end) + " ";
            }

        }

        return sequence;
    }

    public String[] splitSeq10(String seq10) {
        int lines = seq10.length() / 40;
        if (lines * 40 < seq10.length()) {
            lines = lines + 1;
        }
        String[] splitSeq = new String[lines];
        for (int i = 0; i < lines; i++) {
            if (i == lines - 1) {
                splitSeq[i] = seq10.substring(i * 40);
            } else {
                splitSeq[i] = seq10.substring(i * 40, (i + 1) * 40);
            }

        }
        return splitSeq;
    }

    public String getHtml(String[] seq, String[] secResult, String[] sp, Vector processingSites) {
        String html = "<tt>";
        boolean spFound = false;
        if (sp.length == seq.length) {
            spFound = true;
        }
        int count = 1;

        html = html + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
        for (int i = 0; i < seq.length; i++) {
            html = html + "<tr>";

            for (int j = 0; j < seq[i].length(); j++) {
                html = html + "<td><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
                if (count % 10 == 0) {
                    html = html + "<tr><td>" + count + "</td></tr>";
                } else {
                    html = html + "<tr><td>" + "&nbsp;" + "</td></tr>";
                }
                boolean processingSiteFound = false;
                for (int k = 0; k < processingSites.size(); k++) {
                    int site = (Integer) processingSites.get(k);
                    site = site + 1;
                    if (site == count) {
                        processingSiteFound = true;
                    }
                }
                if (processingSiteFound) {
                    html = html + "<tr><td style=\"background-color: black; color: red\"><b>" + seq[i].charAt(j) + "</b></td></tr>";
                } else {
                    html = html + "<tr><td>" + seq[i].charAt(j) + "</td></tr>";
                }



                //prediction
                if (secResult[i].charAt(j) == '-') {
                    html = html + "<tr><td>" + "<img src =\"images/coil.JPG\">" + "</td></tr>";
                }
                if (secResult[i].charAt(j) == 'T') {
                    html = html + "<tr><td>" + "<img src =\"images/turn.JPG\">" + "</td></tr>";
                }
                if (secResult[i].charAt(j) == 'H') {
                    html = html + "<tr><td>" + "<img src =\"images/alpha.JPG\">" + "</td></tr>";
                }
                if (secResult[i].charAt(j) == 'E') {
                    html = html + "<tr><td>" + "<img src =\"images/beta.JPG\">" + "</td></tr>";
                }

                //Sp secondary structure
                if (spFound) {
                    // html = html + "<tr><td>" + sp[i].charAt(j) + "</td></tr>";
                    if (sp[i].charAt(j) == '-') {
                        html = html + "<tr><td>" + "<img src =\"images/coil.JPG\">" + "</td></tr>";
                    }
                    if (sp[i].charAt(j) == 'T') {
                        html = html + "<tr><td>" + "<img src =\"images/turn.JPG\">" + "</td></tr>";
                    }
                    if (sp[i].charAt(j) == 'H') {
                        html = html + "<tr><td>" + "<img src =\"images/alpha.JPG\">" + "</td></tr>";
                    }
                    if (sp[i].charAt(j) == 'E') {
                        html = html + "<tr><td>" + "<img src =\"images/beta.JPG\">" + "</td></tr>";
                    }
                }
                html = html + "</table></td>";
                count = count + 1;
            }

            html = html + "</tr><tr><br></tr>";
        }
        html = html + "</table></tt>";
        return html;
    }

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}