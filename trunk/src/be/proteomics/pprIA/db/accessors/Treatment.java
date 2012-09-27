/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 25-sept-2007
 * Time: 16:56:23
 * To change this template use File | Settings | File Templates.
 */
package com.compomics.ppr.db.accessors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;


/**
 * This class provides the following enhancements over the TreatmentTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Treatment from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the treatmentid + scientific_name + (taxonomy) of the Treatment.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Treatment extends TreatmentTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Treatment(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor sets the only 'settable' field: scientific_name.
     *
     * @param aName String with the full name for the Treatment.
     */
    public Treatment(String aName) {
        super.setScientific_name(aName);
    }

    /**
     * This constructor reads a Treatment from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: TreatmentID <br />
     * Column 2: Scientific_name <br />
     * Column 3: common_name <br />
     * Column 4: protease <br />
     * Column 5: recombinant <br />
     * Column 6: l_taxonomy <br />
     * Column 7: meropsID <br />
     * Column 8: source <br />
     * Column 9: username< br />
     * Column 10: creationdate < br />
     * Column 11: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Treatment(ResultSet aRS) throws SQLException {
        this.iTreatmentid = aRS.getLong(1);
        this.iScientific_name = aRS.getString(2);
        this.iCommon_name = aRS.getString(3);
        this.iProtease = aRS.getString(4);
        this.iRecombinant =aRS.getString(5);
        this.iL_taxonomy = aRS.getLong(6);
        this.iMeropsid = aRS.getString(7);
        this.iPubmedid = aRS.getString(8);
        this.iSource = aRS.getString(9);
        this.iUsername = aRS.getString(10);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(11);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(12);
    }

    /**
     * This method retrieves all Treatments from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the Treatments from.
     * @return  HashMap with the Treatments.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllTreatmentsAsMap(Connection aConn) throws SQLException {
        HashMap lTreatments = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select treatmentid, scientific_name, common_name, protease, recombinant, l_taxonomy, meropsid, pubmedid, source, username, creationdate, modificationdate from treatment");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Treatment temp = new Treatment(rs);
            lTreatments.put(new Long(temp.getTreatmentid()),temp);
        }
        rs.close();
        prep.close();

        return lTreatments;
    }

    /**
     * This method retrieves all Treatments from the connection and stores them in a Treatment[].
     *
     * @param aConn Connection to retrieve the Treatments from.
     * @return  Treatment[] with the Treatments.
     * @throws SQLException when the retrieve failed.
     */
    public static Treatment[] getAllTreatments(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select treatmentid, scientific_name, common_name, protease, recombinant, l_taxonomy, meropsid, pubmedid, source, username, creationdate, modificationdate from treatment");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Treatment(rs));
        }
        Treatment[] result = new Treatment[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }


    /**
     * This method returns a String representation of the Treatment, ie.: the scientific_name.
     *
     * @return  String  with the scientific_name of the Treatment.
     */

    public String toString() {

        return this.iTreatmentid + ". " + this.iScientific_name + " (" + iL_taxonomy + ")";
    }
}
