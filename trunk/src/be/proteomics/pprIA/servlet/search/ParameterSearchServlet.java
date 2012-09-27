package be.proteomics.pprIA.servlet.search;

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
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 18-apr-2008
 * Time: 8:37:40
 * To change this template use File | Settings | File Templates.
 */
public class ParameterSearchServlet extends HttpServlet {
    private String userName;
    private int proteinCount = 0;
    private int siteCount = 0;
    private Vector uniquePeptide = new Vector();
    private Connection iConn;
    private DataSource datasource = null;
    private String[] searchedParameters;
    private String[] searchedTreatments;
    private boolean sameSiteSearch = false;
    private String andOr = "AND";
    private Vector usedTreatments = new Vector();
    private boolean motifSearch;
    private String pre = "";
    private String post = "";
    private String[] preArray;
    private String[] postArray;
    private int preLength = 0;
    private int postLength = 0;

    public void init() throws ServletException {
        try {
            //Create a datasource for pooled connections.
            datasource = (DataSource) getServletContext().getAttribute("DBPool");
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            usedTreatments = new Vector();
            //get connection from connection pool
            iConn = this.getConnection();
            //check same site search
            String sameSite = req.getParameter("sameSite");
            andOr = req.getParameter("andOr");
            if (sameSite.equalsIgnoreCase("yes")) {
                sameSiteSearch = true;
            }

            String[] treatmentA = req.getParameterValues("treatment");
            if(treatmentA == null){
                String url = "/emptySearch.html";
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(url);
                rd.forward(req, res);
                return;
            }
            // make a sql query based on the requested parameters
            String sqlQuery = makeSqlStatement(req);
            String sqlQueryProteins = makeSqlStatementProteinIds(req);
            //find the site parameters
            pre = "";
            post = "";
            preLength = 0;
            postLength = 0;
            uniquePeptide = new Vector();
            pre = req.getParameter("pre");
            post = req.getParameter("post");
            String searchSite = pre + " " + post;
            preLength = pre.length();
            for (int c = 0; c < pre.length(); c++) {
                if (pre.charAt(c) == '/') {
                    preLength = preLength - 4;
                }
            }
            postLength = post.length();
            for (int c = 0; c < post.length(); c++) {
                if (post.charAt(c) == '/') {
                    postLength = postLength - 4;
                }
            }
            // make regexp arrays
            preArray = new String[preLength];
            postArray = new String[postLength];
            int count = 0;
            for (int m = 0; m < pre.length(); m++) {
                if (pre.charAt(m) != '[') {
                    preArray[count] = String.valueOf(pre.charAt(m));
                } else {
                    preArray[count] = pre.substring(m, pre.indexOf(']', m) + 1);
                    m = m + 4;
                }
                count = count + 1;
            }
            count = 0;
            for (int m = 0; m < post.length(); m++) {
                if (post.charAt(m) != '[') {
                    postArray[count] = String.valueOf(post.charAt(m));
                } else {
                    postArray[count] = post.substring(m, post.indexOf(']', m) + 1);
                    m = m + 4;
                }
                count = count + 1;
            }
            if (preLength > 0 || postLength > 0) {
                motifSearch = true;
            }

            //if no parameters were requested no search will be performed
            if (sqlQuery.equalsIgnoreCase("")) {
                String url = "/emptySearch.html";
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(url);
                rd.forward(req, res);
                return;
            }

            //find the proteins for the given parameters
            PreparedStatement prep = null;
            prep = iConn.prepareStatement(sqlQuery);
            ResultSet rs = prep.executeQuery();
            PreparedStatement prep1 = null;
            prep1 = iConn.prepareStatement(sqlQueryProteins);
            ResultSet rs1 = prep1.executeQuery();
            FoundProtein[] result = this.findProteinResutls(rs, rs1);
            rs.close();
            prep.close();
            rs1.close();
            prep1.close();

            HttpSession session = req.getSession(true);
            PerformedSearches searches = (PerformedSearches) session.getValue("searches");
            if (searches == null) {
                searches = new PerformedSearches();
            }
            /*
            for(int i = 0; i < searchedTreatments.length; i++){
                for (int j = 0; j < result.length; j++) {
                        Vector sitesVector = result[j].getProcessingSites();
                        for(int k = 0; k<sitesVector.size() ; k ++){
                            FoundProcessingSite site = (FoundProcessingSite)sitesVector.get(k);
                            String[] treats = site.getTreatments();
                            for(int l = 0; l<treats.length ; l ++){
                                if(treats[l].equalsIgnoreCase(searchedTreatments[i])){

                                    if(site.getIsoforms() > 1){
                                        System.out.println("Iso" + " " + site.getIsoforms());
                                    } else {
                                        System.out.println("no"  + " " + site.getIsoforms());
                                    }


                                }
                            }
                        }
                }
            }
            */

            if (searchedTreatments.length == 0 || sameSiteSearch || motifSearch) {
                String[] treatment = new String[usedTreatments.size()];
                usedTreatments.toArray(treatment);
                searches.addSearch(result, searchedParameters, false, true, false, proteinCount, siteCount, treatment, uniquePeptide);
            } else {
                searches.addSearch(result, searchedParameters, false, true, false, proteinCount, siteCount, searchedTreatments, uniquePeptide);
            }
            session.putValue("searches", searches);
            session.putValue("session", -1);
            String url = "/searchResult.jsp";
            proteinCount = 0;
            siteCount = 0;
            uniquePeptide = new Vector();
            sameSiteSearch = false;
            motifSearch = false;
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
        finally {
            try {
                if (iConn != null) iConn.close();
            } catch (SQLException e) {
            }
        }
    }

    private String makeSqlStatement(HttpServletRequest req) {
        String query = "";
        Vector params = new Vector();

        if (sameSiteSearch) {
            params.add("Same site search:");
        }
        //Find the username
        HttpSession session = req.getSession(true);
        userName = (String) session.getValue("userName");
        if (userName == null) {
            userName = "guest";
        }

        //search paramters
        String[] treatmentA = req.getParameterValues("treatment");
        if (treatmentA == null) {
            treatmentA = new String[0];
        }
        if (andOr.equalsIgnoreCase(" AND NOT")) {
            Vector treats = new Vector();
            String positive = req.getParameter("positive");
            treats.add(positive);

            for (int t = 0; t < treatmentA.length; t++) {
                boolean found = false;
                for (int v = 0; v < treats.size(); v++) {
                    if (treatmentA[t].equalsIgnoreCase((String) treats.get(v))) {
                        found = true;
                    }
                }
                if (!found) {
                    treats.add(treatmentA[t]);
                }
            }
            treatmentA = new String[treats.size()];
            treats.toArray(treatmentA);
        }
        String[] taxonomyA = req.getParameterValues("taxonomy");
        String[] cell_sourceA = req.getParameterValues("cellsource");
        String[] experimentA = req.getParameterValues("experiment");
        String[] inhibitorA = req.getParameterValues("inhibitor");
        String[] projectsA = req.getParameterValues("project");
        String treatment = null;
        String taxonomy = null;
        String cell_source = null;
        String experiment = null;
        String inhibitor = null;
        if (treatmentA != null) {
            for (int i = 0; i < treatmentA.length; i++) {
                params.add(treatmentA[i]);
                treatment = treatment + "," + treatmentA[i];
            }
        }
        if (cell_sourceA != null) {
            for (int i = 0; i < cell_sourceA.length; i++) {
                params.add(cell_sourceA[i]);
                cell_source = cell_source + "," + cell_sourceA[i];
            }
        }
        if (taxonomyA != null) {
            for (int i = 0; i < taxonomyA.length; i++) {
                params.add(taxonomyA[i]);
                taxonomy = taxonomy + "," + taxonomyA[i];
            }
        }
        if (experimentA != null) {
            for (int i = 0; i < experimentA.length; i++) {
                params.add(experimentA[i]);
                experiment = experiment + "," + experimentA[i];
            }
        }
        if (inhibitorA != null) {
            for (int i = 0; i < inhibitorA.length; i++) {
                params.add(inhibitorA[i]);
                inhibitor = inhibitor + "," + inhibitorA[i];
            }
        }


        //make the query
        //first treatment
        query = "select z.identificationid, z.l_groupid from peptide as z where z.identificationid in (select i.identificationid from peptide as i where i.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_treatmentid in (select m.treatmentid from treatment as m where m.scientific_name = '" + treatmentA[0] + "' )))";
        //inhibitor
        if (inhibitorA != null && inhibitorA.length == 1) {
            query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[0] + "' )))";
        }
        if (inhibitorA != null && inhibitorA.length > 1) {
            query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[0] + "' )))";
            for (int i = 0; i < inhibitorA.length - 1; i++) {
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[i] + "' )))";
            }
            query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[inhibitorA.length-1] + "' ))))";
        }
        //taxonomy
        if (taxonomyA != null && taxonomyA.length == 1) {
            query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[0] + "')))";
        }
        if (taxonomyA != null && taxonomyA.length > 1) {
            query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[0] + "')))";
            for (int i = 0; i < taxonomyA.length - 1; i++) {
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[i] + "')))";
            }
            query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[taxonomyA.length - 1] + "'))))";
        }
        //exeriment type
        if (experimentA != null && experimentA.length == 1) {
            query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[0] + "')))";
         }
         if (experimentA != null && experimentA.length > 1) {
            query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[0] + "')))";
            for (int i = 0; i < experimentA.length - 1; i++) {
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[i] + "')))";
            }
            query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[experimentA.length - 1] + "')))";
        }
        //cell source
        if (cell_sourceA != null && cell_sourceA.length == 1) {
            query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[0] + "')))";
        }
        if (cell_sourceA != null && cell_sourceA.length > 1) {
            query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[0] + "')))";
            for (int i = 0; i < cell_sourceA.length - 1; i++) {
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[i] + "')))";
            }
            query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[cell_sourceA.length - 1] + "'))))";
        }
        query = query + ")";


        //middle treatments
        for (int z = 1; z < treatmentA.length - 1; z++) {
            query = query + "OR z.identificationid in (select i.identificationid from peptide as i where i.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_treatmentid in (select m.treatmentid from treatment as m where m.scientific_name = '" + treatmentA[z] + "' )))";
            //inhibitor
            if (inhibitorA != null && inhibitorA.length == 1) {
                query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[0] + "' )))";
            }
            if (inhibitorA != null && inhibitorA.length > 1) {
                query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[0] + "' )))";
                for (int i = 0; i < inhibitorA.length - 1; i++) {
                    query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[i] + "' )))";
                }
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[inhibitorA.length-1] + "' ))))";
            }
            //taxonomy
            if (taxonomyA != null && taxonomyA.length == 1) {
                query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[0] + "')))";
            }
            if (taxonomyA != null && taxonomyA.length > 1) {
                query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[0] + "')))";
                for (int i = 0; i < taxonomyA.length - 1; i++) {
                    query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[i] + "')))";
                }
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[taxonomyA.length - 1] + "'))))";
            }
            //exeriment type
            if (experimentA != null && experimentA.length == 1) {
                query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[0] + "')))";
             }
             if (experimentA != null && experimentA.length > 1) {
                query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[0] + "')))";
                for (int i = 0; i < experimentA.length - 1; i++) {
                    query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[i] + "')))";
                }
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[experimentA.length - 1] + "')))";
            }
            //cell source
            if (cell_sourceA != null && cell_sourceA.length == 1) {
                query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[0] + "')))";
            }
            if (cell_sourceA != null && cell_sourceA.length > 1) {
                query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[0] + "')))";
                for (int i = 0; i < cell_sourceA.length - 1; i++) {
                    query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[i] + "')))";
                }
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[cell_sourceA.length - 1] + "'))))";
            }
            query = query + ")";
        }
        //last treatment
        if(treatmentA.length > 1){
            query = query + "OR z.identificationid in (select i.identificationid from peptide as i where i.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_treatmentid in (select m.treatmentid from treatment as m where m.scientific_name = '" + treatmentA[treatmentA.length - 1] + "' )))";
            //inhibitor
            if (inhibitorA != null && inhibitorA.length == 1) {
                query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[0] + "' )))";
            }
            if (inhibitorA != null && inhibitorA.length > 1) {
                query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[0] + "' )))";
                for (int i = 0; i < inhibitorA.length - 1; i++) {
                    query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[i] + "' )))";
                }
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[inhibitorA.length-1] + "' ))))";
            }
            //taxonomy
            if (taxonomyA != null && taxonomyA.length == 1) {
                query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[0] + "')))";
            }
            if (taxonomyA != null && taxonomyA.length > 1) {
                query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[0] + "')))";
                for (int i = 0; i < taxonomyA.length - 1; i++) {
                    query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[i] + "')))";
                }
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[taxonomyA.length - 1] + "'))))";
            }
            //exeriment type
            if (experimentA != null && experimentA.length == 1) {
                query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[0] + "')))";
             }
             if (experimentA != null && experimentA.length > 1) {
                query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[0] + "')))";
                for (int i = 0; i < experimentA.length - 1; i++) {
                    query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[i] + "')))";
                }
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[experimentA.length - 1] + "')))";
            }
            //cell source
            if (cell_sourceA != null && cell_sourceA.length == 1) {
                query = query + andOr + " i.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[0] + "')))";
            }
            if (cell_sourceA != null && cell_sourceA.length > 1) {
                query = query + andOr + " i.identificationid in (select q.identificationid from peptide as q where q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[0] + "')))";
                for (int i = 0; i < cell_sourceA.length - 1; i++) {
                    query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[i] + "')))";
                }
                query = query + "OR q.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[cell_sourceA.length - 1] + "'))))";
            }
            query = query + ")";
        }

        query = query + "AND z.identificationid in (select d.identificationid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))) group by z.identificationid";

        //put values to know what treatments were searched (if no treatment was selected make it empty
        if (treatmentA == null) {
            treatmentA = new String[1];
            treatmentA[0] = "";
        }
        searchedTreatments = treatmentA;
        if (sameSiteSearch) {
            searchedParameters = new String[params.size() + params.size() - 2];
        } else {
            searchedParameters = new String[params.size() + params.size() - 1];
        }

        int count = 0;
        for (int p = 0; p < params.size(); p++) {
            if (sameSiteSearch) {
                if (p == 0) {
                    searchedParameters[count] = (String) params.get(p);
                    count = count + 1;
                } else {
                    searchedParameters[count] = (String) params.get(p);
                    if (!(params.size() == 1 || p == params.size() - 1)) {
                        searchedParameters[count + 1] = andOr;
                        count = count + 2;
                    }
                }

            } else {
                searchedParameters[count] = (String) params.get(p);
                if (!(params.size() == 1 || p == params.size() - 1)) {
                    searchedParameters[count + 1] = andOr;
                    count = count + 2;
                }
            }
        }

        return query;
    }

    private String makeSqlStatementProteinIds(HttpServletRequest req) {
        String query = "";
        //Find the username
        HttpSession session = req.getSession(true);
        userName = (String) session.getValue("userName");
        if (userName == null) {
            userName = "guest";
        }

        //search paramters
        String[] treatmentA = req.getParameterValues("treatment");
        if (treatmentA == null) {
            treatmentA = new String[0];
        }
        if (andOr.equalsIgnoreCase(" AND NOT")) {
            Vector treats = new Vector();
            String positive = req.getParameter("positive");
            treats.add(positive);

            for (int t = 0; t < treatmentA.length; t++) {
                boolean found = false;
                for (int v = 0; v < treats.size(); v++) {
                    if (treatmentA[t].equalsIgnoreCase((String) treats.get(v))) {
                        found = true;
                    }
                }
                if (!found) {
                    treats.add(treatmentA[t]);
                }
            }
            treatmentA = new String[treats.size()];
            treats.toArray(treatmentA);
        }
        String[] taxonomyA = req.getParameterValues("taxonomy");
        String[] cell_sourceA = req.getParameterValues("cellsource");
        String[] experimentA = req.getParameterValues("experiment");
        String[] inhibitorA = req.getParameterValues("inhibitor");
        String treatment = null;
        String taxonomy = null;
        String cell_source = null;
        String experiment = null;
        String inhibitor = null;
        if (treatmentA != null) {
            for (int i = 0; i < treatmentA.length; i++) {

                treatment = treatment + "," + treatmentA[i];
            }
        }
        if (cell_sourceA != null) {
            for (int i = 0; i < cell_sourceA.length; i++) {

                cell_source = cell_source + "," + cell_sourceA[i];
            }
        }
        if (taxonomyA != null) {
            for (int i = 0; i < taxonomyA.length; i++) {

                taxonomy = taxonomy + "," + taxonomyA[i];
            }
        }
        if (experimentA != null) {
            for (int i = 0; i < experimentA.length; i++) {

                experiment = experiment + "," + experimentA[i];
            }
        }
        if (inhibitorA != null) {
            for (int i = 0; i < inhibitorA.length; i++) {

                inhibitor = inhibitor + "," + inhibitorA[i];
            }
        }


        //first treatment
        query = "select z.proteinid from protein as z where z.proteinid in (select p.proteinid from protein as p where p.proteinid IN (select l.l_proteinid from peptide_location as l where l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_treatmentid in (select m.treatmentid from treatment as m where m.scientific_name = '" + treatmentA[0] + "' ))";
        if (inhibitorA != null && inhibitorA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[0] + "' )))";
            }
            if (inhibitorA != null && inhibitorA.length > 1) {
                for (int i = 0; i < inhibitorA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[i] + "')))";
                }
            }
            if (taxonomyA != null && taxonomyA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[0] + "'))))";
            }
            if (taxonomyA != null && taxonomyA.length > 1) {
                for (int i = 0; i < taxonomyA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[i] + "'))))";
                }
            }
            if (experimentA != null && experimentA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[0] + "'))))";
            }
            if (experimentA != null && experimentA.length > 1) {
                for (int i = 0; i < experimentA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[i] + "'))))";
                }

            }
            if (cell_sourceA != null && cell_sourceA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[0] + "'))))";
            }
            if (cell_sourceA != null && cell_sourceA.length > 1) {
                for (int i = 0; i < cell_sourceA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[i] + "'))))";
                }
            }
            query = query + ")))";
        //the middle treatments
        for(int z  = 1; z<treatmentA.length - 1 ; z++){
            query = query + andOr + " z.proteinid in (select p.proteinid from protein as p where p.proteinid IN (select l.l_proteinid from peptide_location as l where l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_treatmentid in (select m.treatmentid from treatment as m where m.scientific_name = '" + treatmentA[z] + "' ))";
            if (inhibitorA != null && inhibitorA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[0] + "' )))";
            }
            if (inhibitorA != null && inhibitorA.length > 1) {
                for (int i = 0; i < inhibitorA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[i] + "')))";
                }
            }
            if (taxonomyA != null && taxonomyA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[0] + "'))))";
            }
            if (taxonomyA != null && taxonomyA.length > 1) {
                for (int i = 0; i < taxonomyA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[i] + "'))))";
                }
            }
            if (experimentA != null && experimentA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[0] + "'))))";
            }
            if (experimentA != null && experimentA.length > 1) {
                for (int i = 0; i < experimentA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[i] + "'))))";
                }

            }
            if (cell_sourceA != null && cell_sourceA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[0] + "'))))";
            }
            if (cell_sourceA != null && cell_sourceA.length > 1) {
                for (int i = 0; i < cell_sourceA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[i] + "'))))";
                }
            }
            query = query + ")))";
        }
        if(treatmentA.length > 1){
            query = query + andOr + " z.proteinid in (select p.proteinid from protein as p where p.proteinid IN (select l.l_proteinid from peptide_location as l where l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_treatmentid in (select m.treatmentid from treatment as m where m.scientific_name = '" + treatmentA[treatmentA.length - 1] + "' )))";
            if (inhibitorA != null && inhibitorA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[0] + "' )))";
            }
            if (inhibitorA != null && inhibitorA.length > 1) {
                for (int i = 0; i < inhibitorA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select b.l_identificationid from peptide_treatment_and_inhibitor as b where b.l_inhibitorid in (select i.inhibitorid from inhibitor as i where i.scientific_name = '" + inhibitorA[i] + "')))";
                }
            }
            if (taxonomyA != null && taxonomyA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[0] + "'))))";
            }
            if (taxonomyA != null && taxonomyA.length > 1) {
                for (int i = 0; i < taxonomyA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c , taxonomy as t where c.l_taxonomy = t.taxonomyid  and t.species = '" + taxonomyA[i] + "'))))";
                }
            }
            if (experimentA != null && experimentA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[0] + "'))))";
            }
            if (experimentA != null && experimentA.length > 1) {
                for (int i = 0; i < experimentA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_experimentid IN (select e.experimentid from experiment as e  where e.name = '" + experimentA[i] + "'))))";
                }

            }
            if (cell_sourceA != null && cell_sourceA.length == 1) {
                query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[0] + "'))))";
            }
            if (cell_sourceA != null && cell_sourceA.length > 1) {
                for (int i = 0; i < cell_sourceA.length; i++) {
                    query = query + andOr + " l.l_groupid in (select d.l_groupid from peptide as d where d.identificationid in (select x.identificationid from peptide as x where x.l_projectid IN (select r.projectid from project as r where r.l_cell_sourceid IN (select c.cell_sourceid from cell_source as c where c.name = '" + cell_sourceA[i] + "'))))";
                }
            }
            query = query + "))";
        }


        query = query + "AND z.proteinid IN (select l_proteinid from peptide_location as l where l.l_groupid in (select d.l_groupid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))))";

        return query;
    }

    private synchronized Connection getConnection() throws SQLException {
        //Connection lConn = datasource.getConnection();
        //lConn.
        return datasource.getConnection();
    }

    private FoundProtein[] findProteinResutls(ResultSet aRs, ResultSet aaRs) {
        String groupIds = "";
        String proteinsIds = "";
        HashMap idsCount = new HashMap(3);
        ResultSet ids = aRs;
        ResultSet proteins = aaRs;
        Vector results = new Vector();

        try {
            while (ids.next()) {
                groupIds = groupIds + ids.getString(1) + ",";
            }
            if (groupIds.length() != 0) {
                groupIds = groupIds.substring(0, groupIds.lastIndexOf(","));
                PreparedStatement prepIds = iConn.prepareStatement("select l.l_groupid, count(l.l_groupid) from peptide_location as l where l.l_groupid in (select p.l_groupid from peptide as p where p.identificationid in (" + groupIds + ")) group by l.l_groupid");                ResultSet groups = prepIds.executeQuery();
                while(groups.next()){
                    idsCount.put(groups.getInt(1), groups.getInt(2));
                }
            }else {
                //no ids found
                return new FoundProtein[0];
            }
            while (proteins.next()) {
                proteinsIds = proteinsIds + proteins.getString(1) + ",";
            }
            if (proteinsIds.length() != 0) {
                proteinsIds = proteinsIds.substring(0, proteinsIds.lastIndexOf(","));
            }
            if (proteinsIds.equalsIgnoreCase("")) {
                proteinsIds = "0";
            }
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("select r.proteinid, r.spaccession, r.sequence, r.entry_name, r.name, l.start, l.end, l.l_groupid ,group_concat(distinct t.scientific_name separator ' , ') as treatments ,c.description as type, g.peptide_sequence, l.location as loc\n" +
                    "from peptide as p, peptide_location as l, protein as r, project as j, cofradic as c, peptide_treatment_and_inhibitor as a, treatment as t, peptide_group as g\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.identificationid in ( " + groupIds + "))\n" +
                    "and p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))\n" +  
                    "and p.l_projectid = j.projectid and j.l_cofradicid = c.cofradicid and p.l_groupid = l.l_groupid and l.l_groupid = g.groupid and l.l_proteinid = r.proteinid and r.proteinid in (" + proteinsIds + ") and a.l_identificationid = p.identificationid and a.l_treatmentid = t.treatmentid group by loc, r.spaccession order by r.spaccession, loc");

            ResultSet iRs = prep.executeQuery();

            long lastProteinId = Long.MIN_VALUE;
            Vector usedTreatmentsInProtein = new Vector();
            while (iRs.next()) {
                FoundProtein result;

                long id = iRs.getLong("proteinid");
                String accession = iRs.getString("spaccession");
                String seqProt = iRs.getString("sequence");
                String entry = iRs.getString("entry_name");
                String description = iRs.getString("name");
                int loc = iRs.getInt("loc");
                loc = loc - 1;
                int groupId = iRs.getInt("l_groupid");
                String treatmentsString = iRs.getString("treatments");
                String type = iRs.getString("type");
                String[] treatments = treatmentsString.split(" , ");
                String peptide = iRs.getString("peptide_sequence");

                boolean use = true;
                if (sameSiteSearch) {
                    for (int b = 0; b < searchedTreatments.length; b++) {
                        boolean found = false;
                        for (int a = 0; a < treatments.length; a++) {
                            if (searchedTreatments[b].equalsIgnoreCase(treatments[a])) {
                                found = true;
                            }
                        }
                        if (!found) {
                            use = false;
                        }
                    }

                }

                if (motifSearch) {
                    int pos = loc;
                    try {
                        String preMatch = seqProt.substring(pos - preLength, pos);
                        String postMatch = seqProt.substring(pos, pos + postLength);
                        for (int m = 0; m < preLength; m++) {
                            if (preArray[m].equalsIgnoreCase("*")) {
                                // no problemo
                            } else {
                                if (preArray[m].startsWith("[")) {
                                    char char1 = preArray[m].charAt(1);
                                    char char2 = preArray[m].charAt(3);

                                    if (preMatch.charAt(m) == char1 || preMatch.charAt(m) == char2) {
                                        // no problemo
                                    } else {
                                        use = false;
                                        m = preLength;
                                    }
                                } else {
                                    if (preArray[m].equalsIgnoreCase(String.valueOf(preMatch.charAt(m)))) {
                                        // no problemo
                                    } else {
                                        use = false;
                                        m = preLength;
                                    }
                                }
                            }
                        }
                        for (int m = 0; m < postLength; m++) {
                            if (postArray[m].equalsIgnoreCase("*")) {
                                // no problemo
                            } else {
                                if (postArray[m].startsWith("[")) {
                                    char char1 = postArray[m].charAt(1);
                                    char char2 = postArray[m].charAt(3);
                                    if (postMatch.charAt(m) == char1 || postMatch.charAt(m) == char2) {
                                        // no problemo
                                    } else {
                                        use = false;
                                        m = postLength;
                                    }
                                } else {
                                    if (postArray[m].equalsIgnoreCase(String.valueOf(postMatch.charAt(m)))) {
                                        // no problemo
                                    } else {
                                        use = false;
                                        m = postLength;
                                    }
                                }
                            }
                        }

                    } catch (StringIndexOutOfBoundsException s) {
                        use = false;
                    }
                }

                if (use) {

                    //add peptide_groupid to the unique peptide vector
                    boolean foundGroupid = false;
                    for (int q = 0; q < uniquePeptide.size(); q++) {
                        if (groupId == (Integer) uniquePeptide.get(q)) {
                            foundGroupid = true;
                        }
                    }
                    if (!foundGroupid) {
                        uniquePeptide.add(groupId);
                    }


                    boolean newProtein = false;
                    //create protein result
                    if (lastProteinId == id) {
                        result = (FoundProtein) results.get(results.size() - 1);
                    } else {
                        result = new FoundProtein(seqProt, accession, entry, description);
                        newProtein = true;
                        usedTreatmentsInProtein = new Vector();
                        proteinCount = proteinCount + 1;
                    }


                    int pos = loc;
                    String proSite = "";
                    String proSite2 = "";
                    int startSite = 0;
                    int endSite = 0;
                    if ((pos - 15) < 0) {
                        proSite = seqProt.substring(0, pos);
                        proSite2 = seqProt.substring(pos, pos + 15);
                        int length = proSite.length();
                        int diffInt = 15 - length;
                        for (int l = 0; l < diffInt; l++) {
                            proSite = " " + proSite;
                        }
                        startSite = 0;
                        endSite = pos + 15;
                    } else {
                        if ((pos + 15) > seqProt.length()) {
                            proSite = seqProt.substring(pos - 15, pos);
                            proSite2 = seqProt.substring(pos);
                            int length = proSite2.length();
                            int diffInt = 15 - length;
                            for (int l = 0; l < diffInt; l++) {
                                proSite2 = proSite2 + " ";
                            }
                            startSite = pos - 15;
                            endSite = seqProt.length();
                        } else {
                            proSite = seqProt.substring(pos - 15, pos);
                            proSite2 = seqProt.substring(pos, pos + 15);
                            startSite = pos - 15;
                            endSite = pos + 15;
                        }
                    }
                    result.addProcessingSite(new FoundProcessingSite(treatments, pos, proSite, proSite2, startSite, endSite, type, (Integer) idsCount.get(groupId), true, peptide));
                    lastProteinId = id;
                    siteCount = siteCount + 1;

                    //add the treatments to the found treatment vector
                    for (int p = 0; p < treatments.length; p++) {
                        boolean found = false;
                        for (int q = 0; q < usedTreatmentsInProtein.size(); q++) {
                            if (treatments[p].equalsIgnoreCase((String) usedTreatmentsInProtein.get(q))) {
                                found = true;
                            }
                        }
                        if (!found) {
                            usedTreatmentsInProtein.add(treatments[p]);
                        }
                    }
                    for (int p = 0; p < treatments.length; p++) {
                        boolean found = false;
                        for (int q = 0; q < usedTreatments.size(); q++) {
                            if (treatments[p].equalsIgnoreCase((String) usedTreatments.get(q))) {
                                found = true;
                            }
                        }
                        if (!found) {
                            usedTreatments.add(treatments[p]);
                        }
                    }
                    result.setUsedTreatments(usedTreatmentsInProtein);
                    if (newProtein) {
                        results.add(result);
                    }

                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (iConn != null) iConn.close();
            } catch (SQLException e) {
            }
        }

        FoundProtein[] proteinsFound = new FoundProtein[results.size()];
        results.toArray(proteinsFound);
        return proteinsFound;
    }

}