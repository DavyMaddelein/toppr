package be.proteomics.pprIA.das;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 14-apr-2008
 * Time: 8:27:31
 * To change this template use File | Settings | File Templates.
 */
public class ProcessingSiteDas {
    private String iTreatment;
    private String iMeropsid;
    private int iPosition;
    private String iPreSite;
    private String iPostSite;
    private int iStart;
    private int iEnd;
    private int iTreatmentid;

    public ProcessingSiteDas(String aTreament,int aTreatmentid , int aPosition, String aPreSite, String aPostSite, int aStart, int aEnd, String aMeropsid){
        this.iTreatment = aTreament;
        this.iPosition = aPosition;
        this.iPreSite = aPreSite;
        this.iPostSite = aPostSite;
        this.iStart = aStart;
        this.iEnd = aEnd;
        this.iTreatmentid = aTreatmentid;
        this.iMeropsid = aMeropsid;
    }

    //getters
    public String getTreatment(){
        return this.iTreatment;
    }
    public int getPostion(){
        return this.iPosition;
    }
    public String getPreSite(){
        return this.iPreSite;
    }
    public String getPostSite(){
        return this.iPostSite;
    }
    public int getStart(){
        return this.iStart;
    }
    public int getEnd(){
        return this.iEnd;
    }
    public int getTreatmentid(){
        return this.iTreatmentid;
    }

    public String getMeropsid() {
        return this.iMeropsid;
    }
}

