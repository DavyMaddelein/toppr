package be.proteomics.pprIA.update;

import be.proteomics.ppr.db.accessors.Protein;
import be.proteomics.util.io.FTPClient;
import be.proteomics.util.io.FTPClient2;
import com.compomics.util.io.FTP;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 08-Jun-2010
 * Time: 08:21:43
 * To change this template use File | Settings | File Templates.
 */
public class DailyUniprotFetcher {


    private String iUniprotRelease;
    private String iSprotRelease;
    private String iTremblRelease;

    /**
     * The filename of the created database
     */
    private String iFileName;
    /**
     * The number of saved proteins
     */
    private int iProteinUsedCounter = 0;
    private Connection iConn;
    private String iLocation;
    private PrintWriter iOutFile;
    boolean iNewRelease = false;


    public DailyUniprotFetcher(Connection aConn, PrintWriter lOutFile, String aLocation) throws Exception {

        this.iConn = aConn;
        this.iOutFile = lOutFile;
        this.iLocation = aLocation;


        iOutFile.write("########################\n");
        iOutFile.write("Try to download a new UniProt-SwissProt version\n");


        //get the release notes
        URL url = new URL("ftp://ftp.ebi.ac.uk/pub/databases/uniprot/current_release/knowledgebase/complete/reldate.txt");
        URLConnection urlc = url.openConnection();
        InputStream is = urlc.getInputStream();
        Reader r = new InputStreamReader(is);
        StringBuilder inputRelNotes = new StringBuilder();


        int i;
        while ((i = r.read()) != -1) {
            inputRelNotes.append((char) i);
        }
        String lRelNotes = inputRelNotes.toString();
        this.parseReleaseNotes(lRelNotes);
        iOutFile.write("Parsed the release notes\n");
        is.close();
        r.close();


        //create a writer
        iFileName = "sprot_" + iSprotRelease + ".fasta";

        //check if it is a new file
        File lFolder = new File(iLocation);
        File[] lFiles = lFolder.listFiles();
        iNewRelease = true;
        Vector<File> lOldReleases = new Vector<File>();
        for (File lFile : lFiles) {
            System.out.println(lFile.getName());
            if (iFileName.equalsIgnoreCase(lFile.getName())) {
                iNewRelease = false;
            }
            if (lFile.getName().startsWith("sprot")) {
                lOldReleases.add(lFile);
            }
        }

        if (iNewRelease) {
            iOutFile.write("\tNew release found / " + iSprotRelease + "\n");
            //delete the old releases
            for (int f = 0; f < lOldReleases.size(); f++) {
                //lOldReleases.get(f).delete();
            }
            //get the proteins
            Protein[] lProteins = Protein.getAllProteins(iConn);
            boolean[] lFoundInNewRelease = new boolean[lProteins.length];
            for (int p = 0; p < lProteins.length; p++) {
                lFoundInNewRelease[p] = false;
            }
            Vector<Protein> lChangedProteins = new Vector<Protein>();
            iOutFile.write("\tDownloading and unzipping the database\n");
            //create the correct url connection
            try {
                        url = new URL("ftp://ftp.ebi.ac.uk/pub/databases/uniprot/current_release/knowledgebase/complete/uniprot_sprot.fasta.gz");
                        urlc = url.openConnection();
        is = urlc.getInputStream();
        //r = new InputStreamReader(is);
            /*    FTP ftp = new FTP("ftp.ebi.ac.uk");
            ftp.login("anonymous","anonymous");
            ftp.cwd("/pub/databases/uniprot/current_release/knowledgebase/complete");
            ftp.setPassive(true);

            BufferedInputStream in = ftp.getBinary("uniprot_sprot.fasta.gz");
            */
            File f = new File(iLocation+"\\"+iFileName + ".gz");
                System.out.println("start download");
                FileOutputStream out = new FileOutputStream(f);
            IOUtils.copyLarge(is, out);
            out.flush();
            out.close();
            is.close();
            //arrives zipped, must be unzipped first
            GZIPInputStream lZippedInputstream = new GZIPInputStream(new FileInputStream(f));
            //create the writer
            BufferedWriter lDbOutputWriter = new BufferedWriter(new FileWriter(new File(iLocation, iFileName)));
            int lProteinCounter = 0;
            int entry;
            String lHeader = "";
            String lSequence = "";
            boolean lParsingHeader = false;
            boolean lParsingSequence = false;
            while ((entry = lZippedInputstream.read()) != -1) {
                if ((char) entry == '\n') {
                    if (lParsingHeader) {
                        lParsingHeader = false;
                        lParsingSequence = true;
                    }
                } else if ((char) entry == '>') {
                    if (lParsingSequence) {
                        lParsingHeader = true;
                        lParsingSequence = false;
                        this.writeProtein(lDbOutputWriter, lHeader, lSequence);
                        for (int p = 0; p < lProteins.length; p++) {
                            if (lHeader.indexOf(lProteins[p].getSpaccession()) >= 0) {
                                //check the sequence
                                lFoundInNewRelease[p] = true;
                                if (!lSequence.equalsIgnoreCase(lProteins[p].getSequence())) {
                                    //the sequence is not the same anymore
                                    lChangedProteins.add(lProteins[p]);
                                }
                            }
                        }
                        iProteinUsedCounter++;
                        lHeader = "";
                        lSequence = "";
                        lProteinCounter++;
                        if (lProteinCounter % 500 == 0) {
                            iOutFile.write(".");
                            lDbOutputWriter.flush();
                        }
                        if (lProteinCounter % 40000 == 0) {
                            iOutFile.write("\n\t");
                            lDbOutputWriter.flush();
                        }
                    }
                    lParsingSequence = false;
                    lParsingHeader = true;
                    lHeader = lHeader + (char) entry;
                } else if (lParsingHeader) {
                    lHeader = lHeader + (char) entry;
                } else if (lParsingSequence) {
                    lSequence = lSequence + (char) entry;
                }
            }
            //flush and close the db
            if (lHeader.length() != 0 && lSequence.length() != 0) {
                this.writeProtein(lDbOutputWriter, lHeader, lSequence);
                for (int p = 0; p < lProteins.length; p++) {
                    if (lHeader.indexOf(lProteins[p].getSpaccession()) >= 0) {
                        lFoundInNewRelease[p] = true;
                        //check the sequence
                        if (!lSequence.equalsIgnoreCase(lProteins[p].getSequence())) {
                            //the sequence is not the same anymore
                            lChangedProteins.add(lProteins[p]);
                        }
                    }
                }
            }
            lDbOutputWriter.flush();
            lDbOutputWriter.close();
            lZippedInputstream.close();
            iOutFile.write("\tDone downloading and unzipping " + (iProteinUsedCounter + 1) + " proteins\n");


            //delete the changed proteins
            //check the proteins that are not found anymore
            for(int p = 0; p<lProteins.length; p ++){
                if(!lFoundInNewRelease[p]){
                    lChangedProteins.add(lProteins[p]);
                }
            }
            iOutFile.write("\tDeleting " + lChangedProteins.size() + " proteins\n");
            for (int p = 0; p < lChangedProteins.size(); p++) {
                deleteProtein(lChangedProteins.get(p), iConn);
            } } catch (Exception e){
                e.printStackTrace();
            }
        } else{
            //iOutFile.write("\tNo new release found / " + iSprotRelease + "\n");
            File f = new File(iLocation+"\\"+iFileName + ".gz");
            GZIPInputStream lZippedInputstream = new GZIPInputStream(new FileInputStream(f));
            BufferedWriter lDbOutputWriter = new BufferedWriter(new FileWriter(new File(iLocation, iFileName)));
            Protein[] lProteins = Protein.getAllProteins(iConn);
            boolean[] lFoundInNewRelease = new boolean[lProteins.length];
            Vector<Protein> lChangedProteins = new Vector<Protein>();
            int lProteinCounter = 0;
            int entry;
            String lHeader = "";
            String lSequence = "";
            boolean lParsingHeader = false;
            boolean lParsingSequence = false;
            while ((entry = lZippedInputstream.read()) != -1) {
                if ((char) entry == '\n') {
                    if (lParsingHeader) {
                        lParsingHeader = false;
                        lParsingSequence = true;
                    }
                } else if ((char) entry == '>') {
                    if (lParsingSequence) {
                        lParsingHeader = true;
                        lParsingSequence = false;
                        this.writeProtein(lDbOutputWriter, lHeader, lSequence);
                        for (int p = 0; p < lProteins.length; p++) {
                            if (lHeader.indexOf(lProteins[p].getSpaccession()) >= 0) {
                                //check the sequence
                                lFoundInNewRelease[p] = true;
                                if (!lSequence.equalsIgnoreCase(lProteins[p].getSequence())) {
                                    //the sequence is not the same anymore
                                    lChangedProteins.add(lProteins[p]);
                                }
                            }
                        }
                        iProteinUsedCounter++;
                        lHeader = "";
                        lSequence = "";
                        lProteinCounter++;
                        if (lProteinCounter % 500 == 0) {
                            iOutFile.write(".");
                            lDbOutputWriter.flush();
                        }
                        if (lProteinCounter % 40000 == 0) {
                            iOutFile.write("\n\t");
                            lDbOutputWriter.flush();
                        }
                    }
                    lParsingSequence = false;
                    lParsingHeader = true;
                    lHeader = lHeader + (char) entry;
                } else if (lParsingHeader) {
                    lHeader = lHeader + (char) entry;
                } else if (lParsingSequence) {
                    lSequence = lSequence + (char) entry;
                }
            }
            //flush and close the db
            if (lHeader.length() != 0 && lSequence.length() != 0) {
                this.writeProtein(lDbOutputWriter, lHeader, lSequence);
                for (int p = 0; p < lProteins.length; p++) {
                    if (lHeader.indexOf(lProteins[p].getSpaccession()) >= 0) {
                        lFoundInNewRelease[p] = true;
                        //check the sequence
                        if (!lSequence.equalsIgnoreCase(lProteins[p].getSequence())) {
                            //the sequence is not the same anymore
                            lChangedProteins.add(lProteins[p]);
                        }
                    }
                }
            }
            lDbOutputWriter.flush();
            lDbOutputWriter.close();
            lZippedInputstream.close();
            iOutFile.write("\tDone downloading and unzipping " + (iProteinUsedCounter + 1) + " proteins\n");


            //delete the changed proteins
            //check the proteins that are not found anymore
            for(int p = 0; p<lProteins.length; p ++){
                if(!lFoundInNewRelease[p]){
                    lChangedProteins.add(lProteins[p]);
                }
            }
            iOutFile.write("\tDeleting " + lChangedProteins.size() + " proteins\n");
            for (int p = 0; p < lChangedProteins.size(); p++) {
                deleteProtein(lChangedProteins.get(p), iConn);
            }
        }
    }

