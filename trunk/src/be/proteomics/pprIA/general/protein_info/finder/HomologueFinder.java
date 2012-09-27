package be.proteomics.pprIA.general.protein_info.finder;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 26-mrt-2008
 * Time: 14:51:54
 * To change this template use File | Settings | File Templates.
 */
public class HomologueFinder {

    private String[] iNcbiAccessions;
    private String[] homologueId;
    private String iUrl;
    private Vector homolgueProteins = new Vector();
    private Vector uniqueHomologueProteins = new Vector();

    public HomologueFinder(String[] aNcbiAccessions){

        iNcbiAccessions = aNcbiAccessions;
        homologueId = new String[iNcbiAccessions.length];
        for(int i = 0; i<iNcbiAccessions.length ; i ++){
            String urlMake = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=homologene&term=" + iNcbiAccessions[i].replaceAll(" ","");
            String htmlPage = readUrl(urlMake);
            String count = htmlPage.substring(htmlPage.indexOf("<Count>") + 7, htmlPage.indexOf("</Count>"));
            if(count.equalsIgnoreCase("1") ){
                homologueId[i] = htmlPage.substring(htmlPage.indexOf("<Id>") + 4, htmlPage.indexOf("</Id>"));
            } else {
                homologueId[i] = "0";
            }
            //System.out.println("HomoloGene found for " + ncbiAccessions[i] + " (" + homologueId[i] + ")");
        }

        for(int i = 0; i<homologueId.length; i++){
            if(!homologueId[i].equalsIgnoreCase("0")){
                String urlMake = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=homologene&id=" + homologueId[i];
                String htmlPage = readUrl(urlMake);
                homolgueProteins.add(this.findHomolguesFromHtml(htmlPage));
            }

        }

        for(int i = 0; i<homolgueProteins.size(); i ++){

            String[] prot = (String[]) homolgueProteins.get(i);
            for(int k = 0; k<prot.length ; k ++){
                String protAcc = prot[k];
                if(protAcc.startsWith("NP")){
                    boolean found = false;
                    for(int j = 0; j<uniqueHomologueProteins.size(); j ++){
                        String protMatch = (String) uniqueHomologueProteins.get(j);
                        if(protMatch.equalsIgnoreCase(protAcc)){
                            found = true;
                        }
                    }
                    if(!found){
                        uniqueHomologueProteins.add(protAcc);
                    }
                }
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
	        c.setConnectTimeout(5000);
            c.setReadTimeout(5000);
	       // BufferedInputStream in = new BufferedInputStream(c.getInputStream());
            InputStreamReader in = new InputStreamReader(c.getInputStream());
            LineNumberReader r = new LineNumberReader(new BufferedReader(in));

            String i;

            while ((i = r.readLine()) != null) {
                input.append(i);
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
            System.out.println("Connection error for url: " + iUrl);
            System.out.println("Reconnecting ...");
            String html = readUrl(iUrl);
            return html;
        }
        return htmlPage;
    }
    public String[] findHomolguesFromHtml(String aHtml){
        int startPos = 0;
        String iHtml = aHtml;
        Vector accs = new Vector();
        while(iHtml.indexOf("prot-acc", startPos) > 0){
            String acc = iHtml.substring(iHtml.indexOf("prot-acc \"",startPos) + 10 , iHtml.indexOf("\"",iHtml.indexOf("prot-acc \"",startPos) + 10));
            accs.add(acc);
            startPos = iHtml.indexOf("prot-len", startPos) + 5;
        }
        String[] accesions = new String[accs.size()];
        accs.toArray(accesions);
        return accesions;
    }

    public String[] getHomologueIds(){
        return this.homologueId;
    }
    public String[] getHomologueProteinAcc(){
        String[] accessions = new String[uniqueHomologueProteins.size()];
        uniqueHomologueProteins.toArray(accessions);
        //System.out.print(uniqueHomologueProteins.size());
        return accessions;
    }
}