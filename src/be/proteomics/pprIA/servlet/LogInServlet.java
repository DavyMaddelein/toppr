package be.proteomics.pprIA.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 16-jan-2008
 * Time: 11:34:12
 * To change this template use File | Settings | File Templates.
 */
public class LogInServlet extends HttpServlet {
    private Connection lConn;

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String driverClass = "com.mysql.jdbc.Driver";
        String userName = req.getParameter("name");
        String pass = req.getParameter("password");
        try {
            com.mysql.jdbc.Driver d = (com.mysql.jdbc.Driver) Class.forName(driverClass).newInstance();
            Properties lProps = new Properties();
            lProps.put("user", userName);
            lProps.put("password", pass);
            lConn = d.connect("jdbc:mysql://localhost/ppr", lProps);

        } catch (ClassNotFoundException cnfe) {
            String url = "/logInError.html";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);
            return;
        } catch (IllegalAccessException iae) {
            String url = "/logInError.html";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);
            return;
        } catch (InstantiationException ie) {
            String url = "/logInError.html";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);
            return;
        } catch (SQLException sqle) {
            String url = "/logInError.html";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);
            return;
        }
        if (lConn != null) {
            HttpSession session = req.getSession(true);
            session.putValue("userName", userName);
            String url = "/LogInOk.html";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);
            return;
        } else {
            String url = "/logInError.html";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);
            return;
        }

    }
}
