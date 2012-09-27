/**
 * Created by IntelliJ IDEA.
 * user: Lennart
 * Date: 25-sept-2003
 * Time: 17:03:07
 */
package com.compomics.ppr.db.accessors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

/*
 * CVS information:
 *
 * $Revision: 1.2 $
 * $Date: 2007/11/12 08:56:24 $
 */

/**
 * This class provides the following enhancements over the InhibitorTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Inhibitor from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the user.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Inhibitor extends InhibitorTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Inhibitor(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor sets the only 'settable' field: Scientific_name.
     *
     * @param aName String with the full name for the Inhibitor.
     */
    public Inhibitor(String aName) {
        super.setScientific_name(aName);
    }

    /**
     * This constructor reads a Inhibitor from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: InhibitorID <br />
     * Column 2: scientific_name <br />
     * Column 3: common_name <br />
     * Column 4: meropsid <br />
     * Column 5: source <br />
     * Column 6: username< br />
     * Column 7: creationdate < br />
     * Column 8: modificationdate < br />
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Inhibitor(ResultSet aRS) throws SQLException {
        this.iInhibitorid = aRS.getLong(1);
        this.iScientific_name = aRS.getString(2);
        this.iCommon_name = aRS.getString(3);
        this.iMeropsid = aRS.getString(4);
        this.iSource = aRS.getString(5);
        this.iUsername = aRS.getString(6);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(7);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(8);
    }

    /**
     * This method retrieves all Inhibitors from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the users from.
     * @return HashMap with all the Inhibitorin.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllInhibitorsAsMap(Connection aConn) throws SQLException {
        HashMap lInhibitors = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select inhibitorid, scientific_name, common_name, meropsid, source, username, creationdate, modificationdate from inhibitor");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Inhibitor temp = new Inhibitor(rs);
            lInhibitors.put(new Long(temp.getInhibitorid()),temp);
        }
        rs.close();
        prep.close();

        return lInhibitors;
    }

    /**
     * This method retrieves all Inhibitors from the connection and stores them in a Inhibitor[].
     *
     * @param aConn Connection to retrieve the Inhibitors from.
     * @return  Inhibitor[] with the Inhibitors.
     * @throws SQLException when the retrieve failed.
     */
    public static Inhibitor[] getAllInhibitors(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select inhibitorid, scientific_name , common_name, meropsid, source, username, creationdate, modificationdate from inhibitor");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Inhibitor(rs));
        }
        Inhibitor[] result = new Inhibitor[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }


    /**
     * This method returns a String representation of the Inhibitor, ie.: the Scientific_name.
     *
     * @return  String  with the name of the Inhibitor.
     */

    public String toString() {
        return this.iInhibitorid + ". " + this.iScientific_name ;
    }
}
