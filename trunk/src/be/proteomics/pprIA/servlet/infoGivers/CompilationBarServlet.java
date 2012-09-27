package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.search.PerformedSearches;
import be.proteomics.pprIA.search.FoundProtein;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 17-jun-2008
 * Time: 10:48:24
 * To change this template use File | Settings | File Templates.
 */
public class CompilationBarServlet  extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //get session
        HttpSession session = req.getSession(true);
        //get searches, session, proteins, protein number and site number
        PerformedSearches searches = (PerformedSearches) session.getValue("searches");
        String result = "";


        int sessionNumber = -1;
            int sessionCount = -2;
            boolean newCompilation = false;
            if (searches == null) {
                searches = new PerformedSearches();
                sessionNumber = 0;
            } else {
                //try to find to correct session
                sessionCount = searches.getSessionCount();
                for(int i = 0; i<sessionCount; i ++) {
                    if(searches.getCompilation(i)){
                        sessionNumber = i;
                        i = sessionCount;
                    }
                }
                if(sessionNumber == -1){
                    sessionNumber = sessionCount;
                    newCompilation = true;
                }
            }
            String text = "";
            if(sessionNumber == sessionCount){
                text = " no proteins found in  compilation!";
            } else {
                FoundProtein[] proteins = searches.getFoundProteins(sessionNumber);
                text = proteins[0].getSpAccession() + "<img src=\"images/del.jpg\" align=\"top\" style=\"cursor:pointer;\" onclick=\"deleteProtein('" + proteins[0].getSpAccession() +"')\">";
                for(int p = 1; p< proteins.length; p ++){
                    text = text + ", " + proteins[p].getSpAccession() + "<img src=\"images/del.jpg\" align=\"top\" style=\"cursor:pointer;\" onclick=\"deleteProtein('" + proteins[p].getSpAccession() +"')\">";
                }
            }
        result = text;

        // set up the response
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        // write out the response string
        res.getWriter().write(result);



    }



}