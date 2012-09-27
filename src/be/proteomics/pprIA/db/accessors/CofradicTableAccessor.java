/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 16/10/2007
 * Time: 09:45:26
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
 * $Revision: 1.2 $
 * $Date: 2007/11/12 08:56:24 $
 */

/**
 * This class is a generated accessor for the Cofradic table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class CofradicTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'cofradicid' column.
	 */
	protected long iCofradicid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'type_2' column.
	 */
	protected String iType_2 = null;


	/**
	 * This variable represents the contents for the 'description' column.
	 */
	protected String iDescription = null;


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
	 * This variable represents the key for the 'cofradicid' column.
	 */
	public static final String COFRADICID = "COFRADICID";

	/**
	 * This variable represents the key for the 'type_2' column.
	 */
	public static final String TYPE_2 = "TYPE_2";

	/**
	 * This variable represents the key for the 'description' column.
	 */
	public static final String DESCRIPTION = "DESCRIPTION";

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
	public CofradicTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'CofradicTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public CofradicTableAccessor(HashMap aParams) {
		if(aParams.containsKey(COFRADICID)) {
			this.iCofradicid = ((Long)aParams.get(COFRADICID)).longValue();
		}
		if(aParams.containsKey(TYPE_2)) {
			this.iType_2 = (String)aParams.get(TYPE_2);
		}
		if(aParams.containsKey(DESCRIPTION)) {
			this.iDescription = (String)aParams.get(DESCRIPTION);
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
	 * This method returns the value for the 'Cofradicid' column
	 * 
	 * @return	long	with the value for the Cofradicid column.
	 */
	public long getCofradicid() {
		return this.iCofradicid;
	}

	/**
	 * This method returns the value for the 'Type_2' column
	 * 
	 * @return	String	with the value for the Type_2 column.
	 */
	public String getType_2() {
		return this.iType_2;
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
	 * This method sets the value for the 'Cofradicid' column
	 * 
	 * @param	aCofradicid	long with the value for the Cofradicid column.
	 */
	public void setCofradicid(long aCofradicid) {
		this.iCofradicid = aCofradicid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Type_2' column
	 * 
	 * @param	aType_2	String with the value for the Type_2 column.
	 */
	public void setType_2(String aType_2) {
		this.iType_2 = aType_2;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM cofradic WHERE cofradicid = ?");
		lStat.setLong(1, iCofradicid);
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
		if(!aKeys.containsKey(COFRADICID)) {
			throw new IllegalArgumentException("Primary key field 'COFRADICID' is missing in HashMap!");
		} else {
			iCofradicid = ((Long)aKeys.get(COFRADICID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM cofradic WHERE cofradicid = ?");
		lStat.setLong(1, iCofradicid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iCofradicid = lRS.getLong("cofradicid");
			iType_2 = (String)lRS.getObject("type_2");
			iDescription = (String)lRS.getObject("description");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'cofradic' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'cofradic' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE cofradic SET cofradicid = ?, type_2 = ?, description = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE cofradicid = ?");
		lStat.setLong(1, iCofradicid);
		lStat.setObject(2, iType_2);
		lStat.setObject(3, iDescription);
		lStat.setObject(4, iUsername);
		lStat.setObject(5, iCreationdate);
		lStat.setLong(6, iCofradicid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO cofradic (cofradicid, type_2, description, username, creationdate, modificationdate) values(?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iCofradicid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iCofradicid);
		}
		if(iType_2 == null) {
			lStat.setNull(2, 12);
		} else {
			lStat.setObject(2, iType_2);
		}
		if(iDescription == null) {
			lStat.setNull(3, -1);
		} else {
			lStat.setObject(3, iDescription);
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