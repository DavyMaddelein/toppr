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
 * Time: 8:36:30
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the OrthologueTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Orthologue from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Orthologue.</li>
 *   <li><B>getOrthologueFromID()</bi>: returns a Orthologue for a given orthologueid.</li>
 *   <li><b>hashCode()</b>: returns a hashcode for the Orthologue (which is just the Orthologue's ID).</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Orthologue extends com.compomics.ppr.db.accessors.OrthologueTableAccessor {

    /**
     * Default constructor.
     */
    public Orthologue() {
        super();
    }

    /**
     * Wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Orthologue(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads the instrument from a resultset. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: orthologue ID <br />
     * Column 2: l_proteinid <br />
     * Column 3: name <br />
     * Column 4: type <br />
     * Column 5: username< br />
     * Column 6: creationdate < br />
     * Column 7: modificationdate < br />
     * @param   aRS ResultSet to read the data from.
     * @exception java.sql.SQLException    when reading the ResultSet failed.
     */
    public Orthologue(ResultSet aRS) throws SQLException {
        this.iOrthologueid = aRS.getLong(1);
        this.iL_proteinid = aRS.getLong(2);
        this.iEntry_name = aRS.getString(3);
        this.iSequence = aRS.getString(4);
        this.iUsername = aRS.getString(5);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(6);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(7);

    }

    /**
     * This methods reads all Orthologues from the Orthologue table.
     *
     * @param aConn Connection to read the instruments from.
     * @return  Orthologue[] with all the orthologues in the 'Orthologue' table.
     * @throws SQLException when the retrieving of the instruments went wrong.
     */
    public static Orthologue[] getAllOrthologues(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Orthologue order by orthologueid ASC");
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        while(rs.next()) {
            v.add(new Orthologue(rs));
        }
        rs.close();
        prep.close();
        Orthologue[] lOrthologues = new Orthologue[v.size()];
        v.toArray(lOrthologues);

        return lOrthologues;
    }
    /**
     * This method retrieves an Orthologue from the connection and stores them in a Orthologue.
     *
     * @param aConn Connection to retrieve the Orthologue from.
     * @param aOrthologueid Long to search the Orthologueid column .
     * @return identification that has a specific Orthologueid.
     * @throws SQLException when the retrieve failed.
     */
     public static Orthologue getOrthologueFromID(Connection aConn, long aOrthologueid) throws SQLException {

        Orthologue temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from Orthologue where Orthologueid = ?");
        ps.setLong(1, aOrthologueid);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Orthologue(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            throw new SQLException("Select based on Orthologueid '" + aOrthologueid + "' resulted in " + counter + " results instead of 1!");
        }
        return temp;
    }

    /**
     * Returns a String representation for this Orthologue.
     *
     * @return  String  with the String representation for this Orthologue.
     */
    public String toString() {
        return this.iOrthologueid + ". " + this.iEntry_name;
    }

    /**
     * Returns a hashcode for the Orthologue. <br />
     * The hashcode is just the OrthologueID, cast to int, which is
     * the PK on the table.
     *
     * @return  int with the hashcode
     */
    public int hashCode() {
        return (int)this.iOrthologueid;
    }
}
