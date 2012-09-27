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
 * Time: 14:15:59
 * To change this template use File | Settings | File Templates.
 */
public class ShowCellSourcesServlet extends HttpServlet {
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
            String query = "select * from cell_source as c where c.cell_sourceid in (select r.l_cell_sourceid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))";
            PreparedStatement prep = null;
            prep = iConn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            InformationBox[] result = this.getCellsourceResutls(rs);
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


    private InformationBox[] getCellsourceResutls(ResultSet aRs) {
        Vector results = new Vector();
        ResultSet iRs = aRs;
        try {
            while (iRs.next()) {
                String name = iRs.getString("name");

                //get origin
                String origin = "";
                int ori = iRs.getInt("l_origin");
                PreparedStatement prepOri = null;
                prepOri = iConn.prepareStatement("select * from origin where originid = ?");
                prepOri.setInt(1, ori);
                ResultSet rsOri = prepOri.executeQuery();
                while (rsOri.next()) {
                    origin = rsOri.getString("origin");
                }
                rsOri.close();
                prepOri.close();

                //get origin
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


                String organ = iRs.getString("organ");
                String celltype = iRs.getString("celltype");
                String subcellularLocation = iRs.getString("subcellular_location");
                String developmentalStage = iRs.getString("developmental_stage");
                String sex = iRs.getString("sex");
                if(sex.equalsIgnoreCase("null")|| sex ==  null){
                    sex = "unknown";
                }
                String diseaseState = iRs.getString("disease_state");
                String permanentTransfection = iRs.getString("permanent_transfection");
                String source = iRs.getString("source");
                String pretreatment = iRs.getString("pre_treatment");

                InformationBox info = new InformationBox(name, name);
                info.addElement("Name", name, null);

                if (celltype.length() > 1) {
                    if (celltype.indexOf("CL") != -1) {
                        String sub = celltype.substring(celltype.indexOf("CL"));
                        String sub2 = celltype.substring(0, celltype.indexOf(" - "));
                        info.addElement("Cell type", celltype, "http://www.ebi.ac.uk/ontology-lookup/?termId=" + sub);
                    } else {

                        info.addElement("Cell type", celltype, null);
                    }
                }
                if (organ.length() > 1) {
                    if (organ.indexOf("BTO") != -1) {
                        String sub = organ.substring(organ.indexOf("BTO"));
                        String sub2 = organ.substring(0, organ.indexOf(" - "));
                        info.addElement("Organ", organ, "http://www.ebi.ac.uk/ontology-lookup/?termId=" + sub);

                    } else {
                        info.addElement("Organ", organ, null);

                    }
                }

                if (taxonomy.length() > 1) {
                    info.addElement("Taxonomy", taxonomy, "/toppr/ShowTaxonomys");

                }

                if (permanentTransfection.length() > 1) {
                    info.addElement("Permanent transfection", permanentTransfection, null);

                }
                if (pretreatment.length() > 1) {
                    info.addElement("Pre-treatment", pretreatment, null);

                }
                if (sex.length() > 1) {
                    info.addElement("Sex", sex, null);

                }
                if (source.length() > 1) {
                    info.addElement("Source", source, null);

                }
                if (subcellularLocation.length() > 1) {
                    if (subcellularLocation.indexOf("GO") != -1) {
                        String sub = subcellularLocation.substring(subcellularLocation.indexOf("GO"));
                        String sub2 = subcellularLocation.substring(0, subcellularLocation.indexOf(" - "));
                        info.addElement("Subcellular location", subcellularLocation, "http://www.ebi.ac.uk/ontology-lookup/?termId=" + sub);
                    } else {
                        info.addElement("Subcellular location", subcellularLocation, null);
                    }
                }

                if (developmentalStage.length() > 1) {
                    if (developmentalStage.indexOf("EMAP") != -1) {
                        String sub = developmentalStage.substring(developmentalStage.indexOf("EMAP"));
                        String sub2 = developmentalStage.substring(0, developmentalStage.indexOf(" - "));
                        info.addElement("Developmental stage", developmentalStage, "http://www.ebi.ac.uk/ontology-lookup/?termId=" + sub);

                    } else {
                        if (developmentalStage.indexOf("EHDA") != -1) {
                            String sub = developmentalStage.substring(developmentalStage.indexOf("EHDA"));
                            String sub2 = developmentalStage.substring(0, developmentalStage.indexOf(" - "));
                            info.addElement("Developmental stage", subcellularLocation, "http://www.ebi.ac.uk/ontology-lookup/?termId=" + sub);

                        } else {
                            info.addElement("Developmental stage", developmentalStage, null);

                        }
                    }
                }

                if (diseaseState.length() > 1) {
                    if (diseaseState.indexOf("DOID") != -1) {
                        String sub = diseaseState.substring(diseaseState.indexOf("DOID"));
                        String sub2 = diseaseState.substring(0, diseaseState.indexOf(" - "));
                        info.addElement("Disease state", diseaseState, "http://www.ebi.ac.uk/ontology-lookup/?termId=" + sub);
                    } else {
                        info.addElement("Disease state", diseaseState, null);
                    }
                }

                if (origin.length() > 1) {
                    info.addElement("Origin", origin, null);
                }
                results.add(info);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        InformationBox[] cellSources = new InformationBox[results.size()];
        results.toArray(cellSources);
        return cellSources;
    }

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}