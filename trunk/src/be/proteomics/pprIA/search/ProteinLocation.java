package be.proteomics.pprIA.search;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 17-apr-2008
 * Time: 13:37:44
 * To change this template use File | Settings | File Templates.
 */
public class ProteinLocation {
    private String iProteinId;
    private String iDescription;
    private int iPosition;
    private int iStart;
    private int iEnd;
    private String iPreSite;
    private String iPostSite;
    private String[] iTreatment;
    private String[] iInhibitor;

    public ProteinLocation(String aProteinId, String aDescription, int aPosition, int aStart, int aEnd, String aPreSite, String aPostSite) {
        this.iProteinId = aProteinId;
        this.iDescription = aDescription;
        this.iPosition = aPosition;
        this.iStart = aStart;
        this.iEnd = aEnd;
        this.iPreSite = aPreSite;
        this.iPostSite = aPostSite;
    }

    //getters
    public String getProteinId() {
        return iProteinId;
    }

    public String getDescription() {
        return iDescription;
    }

    public int getPosition() {
        return iPosition;
    }

    public int getStart() {
        return iStart;
    }

    public int getEnd() {
        return iEnd;
    }

    public String getPreSite() {
        return iPreSite;
    }

    public String getPostSite() {
        return iPostSite;
    }

    public String[] getTreatment() {
        return iTreatment;
    }

    public String[] getInhibitor() {
        return iInhibitor;
    }

    public String getTreatmentAsString() {
        String treatments = iTreatment[0];
        for(int i = 1; i<iTreatment.length; i ++){
            treatments = treatments + ", " + iTreatment[i];
        }
        return treatments;
    }

    public String getInhibitorAsString() {
        String inhibitors = iInhibitor[0];
        for(int i = 1; i<iInhibitor.length; i ++){
            inhibitors = inhibitors + ", " + iInhibitor[i];
        }
        return inhibitors;
    }

    public String getProcessingSite(){
        String site = "";
        site = iStart + "-" + iPreSite + "-" + iPostSite + "-" + iEnd;
        return site;
    }

    //setters

    public void setTreatment(String[] aTreatment) {
        this.iTreatment = aTreatment;
    }

    public void setInhibitor(String[] aInhibitor) {
        this.iInhibitor = aInhibitor;
    }
}
