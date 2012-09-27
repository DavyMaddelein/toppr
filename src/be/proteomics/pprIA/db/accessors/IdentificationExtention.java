package com.compomics.ppr.db.accessors;

import com.compomics.mslims.db.accessors.Identification;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 4-okt-2007
 * Time: 8:39:18
 * To change this template use File | Settings | File Templates.
 */
public class IdentificationExtention extends Identification {

    /**
    * This method retrieves an identificationID from the connection and stores them in a Identification.
    *
    * @param aConn Connection to retrieve the identification from.
    * @param aIdentificationid String to search the identificationid column .
    * @return identification that has a specific identificationid.
    * @throws java.sql.SQLException when the retrieve failed.
    */
     public static Identification getIdentificationFromID(Connection aConn, long aIdentificationid) throws SQLException {

        Identification temp = null;
        PreparedStatement ps = aConn.prepareStatement("select * from identification where identificationid = ?");
        ps.setLong(1, aIdentificationid);
        ResultSet rs = ps.executeQuery();
        int counter = 0;
        while(rs.next()) {
            counter++;
            temp = new Identification(rs);
        }
        rs.close();
        ps.close();
        if(counter != 1) {
             throw new SQLException("Select based on identificationID '" + aIdentificationid + "' resulted in " + counter + " results instead of 1!");
        }
        return temp;
    }

}
