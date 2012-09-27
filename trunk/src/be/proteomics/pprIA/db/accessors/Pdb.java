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
 * Time: 8:41:38
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the PdbTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Pdb from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Pdb.</li>
 *   <li><B>getPdbFromID()</bi>: returns a Pdb for a given pdbid.</li>
 *   <li><b>hashCode()</b>: returns a hashcode for the Pdb (which is just the Pdb's ID).</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Pdb extends com.compomics.ppr.db.accessors.PdbTableAccessor {

    /**
     * Default constructor.
     */
    public Pdb() {
        super();
    }

    /**
     * Wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Pdb(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads the instrument from a resultset. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: pdb ID <br />
     * Column 2: l_proteinid <br />
     * Column 3: pdbaccession <br />
     * Column 4: title <br />
     * Column 5: experiment_type <br />
     * Column 6: resolution <br />
     * Column 7: username< br />
     * Column 8: creationdate < br />
     * Column 9: modificationdate < br />
     * @param   aRS ResultSet to read the data from.
     * @exception java.sql.SQLException    when reading the ResultSet failed.
     */
    public Pdb(ResultSet aRS) throws SQLException {
        this.iPdbid = aRS.getLong(1);
        this.iL_proteinid = aRS.getLong(2);
        this.iPdbaccession = aRS.getString(3);
        this.iTitle = aRS.getString(4);
        this.iExperiment_type  = aRS.getString(5);
        this.iResolution  = aRS.getString(6);
        this.iUsername = aRS.getString(7);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(8);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(9);

    }

    /**
     * This methods reads all Pdbs from the Pdb table.
     *
     * @param aConn Connection to read the instruments from.
     * @return  Pdb[] with all the pdbs in the 'Pdb' table.
     * @throws SQLException when the retrieving of the instruments went wrong.
     */
    public static Pdb[] getAllPdbs(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Pdb order by pdbid ASC");
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        while(rs.next()) {
            v.add(new Pdb(rs));
        }
        rs.close();
        prep.close();
        Pdb[] lPdbs = new Pdb[v.size()];
        v.toArray(lPdbs);

        return lPdbs;
    }
    /**
     * This method retrieves an Pdb from the connection and stores them in a Pdb.
     *
     * @param aConn Connection to retrieve the Pdb from.
     * @param aPdbid Long to search the Pdbid column .
     * @return identification that has a specific Pdbid.
     * @throws SQLException when the retrieve failed.
     */
     public static Pdb getPdbFromID(Connection aConn, long aPdbid) throws SQLException {

        Pdb temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from Pdb where Pdbid = ?");
        ps.setLong(1, aPdbid);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Pdb(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            throw new SQLException("Select based on Pdbid '" + aPdbid + "' resulted in " + counter + " results instead of 1!");
        }
        return temp;
    }

    /**
     * Returns a String representation for this Pdb.
     *
     * @return  String  with the String representation for this Pdb.
     */
    public String toString() {
        return this.iPdbid + ". " + this.iPdbaccession;
    }

    /**
     * Returns a hashcode for the Pdb. <br />
     * The hashcode is just the PdbID, cast to int, which is
     * the PK on the table.
     *
     * @return  int with the hashcode
     */
    public int hashCode() {
        return (int)this.iPdbid;
    }
}
