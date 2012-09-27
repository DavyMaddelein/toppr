package be.proteomics.pprIA.general.protein_info.finder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 09-Jun-2010
 * Time: 10:02:55
 * To change this template use File | Settings | File Templates.
 */
public class GetSequence {
    private String iSequence = "NO_SEQUENCE_FOUND";
    private boolean firstTry = true;
    private String iUrl = "";

    public GetSequence(String aProtein) {
        iSequence = readSequenceUrl("http://www.ebi.ac.uk/das-srv/uniprot/das/uniprot/sequence?segment=" + aProtein);
    }

    public String readSequenceUrl(String aUrl) {
        String sequence = null;
        iUrl = aUrl;
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

            String inputString = input.toString();

            sequence = inputString.substring(inputString.indexOf(">", inputString.indexOf("<SEQUENCE")) + 1, inputString.indexOf("</SEQ"));
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("No sequence found!!!!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            System.out.println("Connect exception for url " + iUrl);
            if (firstTry) {
                this.readSequenceUrl(iUrl);
            }
            firstTry = false;
        } catch (IOException e) {
            System.out.println("I/O exception for url " + iUrl);
        }

        return sequence;
    }

    public String getSequence() {
        return iSequence;
    }

    public static void main(String[] args) {
        new GetSequence("Q14444");

    }
}

