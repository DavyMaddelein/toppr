package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.JmolLoad;
import be.proteomics.pprIA.general.protein_info.PdbBlock;
import be.proteomics.pprIA.general.protein_info.PdbParameter;
import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.PerformedSearches;

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
 * Date: 8-mei-2008
 * Time: 11:19:34
 * To change this template use File | Settings | File Templates.
 */
public class Show3dServlet extends HttpServlet {
    private String protein;
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
        try{
            iConn = this.getConnection();
            //get session
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
            String lPositionString = req.getParameter("position");
            int lPrimePosition;
            if(lPositionString == null){
                lPrimePosition = 0;
            } else {
                lPrimePosition = Integer.valueOf(lPositionString);
            }
            String pro = req.getParameter("protein");
            int proteinInt = Integer.valueOf(pro);
            FoundProtein proteinFound = proteins[proteinInt];
            protein = proteinFound.getSpAccession();
            Vector sites = proteinFound.getProcessingSites();
            Vector positions = new Vector();
            for (int i = 0; i < sites.size(); i++) {
                FoundProcessingSite site = (FoundProcessingSite) sites.get(i);
                positions.add(site.getPosition());
            }
            PreparedStatement prep = iConn.prepareStatement("select p.* from pdb as p, protein as r where r.spaccession = ? and r.proteinid = p.l_proteinid");
            prep.setString(1, protein);
            ResultSet rs = prep.executeQuery();
            Vector pdbVector = new Vector();
            while(rs.next()){
                pdbVector.add(new PdbParameter(rs.getString("pdbaccession"),rs.getString("title"),rs.getString("experiment_type"), rs.getString("resolution")));
            }
            prep.close();
            rs.close();
            for(int p = 0; p<pdbVector.size(); p ++){
                PdbParameter pdb = (PdbParameter) pdbVector.get(p);
                PreparedStatement prep1 = iConn.prepareStatement("select b.* from pdb as p, pdb_block as b, protein as r where r.spaccession = ? and r.proteinid = p.l_proteinid and p.pdbaccession = ? and p.pdbid = b.l_pdbid and b.l_proteinid = r.proteinid");
                prep1.setString(1, protein);
                prep1.setString(2, pdb.getPdbaccession());
                ResultSet rs1 = prep1.executeQuery();
                while(rs1.next()){
                    pdb.addBlock(new PdbBlock(rs1.getString("block"), rs1.getInt("start_protein"), rs1.getInt("end_protein"), rs1.getInt("start_block"), rs1.getInt("end_block")));
                }
            }

            //check if the processing sites are in the pdb file/block
            for(int p = 0; p<pdbVector.size(); p ++){
                PdbParameter pdb = (PdbParameter) pdbVector.get(p);
                for(int b = 0; b<pdb.getBlocks().length; b ++){
                    PdbBlock block = pdb.getBlocks()[b];
                    Vector selection = new Vector();
                    for(int l = 0; l<positions.size(); l ++){
                        int lSitePosition = (Integer) positions.get(l);
                        lSitePosition = lSitePosition + lPrimePosition;
                        if(block.getStart_protein() <=  lSitePosition && block.getEnd_protein() >= lSitePosition ){
                            selection.add(lSitePosition - block.getDifference());
                        }
                    }
                    Integer[] selectedSites = new Integer[selection.size()];
                    selection.toArray(selectedSites);
                    block.setSelectedPositions(selectedSites);
                }

            }

            String jmolLoad = getJmolLoad(pdbVector);
            String jmolMenu = makePdbHtmlTable(pdbVector);
            JmolLoad result = new JmolLoad(jmolLoad, jmolMenu, lPrimePosition, proteinInt, sessionid);

            session.putValue("jmol", result);
            String url = "/jmol.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (iConn != null) iConn.close();
            } catch (SQLException e) {
            }
        }
    }


    public String makePdbHtmlTable(Vector pdbs) {
        String html = "";

        html = html + "<table>";
        for (int i = 0; i < pdbs.size(); i++) {
            PdbParameter pdb = (PdbParameter) pdbs.get(i);
            html = html + "<tr><td>";
            html = html + "<script language=\"JavaScript\" type=\"text/javascript\"> jmolRadio('";

            html = html + "load pdbFiles/" + pdb.getPdbaccession() + ".pdb;wireframe off; background white; spacefill off; backbone off; cartoon structure; color cartoon structure;";
            boolean selection = false;
            String selectedSites = "";
            for(int b = 0; b<pdb.getBlocks().length; b ++){
                PdbBlock block = pdb.getBlocks()[b];
                if(block.getSelection()){

                    Integer[] selected = block.getSelectedPositions();
                    for(int s = 0; s<selected.length; s++){
                        if(s == 0){
                            if(!selection){
                                selection = true;
                                selectedSites = selectedSites + " " + selected[s] +":"+ block.getBlock();
                            } else {
                                selectedSites = selectedSites + "," + selected[s] +":"+ block.getBlock();
                            }
                        } else {
                            selectedSites = selectedSites + "," + selected[s] +":"+ block.getBlock();    
                        }
                    }
                }
            }
            if(selection){
                html = html + "select " + selectedSites + "; wireframe 0.5; spacefill 0.9; color green;";
            }



            if(i == 0){
                html = html +  "', \"\", true);</script> ";
            } else {
                html = html +  "', \"\", false);</script> ";
            }
            html = html + pdb.getPdbaccession() + ": " + pdb.getTitle().toLowerCase() + " (" + pdb.getExperiment_type().toLowerCase() + ")";
            html = html + "</td></tr>";
        }
        html = html + "</table>";
        return html;

    }

    public String getJmolLoad(Vector pdbs) {
        PdbParameter pdb  = (PdbParameter) pdbs.get(0);
        String jmolLoad = "jmolInitialize(\"pdbFiles/\", false); jmolApplet(800, \"load pdbFiles/" + pdb.getPdbaccession() + ".pdb; wireframe off; background white; spacefill off; backbone off; cartoon structure; color cartoon structure;";

        boolean selection = false;
            String selectedSites = "";
            for(int b = 0; b<pdb.getBlocks().length; b ++){
                PdbBlock block = pdb.getBlocks()[b];
                if(block.getSelection()){
                    Integer[] selected = block.getSelectedPositions();
                    for(int s = 0; s<selected.length; s++){
                        if(s == 0){
                            if(!selection){
                                selection = true;
                                selectedSites = selectedSites + " " + selected[s] +":"+ block.getBlock();
                            } else {
                                selectedSites = selectedSites + "," + selected[s] +":"+ block.getBlock();
                            }
                        } else {
                            selectedSites = selectedSites + "," + selected[s] +":"+ block.getBlock();
                        }
                    }
                }
            }
            if(selection){
                jmolLoad = jmolLoad + "select " + selectedSites + "; wireframe 0.5; spacefill 0.9; color green;";
            }

        jmolLoad = jmolLoad + "\");";
        return jmolLoad;
    }


    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}