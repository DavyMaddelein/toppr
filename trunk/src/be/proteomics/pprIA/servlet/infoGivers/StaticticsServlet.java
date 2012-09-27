package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.InformationBox;
import be.proteomics.pprIA.general.Statistics;

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
 * Date: 2-jul-2008
 * Time: 11:36:29
 * To change this template use File | Settings | File Templates.
 */
public class StaticticsServlet  extends HttpServlet {
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

            
            String query = "select r.proteinid\n" +
                    "from peptide as p, peptide_location as l, protein as r\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid group by r.proteinid";
            PreparedStatement prep = null;
            prep = iConn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            int proteins = 0;
            while(rs.next()){
                proteins = proteins + 1;
            }

            query = "select r.proteinid\n" +
                    "from peptide as p, peptide_group as g, peptide_location as l, protein as r\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid\n" +
                    "and p.l_groupid = g.groupid and g.l_taxonomy = 9606\n" +
                    "group by r.proteinid";
            PreparedStatement prep1 = null;
            prep1 = iConn.prepareStatement(query);
            ResultSet rs1 = prep1.executeQuery();
            int proteinsHum = 0;
            while(rs1.next()){
                proteinsHum = proteinsHum + 1;
            }

            query = "select r.proteinid\n" +
                    "from peptide as p, peptide_group as g, peptide_location as l, protein as r\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid\n" +
                    "and p.l_groupid = g.groupid and g.l_taxonomy = 10090\n" +
                    "group by r.proteinid";
            PreparedStatement prep2 = null;
            prep2 = iConn.prepareStatement(query);
            ResultSet rs2 = prep2.executeQuery();
            int proteinsMouse = 0;
            while(rs2.next()){
                proteinsMouse = proteinsMouse + 1;
            }

            query = "select l.*\n" +
                    "from  peptide_location as l\n" +
                    "where l.l_groupid in (select b.l_groupid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "group by l.peptide_locationid";
            PreparedStatement prep3 = null;
            prep3 = iConn.prepareStatement(query);
            ResultSet rs3 = prep3.executeQuery();
            int sites = 0;
            while(rs3.next()){
                sites = sites + 1;
            }

            query = "select l.*\n" +
                    "from  peptide_location as l, peptide_group as g\n" +
                    "where l.l_groupid in (select b.l_groupid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and g.groupid = l.l_groupid and g.l_taxonomy = 10090\n" +
                    "group by l.peptide_locationid";
            PreparedStatement prep4 = null;
            prep4 = iConn.prepareStatement(query);
            ResultSet rs4 = prep4.executeQuery();
            int sitesM = 0;
            while(rs4.next()){
                sitesM = sitesM + 1;
            }

            query = "select l.*\n" +
                    "from  peptide_location as l, peptide_group as g\n" +
                    "where l.l_groupid in (select b.l_groupid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and g.groupid = l.l_groupid and g.l_taxonomy = 9606\n" +
                    "group by l.peptide_locationid";
            PreparedStatement prep5 = null;
            prep5 = iConn.prepareStatement(query);
            ResultSet rs5 = prep5.executeQuery();
            int sitesH = 0;
            while(rs5.next()){
                sitesH = sitesH + 1;
            }

            query = "select r.proteinid, count(*)\n" +
                    "from peptide_location as l, protein as r\n" +
                    "where l.l_groupid in (select b.l_groupid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and l.l_proteinid = r.proteinid\n" +
                    "group by r.proteinid order by count(*)";
            PreparedStatement prep6 = null;
            prep6 = iConn.prepareStatement(query);
            ResultSet rs6 = prep6.executeQuery();
            Vector sitesProtein = new Vector();
            while(rs6.next()){
                sitesProtein.add(rs6.getInt(2));
            }

            query = "select r.proteinid, count(distinct a.l_treatmentid)as count\n" +
                    "from peptide_location as l, protein as r, peptide as p, peptide_treatment_and_inhibitor as a\n" +
                    "where l.l_groupid in (select b.l_groupid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and l.l_proteinid = r.proteinid\n" +
                    "and p.l_groupid = l.l_groupid\n" +
                    "and p.identificationid = a.l_identificationid\n" +
                    "group by r.proteinid order by count";
            PreparedStatement prep7 = null;
            prep7 = iConn.prepareStatement(query);
            ResultSet rs7 = prep7.executeQuery();
            Vector treatmentProtein = new Vector();
            while(rs7.next()){
                treatmentProtein.add(rs7.getInt(2));
            }

