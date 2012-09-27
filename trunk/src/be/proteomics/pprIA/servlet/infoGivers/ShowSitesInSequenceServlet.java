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
 * Date: 29-apr-2008
 * Time: 16:15:15
 * To change this template use File | Settings | File Templates.
 */
public class ShowSitesInSequenceServlet extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        // get the performed searches , the sessionid and the proteins
        PerformedSearches searches = (PerformedSearches) session.getValue("searches");
        String ses = req.getParameter("session");
        int sessionid = Integer.valueOf(ses);
        FoundProtein[] proteins = searches.getFoundProteins(sessionid);

        String proteinString = req.getParameter("protein");
        int proteinNumber = Integer.valueOf(proteinString);

        FoundProtein protein = proteins[proteinNumber];

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
                    insert = insert + "<img src =\"images/scissorsSmall" + k + ".jpg\" align=\"middle\">";
                }
            }
            sequence10 = sub1 + insert + sub2;


        }
        String result = sequence10;
        // set up the response
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        // write out the response string
        res.getWriter().write(result);
    }


}