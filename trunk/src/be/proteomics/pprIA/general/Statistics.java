package be.proteomics.pprIA.general;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 2-jul-2008
 * Time: 11:38:43
 * To change this template use File | Settings | File Templates.
 */
public class Statistics {
    private int iTotalSubstrates;
    private int iHumanSubstrates;
    private int iMouseSubstrates;
    private int iTotalSites;
    private int iHumanSites;
    private int iMouseSites;
    private int[] iTreatmentsPerProtein;
    private int[] iSitesPerProtein;
    private int iGos;
    private int iPdbs;
    private int iDomains;
    private int iPfamDomains;
    private int iSmartDomains;
    private int iOrthologues;
    private int iIdentifiedPeptides;

    public Statistics(int aTotalSubstrates, int aHumanSubstrates, int aMouseSubstrates, int aTotalSites, int aHumanSites, int aMouseSites, int[] aTreatmentsPerProtein, int[] aSitesPerProtein, int aGos, int aPdbs, int aDomains, int aPfamDomains, int aSmartDomains, int aOrthologues, int aIdentifiedPeptides) {
        this.iTotalSubstrates = aTotalSubstrates;
        this.iHumanSubstrates = aHumanSubstrates;
        this.iMouseSubstrates = aMouseSubstrates;
        this.iTotalSites = aTotalSites;
        this.iHumanSites = aHumanSites;
        this.iMouseSites = aMouseSites;
        this.iTreatmentsPerProtein = aTreatmentsPerProtein;
        this.iSitesPerProtein = aSitesPerProtein;
        this.iGos = aGos;
        this.iPdbs = aPdbs;
        this.iDomains = aDomains;
        this.iPfamDomains = aPfamDomains;
        this.iSmartDomains = aSmartDomains;
        this.iOrthologues = aOrthologues;
        this.iIdentifiedPeptides = aIdentifiedPeptides;
    }

    public int getTotalSubstrates() {
        return iTotalSubstrates;
    }

    public void setTotalSubstrates(int iTotalSubstrates) {
        this.iTotalSubstrates = iTotalSubstrates;
    }

    public int getHumanSubstrates() {
        return iHumanSubstrates;
    }

    public void setHumanSubstrates(int iHumanSubstrates) {
        this.iHumanSubstrates = iHumanSubstrates;
    }

    public int getMouseSubstrates() {
        return iMouseSubstrates;
    }

    public void setMouseSubstrates(int iMouseSubstrates) {
        this.iMouseSubstrates = iMouseSubstrates;
    }

    public int getTotalSites() {
        return iTotalSites;
    }

    public void setTotalSites(int iTotalSites) {
        this.iTotalSites = iTotalSites;
    }

    public int getHumanSites() {
        return iHumanSites;
    }

    public void setHumanSites(int iHumanSites) {
        this.iHumanSites = iHumanSites;
    }

    public int getMouseSites() {
        return iMouseSites;
    }

    public void setMouseSites(int iMouseSites) {
        this.iMouseSites = iMouseSites;
    }

    public int[] getTreatmentsPerProtein() {
        return iTreatmentsPerProtein;
    }

    public void setTreatmentsPerProtein(int[] iTreatmentsPerProtein) {
        this.iTreatmentsPerProtein = iTreatmentsPerProtein;
    }

    public int[] getSitesPerProtein() {
        return iSitesPerProtein;
    }

    public void setSitesPerProtein(int[] iSitesPerProtein) {
        this.iSitesPerProtein = iSitesPerProtein;
    }

    public int getGos() {
        return iGos;
    }

    public void setGos(int iGos) {
        this.iGos = iGos;
    }

    public int getPdbs() {
        return iPdbs;
    }

    public void setPdbs(int iPdbs) {
        this.iPdbs = iPdbs;
    }

    public int getDomains() {
        return iDomains;
    }

    public void setDomains(int iDomains) {
        this.iDomains = iDomains;
    }

    public int getPfamDomains() {
        return iPfamDomains;
    }

    public void setPfamDomains(int iPfamDomains) {
        this.iPfamDomains = iPfamDomains;
    }

    public int getSmartDomains() {
        return iSmartDomains;
    }

    public void setSmartDomains(int iSmartDomains) {
        this.iSmartDomains = iSmartDomains;
    }

    public int getOrthologues() {
        return iOrthologues;
    }

    public void setOrthologues(int iOrthologues) {
        this.iOrthologues = iOrthologues;
    }

    public int getIdentifiedPeptides() {
        return iIdentifiedPeptides;
    }

    public void setIdentifiedPeptides(int iIdentifiedPeptides) {
        this.iIdentifiedPeptides = iIdentifiedPeptides;
    }
}
