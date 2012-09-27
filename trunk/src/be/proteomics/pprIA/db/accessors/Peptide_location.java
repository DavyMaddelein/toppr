package com.compomics.ppr.db.accessors;
/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 30-sep-2007
 * Time: 11:50:04
 * To change this template use File | Settings | File Templates.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;


/**
 * This class provides the following enhancements over the Peptide_locationTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Peptide_location from a ResultSet.</li>
 *   <li><b>getPeptide_locationFromGroupID()</b>: returns a Peptide_location who has a specific groupID.</li>
 *   <li><b>truncatepeptide_locationTable()</b>: truncates the Peptide_location table.</li> 
 *   <li><b>toString()</b>: returns the name of the user.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Peptide_location extends Peptide_locationTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Peptide_location(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Peptide_location from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: Peptide_locationID <br />
     * Column 2: l_groupID <br />
     * Column 3: l_proteinID <br />
     * Column 4: start <br />
     * Column 5: end <br />
     * Column 6: username< br />
     * Column 7: creationdate < br />
     * Column 8: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Peptide_location(ResultSet aRS) throws SQLException {
        this.iPeptide_locationid = aRS.getLong(1);
        this.iL_groupid = aRS.getLong(2);
        this.iL_proteinid = aRS.getLong(3);
        this.iStart = aRS.getLong(4);
        this.iEnd = aRS.getLong(5);
        this.iLocation = aRS.getLong(6);
        this.iUsername = aRS.getString(7);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(8);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(9);
    }

    /**
     * This method retrieves all Peptide_locations from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the users from.
     * @return  HashMap with the Peptide_locations.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllPeptide_locationsAsMap(Connection aConn) throws SQLException {
        HashMap lPeptide_locations = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select Peptide_locationID , l_groupID , l_proteinID, start , end, location, username, creationdate, modificationdate from Peptide_location");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Peptide_location temp = new Peptide_location(rs);
            lPeptide_locations.put(new Long(temp.getPeptide_locationid()),temp);
        }
        rs.close();
        prep.close();

        return lPeptide_locations;
    }

    /**
     * This method retrieves all Peptide_locations from the connection and stores them in a Peptide_location[].
     *
     * @param aConn Connection to retrieve the Peptide_locations from.
     * @return  Peptide_location[] with the Peptide_locations.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide_location[] getAllPeptide_locations(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select Peptide_locationID , l_groupID , l_proteinID, start , end, location, username, creationdate, modificationdate from Peptide_location");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Peptide_location(rs));
        }
        Peptide_location[] result = new Peptide_location[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
	 * This method allows the caller to insert a Peptide_locations in the peptide_location table
     *
     * @param   aConn Connection to the persitent store.
     * @param   groupid Long holds the groupID.
     * @param   proteinid Long holds the protienID.
     * @param   start Long holds the start position of the peptide in the protein.
     * @param   end Long holds the end position of the peptide in the protein.
	 */
	public static void insertNewlocation (Connection aConn, Long groupid, Long proteinid ,Long start, Long end, Long location) throws SQLException {
		    PreparedStatement prep = aConn.prepareStatement("INSERT INTO peptide_location (l_groupid, l_proteinid, start, end, location, username, creationdate, modificationdate) values(?,?,?,?,?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
            prep.setLong(1, groupid);
            prep.setLong(2, proteinid);
            prep.setLong(3, start);
            prep.setLong(4, end);
            prep.setLong(5, location);
            prep.executeUpdate();
		    prep.close();
	}



    /**
     * This method retrieves a petide_location from the connection and stores them in a peptide_location.
     *
     * @param aConn Connection to retrieve the Peptide_group from.
     * @return  Peptide_location.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide_location getPeptide_locationFromGroupID(Connection aConn, Long aGroupID) throws SQLException {

        Peptide_location temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from peptide_location where l_groupID = ?");
        ps.setLong(1, aGroupID);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Peptide_location(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            temp = null;
        }
        return temp;
    }

    /**
     * This method truncates the peptide_location table in the ccdb database
     *
     * @param aConn Connection to the database to delete the peptide_location table.
     * @throws java.sql.SQLException when the deletion failed.
     */
    public static void truncatepeptide_locationTable(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("truncate table peptide_location");
        prep.executeUpdate();
        prep.close();

    }

    /**
     * This method returns a String representation of the Peptide_location, ie.: the name.
     *
     * @return  String  with the name of the user.
     */
    public String toString() {
        return java.lang.Long.toString(this.iPeptide_locationid);
    }
}

