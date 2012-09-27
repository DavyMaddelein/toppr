package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.Weblogo;
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
 * Time: 12:29:21
 * To change this template use File | Settings | File Templates.
 */
public class WeblogoViewStarterServlet extends HttpServlet {


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        HttpSession session = req.getSession(true);
        String[] treatments = req.getParameterValues("treatment");
        if (treatments == null) {
            session.putValue("weblogo", "treatment");
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/weblogoFrame.jsp");
            rd.forward(req, res);
            return;
        }
        Double bits = Double.valueOf(req.getParameter("bits"));
        String ses = req.getParameter("session");
        int sessionid = Integer.valueOf(ses);
        String selected = req.getParameter("weblogoSelected");
        Integer width = Integer.valueOf(req.getParameter("width"));
        PerformedSearches searches = (PerformedSearches) session.getValue("searches");

        String sequences = searches.getPeptideList(sessionid, treatments, selected);
        if (sequences.length() == 0) {
            session.putValue("weblogo", "selection");
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/weblogoFrame.jsp");
            rd.forward(req, res);
            return;
        }
        Weblogo weblogo = new Weblogo(sequences, searches.getListCounted(), bits, width);
        String url = weblogo.getUrl();
        session.putValue("weblogo", url);

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/weblogoFrame.jsp");
        rd.forward(req, res);

    }
}