    public boolean isNewRelease() {
        return iNewRelease;
    }

    /**
     * This method will parse the uniprot release notes
     *
     * @param lRelNotes
     */
    public void parseReleaseNotes(String lRelNotes) {
        iUniprotRelease = lRelNotes.substring(lRelNotes.indexOf("Knowledgebase Release ") + 22, lRelNotes.indexOf(" consists "));
        iSprotRelease = lRelNotes.substring(lRelNotes.indexOf("Swiss-Prot Release ") + 19, lRelNotes.indexOf(" of", lRelNotes.indexOf("Swiss-Prot Release ")));
        iTremblRelease = lRelNotes.substring(lRelNotes.indexOf("TrEMBL Release ") + 15, lRelNotes.indexOf(" of", lRelNotes.indexOf("TrEMBL Release ")));
    }


    /**
     * This method will write the protein if the taxonomy is correct
     *
     * @param lOutputWriter The writer
     * @param lHeader       The fasta protein header
     * @param lSequence     The sequence
     * @throws IOException
     */
    public void writeProtein(BufferedWriter lOutputWriter, String lHeader, String lSequence) throws IOException {
        lOutputWriter.write(lHeader + "\n" + lSequence + "\n");
    }


    public void deleteProtein(Protein lProtein, Connection iConn) throws SQLException {
        //delete pdb
        PreparedStatement prep = iConn.prepareStatement("delete from pdb where l_proteinid = ?");
        prep.setLong(1, lProtein.getProteinid());
        prep.executeUpdate();
        prep.close();
        //delete pdb_block
        PreparedStatement prep2 = iConn.prepareStatement("delete from pdb_block where l_proteinid = ?");
        prep2.setLong(1, lProtein.getProteinid());
        prep2.executeUpdate();
        prep2.close();
        //delete domain
        PreparedStatement prep3 = iConn.prepareStatement("delete from domain where l_proteinid = ?");
        prep3.setLong(1, lProtein.getProteinid());
        prep3.executeUpdate();
        prep3.close();
        //delete go
        PreparedStatement prep4 = iConn.prepareStatement("delete from go where l_proteinid = ?");
        prep4.setLong(1, lProtein.getProteinid());
        prep4.executeUpdate();
        prep4.close();
        //delete homologene_accession
        PreparedStatement prep5 = iConn.prepareStatement("delete from homologene_accession where l_proteinid = ?");
        prep5.setLong(1, lProtein.getProteinid());
        prep5.executeUpdate();
        prep5.close();
        //delete orthologue
        PreparedStatement prep6 = iConn.prepareStatement("delete from orthologue where l_proteinid = ?");
        prep6.setLong(1, lProtein.getProteinid());
        prep6.executeUpdate();
        prep6.close();
        //delete peptide_location
        PreparedStatement prep7 = iConn.prepareStatement("delete from peptide_location where l_proteinid = ?");
        prep7.setLong(1, lProtein.getProteinid());
        prep7.executeUpdate();
        prep7.close();
        //delete protein
        PreparedStatement prep8 = iConn.prepareStatement("delete from protein where proteinid = ?");
        prep8.setLong(1, lProtein.getProteinid());
        prep8.executeUpdate();
        prep8.close();
    }
}
