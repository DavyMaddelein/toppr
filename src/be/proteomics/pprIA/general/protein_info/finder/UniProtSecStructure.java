package be.proteomics.pprIA.general.protein_info.finder;

import be.proteomics.pprIA.das.readers.DasAnnotationServerResultReader;
import be.proteomics.pprIA.das.readers.DasFeature;
import be.proteomics.pprIA.das.readers.StartEndPosition;

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
 * User: Niklaas
 * Date: 09-Jun-2010
 * Time: 08:14:54
 * To change this template use File | Settings | File Templates.
 */
public class UniProtSecStructure {
    private String iUrl;
    private DasAnnotationServerResultReader reader;
    private StartEndPosition[] positionsUnOrdered;
    private StartEndPosition[] positionsOrdered;
    private boolean firstTry = true;

    private Integer[] orderedTypes;
    private Integer[] orderedTypesBeginToEnd;
    private String prediction = "";

    public UniProtSecStructure(String protein, String aSequence) {

        String sequence = aSequence;

        //find features
        String urlMake = "http://www.ebi.ac.uk/das-srv/uniprot/das/uniprot/features?segment=" + protein;
        readUrl(urlMake);
        Vector searchForVector = new Vector();

        searchForVector.add("alpha_helix");
        searchForVector.add("beta_strand");
        searchForVector.add("turn");
        searchForVector.add("coil");
        searchForVector.add("helix");

        //select all features
        if (reader != null) {
            DasFeature[] features = reader.getAllFeatures();
            Vector pos = new Vector();
            Vector types = new Vector();
            for (int j = 0; j < features.length; j++) {
                for (int i = 0; i < searchForVector.size(); i++) {
                    if (features[j].getType().equalsIgnoreCase((String) searchForVector.elementAt(i))) {
                        pos.add(new StartEndPosition(features[j].getStart(), features[j].getEnd()));
                        types.add(i);
                    }
                }
            }
            positionsUnOrdered = new StartEndPosition[pos.size()];
            pos.toArray(positionsUnOrdered);
            Integer[] typesUnOrdered = new Integer[types.size()];
            types.toArray(typesUnOrdered);

            //sort
            StartEndPosition[] positionsOrderedBeginToEnd = orderPositionsBeginToEnd(positionsUnOrdered, typesUnOrdered);

            String predictionResult = "";
            for (int i = 0; i < positionsOrderedBeginToEnd.length; i++) {
                if (i == 0) {
                    int length = positionsOrderedBeginToEnd[i].getStartPosition() - 1;
                    for (int j = 0; j < length; j++) {
                        predictionResult = predictionResult + "-";
                    }

                }
                if (orderedTypesBeginToEnd[i] == 0 || orderedTypesBeginToEnd[i] == 4) {
                    int length = positionsOrderedBeginToEnd[i].getEndPosition() - positionsOrderedBeginToEnd[i].getStartPosition() + 1;
                    for (int j = 0; j < length; j++) {
                        predictionResult = predictionResult + "H";
                    }

                }
                if (orderedTypesBeginToEnd[i] == 1) {
                    int length = positionsOrderedBeginToEnd[i].getEndPosition() - positionsOrderedBeginToEnd[i].getStartPosition() + 1;
                    for (int j = 0; j < length; j++) {
                        predictionResult = predictionResult + "E";
                    }

                }
                if (orderedTypesBeginToEnd[i] == 2) {
                    int length = positionsOrderedBeginToEnd[i].getEndPosition() - positionsOrderedBeginToEnd[i].getStartPosition() + 1;
                    for (int j = 0; j < length; j++) {
                        predictionResult = predictionResult + "T";
                    }

                }
                if (orderedTypesBeginToEnd[i] == 3) {
                    int length = positionsOrderedBeginToEnd[i].getEndPosition() - positionsOrderedBeginToEnd[i].getStartPosition() + 1;
                    for (int j = 0; j < length; j++) {
                        predictionResult = predictionResult + "-";
                    }

                }
                if (i == positionsOrderedBeginToEnd.length - 1) {
                    int length = sequence.length() - positionsOrderedBeginToEnd[i].getEndPosition();
                    for (int j = 0; j < length; j++) {
                        predictionResult = predictionResult + "-";
                    }

                } else {
                    int length = positionsOrderedBeginToEnd[i + 1].getStartPosition() - positionsOrderedBeginToEnd[i].getEndPosition() - 1;
                    for (int j = 0; j < length; j++) {
                        predictionResult = predictionResult + "-";
                    }

                }

            }


            prediction = predictionResult;
        }
    }

    public String getPredcition() {
        return this.prediction;
    }

    public StartEndPosition[] orderPositions(StartEndPosition[] unOrdered, Integer[] types) {
        int value;
        int position;
        for (int i = 0; i < unOrdered.length; i++) {
            value = unOrdered[i].getStartPosition();
            int typeValue = types[i];
            StartEndPosition valuePos = unOrdered[i];
            position = i;
            while ((position > 0) && (unOrdered[position - 1].getStartPosition() < value)) {
                unOrdered[position] = unOrdered[position - 1];
                types[position] = types[position - 1];
                position--;
            }
            unOrdered[position] = valuePos;
            types[position] = typeValue;

        }
        orderedTypes = types.clone();
        return unOrdered;
    }

    public StartEndPosition[] orderPositionsBeginToEnd(StartEndPosition[] unOrdered, Integer[] typesBeginToEnd) {
        int value;
        int position;
        for (int i = 0; i < unOrdered.length; i++) {
            value = unOrdered[i].getStartPosition();
            int typeValue = typesBeginToEnd[i];
            StartEndPosition valuePos = unOrdered[i];
            position = i;
            while ((position > 0) && (unOrdered[position - 1].getStartPosition() > value)) {
                unOrdered[position] = unOrdered[position - 1];
                typesBeginToEnd[position] = typesBeginToEnd[position - 1];
                position--;
            }
            unOrdered[position] = valuePos;
            typesBeginToEnd[position] = typeValue;

        }
        orderedTypesBeginToEnd = typesBeginToEnd.clone();
        return unOrdered;
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
        } catch (ConnectException e) {
            System.out.println("Connect exception for url " + iUrl);
            if (firstTry) {
                this.readUrl(iUrl);
            }
            firstTry = false;
        } catch (IOException e) {
            System.out.println("I/O exception for url " + iUrl);
        }
    }
}
