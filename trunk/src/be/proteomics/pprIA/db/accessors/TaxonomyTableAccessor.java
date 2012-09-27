/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 22/10/2007
 * Time: 08:44:05
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
 * This class is a generated accessor for the Taxonomy table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class TaxonomyTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'taxonomyid' column.
	 */
	protected long iTaxonomyid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'species' column.
	 */
	protected String iSpecies = null;


	/**
	 * This variable represents the contents for the 'scientific_name' column.
	 */
	protected String iScientific_name = null;


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
	 * This variable represents the key for the 'taxonomyid' column.
	 */
	public static final String TAXONOMYID = "TAXONOMYID";

	/**
	 * This variable represents the key for the 'species' column.
	 */
	public static final String SPECIES = "SPECIES";

	/**
	 * This variable represents the key for the 'scientific_name' column.
	 */
	public static final String SCIENTIFIC_NAME = "SCIENTIFIC_NAME";

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
	public TaxonomyTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'TaxonomyTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public TaxonomyTableAccessor(HashMap aParams) {
		if(aParams.containsKey(TAXONOMYID)) {
			this.iTaxonomyid = ((Long)aParams.get(TAXONOMYID)).longValue();
		}
		if(aParams.containsKey(SPECIES)) {
			this.iSpecies = (String)aParams.get(SPECIES);
		}
		if(aParams.containsKey(SCIENTIFIC_NAME)) {
			this.iScientific_name = (String)aParams.get(SCIENTIFIC_NAME);
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
	 * This method returns the value for the 'Taxonomyid' column
	 * 
	 * @return	long	with the value for the Taxonomyid column.
	 */
	public long getTaxonomyid() {
		return this.iTaxonomyid;
	}

	/**
	 * This method returns the value for the 'Species' column
	 * 
	 * @return	String	with the value for the Species column.
	 */
	public String getSpecies() {
		return this.iSpecies;
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
	 * This method sets the value for the 'Taxonomyid' column
	 * 
	 * @param	aTaxonomyid	long with the value for the Taxonomyid column.
	 */
	public void setTaxonomyid(long aTaxonomyid) {
		this.iTaxonomyid = aTaxonomyid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Species' column
	 * 
	 * @param	aSpecies	String with the value for the Species column.
	 */
	public void setSpecies(String aSpecies) {
		this.iSpecies = aSpecies;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM taxonomy WHERE taxonomyid = ?");
		lStat.setLong(1, iTaxonomyid);
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
		if(!aKeys.containsKey(TAXONOMYID)) {
			throw new IllegalArgumentException("Primary key field 'TAXONOMYID' is missing in HashMap!");
		} else {
			iTaxonomyid = ((Long)aKeys.get(TAXONOMYID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM taxonomy WHERE taxonomyid = ?");
		lStat.setLong(1, iTaxonomyid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iTaxonomyid = lRS.getLong("taxonomyid");
			iSpecies = (String)lRS.getObject("species");
			iScientific_name = (String)lRS.getObject("scientific_name");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'taxonomy' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'taxonomy' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE taxonomy SET taxonomyid = ?, species = ?, scientific_name = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE taxonomyid = ?");
		lStat.setLong(1, iTaxonomyid);
		lStat.setObject(2, iSpecies);
		lStat.setObject(3, iScientific_name);
		lStat.setObject(4, iUsername);
		lStat.setObject(5, iCreationdate);
		lStat.setLong(6, iTaxonomyid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO taxonomy (taxonomyid, species, scientific_name, username, creationdate, modificationdate) values(?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iTaxonomyid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iTaxonomyid);
		}
		if(iSpecies == null) {
			lStat.setNull(2, -1);
		} else {
			lStat.setObject(2, iSpecies);
		}
		if(iScientific_name == null) {
			lStat.setNull(3, 12);
		} else {
			lStat.setObject(3, iScientific_name);
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