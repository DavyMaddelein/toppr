/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 27/12/2007
 * Time: 10:41:46
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
 * This class is a generated accessor for the Usergroup table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class UsergroupTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'usergroupid' column.
	 */
	protected long iUsergroupid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'usergroupname' column.
	 */
	protected String iUsergroupname = null;


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
	 * This variable represents the key for the 'usergroupid' column.
	 */
	public static final String USERGROUPID = "USERGROUPID";

	/**
	 * This variable represents the key for the 'usergroupname' column.
	 */
	public static final String USERGROUPNAME = "USERGROUPNAME";

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
	public UsergroupTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'UsergroupTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public UsergroupTableAccessor(HashMap aParams) {
		if(aParams.containsKey(USERGROUPID)) {
			this.iUsergroupid = ((Long)aParams.get(USERGROUPID)).longValue();
		}
		if(aParams.containsKey(USERGROUPNAME)) {
			this.iUsergroupname = (String)aParams.get(USERGROUPNAME);
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
	 * This method returns the value for the 'Usergroupid' column
	 * 
	 * @return	long	with the value for the Usergroupid column.
	 */
	public long getUsergroupid() {
		return this.iUsergroupid;
	}

	/**
	 * This method returns the value for the 'Usergroupname' column
	 * 
	 * @return	String	with the value for the Usergroupname column.
	 */
	public String getUsergroupname() {
		return this.iUsergroupname;
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
	 * This method sets the value for the 'Usergroupid' column
	 * 
	 * @param	aUsergroupid	long with the value for the Usergroupid column.
	 */
	public void setUsergroupid(long aUsergroupid) {
		this.iUsergroupid = aUsergroupid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Usergroupname' column
	 * 
	 * @param	aUsergroupname	String with the value for the Usergroupname column.
	 */
	public void setUsergroupname(String aUsergroupname) {
		this.iUsergroupname = aUsergroupname;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM usergroup WHERE usergroupid = ?");
		lStat.setLong(1, iUsergroupid);
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
		if(!aKeys.containsKey(USERGROUPID)) {
			throw new IllegalArgumentException("Primary key field 'USERGROUPID' is missing in HashMap!");
		} else {
			iUsergroupid = ((Long)aKeys.get(USERGROUPID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM usergroup WHERE usergroupid = ?");
		lStat.setLong(1, iUsergroupid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iUsergroupid = lRS.getLong("usergroupid");
			iUsergroupname = (String)lRS.getObject("usergroupname");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'usergroup' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'usergroup' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE usergroup SET usergroupid = ?, usergroupname = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE usergroupid = ?");
		lStat.setLong(1, iUsergroupid);
		lStat.setObject(2, iUsergroupname);
		lStat.setObject(3, iUsername);
		lStat.setObject(4, iCreationdate);
		lStat.setLong(5, iUsergroupid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO usergroup (usergroupid, usergroupname, username, creationdate, modificationdate) values(?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iUsergroupid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iUsergroupid);
		}
		if(iUsergroupname == null) {
			lStat.setNull(2, 12);
		} else {
			lStat.setObject(2, iUsergroupname);
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