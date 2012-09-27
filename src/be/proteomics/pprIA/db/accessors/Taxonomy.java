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
 * Time: 13:58:42
 * To change this template use File | Settings | File Templates.
 */


/**
 * This class provides the following enhancements over the TaxonomyTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Taxonomy from a ResultSet.</li>
 *    <li><B>getTaxonomyFromID()</bi>: returns a Taxonomy for a given taxonomyid.</li>
 *   <li><b>toString()</b>: returns the name of the Taxonomy.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Taxonomy extends TaxonomyTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Taxonomy(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor sets the only 'settable' field: species.
     *
     * @param aName String with the full name for the Taxonomy.
     */
    public Taxonomy(String aName) {
        super.setSpecies(aName);
    }

    /**
     * This constructor reads a Taxonomy from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: taxonomyID <br />
     * Column 2: species <br />
     * Column 3: scientific_name <br />
     * Column 4: username< br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Taxonomy(ResultSet aRS) throws SQLException {
        this.iTaxonomyid = aRS.getLong(1);
        this.iSpecies = aRS.getString(2);
        this.iScientific_name = aRS.getString(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);
    }

    /**
     * This method retrieves all Taxonomys from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the Taxonomys from.
     * @return  HashMap with the Taxonomys.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllTaxonomysAsMap(Connection aConn) throws SQLException {
        HashMap lTaxonomys = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select Taxonomyid, species, scientific_name, username, creationdate, modificationdate from Taxonomy");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Taxonomy temp = new Taxonomy(rs);
            lTaxonomys.put(new Long(temp.getTaxonomyid()),temp);
        }
        rs.close();
        prep.close();

        return lTaxonomys;
    }

    /**
     * This method retrieves all Taxonomys from the connection and stores them in a Taxonomy[].
     *
     * @param aConn Connection to retrieve the Taxonomys from.
     * @return  Taxonomy[] with the Taxonomys.
     * @throws SQLException when the retrieve failed.
     */
    public static Taxonomy[] getAllTaxonomys(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select Taxonomyid, species, scientific_name, username, creationdate, modificationdate from Taxonomy");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Taxonomy(rs));
        }
        Taxonomy[] result = new Taxonomy[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves an Taxonomy from the connection and stores them in a Taxonomy.
     *
     * @param aConn Connection to retrieve the Taxonomy from.
     * @param aTaxonomyid String to search the Taxonomyid column .
     * @return Taxonomy that has a specific Taxonomyid.
     * @throws SQLException when the retrieve failed.
     */
     public static Taxonomy getTaxonomyFromID(Connection aConn, long aTaxonomyid) throws SQLException {

        Taxonomy temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from Taxonomy where Taxonomyid = ?");
        ps.setLong(1, aTaxonomyid);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Taxonomy(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            throw new SQLException("Select based on TaxonomyID '" + aTaxonomyid + "' resulted in " + counter + " results instead of 1!");
        }
        return temp;
    }
    /**
     * This method retrieves a Scientific_name from the connection for a specific taxonomyid.
     *
     * @param aConn Connection to retrieve the Taxonomy from.
     * @param aTaxonomyid String to search the Taxonomyid column .
     * @return Taxonomy that has a specific Taxonomyid.
     * @throws SQLException when the retrieve failed.
     */
     public static String getScientificNameFromTaxonomyID(Connection aConn, long aTaxonomyid) throws SQLException {

        Taxonomy temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from Taxonomy where Taxonomyid = ?");
        ps.setLong(1, aTaxonomyid);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Taxonomy(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            throw new SQLException("Select based on TaxonomyID '" + aTaxonomyid + "' resulted in " + counter + " results instead of 1!");
        }
        return temp.getScientific_name();
    }
    /**
     * This method returns a String representation of the Taxonomy, ie.: the scientific_name.
     *
     * @return  String  with the species of the Taxonomy.
     */
    public String toString() {
        return this.iSpecies;
    }
}
