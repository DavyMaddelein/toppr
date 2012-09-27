/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 24-sept-2007
 * Time: 8:17:54
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
 * This class provides the following enhancements over the Cell_sourceTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Cell_source from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the Cell_source.</li>
 *   <li><B>getCell_sourceFromID()</bi>: returns a Cell_source for a given cell_sourceid.</li>
 *   <li><b>hashCode()</b>: returns a hashcode for the Cell_source (which is just the Cell_source's ID).</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Cell_source extends Cell_sourceTableAccessor {

    /**
     * Default constructor.
     */
    public Cell_source() {
        super();
    }

    /**
     * Wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Cell_source(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads the instrument from a resultset. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: cell_source ID <br />
     * Column 2: name <br />
     * Column 3: l_origin <br />
     * Column 4: l_taxonomy <br />
     * Column 5: organ <br />
     * Column 6: cell type <br />
     * Column 7: subcellular_location <br />
     * Column 8: developmental stage <br />
     * Column 9: sex <br />
     * Column 10: disease state < br />
     * Column 11: permanent_transfection < br />
     * Column 12: source < br />
     * Column 13: pre_treatment < br />
     * Column 14: username< br />
     * Column 15: creationdate < br />
     * Column 16: modificationdate < br />
     * @param   aRS ResultSet to read the data from.
     * @exception   SQLException    when reading the ResultSet failed.
     */
    public Cell_source(ResultSet aRS) throws SQLException {
        this.iCell_sourceid = aRS.getLong(1);
        this.iName = aRS.getString(2);
        this.iL_origin = aRS.getLong(3);
        this.iL_taxonomy = aRS.getLong(4);
        this.iOrgan  = aRS.getString(5);
        this.iCelltype  = aRS.getString(6);
        this.iSubcellular_location = aRS.getString(7);
        this.iDevelopmental_stage  = aRS.getString(8);
        this.iSex  = aRS.getString(9);
        this.iDisease_state  = aRS.getString(10);
        this.iPermanent_transfection  = aRS.getString(11);
        this.iSource  = aRS.getString(12);
        this.iPre_treatment = aRS.getString(13);
        this.iUsername = aRS.getString(14);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(15);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(16);

    }

    /**
     * This methods reads all Cell_sources from the Cell_source table.
     *
     * @param aConn Connection to read the instruments from.
     * @return  Cell_source[] with all the cell_sources in the 'Cell_source' table.
     * @throws SQLException when the retrieving of the instruments went wrong.
     */
    public static Cell_source[] getAllCell_sources(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select cell_sourceid, name, l_origin, l_taxonomy, organ, celltype, subcellular_location , developmental_stage, sex, disease_state , permanent_transfection, source, pre_treatment, username, creationdate, modificationdate from Cell_source order by cell_sourceid ASC");
        ResultSet rs = prep.executeQuery();
        Vector v = new Vector();
        while(rs.next()) {
            v.add(new Cell_source(rs));
        }
        rs.close();
        prep.close();
        Cell_source[] lCell_sources = new Cell_source[v.size()];
        v.toArray(lCell_sources);

        return lCell_sources;
    }
    /**
     * This method retrieves an Cell_source from the connection and stores them in a Cell_source.
     *
     * @param aConn Connection to retrieve the Cell_source from.
     * @param aCell_sourceid Long to search the Cell_sourceid column .
     * @return identification that has a specific Cell_sourceid.
     * @throws SQLException when the retrieve failed.
     */
     public static Cell_source getCell_sourceFromID(Connection aConn, long aCell_sourceid) throws SQLException {

        Cell_source temp = null;
        PreparedStatement ps = aConn.prepareStatement("select cell_sourceid, name, l_origin, l_taxonomy, organ, celltype, subcellular_location , developmental_stage, sex, disease_state , permanent_transfection, source, pre_treatment, username, creationdate, modificationdate from Cell_source where Cell_sourceid = ?");
        ps.setLong(1, aCell_sourceid);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Cell_source(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
            throw new SQLException("Select based on Cell_sourceid '" + aCell_sourceid + "' resulted in " + counter + " results instead of 1!");
        }
        return temp;
    }

    /**
     * Returns a String representation for this Cell_source.
     *
     * @return  String  with the String representation for this Cell_source.
     */
    public String toString() {
        return this.iCell_sourceid + ". " + this.iName + " (" + iL_taxonomy + ")";
    }

    /**
     * Returns a hashcode for the Cell_source. <br />
     * The hashcode is just the Cell_sourceID, cast to int, which is
     * the PK on the table.
     *
     * @return  int with the hashcode
     */
    public int hashCode() {
        return (int)this.iCell_sourceid;
    }
}