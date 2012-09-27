package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.InformationBox;

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
 * User: Niklaas Colaert
 * Date: 5-mei-2008
 * Time: 14:00:58
 * To change this template use File | Settings | File Templates.
 */
public class ShowTaxonomysServlet extends HttpServlet {
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

        try {
            iConn = this.getConnection();
            HttpSession session = req.getSession(true);
            userName = (String) session.getValue("userName");
            if (userName == null) {
                userName = "guest";
            }
            String query = "select * from taxonomy where taxonomyid in (select c.l_taxonomy from cell_source as c where c.cell_sourceid in (select r.l_cell_sourceid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))))";
            PreparedStatement prep = null;
            prep = iConn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            InformationBox[] result = this.getTaxonomyResults(rs);
            rs.close();
            prep.close();

            session.putValue("info", result);
            String url = "/showInfo.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);

        }
        catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
        finally {
            try {
                if (iConn != null) iConn.close();
            } catch (SQLException e) {
            }
        }
    }


    private InformationBox[] getTaxonomyResults(ResultSet aRs) {
        Vector results = new Vector();
        ResultSet iRs = aRs;
        try {
            while (iRs.next()) {
                int taxid = iRs.getInt("taxonomyid");
                String species = iRs.getString("species");
                String name = iRs.getString("scientific_name");

                InformationBox info = new InformationBox(name, name);
                info.addElement("Species", species, null);
                info.addElement("Scientific name", name, null);
                info.addElement("NEWT ontology id", String.valueOf(taxid), "http://www.ebi.ac.uk/ontology-lookup/?termId=" + String.valueOf(taxid));
                results.add(info);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        InformationBox[] taxs = new InformationBox[results.size()];
        results.toArray(taxs);
        return taxs;
    }

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}
