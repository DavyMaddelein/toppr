package com.compomics.ppr.db.accessors;

import java.util.HashMap;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 3-jan-2008
 * Time: 7:37:23
 * To change this template use File | Settings | File Templates.
 */
/**
 * This class provides the following enhancements over the Pdb_accessionTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Pdb_accession from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Pdb_accession.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Pdb_accession extends Pdb_accessionTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Pdb_accession(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Pdb_accession from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: pdb_accessionID <br />
     * Column 2: l_proteinid <br />
     * Column 3: pdbid <br />
     * Column 4: username< br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Pdb_accession(ResultSet aRS) throws SQLException {
        this.iPdb_accessionid = aRS.getLong(1);
        this.iL_proteinid = aRS.getLong(2);
        this.iPdbid = aRS.getString(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);
    }

    /**
     * This method retrieves all the Pdb_accession from the connection and stores them in a HashMap. <br />
     * The pdb_accessionid is the key (Long type) and the Pdb_accession object is the value.
     *
     * @param aConn Connection to retrieve the Pdb_accession from.
     * @return  HashMap with the Pdb_accession, pdb_accessionid is the key (Long type) and Pdb_accession objects are the values.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllPdb_accessionAsMap(Connection aConn) throws SQLException {
        HashMap lPdb_accession = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Pdb_accession");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Pdb_accession temp = new Pdb_accession(rs);
            lPdb_accession.put(new Long(temp.getPdb_accessionid()),temp);
        }
        rs.close();
        prep.close();

        return lPdb_accession;
    }

    /**
     * This method retrieves all Pdb_accession from the connection and stores them in a Pdb_accession[].
     *
     * @param aConn Connection to retrieve the Pdb_accession from.
     * @return  Pdb_accession[] with the Pdb_accession.
     * @throws SQLException when the retrieve failed.
     */
    public static Pdb_accession[] getAllPdb_accession(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Pdb_accession");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Pdb_accession(rs));
        }
        Pdb_accession[] result = new Pdb_accession[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves a Pdb_accession  for a specific key from the connection and stores them in a Pdb_accession.
     *
     * @param aConn Connection to retrieve the Pdb_accession from.
     * @param aPdb_accessionid Long the key.
     * @return  Pdb_accession[] with the Pdb_accession.
     * @throws SQLException when the retrieve failed.
     */
    public static Pdb_accession getPdb_accessionByKey(Connection aConn, Long aPdb_accessionid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Pdb_accession where pdb_accessionid = ?");
        prep.setLong(1, aPdb_accessionid);
        ResultSet rs = prep.executeQuery();
        Pdb_accession result = null;
        while(rs.next()) {
            result = new Pdb_accession(rs);
        }
        rs.close();
        prep.close();
        return result;
    }
    /**
     * This method truncates the pdb_accesion table in the database
     *
     * @param aConn Connection to the database to delete the pdb_accession table.
     * @throws java.sql.SQLException when the deletion failed.
     */
    public static void truncatePdbAccessionTable(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("truncate table pdb_accession");
        prep.executeUpdate();
        prep.close();

    }

    /**
     * This method returns a String representation of the Pdb_accession, ie.: the name.
     *
     * @return  String  with the name of the Pdb_accession.
     */
    public String toString() {
        return String.valueOf(this.iPdb_accessionid);
    }
}

