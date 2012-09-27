/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 16/10/2007
 * Time: 11:21:19
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
 * This class is a generated accessor for the Peptide_location table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class Peptide_locationTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'peptide_locationid' column.
	 */
	protected long iPeptide_locationid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_proteinid' column.
	 */
	protected long iL_proteinid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_groupid' column.
	 */
	protected long iL_groupid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'start' column.
	 */
	protected long iStart = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'end' column.
	 */
	protected long iEnd = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'location' column.
	 */
	protected long iLocation = Long.MIN_VALUE;


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
	 * This variable represents the key for the 'peptide_locationid' column.
	 */
	public static final String PEPTIDE_LOCATIONID = "PEPTIDE_LOCATIONID";

	/**
	 * This variable represents the key for the 'l_proteinid' column.
	 */
	public static final String L_PROTEINID = "L_PROTEINID";

	/**
	 * This variable represents the key for the 'l_groupid' column.
	 */
	public static final String L_GROUPID = "L_GROUPID";

	/**
	 * This variable represents the key for the 'start' column.
	 */
	public static final String START = "START";

	/**
	 * This variable represents the key for the 'end' column.
	 */
	public static final String END = "END";

	/**
	 * This variable represents the key for the 'location' column.
	 */
	public static final String LOCATION = "LOCATION";

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
	public Peptide_locationTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'Peptide_locationTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public Peptide_locationTableAccessor(HashMap aParams) {
		if(aParams.containsKey(PEPTIDE_LOCATIONID)) {
			this.iPeptide_locationid = ((Long)aParams.get(PEPTIDE_LOCATIONID)).longValue();
		}
		if(aParams.containsKey(L_PROTEINID)) {
			this.iL_proteinid = ((Long)aParams.get(L_PROTEINID)).longValue();
		}
		if(aParams.containsKey(L_GROUPID)) {
			this.iL_groupid = ((Long)aParams.get(L_GROUPID)).longValue();
		}
		if(aParams.containsKey(START)) {
			this.iStart = ((Long)aParams.get(START)).longValue();
		}
		if(aParams.containsKey(END)) {
			this.iEnd = ((Long)aParams.get(END)).longValue();
		}
		if(aParams.containsKey(LOCATION)) {
			this.iLocation = ((Long)aParams.get(LOCATION)).longValue();
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
	 * This constructor allows the creation of the 'Peptide_locationTableAccessor' object based on a resultset
	 * obtained by a 'select * from Peptide_location' query.
	 *
	 * @param	aResultSet	ResultSet with the required columns to initialize this object with.
	 * @exception	SQLException	when the ResultSet could not be read.
	 */
	public Peptide_locationTableAccessor(ResultSet aResultSet) throws SQLException {
		this.iPeptide_locationid = aResultSet.getLong("peptide_locationid");
		this.iL_proteinid = aResultSet.getLong("l_proteinid");
		this.iL_groupid = aResultSet.getLong("l_groupid");
		this.iStart = aResultSet.getLong("start");
		this.iEnd = aResultSet.getLong("end");
		this.iLocation = aResultSet.getLong("location");
		this.iUsername = (String)aResultSet.getObject("username");
		this.iCreationdate = (java.sql.Timestamp)aResultSet.getObject("creationdate");
		this.iModificationdate = (java.sql.Timestamp)aResultSet.getObject("modificationdate");

		this.iUpdated = true;
	}


	/**
	 * This method returns the value for the 'Peptide_locationid' column
	 *
	 * @return	long	with the value for the Peptide_locationid column.
	 */
	public long getPeptide_locationid() {
		return this.iPeptide_locationid;
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
	 * This method returns the value for the 'L_groupid' column
	 *
	 * @return	long	with the value for the L_groupid column.
	 */
	public long getL_groupid() {
		return this.iL_groupid;
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
	 * This method returns the value for the 'Location' column
	 *
	 * @return	long	with the value for the Location column.
	 */
	public long getLocation() {
		return this.iLocation;
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
	 * This method sets the value for the 'Peptide_locationid' column
	 *
	 * @param	aPeptide_locationid	long with the value for the Peptide_locationid column.
	 */
	public void setPeptide_locationid(long aPeptide_locationid) {
		this.iPeptide_locationid = aPeptide_locationid;
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
	 * This method sets the value for the 'L_groupid' column
	 *
	 * @param	aL_groupid	long with the value for the L_groupid column.
	 */
	public void setL_groupid(long aL_groupid) {
		this.iL_groupid = aL_groupid;
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
	 * This method sets the value for the 'Location' column
	 *
	 * @param	aLocation	long with the value for the Location column.
	 */
	public void setLocation(long aLocation) {
		this.iLocation = aLocation;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM peptide_location WHERE peptide_locationid = ?");
		lStat.setLong(1, iPeptide_locationid);
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
		if(!aKeys.containsKey(PEPTIDE_LOCATIONID)) {
			throw new IllegalArgumentException("Primary key field 'PEPTIDE_LOCATIONID' is missing in HashMap!");
		} else {
			iPeptide_locationid = ((Long)aKeys.get(PEPTIDE_LOCATIONID)).longValue();
		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM peptide_location WHERE peptide_locationid = ?");
		lStat.setLong(1, iPeptide_locationid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iPeptide_locationid = lRS.getLong("peptide_locationid");
			iL_proteinid = lRS.getLong("l_proteinid");
			iL_groupid = lRS.getLong("l_groupid");
			iStart = lRS.getLong("start");
			iEnd = lRS.getLong("end");
			iLocation = lRS.getLong("location");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'peptide_location' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'peptide_location' table! Object is not initialized correctly!");
		}
	}
	/**
	 * This method allows the caller to obtain a basic select for this table.
	 *
	 * @return   String with the basic select statement for this table.
	 */
	public static String getBasicSelect(){
		return "select * from peptide_location";
	}

	/**
	 * This method allows the caller to obtain all rows for this
	 * table from a persistent store.
	 *
	 * @param   aConn Connection to the persitent store.
	 * @return   ArrayList<Peptide_locationTableAccessor>   with all entries for this table.
	 */
	public static ArrayList<Peptide_locationTableAccessor> retrieveAllEntries(Connection aConn) throws SQLException {
		ArrayList<Peptide_locationTableAccessor>  entities = new ArrayList<Peptide_locationTableAccessor>();
		Statement stat = aConn.createStatement();
		ResultSet rs = stat.executeQuery(getBasicSelect());
		while(rs.next()) {
			entities.add(new Peptide_locationTableAccessor(rs));
		}
		rs.close();
		stat.close();
		return entities;
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE peptide_location SET peptide_locationid = ?, l_proteinid = ?, l_groupid = ?, start = ?, end = ?, location = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE peptide_locationid = ?");
		lStat.setLong(1, iPeptide_locationid);
		lStat.setLong(2, iL_proteinid);
		lStat.setLong(3, iL_groupid);
		lStat.setLong(4, iStart);
		lStat.setLong(5, iEnd);
		lStat.setLong(6, iLocation);
		lStat.setObject(7, iUsername);
		lStat.setObject(8, iCreationdate);
		lStat.setLong(9, iPeptide_locationid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO peptide_location (peptide_locationid, l_proteinid, l_groupid, start, end, location, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iPeptide_locationid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iPeptide_locationid);
		}
		if(iL_proteinid == Long.MIN_VALUE) {
			lStat.setNull(2, 4);
		} else {
			lStat.setLong(2, iL_proteinid);
		}
		if(iL_groupid == Long.MIN_VALUE) {
			lStat.setNull(3, 4);
		} else {
			lStat.setLong(3, iL_groupid);
		}
		if(iStart == Long.MIN_VALUE) {
			lStat.setNull(4, 4);
		} else {
			lStat.setLong(4, iStart);
		}
		if(iEnd == Long.MIN_VALUE) {
			lStat.setNull(5, 4);
		} else {
			lStat.setLong(5, iEnd);
		}
		if(iLocation == Long.MIN_VALUE) {
			lStat.setNull(6, 4);
		} else {
			lStat.setLong(6, iLocation);
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
		// Verify that we have a single, generated key.
		if(iKeys != null && iKeys.length == 1) {
			// Since we have exactly one key specified, and only
			// one Primary Key column, we can infer that this was the
			// generated column, and we can therefore initialize it here.
			iPeptide_locationid = ((Number) iKeys[0]).longValue();
		}
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