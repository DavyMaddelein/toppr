package be.proteomics.pprIA.general.protein_info.finder;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ConnectException;
import java.io.BufferedInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 27-mrt-2008
 * Time: 11:52:22
 * To change this template use File | Settings | File Templates.
 */
public class SpToUniProt {
    private String iSpAccession;
        private String[] uniprotAccessions;
        private String iUrl;

        //constructor
        public SpToUniProt(String aSpAccession) {
            this.iSpAccession = aSpAccession;
            String urlMake = "http://www.ebi.ac.uk/Tools/picr/rest/getUPIForAccession?accession=" + iSpAccession + "&database=SWISSPROT";
            String xmlPage = readUrl(urlMake);
            uniprotAccessions = findUniAccessions(xmlPage);
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

        public String[] findUniAccessions(String xml) {
            String[] ncbiA;
            Vector accessions = new Vector();
            int startIndex = 0;
            while (xml.indexOf("<ns2:databaseDescription>UniProtKB/Swiss-Prot</ns2:databaseDescription>", startIndex) != -1) {
                startIndex = xml.indexOf("<ns2:databaseDescription>UniProtKB/Swiss-Prot</ns2:databaseDescription>", startIndex) + 1;

                String feature = xml.substring(xml.indexOf("<ns2:identicalCrossReferences>", startIndex - 200), xml.indexOf("</ns2:identicalCrossReferences>", xml.indexOf("<ns2:identicalCrossReferences>", startIndex - 200)));
                //System.out.println(feature);
                String sub = feature.substring(feature.indexOf("<ns2:accession>") + 15, feature.indexOf("</ns2:accession>"));
                //System.out.println(sub);
                accessions.add(sub);

            }
            ncbiA = new String[accessions.size()];
            accessions.toArray(ncbiA);
            return ncbiA;
        }

        public String[] getUniAccessions() {
            return uniprotAccessions;
        }
    }

