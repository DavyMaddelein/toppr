/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 27/06/2008
 * Time: 08:24:07
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
 * This class is a generated accessor for the Domain table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class DomainTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'domainid' column.
	 */
	protected long iDomainid = Long.MIN_VALUE;


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
	 * This variable represents the contents for the 'domaintermid' column.
	 */
	protected String iDomaintermid = null;


	/**
	 * This variable represents the contents for the 'start' column.
	 */
	protected long iStart = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'end' column.
	 */
	protected long iEnd = Long.MIN_VALUE;


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
	 * This variable represents the key for the 'domainid' column.
	 */
	public static final String DOMAINID = "DOMAINID";

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
	 * This variable represents the key for the 'domaintermid' column.
	 */
	public static final String DOMAINTERMID = "DOMAINTERMID";

	/**
	 * This variable represents the key for the 'start' column.
	 */
	public static final String START = "START";

	/**
	 * This variable represents the key for the 'end' column.
	 */
	public static final String END = "END";

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
	public DomainTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'DomainTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public DomainTableAccessor(HashMap aParams) {
		if(aParams.containsKey(DOMAINID)) {
			this.iDomainid = ((Long)aParams.get(DOMAINID)).longValue();
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
		if(aParams.containsKey(DOMAINTERMID)) {
			this.iDomaintermid = (String)aParams.get(DOMAINTERMID);
		}
		if(aParams.containsKey(START)) {
			this.iStart = ((Long)aParams.get(START)).longValue();
		}
		if(aParams.containsKey(END)) {
			this.iEnd = ((Long)aParams.get(END)).longValue();
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
	 * This method returns the value for the 'Domainid' column
	 * 
	 * @return	long	with the value for the Domainid column.
	 */
	public long getDomainid() {
		return this.iDomainid;
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
	 * This method returns the value for the 'Domaintermid' column
	 * 
	 * @return	String	with the value for the Domaintermid column.
	 */
	public String getDomaintermid() {
		return this.iDomaintermid;
	}

	/**
	 * This method returns the value for the 'Start' column
	 * 
	 * @return	long	with the value for the Start column.
	 */
	public long getStart() {
		return this.iStart;
	}

	/**
	 * This method returns the value for the 'End' column
	 * 
	 * @return	long	with the value for the End column.
	 */
	public long getEnd() {
		return this.iEnd;
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
	 * This method sets the value for the 'Domainid' column
	 * 
	 * @param	aDomainid	long with the value for the Domainid column.
	 */
	public void setDomainid(long aDomainid) {
		this.iDomainid = aDomainid;
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
	 * This method sets the value for the 'Domaintermid' column
	 * 
	 * @param	aDomaintermid	String with the value for the Domaintermid column.
	 */
	public void setDomaintermid(String aDomaintermid) {
		this.iDomaintermid = aDomaintermid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Start' column
	 * 
	 * @param	aStart	long with the value for the Start column.
	 */
	public void setStart(long aStart) {
		this.iStart = aStart;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'End' column
	 * 
	 * @param	aEnd	long with the value for the End column.
	 */
	public void setEnd(long aEnd) {
		this.iEnd = aEnd;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM domain WHERE domainid = ?");
		lStat.setLong(1, iDomainid);
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
		if(!aKeys.containsKey(DOMAINID)) {
			throw new IllegalArgumentException("Primary key field 'DOMAINID' is missing in HashMap!");
		} else {
			iDomainid = ((Long)aKeys.get(DOMAINID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM domain WHERE domainid = ?");
		lStat.setLong(1, iDomainid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iDomainid = lRS.getLong("domainid");
			iL_proteinid = lRS.getLong("l_proteinid");
			iName = (String)lRS.getObject("name");
			iType = (String)lRS.getObject("type");
			iDomaintermid = (String)lRS.getObject("domaintermid");
			iStart = lRS.getLong("start");
			iEnd = lRS.getLong("end");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'domain' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'domain' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE domain SET domainid = ?, l_proteinid = ?, name = ?, type = ?, domaintermid = ?, start = ?, end = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE domainid = ?");
		lStat.setLong(1, iDomainid);
		lStat.setLong(2, iL_proteinid);
		lStat.setObject(3, iName);
		lStat.setObject(4, iType);
		lStat.setObject(5, iDomaintermid);
		lStat.setLong(6, iStart);
		lStat.setLong(7, iEnd);
		lStat.setObject(8, iUsername);
		lStat.setObject(9, iCreationdate);
		lStat.setLong(10, iDomainid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO domain (domainid, l_proteinid, name, type, domaintermid, start, end, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",PreparedStatement.RETURN_GENERATED_KEYS);
		if(iDomainid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iDomainid);
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
		if(iDomaintermid == null) {
			lStat.setNull(5, -1);
		} else {
			lStat.setObject(5, iDomaintermid);
		}
		if(iStart == Long.MIN_VALUE) {
			lStat.setNull(6, 4);
		} else {
			lStat.setLong(6, iStart);
		}
		if(iEnd == Long.MIN_VALUE) {
			lStat.setNull(7, 4);
		} else {
			lStat.setLong(7, iEnd);
		}
		int result = lStat.executeUpdate();

		// Retrieving the generated keys (if any).
		try{
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
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
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