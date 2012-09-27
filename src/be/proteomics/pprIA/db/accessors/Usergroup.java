package com.compomics.ppr.db.accessors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 27-dec-2007
 * Time: 10:57:07
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the UsergroupTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Usergroup from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Usergroup.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Usergroup extends UsergroupTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Usergroup(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Usergroup from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: usergroupID <br />
     * Column 2: usergroupname <br />
     * Column 3: username < br />
     * Column 4: creationdate < br />
     * Column 5: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Usergroup(ResultSet aRS) throws SQLException {
        this.iUsergroupid = aRS.getLong(1);
        this.iUsergroupname = aRS.getString(2);
        this.iUsername = aRS.getString(3);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(4);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(5);
    }

    /**
     * This method retrieves all the Usergroup from the connection and stores them in a HashMap. <br />
     * The usergroupid is the key (Long type) and the Usergroup object is the value.
     *
     * @param aConn Connection to retrieve the Usergroup from.
     * @return  HashMap with the Usergroup, usergroupid is the key (Long type) and Usergroup objects are the values.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllUsergroupAsMap(Connection aConn) throws SQLException {
        HashMap lUsergroup = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Usergroup");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Usergroup temp = new Usergroup(rs);
            lUsergroup.put(new Long(temp.getUsergroupid()),temp);
        }
        rs.close();
        prep.close();

        return lUsergroup;
    }

    /**
     * This method retrieves all Usergroup from the connection and stores them in a Usergroup[].
     *
     * @param aConn Connection to retrieve the Usergroup from.
     * @return  Usergroup[] with the Usergroup.
     * @throws SQLException when the retrieve failed.
     */
    public static Usergroup[] getAllUsergroups(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Usergroup");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Usergroup(rs));
        }
        Usergroup[] result = new Usergroup[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves a Usergroup from the connection where the usergroups name is given and stores them in a Usergroup.
     *
     * @param aConn Connection to retrieve the Usergroup from.
     * @param aUsergroupname the Usergroupname.
     * @return  Usergroup[] with the Usergroup.
     * @throws SQLException when the retrieve failed.
     */
    public static Usergroup getUsergroupByName(Connection aConn,  String aUsergroupname) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Usergroup where usergroupname = ?");
        prep.setString(1, aUsergroupname);
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        Usergroup result = null;
        while(rs.next()) {
            result = new Usergroup(rs);
        }

        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves a Usergroup from the connection where the usergroups id is given and stores them in a Usergroup.
     *
     * @param aConn Connection to retrieve the Usergroup from.
     * @param aUsergroupid the Usergroupid.
     * @return  Usergroup[] with the Usergroup.
     * @throws SQLException when the retrieve failed.
     */
    public static Usergroup getUsergroupById(Connection aConn,  Long aUsergroupid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Usergroup where usergroupid = ?");
        prep.setLong(1, aUsergroupid);
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        Usergroup result = null;
        while(rs.next()) {
            result = new Usergroup(rs);
        }

        rs.close();
        prep.close();
        return result;
    }
    /**
     * This method returns a String representation of the Usergroup, ie.: the name.
     *
     * @return  String  with the name of the Usergroup.
     */
    public String toString() {
        return this.iUsergroupname;
    }
}




