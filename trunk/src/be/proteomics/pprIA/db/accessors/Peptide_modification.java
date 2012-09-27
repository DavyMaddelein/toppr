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
 * Time: 9:16:06
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the Peptide_modificationTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Peptide_modification from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Peptide_modification.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Peptide_modification extends Peptide_modificationTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Peptide_modification(HashMap aParams) {
        super(aParams);
    }


    /**
     * This constructor reads a Peptide_modification from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: Peptide_modificationID <br />
     * Column 2: l_identificationid <br />
     * Column 3: l_modificationid <br />
     * Column 4: username< br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Peptide_modification(ResultSet aRS) throws SQLException {
        this.iPeptide_modificationid = aRS.getLong(1);
        this.iL_identificationid = aRS.getLong(2);
        this.iL_modificationid = aRS.getLong(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);

    }

    /**
     * This method retrieves all Peptide_modification from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the Peptide_modifications from.
     * @return  HashMap with the Peptide_modifications.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllPeptide_modificationsAsMap(Connection aConn) throws SQLException {
        HashMap lPeptide_modifications = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Peptide_modification");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Peptide_modification temp = new Peptide_modification(rs);
            lPeptide_modifications.put(new Long(temp.getPeptide_modificationid()),temp);
        }
        rs.close();
        prep.close();

        return lPeptide_modifications;
    }

    /**
     * This method retrieves all Peptide_modifications from the connection and stores them in a Peptide_modification[].
     *
     * @param aConn Connection to retrieve the Peptide_modifications from.
     * @return  Peptide_modification[] with the Peptide_modifications.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide_modification[] getAllPeptide_modifications(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Peptide_modification");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Peptide_modification(rs));
        }
        Peptide_modification[] result = new Peptide_modification[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }
    /**
	 * This method allows the caller to insert a Peptide_modification in the Peptide_modification table
	 *
	 *
     * @param   aConn Connection to the persitent store.
     * @param   modificationid Long holds the modification.
     * @param   identificationid Long holds the identification.
	 */
	public static void insertPeptide_modification(Connection aConn, Long identificationid,Long modificationid) throws SQLException {
	    PreparedStatement prep = aConn.prepareStatement("INSERT INTO Peptide_modification (l_identificationid, l_modificationid, username, creationdate, modificationdate)values(?,?,CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        prep.setLong(1, identificationid);
        prep.setLong(2, modificationid);
        prep.executeUpdate();
		prep.close();
	}
}

