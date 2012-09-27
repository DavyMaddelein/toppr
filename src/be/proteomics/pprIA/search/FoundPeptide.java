package be.proteomics.pprIA.search;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 17-apr-2008
 * Time: 9:27:33
 * To change this template use File | Settings | File Templates.
 */
public class FoundPeptide {
    private String iPeptideSequence;
    private double iScore;
    private double iThreshold;
    private String iModifiedSequence;
    private String iIonCoverage;
    private int iIdentificationid;
    private String iMascotVersion;
    private double iPrecursorMass;
    private int iCharge;
    private double iConfidence;
    private String iCellSource;
    private String iExpType;
    private Vector iProteinLocation = new Vector();

    public FoundPeptide() {
    }

    //setters

    public void setPeptideSequence(String aPeptideSequence) {
        this.iPeptideSequence = aPeptideSequence;
    }

    public void setScore(double aScore) {
        this.iScore = aScore;
    }

    public void setThreshold(double aThreshold) {
        this.iThreshold = aThreshold;
    }

    public void setModifiedSequence(String aModifiedSequence) {
        this.iModifiedSequence = aModifiedSequence;
    }

    public void setIonCoverage(String aIonCoverage) {
        this.iIonCoverage = aIonCoverage;
    }

    public void setIdentificationid(int aIdentificationid) {
        this.iIdentificationid = aIdentificationid;
    }

    public void setMascotVersion(String aMascotVersion) {
        this.iMascotVersion = aMascotVersion;
    }

    public void setPrecursorMass(double aPrecursorMass) {
        this.iPrecursorMass = aPrecursorMass;
    }

    public void setCharge(int aCharge) {
        this.iCharge = aCharge;
    }

    public void setConfidence(double aConfidence) {
        this.iConfidence = aConfidence;
    }

    public void setCellSource(String aCellSource) {
        this.iCellSource = aCellSource;
    }

    public void setExpType(String aExpType) {
        this.iExpType = aExpType;
    }

    public void setProteinLocation(Vector aProteinLocation) {
        for(int i = 0; i<aProteinLocation.size() ; i ++){
            iProteinLocation.add(aProteinLocation.get(i));
        }
    }

    //getters

    public String getPeptideSequence() {
        return iPeptideSequence;
    }

    public double getScore() {
        return iScore;
    }

    public double getThreshold() {
        return iThreshold;
    }

    public String getModifiedSequence() {
        return iModifiedSequence;
    }

    public String getIonCoverage() {
        return iIonCoverage;
    }

    public int getIdentificationid() {
        return iIdentificationid;
    }

    public String getMascotVersion() {
        return iMascotVersion;
    }

    public double getPrecursorMass() {
        return iPrecursorMass;
    }

    public int getCharge() {
        return iCharge;
    }

    public double getConfidence() {
        return iConfidence;
    }

    public String getCellSource() {
        return iCellSource;
    }

    public String getExpType() {
        return iExpType;
    }

    public ProteinLocation[] getProteinLocation() {
        ProteinLocation[] locations = new ProteinLocation[iProteinLocation.size()];
        iProteinLocation.toArray(locations);
        return locations;
    }
}
