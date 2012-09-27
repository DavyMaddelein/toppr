package be.proteomics.pprIA.search;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 17-apr-2008
 * Time: 9:27:26
 * To change this template use File | Settings | File Templates.
 */
public class FoundProcessingSite {
    private String[] iTreatments;
    private int iPosition;
    private String iPreSite;
    private String iPostSite;
    private int iStart;
    private int iEnd;
    private String iCofradicType;
    private boolean selected = false;
    private int iIsoforms = -1;
    private boolean iShow = false;
    private String iPeptide;


    public FoundProcessingSite(String[] aTreatments, int aPosition, String aPreSite, String aPostSite, int aStart, int aEnd, String aCofradicType, int aIsoform, boolean aShow, String aPeptide) {
        this.iTreatments = aTreatments;
        this.iPosition = aPosition;
        this.iPreSite = aPreSite;
        this.iPostSite = aPostSite;
        this.iStart = aStart;
        this.iEnd = aEnd;
        this.iCofradicType = aCofradicType;
        this.iIsoforms = aIsoform;
        this.iShow = aShow;
        this.iPeptide = aPeptide;
    }

     public FoundProcessingSite(String aTreatments, int aPosition, String aPreSite, String aPostSite, int aStart, int aEnd, String aCofradicType, int aIsoform, boolean aShow, String aPeptide) {
        this.iTreatments = new String[1];
         iTreatments[0] = aTreatments;
        this.iPosition = aPosition;
        this.iPreSite = aPreSite;
        this.iPostSite = aPostSite;
        this.iStart = aStart;
        this.iEnd = aEnd;
        this.iCofradicType = aCofradicType;
         this.iIsoforms = aIsoform;
         this.iShow = aShow;
         this.iPeptide = aPeptide;
    }

    //setters

    public void setSelected(boolean selec){
        selected = selec;    
    }

    public void setIsoforms(int aIsoform){
        iIsoforms = aIsoform;
    }

    //getters

    public String[] getTreatments() {
        return iTreatments;
    }

    public int getPosition() {
        return iPosition;
    }

    public String getPreSite() {
        return iPreSite;
    }

    public String getPostSite() {
        return iPostSite;
    }

    public int getStart() {
        return iStart;
    }

    public int getEnd() {
        return iEnd;
    }

    public String getCofradicType(){
        return iCofradicType;
    }

    public boolean isNterminal(){
        if(iCofradicType.startsWith("N")){
            return true;
        }
        return false;
    }

    public boolean getSelected(){
        return selected;
    }

    public int getIsoformCount() {
        return iIsoforms;
    }

    public boolean isIsoform(){
        boolean iso = false;
        if(iIsoforms > 1){
            iso = true;
        }
        return iso;
    }

    public boolean isShow() {
        return iShow;
    }

    public String getPeptide() {
        return iPeptide;
    }
}

