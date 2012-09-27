package be.proteomics.pprIA.update;

import be.proteomics.ppr.db.accessors.Peptide_group;
import be.proteomics.pprIA.general.protein_info.finder.PeptideGroupIdentifier;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 08-Jun-2010
 * Time: 09:55:57
 * To change this template use File | Settings | File Templates.
 */
public class DialyNewSpReleaseUpdater {

    private Connection iConn;
    private String iSwissProtSource;
    private long startMillis;
    private long endMillis;
    private String iLocation;
    private PrintWriter iOutFile;

    public DialyNewSpReleaseUpdater(Connection aConn, PrintWriter lOutFile, String lLocation) throws Exception {
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

        PreparedStatement prep = aConn.prepareStatement("select groupid, l_taxonomy, peptide_sequence, mapped, old, position, username, creationdate, modificationdate from peptide_group where old = 1");
        ResultSet rs2 = prep.executeQuery();
        Vector<be.proteomics.pprIA.db.accessors.Peptide_group> temp = new Vector<be.proteomics.pprIA.db.accessors.Peptide_group>();
        while(rs2.next()) {
            temp.add(new be.proteomics.pprIA.db.accessors.Peptide_group(rs2));
        }
        be.proteomics.pprIA.db.accessors.Peptide_group[] iOldPeptidesToRemap = new be.proteomics.pprIA.db.accessors.Peptide_group[temp.size()];
        temp.toArray(iOldPeptidesToRemap);
        rs2.close();
        prep.close();

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
        iOutFile.println("Date: " + date + "\n");

        PeptideGroupIdentifier identifySProt = new PeptideGroupIdentifier(iOldPeptidesToRemap, iConn, iSwissProtSource, iOutFile, iLocation, false);
        identifySProt.identifyPeptideGroups();
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
        iOutFile.println("----------------------------------------------\nSummary\nTotal update process took : " + duration + "\n\n");


    }

}
