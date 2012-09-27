package be.proteomics.pprIA.update;

import be.proteomics.ppr.db.accessors.Protein;
import be.proteomics.ppr.general.PdbFileSaver;
import be.proteomics.pprIA.update.db_store.ExtraProteinInfoStorer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Observable;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 09-Jun-2010
 * Time: 09:19:57
 * To change this template use File | Settings | File Templates.
 */
public class DailyProteinInfoFinder {
    private com.compomics.ppr.db.accessors.Protein[] newProteins;
    private Connection iConn;
    private String iSwissProtSource;
    private String iTremblSource;
    private long startMillis;
    private long endMillis;
    private boolean propFound = true;
    private String pdbFileLocation;
    private PrintWriter iOutFile;
    private String iLocation;

    public DailyProteinInfoFinder(Connection aConn, PrintWriter lOutFile, String lLocation) throws SQLException {
        this.iConn = aConn;
        this.iOutFile = lOutFile;
        this.iLocation = lLocation;
        startMillis = System.currentTimeMillis();



        iOutFile.write("#########################\n");
        iOutFile.write("Collecting extra protein information\n");
        newProteins = com.compomics.ppr.db.accessors.Protein.getAllPeptide_groupsNotInfoFound(iConn);
        if (newProteins.length != 0) {
            iOutFile.write("Getting info for  " + newProteins.length + " new proteins\n");

            for (int p = 0; p < newProteins.length; p++) {
                try{
                    ExtraProteinInfoStorer infoGetter = new ExtraProteinInfoStorer(iConn, newProteins[p], iOutFile);
                    iOutFile.write("\tStored info for  " + newProteins[p].getSpaccession() + "\n");
                    iOutFile.flush();
                    Protein.setProteinInfoFound(iConn, newProteins[p].getProteinid());
                } catch(Exception e){
                    iOutFile.write("\tProblem finding information for protein :" + newProteins[p].getSpaccession()  + "\n");
                    e.printStackTrace(iOutFile);
                }
            }


            endMillis = System.currentTimeMillis();
            double totalTime = 0.0;
            boolean inSeconds = false;
            totalTime = endMillis - startMillis;
            if (totalTime > 1000) {
                totalTime /= 1000.0;
                inSeconds = true;
            }
            String duration = new BigDecimal(totalTime).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + (inSeconds ? " seconds" : " milliseconds");
            iOutFile.write("\t----------------------------------------------\nSummary \nTotal update process took : " + duration + "\n");
            iOutFile.write("\tFound info for " + newProteins.length + " proteins!\n");

        } else {
            iOutFile.write("\tInfo found for all the proteins\n");
        }


    }
    public static void main(String[] Args){
            Connection lConn = null;
        String driverClass = "com.mysql.jdbc.Driver";
        String userName = "updater";
        String pass = "updater,13*";
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
        try {
            com.compomics.ppr.db.accessors.Protein[] newProteins = com.compomics.ppr.db.accessors.Protein.getAllPeptide_groupsNotInfoFound(lConn);
        if (newProteins.length != 0) {

            for (int p = 0; p < newProteins.length; p++) {
                try{
                    ExtraProteinInfoStorer infoGetter = new ExtraProteinInfoStorer(lConn, newProteins[p]);
                    System.out.println("\tStored info for  " + newProteins[p].getSpaccession() + "\n");
                    Protein.setProteinInfoFound(lConn, newProteins[p].getProteinid());
                } catch(Exception e){
                    e.printStackTrace();
                    }
                }

                }
            } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

