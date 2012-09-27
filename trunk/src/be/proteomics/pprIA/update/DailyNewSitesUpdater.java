package be.proteomics.pprIA.update;


import be.proteomics.pprIA.general.protein_info.finder.PeptideGroupIdentifier;
import com.compomics.ppr.db.accessors.Peptide_group;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 09-Jun-2010
 * Time: 08:44:59
 * To change this template use File | Settings | File Templates.
 */
public class DailyNewSitesUpdater {
    private be.proteomics.pprIA.db.accessors.Peptide_group[] iNewPeptides;
    private Connection iConn;
    private String iSwissProtSource;
    private long startMillis;
    private long endMillis;
    private String iLocation;
    private PrintWriter iOutFile;

    public DailyNewSitesUpdater(Connection aConn, PrintWriter lOutFile, String lLocation) throws Exception {
        this.iOutFile = lOutFile;
        this.iLocation = lLocation;
        this.iConn = aConn;
        File lFolder = new File(iLocation);
        File[] lFiles = lFolder.listFiles();
        for (int f = 0; f < lFiles.length; f++) {
            if (lFiles[f].getName().startsWith("sprot") && lFiles[f].getName().endsWith(".fasta")) {
                iSwissProtSource = lFiles[f].getAbsolutePath();
            }
        }
        iOutFile.write("#################################\n");
        iOutFile.write("Mapping new peptides to " + iSwissProtSource + "\n");
        iNewPeptides = be.proteomics.pprIA.db.accessors.Peptide_group.getAllPeptide_groups(iConn);
        if (iNewPeptides.length != 0) {
            String date = null;
            try {
                PreparedStatement prepS = iConn.prepareStatement("select now()");
                ResultSet rs = prepS.executeQuery();
                while (rs.next()) {
                    date = rs.getString(1);
                }
                rs.close();
                prepS.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            iOutFile.println("\nDate: " + date);
            System.out.println("\nDate: " + date);

            PeptideGroupIdentifier identifySProt = new PeptideGroupIdentifier(iNewPeptides, iConn, iSwissProtSource, iOutFile, iLocation, true);
            identifySProt.identifyPeptideGroups();

            //set peptides old
            for (int i = 0; i < iNewPeptides.length; i++) {
                Peptide_group.setPeptide_groupOld(iConn, iNewPeptides[i].getGroupid());
            }
            //set peptides group mapped
            PreparedStatement mapped = iConn.prepareStatement("update peptide_group as g set g.mapped = 1 where g.groupid in (select l.l_groupid from peptide_location as l)");
            mapped.executeUpdate();
            mapped.close();

            endMillis = System.currentTimeMillis();
            double totalTime = 0.0;
            boolean inSeconds = false;
            totalTime = endMillis - startMillis;
            if (totalTime > 1000) {
                totalTime /= 1000.0;
                inSeconds = true;
            }
            String duration = new BigDecimal(totalTime).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + (inSeconds ? " seconds" : " milliseconds");
            iOutFile.println("----------------------------------------------\nSummary \nTotal update process took : " + duration + "\n");

        } else {
            iOutFile.println("\nNo unmapped peptides to map");
        }


    }

}

