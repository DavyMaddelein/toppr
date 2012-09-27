package be.proteomics.pprIA.general.homologue;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ConnectException;
import java.io.BufferedInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 31-jan-2008
 * Time: 16:19:50
 * To change this template use File | Settings | File Templates.
 */
public class HomoloGeneIdFinder {

    private String[] ncbiAccessions;
    private int[] homologueId;
    private String iUrl;

    public HomoloGeneIdFinder(String[] accessions){
        ncbiAccessions = accessions;
        homologueId = new int[ncbiAccessions.length];
        for(int i = 0; i<ncbiAccessions.length ; i ++){
            String urlMake = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=homologene&term=" + ncbiAccessions[i];
            String htmlPage = readUrl(urlMake);
            if (!htmlPage.contains("ERROR")) {
                if((htmlPage.substring(htmlPage.indexOf("<Count>") + 7, htmlPage.indexOf("</Count>"))).equalsIgnoreCase("1")){
                    homologueId[i] = Integer.valueOf(htmlPage.substring(htmlPage.indexOf("<Id>") + 4, htmlPage.indexOf("</Id>")));
                } else {
                    homologueId[i] = 0;
                }
            } else {
                homologueId[i] = 0;
            }
        }

    }
    public String readUrl(String aUrl){
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlPage;
    }
    public int[] getHomologueIds(){
        return this.homologueId;
    }
    public static void main(String[] args){
        HomoloGeneIdFinder test = new HomoloGeneIdFinder(new String[]{"NP_783596.1"});
        int[] results = test.getHomologueIds();
        System.out.println(results[0]);
    }
}
