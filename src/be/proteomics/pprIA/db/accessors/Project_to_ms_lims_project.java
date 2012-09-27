package com.compomics.ppr.db.accessors;

import java.util.HashMap;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 15-feb-2008
 * Time: 13:57:22
 * To change this template use File | Settings | File Templates.
 */
/**
 * This class provides the following enhancements over the Project_to_ms_lims_projectTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Project_to_ms_lims_project from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Project_to_ms_lims_project.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Project_to_ms_lims_project extends Project_to_ms_lims_projectTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Project_to_ms_lims_project(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Project_to_ms_lims_project from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: Project_to_ms_lims_projectID <br />
     * Column 2: l_projectid <br />
     * Column 3: l_ms_lims_projectid < br />
     * Column 4: username < br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Project_to_ms_lims_project(ResultSet aRS) throws SQLException {
        this.iProject_to_ms_lims_projectid = aRS.getLong(1);
        this.iL_projectid = aRS.getLong(2);
        this.iL_ms_limsprojectid = aRS.getLong(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);
    }

    /**
     * This method retrieves all the Project_to_ms_lims_project from the connection and stores them in a HashMap. <br />
     * The Project_to_ms_lims_projectid is the key (Long type) and the Project_to_ms_lims_project object is the value.
     *
     * @param aConn Connection to retrieve the Project_to_ms_lims_project from.
     * @return  HashMap with the Project_to_ms_lims_project, Project_to_ms_lims_projectid is the key (Long type) and Project_to_ms_lims_project objects are the values.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllProject_to_ms_lims_projectAsMap(Connection aConn) throws SQLException {
        HashMap lProject_to_ms_lims_project = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Project_to_ms_lims_project");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Project_to_ms_lims_project temp = new Project_to_ms_lims_project(rs);
            lProject_to_ms_lims_project.put(new Long(temp.getProject_to_ms_lims_projectid()),temp);
        }
        rs.close();
        prep.close();

        return lProject_to_ms_lims_project;
    }

    /**
     * This method retrieves all Project_to_ms_lims_project from the connection and stores them in a Project_to_ms_lims_project[].
     *
     * @param aConn Connection to retrieve the Project_to_ms_lims_project from.
     * @return  Project_to_ms_lims_project[] with the Project_to_ms_lims_project.
     * @throws SQLException when the retrieve failed.
     */
    public static Project_to_ms_lims_project[] getAllProject_to_ms_lims_projects(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Project_to_ms_lims_project");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Project_to_ms_lims_project(rs));
        }
        Project_to_ms_lims_project[] result = new Project_to_ms_lims_project[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }
     /**
     * This method retrieves all Project_to_usergroup with a specific l_projecdid from the connection and stores them in a Project_to_ms_lims_project[].
     *
     * @param aConn Connection to retrieve the Project_to_ms_lims_project from.
     * @param aProjectid Long.
     * @return  Project_to_ms_lims_project[] with the Project_to_ms_lims_project.
     * @throws SQLException when the retrieve failed.
     */
    public static Project_to_ms_lims_project[] getAllProject_to_usergroupsWithProjectid(Connection aConn, Long aProjectid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Project_to_ms_lims_project where l_projectid = ?");
        prep.setLong(1, aProjectid);
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Project_to_ms_lims_project(rs));
        }
        Project_to_ms_lims_project[] result = new Project_to_ms_lims_project[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }


    /**
     * This method retrieves all Project_to_ms_lims_project with a specific l_projecdid from the connection and stores them in a Project_to_ms_lims_project[].
     *
     * @param aConn Connection to retrieve the Project_to_ms_lims_project from.
     * @param aProjectid Long.
     * @return  Project_to_ms_lims_project[] with the Project_to_ms_lims_project.
     * @throws SQLException when the retrieve failed.
     */
    public static Project_to_ms_lims_project[] getAllProject_to_ms_lims_projectsWithProjectid(Connection aConn, Long aProjectid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Project_to_ms_lims_project where l_projectid = ?");
        prep.setLong(1, aProjectid);
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Project_to_ms_lims_project(rs));
        }
        Project_to_ms_lims_project[] result = new Project_to_ms_lims_project[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method deletes all Project_to_ms_lims_project with a specifiec l_projectid from the connection.
     *
     * @param aConn Connection to delete the Usergroup_to_groupusers from.
     * @param aProjectid Long the projectid.
     * @throws SQLException when the retrieve failed.
     */
    public static void deleteProject_to_ms_lims_projectByProjectid(Connection aConn, Long aProjectid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("delete from Project_to_ms_lims_project where l_projectid = ?");
        prep.setLong(1, aProjectid);
        prep.executeUpdate();
    }

    /**
     * This method returns a String representation of the Project_to_ms_lims_project, ie.: the id.
     *
     * @return  String  with the id of the Project_to_ms_lims_project.
     */
    public String toString() {
        return String.valueOf(this.iProject_to_ms_lims_projectid);
    }
}