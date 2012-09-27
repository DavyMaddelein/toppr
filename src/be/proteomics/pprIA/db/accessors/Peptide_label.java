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
 * Time: 11:01:21
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the Peptide_labelTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Peptide_label from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Peptide_label.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Peptide_label extends Peptide_labelTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Peptide_label(HashMap aParams) {
        super(aParams);
    }


    /**
     * This constructor reads a Peptide_label from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: Peptide_labelid <br />
     * Column 2: l_identificationid <br />
     * Column 3: l_labelid <br />
     * Column 4: username< br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Peptide_label(ResultSet aRS) throws SQLException {
        this.iPeptide_labelid = aRS.getLong(1);
        this.iL_identificationid = aRS.getLong(2);
        this.iL_labelid = aRS.getLong(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);

    }

    /**
     * This method retrieves all Peptide_label from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the Peptide_labels from.
     * @return  HashMap with the Peptide_labels.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllPeptide_labelsAsMap(Connection aConn) throws SQLException {
        HashMap lPeptide_labels = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Peptide_label");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Peptide_label temp = new Peptide_label(rs);
            lPeptide_labels.put(new Long(temp.getPeptide_labelid()),temp);
        }
        rs.close();
        prep.close();

        return lPeptide_labels;
    }

    /**
     * This method retrieves all Peptide_labels from the connection and stores them in a Peptide_label[].
     *
     * @param aConn Connection to retrieve the Peptide_labels from.
     * @return  Peptide_label[] with the Peptide_labels.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide_label[] getAllPeptide_labels(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Peptide_label");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Peptide_label(rs));
        }
        Peptide_label[] result = new Peptide_label[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }
    /**
	 * This method allows the caller to insert a Peptide_label in the Peptide_label table
	 *
	 *
     * @param   aConn Connection to the persitent store.
     * @param   labelid Long holds the modification.
     * @param   identificationid Long holds the identification.
	 */
	public static void insertPeptide_label(Connection aConn,Long identificationid,Long labelid) throws SQLException {
	    PreparedStatement prep = aConn.prepareStatement("INSERT INTO Peptide_label (l_identificationid, l_labelid, username, creationdate, modificationdate)values(?,?,CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        prep.setLong(1, identificationid);
        prep.setLong(2, labelid);
        prep.executeUpdate();
		prep.close();
	}
}
