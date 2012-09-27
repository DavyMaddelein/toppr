package be.proteomics.pprIA.general.protein_info.finder;

import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ConnectException;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 24-mrt-2008
 * Time: 10:46:43
 * To change this template use File | Settings | File Templates.
 */
public class SpToRefSeq {
    private String iSpAccession;
    private String[] ncbiAccessions;
    private String iUrl;

    //constructor
    public SpToRefSeq(String aSpAccession) {
        this.iSpAccession = aSpAccession;
        //String urlMake = "http://www.ebi.ac.uk/Tools/picr/rest/getUPIForAccession?accession=" + iSpAccession + "&database=REFSEQ";
        String urlMake = "http://www.uniprot.org/uniprot/"+iSpAccession+".txt";
        ncbiAccessions = readUrl(urlMake);
    }

    public String[] readUrl(String aUrl) {
        this.iUrl = aUrl;
        String[] ncbiA;
        Vector<String> accessions = new Vector<String>();
        try {
            URL myURL = new URL(aUrl);

            HttpURLConnection c = (HttpURLConnection) myURL.openConnection();
            //BufferedInputStream in = new BufferedInputStream(c.getInputStream());
            InputStreamReader in = new InputStreamReader(c.getInputStream());
            LineNumberReader r = new LineNumberReader(new BufferedReader(in));

            String i;

            while ((i = r.readLine()) != null) {
                if(i.contains("DR   RefSeq")) {
                    String qi = i.substring(i.indexOf(";")+2,i.length()-1);
                    String[] splitRefSeq = qi.split(";");
                    Collections.addAll(accessions, splitRefSeq);

                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            System.out.println("Connection error for url: " + iUrl);
            System.out.println("Reconnecting ...");
            String[] html = readUrl(iUrl);
            return  html;
        } catch (IOException e) {
            e.printStackTrace();
        }
        ncbiA = new String[accessions.size()];
        accessions.toArray(ncbiA);
        return ncbiA;
    }

    public String[] findNcbiAccessions(String xml) {
        String[] ncbiA;
        Vector accessions = new Vector();
        int startIndex = 0;
        int newIndex = 0;
        while ((startIndex = xml.indexOf("DR   RefSeq", startIndex)) != -1) {
            //startIndex = xml.indexOf("DR   RefSeq", startIndex) + 1;

            String feature = xml.substring(startIndex, (newIndex = xml.indexOf("DR   RefSeq",startIndex)));
            //System.out.println(feature);
            startIndex = newIndex;
            String sub = feature.substring(feature.indexOf("<ns2:accession>") + 15, feature.indexOf("</ns2:accession>"));
            //System.out.println(sub);
            accessions.add(sub);

        }
        ncbiA = new String[accessions.size()];
        accessions.toArray(ncbiA);
        return ncbiA;
    }

    public String[] getNcbiAccessions() {
        return ncbiAccessions;
    }
}
