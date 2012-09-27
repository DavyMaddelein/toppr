/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 03/01/2008
 * Time: 09:59:11
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
 * This class is a generated accessor for the Pdb_accession table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class Pdb_accessionTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'pdb_accessionid' column.
	 */
	protected long iPdb_accessionid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_proteinid' column.
	 */
	protected long iL_proteinid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'pdbid' column.
	 */
	protected String iPdbid = null;


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
	 * This variable represents the key for the 'pdb_accessionid' column.
	 */
	public static final String PDB_ACCESSIONID = "PDB_ACCESSIONID";

	/**
	 * This variable represents the key for the 'l_proteinid' column.
	 */
	public static final String L_PROTEINID = "L_PROTEINID";

	/**
	 * This variable represents the key for the 'pdbid' column.
	 */
	public static final String PDBID = "PDBID";

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
	public Pdb_accessionTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'Pdb_accessionTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public Pdb_accessionTableAccessor(HashMap aParams) {
		if(aParams.containsKey(PDB_ACCESSIONID)) {
			this.iPdb_accessionid = ((Long)aParams.get(PDB_ACCESSIONID)).longValue();
		}
		if(aParams.containsKey(L_PROTEINID)) {
			this.iL_proteinid = ((Long)aParams.get(L_PROTEINID)).longValue();
		}
		if(aParams.containsKey(PDBID)) {
			this.iPdbid = (String)aParams.get(PDBID);
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
	 * This method returns the value for the 'Pdb_accessionid' column
	 * 
	 * @return	long	with the value for the Pdb_accessionid column.
	 */
	public long getPdb_accessionid() {
		return this.iPdb_accessionid;
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
	 * This method returns the value for the 'Pdbid' column
	 * 
	 * @return	String	with the value for the Pdbid column.
	 */
	public String getPdbid() {
		return this.iPdbid;
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
	 * This method sets the value for the 'Pdb_accessionid' column
	 * 
	 * @param	aPdb_accessionid	long with the value for the Pdb_accessionid column.
	 */
	public void setPdb_accessionid(long aPdb_accessionid) {
		this.iPdb_accessionid = aPdb_accessionid;
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
	 * This method sets the value for the 'Pdbid' column
	 * 
	 * @param	aPdbid	String with the value for the Pdbid column.
	 */
	public void setPdbid(String aPdbid) {
		this.iPdbid = aPdbid;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM pdb_accession WHERE pdb_accessionid = ?");
		lStat.setLong(1, iPdb_accessionid);
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
		if(!aKeys.containsKey(PDB_ACCESSIONID)) {
			throw new IllegalArgumentException("Primary key field 'PDB_ACCESSIONID' is missing in HashMap!");
		} else {
			iPdb_accessionid = ((Long)aKeys.get(PDB_ACCESSIONID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM pdb_accession WHERE pdb_accessionid = ?");
		lStat.setLong(1, iPdb_accessionid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iPdb_accessionid = lRS.getLong("pdb_accessionid");
			iL_proteinid = lRS.getLong("l_proteinid");
			iPdbid = (String)lRS.getObject("pdbid");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'pdb_accession' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'pdb_accession' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE pdb_accession SET pdb_accessionid = ?, l_proteinid = ?, pdbid = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE pdb_accessionid = ?");
		lStat.setLong(1, iPdb_accessionid);
		lStat.setLong(2, iL_proteinid);
		lStat.setObject(3, iPdbid);
		lStat.setObject(4, iUsername);
		lStat.setObject(5, iCreationdate);
		lStat.setLong(6, iPdb_accessionid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO pdb_accession (pdb_accessionid, l_proteinid, pdbid, username, creationdate, modificationdate) values(?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iPdb_accessionid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iPdb_accessionid);
		}
		if(iL_proteinid == Long.MIN_VALUE) {
			lStat.setNull(2, 4);
		} else {
			lStat.setLong(2, iL_proteinid);
		}
		if(iPdbid == null) {
			lStat.setNull(3, 12);
		} else {
			lStat.setObject(3, iPdbid);
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