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
 * Date: 27-jun-2008
 * Time: 8:27:35
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the DomainTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Domain from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Domain.</li>
 *   <li><B>getDomainFromID()</bi>: returns a Domain for a given domainid.</li>
 *   <li><b>hashCode()</b>: returns a hashcode for the Domain (which is just the Domain's ID).</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Domain extends DomainTableAccessor {

    /**
     * Default constructor.
     */
    public Domain() {
        super();
    }

    /**
     * Wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Domain(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads the instrument from a resultset. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: domain ID <br />
     * Column 2: l_proteinid <br />
     * Column 3: name <br />
     * Column 4: type <br />
     * Column 5: domaintermid <br />
     * Column 6: start <br />
     * Column 7: end <br />
     * Column 8: username< br />
     * Column 9: creationdate < br />
     * Column 10: modificationdate < br />
     * @param   aRS ResultSet to read the data from.
     * @exception java.sql.SQLException    when reading the ResultSet failed.
     */
    public Domain(ResultSet aRS) throws SQLException {
        this.iDomainid = aRS.getLong(1);
        this.iL_proteinid = aRS.getLong(2);
        this.iName = aRS.getString(3);
        this.iType = aRS.getString(4);
        this.iDomaintermid  = aRS.getString(5);
        this.iStart  = aRS.getLong(6);
        this.iEnd = aRS.getLong(7);
        this.iUsername = aRS.getString(8);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(9);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(10);

    }

    /**
     * This methods reads all Domains from the Domain table.
     *
     * @param aConn Connection to read the instruments from.
     * @return  Domain[] with all the domains in the 'Domain' table.
     * @throws SQLException when the retrieving of the instruments went wrong.
     */
    public static Domain[] getAllDomains(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Domain order by domainid ASC");
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        while(rs.next()) {
            v.add(new Domain(rs));
        }
        rs.close();
        prep.close();
        Domain[] lDomains = new Domain[v.size()];
        v.toArray(lDomains);

        return lDomains;
    }
    /**
     * This method retrieves an Domain from the connection and stores them in a Domain.
     *
     * @param aConn Connection to retrieve the Domain from.
     * @param aDomainid Long to search the Domainid column .
     * @return identification that has a specific Domainid.
     * @throws SQLException when the retrieve failed.
     */
     public static Domain getDomainFromID(Connection aConn, long aDomainid) throws SQLException {

        Domain temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from Domain where Domainid = ?");
        ps.setLong(1, aDomainid);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Domain(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            throw new SQLException("Select based on Domainid '" + aDomainid + "' resulted in " + counter + " results instead of 1!");
        }
        return temp;
    }

    /**
     * Returns a String representation for this Domain.
     *
     * @return  String  with the String representation for this Domain.
     */
    public String toString() {
        return this.iDomainid + ". " + this.iName;
    }

    /**
     * Returns a hashcode for the Domain. <br />
     * The hashcode is just the DomainID, cast to int, which is
     * the PK on the table.
     *
     * @return  int with the hashcode
     */
    public int hashCode() {
        return (int)this.iDomainid;
    }
}
