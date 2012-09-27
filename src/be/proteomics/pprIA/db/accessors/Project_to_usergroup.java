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
 * Date: 27-dec-2007
 * Time: 10:51:54
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the Project_to_usergroupTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Project_to_usergroup from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Project_to_usergroup.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Project_to_usergroup extends Project_to_usergroupTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Project_to_usergroup(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Project_to_usergroup from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: project_to_usergroupID <br />
     * Column 2: l_projectid <br />
     * Column 3: l_usergroupid < br />
     * Column 4: username < br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Project_to_usergroup(ResultSet aRS) throws SQLException {
        this.iProject_to_usergroupid = aRS.getLong(1);
        this.iL_projectid = aRS.getLong(2);
        this.iL_usergroupid = aRS.getLong(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);
    }

    /**
     * This method retrieves all the Project_to_usergroup from the connection and stores them in a HashMap. <br />
     * The project_to_usergroupid is the key (Long type) and the Project_to_usergroup object is the value.
     *
     * @param aConn Connection to retrieve the Project_to_usergroup from.
     * @return  HashMap with the Project_to_usergroup, project_to_usergroupid is the key (Long type) and Project_to_usergroup objects are the values.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllProject_to_usergroupAsMap(Connection aConn) throws SQLException {
        HashMap lProject_to_usergroup = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Project_to_usergroup");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Project_to_usergroup temp = new Project_to_usergroup(rs);
            lProject_to_usergroup.put(new Long(temp.getProject_to_usergroupid()),temp);
        }
        rs.close();
        prep.close();

        return lProject_to_usergroup;
    }

    /**
     * This method retrieves all Project_to_usergroup from the connection and stores them in a Project_to_usergroup[].
     *
     * @param aConn Connection to retrieve the Project_to_usergroup from.
     * @return  Project_to_usergroup[] with the Project_to_usergroup.
     * @throws SQLException when the retrieve failed.
     */
    public static Project_to_usergroup[] getAllProject_to_usergroups(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Project_to_usergroup");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Project_to_usergroup(rs));
        }
        Project_to_usergroup[] result = new Project_to_usergroup[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }
    /**
     * This method retrieves all Project_to_usergroup with a specific l_projecdid from the connection and stores them in a Project_to_usergroup[].
     *
     * @param aConn Connection to retrieve the Project_to_usergroup from.
     * @param aProjectid Long.
     * @return  Project_to_usergroup[] with the Project_to_usergroup.
     * @throws SQLException when the retrieve failed.
     */
    public static Project_to_usergroup[] getAllProject_to_usergroupsWithProjectid(Connection aConn, Long aProjectid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Project_to_usergroup where l_projectid = ?");
        prep.setLong(1, aProjectid);
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Project_to_usergroup(rs));
        }
        Project_to_usergroup[] result = new Project_to_usergroup[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method deletes all project_to_usergroup with a specifiec l_projectid from the connection.
     *
     * @param aConn Connection to delete the Usergroup_to_groupusers from.
     * @param aProjectid Long the projectid.
     * @throws SQLException when the retrieve failed.
     */
    public static void deleteProject_to_usergroupByProjectid(Connection aConn, Long aProjectid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("delete from Project_to_usergroup where l_projectid = ?");
        prep.setLong(1, aProjectid);
        prep.executeUpdate();
    }

    /**
     * This method returns a String representation of the Project_to_usergroup, ie.: the id.
     *
     * @return  String  with the id of the Project_to_usergroup.
     */
    public String toString() {
        return String.valueOf(this.iProject_to_usergroupid);
    }
}



