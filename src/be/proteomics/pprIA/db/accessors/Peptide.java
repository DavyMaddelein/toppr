package com.compomics.ppr.db.accessors;
/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 24-sept-2007
 * Time: 15:23:27
 * To change this template use File | Settings | File Templates.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import java.io.InputStream;
import java.io.IOException;


/**
 * This class provides the following enhancements over the PeptideTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Peptide from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the user.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Peptide extends PeptideTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Peptide(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Peptide from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />     
     * Column 1: identificationid <br />
     * Column 2: l_groupID <br />
     * Column 3: l_projectID <br />
     * Column 4: l_instrumentID <br />
     * Column 5: modified_sequence <br />
     * Column 6: ion_coverage <br />
     * Column 7: score <br />
     * Column 8: identitythreshold <br />
     * Column 9: confidence <br />
     * Column 10: precursor <br />
     * Column 11: charge <br />
     * Column 12: exp_mass <br />
     * Column 13: cal_mass <br />
     * Column 14: spectrumfile <br />
     * Column 15: db <br />
     * Column 16: mascot_version <br />
     * Column 17: identificationdate <br />
     * Column 18: username< br />
     * Column 19: creationdate < br />
     * Column 20: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Peptide(ResultSet aRS) throws SQLException {
        this.iIdentificationid = aRS.getLong(1);
        this.iL_groupid = aRS.getLong(2);
        this.iL_projectid = aRS.getLong(3);
        this.iL_instrumentid = aRS.getLong(4);
        this.iModified_sequence = aRS.getString(5);
        this.iIon_coverage = aRS.getString(6);
        this.iScore = aRS.getLong(7);
        this.iIdentitythreshold = aRS.getLong(8);
        this.iConfidence = aRS.getLong(9);
        this.iPrecursor = aRS.getLong(10);
        this.iCharge = aRS.getInt(11);
        this.iExp_mass = aRS.getLong(12);
        this.iCal_mass = aRS.getLong(13);
        InputStream is1 = aRS.getBinaryStream(14);
        Vector bytes1 = new Vector();
        int reading = -1;
        try {
            while((reading = is1.read()) != -1) {
                bytes1.add(new Byte((byte)reading));
            }
            is1.close();
        } catch(IOException ioe) {
            bytes1 = new Vector();
        }
        reading = bytes1.size();
        iSpectrumfile = new byte[reading];
        for(int i=0;i<reading;i++) {
            iSpectrumfile[i] = ((Byte)bytes1.get(i)).byteValue();
        }
        this.iDb = aRS.getString(15);
        this.iMascot_version = aRS.getString(16);
        this.iIdentificationdate = (java.sql.Timestamp)aRS.getObject(17);
        this.iUsername = aRS.getString(18);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(19);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(20);

    }

    /**
     * This method retrieves all Peptides from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the users from.
     * @return  HashMap with the Peptides.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllPeptidesAsMap(Connection aConn) throws SQLException {
        HashMap lPeptides = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from peptide");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Peptide temp = new Peptide(rs);
            lPeptides.put(new Long(temp.getIdentificationid()),temp);
        }
        rs.close();
        prep.close();

        return lPeptides;
    }

    /**
     * This method retrieves all Peptides from the connection and stores them in a Peptide[].
     *
     * @param aConn Connection to retrieve the Peptides from.
     * @return  Peptide[] with the Peptides.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide[] getAllPeptides(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from peptide");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Peptide(rs));
        }
        Peptide[] result = new Peptide[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

     /**
     * This method retrieves all Protein for a peptide from the connection and stores them in a Protein[].
     *
     * @param aConn Connection to retrieve the Peptides from.
     * @param identificationid long id to search the proteins for
     * @return  Protein[] with the Protein.
     * @throws SQLException when the retrieve failed.
     */
    public static Protein[] getProteinsForPeptide(Connection aConn,Long identificationid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select p.* from protein as p, peptide as e, peptide_group as g, peptide_location as l where p.proteinid = l.l_proteinid and l.l_groupid = g.groupid and g.groupid = e.l_groupid and e.identificationid = ?");
        prep.setLong(1, identificationid);
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Protein(rs));
        }
        Protein[] result = new Protein[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves peptides for a peptide identificationid from the connection and stores them in a peptide[].
     *
     * @param aConn Connection to retrieve the Peptides from.
     * @param identificationid long id to search the proteins for
     * @return  Protein[] with the Protein.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide getPeptideForIdentificationID(Connection aConn,Long identificationid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from peptide where identificationid = ?");
        prep.setLong(1, identificationid);
        ResultSet rs = prep.executeQuery();
        Peptide result = null;
        while(rs.next()) {
            result = new Peptide(rs);
        }
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves peptides for a peptide identificationid from the connection and stores them in a peptide[].
     *
     * @param aConn Connection to retrieve the Peptides from.
     * @param groupid long id to search the proteins for
     * @return  Protein[] with the Protein.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide[] getPeptideForGroupid(Connection aConn,Long groupid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from peptide where l_groupid = ?");
        prep.setLong(1, groupid);
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Peptide(rs));
        }
        Peptide[] result = new Peptide[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method returns a String representation of the Peptide, ie.: the peptideID.
     *
     * @return  String  with the id of the Peptide.
     */
    public String toString() {
        return java.lang.Long.toString(this.iIdentificationid);
    }
}
