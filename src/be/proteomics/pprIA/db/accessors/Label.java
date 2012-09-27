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
 * Date: 4-dec-2007
 * Time: 8:31:44
 * To change this template use File | Settings | File Templates.
 */
/**
 * This class provides the following enhancements over the LabelTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Label from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the user.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Label extends LabelTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Label(HashMap aParams) {
        super(aParams);
    }


    /**
     * This constructor reads a Label from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: LabelID <br />
     * Column 2: name <br />
     * Column 2: description <br />
     * Column 2: molecular_weight <br />
     * Column 3: username< br />
     * Column 4: creationdate < br />
     * Column 5: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Label(ResultSet aRS) throws SQLException {
        this.iLabelid = aRS.getLong(1);
        this.iName = aRS.getString(2);
        this.iDescription = aRS.getString(3);
        this.iMolecular_weight = aRS.getDouble(4);
        this.iUsername = aRS.getString(5);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(6);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(7);
    }

    /**
     * This method retrieves all Labels from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the users from.
     * @return HashMap with all the Labels.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllLabelsAsMap(Connection aConn) throws SQLException {
        HashMap lLabels = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select labelid, name, description, molecular_weight,  username, creationdate, modificationdate from Label");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Label temp = new Label(rs);
            lLabels.put(new Long(temp.getLabelid()),temp);
        }
        rs.close();
        prep.close();

        return lLabels;
    }

    /**
     * This method retrieves all Labels from the connection and stores them in a Label[].
     *
     * @param aConn Connection to retrieve the Labels from.
     * @return  Label[] with the Labels.
     * @throws SQLException when the retrieve failed.
     */
    public static Label[] getAllLabels(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select labelid, name, description, molecular_weight,  username, creationdate, modificationdate from Label");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Label(rs));
        }
        Label[] result = new Label[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }


    /**
     * This method returns a String representation of the Label, ie.: the name.
     *
     * @return  String  with the name of the Label.
     */
    public String toString() {
        return this.iName;
    }
}