package be.proteomics.pprIA.general;


import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 16-jan-2008
 * Time: 9:41:15
 * To change this template use File | Settings | File Templates.
 */

public class DBPoolingListener implements javax.servlet.ServletContextListener {

    public void contextInitialized (ServletContextEvent sce){
          
        try {
            // Obtain our environment naming context
            Context envCtx = (Context) new InitialContext().lookup("java:comp/env");

            // Look up our data source
            DataSource  ds = (DataSource) envCtx.lookup("jdbc/TestDB");

            sce.getServletContext().setAttribute("DBPool", ds);
        } catch(NamingException e){
            e.printStackTrace();
        }
    }
    public void contextDestroyed(ServletContextEvent sce){

    }
}


