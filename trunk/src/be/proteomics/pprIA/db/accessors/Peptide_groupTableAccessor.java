/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 19/05/2008
 * Time: 15:38:14
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
 * This class is a generated accessor for the Peptide_group table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class Peptide_groupTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'groupid' column.
	 */
	protected long iGroupid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_taxonomy' column.
	 */
	protected long iL_taxonomy = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'peptide_sequence' column.
	 */
	protected String iPeptide_sequence = null;


	/**
	 * This variable represents the contents for the 'mapped' column.
	 */
	protected boolean iMapped = false;


	/**
	 * This variable represents the contents for the 'old' column.
	 */
	protected boolean iOld = false;


	/**
	 * This variable represents the contents for the 'position' column.
	 */
	protected String iPosition = null;


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
	 * This variable represents the key for the 'groupid' column.
	 */
	public static final String GROUPID = "GROUPID";

	/**
	 * This variable represents the key for the 'l_taxonomy' column.
	 */
	public static final String L_TAXONOMY = "L_TAXONOMY";

	/**
	 * This variable represents the key for the 'peptide_sequence' column.
	 */
	public static final String PEPTIDE_SEQUENCE = "PEPTIDE_SEQUENCE";

	/**
	 * This variable represents the key for the 'mapped' column.
	 */
	public static final String MAPPED = "MAPPED";

	/**
	 * This variable represents the key for the 'old' column.
	 */
	public static final String OLD = "OLD";

	/**
	 * This variable represents the key for the 'position' column.
	 */
	public static final String POSITION = "POSITION";

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
	public Peptide_groupTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'Peptide_groupTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public Peptide_groupTableAccessor(HashMap aParams) {
		if(aParams.containsKey(GROUPID)) {
			this.iGroupid = ((Long)aParams.get(GROUPID)).longValue();
		}
		if(aParams.containsKey(L_TAXONOMY)) {
			this.iL_taxonomy = ((Long)aParams.get(L_TAXONOMY)).longValue();
		}
		if(aParams.containsKey(PEPTIDE_SEQUENCE)) {
			this.iPeptide_sequence = (String)aParams.get(PEPTIDE_SEQUENCE);
		}
		if(aParams.containsKey(MAPPED)) {
			this.iMapped = ((Boolean)aParams.get(MAPPED)).booleanValue();
		}
		if(aParams.containsKey(OLD)) {
			this.iOld = ((Boolean)aParams.get(OLD)).booleanValue();
		}
		if(aParams.containsKey(POSITION)) {
			this.iPosition = (String)aParams.get(POSITION);
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
	 * This constructor allows the creation of the 'Peptide_groupTableAccessor' object based on a resultset
	 * obtained by a 'select * from Peptide_group' query.
	 *
	 * @param	aResultSet	ResultSet with the required columns to initialize this object with.
	 * @exception	SQLException	when the ResultSet could not be read.
	 */
	public Peptide_groupTableAccessor(ResultSet aResultSet) throws SQLException {
		this.iGroupid = aResultSet.getLong("groupid");
		this.iL_taxonomy = aResultSet.getLong("l_taxonomy");
		this.iPeptide_sequence = (String)aResultSet.getObject("peptide_sequence");
		this.iMapped = aResultSet.getBoolean("mapped");
		this.iOld = aResultSet.getBoolean("old");
		this.iPosition = (String)aResultSet.getObject("position");
		this.iUsername = (String)aResultSet.getObject("username");
		this.iCreationdate = (java.sql.Timestamp)aResultSet.getObject("creationdate");
		this.iModificationdate = (java.sql.Timestamp)aResultSet.getObject("modificationdate");

		this.iUpdated = true;
	}


	/**
	 * This method returns the value for the 'Groupid' column
	 *
	 * @return	long	with the value for the Groupid column.
	 */
	public long getGroupid() {
		return this.iGroupid;
	}

	/**
	 * This method returns the value for the 'L_taxonomy' column
	 *
	 * @return	long	with the value for the L_taxonomy column.
	 */
	public long getL_taxonomy() {
		return this.iL_taxonomy;
	}

	/**
	 * This method returns the value for the 'Peptide_sequence' column
	 *
	 * @return	String	with the value for the Peptide_sequence column.
	 */
	public String getPeptide_sequence() {
		return this.iPeptide_sequence;
	}

	/**
	 * This method returns the value for the 'Mapped' column
	 *
	 * @return	boolean	with the value for the Mapped column.
	 */
	public boolean getMapped() {
		return this.iMapped;
	}

	/**
	 * This method returns the value for the 'Old' column
	 *
	 * @return	boolean	with the value for the Old column.
	 */
	public boolean getOld() {
		return this.iOld;
	}

	/**
	 * This method returns the value for the 'Position' column
	 *
	 * @return	String	with the value for the Position column.
	 */
	public String getPosition() {
		return this.iPosition;
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
	 * This method sets the value for the 'Groupid' column
	 *
	 * @param	aGroupid	long with the value for the Groupid column.
	 */
	public void setGroupid(long aGroupid) {
		this.iGroupid = aGroupid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'L_taxonomy' column
	 *
	 * @param	aL_taxonomy	long with the value for the L_taxonomy column.
	 */
	public void setL_taxonomy(long aL_taxonomy) {
		this.iL_taxonomy = aL_taxonomy;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Peptide_sequence' column
	 *
	 * @param	aPeptide_sequence	String with the value for the Peptide_sequence column.
	 */
	public void setPeptide_sequence(String aPeptide_sequence) {
		this.iPeptide_sequence = aPeptide_sequence;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Mapped' column
	 *
	 * @param	aMapped	boolean with the value for the Mapped column.
	 */
	public void setMapped(boolean aMapped) {
		this.iMapped = aMapped;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Old' column
	 *
	 * @param	aOld	boolean with the value for the Old column.
	 */
	public void setOld(boolean aOld) {
		this.iOld = aOld;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Position' column
	 *
	 * @param	aPosition	String with the value for the Position column.
	 */
	public void setPosition(String aPosition) {
		this.iPosition = aPosition;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM peptide_group WHERE groupid = ?");
		lStat.setLong(1, iGroupid);
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
		if(!aKeys.containsKey(GROUPID)) {
			throw new IllegalArgumentException("Primary key field 'GROUPID' is missing in HashMap!");
		} else {
			iGroupid = ((Long)aKeys.get(GROUPID)).longValue();
		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM peptide_group WHERE groupid = ?");
		lStat.setLong(1, iGroupid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iGroupid = lRS.getLong("groupid");
			iL_taxonomy = lRS.getLong("l_taxonomy");
			iPeptide_sequence = (String)lRS.getObject("peptide_sequence");
			iMapped = lRS.getBoolean("mapped");
			iOld = lRS.getBoolean("old");
			iPosition = (String)lRS.getObject("position");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'peptide_group' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'peptide_group' table! Object is not initialized correctly!");
		}
	}
	/**
	 * This method allows the caller to obtain a basic select for this table.
	 *
	 * @return   String with the basic select statement for this table.
	 */
	public static String getBasicSelect(){
		return "select * from peptide_group";
	}

	/**
	 * This method allows the caller to obtain all rows for this
	 * table from a persistent store.
	 *
	 * @param   aConn Connection to the persitent store.
	 * @return   ArrayList<Peptide_groupTableAccessor>   with all entries for this table.
	 */
	public static ArrayList<Peptide_groupTableAccessor> retrieveAllEntries(Connection aConn) throws SQLException {
		ArrayList<Peptide_groupTableAccessor>  entities = new ArrayList<Peptide_groupTableAccessor>();
		Statement stat = aConn.createStatement();
		ResultSet rs = stat.executeQuery(getBasicSelect());
		while(rs.next()) {
			entities.add(new Peptide_groupTableAccessor(rs));
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE peptide_group SET groupid = ?, l_taxonomy = ?, peptide_sequence = ?, mapped = ?, old = ?, position = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE groupid = ?");
		lStat.setLong(1, iGroupid);
		lStat.setLong(2, iL_taxonomy);
		lStat.setObject(3, iPeptide_sequence);
		lStat.setBoolean(4, iMapped);
		lStat.setBoolean(5, iOld);
		lStat.setObject(6, iPosition);
		lStat.setObject(7, iUsername);
		lStat.setObject(8, iCreationdate);
		lStat.setLong(9, iGroupid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO peptide_group (groupid, l_taxonomy, peptide_sequence, mapped, old, position, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iGroupid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iGroupid);
		}
		if(iL_taxonomy == Long.MIN_VALUE) {
			lStat.setNull(2, 4);
		} else {
			lStat.setLong(2, iL_taxonomy);
		}
		if(iPeptide_sequence == null) {
			lStat.setNull(3, 12);
		} else {
			lStat.setObject(3, iPeptide_sequence);
		}
		lStat.setBoolean(4, iMapped);
		lStat.setBoolean(5, iOld);
		if(iPosition == null) {
			lStat.setNull(6, 12);
		} else {
			lStat.setObject(6, iPosition);
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
			iGroupid = ((Number) iKeys[0]).longValue();
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