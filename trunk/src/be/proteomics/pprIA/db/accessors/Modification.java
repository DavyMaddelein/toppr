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
 * Date: 4-dec-2007
 * Time: 9:16:21
 * To change this template use File | Settings | File Templates.
 */
/**
 * This class provides the following enhancements over the ModificationTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Modification from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the user.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Modification extends ModificationTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Modification(HashMap aParams) {
        super(aParams);
    }


    /**
     * This constructor reads a Modification from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: ModificationID <br />
     * Column 2: name <br />
     * Column 3: unimodid <br />
     * Column 4: target <br />
     * Column 5: description <br />
     * Column 6: molecular_weight <br />
     * Column 7: username< br />
     * Column 8: creationdate < br />
     * Column 9: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Modification(ResultSet aRS) throws SQLException {
        this.iModificationid = aRS.getLong(1);
        this.iName = aRS.getString(2);
        this.iUnimodid = aRS.getLong(3);
        this.iTarget = aRS.getString(4);
        this.iDescription = aRS.getString(5);
        this.iMolecular_weight = aRS.getDouble(6);
        this.iUsername = aRS.getString(7);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(8);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(9);
    }

    /**
     * This method retrieves all Modifications from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the users from.
     * @return HashMap with all the Modifications.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllModificationsAsMap(Connection aConn) throws SQLException {
        HashMap lModifications = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Modification");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Modification temp = new Modification(rs);
            lModifications.put(new Long(temp.getModificationid()),temp);
        }
        rs.close();
        prep.close();

        return lModifications;
    }

    /**
     * This method retrieves all Modifications from the connection and stores them in a Modification[].
     *
     * @param aConn Connection to retrieve the Modifications from.
     * @return  Modification[] with the Modifications.
     * @throws SQLException when the retrieve failed.
     */
    public static Modification[] getAllModifications(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Modification");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Modification(rs));
        }
        Modification[] result = new Modification[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }


    /**
     * This method returns a String representation of the Modification, ie.: the name.
     *
     * @return  String  with the name of the Modification.
     */
    public String toString() {
        return this.iName;
    }
}
