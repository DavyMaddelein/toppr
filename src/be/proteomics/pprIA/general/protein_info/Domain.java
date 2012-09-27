package be.proteomics.pprIA.general.protein_info;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 18-mrt-2008
 * Time: 13:40:32
 * To change this template use File | Settings | File Templates.
 */
public class Domain {
    private String iLinks;
    private String iLabel;
    private String iId;
    private int iStart;
    private int iEnd;
    private String iType;


    public Domain(){

    }
    
    public Domain(String aLabel, String aLinks, int aStart, int aEnd) {
        this.iLabel = aLabel;
        this.iLinks = aLinks;
        this.iStart = aStart;
        this.iEnd = aEnd;
    }

//setters

    public void setLabel(String aLabel) {
        this.iLabel = aLabel;
    }

    public void setLink(String aLinks) {
        this.iLinks = aLinks;
    }

    public void setStart(int aStart) {
        this.iStart = aStart;
    }

    public void setEnd(int aEnd) {
        this.iEnd = aEnd;
    }
    public void setId(String aId) {
        this.iId = aId;
    }
    public void setType(String aType) {
        this.iType = aType;
    }

    //getters

    public String getLabel() {
        return iLabel;
    }

    public String getLink() {
        return iLinks;
    }

    public String getId() {
        return iId;
    }

    public int getStart() {
        return iStart;
    }

    public int getEnd() {
        return iEnd;
    }

    public String getType() {
        return iType;
    }
}
