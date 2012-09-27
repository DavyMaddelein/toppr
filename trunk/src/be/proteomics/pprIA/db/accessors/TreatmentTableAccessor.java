/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 25/10/2010
 * Time: 08:06:01
 */
package com.compomics.ppr.db.accessors;

import java.sql.*;
import java.io.*;
import java.util.*;
import com.compomics.util.db.interfaces.*;

/*
 * CVS information:
 *
 * $Revision: 1.4 $
 * $Date: 2007/07/06 09:41:53 $
 */

/**
 * This class is a generated accessor for the Treatment table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class TreatmentTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'treatmentid' column.
	 */
	protected long iTreatmentid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'scientific_name' column.
	 */
	protected String iScientific_name = null;


	/**
	 * This variable represents the contents for the 'common_name' column.
	 */
	protected String iCommon_name = null;


	/**
	 * This variable represents the contents for the 'protease' column.
	 */
	protected String iProtease = null;


	/**
	 * This variable represents the contents for the 'recombinant' column.
	 */
	protected String iRecombinant = null;


	/**
	 * This variable represents the contents for the 'l_taxonomy' column.
	 */
	protected long iL_taxonomy = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'meropsid' column.
	 */
	protected String iMeropsid = null;


	/**
	 * This variable represents the contents for the 'pubmedid' column.
	 */
	protected String iPubmedid = null;


	/**
	 * This variable represents the contents for the 'source' column.
	 */
	protected String iSource = null;


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
	 * This variable represents the key for the 'treatmentid' column.
	 */
	public static final String TREATMENTID = "TREATMENTID";

	/**
	 * This variable represents the key for the 'scientific_name' column.
	 */
	public static final String SCIENTIFIC_NAME = "SCIENTIFIC_NAME";

	/**
	 * This variable represents the key for the 'common_name' column.
	 */
	public static final String COMMON_NAME = "COMMON_NAME";

	/**
	 * This variable represents the key for the 'protease' column.
	 */
	public static final String PROTEASE = "PROTEASE";

	/**
	 * This variable represents the key for the 'recombinant' column.
	 */
	public static final String RECOMBINANT = "RECOMBINANT";

	/**
	 * This variable represents the key for the 'l_taxonomy' column.
	 */
	public static final String L_TAXONOMY = "L_TAXONOMY";

	/**
	 * This variable represents the key for the 'meropsid' column.
	 */
	public static final String MEROPSID = "MEROPSID";

	/**
	 * This variable represents the key for the 'pubmedid' column.
	 */
	public static final String PUBMEDID = "PUBMEDID";

	/**
	 * This variable represents the key for the 'source' column.
	 */
	public static final String SOURCE = "SOURCE";

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
	public TreatmentTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'TreatmentTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public TreatmentTableAccessor(HashMap aParams) {
		if(aParams.containsKey(TREATMENTID)) {
			this.iTreatmentid = ((Long)aParams.get(TREATMENTID)).longValue();
		}
		if(aParams.containsKey(SCIENTIFIC_NAME)) {
			this.iScientific_name = (String)aParams.get(SCIENTIFIC_NAME);
		}
		if(aParams.containsKey(COMMON_NAME)) {
			this.iCommon_name = (String)aParams.get(COMMON_NAME);
		}
		if(aParams.containsKey(PROTEASE)) {
			this.iProtease = (String)aParams.get(PROTEASE);
		}
		if(aParams.containsKey(RECOMBINANT)) {
			this.iRecombinant = (String)aParams.get(RECOMBINANT);
		}
		if(aParams.containsKey(L_TAXONOMY)) {
			this.iL_taxonomy = ((Long)aParams.get(L_TAXONOMY)).longValue();
		}
		if(aParams.containsKey(MEROPSID)) {
			this.iMeropsid = (String)aParams.get(MEROPSID);
		}
		if(aParams.containsKey(PUBMEDID)) {
			this.iPubmedid = (String)aParams.get(PUBMEDID);
		}
		if(aParams.containsKey(SOURCE)) {
			this.iSource = (String)aParams.get(SOURCE);
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
	 * This constructor allows the creation of the 'TreatmentTableAccessor' object based on a resultset
	 * obtained by a 'select * from Treatment' query.
	 *
	 * @param	aResultSet	ResultSet with the required columns to initialize this object with.
	 * @exception	SQLException	when the ResultSet could not be read.
	 */
	public TreatmentTableAccessor(ResultSet aResultSet) throws SQLException {
		this.iTreatmentid = aResultSet.getLong("treatmentid");
		this.iScientific_name = (String)aResultSet.getObject("scientific_name");
		this.iCommon_name = (String)aResultSet.getObject("common_name");
		this.iProtease = (String)aResultSet.getObject("protease");
		this.iRecombinant = (String)aResultSet.getObject("recombinant");
		this.iL_taxonomy = aResultSet.getLong("l_taxonomy");
		this.iMeropsid = (String)aResultSet.getObject("meropsid");
		this.iPubmedid = (String)aResultSet.getObject("pubmedid");
		this.iSource = (String)aResultSet.getObject("source");
		this.iUsername = (String)aResultSet.getObject("username");
		this.iCreationdate = (java.sql.Timestamp)aResultSet.getObject("creationdate");
		this.iModificationdate = (java.sql.Timestamp)aResultSet.getObject("modificationdate");

		this.iUpdated = true;
	}


	/**
	 * This method returns the value for the 'Treatmentid' column
	 * 
	 * @return	long	with the value for the Treatmentid column.
	 */
	public long getTreatmentid() {
		return this.iTreatmentid;
	}

	/**
	 * This method returns the value for the 'Scientific_name' column
	 * 
	 * @return	String	with the value for the Scientific_name column.
	 */
	public String getScientific_name() {
		return this.iScientific_name;
	}

	/**
	 * This method returns the value for the 'Common_name' column
	 * 
	 * @return	String	with the value for the Common_name column.
	 */
	public String getCommon_name() {
		return this.iCommon_name;
	}

	/**
	 * This method returns the value for the 'Protease' column
	 * 
	 * @return	String	with the value for the Protease column.
	 */
	public String getProtease() {
		return this.iProtease;
	}

	/**
	 * This method returns the value for the 'Recombinant' column
	 * 
	 * @return	String	with the value for the Recombinant column.
	 */
	public String getRecombinant() {
		return this.iRecombinant;
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
	 * This method returns the value for the 'Meropsid' column
	 * 
	 * @return	String	with the value for the Meropsid column.
	 */
	public String getMeropsid() {
		return this.iMeropsid;
	}

	/**
	 * This method returns the value for the 'Pubmedid' column
	 * 
	 * @return	String	with the value for the Pubmedid column.
	 */
	public String getPubmedid() {
		return this.iPubmedid;
	}

	/**
	 * This method returns the value for the 'Source' column
	 * 
	 * @return	String	with the value for the Source column.
	 */
	public String getSource() {
		return this.iSource;
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
	 * This method sets the value for the 'Treatmentid' column
	 * 
	 * @param	aTreatmentid	long with the value for the Treatmentid column.
	 */
	public void setTreatmentid(long aTreatmentid) {
		this.iTreatmentid = aTreatmentid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Scientific_name' column
	 * 
	 * @param	aScientific_name	String with the value for the Scientific_name column.
	 */
	public void setScientific_name(String aScientific_name) {
		this.iScientific_name = aScientific_name;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Common_name' column
	 * 
	 * @param	aCommon_name	String with the value for the Common_name column.
	 */
	public void setCommon_name(String aCommon_name) {
		this.iCommon_name = aCommon_name;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Protease' column
	 * 
	 * @param	aProtease	String with the value for the Protease column.
	 */
	public void setProtease(String aProtease) {
		this.iProtease = aProtease;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Recombinant' column
	 * 
	 * @param	aRecombinant	String with the value for the Recombinant column.
	 */
	public void setRecombinant(String aRecombinant) {
		this.iRecombinant = aRecombinant;
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
	 * This method sets the value for the 'Meropsid' column
	 * 
	 * @param	aMeropsid	String with the value for the Meropsid column.
	 */
	public void setMeropsid(String aMeropsid) {
		this.iMeropsid = aMeropsid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Pubmedid' column
	 * 
	 * @param	aPubmedid	String with the value for the Pubmedid column.
	 */
	public void setPubmedid(String aPubmedid) {
		this.iPubmedid = aPubmedid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Source' column
	 * 
	 * @param	aSource	String with the value for the Source column.
	 */
	public void setSource(String aSource) {
		this.iSource = aSource;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM treatment WHERE treatmentid = ?");
		lStat.setLong(1, iTreatmentid);
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
		if(!aKeys.containsKey(TREATMENTID)) {
			throw new IllegalArgumentException("Primary key field 'TREATMENTID' is missing in HashMap!");
		} else {
			iTreatmentid = ((Long)aKeys.get(TREATMENTID)).longValue();
		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM treatment WHERE treatmentid = ?");
		lStat.setLong(1, iTreatmentid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iTreatmentid = lRS.getLong("treatmentid");
			iScientific_name = (String)lRS.getObject("scientific_name");
			iCommon_name = (String)lRS.getObject("common_name");
			iProtease = (String)lRS.getObject("protease");
			iRecombinant = (String)lRS.getObject("recombinant");
			iL_taxonomy = lRS.getLong("l_taxonomy");
			iMeropsid = (String)lRS.getObject("meropsid");
			iPubmedid = (String)lRS.getObject("pubmedid");
			iSource = (String)lRS.getObject("source");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'treatment' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'treatment' table! Object is not initialized correctly!");
		}
	}
	/**
	 * This method allows the caller to obtain a basic select for this table.
	 *
	 * @return   String with the basic select statement for this table.
	 */
	public static String getBasicSelect(){
		return "select * from treatment";
	}

	/**
	 * This method allows the caller to obtain all rows for this
	 * table from a persistent store.
	 *
	 * @param   aConn Connection to the persitent store.
	 * @return   ArrayList<TreatmentTableAccessor>   with all entries for this table.
	 */
	public static ArrayList<TreatmentTableAccessor> retrieveAllEntries(Connection aConn) throws SQLException {
		ArrayList<TreatmentTableAccessor>  entities = new ArrayList<TreatmentTableAccessor>();
		Statement stat = aConn.createStatement();
		ResultSet rs = stat.executeQuery(getBasicSelect());
		while(rs.next()) {
			entities.add(new TreatmentTableAccessor(rs));
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE treatment SET treatmentid = ?, scientific_name = ?, common_name = ?, protease = ?, recombinant = ?, l_taxonomy = ?, meropsid = ?, pubmedid = ?, source = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE treatmentid = ?");
		lStat.setLong(1, iTreatmentid);
		lStat.setObject(2, iScientific_name);
		lStat.setObject(3, iCommon_name);
		lStat.setObject(4, iProtease);
		lStat.setObject(5, iRecombinant);
		lStat.setLong(6, iL_taxonomy);
		lStat.setObject(7, iMeropsid);
		lStat.setObject(8, iPubmedid);
		lStat.setObject(9, iSource);
		lStat.setObject(10, iUsername);
		lStat.setObject(11, iCreationdate);
		lStat.setLong(12, iTreatmentid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO treatment (treatmentid, scientific_name, common_name, protease, recombinant, l_taxonomy, meropsid, pubmedid, source, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iTreatmentid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iTreatmentid);
		}
		if(iScientific_name == null) {
			lStat.setNull(2, 12);
		} else {
			lStat.setObject(2, iScientific_name);
		}
		if(iCommon_name == null) {
			lStat.setNull(3, 12);
		} else {
			lStat.setObject(3, iCommon_name);
		}
		if(iProtease == null) {
			lStat.setNull(4, 1);
		} else {
			lStat.setObject(4, iProtease);
		}
		if(iRecombinant == null) {
			lStat.setNull(5, 1);
		} else {
			lStat.setObject(5, iRecombinant);
		}
		if(iL_taxonomy == Long.MIN_VALUE) {
			lStat.setNull(6, 4);
		} else {
			lStat.setLong(6, iL_taxonomy);
		}
		if(iMeropsid == null) {
			lStat.setNull(7, 12);
		} else {
			lStat.setObject(7, iMeropsid);
		}
		if(iPubmedid == null) {
			lStat.setNull(8, -1);
		} else {
			lStat.setObject(8, iPubmedid);
		}
		if(iSource == null) {
			lStat.setNull(9, -1);
		} else {
			lStat.setObject(9, iSource);
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
		if(iKeys != null && iKeys.length == 1 && iKeys[0] != null) {
			// Since we have exactly one key specified, and only
			// one Primary Key column, we can infer that this was the
			// generated column, and we can therefore initialize it here.
			iTreatmentid = ((Number) iKeys[0]).longValue();
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