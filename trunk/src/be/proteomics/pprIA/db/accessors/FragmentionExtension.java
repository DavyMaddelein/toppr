package com.compomics.ppr.db.accessors;


import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 18-feb-2008
 * Time: 12:00:44
 * To change this template use File | Settings | File Templates.
 */
public class FragmentionExtension extends com.compomics.mslims.db.accessors.Fragmention {

    /**
    * This method retrieves an fragmentions from the connection and stores them in a fragmention[].
    *
    * @param aConn Connection to retrieve the fragmentions from.
    * @param aIdentificationid String to search the l_identificationid column .
    * @return identification that has a specific identificationid.
    * @throws java.sql.SQLException when the retrieve failed.
    */
     public static com.compomics.mslims.db.accessors.Fragmention[] getFragmentionsFromID(java.sql.Connection aConn, long aIdentificationid) throws SQLException {

        PreparedStatement ps = aConn.prepareStatement("select * from fragmention where l_identificationid = ?");
        ps.setLong(1, aIdentificationid);
        ResultSet rs = ps.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new com.compomics.mslims.db.accessors.Fragmention(rs));
        }
        com.compomics.mslims.db.accessors.Fragmention[] result = new com.compomics.mslims.db.accessors.Fragmention[temp.size()];
        temp.toArray(result);
        rs.close();
        ps.close();
        return result;
    }

}
