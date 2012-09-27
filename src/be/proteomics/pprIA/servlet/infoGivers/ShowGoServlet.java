package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.protein_info.GoTerm;
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
 * Date: 24-jan-2008
 * Time: 17:04:18
 * To change this template use File | Settings | File Templates.
 */
public class ShowGoServlet extends HttpServlet {

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


            String swissProtId = protein.getSpAccession();
            //get connection from connection pool
            iConn = this.getConnection();

            //find the proteins for the given parameters
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("select g.* from protein as r, go as g  where r.spaccession =? and r.proteinid = g.l_proteinid");
            prep.setString(1, swissProtId);
            ResultSet rs = prep.executeQuery();
            Vector goVector = new Vector();
            while (rs.next()) {
                goVector.add(new GoTerm(rs.getString("name"), rs.getString("type"), rs.getString("gotermid")));
            }
            prep.close();
            rs.close();

            GoTerm[] gos = new GoTerm[goVector.size()];
            goVector.toArray(gos);
            String html = makeHtmlTable(gos);
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

    public String makeHtmlTable(GoTerm[] gos) {
        String html = "Nothing found";

        if (gos.length > 0) {
            html = "<table width=\"100%\">";
            html = html + "<tr><th width=\"30%\">Category</th><th width=\"50%\">Name</th><th width=\"20%\">Id</th></tr>";
            for (int i = 0; i < gos.length; i++) {
                GoTerm go = gos[i];
                if(go.getCategory().equalsIgnoreCase("C")){
                    html = html + "<tr><td width=\"20%\">" + "cellular component";
                } else {
                    if(go.getCategory().equalsIgnoreCase("F")){
                        html = html + "<tr><td width=\"20%\">" + "molecular function";
                    } else {
                        html = html + "<tr><td width=\"20%\">" + "biological process";
                    }
                }

                html = html +  "</td><td width=\"30%\"> " + go.getName() + "</td><td width=\"20%\"><a href=\"http://www.ebi.ac.uk/ego/GTerm?id=" + go.getId() + "\" target=\"_blank\">" + go.getId() + "</a></td></tr>";
            }
            html = html + "</table>";
        }

        return html;

    }

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}