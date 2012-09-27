/*
 * Created by the DBAccessor generator.
 * Programmer: Lennart Martens
 * Date: 18/02/2008
 * Time: 13:24:09
 */
package com.compomics.ppr.db.accessors;
import java.sql.*;
import java.io.*;
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
 * This class is a generated accessor for the Peptide table.
 *
 * @author DBAccessor generator class (Lennart Martens).
 */
public class PeptideTableAccessor implements Deleteable, Retrievable, Updateable, Persistable {

	/**
	 * This variable tracks changes to the object.
	 */
	protected boolean iUpdated = false;

	/**
	 * This variable can hold generated primary key columns.
	 */
	protected Object[] iKeys = null;

	/**
	 * This variable represents the contents for the 'identificationid' column.
	 */
	protected long iIdentificationid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_groupid' column.
	 */
	protected long iL_groupid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_projectid' column.
	 */
	protected long iL_projectid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'l_instrumentid' column.
	 */
	protected long iL_instrumentid = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'modified_sequence' column.
	 */
	protected String iModified_sequence = null;


	/**
	 * This variable represents the contents for the 'ion_coverage' column.
	 */
	protected String iIon_coverage = null;


	/**
	 * This variable represents the contents for the 'score' column.
	 */
	protected long iScore = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'identitythreshold' column.
	 */
	protected long iIdentitythreshold = Long.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'confidence' column.
	 */
	protected Number iConfidence = null;


	/**
	 * This variable represents the contents for the 'precursor' column.
	 */
	protected Number iPrecursor = null;


	/**
	 * This variable represents the contents for the 'charge' column.
	 */
	protected int iCharge = Integer.MIN_VALUE;


	/**
	 * This variable represents the contents for the 'exp_mass' column.
	 */
	protected Number iExp_mass = null;


	/**
	 * This variable represents the contents for the 'cal_mass' column.
	 */
	protected Number iCal_mass = null;


	/**
	 * This variable represents the contents for the 'spectrumfile' column.
	 */
	protected byte[] iSpectrumfile = null;


	/**
	 * This variable represents the contents for the 'db' column.
	 */
	protected String iDb = null;


	/**
	 * This variable represents the contents for the 'mascot_version' column.
	 */
	protected String iMascot_version = null;


	/**
	 * This variable represents the contents for the 'identificationdate' column.
	 */
	protected java.sql.Timestamp iIdentificationdate = null;


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
	 * This variable represents the key for the 'identificationid' column.
	 */
	public static final String IDENTIFICATIONID = "IDENTIFICATIONID";

	/**
	 * This variable represents the key for the 'l_groupid' column.
	 */
	public static final String L_GROUPID = "L_GROUPID";

	/**
	 * This variable represents the key for the 'l_projectid' column.
	 */
	public static final String L_PROJECTID = "L_PROJECTID";

	/**
	 * This variable represents the key for the 'l_instrumentid' column.
	 */
	public static final String L_INSTRUMENTID = "L_INSTRUMENTID";

	/**
	 * This variable represents the key for the 'modified_sequence' column.
	 */
	public static final String MODIFIED_SEQUENCE = "MODIFIED_SEQUENCE";

	/**
	 * This variable represents the key for the 'ion_coverage' column.
	 */
	public static final String ION_COVERAGE = "ION_COVERAGE";

	/**
	 * This variable represents the key for the 'score' column.
	 */
	public static final String SCORE = "SCORE";

	/**
	 * This variable represents the key for the 'identitythreshold' column.
	 */
	public static final String IDENTITYTHRESHOLD = "IDENTITYTHRESHOLD";

	/**
	 * This variable represents the key for the 'confidence' column.
	 */
	public static final String CONFIDENCE = "CONFIDENCE";

	/**
	 * This variable represents the key for the 'precursor' column.
	 */
	public static final String PRECURSOR = "PRECURSOR";

	/**
	 * This variable represents the key for the 'charge' column.
	 */
	public static final String CHARGE = "CHARGE";

	/**
	 * This variable represents the key for the 'exp_mass' column.
	 */
	public static final String EXP_MASS = "EXP_MASS";

	/**
	 * This variable represents the key for the 'cal_mass' column.
	 */
	public static final String CAL_MASS = "CAL_MASS";

	/**
	 * This variable represents the key for the 'spectrumfile' column.
	 */
	public static final String SPECTRUMFILE = "SPECTRUMFILE";

	/**
	 * This variable represents the key for the 'db' column.
	 */
	public static final String DB = "DB";

