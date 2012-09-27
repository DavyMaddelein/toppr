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
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 29-apr-2008
 * Time: 15:47:25
 * To change this template use File | Settings | File Templates.
 */
public class SelectSiteServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get session
        HttpSession session = req.getSession(true);
        //get searches, session, proteins, protein number and site number
        PerformedSearches searches = (PerformedSearches) session.getValue("searches");
        String ses = req.getParameter("session");
        int sessionid = Integer.valueOf(ses);
        FoundProtein[] proteins = searches.getFoundProteins(sessionid);
        String pro = req.getParameter("protein");
        int proteinInt = Integer.valueOf(pro);
        String sit = req.getParameter("site");
        int siteInt = Integer.valueOf(sit);
        //get the correct protein and site
        FoundProtein protein = proteins[proteinInt];
        Vector sites = protein.getProcessingSites();
        FoundProcessingSite site = (FoundProcessingSite) sites.get(siteInt);
        //invert the selection
        boolean selected = site.getSelected();
        if (selected) {
            site.setSelected(false);
        } else {
            site.setSelected(true);
        }
        // set up the response
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        // write out the response string
        res.getWriter().write("ok");
    }
}