package be.proteomics.pprIA.update;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 08-Jun-2010
 * Time: 10:15:18
 * To change this template use File | Settings | File Templates.
 */
public class Updater {
    private String iLocation;
    private Connection iConn;
    private PrintWriter out;
    private Connection iConnUpdater;

    public Updater() {
        try {

            //get the location
            readProperties();
            System.out.println(iLocation);
            iConn = createConnection("guest","guest");
            System.out.println(iConn);


            //create the filewriter
            out = new PrintWriter(new FileWriter(iLocation + "\\" + "updateLog.txt"));

            //find the search settings
            try {
                new DailySearchSettings(iConn, out, iLocation);
                out.flush();
                iConn.close();
                iConnUpdater = createConnection("updater","updater,13*");
            } catch (Exception e) {
                out.write("Error in finding search settings\n");
                e.printStackTrace(out);
            }


            //find the new UniProt-SwissProt release
            boolean lNewRelease = false;
            try {
                DailyUniprotFetcher lDailyFetcher = new DailyUniprotFetcher(iConnUpdater, out, iLocation);
                lNewRelease = lDailyFetcher.iNewRelease;
                out.flush();
            } catch (Exception e) {
                out.write("Error in finding the new UniProt-SwissProt release\n");
                e.printStackTrace(out);
            }

            if(lNewRelease){
                //try to match the old peptides to the new release
                try {
                    new DialyNewSpReleaseUpdater(iConnUpdater, out, iLocation);
                    out.flush();
                } catch (Exception e) {
                    out.write("Error in matching the old peptides to the new release\n");
                    e.printStackTrace(out);
                }
            }

            //try to match the new peptides to the protein database
            try {
                new DailyNewSitesUpdater(iConnUpdater, out, iLocation);
                out.flush();
            } catch (Exception e) {
                out.write("Error in mapping new sites\n");
                e.printStackTrace(out);
            }


            //find the new extra protein info
            try {
                new DailyProteinInfoFinder(iConnUpdater, out, iLocation);
                out.flush();
            } catch (Exception e) {
                out.write("Error in finding extra protein info\n");
                e.printStackTrace(out);
            }

            //find the new extra protein info
            try {
                new DailyPdbFilesSaver(iConnUpdater, out, iLocation);
                out.flush();
            } catch (Exception e) {
                out.write("Error in finding pdbs\n");
                e.printStackTrace(out);
            }

            out.write("\n\nUpdating done");
            out.flush();
            out.close();  
            

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        new Updater();

    }


    public void readProperties() throws IOException {
        iLocation = "C:\\Tomcat-7.0\\webapps\\toppr\\";
    }

    public Connection createConnection(String lUser, String lPass) {
        Connection lConn = null;
        String driverClass = "com.mysql.jdbc.Driver";
        String userName = lUser;
        String pass = lPass;
        try {
            com.mysql.jdbc.Driver d = (com.mysql.jdbc.Driver) Class.forName(driverClass).newInstance();
            Properties lProps = new Properties();
            lProps.put("user", userName);
            lProps.put("password", pass);
            lConn = d.connect("jdbc:mysql://iomics.ugent.be/ppr", lProps);

        } catch (ClassNotFoundException cnfe) {
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return lConn;
    }


}
