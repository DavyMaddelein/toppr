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
 * Date: 15-feb-2008
 * Time: 13:34:00
 * To change this template use File | Settings | File Templates.
 */
/**
 * This class provides the following enhancements over the FragmentionTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Fragmention from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the user.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Fragmention extends FragmentionTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Fragmention(HashMap aParams) {
        super(aParams);
    }


    /**
     * This constructor reads a Fragmention from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: FragmentionID <br />
     * Column 2: peptideid <br />
     * Column 3: iontype <br />
     * Column 4: ionname <br />
     * Column 5: l_ionscoringid <br />
     * Column 6: mz <br />
     * Column 7: intensity <br />
     * Column 8: fragmentionnumber <br />
     * Column 9: massdelta <br />
     * Column 10: masserrormargin <br />
     * Column 11: username< br />
     * Column 12: creationdate < br />
     * Column 13: modificationdate < br />
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Fragmention(ResultSet aRS) throws SQLException {
        this.iFragmentionid = aRS.getLong(1);
        this.iL_peptideid = aRS.getLong(2);
        this.iIontype  = aRS.getLong(3);
        this.iIonname = aRS.getString(4);
        this.iL_ionscoringid = aRS.getLong(5);
        this.iMz = aRS.getLong(6);
        this.iIntensity = aRS.getLong(7);
        this.iFragmentionnumber = aRS.getLong(8);
        this.iMassdelta = aRS.getLong(9);
        this.iMasserrormargin = aRS.getLong(10);
        this.iUsername = aRS.getString(11);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(12);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(13);
    }

    /**
     * This method retrieves all Fragmentions from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the users from.
     * @return HashMap with all the Fragmentionin.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllFragmentionsAsMap(Connection aConn) throws SQLException {
        HashMap lFragmentions = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Fragmention");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Fragmention temp = new Fragmention(rs);
            lFragmentions.put(new Long(temp.getFragmentionid()),temp);
        }
        rs.close();
        prep.close();

        return lFragmentions;
    }

    /**
     * This method retrieves all Fragmentions from the connection and stores them in a Fragmention[].
     *
     * @param aConn Connection to retrieve the Fragmentions from.
     * @return  Fragmention[] with the Fragmentions.
     * @throws SQLException when the retrieve failed.
     */
    public static Fragmention[] getAllFragmentions(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Fragmention");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Fragmention(rs));
        }
        Fragmention[] result = new Fragmention[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }


    /**
     * This method returns a String representation of the Fragmention, ie.: the Scientific_name.
     *
     * @return  String  with the name of the Fragmention.
     */

    public String toString() {
        return String.valueOf(this.iFragmentionid);
    }
}

