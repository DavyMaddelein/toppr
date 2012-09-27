package be.proteomics.pprIA.general.protein_info.finder;


import be.proteomics.pprIA.das.readers.DasAnnotationServerResultReader;
import be.proteomics.pprIA.das.readers.DasFeature;

import java.util.Vector;
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
 * Date: 18-mrt-2008
 * Time: 16:33:18
 * To change this template use File | Settings | File Templates.
 */
public class SmartFinder{
    private String iUrl;
    private DasAnnotationServerResultReader reader;
    private be.proteomics.pprIA.general.protein_info.Domain[] smartDomain;
    private boolean firstTry = true;

    public SmartFinder(String protein){

        //find features
        String urlMake = "http://smart.embl.de/smart/das/smart/features?segment=" + protein;
        readUrl(urlMake);

        //select all features
        DasFeature[] features = reader.getAllFeatures();
        Vector pos = new Vector();
        Vector types = new Vector();
        for(int j = 0; j<features.length ; j ++){
                be.proteomics.pprIA.general.protein_info.Domain smart = new be.proteomics.pprIA.general.protein_info.Domain();
                smart.setStart(features[j].getStart());
                smart.setEnd(features[j].getEnd());
                smart.setId(features[j].getFeatureLabel());
                smart.setType("Smart");
                smart.setLabel(features[j].getFeatureId());
                pos.add(smart);                            
        }
        smartDomain = new be.proteomics.pprIA.general.protein_info.Domain[pos.size()];
        pos.toArray(smartDomain);
    }

    public be.proteomics.pprIA.general.protein_info.Domain[] getSmartDomain() {
        return smartDomain;
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
            reader = new DasAnnotationServerResultReader(input.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ConnectException e){
            System.out.println("Connect exception for url " + iUrl);
            if(firstTry){
                this.readUrl(iUrl);
            }
            firstTry = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new SmartFinder("P60709");

    }
}