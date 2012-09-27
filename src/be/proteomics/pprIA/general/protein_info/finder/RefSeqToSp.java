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

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 24-mrt-2008
 * Time: 11:02:46
 * To change this template use File | Settings | File Templates.
 */
public class RefSeqToSp {
    private String iNcbiAccession;
    private String[] spAccessions;
    private String iUrl;

    //constructor
    public RefSeqToSp(String aNcbiAccession) {
        this.iNcbiAccession = aNcbiAccession;

        String urlMake = "http://www.ebi.ac.uk/Tools/picr/rest/getUPIForAccession?accession=" + iNcbiAccession + "&database=SWISSPROT";
        System.out.println(iNcbiAccession);
        String xmlPage = readUrl(urlMake);
        spAccessions = findSpAccessions(xmlPage);
    }

    public String readUrl(String aUrl) {
        this.iUrl = aUrl;
        String htmlPage = "";
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

    public String[] findSpAccessions(String xml) {
        String[] ncbiA;
        Vector accessions = new Vector();
        int startIndex = 0;
        System.out.println(xml);
        while (xml.indexOf("<databaseName>SWISSPROT</databaseName>", startIndex) != -1) {
            startIndex = xml.indexOf("<identicalCrossReferences>", startIndex);
            String feature = xml.substring(startIndex,xml.indexOf("</identicalCrossReferences>",startIndex));
            //System.out.println(feature);
            String sub = feature.substring(feature.indexOf("<accession>") + 11, feature.indexOf("</accession>"));
            //System.out.println(sub);
            accessions.add(sub);
            startIndex = xml.indexOf("</identicalCrossReferences>",startIndex)+27;
        }
        ncbiA = new String[accessions.size()];
        accessions.toArray(ncbiA);
        return ncbiA;
    }

    public String[] getSpAccessions() {
        return spAccessions;
    }
}
