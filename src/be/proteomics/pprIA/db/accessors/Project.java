package com.compomics.ppr.db.accessors;


import java.util.HashMap;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 4-okt-2007
 * Time: 9:46:58
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides the following enhancements over the ProjectTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Project from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the title of the project.</li>
 *   <li><b>hashCode()</b>: returns a hashcode for the project (which is just the Project's ID).</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Project extends ProjectTableAccessor {

    /**
     * Default constructor.
     */
    public Project() {
        super();
    }

    /**
     * Wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Project(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads the project from a resultset. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: project ID <br />
     * Column 2: link to the cell_source for the project <br />
     * Column 3: link to the experiment type used for the project <br />
     * Column 4: link to the COFRADIC type used for the project <br />
     * Column 5: link to the user for the project <br />
     * Column 6: title of the project <br />
     * Column 7: the description for the project
     * Column 8: username< br />
     * Column 9: creationdate < br />
     * Column 10: modificationdate < br />
     *
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Project(ResultSet aRS) throws SQLException {
        this.iProjectid = aRS.getLong(1);
        this.iL_cell_sourceid = aRS.getLong(2);
        this.iL_experimentid= aRS.getLong(3);
        this.iL_cofradicid = aRS.getLong(4);
        this.iL_userid = aRS.getLong(5);
        this.iTitle  = aRS.getString(6);
        this.iDescription = aRS.getString(7);
        this.iUsername = aRS.getString(8);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(9);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(10);
    }

    /**
     * This methods reads all projects from the Project table.
     *
     * @param aConn Connection to read the projects from.
     * @return  Project[] with the projects in the 'Project' table.
     * @throws SQLException when the retrieving of the projects went wrong.
     */
    public static Project[] getAllProjects(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select projectid, l_cell_sourceid, l_experimentid, l_cofradicid, l_userid, title, description, username,  creationdate, modificationdate from Project order by creationdate desc, title asc");
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        while(rs.next()) {
            v.add(new Project(rs));
        }
        rs.close();
        prep.close();
        Project[] lProjects = new Project[v.size()];
        v.toArray(lProjects);

        return lProjects;
    }
    /**
     * This methods finds a project with a specific title from the Project table.
     *
     * @param aConn Connection to read the projects from.
     * @param aTitle String .
     * @return  Project[] with the projects in the 'Project' table.
     * @throws SQLException when the retrieving of the projects went wrong.
     */
    public static Project getProjectByName(Connection aConn, String aTitle) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Project where title = ?");
        prep.setString(1, aTitle);
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        Project result = null;
        while(rs.next()) {
            result = new Project(rs);
        }
        rs.close();
        prep.close();
        return result;
    }

    /**
     * Returns a String representation for this Project.
     *
     * @return  String  with the String representation for this Project.
     */
    public String toString() {
        return this.iProjectid + ". " + this.iTitle;
    }

    /**
     * Returns a hashcode for the Project. <br />
     * The hashcode is just the ProjectID, cast to int, which is
     * the PK on the table.
     *
     * @return  int with the hashcode
     */
    public int hashCode() {
        return (int)this.iProjectid;
    }

}
