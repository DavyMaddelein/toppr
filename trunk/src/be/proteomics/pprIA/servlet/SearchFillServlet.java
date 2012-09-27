package be.proteomics.pprIA.servlet;

import be.proteomics.pprIA.general.SearchOptions;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 16-jan-2008
 * Time: 10:44:45
 * To change this template use File | Settings | File Templates.
 */
public class SearchFillServlet extends HttpServlet {

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
        this.doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            iConn = this.getConnection();
            SearchOptions result = this.getSearchOptions(req);
            HttpSession session = req.getSession(true);
            session.setAttribute("options", result);
            String url = "/search.jsp";
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


    private SearchOptions getSearchOptions(HttpServletRequest req) {
        SearchOptions options = null;
        HttpSession session = req.getSession(true);
        userName = (String) session.getAttribute("userName");
        if (userName == null) {
            userName = "guest";
        }


        try {

            String[] cell_sources = new String[0];
            String[] taxsScientific = new String[0];
            String[] taxs = new String[0];
            String[] treats = new String[0];
            String[] inhibs = new String[0];
            String[] exps = new String[0];
            String[] projects = new String[0];


            if(userName.equalsIgnoreCase("guest")){
               try{
                    //get the species list
                    InputStream is = getServletContext().getResourceAsStream("search.properties");
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader in = new BufferedReader(reader);
                    String strLine;
                    while ((strLine = in.readLine()) != null) {
                        try{

                            String lOptions = strLine.substring(strLine.indexOf(" =") + 3);
                            if(strLine.startsWith("cell_source")){
                                cell_sources = lOptions.split(",");
                            }
                            if(strLine.startsWith("treatment")){
                                treats = lOptions.split(",");
                            }
                            if(strLine.startsWith("inhibitor")){
                                inhibs = lOptions.split(",");
                            }
                            if(strLine.startsWith("experiment_type")){
                                exps = lOptions.split(",");
                            }
                            if(strLine.startsWith("species")){
                                taxs = lOptions.split(",");
                            }
                            if(strLine.startsWith("speciesScientific")){
                                taxsScientific = lOptions.split(",");
                            }
                        } catch (IndexOutOfBoundsException e ){
                            
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } else {

                //get cell sources
                PreparedStatement prep1 = iConn.prepareStatement("select c.name from cell_source as c where c.cell_sourceid in (select r.l_cell_sourceid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))");
                ResultSet rs1 = prep1.executeQuery();
                Vector vect1 = new Vector();
                while (rs1.next()) {
                    vect1.add(rs1.getObject(1));
                }
                cell_sources = new String[vect1.size()];
                vect1.toArray(cell_sources);
                prep1.close();
                rs1.close();

                //get taxonomys
                PreparedStatement prep2 = iConn.prepareStatement("select t.species from taxonomy as t where t.taxonomyid in (select c.l_taxonomy from cell_source as c where c.cell_sourceid in (select r.l_cell_sourceid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))))");
                ResultSet rs2 = prep2.executeQuery();
                Vector vect2 = new Vector();
                while (rs2.next()) {
                    vect2.add(rs2.getObject(1));
                }
                taxs = new String[vect2.size()];
                vect2.toArray(taxs);
                prep2.close();
                rs2.close();

                PreparedStatement prep22 = iConn.prepareStatement("select t.scientific_name from taxonomy as t where t.taxonomyid in (select c.l_taxonomy from cell_source as c where c.cell_sourceid in (select r.l_cell_sourceid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))))");
                ResultSet rs22 = prep22.executeQuery();
                Vector vect22 = new Vector();
                while (rs22.next()) {
                    vect22.add(rs22.getObject(1));
                }
                taxsScientific = new String[vect22.size()];
                vect22.toArray(taxsScientific);
                prep22.close();
                rs22.close();

                //get treatments
                PreparedStatement prep3 = iConn.prepareStatement("select t.scientific_name from treatment as t where t.treatmentid in (select i.l_treatmentid from peptide_treatment_and_inhibitor as i where i.l_identificationid in (select d.identificationid from peptide as d where d.l_projectid in (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))))) order by t.scientific_name");
                ResultSet rs3 = prep3.executeQuery();
                Vector vect3 = new Vector();
                while (rs3.next()) {
                    vect3.add(rs3.getObject(1));
                }
                treats = new String[vect3.size()];
                vect3.toArray(treats);
                prep3.close();
                rs3.close();

                //get inhibitors
                PreparedStatement prep4 = iConn.prepareStatement("select b.scientific_name from inhibitor as b where b.inhibitorid in (select i.l_inhibitorid from peptide_treatment_and_inhibitor as i where i.l_identificationid in (select d.identificationid from peptide as d where d.l_projectid in (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))))");
                prep4 = iConn.prepareStatement("select scientific_name from inhibitor");
                ResultSet rs4 = prep4.executeQuery();
                Vector vect4 = new Vector();
                while (rs4.next()) {
                    vect4.add(rs4.getObject(1));
                }
                inhibs = new String[vect4.size()];
                vect4.toArray(inhibs);
                prep4.close();
                rs4.close();

                //get experiment types
                PreparedStatement prep5 = iConn.prepareStatement("select e.name from experiment as e where e.experimentid in (select r.l_experimentid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))");
                ResultSet rs5 = prep5.executeQuery();
                Vector vect5 = new Vector();
                while (rs5.next()) {
                    vect5.add(rs5.getObject(1));
                }
                exps = new String[vect5.size()];
                vect5.toArray(exps);
                prep5.close();
                rs5.close();

                //get projects
                PreparedStatement prep6 = iConn.prepareStatement("select r.title from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))");
                ResultSet rs6 = prep6.executeQuery();
                Vector vect6 = new Vector();
                while (rs6.next()) {
                    vect6.add(rs6.getObject(1));
                }
                projects = new String[vect6.size()];
                vect6.toArray(projects);
                prep6.close();
                rs6.close();
            }
            options = new SearchOptions(cell_sources, taxs, treats, inhibs, exps, projects, taxsScientific);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            iConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}