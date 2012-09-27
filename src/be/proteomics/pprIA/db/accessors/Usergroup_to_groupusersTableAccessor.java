/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 27/12/2007
 * Time: 10:43:12
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
 * This class is a generated accessor for the Usergroup_to_groupusers table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class Usergroup_to_groupusersTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'usergroup_to_groupusersid' column.
	 */
	protected long iUsergroup_to_groupusersid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_usergroupid' column.
	 */
	protected long iL_usergroupid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_groupusersid' column.
	 */
	protected long iL_groupusersid = Long.MIN_VALUE;


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
	 * This variable represents the key for the 'usergroup_to_groupusersid' column.
	 */
	public static final String USERGROUP_TO_GROUPUSERSID = "USERGROUP_TO_GROUPUSERSID";

	/**
	 * This variable represents the key for the 'l_usergroupid' column.
	 */
	public static final String L_USERGROUPID = "L_USERGROUPID";

	/**
	 * This variable represents the key for the 'l_groupusersid' column.
	 */
	public static final String L_GROUPUSERSID = "L_GROUPUSERSID";

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
	public Usergroup_to_groupusersTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'Usergroup_to_groupusersTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public Usergroup_to_groupusersTableAccessor(HashMap aParams) {
		if(aParams.containsKey(USERGROUP_TO_GROUPUSERSID)) {
			this.iUsergroup_to_groupusersid = ((Long)aParams.get(USERGROUP_TO_GROUPUSERSID)).longValue();
		}
		if(aParams.containsKey(L_USERGROUPID)) {
			this.iL_usergroupid = ((Long)aParams.get(L_USERGROUPID)).longValue();
		}
		if(aParams.containsKey(L_GROUPUSERSID)) {
			this.iL_groupusersid = ((Long)aParams.get(L_GROUPUSERSID)).longValue();
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
	 * This method returns the value for the 'Usergroup_to_groupusersid' column
	 * 
	 * @return	long	with the value for the Usergroup_to_groupusersid column.
	 */
	public long getUsergroup_to_groupusersid() {
		return this.iUsergroup_to_groupusersid;
	}

	/**
	 * This method returns the value for the 'L_usergroupid' column
	 * 
	 * @return	long	with the value for the L_usergroupid column.
	 */
	public long getL_usergroupid() {
		return this.iL_usergroupid;
	}

	/**
	 * This method returns the value for the 'L_groupusersid' column
	 * 
	 * @return	long	with the value for the L_groupusersid column.
	 */
	public long getL_groupusersid() {
		return this.iL_groupusersid;
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
	 * This method sets the value for the 'Usergroup_to_groupusersid' column
	 * 
	 * @param	aUsergroup_to_groupusersid	long with the value for the Usergroup_to_groupusersid column.
	 */
	public void setUsergroup_to_groupusersid(long aUsergroup_to_groupusersid) {
		this.iUsergroup_to_groupusersid = aUsergroup_to_groupusersid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'L_usergroupid' column
	 * 
	 * @param	aL_usergroupid	long with the value for the L_usergroupid column.
	 */
	public void setL_usergroupid(long aL_usergroupid) {
		this.iL_usergroupid = aL_usergroupid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'L_groupusersid' column
	 * 
	 * @param	aL_groupusersid	long with the value for the L_groupusersid column.
	 */
	public void setL_groupusersid(long aL_groupusersid) {
		this.iL_groupusersid = aL_groupusersid;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM usergroup_to_groupusers WHERE usergroup_to_groupusersid = ?");
		lStat.setLong(1, iUsergroup_to_groupusersid);
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
		if(!aKeys.containsKey(USERGROUP_TO_GROUPUSERSID)) {
			throw new IllegalArgumentException("Primary key field 'USERGROUP_TO_GROUPUSERSID' is missing in HashMap!");
		} else {
			iUsergroup_to_groupusersid = ((Long)aKeys.get(USERGROUP_TO_GROUPUSERSID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM usergroup_to_groupusers WHERE usergroup_to_groupusersid = ?");
		lStat.setLong(1, iUsergroup_to_groupusersid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iUsergroup_to_groupusersid = lRS.getLong("usergroup_to_groupusersid");
			iL_usergroupid = lRS.getLong("l_usergroupid");
			iL_groupusersid = lRS.getLong("l_groupusersid");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'usergroup_to_groupusers' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'usergroup_to_groupusers' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE usergroup_to_groupusers SET usergroup_to_groupusersid = ?, l_usergroupid = ?, l_groupusersid = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE usergroup_to_groupusersid = ?");
		lStat.setLong(1, iUsergroup_to_groupusersid);
		lStat.setLong(2, iL_usergroupid);
		lStat.setLong(3, iL_groupusersid);
		lStat.setObject(4, iUsername);
		lStat.setObject(5, iCreationdate);
		lStat.setLong(6, iUsergroup_to_groupusersid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO usergroup_to_groupusers (usergroup_to_groupusersid, l_usergroupid, l_groupusersid, username, creationdate, modificationdate) values(?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iUsergroup_to_groupusersid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iUsergroup_to_groupusersid);
		}
		if(iL_usergroupid == Long.MIN_VALUE) {
			lStat.setNull(2, 4);
		} else {
			lStat.setLong(2, iL_usergroupid);
		}
		if(iL_groupusersid == Long.MIN_VALUE) {
			lStat.setNull(3, 4);
		} else {
			lStat.setLong(3, iL_groupusersid);
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