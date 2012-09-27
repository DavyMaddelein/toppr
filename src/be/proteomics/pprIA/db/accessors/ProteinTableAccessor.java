/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 07/08/2008
 * Time: 09:45:27
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
 * This class is a generated accessor for the Protein table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class ProteinTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'proteinid' column.
	 */
	protected long iProteinid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'SPaccession' column.
	 */
	protected String iSpaccession = null;


	/**
	 * This variable represents the contents for the 'sequence' column.
	 */
	protected String iSequence = null;


	/**
	 * This variable represents the contents for the 'entry_name' column.
	 */
	protected String iEntry_name = null;


	/**
	 * This variable represents the contents for the 'name' column.
	 */
	protected String iName = null;


	/**
	 * This variable represents the contents for the 'sec_structure_prediction' column.
	 */
	protected String iSec_structure_prediction = null;


	/**
	 * This variable represents the contents for the 'sec_structure_swissprot' column.
	 */
	protected String iSec_structure_swissprot = null;


	/**
	 * This variable represents the contents for the 'info_found' column.
	 */
	protected int iInfo_found = Integer.MIN_VALUE;


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
	 * This variable represents the key for the 'proteinid' column.
	 */
	public static final String PROTEINID = "PROTEINID";

	/**
	 * This variable represents the key for the 'SPaccession' column.
	 */
	public static final String SPACCESSION = "SPACCESSION";

	/**
	 * This variable represents the key for the 'sequence' column.
	 */
	public static final String SEQUENCE = "SEQUENCE";

	/**
	 * This variable represents the key for the 'entry_name' column.
	 */
	public static final String ENTRY_NAME = "ENTRY_NAME";

	/**
	 * This variable represents the key for the 'name' column.
	 */
	public static final String NAME = "NAME";

	/**
	 * This variable represents the key for the 'sec_structure_prediction' column.
	 */
	public static final String SEC_STRUCTURE_PREDICTION = "SEC_STRUCTURE_PREDICTION";

	/**
	 * This variable represents the key for the 'sec_structure_swissprot' column.
	 */
	public static final String SEC_STRUCTURE_SWISSPROT = "SEC_STRUCTURE_SWISSPROT";

	/**
	 * This variable represents the key for the 'info_found' column.
	 */
	public static final String INFO_FOUND = "INFO_FOUND";

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
	public ProteinTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'ProteinTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public ProteinTableAccessor(HashMap aParams) {
		if(aParams.containsKey(PROTEINID)) {
			this.iProteinid = ((Long)aParams.get(PROTEINID)).longValue();
		}
		if(aParams.containsKey(SPACCESSION)) {
			this.iSpaccession = (String)aParams.get(SPACCESSION);
		}
		if(aParams.containsKey(SEQUENCE)) {
			this.iSequence = (String)aParams.get(SEQUENCE);
		}
		if(aParams.containsKey(ENTRY_NAME)) {
			this.iEntry_name = (String)aParams.get(ENTRY_NAME);
		}
		if(aParams.containsKey(NAME)) {
			this.iName = (String)aParams.get(NAME);
		}
		if(aParams.containsKey(SEC_STRUCTURE_PREDICTION)) {
			this.iSec_structure_prediction = (String)aParams.get(SEC_STRUCTURE_PREDICTION);
		}
		if(aParams.containsKey(SEC_STRUCTURE_SWISSPROT)) {
			this.iSec_structure_swissprot = (String)aParams.get(SEC_STRUCTURE_SWISSPROT);
		}
		if(aParams.containsKey(INFO_FOUND)) {
			this.iInfo_found = ((Integer)aParams.get(INFO_FOUND)).intValue();
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
	 * This constructor allows the creation of the 'ProteinTableAccessor' object based on a resultset
	 * obtained by a 'select * from Protein' query.
	 *
	 * @param	aResultSet	ResultSet with the required columns to initialize this object with.
	 * @exception	SQLException	when the ResultSet could not be read.
	 */
	public ProteinTableAccessor(ResultSet aResultSet) throws SQLException {
		this.iProteinid = aResultSet.getLong("proteinid");
		this.iSpaccession = (String)aResultSet.getObject("SPaccession");
		this.iSequence = (String)aResultSet.getObject("sequence");
		this.iEntry_name = (String)aResultSet.getObject("entry_name");
		this.iName = (String)aResultSet.getObject("name");
		this.iSec_structure_prediction = (String)aResultSet.getObject("sec_structure_prediction");
		this.iSec_structure_swissprot = (String)aResultSet.getObject("sec_structure_swissprot");
		this.iInfo_found = aResultSet.getInt("info_found");
		this.iUsername = (String)aResultSet.getObject("username");
		this.iCreationdate = (java.sql.Timestamp)aResultSet.getObject("creationdate");
		this.iModificationdate = (java.sql.Timestamp)aResultSet.getObject("modificationdate");

		this.iUpdated = true;
	}


	/**
	 * This method returns the value for the 'Proteinid' column
	 * 
	 * @return	long	with the value for the Proteinid column.
	 */
	public long getProteinid() {
		return this.iProteinid;
	}

	/**
	 * This method returns the value for the 'Spaccession' column
	 * 
	 * @return	String	with the value for the Spaccession column.
	 */
	public String getSpaccession() {
		return this.iSpaccession;
	}

	/**
	 * This method returns the value for the 'Sequence' column
	 * 
	 * @return	String	with the value for the Sequence column.
	 */
	public String getSequence() {
		return this.iSequence;
	}

	/**
	 * This method returns the value for the 'Entry_name' column
	 * 
	 * @return	String	with the value for the Entry_name column.
	 */
	public String getEntry_name() {
		return this.iEntry_name;
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
	 * This method returns the value for the 'Sec_structure_prediction' column
	 * 
	 * @return	String	with the value for the Sec_structure_prediction column.
	 */
	public String getSec_structure_prediction() {
		return this.iSec_structure_prediction;
	}

	/**
	 * This method returns the value for the 'Sec_structure_swissprot' column
	 * 
	 * @return	String	with the value for the Sec_structure_swissprot column.
	 */
	public String getSec_structure_swissprot() {
		return this.iSec_structure_swissprot;
	}

	/**
	 * This method returns the value for the 'Info_found' column
	 * 
	 * @return	int	with the value for the Info_found column.
	 */
	public int getInfo_found() {
		return this.iInfo_found;
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
	 * This method sets the value for the 'Proteinid' column
	 * 
	 * @param	aProteinid	long with the value for the Proteinid column.
	 */
	public void setProteinid(long aProteinid) {
		this.iProteinid = aProteinid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Spaccession' column
	 * 
	 * @param	aSpaccession	String with the value for the Spaccession column.
	 */
	public void setSpaccession(String aSpaccession) {
		this.iSpaccession = aSpaccession;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Sequence' column
	 * 
	 * @param	aSequence	String with the value for the Sequence column.
	 */
	public void setSequence(String aSequence) {
		this.iSequence = aSequence;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Entry_name' column
	 * 
	 * @param	aEntry_name	String with the value for the Entry_name column.
	 */
	public void setEntry_name(String aEntry_name) {
		this.iEntry_name = aEntry_name;
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
	 * This method sets the value for the 'Sec_structure_prediction' column
	 * 
	 * @param	aSec_structure_prediction	String with the value for the Sec_structure_prediction column.
	 */
	public void setSec_structure_prediction(String aSec_structure_prediction) {
		this.iSec_structure_prediction = aSec_structure_prediction;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Sec_structure_swissprot' column
	 * 
	 * @param	aSec_structure_swissprot	String with the value for the Sec_structure_swissprot column.
	 */
	public void setSec_structure_swissprot(String aSec_structure_swissprot) {
		this.iSec_structure_swissprot = aSec_structure_swissprot;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Info_found' column
	 * 
	 * @param	aInfo_found	int with the value for the Info_found column.
	 */
	public void setInfo_found(int aInfo_found) {
		this.iInfo_found = aInfo_found;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM protein WHERE proteinid = ?");
		lStat.setLong(1, iProteinid);
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
		if(!aKeys.containsKey(PROTEINID)) {
			throw new IllegalArgumentException("Primary key field 'PROTEINID' is missing in HashMap!");
		} else {
			iProteinid = ((Long)aKeys.get(PROTEINID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM protein WHERE proteinid = ?");
		lStat.setLong(1, iProteinid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iProteinid = lRS.getLong("proteinid");
			iSpaccession = (String)lRS.getObject("SPaccession");
			iSequence = (String)lRS.getObject("sequence");
			iEntry_name = (String)lRS.getObject("entry_name");
			iName = (String)lRS.getObject("name");
			iSec_structure_prediction = (String)lRS.getObject("sec_structure_prediction");
			iSec_structure_swissprot = (String)lRS.getObject("sec_structure_swissprot");
			iInfo_found = lRS.getInt("info_found");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'protein' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'protein' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE protein SET proteinid = ?, SPaccession = ?, sequence = ?, entry_name = ?, name = ?, sec_structure_prediction = ?, sec_structure_swissprot = ?, info_found = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE proteinid = ?");
		lStat.setLong(1, iProteinid);
		lStat.setObject(2, iSpaccession);
		lStat.setObject(3, iSequence);
		lStat.setObject(4, iEntry_name);
		lStat.setObject(5, iName);
		lStat.setObject(6, iSec_structure_prediction);
		lStat.setObject(7, iSec_structure_swissprot);
		lStat.setInt(8, iInfo_found);
		lStat.setObject(9, iUsername);
		lStat.setObject(10, iCreationdate);
		lStat.setLong(11, iProteinid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO protein (proteinid, SPaccession, sequence, entry_name, name, sec_structure_prediction, sec_structure_swissprot, info_found, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",PreparedStatement.RETURN_GENERATED_KEYS);
        if(iProteinid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iProteinid);
		}
		if(iSpaccession == null) {
			lStat.setNull(2, 12);
		} else {
			lStat.setObject(2, iSpaccession);
		}
		if(iSequence == null) {
			lStat.setNull(3, -1);
		} else {
			lStat.setObject(3, iSequence);
		}
		if(iEntry_name == null) {
			lStat.setNull(4, -1);
		} else {
			lStat.setObject(4, iEntry_name);
		}
		if(iName == null) {
			lStat.setNull(5, -1);
		} else {
			lStat.setObject(5, iName);
		}
		if(iSec_structure_prediction == null) {
			lStat.setNull(6, -1);
		} else {
			lStat.setObject(6, iSec_structure_prediction);
		}
		if(iSec_structure_swissprot == null) {
			lStat.setNull(7, -1);
		} else {
			lStat.setObject(7, iSec_structure_swissprot);
		}
		if(iInfo_found == Integer.MIN_VALUE) {
			lStat.setNull(8, -6);
		} else {
			lStat.setInt(8, iInfo_found);
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