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
 * Time: 8:44:41
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the Pdb_blockTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Pdb_block from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Pdb_block.</li>
 *   <li><B>getPdb_blockFromID()</bi>: returns a Pdb_block for a given pdb_blockid.</li>
 *   <li><b>hashCode()</b>: returns a hashcode for the Pdb_block (which is just the Pdb_block's ID).</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Pdb_block extends com.compomics.ppr.db.accessors.Pdb_blockTableAccessor {

    /**
     * Default constructor.
     */
    public Pdb_block() {
        super();
    }

    /**
     * Wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Pdb_block(HashMap aParams) {
        if(aParams.containsKey(PDB_BLOCKID)) {
			this.iPdb_blockid = ((Long)aParams.get(PDB_BLOCKID)).longValue();
		}
		if(aParams.containsKey(L_PDBID)) {
			this.iL_pdbid = ((Long)aParams.get(L_PDBID)).longValue();
		}
		if(aParams.containsKey(L_PROTEINID)) {
			this.iL_proteinid = ((Long)aParams.get(L_PROTEINID)).longValue();
		}
		if(aParams.containsKey(BLOCK)) {
			this.iBlock = (String)aParams.get(BLOCK);
		}
		if(aParams.containsKey(START_PROTEIN)) {
			this.iStart_protein = ((Long)aParams.get(START_PROTEIN)).longValue();
		}
		if(aParams.containsKey(END_PROTEIN)) {
			this.iEnd_protein = ((Long)aParams.get(END_PROTEIN)).longValue();
		}
		if(aParams.containsKey(START_BLOCK)) {
			this.iStart_block = ((Long)aParams.get(START_BLOCK)).longValue();
		}
		if(aParams.containsKey(END_BLOCK)) {
			this.iEnd_block = ((Long)aParams.get(END_BLOCK)).longValue();
		}
		if(aParams.containsKey(USERNAME)) {
			this.iUsername = (String)aParams.get(USERNAME);
		}
		if(aParams.containsKey(CREATIONDATE)) {
			this.iCreationdate = (java.sql.Timestamp)aParams.get(CREATIONDATE);
		}
		if(aParams.containsKey(MODIFICATIONDATE)) {
			this.iModificationdate = (java.sql.Timestamp)aParams.get(MODIFICATIONDATE);
		}
		this.iUpdated = true;
    }

    /**
     * This constructor reads the instrument from a resultset. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: pdb_block ID <br />
     * Column 2: l_pdbid <br />
     * Column 3: l_proteinid <br />
     * Column 4: block <br />
     * Column 5: start_protein <br />
     * Column 6: end_protein <br />
     * Column 7: start_block <br />
     * Column 8: end_block <br />
     * Column 9: username< br />
     * Column 10: creationdate < br />
     * Column 11: modificationdate < br />
     * @param   aRS ResultSet to read the data from.
     * @exception java.sql.SQLException    when reading the ResultSet failed.
     */
    public Pdb_block(ResultSet aRS) throws SQLException {
        this.iPdb_blockid = aRS.getLong(1);
        this.iL_pdbid = aRS.getLong(2);
        this.iL_proteinid = aRS.getLong(3);
        this.iBlock = aRS.getString(4);
        this.iStart_protein = aRS.getLong(5);
        this.iEnd_protein  = aRS.getLong(6);
        this.iStart_block  = aRS.getLong(7);
        this.iEnd_block = aRS.getLong(8);
        this.iUsername = aRS.getString(9);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(10);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(11);

    }

    /**
     * This methods reads all Pdb_blocks from the Pdb_block table.
     *
     * @param aConn Connection to read the instruments from.
     * @return  Pdb_block[] with all the pdb_blocks in the 'Pdb_block' table.
     * @throws SQLException when the retrieving of the instruments went wrong.
     */
    public static Pdb_block[] getAllPdb_blocks(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Pdb_block order by pdb_blockid ASC");
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        while(rs.next()) {
            v.add(new Pdb_block(rs));
        }
        rs.close();
        prep.close();
        Pdb_block[] lPdb_blocks = new Pdb_block[v.size()];
        v.toArray(lPdb_blocks);

        return lPdb_blocks;
    }
    /**
     * This method retrieves an Pdb_block from the connection and stores them in a Pdb_block.
     *
     * @param aConn Connection to retrieve the Pdb_block from.
     * @param aPdb_blockid Long to search the Pdb_blockid column .
     * @return identification that has a specific Pdb_blockid.
     * @throws SQLException when the retrieve failed.
     */
     public static Pdb_block getPdb_blockFromID(Connection aConn, long aPdb_blockid) throws SQLException {

        Pdb_block temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from Pdb_block where Pdb_blockid = ?");
        ps.setLong(1, aPdb_blockid);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Pdb_block(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            throw new SQLException("Select based on Pdb_blockid '" + aPdb_blockid + "' resulted in " + counter + " results instead of 1!");
        }
        return temp;
    }

    /**
     * Returns a String representation for this Pdb_block.
     *
     * @return  String  with the String representation for this Pdb_block.
     */
    public String toString() {
        return this.iPdb_blockid + ". " + this.iBlock;
    }

    /**
     * Returns a hashcode for the Pdb_block. <br />
     * The hashcode is just the Pdb_blockID, cast to int, which is
     * the PK on the table.
     *
     * @return  int with the hashcode
     */
    public int hashCode() {
        return (int)this.iPdb_blockid;
    }
}