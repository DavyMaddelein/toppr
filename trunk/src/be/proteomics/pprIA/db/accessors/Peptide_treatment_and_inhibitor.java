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
 * Date: 16-okt-2007
 * Time: 10:54:34
 * To change this template use File | Settings | File Templates.
 */



/**
 * This class provides the following enhancements over the Peptide_treatment_and_inhibitorTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Peptide_treatment_and_inhibitor from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Peptide_treatment_and_inhibitor.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Peptide_treatment_and_inhibitor extends Peptide_treatment_and_inhibitorTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Peptide_treatment_and_inhibitor(HashMap aParams) {
        super(aParams);
    }


    /**
     * This constructor reads a Peptide_treatment_and_inhibitor from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: peptide_treatment_and_inhibitorID <br />
     * Column 2: l_inhibitorid <br />
     * Column 3: l_treatmentid <br />
     * Column 4: l_identificationid <br />
     * Column 5: username< br />
     * Column 6: creationdate < br />
     * Column 7: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Peptide_treatment_and_inhibitor(ResultSet aRS) throws SQLException {
        this.iPeptide_treatment_and_inhibitorid = aRS.getLong(1);
        this.iL_inhibitorid = aRS.getLong(2);
        this.iL_treatmentid = aRS.getLong(3);
        this.iL_identificationid = aRS.getLong(4);
        this.iUsername = aRS.getString(5);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(6);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(7);

    }

    /**
     * This method retrieves all Peptide_treatment_and_inhibitor from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the Peptide_treatment_and_inhibitors from.
     * @return  HashMap with the Peptide_treatment_and_inhibitors.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllPeptide_treatment_and_inhibitorsAsMap(Connection aConn) throws SQLException {
        HashMap lPeptide_treatment_and_inhibitors = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Peptide_treatment_and_inhibitor");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Peptide_treatment_and_inhibitor temp = new Peptide_treatment_and_inhibitor(rs);
            lPeptide_treatment_and_inhibitors.put(new Long(temp.getPeptide_treatment_and_inhibitorid()),temp);
        }
        rs.close();
        prep.close();

        return lPeptide_treatment_and_inhibitors;
    }

    /**
     * This method retrieves all Peptide_treatment_and_inhibitors from the connection and stores them in a Peptide_treatment_and_inhibitor[].
     *
     * @param aConn Connection to retrieve the Peptide_treatment_and_inhibitors from.
     * @return  Peptide_treatment_and_inhibitor[] with the Peptide_treatment_and_inhibitors.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide_treatment_and_inhibitor[] getAllPeptide_treatment_and_inhibitors(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Peptide_treatment_and_inhibitor");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Peptide_treatment_and_inhibitor(rs));
        }
        Peptide_treatment_and_inhibitor[] result = new Peptide_treatment_and_inhibitor[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }
    /**
	 * This method allows the caller to insert a Peptide_treatment_and_inhibitor in the Peptide_treatment_and_inhibitor table
	 *
	 *
     * @param   aConn Connection to the persitent store.
     * @param   inhibitorid Long holds the inhibitor.
     * @param   treatmentid Long holds the inhibitor.
     * @param   identificationid Long holds the identification.
	 */
	public static void insertPeptide_treatment_and_inhibitor(Connection aConn,Long inhibitorid, Long treatmentid, Long identificationid) throws SQLException {
	    PreparedStatement prep = aConn.prepareStatement("INSERT INTO peptide_treatment_and_inhibitor (l_inhibitorid, l_treatmentid, l_identificationid, username, creationdate, modificationdate)values(?,?,?,CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        prep.setLong(1, inhibitorid);
        prep.setLong(2, treatmentid);
        prep.setLong(3, identificationid);
        prep.executeUpdate();
		prep.close();
	}
}

