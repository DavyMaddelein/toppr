/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 26-sept-2007
 * Time: 16:01:03
 * To change this template use File | Settings | File Templates.
 */
package be.proteomics.pprIA.db.accessors;

import com.compomics.ppr.db.accessors.Peptide_groupTableAccessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

/*
 * CVS information:
 *
 * $Revision: 1.2 $
 * $Date: 2007/11/12 08:56:24 $
 */

/**
 * This class provides the following enhancements over the Peptide_groupTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Peptide_group from a ResultSet.</li>
 *   <li><b>getPeptide_groupFromSeq()</b>: returns a Peptide_group who has a specific peptide_sequence.</li>
 *   <li><B>insertCell_source()</bi>: insert a new peptide_group in the petide_gorup table.</li>
 *  <li><b>toString()</b>: returns the name of the Peptide_group.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Peptide_group extends Peptide_groupTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Peptide_group(HashMap aParams) {
        super(aParams);
    }


    /**
     * This constructor reads a Peptide_group from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: Peptide_group ID <br />
     * Column 2: l_taxonomy <br />
     * Column 3: Peptide_sequence <br />
     * Column 4: mapped <br />
     * Column 5: old <br />
     * Column 6: position <br />
     * Column 7: username< br />
     * Column 8: creationdate < br />
     * Column 9: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Peptide_group(ResultSet aRS) throws SQLException {
        this.iGroupid = aRS.getLong(1);
        this.iL_taxonomy = aRS.getLong(2);
        this.iPeptide_sequence = aRS.getString(3);
        this.iMapped = aRS.getBoolean(4);
        this.iOld = aRS.getBoolean(5);
        this.iPosition = aRS.getString(6);
        this.iUsername = aRS.getString(7);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(8);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(9);
    }

    /**
     * This method retrieves all Peptide_groups from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the Peptide_groups from.
     * @return  HashMap with the Peptide_groups.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap<Long, Peptide_group> getAllPeptide_groupsAsMap(Connection aConn) throws SQLException {
        HashMap<Long, Peptide_group> lPeptide_groups = new HashMap<Long, Peptide_group>();
        PreparedStatement prep = aConn.prepareStatement("select groupid, l_taxonomy, peptide_sequence, mapped, old, position, username, creationdate, modificationdate from peptide_group");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Peptide_group temp = new Peptide_group(rs);
            lPeptide_groups.put(new Long(temp.getGroupid()),temp);
        }
        rs.close();
        prep.close();

        return lPeptide_groups;
    }

    /**
     * This method retrieves all Peptide_groups from the connection and stores them in a Peptide_group[].
     *
     * @param aConn Connection to retrieve the Peptide_groups from.
     * @return  Peptide_group[] with the Peptide_groups.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide_group[] getAllPeptide_groups(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select groupid, l_taxonomy, peptide_sequence, mapped, old, position, username, creationdate, modificationdate from peptide_group");
        ResultSet rs = prep.executeQuery();
        Vector<Peptide_group> temp = new Vector<Peptide_group>();
        while(rs.next()) {
            temp.add(new Peptide_group(rs));
        }
        Peptide_group[] result = new Peptide_group[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves all Peptide_groups from the connection and stores them in a Peptide_group[].
     *
     * @param aConn Connection to retrieve the Peptide_groups from.
     * @return  Peptide_group[] with the Peptide_groups.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide_group[] getAllPeptide_groupsNotMapped(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select groupid, l_taxonomy, peptide_sequence, mapped, old, position, username, creationdate, modificationdate from peptide_group where mapped = 0");
        ResultSet rs = prep.executeQuery();
        Vector<Peptide_group> temp = new Vector<Peptide_group>();
        while(rs.next()) {
            temp.add(new Peptide_group(rs));
        }
        Peptide_group[] result = new Peptide_group[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

     /**
     * This method retrieves all Peptide_groups from the connection and stores them in a Peptide_group[].
     *
     * @param aConn Connection to retrieve the Peptide_groups from.
     * @return  Peptide_group[] with the Peptide_groups.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide_group[] getAllPeptide_groupsNotOld(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select groupid, l_taxonomy, peptide_sequence, mapped, old, position, username, creationdate, modificationdate from peptide_group where old = 0");
        ResultSet rs = prep.executeQuery();
        Vector<Peptide_group> temp = new Vector<Peptide_group>();
        while(rs.next()) {
            temp.add(new Peptide_group(rs));
        }
        Peptide_group[] result = new Peptide_group[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves a petide_group from the connection and stores them in a peptide_group.
     *
     * @param aConn Connection to retrieve the Peptide_group from.
     * @param aSequence String to search the peptide_sequence column.
     * @param taxonomyID Long to search the l_taxonomy column.
     * @return petide_group with the searched sequence.
     * @throws SQLException when the retrieve failed.
     */
    public static Peptide_group getPeptide_groupFromSeq(Connection aConn, String aSequence, Long taxonomyID, String lCofradic) throws SQLException {
        Peptide_group temp = null;
        PreparedStatement ps = aConn.prepareStatement("select groupid, l_taxonomy, peptide_sequence, mapped, old, position, username, creationdate, modificationdate from peptide_group where peptide_sequence = ? and l_taxonomy = ? and position = ?");
        ps.setString(1, aSequence);
        ps.setLong(2, taxonomyID);
        ps.setString(3, lCofradic);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Peptide_group(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            temp = null;
        }
        return temp;
    }

    /**
     * This method retrieves a petide_group from the connection and stores them in a peptide_group.
     *
     * @param aConn Connection to retrieve the Peptide_group from.
     * @param aGroupid n.
     * @return petide_group with the searched sequence.
     * @throws SQLException when the retrieve failed.
     */
    public static int setPeptide_groupMapped(Connection aConn, Long aGroupid) throws SQLException {
        PreparedStatement ps = aConn.prepareStatement("update peptide_group as g set g.mapped = 1 where g.groupid = ?");
        ps.setLong(1, aGroupid);
        int result = ps.executeUpdate();
        return result;
    }
     /**
     * This method retrieves a petide_group from the connection and stores them in a peptide_group.
     *
     * @param aConn Connection to retrieve the Peptide_group from.
     * @param aGroupid n.
     * @return petide_group with the searched sequence.
     * @throws SQLException when the retrieve failed.
     */
    public static int setPeptide_groupUnMapped(Connection aConn, Long aGroupid) throws SQLException {
        PreparedStatement ps = aConn.prepareStatement("update peptide_group as g set g.mapped = 0 where g.groupid = ?");
        ps.setLong(1, aGroupid);
        int result = ps.executeUpdate();
        return result;
    }

    /**
     * This method retrieves a petide_group from the connection and stores them in a peptide_group.
     *
     * @param aConn Connection to retrieve the Peptide_group from.
     * @param aGroupid n.
     * @return petide_group with the searched sequence.
     * @throws SQLException when the retrieve failed.
     */
    public static int setPeptide_groupOld(Connection aConn, Long aGroupid) throws SQLException {
        PreparedStatement ps = aConn.prepareStatement("update peptide_group as g set g.old = 1 where g.groupid = ?");
        ps.setLong(1, aGroupid);
        int result = ps.executeUpdate();
        return result;
    }

    /**
	 * This method allows the caller to insert a sequence in the peptide_group table
	 *
	 *
     * @param   aConn Connection to the persitent store.
     * @param   sequence String holds the sequences.
     * @param   taxonomy Long holds the l_taxonomy.
	 */
	public static void insertSequence(Connection aConn, String sequence, Long taxonomy, String lCofradic) throws SQLException {
		    PreparedStatement prep = aConn.prepareStatement("INSERT INTO peptide_group (l_taxonomy, peptide_sequence, mapped,old,position, username, creationdate, modificationdate) values(?,?,0,0,?,CURRENT_USER, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
            prep.setLong(1, taxonomy);
            prep.setString(2, sequence);
            prep.setString(3, lCofradic);
            prep.executeUpdate();
		    prep.close();
	}    


    /**
     * This method returns a String representation of the Peptide_group, ie.: the peptide_sequence.
     *
     * @return  String  with the peptide_sequence of the Peptide_group.
     */
    public String toString() {
        return this.iPeptide_sequence;
    }
}
