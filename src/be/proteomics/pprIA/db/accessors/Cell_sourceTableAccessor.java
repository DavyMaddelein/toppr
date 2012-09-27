/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 16/10/2007
 * Time: 08:53:07
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
 * This class is a generated accessor for the Cell_source table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class Cell_sourceTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'cell_sourceid' column.
	 */
	protected long iCell_sourceid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'name' column.
	 */
	protected String iName = null;


	/**
	 * This variable represents the contents for the 'l_origin' column.
	 */
	protected long iL_origin = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_taxonomy' column.
	 */
	protected long iL_taxonomy = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'organ' column.
	 */
	protected String iOrgan = null;


	/**
	 * This variable represents the contents for the 'celltype' column.
	 */
	protected String iCelltype = null;


	/**
	 * This variable represents the contents for the 'subcellular_location' column.
	 */
	protected String iSubcellular_location = null;


	/**
	 * This variable represents the contents for the 'developmental_stage' column.
	 */
	protected String iDevelopmental_stage = null;


	/**
	 * This variable represents the contents for the 'sex' column.
	 */
	protected String iSex = null;


	/**
	 * This variable represents the contents for the 'disease_state' column.
	 */
	protected String iDisease_state = null;


	/**
	 * This variable represents the contents for the 'permanent_transfection' column.
	 */
	protected String iPermanent_transfection = null;


	/**
	 * This variable represents the contents for the 'source' column.
	 */
	protected String iSource = null;


	/**
	 * This variable represents the contents for the 'pre_treatment' column.
	 */
	protected String iPre_treatment = null;


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
	 * This variable represents the key for the 'cell_sourceid' column.
	 */
	public static final String CELL_SOURCEID = "CELL_SOURCEID";

	/**
	 * This variable represents the key for the 'name' column.
	 */
	public static final String NAME = "NAME";

	/**
	 * This variable represents the key for the 'l_origin' column.
	 */
	public static final String L_ORIGIN = "L_ORIGIN";

	/**
	 * This variable represents the key for the 'l_taxonomy' column.
	 */
	public static final String L_TAXONOMY = "L_TAXONOMY";

	/**
	 * This variable represents the key for the 'organ' column.
	 */
	public static final String ORGAN = "ORGAN";

	/**
	 * This variable represents the key for the 'celltype' column.
	 */
	public static final String CELLTYPE = "CELLTYPE";

	/**
	 * This variable represents the key for the 'subcellular_location' column.
	 */
	public static final String SUBCELLULAR_LOCATION = "SUBCELLULAR_LOCATION";

	/**
	 * This variable represents the key for the 'developmental_stage' column.
	 */
	public static final String DEVELOPMENTAL_STAGE = "DEVELOPMENTAL_STAGE";

	/**
	 * This variable represents the key for the 'sex' column.
	 */
	public static final String SEX = "SEX";

	/**
	 * This variable represents the key for the 'disease_state' column.
	 */
	public static final String DISEASE_STATE = "DISEASE_STATE";

	/**
	 * This variable represents the key for the 'permanent_transfection' column.
	 */
	public static final String PERMANENT_TRANSFECTION = "PERMANENT_TRANSFECTION";

	/**
	 * This variable represents the key for the 'source' column.
	 */
	public static final String SOURCE = "SOURCE";

	/**
	 * This variable represents the key for the 'pre_treatment' column.
	 */
	public static final String PRE_TREATMENT = "PRE_TREATMENT";

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
	public Cell_sourceTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'Cell_sourceTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public Cell_sourceTableAccessor(HashMap aParams) {
		if(aParams.containsKey(CELL_SOURCEID)) {
			this.iCell_sourceid = ((Long)aParams.get(CELL_SOURCEID)).longValue();
		}
		if(aParams.containsKey(NAME)) {
			this.iName = (String)aParams.get(NAME);
		}
		if(aParams.containsKey(L_ORIGIN)) {
			this.iL_origin = ((Long)aParams.get(L_ORIGIN)).longValue();
		}
		if(aParams.containsKey(L_TAXONOMY)) {
			this.iL_taxonomy = ((Long)aParams.get(L_TAXONOMY)).longValue();
		}
		if(aParams.containsKey(ORGAN)) {
			this.iOrgan = (String)aParams.get(ORGAN);
		}
		if(aParams.containsKey(CELLTYPE)) {
			this.iCelltype = (String)aParams.get(CELLTYPE);
		}
		if(aParams.containsKey(SUBCELLULAR_LOCATION)) {
			this.iSubcellular_location = (String)aParams.get(SUBCELLULAR_LOCATION);
		}
		if(aParams.containsKey(DEVELOPMENTAL_STAGE)) {
			this.iDevelopmental_stage = (String)aParams.get(DEVELOPMENTAL_STAGE);
		}
		if(aParams.containsKey(SEX)) {
			this.iSex = (String)aParams.get(SEX);
		}
		if(aParams.containsKey(DISEASE_STATE)) {
			this.iDisease_state = (String)aParams.get(DISEASE_STATE);
		}
		if(aParams.containsKey(PERMANENT_TRANSFECTION)) {
			this.iPermanent_transfection = (String)aParams.get(PERMANENT_TRANSFECTION);
		}
		if(aParams.containsKey(SOURCE)) {
			this.iSource = (String)aParams.get(SOURCE);
		}
		if(aParams.containsKey(PRE_TREATMENT)) {
			this.iPre_treatment = (String)aParams.get(PRE_TREATMENT);
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
	 * This method returns the value for the 'Cell_sourceid' column
	 * 
	 * @return	long	with the value for the Cell_sourceid column.
	 */
	public long getCell_sourceid() {
		return this.iCell_sourceid;
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
	 * This method returns the value for the 'L_origin' column
	 * 
	 * @return	long	with the value for the L_origin column.
	 */
	public long getL_origin() {
		return this.iL_origin;
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
	 * This method returns the value for the 'Organ' column
	 * 
	 * @return	String	with the value for the Organ column.
	 */
	public String getOrgan() {
		return this.iOrgan;
	}

	/**
	 * This method returns the value for the 'Celltype' column
	 * 
	 * @return	String	with the value for the Celltype column.
	 */
	public String getCelltype() {
		return this.iCelltype;
	}

	/**
	 * This method returns the value for the 'Subcellular_location' column
	 * 
	 * @return	String	with the value for the Subcellular_location column.
	 */
	public String getSubcellular_location() {
		return this.iSubcellular_location;
	}

	/**
	 * This method returns the value for the 'Developmental_stage' column
	 * 
	 * @return	String	with the value for the Developmental_stage column.
	 */
	public String getDevelopmental_stage() {
		return this.iDevelopmental_stage;
	}

	/**
	 * This method returns the value for the 'Sex' column
	 * 
	 * @return	String	with the value for the Sex column.
	 */
	public String getSex() {
		return this.iSex;
	}

	/**
	 * This method returns the value for the 'Disease_state' column
	 * 
	 * @return	String	with the value for the Disease_state column.
	 */
	public String getDisease_state() {
		return this.iDisease_state;
	}

	/**
	 * This method returns the value for the 'Permanent_transfection' column
	 * 
	 * @return	String	with the value for the Permanent_transfection column.
	 */
	public String getPermanent_transfection() {
		return this.iPermanent_transfection;
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
	 * This method returns the value for the 'Pre_treatment' column
	 * 
	 * @return	String	with the value for the Pre_treatment column.
	 */
	public String getPre_treatment() {
		return this.iPre_treatment;
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
	 * This method sets the value for the 'Cell_sourceid' column
	 * 
	 * @param	aCell_sourceid	long with the value for the Cell_sourceid column.
	 */
	public void setCell_sourceid(long aCell_sourceid) {
		this.iCell_sourceid = aCell_sourceid;
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
	 * This method sets the value for the 'L_origin' column
	 * 
	 * @param	aL_origin	long with the value for the L_origin column.
	 */
	public void setL_origin(long aL_origin) {
		this.iL_origin = aL_origin;
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
	 * This method sets the value for the 'Organ' column
	 * 
	 * @param	aOrgan	String with the value for the Organ column.
	 */
	public void setOrgan(String aOrgan) {
		this.iOrgan = aOrgan;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Celltype' column
	 * 
	 * @param	aCelltype	String with the value for the Celltype column.
	 */
	public void setCelltype(String aCelltype) {
		this.iCelltype = aCelltype;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Subcellular_location' column
	 * 
	 * @param	aSubcellular_location	String with the value for the Subcellular_location column.
	 */
	public void setSubcellular_location(String aSubcellular_location) {
		this.iSubcellular_location = aSubcellular_location;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Developmental_stage' column
	 * 
	 * @param	aDevelopmental_stage	String with the value for the Developmental_stage column.
	 */
	public void setDevelopmental_stage(String aDevelopmental_stage) {
		this.iDevelopmental_stage = aDevelopmental_stage;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Sex' column
	 * 
	 * @param	aSex	String with the value for the Sex column.
	 */
	public void setSex(String aSex) {
		this.iSex = aSex;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Disease_state' column
	 * 
	 * @param	aDisease_state	String with the value for the Disease_state column.
	 */
	public void setDisease_state(String aDisease_state) {
		this.iDisease_state = aDisease_state;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Permanent_transfection' column
	 * 
	 * @param	aPermanent_transfection	String with the value for the Permanent_transfection column.
	 */
	public void setPermanent_transfection(String aPermanent_transfection) {
		this.iPermanent_transfection = aPermanent_transfection;
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
	 * This method sets the value for the 'Pre_treatment' column
	 * 
	 * @param	aPre_treatment	String with the value for the Pre_treatment column.
	 */
	public void setPre_treatment(String aPre_treatment) {
		this.iPre_treatment = aPre_treatment;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM cell_source WHERE cell_sourceid = ?");
		lStat.setLong(1, iCell_sourceid);
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
		if(!aKeys.containsKey(CELL_SOURCEID)) {
			throw new IllegalArgumentException("Primary key field 'CELL_SOURCEID' is missing in HashMap!");
		} else {
			iCell_sourceid = ((Long)aKeys.get(CELL_SOURCEID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM cell_source WHERE cell_sourceid = ?");
		lStat.setLong(1, iCell_sourceid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iCell_sourceid = lRS.getLong("cell_sourceid");
			iName = (String)lRS.getObject("name");
			iL_origin = lRS.getLong("l_origin");
			iL_taxonomy = lRS.getLong("l_taxonomy");
			iOrgan = (String)lRS.getObject("organ");
			iCelltype = (String)lRS.getObject("celltype");
			iSubcellular_location = (String)lRS.getObject("subcellular_location");
			iDevelopmental_stage = (String)lRS.getObject("developmental_stage");
			iSex = (String)lRS.getObject("sex");
			iDisease_state = (String)lRS.getObject("disease_state");
			iPermanent_transfection = (String)lRS.getObject("permanent_transfection");
			iSource = (String)lRS.getObject("source");
			iPre_treatment = (String)lRS.getObject("pre_treatment");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'cell_source' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'cell_source' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE cell_source SET cell_sourceid = ?, name = ?, l_origin = ?, l_taxonomy = ?, organ = ?, celltype = ?, subcellular_location = ?, developmental_stage = ?, sex = ?, disease_state = ?, permanent_transfection = ?, source = ?, pre_treatment = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE cell_sourceid = ?");
		lStat.setLong(1, iCell_sourceid);
		lStat.setObject(2, iName);
		lStat.setLong(3, iL_origin);
		lStat.setLong(4, iL_taxonomy);
		lStat.setObject(5, iOrgan);
		lStat.setObject(6, iCelltype);
		lStat.setObject(7, iSubcellular_location);
		lStat.setObject(8, iDevelopmental_stage);
		lStat.setObject(9, iSex);
		lStat.setObject(10, iDisease_state);
		lStat.setObject(11, iPermanent_transfection);
		lStat.setObject(12, iSource);
		lStat.setObject(13, iPre_treatment);
		lStat.setObject(14, iUsername);
		lStat.setObject(15, iCreationdate);
		lStat.setLong(16, iCell_sourceid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO cell_source (cell_sourceid, name, l_origin, l_taxonomy, organ, celltype, subcellular_location, developmental_stage, sex, disease_state, permanent_transfection, source, pre_treatment, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iCell_sourceid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iCell_sourceid);
		}
		if(iName == null) {
			lStat.setNull(2, 12);
		} else {
			lStat.setObject(2, iName);
		}
		if(iL_origin == Long.MIN_VALUE) {
			lStat.setNull(3, 4);
		} else {
			lStat.setLong(3, iL_origin);
		}
		if(iL_taxonomy == Long.MIN_VALUE) {
			lStat.setNull(4, 4);
		} else {
			lStat.setLong(4, iL_taxonomy);
		}
		if(iOrgan == null) {
			lStat.setNull(5, -1);
		} else {
			lStat.setObject(5, iOrgan);
		}
		if(iCelltype == null) {
			lStat.setNull(6, 12);
		} else {
			lStat.setObject(6, iCelltype);
		}
		if(iSubcellular_location == null) {
			lStat.setNull(7, -1);
		} else {
			lStat.setObject(7, iSubcellular_location);
		}
		if(iDevelopmental_stage == null) {
			lStat.setNull(8, -1);
		} else {
			lStat.setObject(8, iDevelopmental_stage);
		}
		if(iSex == null) {
			lStat.setNull(9, 1);
		} else {
			lStat.setObject(9, iSex);
		}
		if(iDisease_state == null) {
			lStat.setNull(10, -1);
		} else {
			lStat.setObject(10, iDisease_state);
		}
		if(iPermanent_transfection == null) {
			lStat.setNull(11, -1);
		} else {
			lStat.setObject(11, iPermanent_transfection);
		}
		if(iSource == null) {
			lStat.setNull(12, -1);
		} else {
			lStat.setObject(12, iSource);
		}
		if(iPre_treatment == null) {
			lStat.setNull(13, -1);
		} else {
			lStat.setObject(13, iPre_treatment);
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