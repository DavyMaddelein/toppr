package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.protein_info.Domain;
import be.proteomics.pprIA.general.util.NavigationProteinBar;
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
 * Date: 7-mei-2008
 * Time: 9:00:03
 * To change this template use File | Settings | File Templates.
 */
public class ShowDomainServlet extends HttpServlet {


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

            NavigationProteinBar bar = new NavigationProteinBar(sites, protein);
            String sitesPictureHtml = bar.getNavigationBar();


            String swissProtId = protein.getSpAccession();
            //get connection from connection pool
            iConn = this.getConnection();

            //find the proteins for the given parameters
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("select d.* from protein as r, domain as d where r.spaccession = ? and r.proteinid = d.l_proteinid");
            prep.setString(1, swissProtId);
            ResultSet rs = prep.executeQuery();
            Vector domainVectorSmart = new Vector();
            Vector domainVectorPfam = new Vector();
            while (rs.next()) {
                if(rs.getString("type").equalsIgnoreCase("Smart")){
                        String link = "http://smart.embl-heidelberg.de/smart/do_annotation.pl?DOMAIN=" + rs.getString("domaintermid");
                        domainVectorSmart.add(new Domain(rs.getString("name"), link, rs.getInt("start"), rs.getInt("end")));
                }  else {
                        String link = "http://pfam.sanger.ac.uk/family?acc=" + rs.getString("domaintermid");
                        domainVectorPfam.add(new Domain(rs.getString("name"), link, rs.getInt("start"), rs.getInt("end")));
                }
            }
            prep.close();
            rs.close();


            Domain[] smartDomains = new Domain[domainVectorSmart.size()];
            domainVectorSmart.toArray(smartDomains);
            String smartDomainHtml = bar.getDomainHtml(smartDomains, "Smart");

            Domain[] pfamDomains = new Domain[domainVectorPfam.size()];
            domainVectorPfam.toArray(pfamDomains);
            String pfamDomainHtml = bar.getDomainHtml(pfamDomains, "Pfam");

            String html = sitesPictureHtml + "<br>" + smartDomainHtml + "<br>" + pfamDomainHtml;

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

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }


}