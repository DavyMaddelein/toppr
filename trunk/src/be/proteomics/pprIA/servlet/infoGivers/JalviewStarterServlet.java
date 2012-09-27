package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.JalviewResult;
import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.PerformedSearches;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 30-apr-2008
 * Time: 14:04:49
 * To change this template use File | Settings | File Templates.
 */
public class JalviewStarterServlet extends HttpServlet {


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Vector jalview = new Vector();
        HttpSession session = req.getSession(true);
        String[] treatments = req.getParameterValues("treatment");
        if (treatments == null) {
            session.putValue("jalview", jalview);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/jalview.jsp");
            rd.forward(req, res);
            return;
        }

        String ses = req.getParameter("session");
        int sessionid = Integer.valueOf(ses);
        String selected = req.getParameter("jalviewSelected");

        PerformedSearches searches = (PerformedSearches) session.getValue("searches");
        FoundProtein[] proteins = searches.getFoundProteins(sessionid);


        for (int j = 0; j < proteins.length; j++) {
            //String sequence = proteins[j].getSequence();
            Vector sitesVector = proteins[j].getProcessingSites();
            for (int k = 0; k < sitesVector.size(); k++) {
                FoundProcessingSite site = (FoundProcessingSite) sitesVector.get(k);
                String[] treats = site.getTreatments();
                boolean use = false;
                for (int l = 0; l < treats.length; l++) {
                    for (int m = 0; m < treatments.length; m++)
                        if (treats[l].equalsIgnoreCase(treatments[m])) {
                            use = true;
                        }
                }

                if (use) {
                    if (selected != null) {
                        //only the selected proteins will be used to make the jalview
                        if (site.getSelected()) {
                            String start = site.getPreSite();
                            String end = site.getPostSite();
                            String processing = start + end;
                            processing = processing.replace(' ', 'X');
                            String accession = proteins[j].getSpAccession() + "-Site:" + site.getPosition();
                            JalviewResult result = new JalviewResult(accession, processing);
                            jalview.add(result);

                        }
                    } else {
                            if(site.isShow()){
                            String start = site.getPreSite();
                            String end = site.getPostSite();
                            String processing = start + end;
                            processing = processing.replace(' ', 'X');
                            boolean found = false;
                            for (int l = 0; l < jalview.size(); l++) {

                                if (processing.equalsIgnoreCase(((JalviewResult) jalview.get(l)).getSequence())) {
                                    found = true;
                                }
                            }
                            if (!found) {
                                String accession = proteins[j].getSpAccession() + "-Site:" + site.getPosition();
                                JalviewResult result = new JalviewResult(accession, processing);
                                jalview.add(result);
                            }
                        }         
                    }
                }
            }
        }

        session.putValue("jalview", jalview);

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/jalview.jsp");
        rd.forward(req, res);

    }


}