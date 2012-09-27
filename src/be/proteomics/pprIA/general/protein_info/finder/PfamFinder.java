package be.proteomics.pprIA.general.protein_info.finder;

import be.proteomics.ppr.db.accessors.Protein;
import be.proteomics.pprIA.das.readers.DasAnnotationServerResultReader;
import be.proteomics.pprIA.das.readers.DasFeature;
import be.proteomics.pprIA.general.protein_info.Domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
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
 * Time: 16:27:43
 * To change this template use File | Settings | File Templates.
 */
public class PfamFinder {
    private String iUrl;
    private DasAnnotationServerResultReader reader;
    private be.proteomics.pprIA.general.protein_info.Domain[] pfamDomain;
    private boolean firstTry = true;

    public PfamFinder(String protein) {

        //find features
        String urlMake = "http://das.sanger.ac.uk/das/pfam/features?segment=" + protein;
        readUrl(urlMake);

        //select all features
        try{
            DasFeature[] features = reader.getAllFeatures();
            Vector pos = new Vector();
            Vector types = new Vector();
            for (int j = 0; j < features.length; j++) {
                if (features[j].getMethod().equalsIgnoreCase("Pfam-A") && features[j].isValid()) {
                    Domain pfam = new Domain();
                    pfam.setStart(features[j].getStart());
                    pfam.setEnd(features[j].getEnd());
                    String[] links = features[j].getLinkHref();
                    String link = links[0];
                    String id = link.substring(link.indexOf("?") + 1);
                    pfam.setId(id);
                    pfam.setType("Pfam");
                    pfam.setLabel(features[j].getFeatureId());
                    pos.add(pfam);
                }
            }
            pfamDomain = new be.proteomics.pprIA.general.protein_info.Domain[pos.size()];
            pos.toArray(pfamDomain);
        } catch (NullPointerException e){
            pfamDomain = new Domain[0];
        }
    }

    public be.proteomics.pprIA.general.protein_info.Domain[] getPfamDomain() {
        return pfamDomain;
    }

    public void readUrl(String aUrl) {
        this.iUrl = aUrl;
        try {
            URL myURL = new URL(aUrl);
            StringBuilder input = new StringBuilder();
            HttpURLConnection c = (HttpURLConnection) myURL.openConnection();
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
            System.out.println("I/O exception for url " + iUrl);
        }
    }
   public static void main(String[] args) {
       Connection lConn = null;
        String driverClass = "com.mysql.jdbc.Driver";
        String userName = "niklaas";
        String pass = "niklaas,13*";
        try {
            com.mysql.jdbc.Driver d = (com.mysql.jdbc.Driver) Class.forName(driverClass).newInstance();
            Properties lProps = new Properties();
            lProps.put("user", userName);
            lProps.put("password", pass);
            lConn = d.connect("jdbc:mysql://localhost/ppr", lProps);
            Protein[] lProteins = Protein.getAllProteins(lConn);
            for(int  i = 0; i<lProteins.length; i ++){
                System.out.println(lProteins[i].getSpaccession() + " " + (i+1) + " / " + lProteins.length);
                PfamFinder lPfam = new PfamFinder(lProteins[i].getSpaccession());
                be.proteomics.pprIA.general.protein_info.Domain[] lPfamDomains = lPfam.getPfamDomain();
                for (int g = 0; g < lPfamDomains.length; g++) {
                    be.proteomics.pprIA.general.protein_info.Domain dom = lPfamDomains[g];
                    HashMap aParams = new HashMap(3);
                    aParams.put(be.proteomics.ppr.db.accessors.Domain.L_PROTEINID, lProteins[i].getProteinid());
                    aParams.put(be.proteomics.ppr.db.accessors.Domain.TYPE, dom.getType());
                    aParams.put(be.proteomics.ppr.db.accessors.Domain.NAME, dom.getLabel());
                    String id = dom.getId();
                    id = id.substring(id.indexOf("=") + 1);
                    aParams.put(be.proteomics.ppr.db.accessors.Domain.DOMAINTERMID, id);
                    aParams.put(be.proteomics.ppr.db.accessors.Domain.START, (long) dom.getStart());
                    aParams.put(be.proteomics.ppr.db.accessors.Domain.END, (long) dom.getEnd());
                    be.proteomics.ppr.db.accessors.Domain domToStore = new be.proteomics.ppr.db.accessors.Domain(aParams);
                    domToStore.persist(lConn);
                }
            }

        } catch (ClassNotFoundException cnfe) {
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


       //new PfamFinder("P60709");

    }
}



