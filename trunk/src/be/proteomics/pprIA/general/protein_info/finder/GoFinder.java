package be.proteomics.pprIA.general.protein_info.finder;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import be.proteomics.pprIA.general.protein_info.GoTerm;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 18-mrt-2008
 * Time: 15:51:07
 * To change this template use File | Settings | File Templates.
 */
public class GoFinder{
    private String iProtein;
    private String iUrl;
    private String html;
    private GoTerm[] terms;
    private boolean firstTry = true;


    public GoFinder(String aProtein){
        this.iProtein = aProtein;
        //find features
        String urlMake = "http://www.uniprot.org/uniprot/" + iProtein + ".txt";
        readUrl(urlMake);
        if(html == null){
            terms = new GoTerm[0];
        } else {
            terms = getGoFromHtml();    
        }



    }

    public GoTerm[] getGoFromHtml(){

        Vector gos = new Vector();
        int start = 0;

        start = html.indexOf("DR   GO;"); 
        while(start > -1){
            String goterm = html.substring(html.indexOf("DR   GO;", start), html.indexOf("\n", html.indexOf("DR   GO;", start)));
            String id = goterm.substring(goterm.indexOf("DR   GO;") + 9, goterm.indexOf(";", goterm.indexOf("DR   GO;") + 10));
            goterm = goterm.substring(goterm.indexOf(";", goterm.indexOf("DR   GO;") + 10) + 2);
            String type = goterm.substring(0, goterm.indexOf(":"));
            String name = goterm.substring(goterm.indexOf(":") + 1, goterm.indexOf(";"));
            GoTerm goFound = new GoTerm();
            goFound.setId(id);
            goFound.setCategory(type);
            goFound.setName(name);
            gos.add(goFound);
            start = html.indexOf("DR   GO;", start + 1);    
        }

        GoTerm[] go = new GoTerm[gos.size()];
        gos.toArray(go);
        return go;
    }

    public void readUrl(String aUrl){
        this.iUrl = aUrl;

        try {
            URL myURL = new URL(aUrl);
            StringBuilder input = new StringBuilder();
    	    HttpURLConnection c = (HttpURLConnection)myURL.openConnection();
	        BufferedInputStream in = new BufferedInputStream(c.getInputStream());
	        Reader r = new InputStreamReader(in);

	        int i;
	        while ((i = r.read()) != -1) {
	    	    input.append((char) i);
	        }

            html = input.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ConnectException e){
            System.out.println("Connect exception for url " + iUrl);
            if(firstTry){
                this.readUrl(iUrl);
            }
            firstTry = false;
        } catch (IOException e) {
        }
    }

    public GoTerm[] getGoTerms(){
        return this.terms;
    }

    public static void main(String[] args) {
        new GoFinder("Q05524");

    }
}
