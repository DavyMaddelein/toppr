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
 * Date: 27-jun-2008
 * Time: 8:34:40
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the GoTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Go from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Go.</li>
 *   <li><B>getGoFromID()</bi>: returns a Go for a given goid.</li>
 *   <li><b>hashCode()</b>: returns a hashcode for the Go (which is just the Go's ID).</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Go extends com.compomics.ppr.db.accessors.GoTableAccessor {

    /**
     * Default constructor.
     */
    public Go() {
        super();
    }

    /**
     * Wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Go(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads the instrument from a resultset. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: go ID <br />
     * Column 2: l_proteinid <br />
     * Column 3: name <br />
     * Column 4: type <br />
     * Column 5: gotermid <br />
     * Column 6: username< br />
     * Column 7: creationdate < br />
     * Column 8: modificationdate < br />
     * @param   aRS ResultSet to read the data from.
     * @exception java.sql.SQLException    when reading the ResultSet failed.
     */
    public Go(ResultSet aRS) throws SQLException {
        this.iGoid = aRS.getLong(1);
        this.iL_proteinid = aRS.getLong(2);
        this.iName = aRS.getString(3);
        this.iType = aRS.getString(4);
        this.iGotermid  = aRS.getString(5);
        this.iUsername = aRS.getString(6);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(7);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(8);

    }

    /**
     * This methods reads all Gos from the Go table.
     *
     * @param aConn Connection to read the instruments from.
     * @return  Go[] with all the gos in the 'Go' table.
     * @throws SQLException when the retrieving of the instruments went wrong.
     */
    public static Go[] getAllGos(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Go order by goid ASC");
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        while(rs.next()) {
            v.add(new Go(rs));
        }
        rs.close();
        prep.close();
        Go[] lGos = new Go[v.size()];
        v.toArray(lGos);

        return lGos;
    }
    /**
     * This method retrieves an Go from the connection and stores them in a Go.
     *
     * @param aConn Connection to retrieve the Go from.
     * @param aGoid Long to search the Goid column .
     * @return identification that has a specific Goid.
     * @throws SQLException when the retrieve failed.
     */
     public static Go getGoFromID(Connection aConn, long aGoid) throws SQLException {

        Go temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from Go where Goid = ?");
        ps.setLong(1, aGoid);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Go(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            throw new SQLException("Select based on Goid '" + aGoid + "' resulted in " + counter + " results instead of 1!");
        }
        return temp;
    }

    /**
     * Returns a String representation for this Go.
     *
     * @return  String  with the String representation for this Go.
     */
    public String toString() {
        return this.iGoid + ". " + this.iName;
    }

    /**
     * Returns a hashcode for the Go. <br />
     * The hashcode is just the GoID, cast to int, which is
     * the PK on the table.
     *
     * @return  int with the hashcode
     */
    public int hashCode() {
        return (int)this.iGoid;
    }
}