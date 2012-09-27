package be.proteomics.pprIA.update;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 02-Jun-2010
 * Time: 10:36:54
 * To change this template use File | Settings | File Templates.
 */
public class DailySearchSettings {

    private Connection iConn;
    private String iLocation;
    private PrintWriter iOutFile;


    public DailySearchSettings(Connection aConn, PrintWriter lOutFile, String lLocation) throws IOException, SQLException {
        this.iOutFile = lOutFile;
        this.iConn = aConn;
        this.iLocation = lLocation;

        iOutFile.write("###########################\n");
        iOutFile.write("Finding search settings for \"Guest\" account\n");
        String userName = "guest";

        //get cell sources
        PreparedStatement prep1 = iConn.prepareStatement("select c.name from cell_source as c where c.cell_sourceid in (select r.l_cell_sourceid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))");
        ResultSet rs1 = prep1.executeQuery();
        Vector vect1 = new Vector();
        String lCell_source = "";
        while (rs1.next()) {
            vect1.add(rs1.getObject(1));
            lCell_source = lCell_source + rs1.getObject(1) + ",";
        }
        if (lCell_source.indexOf(",") > 0) {
            lCell_source = lCell_source.substring(0, lCell_source.lastIndexOf(","));
        }
        String[] cell_sources = new String[vect1.size()];
        vect1.toArray(cell_sources);
        prep1.close();
        rs1.close();

        //get taxonomys
        PreparedStatement prep2 = iConn.prepareStatement("select t.species from taxonomy as t where t.taxonomyid in (select c.l_taxonomy from cell_source as c where c.cell_sourceid in (select r.l_cell_sourceid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))))");
        ResultSet rs2 = prep2.executeQuery();
        Vector vect2 = new Vector();
        String lSpecies = "";
        while (rs2.next()) {
            vect2.add(rs2.getObject(1));
            lSpecies = lSpecies + rs2.getObject(1) + ",";
        }
        if (lSpecies.indexOf(",") > 0) {
            lSpecies = lSpecies.substring(0, lSpecies.lastIndexOf(","));
        }
        String[] taxs = new String[vect2.size()];
        vect2.toArray(taxs);
        prep2.close();
        rs2.close();

        PreparedStatement prep22 = iConn.prepareStatement("select t.scientific_name from taxonomy as t where t.taxonomyid in (select c.l_taxonomy from cell_source as c where c.cell_sourceid in (select r.l_cell_sourceid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))))");
        ResultSet rs22 = prep22.executeQuery();
        Vector vect22 = new Vector();
        String lSpeciesScientific = "";
        while (rs22.next()) {
            vect22.add(rs22.getObject(1));
            lSpeciesScientific = lSpeciesScientific + rs22.getObject(1) + ",";
        }
        if (lSpecies.indexOf(",") > 0) {
            lSpeciesScientific = lSpeciesScientific.substring(0, lSpeciesScientific.lastIndexOf(","));
        }
        String[] taxsScientific = new String[vect22.size()];
        vect22.toArray(taxs);
        prep22.close();
        rs22.close();

        //get treatments
        PreparedStatement prep3 = iConn.prepareStatement("select t.scientific_name from treatment as t where t.treatmentid in (select i.l_treatmentid from peptide_treatment_and_inhibitor as i where i.l_identificationid in (select d.identificationid from peptide as d where d.l_projectid in (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "'))))))) order by t.scientific_name");
        ResultSet rs3 = prep3.executeQuery();
        Vector vect3 = new Vector();
        String lTreatment = "";
        while (rs3.next()) {
            vect3.add(rs3.getObject(1));
            lTreatment = lTreatment + rs3.getObject(1) + ",";
        }
        if (lTreatment.indexOf(",") > 0) {
            lTreatment = lTreatment.substring(0, lTreatment.lastIndexOf(","));
        }
        String[] treats = new String[vect3.size()];
        vect3.toArray(treats);
        prep3.close();
        rs3.close();

        //get inhibitors
        PreparedStatement prep4 = iConn.prepareStatement("select b.scientific_name from inhibitor as b where b.inhibitorid in (select i.l_inhibitorid from peptide_treatment_and_inhibitor as i where i.l_identificationid in (select d.identificationid from peptide as d where d.l_projectid in (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))))");
        //prep4 = iConn.prepareStatement("select scientific_name from inhibitor");
        ResultSet rs4 = prep4.executeQuery();
        Vector vect4 = new Vector();
        String lInhibitor = "";
        while (rs4.next()) {
            lInhibitor = lInhibitor + rs4.getObject(1) + ",";
            vect4.add(rs4.getObject(1));
        }
        if (lInhibitor.indexOf(",") > 0) {
            lInhibitor = lInhibitor.substring(0, lInhibitor.lastIndexOf(","));
        }
        String[] inhibs = new String[vect4.size()];
        vect4.toArray(inhibs);
        prep4.close();
        rs4.close();

        //get experiment types
        PreparedStatement prep5 = iConn.prepareStatement("select e.name from experiment as e where e.experimentid in (select r.l_experimentid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))");
        ResultSet rs5 = prep5.executeQuery();
        Vector vect5 = new Vector();
        String lExperimentType = "";
        while (rs5.next()) {
            vect5.add(rs5.getObject(1));
            lExperimentType = lExperimentType + rs5.getObject(1) + ",";
        }
        if (lExperimentType.indexOf(",") > 0) {
            lExperimentType = lExperimentType.substring(0, lExperimentType.lastIndexOf(","));
        }
        String[] exps = new String[vect5.size()];
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
        String[] projects = new String[vect6.size()];
        vect6.toArray(projects);
        prep6.close();
        rs6.close();


        //write these things to the search.properties file
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(iLocation + "\\" + "search.properties"))));
        bw.write("cell_source = " + lCell_source + "\n");
        bw.write("treatment = " + lTreatment + "\n");
        bw.write("speciesScientific = " + lSpeciesScientific + "\n");
        bw.write("inhibitor = " + lInhibitor + "\n");
        bw.write("experiment_type = " + lExperimentType + "\n");
        bw.write("species = " + lSpecies + "\n");
        bw.flush();
        bw.close();

        iOutFile.write("\tNew settings are:\n");
        iOutFile.write("\tcell_source = " + lCell_source + "\n");
        iOutFile.write("\ttreatment = " + lTreatment + "\n");
        iOutFile.write("\tspeciesScientific = " + lSpeciesScientific + "\n");
        iOutFile.write("\tinhibitor = " + lInhibitor + "\n");
        iOutFile.write("\texperiment_type = " + lExperimentType + "\n");
        iOutFile.write("\tspecies = " + lSpecies + "\n\n\n");

    }
}
