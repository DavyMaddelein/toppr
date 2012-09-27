/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 24-sept-2007
 * Time: 15:28:26
 * To change this template use File | Settings | File Templates.
 */
package com.compomics.ppr.db.accessors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;


/**
 * This class provides the following enhancements over the ExperimentTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Experiment from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the user.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Experiment extends ExperimentTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Experiment(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor sets the only 'settable' field: name.
     *
     * @param aName String with the full name for the Experiment.
     */
    public Experiment(String aName) {
        super.setName(aName);
    }

    /**
     * This constructor reads a Experiment from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: experimentID <br />
     * Column 2: name <br />
     * Column 3: description <br />
     * Column 4: username< br />
     * Column 5: creationdate < br />
     * Column 6: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Experiment(ResultSet aRS) throws SQLException {
        this.iExperimentid = aRS.getLong(1);
        this.iName = aRS.getString(2);
        this.iDescription = aRS.getString(3);
        this.iUsername = aRS.getString(4);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(5);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(6);
    }

    /**
     * This method retrieves all experiments from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the Experiment from.
     * @return HashMap with the all the experiments.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllExperimentsAsMap(Connection aConn) throws SQLException {
        HashMap lExperiments = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select experimentid, name, description, username, creationdate, modificationdate from experiment");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Experiment temp = new Experiment(rs);
            lExperiments.put(new Long(temp.getExperimentid()),temp);
        }
        rs.close();
        prep.close();

        return lExperiments;
    }

    /**
     * This method retrieves all Experiment from the connection and stores them in a Experiment[].
     *
     * @param aConn Connection to retrieve the Experiment from.
     * @return  Experiment[] with the Experiments.
     * @throws SQLException when the retrieve failed.
     */
    public static Experiment[] getAllExperiments(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select experimentid, name, description, username, creationdate, modificationdate from experiment");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Experiment(rs));
        }
        Experiment[] result = new Experiment[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }


    /**
     * This method returns a String representation of the Experiment, ie.: the name.
     *
     * @return  String  with the name of the Experiment.
     */
    public String toString() {
        return this.iName;
    }
}
