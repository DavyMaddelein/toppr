package be.proteomics.pprIA.general;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 16-jan-2008
 * Time: 10:53:16
 * To change this template use File | Settings | File Templates.
 */
public class SearchOptions {
    private String[] iCellSources;
    private String[] iTaxonomys;
    private String[] iTreatments;
    private String[] iInhibitors;
    private String[] iExperiments;
    private String[] iProjects;
    private String[] iTaxsScientific;

    public SearchOptions(String[] aCs, String[] aTaxs, String[] aTreats, String[] aInhibs, String[] aExps, String[] aProjects, String[] aTaxsScientific){
        this.iCellSources = aCs;
        this.iExperiments = aExps;
        this.iInhibitors = aInhibs;
        this.iProjects = aProjects;
        this.iTaxonomys = aTaxs;
        this.iTreatments = aTreats;
        this.iTaxsScientific = aTaxsScientific;
    }

    //getters    
    public String[] getCellSources(){
        return this.iCellSources;
    }
    public String[] getTaxonomys(){
        return this.iTaxonomys;
    }
    public String[] getTaxonomysScientific(){
        return this.iTaxsScientific;
    }
    public String[] getTreatments(){
        return this.iTreatments;
    }
    public String[] getInhibitors(){
        return this.iInhibitors;
    }
    public String[] getExperiments(){
        return this.iExperiments;
    }
    public String[] getProjects(){
        return this.iProjects;
    }


    /**
     * Created by IntelliJ IDEA.
     * User: Niklaas Colaert
     * Date: 24-jan-2008
     * Time: 9:29:31
     * To change this template use File | Settings | File Templates.
     */
    public static class PictureElement {
        private int iLenght;
        private String iName;
        private String iLink;
        private String iPicture;
        private int iStart;
        private int iEnd;
        private boolean doubleDomain = false;
        private boolean doubleLeft = false;
        private boolean doubleRight = false;
        private boolean doubleMiddle = false;
        private int doublePosition = 0;
        private String[] treatments;
        private int position;

        public PictureElement(int aLength, String aName, String aLink, String aPicture, int aStart, int aEnd){
            this.iLenght = aLength;
            this.iLink = aLink;
            this.iName = aName;
            this.iPicture = aPicture;
            this.iStart = aStart;
            this.iEnd = aEnd;

        }
        //setters

        public void setDoubleDomain(boolean doubleDomain) {
            this.doubleDomain = doubleDomain;
        }

        public void setDoublePosition(int doublePosition) {
            this.doublePosition = doublePosition;
        }

        public void setDoubleLeft(boolean doubleLeft) {
            this.doubleLeft = doubleLeft;
        }

        public void setDoubleRight(boolean doubleRight) {
            this.doubleRight = doubleRight;
        }

        public void setDoubleMiddle(boolean doubleMiddle) {
            this.doubleMiddle = doubleMiddle;
        }



        public void setTreatments(String[] treatments) {
            this.treatments = treatments;
        }

        public void setPosition(int position) {
            this.position = position;
        }
        //getters

        public int getPosition() {
            return position;
        }
        public int getLength(){
            return this.iLenght;
        }
        public int getStart(){
            return this.iStart;
        }
        public int getEnd(){
            return this.iEnd;
        }
        public String getName(){
            return this.iName;
        }
        public String getLink(){
            return this.iLink;
        }
        public String getPicture(){
            return this.iPicture;
        }

        public boolean isDoubleDomain() {
            return doubleDomain;
        }

        public int getDoublePosition() {
            return doublePosition;
        }

        public boolean isDoubleLeft() {
            return doubleLeft;
        }

        public boolean isDoubleRight() {
            return doubleRight;
        }

        public boolean isDoubleMiddle() {
            return doubleMiddle;
        }
        public String[] getTreatments() {
            return treatments;
        }
    }
}
