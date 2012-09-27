package be.proteomics.pprIA.general.protein_info;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 31-mrt-2008
 * Time: 12:03:27
 * To change this template use File | Settings | File Templates.
 */
public class SecondaryStructurePrediction {
    private String iPredictions;
    private Double[] iHperc;
    private Double[] iEperc;

    public SecondaryStructurePrediction() {
    }

    //setters

    public void setPredictions(String aPredictions) {
        this.iPredictions = aPredictions;
    }

    public void setHperc(Double[] aHperc) {
        this.iHperc = aHperc;
    }

    public void setEperc(Double[] aEperc) {
        this.iEperc = aEperc;
    }

    //getters

    public String getPredictions() {
        return iPredictions;
    }

    public Double[] getHperc() {
        return iHperc;
    }

    public Double[] getEperc() {
        return iEperc;
    }
}
