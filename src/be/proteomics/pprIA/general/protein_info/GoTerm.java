package be.proteomics.pprIA.general.protein_info;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 18-mrt-2008
 * Time: 13:40:23
 * To change this template use File | Settings | File Templates.
 */
public class GoTerm {
    private String iCategory;
    private String iId;
    private String iName;

    public GoTerm(){

    }

    public GoTerm(String aName, String aCategory, String aId) {
        this.iName = aName;
        this.iCategory = aCategory;
        this.iId = aId;
    }

    //setters

    public void setCategory(String aCategory) {
        this.iCategory = aCategory;
    }

    public void setId(String aId) {
        this.iId = aId;
    }

    public void setName(String aName) {
        this.iName = aName;
    }

    //getters



    public String getCategory() {
        return iCategory;
    }

    public String getId() {
        return iId;
    }

    public String getName() {
        return iName;
    }
}
