package be.proteomics.pprIA.update;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 07-Jun-2010
 * Time: 15:13:22
 * To change this template use File | Settings | File Templates.
 */
public class DailyPdbFilesSaver {

    private Connection iConn;
    private String iLocation;
    private Vector<String> ids = new Vector<String>();
    private String iUrl;
    private PrintWriter iOutFile;

    public DailyPdbFilesSaver(Connection aConn, PrintWriter lOutFile, String lLocation) {

        this.iOutFile = lOutFile;
        this.iConn = aConn;
        this.iLocation = lLocation + "pdbFiles/";

        try {

            //get pdbfileids
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("Select d.pdbaccession from pdb as d group by d.pdbaccession");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                ids.add(rs.getString(1));

            }
            rs.close();
            prep.close();

            iOutFile.write("###################\n");
            iOutFile.write("Trying to find " + ids.size() + " pdb files");
            System.out.println("Trying to find " + ids.size() + " pdb files");
            for (int i = 0; i < ids.size(); i++) {
                String saveLocation = iLocation + ids.get(i) + ".pdb";
                //check if the file already exists
                File f = new File(saveLocation);
                if (f.exists()) {
                    System.out.println("\tPdb file : " + ids.get(i) + " already saved to " + saveLocation + "  (" + (i + 1) + "/" + ids.size() + ")\n");
                    iOutFile.write("\tPdb file : " + ids.get(i) + " already saved to " + saveLocation + "  (" + (i + 1) + "/" + ids.size() + ")\n");
                } else {
                    String url = "http://www.rcsb.org/pdb/files/" + ids.get(i) + ".pdb";
                    System.out.println(url);
                    readUrl(url, saveLocation);
                    iOutFile.write("\tPdb file : " + ids.get(i) + " saved to " + saveLocation + "  (" + (i + 1) + "/" + ids.size() + ")\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readUrl(String aUrl, String s) {
        this.iUrl = aUrl;
        try {
            URL myURL = new URL(aUrl);
            HttpURLConnection conn =(HttpURLConnection) myURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            File f = new File(s);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            int len;
            while ((len = in.read()) != -1) {
                out.write(len);
            }
            out.flush();
            out.close();
            in.close();
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            iOutFile.write("\tConnect error for url: " + iUrl + "\n");
        } catch (IOException e) {
            iOutFile.write("\tServer returned HTTP response code: 500 for URL: " + iUrl + "\n");
        }
    }
}