	/**
	 * This variable represents the key for the 'mascot_version' column.
	 */
	public static final String MASCOT_VERSION = "MASCOT_VERSION";

	/**
	 * This variable represents the key for the 'identificationdate' column.
	 */
	public static final String IDENTIFICATIONDATE = "IDENTIFICATIONDATE";

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
	public PeptideTableAccessor() {
	}

	/**
	 * This constructor allows the creation of the 'PeptideTableAccessor' object based on a set of values in the HashMap.
	 *
	 * @param	aParams	HashMap with the parameters to initialize this object with.
	 *		<i>Please use only constants defined on this class as keys in the HashMap!</i>
	 */
	public PeptideTableAccessor(HashMap aParams) {
		if(aParams.containsKey(IDENTIFICATIONID)) {
			this.iIdentificationid = ((Long)aParams.get(IDENTIFICATIONID)).longValue();
		}
		if(aParams.containsKey(L_GROUPID)) {
			this.iL_groupid = ((Long)aParams.get(L_GROUPID)).longValue();
		}
		if(aParams.containsKey(L_PROJECTID)) {
			this.iL_projectid = ((Long)aParams.get(L_PROJECTID)).longValue();
		}
		if(aParams.containsKey(L_INSTRUMENTID)) {
			this.iL_instrumentid = ((Long)aParams.get(L_INSTRUMENTID)).longValue();
		}
		if(aParams.containsKey(MODIFIED_SEQUENCE)) {
			this.iModified_sequence = (String)aParams.get(MODIFIED_SEQUENCE);
		}
		if(aParams.containsKey(ION_COVERAGE)) {
			this.iIon_coverage = (String)aParams.get(ION_COVERAGE);
		}
		if(aParams.containsKey(SCORE)) {
			this.iScore = ((Long)aParams.get(SCORE)).longValue();
		}
		if(aParams.containsKey(IDENTITYTHRESHOLD)) {
			this.iIdentitythreshold = ((Long)aParams.get(IDENTITYTHRESHOLD)).longValue();
		}
		if(aParams.containsKey(CONFIDENCE)) {
			this.iConfidence = (Number)aParams.get(CONFIDENCE);
		}
		if(aParams.containsKey(PRECURSOR)) {
			this.iPrecursor = (Number)aParams.get(PRECURSOR);
		}
		if(aParams.containsKey(CHARGE)) {
			this.iCharge = ((Integer)aParams.get(CHARGE)).intValue();
		}
		if(aParams.containsKey(EXP_MASS)) {
			this.iExp_mass = (Number)aParams.get(EXP_MASS);
		}
		if(aParams.containsKey(CAL_MASS)) {
			this.iCal_mass = (Number)aParams.get(CAL_MASS);
		}
		if(aParams.containsKey(SPECTRUMFILE)) {
			this.iSpectrumfile = (byte[])aParams.get(SPECTRUMFILE);
		}
		if(aParams.containsKey(DB)) {
			this.iDb = (String)aParams.get(DB);
		}
		if(aParams.containsKey(MASCOT_VERSION)) {
			this.iMascot_version = (String)aParams.get(MASCOT_VERSION);
		}
		if(aParams.containsKey(IDENTIFICATIONDATE)) {
			this.iIdentificationdate = (java.sql.Timestamp)aParams.get(IDENTIFICATIONDATE);
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
	 * This method returns the value for the 'Identificationid' column
	 * 
	 * @return	long	with the value for the Identificationid column.
	 */
	public long getIdentificationid() {
		return this.iIdentificationid;
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
	 * This method returns the value for the 'L_projectid' column
	 * 
	 * @return	long	with the value for the L_projectid column.
	 */
	public long getL_projectid() {
		return this.iL_projectid;
	}

	/**
	 * This method returns the value for the 'L_instrumentid' column
	 * 
	 * @return	long	with the value for the L_instrumentid column.
	 */
	public long getL_instrumentid() {
		return this.iL_instrumentid;
	}

	/**
	 * This method returns the value for the 'Modified_sequence' column
	 * 
	 * @return	String	with the value for the Modified_sequence column.
	 */
	public String getModified_sequence() {
		return this.iModified_sequence;
	}

	/**
	 * This method returns the value for the 'Ion_coverage' column
	 * 
	 * @return	String	with the value for the Ion_coverage column.
	 */
	public String getIon_coverage() {
		return this.iIon_coverage;
	}

	/**
	 * This method returns the value for the 'Score' column
	 * 
	 * @return	long	with the value for the Score column.
	 */
	public long getScore() {
		return this.iScore;
	}

	/**
	 * This method returns the value for the 'Identitythreshold' column
	 * 
	 * @return	long	with the value for the Identitythreshold column.
	 */
	public long getIdentitythreshold() {
		return this.iIdentitythreshold;
	}

	/**
	 * This method returns the value for the 'Confidence' column
	 * 
	 * @return	Number	with the value for the Confidence column.
	 */
	public Number getConfidence() {
		return this.iConfidence;
	}

	/**
	 * This method returns the value for the 'Precursor' column
	 * 
	 * @return	Number	with the value for the Precursor column.
	 */
	public Number getPrecursor() {
		return this.iPrecursor;
	}

	/**
	 * This method returns the value for the 'Charge' column
	 * 
	 * @return	int	with the value for the Charge column.
	 */
	public int getCharge() {
		return this.iCharge;
	}

	/**
	 * This method returns the value for the 'Exp_mass' column
	 * 
	 * @return	Number	with the value for the Exp_mass column.
	 */
	public Number getExp_mass() {
		return this.iExp_mass;
	}

	/**
	 * This method returns the value for the 'Cal_mass' column
	 * 
	 * @return	Number	with the value for the Cal_mass column.
	 */
	public Number getCal_mass() {
		return this.iCal_mass;
	}

	/**
	 * This method returns the value for the 'Spectrumfile' column
	 * 
	 * @return	byte[]	with the value for the Spectrumfile column.
	 */
	public byte[] getSpectrumfile() {
		return this.iSpectrumfile;
	}

	/**
	 * This method returns the value for the 'Db' column
	 * 
	 * @return	String	with the value for the Db column.
	 */
	public String getDb() {
		return this.iDb;
	}

	/**
	 * This method returns the value for the 'Mascot_version' column
	 * 
	 * @return	String	with the value for the Mascot_version column.
	 */
	public String getMascot_version() {
		return this.iMascot_version;
	}

	/**
	 * This method returns the value for the 'Identificationdate' column
	 * 
	 * @return	java.sql.Timestamp	with the value for the Identificationdate column.
	 */
	public java.sql.Timestamp getIdentificationdate() {
		return this.iIdentificationdate;
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
	 * This method sets the value for the 'Identificationid' column
	 * 
	 * @param	aIdentificationid	long with the value for the Identificationid column.
	 */
	public void setIdentificationid(long aIdentificationid) {
		this.iIdentificationid = aIdentificationid;
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
	 * This method sets the value for the 'L_projectid' column
	 * 
	 * @param	aL_projectid	long with the value for the L_projectid column.
	 */
	public void setL_projectid(long aL_projectid) {
		this.iL_projectid = aL_projectid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'L_instrumentid' column
	 * 
	 * @param	aL_instrumentid	long with the value for the L_instrumentid column.
	 */
	public void setL_instrumentid(long aL_instrumentid) {
		this.iL_instrumentid = aL_instrumentid;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Modified_sequence' column
	 * 
	 * @param	aModified_sequence	String with the value for the Modified_sequence column.
	 */
	public void setModified_sequence(String aModified_sequence) {
		this.iModified_sequence = aModified_sequence;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Ion_coverage' column
	 * 
	 * @param	aIon_coverage	String with the value for the Ion_coverage column.
	 */
	public void setIon_coverage(String aIon_coverage) {
		this.iIon_coverage = aIon_coverage;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Score' column
	 * 
	 * @param	aScore	long with the value for the Score column.
	 */
	public void setScore(long aScore) {
		this.iScore = aScore;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Identitythreshold' column
	 * 
	 * @param	aIdentitythreshold	long with the value for the Identitythreshold column.
	 */
	public void setIdentitythreshold(long aIdentitythreshold) {
		this.iIdentitythreshold = aIdentitythreshold;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Confidence' column
	 * 
	 * @param	aConfidence	Number with the value for the Confidence column.
	 */
	public void setConfidence(Number aConfidence) {
		this.iConfidence = aConfidence;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Precursor' column
	 * 
	 * @param	aPrecursor	Number with the value for the Precursor column.
	 */
	public void setPrecursor(Number aPrecursor) {
		this.iPrecursor = aPrecursor;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Charge' column
	 * 
	 * @param	aCharge	int with the value for the Charge column.
	 */
	public void setCharge(int aCharge) {
		this.iCharge = aCharge;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Exp_mass' column
	 * 
	 * @param	aExp_mass	Number with the value for the Exp_mass column.
	 */
	public void setExp_mass(Number aExp_mass) {
		this.iExp_mass = aExp_mass;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Cal_mass' column
	 * 
	 * @param	aCal_mass	Number with the value for the Cal_mass column.
	 */
	public void setCal_mass(Number aCal_mass) {
		this.iCal_mass = aCal_mass;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Spectrumfile' column
	 * 
	 * @param	aSpectrumfile	byte[] with the value for the Spectrumfile column.
	 */
	public void setSpectrumfile(byte[] aSpectrumfile) {
		this.iSpectrumfile = aSpectrumfile;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Db' column
	 * 
	 * @param	aDb	String with the value for the Db column.
	 */
	public void setDb(String aDb) {
		this.iDb = aDb;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Mascot_version' column
	 * 
	 * @param	aMascot_version	String with the value for the Mascot_version column.
	 */
	public void setMascot_version(String aMascot_version) {
		this.iMascot_version = aMascot_version;
		this.iUpdated = true;
	}

	/**
	 * This method sets the value for the 'Identificationdate' column
	 * 
	 * @param	aIdentificationdate	java.sql.Timestamp with the value for the Identificationdate column.
	 */
	public void setIdentificationdate(java.sql.Timestamp aIdentificationdate) {
		this.iIdentificationdate = aIdentificationdate;
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
		PreparedStatement lStat = aConn.prepareStatement("DELETE FROM peptide WHERE identificationid = ?");
		lStat.setLong(1, iIdentificationid);
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
		if(!aKeys.containsKey(IDENTIFICATIONID)) {
			throw new IllegalArgumentException("Primary key field 'IDENTIFICATIONID' is missing in HashMap!");
		} else {
			iIdentificationid = ((Long)aKeys.get(IDENTIFICATIONID)).longValue()
;		}
		// In getting here, we probably have all we need to continue. So let's...
		PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM peptide WHERE identificationid = ?");
		lStat.setLong(1, iIdentificationid);
		ResultSet lRS = lStat.executeQuery();
		int hits = 0;
		while(lRS.next()) {
			hits++;
			iIdentificationid = lRS.getLong("identificationid");
			iL_groupid = lRS.getLong("l_groupid");
			iL_projectid = lRS.getLong("l_projectid");
			iL_instrumentid = lRS.getLong("l_instrumentid");
			iModified_sequence = (String)lRS.getObject("modified_sequence");
			iIon_coverage = (String)lRS.getObject("ion_coverage");
			iScore = lRS.getLong("score");
			iIdentitythreshold = lRS.getLong("identitythreshold");
			iConfidence = (Number)lRS.getObject("confidence");
			iPrecursor = (Number)lRS.getObject("precursor");
			iCharge = lRS.getInt("charge");
			iExp_mass = (Number)lRS.getObject("exp_mass");
			iCal_mass = (Number)lRS.getObject("cal_mass");
			InputStream is13 = lRS.getBinaryStream("spectrumfile");
			Vector bytes13 = new Vector();
			int reading = -1;
			try {
				while((reading = is13.read()) != -1) {
					bytes13.add(new Byte((byte)reading));
				}
				is13.close();
			} catch(IOException ioe) {
				bytes13 = new Vector();
			}
			reading = bytes13.size();
			iSpectrumfile = new byte[reading];
			for(int i=0;i<reading;i++) {
				iSpectrumfile[i] = ((Byte)bytes13.get(i)).byteValue();
			}
			iDb = (String)lRS.getObject("db");
			iMascot_version = (String)lRS.getObject("mascot_version");
			iIdentificationdate = (java.sql.Timestamp)lRS.getObject("identificationdate");
			iUsername = (String)lRS.getObject("username");
			iCreationdate = (java.sql.Timestamp)lRS.getObject("creationdate");
			iModificationdate = (java.sql.Timestamp)lRS.getObject("modificationdate");
		}
		lRS.close();
		lStat.close();
		if(hits>1) {
			throw new SQLException("More than one hit found for the specified primary keys in the 'peptide' table! Object is initialized to last row returned.");
		} else if(hits == 0) {
			throw new SQLException("No hits found for the specified primary keys in the 'peptide' table! Object is not initialized correctly!");
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
		PreparedStatement lStat = aConn.prepareStatement("UPDATE peptide SET identificationid = ?, l_groupid = ?, l_projectid = ?, l_instrumentid = ?, modified_sequence = ?, ion_coverage = ?, score = ?, identitythreshold = ?, confidence = ?, precursor = ?, charge = ?, exp_mass = ?, cal_mass = ?, spectrumfile = ?, db = ?, mascot_version = ?, identificationdate = ?, username = ?, creationdate = ?, modificationdate = CURRENT_TIMESTAMP WHERE identificationid = ?");
		lStat.setLong(1, iIdentificationid);
		lStat.setLong(2, iL_groupid);
		lStat.setLong(3, iL_projectid);
		lStat.setLong(4, iL_instrumentid);
		lStat.setObject(5, iModified_sequence);
		lStat.setObject(6, iIon_coverage);
		lStat.setLong(7, iScore);
		lStat.setLong(8, iIdentitythreshold);
		lStat.setObject(9, iConfidence);
		lStat.setObject(10, iPrecursor);
		lStat.setInt(11, iCharge);
		lStat.setObject(12, iExp_mass);
		lStat.setObject(13, iCal_mass);
		ByteArrayInputStream bais13 = new ByteArrayInputStream(iSpectrumfile);
		lStat.setBinaryStream(14, bais13, iSpectrumfile.length);
		lStat.setObject(15, iDb);
		lStat.setObject(16, iMascot_version);
		lStat.setObject(17, iIdentificationdate);
		lStat.setObject(18, iUsername);
		lStat.setObject(19, iCreationdate);
		lStat.setLong(20, iIdentificationid);
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
		PreparedStatement lStat = aConn.prepareStatement("INSERT INTO peptide (identificationid, l_groupid, l_projectid, l_instrumentid, modified_sequence, ion_coverage, score, identitythreshold, confidence, precursor, charge, exp_mass, cal_mass, spectrumfile, db, mascot_version, identificationdate, username, creationdate, modificationdate) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
		if(iIdentificationid == Long.MIN_VALUE) {
			lStat.setNull(1, 4);
		} else {
			lStat.setLong(1, iIdentificationid);
		}
		if(iL_groupid == Long.MIN_VALUE) {
			lStat.setNull(2, 4);
		} else {
			lStat.setLong(2, iL_groupid);
		}
		if(iL_projectid == Long.MIN_VALUE) {
			lStat.setNull(3, 4);
		} else {
			lStat.setLong(3, iL_projectid);
		}
		if(iL_instrumentid == Long.MIN_VALUE) {
			lStat.setNull(4, 4);
		} else {
			lStat.setLong(4, iL_instrumentid);
		}
		if(iModified_sequence == null) {
			lStat.setNull(5, -1);
		} else {
			lStat.setObject(5, iModified_sequence);
		}
		if(iIon_coverage == null) {
			lStat.setNull(6, -1);
		} else {
			lStat.setObject(6, iIon_coverage);
		}
		if(iScore == Long.MIN_VALUE) {
			lStat.setNull(7, 4);
		} else {
			lStat.setLong(7, iScore);
		}
		if(iIdentitythreshold == Long.MIN_VALUE) {
			lStat.setNull(8, 4);
		} else {
			lStat.setLong(8, iIdentitythreshold);
		}
		if(iConfidence == null) {
			lStat.setNull(9, 3);
		} else {
			lStat.setObject(9, iConfidence);
		}
		if(iPrecursor == null) {
			lStat.setNull(10, 3);
		} else {
			lStat.setObject(10, iPrecursor);
		}
		if(iCharge == Integer.MIN_VALUE) {
			lStat.setNull(11, 5);
		} else {
			lStat.setInt(11, iCharge);
		}
		if(iExp_mass == null) {
			lStat.setNull(12, 3);
		} else {
			lStat.setObject(12, iExp_mass);
		}
		if(iCal_mass == null) {
			lStat.setNull(13, 3);
		} else {
			lStat.setObject(13, iCal_mass);
		}
		if(iSpectrumfile == null) {
			lStat.setNull(14, -4);
		} else {
			ByteArrayInputStream bais13 = new ByteArrayInputStream(iSpectrumfile);
			lStat.setBinaryStream(14, bais13, iSpectrumfile.length);
		}
		if(iDb == null) {
			lStat.setNull(15, 12);
		} else {
			lStat.setObject(15, iDb);
		}
		if(iMascot_version == null) {
			lStat.setNull(16, 12);
		} else {
			lStat.setObject(16, iMascot_version);
		}
		if(iIdentificationdate == null) {
			lStat.setNull(17, 93);
		} else {
			lStat.setObject(17, iIdentificationdate);
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