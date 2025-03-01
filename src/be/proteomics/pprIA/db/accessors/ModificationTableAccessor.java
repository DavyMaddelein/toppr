/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 04/12/2007
 * Time: 09:18:50
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
 * This class is a generated accessor for the Modification table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class ModificationTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'modificationid' column.
	 */
	protected long iModificationid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'name' column.
	 */
	protected String iName = null;


	/**
	 * This variable represents the contents for the 'unimodid' column.
	 */
	protected long iUnimodid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'target' column.
	 */
	protected String iTarget = null;


	/**
	 * This variable represents the contents for the 'description' column.
	 */
	protected String iDescription = null;


	/**
	 * This variable represents the contents for the 'molecular_weight' column.
	 */
	protected double iMolecular_weight = Double.MIN_VALUE;


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
	 * This variable represents the key for the 'modificationid' column.
	 */
	public static final String MODIFICATIONID = "MODIFICATIONID";

	/**
	 * This variable represents the key for the 'name' column.
	 */
	public static final String NAME = "NAME";

	/**
	 * This variable represents the key for the 'unimodid' column.
	 */
	public static final String UNIMODID = "UNIMODID";

	/**
	 * This variable represents the key for the 'target' column.
	 */
	public static final String TARGET = "TARGET";

	/**
	 * This variable represents the key for the 'description' column.
	 */
	public static final String DESCRIPTION = "DESCRIPTION";

	/**
	 * This variable represents the key for the 'molecular_weight' column.
	 */
	public static final String MOLECULAR_WEIGHT = "MOLECULAR_WEIGHT";

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
	public ModificationTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'ModificationTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public ModificationTableAccessor(HashMap aParams) {
		if(aParams.containsKey(MODIFICATIONID)) {
			this.iModificationid = ((Long)aParams.get(MODIFICATIONID)).longValue();
		}
		if(aParams.containsKey(NAME)) {
			this.iName = (String)aParams.get(NAME);
		}
		if(aParams.containsKey(UNIMODID)) {
			this.iUnimodid = ((Long)aParams.get(UNIMODID)).longValue();
		}
		if(aParams.containsKey(TARGET)) {
			this.iTarget = (String)aParams.get(TARGET);
		}
		if(aParams.containsKey(DESCRIPTION)) {
			this.iDescription = (String)aParams.get(DESCRIPTION);
		}
		if(aParams.containsKey(MOLECULAR_WEIGHT)) {
			this.iMolecular_weight = ((Double)aParams.get(MOLECULAR_WEIGHT)).doubleValue();
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
	 * This method returns the value for the 'Modificationid' column
	 * 
	 * @return	long	with the value for the Modificationid column.
	 */
	public long getModificationid() {
		return this.iModificationid;
	}

	/**
	 * This method returns the value for the 'Name' column
	 * 
	 * @return	String	with the value for the Name column.
	 */
	public String getName() {
		return this.iName;
	}

	/**
	 * This method returns the value for the 'Unimodid' column
	 * 
	 * @return	long	with the value for the Unimodid column.
	 */
	public long getUnimodid() {
		return this.iUnimodid;
	}

	/**
	 * This method returns the value for the 'Target' column
	 * 
	 * @return	String	with the value for the Target column.
	 */
	public String getTarget() {
		return this.iTarget;
	}

	/**
	 * This method returns the value for the 'Description' column
	 * 
	 * @return	String	with the value for the Description column.
	 */
	public String getDescription() {
		return this.iDescription;
	}

	/**
	 * This method returns the value for the 'Molecular_weight' column
	 * 
	 * @return	double	with the value for the Molecular_weight column.
	 */
	public double getMolecular_weight() {
		return this.iMolecular_weight;
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
	 * This method sets the value for the 'Modificationid' column
	 * 
	 * @param	aModificationid	long with the value for the Modificationid column.
	 */
	public void setModificationid(long aModificationid) {
		this.iModificationid = aModificationid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Name' column
	 * 
	 * @param	aName	String with the value for the Name column.
	 */
	public void setName(String aName) {
		this.iName = aName;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Unimodid' column
	 * 
	 * @param	aUnimodid	long with the value for the Unimodid column.
	 */
	public void setUnimodid(long aUnimodid) {
		this.iUnimodid = aUnimodid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Target' column
	 * 
	 * @param	aTarget	String with the value for the Target column.
	 */
	public void setTarget(String aTarget) {
		this.iTarget = aTarget;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Description' column
	 * 
	 * @param	aDescription	String with the value for the Description column.
	 */
	public void setDescription(String aDescription) {
		this.iDescription = aDescription;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Molecular_weight' column
	 * 
	 * @param	aMolecular_weight	double with the value for the Molecular_weight column.
	 */
	public void setMolecular_weight(double aMolecular_weight) {
		this.iMolecular_weight = aMolecular_weight;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM modification WHERE modificationid = ?");
		lStat.setLong(1, iModificationid);
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
		if(!aKeys.containsKey(MODIFICATIONID)) {
			throw new IllegalArgumentException("Primary key field 'MODIFICATIONID' is missing in HashMap!");
		} else {
			iModificationid = ((Long)aKeys.get(MODIFICATIONID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM modification WHERE modificationid = ?");
		lStat.setLong(1, iModificationid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iModificationid = lRS.getLong("modificationid");
			iName = (String)lRS.getObject("name");
			iUnimodid = lRS.getLong("unimodid");
			iTarget = (String)lRS.getObject("target");
			iDescription = (String)lRS.getObject("description");
			iMolecular_weight = lRS.getDouble("molecular_weight");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'modification' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'modification' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE modification SET modificationid = ?, name = ?, unimodid = ?, target = ?, description = ?, molecular_weight = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE modificationid = ?");
		lStat.setLong(1, iModificationid);
		lStat.setObject(2, iName);
		lStat.setLong(3, iUnimodid);
		lStat.setObject(4, iTarget);
		lStat.setObject(5, iDescription);
		lStat.setDouble(6, iMolecular_weight);
		lStat.setObject(7, iUsername);
		lStat.setObject(8, iCreationdate);
		lStat.setLong(9, iModificationid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO modification (modificationid, name, unimodid, target, description, molecular_weight, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iModificationid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iModificationid);
		}
		if(iName == null) {
			lStat.setNull(2, 12);
		} else {
			lStat.setObject(2, iName);
		}
		if(iUnimodid == Long.MIN_VALUE) {
			lStat.setNull(3, 4);
		} else {
			lStat.setLong(3, iUnimodid);
		}
		if(iTarget == null) {
			lStat.setNull(4, -1);
		} else {
			lStat.setObject(4, iTarget);
		}
		if(iDescription == null) {
			lStat.setNull(5, -1);
		} else {
			lStat.setObject(5, iDescription);
		}
		if(iMolecular_weight == Double.MIN_VALUE) {
			lStat.setNull(6, 8);
		} else {
			lStat.setDouble(6, iMolecular_weight);
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