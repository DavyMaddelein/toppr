/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 27/12/2007
 * Time: 10:42:37
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
 * This class is a generated accessor for the Project_to_usergroup table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class Project_to_usergroupTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'project_to_usergroupid' column.
	 */
	protected long iProject_to_usergroupid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_projectid' column.
	 */
	protected long iL_projectid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_usergroupid' column.
	 */
	protected long iL_usergroupid = Long.MIN_VALUE;


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
	 * This variable represents the key for the 'project_to_usergroupid' column.
	 */
	public static final String PROJECT_TO_USERGROUPID = "PROJECT_TO_USERGROUPID";

	/**
	 * This variable represents the key for the 'l_projectid' column.
	 */
	public static final String L_PROJECTID = "L_PROJECTID";

	/**
	 * This variable represents the key for the 'l_usergroupid' column.
	 */
	public static final String L_USERGROUPID = "L_USERGROUPID";

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
	public Project_to_usergroupTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'Project_to_usergroupTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public Project_to_usergroupTableAccessor(HashMap aParams) {
		if(aParams.containsKey(PROJECT_TO_USERGROUPID)) {
			this.iProject_to_usergroupid = ((Long)aParams.get(PROJECT_TO_USERGROUPID)).longValue();
		}
		if(aParams.containsKey(L_PROJECTID)) {
			this.iL_projectid = ((Long)aParams.get(L_PROJECTID)).longValue();
		}
		if(aParams.containsKey(L_USERGROUPID)) {
			this.iL_usergroupid = ((Long)aParams.get(L_USERGROUPID)).longValue();
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
	 * This method returns the value for the 'Project_to_usergroupid' column
	 * 
	 * @return	long	with the value for the Project_to_usergroupid column.
	 */
	public long getProject_to_usergroupid() {
		return this.iProject_to_usergroupid;
	}

	/**
	 * This method returns the value for the 'L_projectid' column
	 * 
	 * @return	long	with the value for the L_projectid column.
	 */
	public long getL_projectid() {
		return this.iL_projectid;
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
	 * This method sets the value for the 'Project_to_usergroupid' column
	 * 
	 * @param	aProject_to_usergroupid	long with the value for the Project_to_usergroupid column.
	 */
	public void setProject_to_usergroupid(long aProject_to_usergroupid) {
		this.iProject_to_usergroupid = aProject_to_usergroupid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'L_projectid' column
	 * 
	 * @param	aL_projectid	long with the value for the L_projectid column.
	 */
	public void setL_projectid(long aL_projectid) {
		this.iL_projectid = aL_projectid;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM project_to_usergroup WHERE project_to_usergroupid = ?");
		lStat.setLong(1, iProject_to_usergroupid);
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
		if(!aKeys.containsKey(PROJECT_TO_USERGROUPID)) {
			throw new IllegalArgumentException("Primary key field 'PROJECT_TO_USERGROUPID' is missing in HashMap!");
		} else {
			iProject_to_usergroupid = ((Long)aKeys.get(PROJECT_TO_USERGROUPID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM project_to_usergroup WHERE project_to_usergroupid = ?");
		lStat.setLong(1, iProject_to_usergroupid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iProject_to_usergroupid = lRS.getLong("project_to_usergroupid");
			iL_projectid = lRS.getLong("l_projectid");
			iL_usergroupid = lRS.getLong("l_usergroupid");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'project_to_usergroup' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'project_to_usergroup' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE project_to_usergroup SET project_to_usergroupid = ?, l_projectid = ?, l_usergroupid = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE project_to_usergroupid = ?");
		lStat.setLong(1, iProject_to_usergroupid);
		lStat.setLong(2, iL_projectid);
		lStat.setLong(3, iL_usergroupid);
		lStat.setObject(4, iUsername);
		lStat.setObject(5, iCreationdate);
		lStat.setLong(6, iProject_to_usergroupid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO project_to_usergroup (project_to_usergroupid, l_projectid, l_usergroupid, username, creationdate, modificationdate) values(?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iProject_to_usergroupid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iProject_to_usergroupid);
		}
		if(iL_projectid == Long.MIN_VALUE) {
			lStat.setNull(2, 4);
		} else {
			lStat.setLong(2, iL_projectid);
		}
		if(iL_usergroupid == Long.MIN_VALUE) {
			lStat.setNull(3, 4);
		} else {
			lStat.setLong(3, iL_usergroupid);
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