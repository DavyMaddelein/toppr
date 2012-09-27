package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.search.PerformedSearches;
import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.general.Weblogo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 20-mei-2008
 * Time: 10:29:58
 * To change this template use File | Settings | File Templates.
 */
public class SiteListServlet extends HttpServlet {


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        HttpSession session = req.getSession(true);
        String[] treatments = req.getParameterValues("treatment");
        if (treatments == null) {
            session.putValue("list", "");
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/listFrame.jsp");
            rd.forward(req, res);
            return;
        }

        String ses = req.getParameter("session");
        int sessionid = Integer.valueOf(ses);
        String selected = req.getParameter("listSelected");

        PerformedSearches searches = (PerformedSearches) session.getValue("searches");
        session.putValue("list", searches.getPeptideList(sessionid, treatments, selected));

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/listFrame.jsp");
        rd.forward(req, res);

    }
}
