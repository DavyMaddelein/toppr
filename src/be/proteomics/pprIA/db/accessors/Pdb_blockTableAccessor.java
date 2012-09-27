/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 27/06/2008
 * Time: 08:26:27
 */
package com.compomics.ppr.db.accessors;

import java.sql.*;
import java.util.*;
import com.compomics.util.db.interfaces.Deleteable;
import com.compomics.util.db.interfaces.Persistable;
import com.compomics.util.db.interfaces.Retrievable;
import com.compomics.util.db.interfaces.Updateable;

/*
 * CVS information:
 *
 * $Revision: 1.4 $
 * $Date: 2007/07/06 09:41:53 $
 */

/**
 * This class is a generated accessor for the Pdb_block table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class Pdb_blockTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'pdb_blockid' column.
	 */
	protected long iPdb_blockid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_pdbid' column.
	 */
	protected long iL_pdbid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_proteinid' column.
	 */
	protected long iL_proteinid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'block' column.
	 */
	protected String iBlock = null;


	/**
	 * This variable represents the contents for the 'start_protein' column.
	 */
	protected long iStart_protein = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'end_protein' column.
	 */
	protected long iEnd_protein = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'start_block' column.
	 */
	protected long iStart_block = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'end_block' column.
	 */
	protected long iEnd_block = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'username' column.
	 */
	protected String iUsername = null;


	/**
	 * This variable represents the contents for the 'creationdate' column.
	 */
	protected java.sql.Timestamp iCreationdate = null;


	/**
	 * This variable represents the contents for the 'modificationdate' column.
	 */
	protected java.sql.Timestamp iModificationdate = null;


	/**
	 * This variable represents the key for the 'pdb_blockid' column.
	 */
	public static final String PDB_BLOCKID = "PDB_BLOCKID";

	/**
	 * This variable represents the key for the 'l_pdbid' column.
	 */
	public static final String L_PDBID = "L_PDBID";

	/**
	 * This variable represents the key for the 'l_proteinid' column.
	 */
	public static final String L_PROTEINID = "L_PROTEINID";

	/**
	 * This variable represents the key for the 'block' column.
	 */
	public static final String BLOCK = "BLOCK";

	/**
	 * This variable represents the key for the 'start_protein' column.
	 */
	public static final String START_PROTEIN = "START_PROTEIN";

	/**
	 * This variable represents the key for the 'end_protein' column.
	 */
	public static final String END_PROTEIN = "END_PROTEIN";

	/**
	 * This variable represents the key for the 'start_block' column.
	 */
	public static final String START_BLOCK = "START_BLOCK";

	/**
	 * This variable represents the key for the 'end_block' column.
	 */
	public static final String END_BLOCK = "END_BLOCK";

	/**
	 * This variable represents the key for the 'username' column.
	 */
	public static final String USERNAME = "USERNAME";

	/**
	 * This variable represents the key for the 'creationdate' column.
	 */
	public static final String CREATIONDATE = "CREATIONDATE";

	/**
	 * This variable represents the key for the 'modificationdate' column.
	 */
	public static final String MODIFICATIONDATE = "MODIFICATIONDATE";




	/**
	 * Default constructor.
	 */
	public Pdb_blockTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'Pdb_blockTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public Pdb_blockTableAccessor(HashMap aParams) {
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
	 * This method returns the value for the 'Pdb_blockid' column
	 * 
	 * @return	long	with the value for the Pdb_blockid column.
	 */
	public long getPdb_blockid() {
		return this.iPdb_blockid;
	}

	/**
	 * This method returns the value for the 'L_pdbid' column
	 * 
	 * @return	long	with the value for the L_pdbid column.
	 */
	public long getL_pdbid() {
		return this.iL_pdbid;
	}

	/**
	 * This method returns the value for the 'L_proteinid' column
	 * 
	 * @return	long	with the value for the L_proteinid column.
	 */
	public long getL_proteinid() {
		return this.iL_proteinid;
	}

	/**
	 * This method returns the value for the 'Block' column
	 * 
	 * @return	String	with the value for the Block column.
	 */
	public String getBlock() {
		return this.iBlock;
	}

	/**
	 * This method returns the value for the 'Start_protein' column
	 * 
	 * @return	long	with the value for the Start_protein column.
	 */
	public long getStart_protein() {
		return this.iStart_protein;
	}

	/**
	 * This method returns the value for the 'End_protein' column
	 * 
	 * @return	long	with the value for the End_protein column.
	 */
	public long getEnd_protein() {
		return this.iEnd_protein;
	}

	/**
	 * This method returns the value for the 'Start_block' column
	 * 
	 * @return	long	with the value for the Start_block column.
	 */
	public long getStart_block() {
		return this.iStart_block;
	}

	/**
	 * This method returns the value for the 'End_block' column
	 * 
	 * @return	long	with the value for the End_block column.
	 */
	public long getEnd_block() {
		return this.iEnd_block;
	}

	/**
	 * This method returns the value for the 'Username' column
	 * 
	 * @return	String	with the value for the Username column.
	 */
	public String getUsername() {
		return this.iUsername;
	}

	/**
	 * This method returns the value for the 'Creationdate' column
	 * 
	 * @return	java.sql.Timestamp	with the value for the Creationdate column.
	 */
	public java.sql.Timestamp getCreationdate() {
		return this.iCreationdate;
	}

	/**
	 * This method returns the value for the 'Modificationdate' column
	 * 
	 * @return	java.sql.Timestamp	with the value for the Modificationdate column.
	 */
	public java.sql.Timestamp getModificationdate() {
		return this.iModificationdate;
	}

	/**
	 * This method sets the value for the 'Pdb_blockid' column
	 * 
	 * @param	aPdb_blockid	long with the value for the Pdb_blockid column.
	 */
	public void setPdb_blockid(long aPdb_blockid) {
		this.iPdb_blockid = aPdb_blockid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'L_pdbid' column
	 * 
	 * @param	aL_pdbid	long with the value for the L_pdbid column.
	 */
	public void setL_pdbid(long aL_pdbid) {
		this.iL_pdbid = aL_pdbid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'L_proteinid' column
	 * 
	 * @param	aL_proteinid	long with the value for the L_proteinid column.
	 */
	public void setL_proteinid(long aL_proteinid) {
		this.iL_proteinid = aL_proteinid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Block' column
	 * 
	 * @param	aBlock	String with the value for the Block column.
	 */
	public void setBlock(String aBlock) {
		this.iBlock = aBlock;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Start_protein' column
	 * 
	 * @param	aStart_protein	long with the value for the Start_protein column.
	 */
	public void setStart_protein(long aStart_protein) {
		this.iStart_protein = aStart_protein;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'End_protein' column
	 * 
	 * @param	aEnd_protein	long with the value for the End_protein column.
	 */
	public void setEnd_protein(long aEnd_protein) {
		this.iEnd_protein = aEnd_protein;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Start_block' column
	 * 
	 * @param	aStart_block	long with the value for the Start_block column.
	 */
	public void setStart_block(long aStart_block) {
		this.iStart_block = aStart_block;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'End_block' column
	 * 
	 * @param	aEnd_block	long with the value for the End_block column.
	 */
	public void setEnd_block(long aEnd_block) {
		this.iEnd_block = aEnd_block;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Username' column
	 * 
	 * @param	aUsername	String with the value for the Username column.
	 */
	public void setUsername(String aUsername) {
		this.iUsername = aUsername;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Creationdate' column
	 * 
	 * @param	aCreationdate	java.sql.Timestamp with the value for the Creationdate column.
	 */
	public void setCreationdate(java.sql.Timestamp aCreationdate) {
		this.iCreationdate = aCreationdate;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Modificationdate' column
	 * 
	 * @param	aModificationdate	java.sql.Timestamp with the value for the Modificationdate column.
	 */
	public void setModificationdate(java.sql.Timestamp aModificationdate) {
		this.iModificationdate = aModificationdate;
		this.iUpdated = true;
	}



	/**
	 * This method allows the caller to delete the data represented by this
	 * object in a persistent store.
	 *
	 * @param   aConn Connection to the persitent store.
	 */
	public int delete(Connection aConn) throws SQLException {
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM pdb_block WHERE pdb_blockid = ?");
		lStat.setLong(1, iPdb_blockid);
		int result = lStat.executeUpdate();
		lStat.close();
		return result;
	}


	/**
	 * This method allows the caller to read data for this
	 * object from a persistent store based on the specified keys.
	 *
	 * @param   aConn Connection to the persitent store.
	 */
	public void retrieve(Connection aConn, HashMap aKeys) throws SQLException {
		// First check to see whether all PK fields are present.
		if(!aKeys.containsKey(PDB_BLOCKID)) {
			throw new IllegalArgumentException("Primary key field 'PDB_BLOCKID' is missing in HashMap!");
		} else {
			iPdb_blockid = ((Long)aKeys.get(PDB_BLOCKID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM pdb_block WHERE pdb_blockid = ?");
		lStat.setLong(1, iPdb_blockid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iPdb_blockid = lRS.getLong("pdb_blockid");
			iL_pdbid = lRS.getLong("l_pdbid");
			iL_proteinid = lRS.getLong("l_proteinid");
			iBlock = (String)lRS.getObject("block");
			iStart_protein = lRS.getLong("start_protein");
			iEnd_protein = lRS.getLong("end_protein");
			iStart_block = lRS.getLong("start_block");
			iEnd_block = lRS.getLong("end_block");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'pdb_block' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'pdb_block' table! Object is not initialized correctly!");
		}
	}


	/**
	 * This method allows the caller to update the data represented by this
	 * object in a persistent store.
	 *
	 * @param   aConn Connection to the persitent store.
	 */
	public int update(Connection aConn) throws SQLException {
		if(!this.iUpdated) {
			return 0;
		}
		PreparedStatement lStat = aConn.prepareStatement("UPDATE pdb_block SET pdb_blockid = ?, l_pdbid = ?, l_proteinid = ?, block = ?, start_protein = ?, end_protein = ?, start_block = ?, end_block = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE pdb_blockid = ?");
		lStat.setLong(1, iPdb_blockid);
		lStat.setLong(2, iL_pdbid);
		lStat.setLong(3, iL_proteinid);
		lStat.setObject(4, iBlock);
		lStat.setLong(5, iStart_protein);
		lStat.setLong(6, iEnd_protein);
		lStat.setLong(7, iStart_block);
		lStat.setLong(8, iEnd_block);
		lStat.setObject(9, iUsername);
		lStat.setObject(10, iCreationdate);
		lStat.setLong(11, iPdb_blockid);
		int result = lStat.executeUpdate();
		lStat.close();
		this.iUpdated = false;
		return result;
	}


	/**
	 * This method allows the caller to insert the data represented by this
	 * object in a persistent store.
	 *
	 * @param   aConn Connection to the persitent store.
	 */
	public int persist(Connection aConn) throws SQLException {
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO pdb_block (pdb_blockid, l_pdbid, l_proteinid, block, start_protein, end_protein, start_block, end_block, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",Statement.RETURN_GENERATED_KEYS);
		if(iPdb_blockid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iPdb_blockid);
		}
		if(iL_pdbid == Long.MIN_VALUE) {
			lStat.setNull(2, 4);
		} else {
			lStat.setLong(2, iL_pdbid);
		}
		if(iL_proteinid == Long.MIN_VALUE) {
			lStat.setNull(3, 4);
		} else {
			lStat.setLong(3, iL_proteinid);
		}
		if(iBlock == null) {
			lStat.setNull(4, 12);
		} else {
			lStat.setObject(4, iBlock);
		}
		if(iStart_protein == Long.MIN_VALUE) {
			lStat.setNull(5, 4);
		} else {
			lStat.setLong(5, iStart_protein);
		}
		if(iEnd_protein == Long.MIN_VALUE) {
			lStat.setNull(6, 4);
		} else {
			lStat.setLong(6, iEnd_protein);
		}
		if(iStart_block == Long.MIN_VALUE) {
			lStat.setNull(7, 4);
		} else {
			lStat.setLong(7, iStart_block);
		}
		if(iEnd_block == Long.MIN_VALUE) {
			lStat.setNull(8, 4);
		} else {
			lStat.setLong(8, iEnd_block);
		}
		int result = lStat.executeUpdate();

		// Retrieving the generated keys (if any).
		ResultSet lrsKeys = lStat.getGeneratedKeys();
		ResultSetMetaData lrsmKeys = lrsKeys.getMetaData();
		int colCount = lrsmKeys.getColumnCount();
		iKeys = new Object[colCount];
		while(lrsKeys.next()) {
			for(int i=0;i<iKeys.length;i++) {
				iKeys[i] = lrsKeys.getObject(i+1);
			}
		}
		lrsKeys.close();
		lStat.close();
		this.iUpdated = false;
		return result;
	}

	/**
	 * This method will return the automatically generated key for the insert if 
	 * one was triggered, or 'null' otherwise.
	 *
	 * @return	Object[]	with the generated keys.
	 */
	public Object[] getGeneratedKeys() {
		return this.iKeys;
	}

}