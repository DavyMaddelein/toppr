/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 27/06/2008
 * Time: 08:23:57
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
 * This class is a generated accessor for the Go table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class GoTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'goid' column.
	 */
	protected long iGoid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_proteinid' column.
	 */
	protected long iL_proteinid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'name' column.
	 */
	protected String iName = null;


	/**
	 * This variable represents the contents for the 'type' column.
	 */
	protected String iType = null;


	/**
	 * This variable represents the contents for the 'gotermid' column.
	 */
	protected String iGotermid = null;


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
	 * This variable represents the key for the 'goid' column.
	 */
	public static final String GOID = "GOID";

	/**
	 * This variable represents the key for the 'l_proteinid' column.
	 */
	public static final String L_PROTEINID = "L_PROTEINID";

	/**
	 * This variable represents the key for the 'name' column.
	 */
	public static final String NAME = "NAME";

	/**
	 * This variable represents the key for the 'type' column.
	 */
	public static final String TYPE = "TYPE";

	/**
	 * This variable represents the key for the 'gotermid' column.
	 */
	public static final String GOTERMID = "GOTERMID";

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
	public GoTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'GoTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public GoTableAccessor(HashMap aParams) {
		if(aParams.containsKey(GOID)) {
			this.iGoid = ((Long)aParams.get(GOID)).longValue();
		}
		if(aParams.containsKey(L_PROTEINID)) {
			this.iL_proteinid = ((Long)aParams.get(L_PROTEINID)).longValue();
		}
		if(aParams.containsKey(NAME)) {
			this.iName = (String)aParams.get(NAME);
		}
		if(aParams.containsKey(TYPE)) {
			this.iType = (String)aParams.get(TYPE);
		}
		if(aParams.containsKey(GOTERMID)) {
			this.iGotermid = (String)aParams.get(GOTERMID);
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
	 * This method returns the value for the 'Goid' column
	 * 
	 * @return	long	with the value for the Goid column.
	 */
	public long getGoid() {
		return this.iGoid;
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
	 * This method returns the value for the 'Name' column
	 * 
	 * @return	String	with the value for the Name column.
	 */
	public String getName() {
		return this.iName;
	}

	/**
	 * This method returns the value for the 'Type' column
	 * 
	 * @return	String	with the value for the Type column.
	 */
	public String getType() {
		return this.iType;
	}

	/**
	 * This method returns the value for the 'Gotermid' column
	 * 
	 * @return	String	with the value for the Gotermid column.
	 */
	public String getGotermid() {
		return this.iGotermid;
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
	 * This method sets the value for the 'Goid' column
	 * 
	 * @param	aGoid	long with the value for the Goid column.
	 */
	public void setGoid(long aGoid) {
		this.iGoid = aGoid;
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
	 * This method sets the value for the 'Name' column
	 * 
	 * @param	aName	String with the value for the Name column.
	 */
	public void setName(String aName) {
		this.iName = aName;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Type' column
	 * 
	 * @param	aType	String with the value for the Type column.
	 */
	public void setType(String aType) {
		this.iType = aType;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Gotermid' column
	 * 
	 * @param	aGotermid	String with the value for the Gotermid column.
	 */
	public void setGotermid(String aGotermid) {
		this.iGotermid = aGotermid;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM go WHERE goid = ?");
		lStat.setLong(1, iGoid);
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
		if(!aKeys.containsKey(GOID)) {
			throw new IllegalArgumentException("Primary key field 'GOID' is missing in HashMap!");
		} else {
			iGoid = ((Long)aKeys.get(GOID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM go WHERE goid = ?");
		lStat.setLong(1, iGoid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iGoid = lRS.getLong("goid");
			iL_proteinid = lRS.getLong("l_proteinid");
			iName = (String)lRS.getObject("name");
			iType = (String)lRS.getObject("type");
			iGotermid = (String)lRS.getObject("gotermid");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'go' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'go' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE go SET goid = ?, l_proteinid = ?, name = ?, type = ?, gotermid = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE goid = ?");
		lStat.setLong(1, iGoid);
		lStat.setLong(2, iL_proteinid);
		lStat.setObject(3, iName);
		lStat.setObject(4, iType);
		lStat.setObject(5, iGotermid);
		lStat.setObject(6, iUsername);
		lStat.setObject(7, iCreationdate);
		lStat.setLong(8, iGoid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO go (goid, l_proteinid, name, type, gotermid, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",Statement.RETURN_GENERATED_KEYS);
		if(iGoid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iGoid);
		}
		if(iL_proteinid == Long.MIN_VALUE) {
			lStat.setNull(2, 4);
		} else {
			lStat.setLong(2, iL_proteinid);
		}
		if(iName == null) {
			lStat.setNull(3, -1);
		} else {
			lStat.setObject(3, iName);
		}
		if(iType == null) {
			lStat.setNull(4, 12);
		} else {
			lStat.setObject(4, iType);
		}
		if(iGotermid == null) {
			lStat.setNull(5, -1);
		} else {
			lStat.setObject(5, iGotermid);
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