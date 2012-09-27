package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.GlobalAlignment;
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
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 05-Jan-2011
 * Time: 07:51:40
 * To change this template use File | Settings | File Templates.
 */
public class ShowCsvServlet extends HttpServlet {


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

        //get session
        HttpSession session = req.getSession(true);
        //get searches, session, proteins, protein number and site number
        PerformedSearches searches = (PerformedSearches) session.getValue("searches");
        String ses = req.getParameter("session");
        int sessionid = Integer.valueOf(ses);
        FoundProtein[] proteins = searches.getFoundProteins(sessionid);
        String lResult = "Protein,Prime site,Non-prime site,Position,Identified peptide,Treatment<br>";

        for (int j = 0; j < proteins.length; j++) {
            Vector sitesVector = proteins[j].getProcessingSites();
            for (int s = 0; s < sitesVector.size(); s++) {

                FoundProcessingSite site = (FoundProcessingSite) sitesVector.get(s);
                if(site.isShow()){
                    lResult = lResult + proteins[j].getSpAccession();
                    lResult = lResult + "," + site.getPreSite().trim() + "," + site.getPostSite().trim() + "," + site.getPosition() + "," + site.getPeptide();
                    String lTreat = "";
                    for(int t = 0; t<site.getTreatments().length; t ++){
                        lTreat = lTreat + site.getTreatments()[t] + ";";
                    }
                    lTreat = lTreat.substring(0, lTreat.lastIndexOf(";"));
                    lResult = lResult + "," + lTreat + "<br>";
                }
            }


        }


        session.putValue("csv", lResult);

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/csv.jsp");
        rd.forward(req, res);


    }

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}
