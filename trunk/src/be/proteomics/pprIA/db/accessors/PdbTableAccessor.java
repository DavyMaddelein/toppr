/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 27/06/2008
 * Time: 08:23:43
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
 * This class is a generated accessor for the Pdb table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class PdbTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'pdbid' column.
	 */
	protected long iPdbid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_proteinid' column.
	 */
	protected long iL_proteinid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'pdbaccession' column.
	 */
	protected String iPdbaccession = null;


	/**
	 * This variable represents the contents for the 'title' column.
	 */
	protected String iTitle = null;


	/**
	 * This variable represents the contents for the 'experiment_type' column.
	 */
	protected String iExperiment_type = null;


	/**
	 * This variable represents the contents for the 'resolution' column.
	 */
	protected String iResolution = null;


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
	 * This variable represents the key for the 'pdbid' column.
	 */
	public static final String PDBID = "PDBID";

	/**
	 * This variable represents the key for the 'l_proteinid' column.
	 */
	public static final String L_PROTEINID = "L_PROTEINID";

	/**
	 * This variable represents the key for the 'pdbaccession' column.
	 */
	public static final String PDBACCESSION = "PDBACCESSION";

	/**
	 * This variable represents the key for the 'title' column.
	 */
	public static final String TITLE = "TITLE";

	/**
	 * This variable represents the key for the 'experiment_type' column.
	 */
	public static final String EXPERIMENT_TYPE = "EXPERIMENT_TYPE";

	/**
	 * This variable represents the key for the 'resolution' column.
	 */
	public static final String RESOLUTION = "RESOLUTION";

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
	public PdbTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'PdbTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public PdbTableAccessor(HashMap aParams) {
		if(aParams.containsKey(PDBID)) {
			this.iPdbid = ((Long)aParams.get(PDBID)).longValue();
		}
		if(aParams.containsKey(L_PROTEINID)) {
			this.iL_proteinid = ((Long)aParams.get(L_PROTEINID)).longValue();
		}
		if(aParams.containsKey(PDBACCESSION)) {
			this.iPdbaccession = (String)aParams.get(PDBACCESSION);
		}
		if(aParams.containsKey(TITLE)) {
			this.iTitle = (String)aParams.get(TITLE);
		}
		if(aParams.containsKey(EXPERIMENT_TYPE)) {
			this.iExperiment_type = (String)aParams.get(EXPERIMENT_TYPE);
		}
		if(aParams.containsKey(RESOLUTION)) {
			this.iResolution = (String)aParams.get(RESOLUTION);
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
	 * This method returns the value for the 'Pdbid' column
	 * 
	 * @return	long	with the value for the Pdbid column.
	 */
	public long getPdbid() {
		return this.iPdbid;
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
	 * This method returns the value for the 'Pdbaccession' column
	 * 
	 * @return	String	with the value for the Pdbaccession column.
	 */
	public String getPdbaccession() {
		return this.iPdbaccession;
	}

	/**
	 * This method returns the value for the 'Title' column
	 * 
	 * @return	String	with the value for the Title column.
	 */
	public String getTitle() {
		return this.iTitle;
	}

	/**
	 * This method returns the value for the 'Experiment_type' column
	 * 
	 * @return	String	with the value for the Experiment_type column.
	 */
	public String getExperiment_type() {
		return this.iExperiment_type;
	}

	/**
	 * This method returns the value for the 'Resolution' column
	 * 
	 * @return	String	with the value for the Resolution column.
	 */
	public String getResolution() {
		return this.iResolution;
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
	 * This method sets the value for the 'Pdbid' column
	 * 
	 * @param	aPdbid	long with the value for the Pdbid column.
	 */
	public void setPdbid(long aPdbid) {
		this.iPdbid = aPdbid;
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
	 * This method sets the value for the 'Pdbaccession' column
	 * 
	 * @param	aPdbaccession	String with the value for the Pdbaccession column.
	 */
	public void setPdbaccession(String aPdbaccession) {
		this.iPdbaccession = aPdbaccession;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Title' column
	 * 
	 * @param	aTitle	String with the value for the Title column.
	 */
	public void setTitle(String aTitle) {
		this.iTitle = aTitle;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Experiment_type' column
	 * 
	 * @param	aExperiment_type	String with the value for the Experiment_type column.
	 */
	public void setExperiment_type(String aExperiment_type) {
		this.iExperiment_type = aExperiment_type;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Resolution' column
	 * 
	 * @param	aResolution	String with the value for the Resolution column.
	 */
	public void setResolution(String aResolution) {
		this.iResolution = aResolution;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM pdb WHERE pdbid = ?");
		lStat.setLong(1, iPdbid);
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
		if(!aKeys.containsKey(PDBID)) {
			throw new IllegalArgumentException("Primary key field 'PDBID' is missing in HashMap!");
		} else {
			iPdbid = ((Long)aKeys.get(PDBID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM pdb WHERE pdbid = ?");
		lStat.setLong(1, iPdbid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iPdbid = lRS.getLong("pdbid");
			iL_proteinid = lRS.getLong("l_proteinid");
			iPdbaccession = (String)lRS.getObject("pdbaccession");
			iTitle = (String)lRS.getObject("title");
			iExperiment_type = (String)lRS.getObject("experiment_type");
			iResolution = (String)lRS.getObject("resolution");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'pdb' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'pdb' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE pdb SET pdbid = ?, l_proteinid = ?, pdbaccession = ?, title = ?, experiment_type = ?, resolution = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE pdbid = ?");
		lStat.setLong(1, iPdbid);
		lStat.setLong(2, iL_proteinid);
		lStat.setObject(3, iPdbaccession);
		lStat.setObject(4, iTitle);
		lStat.setObject(5, iExperiment_type);
		lStat.setObject(6, iResolution);
		lStat.setObject(7, iUsername);
		lStat.setObject(8, iCreationdate);
		lStat.setLong(9, iPdbid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO pdb (pdbid, l_proteinid, pdbaccession, title, experiment_type, resolution, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",Statement.RETURN_GENERATED_KEYS);
		if(iPdbid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iPdbid);
		}
		if(iL_proteinid == Long.MIN_VALUE) {
			lStat.setNull(2, 4);
		} else {
			lStat.setLong(2, iL_proteinid);
		}
		if(iPdbaccession == null) {
			lStat.setNull(3, 12);
		} else {
			lStat.setObject(3, iPdbaccession);
		}
		if(iTitle == null) {
			lStat.setNull(4, -1);
		} else {
			lStat.setObject(4, iTitle);
		}
		if(iExperiment_type == null) {
			lStat.setNull(5, 12);
		} else {
			lStat.setObject(5, iExperiment_type);
		}
		if(iResolution == null) {
			lStat.setNull(6, 12);
		} else {
			lStat.setObject(6, iResolution);
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