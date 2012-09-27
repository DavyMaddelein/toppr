package com.compomics.ppr.db.accessors;

/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 30-sep-2007
 * Time: 12:26:56
 * To change this template use File | Settings | File Templates.
 */


import com.compomics.ppr.db.accessors.ProteinTableAccessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;


/**
 * This class provides the following enhancements over the ProteinTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single Protein from a ResultSet.</li>
 *    <li><B>getProteinFromSPaccession()</bi>: returns a Protein for a given SPaccession.</li>
 *    <li><B>insertProtein()</bi>: insert a protein in the protein table.</li>
 *    <li><b>truncateProtein_locationTable()</b>: truncates the protein table.</li>
 *   <li><b>toString()</b>: returns the name of the user.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class Protein extends ProteinTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public Protein(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor reads a Protein from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: ProteinID <br />
     * Column 2: SPaccesion <br />
     * Column 3: sequence <br />
     * Column 4: entry_name <br />
     * Column 5: name <br />
     * Column 6: secStruc Pred <br />
     * Column 7: secStruc Swiss<br />
     * Column 8: info_found<br />
     * Column 9: username< br />
     * Column 10: creationdate < br />
     * Column 11: modificationdate < br />

     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public Protein(ResultSet aRS) throws SQLException {
        this.iProteinid = aRS.getLong(1);
        this.iSpaccession = aRS.getString(2);
        this.iSequence= aRS.getString(3);
        this.iEntry_name = aRS.getString(4);
        this.iName= aRS.getString(5);
        this.iSec_structure_prediction= aRS.getString(6);
        this.iSec_structure_swissprot= aRS.getString(7);
        this.iInfo_found = aRS.getInt(8);
        this.iUsername = aRS.getString(9);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(10);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(11);
    }

    /**
     * This method retrieves all Proteins from the connection and stores them in a HashMap. <br />
     *
     * @param aConn Connection to retrieve the Proteins from.
     * @return  HashMap with the Proteins.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllProteinsAsMap(Connection aConn) throws SQLException {
        HashMap lProteins = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select * from Protein");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            Protein temp = new Protein(rs);
            lProteins.put(new Long(temp.getProteinid()),temp);
        }
        rs.close();
        prep.close();

        return lProteins;
    }

   /**
     * This method retrieves all proteins from the connection and stores them in a Protein[].
     *
     * @param aConn Connection to retrieve the Proteins from.
     * @return  Protein [] with the Proteins.
     * @throws SQLException when the retrieve failed.
     */
    public static Protein[] getAllPeptide_groupsNotInfoFound(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from protein where info_found = 0 and SPaccession != 'Q06265' and SPaccession != 'P68871' and SPaccession != 'P68431' and SPaccession != 'P62805' and Spaccession != 'P60709'");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Protein(rs));
        }
        Protein[] result = new Protein[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }

    /**
     *
     *
     * @param aConn Connection to retrieve the Protein from.
     * @param aProteinid n.
     * @return Protein with the searched sequence.
     * @throws SQLException when the retrieve failed.
     */
    public static int setProteinInfoFound(Connection aConn, Long aProteinid) throws SQLException {
        PreparedStatement ps = aConn.prepareStatement("update protein as p set p.info_found = 1 where p.proteinid = ?");
        ps.setLong(1, aProteinid);
        int result = ps.executeUpdate();
        return result;
    }

    /**
     * This method retrieves all Proteins from the connection and stores them in a Protein[].
     *
     * @param aConn Connection to retrieve the Proteins from.
     * @return  Protein[] with the Proteins.
     * @throws SQLException when the retrieve failed.
     */
    public static Protein[] getAllProteins(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Protein");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Protein(rs));
        }
        Protein[] result = new Protein[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }



    /**
     * This method retrieves all Proteins from the connection and stores them in a Protein[].
     *
     * @param aConn Connection to retrieve the Proteins from.
     * @return  Protein[] with the Proteins.
     * @throws SQLException when the retrieve failed.
     */
    public static Protein[] getAllProteinsWithoutPdb(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select * from Protein as p where p.proteinid not in (select d.l_proteinid from pdb as d) ");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new Protein(rs));
        }
        Protein[] result = new Protein[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }
   /**
    * This method retrieves a protein from the connection and stores them in a protein.
    *
    * @param aConn Connection to retrieve the Peptide_group from.
    * @param aAccession String to search the SPaccession column.
    * @return Protein that has a specific SPaccession.
    * @throws SQLException when the retrieve failed.
    */
    public static Protein getProteinFromSPaccession(Connection aConn, String aAccession) throws SQLException {

        Protein temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from Protein where SPaccession = ?");
        ps.setString(1, aAccession);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Protein(rs);
        }
       rs.close();
       ps.close();
       if(counter != 1) {
            temp = null;
       }
       return temp;
    }

    /**
	 * This method allows the caller to insert a protein (SPaccession) in the protein table
	 *
	 *
     * @param   aConn Connection to the persitent store.
     * @param   accession String holds the accession.
	 */
	public static void insertAccession(Connection aConn, String accession) throws SQLException {
		    PreparedStatement prep = aConn.prepareStatement("INSERT INTO protein (SPaccession) values(?)");
            prep.setString(1, accession);
            prep.executeUpdate();
		    prep.close();
	}

    /**
     * This method truncates the protein table in the ccdb database
     *
     * @param aConn Connection to the database to delete the protein table.
     * @throws java.sql.SQLException when the deletion failed.
     */
    public static void truncateProteinTable(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("truncate table protein");
        prep.executeUpdate();
        prep.close();

    }


    /**
     * This method returns a String representation of the Protein, ie.: the name.
     *
     * @return  String  with the name of the user.
     */
    public String toString() {
        return java.lang.Long.toString(this.iProteinid);
    }
}

