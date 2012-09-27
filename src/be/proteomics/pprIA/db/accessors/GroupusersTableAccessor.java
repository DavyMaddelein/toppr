/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 27/12/2007
 * Time: 10:42:09
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
 * This class is a generated accessor for the Groupusers table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class GroupusersTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'groupusersid' column.
	 */
	protected long iGroupusersid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'groupusersname' column.
	 */
	protected String iGroupusersname = null;


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
	 * This variable represents the key for the 'groupusersid' column.
	 */
	public static final String GROUPUSERSID = "GROUPUSERSID";

	/**
	 * This variable represents the key for the 'groupusersname' column.
	 */
	public static final String GROUPUSERSNAME = "GROUPUSERSNAME";

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
	public GroupusersTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'GroupusersTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public GroupusersTableAccessor(HashMap aParams) {
		if(aParams.containsKey(GROUPUSERSID)) {
			this.iGroupusersid = ((Long)aParams.get(GROUPUSERSID)).longValue();
		}
		if(aParams.containsKey(GROUPUSERSNAME)) {
			this.iGroupusersname = (String)aParams.get(GROUPUSERSNAME);
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
	 * This method returns the value for the 'Groupusersid' column
	 * 
	 * @return	long	with the value for the Groupusersid column.
	 */
	public long getGroupusersid() {
		return this.iGroupusersid;
	}

	/**
	 * This method returns the value for the 'Groupusersname' column
	 * 
	 * @return	String	with the value for the Groupusersname column.
	 */
	public String getGroupusersname() {
		return this.iGroupusersname;
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
	 * This method sets the value for the 'Groupusersid' column
	 * 
	 * @param	aGroupusersid	long with the value for the Groupusersid column.
	 */
	public void setGroupusersid(long aGroupusersid) {
		this.iGroupusersid = aGroupusersid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Groupusersname' column
	 * 
	 * @param	aGroupusersname	String with the value for the Groupusersname column.
	 */
	public void setGroupusersname(String aGroupusersname) {
		this.iGroupusersname = aGroupusersname;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM groupusers WHERE groupusersid = ?");
		lStat.setLong(1, iGroupusersid);
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
		if(!aKeys.containsKey(GROUPUSERSID)) {
			throw new IllegalArgumentException("Primary key field 'GROUPUSERSID' is missing in HashMap!");
		} else {
			iGroupusersid = ((Long)aKeys.get(GROUPUSERSID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM groupusers WHERE groupusersid = ?");
		lStat.setLong(1, iGroupusersid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iGroupusersid = lRS.getLong("groupusersid");
			iGroupusersname = (String)lRS.getObject("groupusersname");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'groupusers' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'groupusers' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE groupusers SET groupusersid = ?, groupusersname = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE groupusersid = ?");
		lStat.setLong(1, iGroupusersid);
		lStat.setObject(2, iGroupusersname);
		lStat.setObject(3, iUsername);
		lStat.setObject(4, iCreationdate);
		lStat.setLong(5, iGroupusersid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO groupusers (groupusersid, groupusersname, username, creationdate, modificationdate) values(?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iGroupusersid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iGroupusersid);
		}
		if(iGroupusersname == null) {
			lStat.setNull(2, 12);
		} else {
			lStat.setObject(2, iGroupusersname);
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