            query = "select p.identificationid\n" +
                    "from peptide as p\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "group by p.identificationid";
            PreparedStatement prep8 = null;
            prep8 = iConn.prepareStatement(query);
            ResultSet rs8 = prep8.executeQuery();
            int peptides = 0;
            while(rs8.next()){
                peptides = peptides + 1;
            }

            query = "select g.*\n" +
                    "from peptide as p, peptide_location as l, protein as r, go as g\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid\n" +
                    "and g.l_proteinid = r.proteinid group by goid";
            PreparedStatement prep9 = null;
            prep9 = iConn.prepareStatement(query);
            ResultSet rs9 = prep9.executeQuery();
            int go = 0;
            while(rs9.next()){
                go = go + 1;
            }

            query = "select d.*\n" +
                    "from peptide as p, peptide_location as l, protein as r, domain as d\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid \n" +
                    "and d.l_proteinid = r.proteinid group by d.domainid";
            PreparedStatement prep10 = null;
            prep10 = iConn.prepareStatement(query);
            ResultSet rs10 = prep10.executeQuery();
            int domain = 0;
            while(rs10.next()){
                domain = domain + 1;
            }

            query = "select d.*\n" +
                    "from peptide as p, peptide_location as l, protein as r, domain as d\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid\n" +
                    "and d.l_proteinid = r.proteinid and d.type = 'Pfam' group by d.domainid";
            PreparedStatement prep11 = null;
            prep11 = iConn.prepareStatement(query);
            ResultSet rs11 = prep11.executeQuery();
            int domainP = 0;
            while(rs11.next()){
                domainP = domainP + 1;
            }


            query = "select d.*\n" +
                    "from peptide as p, peptide_location as l, protein as r, domain as d\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid\n" +
                    "and d.l_proteinid = r.proteinid and d.type = 'Smart' group by d.domainid" ;
            PreparedStatement prep12 = null;
            prep12 = iConn.prepareStatement(query);
            ResultSet rs12 = prep12.executeQuery();
            int domainS = 0;
            while(rs12.next()){
                domainS = domainS + 1;
            }

            query = "select o.*\n" +
                    "from peptide as p, peptide_location as l, protein as r, orthologue as o\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid\n" +
                    "and o.l_proteinid = r.proteinid group by o.orthologueid";
            PreparedStatement prep13 = null;
            prep13 = iConn.prepareStatement(query);
            ResultSet rs13 = prep13.executeQuery();
            int orthologue = 0;
            while(rs13.next()){
                orthologue = orthologue + 1;
            }

            query = "select c.* from peptide as p, peptide_location as l, protein as r, pdb as c\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" +userName +  "')))))\n" +
                    "and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid and c.l_proteinid = r.proteinid group by c.pdbaccession";
            PreparedStatement prep14 = null;
            prep14 = iConn.prepareStatement(query);
            ResultSet rs14 = prep14.executeQuery();
            int pdb = 0;
            while(rs14.next()){
                pdb = pdb + 1;
            }
            rs.close();
            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
            rs5.close();
            rs6.close();
            rs7.close();
            rs8.close();
            rs9.close();
            rs10.close();
            rs11.close();
            rs12.close();
            rs13.close();
            rs14.close();
            prep.close();
            prep1.close();
            prep2.close();
            prep3.close();
            prep4.close();
            prep5.close();
            prep6.close();
            prep7.close();
            prep8.close();
            prep9.close();
            prep10.close();
            prep11.close();
            prep12.close();
            prep13.close();
            prep14.close();

            int sitesProteinLength = (Integer) sitesProtein.get(sitesProtein.size() - 1);
            int[] sitesProt = new int[sitesProteinLength];
            for(int s = 0; s<sitesProt.length; s ++){
                sitesProt[s] = 0;
            }
            for(int s = 0; s<sitesProtein.size(); s ++){
                int count = (Integer) sitesProtein.get(s);
                count = count - 1;
                sitesProt[count] = sitesProt[count] + 1;
            }

            int treatProteinLength = (Integer) treatmentProtein.get(treatmentProtein.size() - 1);
            int[] treatProt = new int[treatProteinLength];
            for(int s = 0; s<treatProt.length; s ++){
                treatProt[s] = 0;
            }
            for(int s = 0; s<treatmentProtein.size(); s ++){
                int count = (Integer) treatmentProtein.get(s);
                count = count - 1;
                treatProt[count] = treatProt[count] + 1;
            }

            Statistics stat = new Statistics(proteins, proteinsHum, proteinsMouse, sites, sitesH, sitesM, treatProt,sitesProt, go, pdb, domain, domainP, domainS, orthologue, peptides);



            session.putValue("stat", stat);
            String url = "/statistics.jsp";
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


    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}