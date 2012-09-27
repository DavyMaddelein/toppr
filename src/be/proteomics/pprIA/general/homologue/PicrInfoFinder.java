package be.proteomics.pprIA.general.homologue;

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
 * Date: 31-jan-2008
 * Time: 16:17:30
 * To change this template use File | Settings | File Templates.
 */


public class PicrInfoFinder {

    private String iSpAccession;
    private String iUrl;
    private String[] ncbiAccessions;

    //constructor
    public PicrInfoFinder(String aSpAccession){
        /*this.iSpAccession = aSpAccession;

        String urlMake = "http://www.ebi.ac.uk/Tools/picr/rest/getUPIForAccession?accession=" + iSpAccession + "&database=PDB&database=REFSEQ";
        String xmlPage = readUrl(urlMake);

        ncbiAccessions = findNcbiAccessions(xmlPage);*/
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
                    String[] splitRefSeq = qi.split("; ");
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

    /*public String readUrl(String aUrl){
        this.iUrl = aUrl;
        String htmlPage = "";
        try {
            URL myURL=new URL(aUrl);
            StringBuilder input = new StringBuilder();

            HttpURLConnection c = (HttpURLConnection)myURL.openConnection();
	        BufferedInputStream in = new BufferedInputStream(c.getInputStream());
	        Reader r = new InputStreamReader(in);

	        int i;

            while ((i = r.read()) != -1) {
	    	    input.append((char) i);
	        }

            htmlPage = input.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            System.out.println("Connection error for url: " + iUrl);
            System.out.println("Reconnecting ...");
            String html = readUrl(iUrl);
            return html;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return htmlPage;
    }     */

    public String[] findPdbAccessions(String xml){
        String[] pdbA;
        Vector accessions = new Vector();
        int startIndex = 0;
        while(xml.indexOf("<ns2:databaseDescription>PDB</ns2:databaseDescription>",startIndex) != -1){
            startIndex = xml.indexOf("<ns2:databaseDescription>PDB</ns2:databaseDescription>",startIndex) + 1;

            String feature = xml.substring(xml.indexOf("<ns2:logicalCrossReferences>",startIndex - 200), xml.indexOf("</ns2:logicalCrossReferences>",xml.indexOf("<ns2:logicalCrossReferences>",startIndex - 200)));
            //System.out.println(feature);
            String sub =  feature.substring(feature.indexOf("<ns2:accession>") + 15,feature.indexOf("</ns2:accession>") );
            //System.out.println(sub);
            accessions.add(sub);

        }
        pdbA = new String[accessions.size()];
        accessions.toArray(pdbA);
        return pdbA;
    }

    public String[] findNcbiAccessions(String xml){
        String[] ncbiA;
        Vector accessions = new Vector();
        int startIndex = 0;
        while(xml.indexOf("<ns2:databaseDescription>RefSeq release + updates</ns2:databaseDescription>",startIndex) != -1){
            startIndex = xml.indexOf("<ns2:databaseDescription>RefSeq release + updates</ns2:databaseDescription>",startIndex) + 1;

            String feature = xml.substring(xml.indexOf("<ns2:identicalCrossReferences>",startIndex - 200), xml.indexOf("</ns2:identicalCrossReferences>",xml.indexOf("<ns2:identicalCrossReferences>",startIndex - 200)));
            //System.out.println(feature);
            String sub =  feature.substring(feature.indexOf("<ns2:accession>") + 15,feature.indexOf("</ns2:accession>") );
            //System.out.println(sub);
            accessions.add(sub);

        }
        ncbiA = new String[accessions.size()];
        accessions.toArray(ncbiA);
        return ncbiA;
    }

    public String[] getNcbiAccessions(){
        return this.ncbiAccessions;
    }
    public static void main(String[] args){
        PicrInfoFinder test = new PicrInfoFinder("Q8CGP1");
        String[] results = test.getNcbiAccessions();
        System.out.println(results[0]);
    }
}

