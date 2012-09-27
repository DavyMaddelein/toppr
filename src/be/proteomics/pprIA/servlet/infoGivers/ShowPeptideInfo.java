package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.search.FoundPeptide;
import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.PerformedSearches;
import be.proteomics.pprIA.search.ProteinLocation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 8-mei-2008
 * Time: 14:14:04
 * To change this template use File | Settings | File Templates.
 */
public class ShowPeptideInfo extends HttpServlet {

    private String userName;
    private java.sql.Connection iConn;
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
            HttpSession session = req.getSession(true);
            userName = (String) session.getValue("userName");
            if (userName == null) {
                userName = "guest";
            }
            //get searches, session, proteins, protein number and site number
            PerformedSearches searches = (PerformedSearches) session.getValue("searches");
            String ses = req.getParameter("session");
            int sessionid = Integer.valueOf(ses);
            FoundProtein[] proteins = searches.getFoundProteins(sessionid);
            String pro = req.getParameter("protein");
            int proteinInt = Integer.valueOf(pro);
            FoundProtein proteinFound = proteins[proteinInt];
            String protein = proteinFound.getSpAccession();

            FoundPeptide[] result = getPeptideResults(protein);
            session.putValue("spectrum", result);
            String url = "/spectrumResult.jsp";
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


    public FoundPeptide[] getPeptideResults(String protein) {
        Vector result = new Vector();

        try {
            //find processing sites
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("select p.identificationid from peptide as p, peptide_location as l, protein as r where p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid and r.spaccession = ? and p.l_projectid in (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))) group by p.identificationid");
            prep.setString(1, protein);

            ResultSet rs = prep.executeQuery();
            Vector idsV = new Vector();
            String ids = "";
            while (rs.next()) {
                idsV.add(rs.getInt(1));
                ids = ids +  rs.getString(1)  + ",";
            }
            ids = ids.substring(0, ids.lastIndexOf(","));
            Integer[] idsFound = new Integer[idsV.size()];
            idsV.toArray(idsFound);
            rs.close();
            prep.close();



                PreparedStatement prepIds = null;
                prepIds = iConn.prepareStatement("select p.*, g.peptide_sequence, r.proteinid, r.name, l.start, l.end, l.location, group_concat(distinct t.scientific_name separator ' , ') as treatments, group_concat(distinct i.scientific_name separator ' , ') as inhibitors ,c.description as type, r.sequence, e.name as experiment, cs.name as cell_source, r.spaccession\n" +
                        "from peptide as p, protein as r, peptide_location as l, project as j, cofradic as c, peptide_treatment_and_inhibitor as a, treatment as t, inhibitor as i, cell_source as cs, experiment as e, peptide_group as g\n" +
                        "where p.identificationid in ("+ ids + ") and p.l_groupid = l.l_groupid and g.groupid = p.l_groupid and l.l_proteinid = r.proteinid and p.l_projectid = j.projectid and j.l_cell_sourceid = cs.cell_sourceid  and e.experimentid = j.l_experimentid and j.l_cofradicid = c.cofradicid and a.l_identificationid = p.identificationid and a.l_treatmentid = t.treatmentid and a.l_inhibitorid = i.inhibitorid\n" +
                        "and p.l_projectid in (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))\n" +
                        "group by l.start, r.spaccession, p.identificationid order by g.peptide_sequence, p.identificationid, r.spaccession, l.start");
                ResultSet rsIds = prepIds.executeQuery();
                while (rsIds.next()) {
                    Vector proteinLocation = new Vector();
                    FoundPeptide peptide = new FoundPeptide();
                    boolean newPeptide = true;
                    for(int i = 0; i<result.size(); i ++){
                        FoundPeptide peptideAlreadyFound = (FoundPeptide) result.get(i);
                        if(peptideAlreadyFound.getIdentificationid() == rsIds.getInt("identificationid")){
                            newPeptide = false;
                            peptide = peptideAlreadyFound;
                        }
                    }

                    if(newPeptide){
                        peptide = new FoundPeptide();
                        peptide.setIdentificationid(rsIds.getInt("identificationid"));
                        //peptide.setIIonCoverage(rsLoc.getString("ion_coverage"));
                        peptide.setModifiedSequence(rsIds.getString("modified_sequence"));
                        peptide.setScore(rsIds.getDouble("score"));
                        peptide.setThreshold(rsIds.getDouble("identitythreshold"));
                        peptide.setMascotVersion(rsIds.getString("mascot_version"));
                        peptide.setPrecursorMass(rsIds.getDouble("precursor"));
                        peptide.setCharge(rsIds.getInt("charge"));
                        peptide.setConfidence(rsIds.getDouble("confidence"));
                        peptide.setCellSource(rsIds.getString("cell_source"));
                        peptide.setExpType(rsIds.getString("experiment"));
                        peptide.setPeptideSequence(rsIds.getString("peptide_sequence"));
                    }
                    ProteinLocation site;

                    String spAccession = rsIds.getString("spaccession");
                    String sequence = rsIds.getString("sequence");
                    String proteinDescription = rsIds.getString("name");
                    String type = rsIds.getString("type");
                    String[] treatments = (rsIds.getString("treatments")).split(" , ");
                    String[] inhibitors = (rsIds.getString("inhibitors")).split(" , ");

                    int pos = rsIds.getInt("location") - 1;
                        String proSite = "";
                        String proSite2 = "";
                        int start = 0;
                        int end = 0;
                        if ((pos - 5) < 0) {
                            proSite = sequence.substring(0, pos);
                            proSite2 = sequence.substring(pos, pos + 5);
                            int length = proSite.length();
                            int diffInt = 5 - length;
                            for (int l = 0; l < diffInt; l++) {
                                proSite = " " + proSite;
                            }
                            start = 0;
                            end = pos + 5;
                        } else {
                            if ((pos + 5) > sequence.length()) {
                                proSite = sequence.substring(pos - 5, pos);
                                proSite2 = sequence.substring(pos);
                                int length = proSite.length();
                                int diffInt = 5 - length;
                                for (int l = 0; l < diffInt; l++) {
                                    proSite2 = proSite2 + " ";
                                }
                                start = pos - 5;
                                end = sequence.length();
                            } else {
                                proSite = sequence.substring(pos - 5, pos);
                                proSite2 = sequence.substring(pos, pos + 5);
                                start = pos - 5;
                                end = pos + 5;
                            }
                        }
                        String processingSite = start + "-" + proSite + "<img src =\"images/scissors.JPG\" align=\"middle\">" + proSite2 + "-" + end;
                        site = new ProteinLocation(spAccession, proteinDescription, pos, start, end, proSite, proSite2);

                    site.setTreatment(treatments);
                    site.setInhibitor(inhibitors);
                    proteinLocation.add(site);

                    peptide.setProteinLocation(proteinLocation);
                    if(newPeptide){
                        result.add(peptide);
                    }

                
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        FoundPeptide[] peptides = new FoundPeptide[result.size()];
        result.toArray(peptides);
        return peptides;
    }

    private synchronized java.sql.Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}