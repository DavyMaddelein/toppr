package be.proteomics.pprIA.general.protein_info.finder;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 09-Jun-2010
 * Time: 08:10:20
 * To change this template use File | Settings | File Templates.
 */
public class YapfFile {
    private String iFile;
    private String[] iPredictions;
    private Double[] iHperc;
    private Double[] iEperc;
    private String[] iSequence;

    public YapfFile(String aFile) {
        this.iFile = aFile;
        Vector predictions = new Vector();
        Vector hPerc = new Vector();
        Vector ePerc = new Vector();
        Vector sequence = new Vector();

        String yapf = iFile;
        yapf = yapf.substring(yapf.indexOf("PREDSS"));
        while (yapf.indexOf("PREDSS") > -1) {
            String line = yapf.substring(0, yapf.indexOf("\n"));
            String[] args = line.split(" ");
            int index = args.length;
            sequence.add(args[index - 4]);
            predictions.add(args[index - 3]);
            hPerc.add(Double.valueOf(args[index - 2]));
            ePerc.add(Double.valueOf(args[index - 1]));
            yapf = yapf.substring(yapf.indexOf("\n") + 1);
        }

        iPredictions = new String[predictions.size()];
        predictions.toArray(iPredictions);

        iSequence = new String[sequence.size()];
        sequence.toArray(iSequence);

        iHperc = new Double[hPerc.size()];
        hPerc.toArray(iHperc);

        iEperc = new Double[ePerc.size()];
        ePerc.toArray(iEperc);


    }

    //getters

    public String getFile() {
        return iFile;
    }

    public String[] getPredictionsArray() {
        return iPredictions;
    }

    public String getPredictions() {
        String prediction = "";
        for (int i = 0; i < iPredictions.length; i++) {
            prediction = prediction + iPredictions[i];
        }
        return prediction;
    }

    public Double[] getHperc() {
        return iHperc;
    }

    public Double[] getEperc() {
        return iEperc;
    }

    public String[] getSequenceArray() {
        return iSequence;
    }

    public String getSequence() {
        String sequence = "";
        for (int i = 0; i < iSequence.length; i++) {
            sequence = sequence + iSequence[i];
        }
        return sequence;
    }
}
