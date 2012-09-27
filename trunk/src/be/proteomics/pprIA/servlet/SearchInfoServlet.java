package be.proteomics.pprIA.servlet;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 13-feb-2008
 * Time: 13:26:21
 * To change this template use File | Settings | File Templates.
 */
public class SearchInfoServlet extends HttpServlet {

    private String userName;
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


        HttpSession session = req.getSession(true);
        String ses = req.getParameter("session");
        Integer sessionid = Integer.valueOf(ses);
        session.putValue("session", sessionid);

        String url = "/searchResult.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(req, res);

    }


}