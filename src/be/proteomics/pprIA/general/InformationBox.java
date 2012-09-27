package be.proteomics.pprIA.general;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 5-mei-2008
 * Time: 10:06:02
 * To change this template use File | Settings | File Templates.
 */
public class InformationBox {
    private String iAnchor;
    private String iTitle;
    private Vector iDecriptions = new Vector();
    private Vector iValues = new Vector();
    private Vector iLinks = new Vector();

    public InformationBox(String aAnchor, String aTitle) {
        this.iAnchor = aAnchor;
        this.iTitle = aTitle;
    }

    public void addElement(String aDesc, String aValue, String aLink){
        iDecriptions.add(aDesc);
        iValues.add(aValue);
        iLinks.add(aLink);
    }

    public String getAnchor() {
        return iAnchor;
    }

    public String getTitle() {
        return iTitle;
    }

    public Vector getDecriptions() {
        return iDecriptions;
    }

    public Vector getValues() {
        return iValues;
    }

    public Vector getLinks() {
        return iLinks;
    }
}
