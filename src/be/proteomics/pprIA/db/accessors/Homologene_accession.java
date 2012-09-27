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
 * Time: 7:31:53
 * To change this template use File | Settings | File Templates.
 */
/**
 * This class provides the following enhancements over the Homologene_accessionTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Homologene_accession from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Homologene_accession.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Homologene_accession extends com.compomics.ppr.db.accessors.Homologene_accessionTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Homologene_accession(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Homologene_accession from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: homologene_accessionID <br />
     * Column 2: l_proteinid <br />
     * Column 3: homologeneid <br />
     * Column 4: username< br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Homologene_accession(ResultSet aRS) throws SQLException {
        this.iHomologene_accessionid = aRS.getLong(1);
        this.iL_proteinid = aRS.getLong(2);
        this.iHomologeneid = aRS.getLong(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);
    }

    /**
     * This method retrieves all the Homologene_accession from the connection and stores them in a HashMap. <br />
     * The homologene_accessionid is the key (Long type) and the Homologene_accession object is the value.
     *
     * @param aConn Connection to retrieve the Homologene_accession from.
     * @return  HashMap with the Homologene_accession, homologene_accessionid is the key (Long type) and Homologene_accession objects are the values.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllHomologene_accessionAsMap(Connection aConn) throws SQLException {
        HashMap lHomologene_accession = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Homologene_accession");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Homologene_accession temp = new Homologene_accession(rs);
            lHomologene_accession.put(new Long(temp.getHomologene_accessionid()),temp);
        }
        rs.close();
        prep.close();

        return lHomologene_accession;
    }

    /**
     * This method retrieves all Homologene_accession from the connection and stores them in a Homologene_accession[].
     *
     * @param aConn Connection to retrieve the Homologene_accession from.
     * @return  Homologene_accession[] with the Homologene_accession.
     * @throws SQLException when the retrieve failed.
     */
    public static Homologene_accession[] getAllHomologene_accession(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Homologene_accession");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Homologene_accession(rs));
        }
        Homologene_accession[] result = new Homologene_accession[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     * This method retrieves a Homologene_accession  for a specific key from the connection and stores them in a Homologene_accession.
     *
     * @param aConn Connection to retrieve the Homologene_accession from.
     * @param aHomologene_accessionid Long the key.
     * @return  Homologene_accession[] with the Homologene_accession.
     * @throws SQLException when the retrieve failed.
     */
    public static Homologene_accession getHomologene_accessionByKey(Connection aConn, Long aHomologene_accessionid) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Homologene_accession where homologene_accessionid = ?");
        prep.setLong(1, aHomologene_accessionid);
        ResultSet rs = prep.executeQuery();
        Homologene_accession result = null;
        while(rs.next()) {
            result = new Homologene_accession(rs);
        }
        rs.close();
        prep.close();
        return result;
    }
    /**
     * This method truncates the homologene_accesion table in the database
     *
     * @param aConn Connection to the database to delete the homologene_accession table.
     * @throws java.sql.SQLException when the deletion failed.
     */
    public static void truncateHomologeneAccessionTable(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("truncate table homologene_accession");
        prep.executeUpdate();
        prep.close();

    }

    /**
     * This method returns a String representation of the Homologene_accession, ie.: the name.
     *
     * @return  String  with the name of the Homologene_accession.
     */
    public String toString() {
        return String.valueOf(this.iHomologene_accessionid);
    }
}



