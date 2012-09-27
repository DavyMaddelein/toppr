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
 * Time: 10:45:47
 * To change this template use File | Settings | File Templates.
 */
public class ShowTreatmentsServlet extends HttpServlet {
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
            String query = "select * from treatment as t where t.treatmentid in ( select b.l_treatmentid from peptide_treatment_and_inhibitor as b where b.l_identificationid in (select d.identificationid from peptide as d where d.l_projectid in (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))))";
            PreparedStatement prep = null;
            prep = iConn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            InformationBox[] result = this.getTreatmentResults(rs);
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


    private InformationBox[] getTreatmentResults(ResultSet aRs) {
        Vector results = new Vector();
        ResultSet iRs = aRs;
        try {
            while (iRs.next()) {

                String scientificName = iRs.getString("scientific_name");
                String commonName = iRs.getString("common_name");
                String protease = iRs.getString("protease");
                String recombinant = iRs.getString("recombinant");
                //get taxonomy
                String taxonomy = "";
                int tax = iRs.getInt("l_taxonomy");
                PreparedStatement prepTax = null;
                prepTax = iConn.prepareStatement("select * from taxonomy where taxonomyid = ?");
                prepTax.setInt(1, tax);
                ResultSet rsTax = prepTax.executeQuery();
                while (rsTax.next()) {
                    taxonomy = rsTax.getString("scientific_name");
                }
                rsTax.close();
                prepTax.close();
                String meropsid = iRs.getString("meropsid");
                String pubmedid = iRs.getString("pubmedid");
                String source = iRs.getString("source");


                InformationBox info = new InformationBox(scientificName, scientificName);
                info.addElement("Scientific name", scientificName, null);
                if (commonName.length() > 1) {
                    info.addElement("Common name", commonName, null);
                }
                if (meropsid.length() > 1) {
                    info.addElement("Merops id", meropsid, "http://merops.sanger.ac.uk/cgi-bin/pepsum?id=" + meropsid + "");
                }
                String[] pubmedids = pubmedid.split(",");
                if (pubmedids.length >= 1) {
                    for(int i = 0; i< pubmedids.length; i ++){
                        info.addElement("Pubmedid " + (i+1), pubmedids[i], "http://www.ncbi.nlm.nih.gov/pubmed/" + pubmedids[i] + "");
                    }
                }
                if (protease.length() > 1) {
                    info.addElement("Protease", protease, null);
                }
                if (recombinant.length() > 1) {
                    info.addElement("Recombinant", recombinant, null);
                }
                if (source.length() > 1) {
                    info.addElement("Source", source, null);
                }
                if (taxonomy.length() > 1) {
                    info.addElement("taxonomy", taxonomy, "/toppr/ShowTaxonomys");
                }

                results.add(info);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        InformationBox[] treatments = new InformationBox[results.size()];
        results.toArray(treatments);
        return treatments;
    }

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}