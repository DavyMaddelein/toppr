/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 15/02/2008
 * Time: 11:57:38
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
 * This class is a generated accessor for the Ms_lims_project table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class Ms_lims_projectTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'ms_lims_projectid' column.
	 */
	protected long iMs_lims_projectid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'ms_limstitle' column.
	 */
	protected String iMs_limstitle = null;


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
	 * This variable represents the key for the 'ms_lims_projectid' column.
	 */
	public static final String MS_LIMS_PROJECTID = "MS_LIMS_PROJECTID";

	/**
	 * This variable represents the key for the 'ms_limstitle' column.
	 */
	public static final String MS_LIMSTITLE = "MS_LIMSTITLE";

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
	public Ms_lims_projectTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'Ms_lims_projectTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public Ms_lims_projectTableAccessor(HashMap aParams) {
		if(aParams.containsKey(MS_LIMS_PROJECTID)) {
			this.iMs_lims_projectid = ((Long)aParams.get(MS_LIMS_PROJECTID)).longValue();
		}
		if(aParams.containsKey(MS_LIMSTITLE)) {
			this.iMs_limstitle = (String)aParams.get(MS_LIMSTITLE);
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
	 * This method returns the value for the 'Ms_lims_projectid' column
	 * 
	 * @return	long	with the value for the Ms_lims_projectid column.
	 */
	public long getMs_lims_projectid() {
		return this.iMs_lims_projectid;
	}

	/**
	 * This method returns the value for the 'Ms_limstitle' column
	 * 
	 * @return	String	with the value for the Ms_limstitle column.
	 */
	public String getMs_limstitle() {
		return this.iMs_limstitle;
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
	 * This method sets the value for the 'Ms_lims_projectid' column
	 * 
	 * @param	aMs_lims_projectid	long with the value for the Ms_lims_projectid column.
	 */
	public void setMs_lims_projectid(long aMs_lims_projectid) {
		this.iMs_lims_projectid = aMs_lims_projectid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Ms_limstitle' column
	 * 
	 * @param	aMs_limstitle	String with the value for the Ms_limstitle column.
	 */
	public void setMs_limstitle(String aMs_limstitle) {
		this.iMs_limstitle = aMs_limstitle;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM ms_lims_project WHERE ms_lims_projectid = ?");
		lStat.setLong(1, iMs_lims_projectid);
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
		if(!aKeys.containsKey(MS_LIMS_PROJECTID)) {
			throw new IllegalArgumentException("Primary key field 'MS_LIMS_PROJECTID' is missing in HashMap!");
		} else {
			iMs_lims_projectid = ((Long)aKeys.get(MS_LIMS_PROJECTID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM ms_lims_project WHERE ms_lims_projectid = ?");
		lStat.setLong(1, iMs_lims_projectid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iMs_lims_projectid = lRS.getLong("ms_lims_projectid");
			iMs_limstitle = (String)lRS.getObject("ms_limstitle");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'ms_lims_project' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'ms_lims_project' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE ms_lims_project SET ms_lims_projectid = ?, ms_limstitle = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE ms_lims_projectid = ?");
		lStat.setLong(1, iMs_lims_projectid);
		lStat.setObject(2, iMs_limstitle);
		lStat.setObject(3, iUsername);
		lStat.setObject(4, iCreationdate);
		lStat.setLong(5, iMs_lims_projectid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO ms_lims_project (ms_lims_projectid, ms_limstitle, username, creationdate, modificationdate) values(?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iMs_lims_projectid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iMs_lims_projectid);
		}
		if(iMs_limstitle == null) {
			lStat.setNull(2, 12);
		} else {
			lStat.setObject(2, iMs_limstitle);
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