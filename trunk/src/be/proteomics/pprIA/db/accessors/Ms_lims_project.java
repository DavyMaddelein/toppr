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
 * Time: 13:53:29
 * To change this template use File | Settings | File Templates.
 */
/**
 * This class provides the following enhancements over the Ms_lims_projectTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Ms_lims_project from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Ms_lims_project.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Ms_lims_project extends Ms_lims_projectTableAccessor {

    /**
     * Default constructor.
     */
    public Ms_lims_project() {
        super();
    }

    /**
     * Wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Ms_lims_project(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads the Ms_lims_project from a resultset. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: Ms_lims_project ID <br />
     * Column 2: Ms_lims_project title <br />
     * Column 3: username <br />
     * Column 4: creationdate <br />
     * Column 5: modificationdate.
     *
     * @param   aRS ResultSet to read the data from.
     * @exception java.sql.SQLException    when reading the ResultSet failed.
     */
    public Ms_lims_project(ResultSet aRS) throws SQLException {
        iMs_lims_projectid = aRS.getLong(1);
        iMs_limstitle = aRS.getString(2);
        iUsername = aRS.getString(3);
        iCreationdate = (java.sql.Timestamp)aRS.getObject(4);
        iModificationdate = (java.sql.Timestamp)aRS.getObject(5);
    }

    /**
     * This methods reads all Ms_lims_projects from the Ms_lims_project table.
     *
     * @param aConn Connection to read the Ms_lims_projects from.
     * @return  Ms_lims_project[] with the Ms_lims_projects in the 'Ms_lims_project' table.
     * @throws SQLException when the retrieving of the Ms_lims_projects went wrong.
     */
    public static Ms_lims_project[] getAllMs_lims_projects(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Ms_lims_project");
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        while(rs.next()) {
            v.add(new Ms_lims_project(rs));
        }
        rs.close();
        prep.close();
        Ms_lims_project[] lMs_lims_projects = new Ms_lims_project[v.size()];
        v.toArray(lMs_lims_projects);

        return lMs_lims_projects;
    }

    /**
     * This methods reads all Ms_lims_projectss from the Ms_lims_project table that have a
     * calibrated differential standard deviation.
     *
     * @param aConn Connection to read the Ms_lims_projects from.
     * @return  Ms_lims_project[] with the Ms_lims_projects in the 'Ms_lims_project' table.
     * @throws SQLException when the retrieving of the Ms_lims_projects went wrong.
     */
    public static Ms_lims_project[] getAllDifferentialCalibratedMs_lims_projects(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Ms_lims_project");
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        while(rs.next()) {
            v.add(new Ms_lims_project(rs));
        }
        rs.close();
        prep.close();
        Ms_lims_project[] lMs_lims_projects = new Ms_lims_project[v.size()];
        v.toArray(lMs_lims_projects);

        return lMs_lims_projects;
    }

    /**
	 * This method allows the caller to read data for this
	 * object from a persistent store based on the specified keys.
	 *
	 * @param   aConn Connection to the persitent store.
	 */
	public static boolean retrieveProject(Connection aConn, long aId) throws SQLException {
		// In getting here, we probably have all we need to continue. So let's...
		boolean found = false;
            PreparedStatement lStat = aConn.prepareStatement("SELECT * FROM ms_lims_project WHERE ms_lims_projectid = ?");
		lStat.setLong(1, aId);
		ResultSet lRS = lStat.executeQuery();
		while(lRS.next()) {
            found = true;
		}
		lRS.close();
		lStat.close();
		return found;
	}

    /**
     * Returns a String representation for this Ms_lims_project.
     *
     * @return  String  with the String representation for this Ms_lims_project.
     */
    public String toString() {
        return this.iMs_limstitle;
    }
}