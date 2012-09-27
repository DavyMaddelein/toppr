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
 * Time: 10:45:36
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the GroupusersTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Groupusers from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Groupusers.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Groupusers extends GroupusersTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Groupusers(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Groupusers from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: groupusersID <br />
     * Column 2: groupusername <br />
     * Column 3: username< br />
     * Column 4: creationdate < br />
     * Column 5: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Groupusers(ResultSet aRS) throws SQLException {
        this.iGroupusersid = aRS.getLong(1);
        this.iGroupusersname = aRS.getString(2);
        this.iUsername = aRS.getString(3);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(4);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(5);
    }

    /**
     * This method retrieves all the Groupusers from the connection and stores them in a HashMap. <br />
     * The groupusersid is the key (Long type) and the Groupusers object is the value.
     *
     * @param aConn Connection to retrieve the Groupusers from.
     * @return  HashMap with the Groupusers, groupusersid is the key (Long type) and Groupusers objects are the values.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllGroupusersAsMap(Connection aConn) throws SQLException {
        HashMap lGroupusers = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Groupusers");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Groupusers temp = new Groupusers(rs);
            lGroupusers.put(new Long(temp.getGroupusersid()),temp);
        }
        rs.close();
        prep.close();

        return lGroupusers;
    }

    /**
     * This method retrieves all Groupusers from the connection and stores them in a Groupusers[].
     *
     * @param aConn Connection to retrieve the Groupusers from.
     * @return  Groupusers[] with the Groupusers.
     * @throws SQLException when the retrieve failed.
     */
    public static Groupusers[] getAllGroupusers(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Groupusers");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Groupusers(rs));
        }
        Groupusers[] result = new Groupusers[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves a Groupusers  for a specific key from the connection and stores them in a Groupusers.
     *
     * @param aConn Connection to retrieve the Groupusers from.
     * @param aGroupuserid Long the key.
     * @return  Groupusers[] with the Groupusers.
     * @throws SQLException when the retrieve failed.
     */
    public static Groupusers getGroupusersByKey(Connection aConn, Long aGroupuserid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Groupusers where groupusersid = ?");
        prep.setLong(1, aGroupuserid);
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        Groupusers result = null;
        while(rs.next()) {
            result = new Groupusers(rs);
        }
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method returns a String representation of the Groupusers, ie.: the name.
     *
     * @return  String  with the name of the Groupusers.
     */
    public String toString() {
        return this.iGroupusersname;
    }
}


