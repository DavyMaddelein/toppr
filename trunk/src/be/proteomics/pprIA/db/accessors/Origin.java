package com.compomics.ppr.db.accessors;

import java.util.HashMap;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 1-okt-2007
 * Time: 14:10:23
 * To change this template use File | Settings | File Templates.
 */
/**
 * This class provides the following enhancements over the OriginTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Origin from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the user.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Origin extends OriginTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Origin(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor sets the only 'settable' field: Scientific_name.
     *
     * @param aName String with the full name for the Origin.
     */
    public Origin(String aName) {
        super.setOrigin(aName);
    }

    /**
     * This constructor reads a Origin from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: OriginID <br />
     * Column 2: name <br />
     * Column 3: username< br />
     * Column 4: creationdate < br />
     * Column 5: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Origin(ResultSet aRS) throws SQLException {
        this.iOriginid = aRS.getLong(1);
        this.iOrigin = aRS.getString(2);
        this.iUsername = aRS.getString(3);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(4);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(5);
    }

    /**
     * This method retrieves all Origins from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the users from.
     * @return HashMap with all the Origins.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllOriginsAsMap(Connection aConn) throws SQLException {
        HashMap lOrigins = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select Originid, origin, username, creationdate, modificationdate from Origin");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Origin temp = new Origin(rs);
            lOrigins.put(new Long(temp.getOriginid()),temp);
        }
        rs.close();
        prep.close();

        return lOrigins;
    }

    /**
     * This method retrieves all Origins from the connection and stores them in a Origin[].
     *
     * @param aConn Connection to retrieve the Origins from.
     * @return  Origin[] with the Origins.
     * @throws SQLException when the retrieve failed.
     */
    public static Origin[] getAllOrigins(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select Originid, origin, username, creationdate, modificationdate from Origin");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Origin(rs));
        }
        Origin[] result = new Origin[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }


    /**
     * This method returns a String representation of the Origin, ie.: the Scientific_name.
     *
     * @return  String  with the name of the Origin.
     */
    public String toString() {
        return this.iOrigin;
    }
}
