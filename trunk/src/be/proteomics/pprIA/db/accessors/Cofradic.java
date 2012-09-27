package com.compomics.ppr.db.accessors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 4-okt-2007
 * Time: 9:38:50
 * To change this template use File | Settings | File Templates.
 */



/**
 * This class implements a wrapper around the CofradicTableAccessor.
 *
 * @author Niklaas Colaert
 */
public class Cofradic extends CofradicTableAccessor {

    /**
     * Default constructor.
     */
    public Cofradic() {
    }

    /**
     * This constructor reads the PKL file from a resultset. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet).
     * The columns should be in this order: <br />
     * Column 1: cofradicid <br />
     * Column 2: type_2 <br />
     * Column 3: description <br />
     * Column 4: username< br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Cofradic(ResultSet aRS) throws SQLException {
        this.iCofradicid = aRS.getLong(1);
        this.iType_2 = aRS.getString(2);
        this.iDescription = aRS.getString(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);
    }

    /**
     * This method finds all Cofradic types from the DB and stores them in a HashMap,
     *
     * @param aConn Connection from which to read the Cofradic types.
     * @return  HashMap with the Cofradics.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllCofradicsAsMap(Connection aConn) throws SQLException {
        HashMap lCofradic = new HashMap();

        PreparedStatement prep = aConn.prepareStatement("select cofradicid, type, description from Cofradic");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Cofradic temp = new Cofradic(rs);
            lCofradic.put(new Long(temp.getCofradicid()),temp);
        }
        rs.close();
        prep.close();

        return lCofradic;
    }

    /**
     * This method finds all Cofradic types from the DB and stores them in a Cofradic[].
     *
     * @param aConn Connection from which to read the Cofradic types.
     * @return  Cofradic[] with the Cofradic types as values.
     * @throws SQLException when the retrieve failed.
     */
    public static Cofradic[] getAllCofradics(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Cofradic");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Cofradic(rs));
        }
        Cofradic[] lCofradic = new Cofradic[temp.size()];
        temp.toArray(lCofradic);
        rs.close();
        prep.close();

        return lCofradic;
    }

    /**
     * This method returns a String representation for this Cofradic.
     *
     * @return  String  with the String description for this cofradic.
     */
    public String toString() {
        return this.iType_2;
    }
}
