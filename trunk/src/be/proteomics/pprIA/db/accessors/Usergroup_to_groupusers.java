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
 * Time: 11:01:45
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the Usergroup_to_groupusersTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Usergroup_to_groupusers from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Usergroup_to_groupusers.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Usergroup_to_groupusers extends Usergroup_to_groupusersTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Usergroup_to_groupusers(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Usergroup_to_groupusers from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: usergroup_to_groupusersID <br />
     * Column 2: l_usergroupid <br />
     * Column 3: l_groupusersid < br />
     * Column 4: username < br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Usergroup_to_groupusers(ResultSet aRS) throws SQLException {
        this.iUsergroup_to_groupusersid = aRS.getLong(1);
        this.iL_usergroupid = aRS.getLong(2);
        this.iL_groupusersid = aRS.getLong(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);
    }

    /**
     * This method retrieves all the Usergroup_to_groupusers from the connection and stores them in a HashMap. <br />
     * The usergroup_to_groupusersid is the key (Long type) and the Usergroup_to_groupusers object is the value.
     *
     * @param aConn Connection to retrieve the Usergroup_to_groupusers from.
     * @return  HashMap with the Usergroup_to_groupusers, usergroup_to_groupusersid is the key (Long type) and Usergroup_to_groupusers objects are the values.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllUsergroup_to_groupusersAsMap(Connection aConn) throws SQLException {
        HashMap lUsergroup_to_groupusers = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Usergroup_to_groupusers");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Usergroup_to_groupusers temp = new Usergroup_to_groupusers(rs);
            lUsergroup_to_groupusers.put(new Long(temp.getUsergroup_to_groupusersid()),temp);
        }
        rs.close();
        prep.close();

        return lUsergroup_to_groupusers;
    }

    /**
     * This method retrieves all Usergroup_to_groupusers from the connection and stores them in a Usergroup_to_groupusers[].
     *
     * @param aConn Connection to retrieve the Usergroup_to_groupusers from.
     * @return  Usergroup_to_groupusers[] with the Usergroup_to_groupusers.
     * @throws SQLException when the retrieve failed.
     */
    public static Usergroup_to_groupusers[] getAllUsergroup_to_groupusers(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Usergroup_to_groupusers");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Usergroup_to_groupusers(rs));
        }
        Usergroup_to_groupusers[] result = new Usergroup_to_groupusers[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves all Usergroup_to_groupusers with a specifiec l_usergroupid from the connection and stores them in a Usergroup_to_groupusers[].
     *
     * @param aConn Connection to retrieve the Usergroup_to_groupusers from.
     * @param aUsergroupid Long the usergroupid.
     * @return  Usergroup_to_groupusers[] with the Usergroup_to_groupusers.
     * @throws SQLException when the retrieve failed.
     */
    public static Usergroup_to_groupusers[] getAllUsergroup_to_groupusersByUsergroupid(Connection aConn, Long aUsergroupid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Usergroup_to_groupusers where l_usergroupid = ?");
        prep.setLong(1, aUsergroupid);
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Usergroup_to_groupusers(rs));
        }
        Usergroup_to_groupusers[] result = new Usergroup_to_groupusers[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method deletes all Usergroup_to_groupusers with a specifiec l_usergroupid from the connection.
     *
     * @param aConn Connection to delete the Usergroup_to_groupusers from.
     * @param aUsergroupid Long the usergroupid.
     * @return  Usergroup_to_groupusers[] with the Usergroup_to_groupusers.
     * @throws SQLException when the retrieve failed.
     */
    public static void deleteUsergroup_to_groupusersByUsergroupid(Connection aConn, Long aUsergroupid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("delete from Usergroup_to_groupusers where l_usergroupid = ?");
        prep.setLong(1, aUsergroupid);
        prep.executeUpdate();
    }

    /**
     * This method returns a String representation of the Usergroup_to_groupusers, ie.: the id.
     *
     * @return  String  with the id of the Usergroup_to_groupusers.
     */
    public String toString() {
        return String.valueOf(this.iUsergroup_to_groupusersid);
    }
}




