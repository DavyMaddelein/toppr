package be.proteomics.pprIA.search;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 17-apr-2008
 * Time: 9:27:15
 * To change this template use File | Settings | File Templates.
 */
public class FoundTreatment {

    private String iScientificName;
    private String iMeropsid;
    private Vector iProcessingSites = new Vector();

    public FoundTreatment(String aScientificName, String aMeropsid) {
        this.iScientificName = aScientificName;
        this.iMeropsid = aMeropsid;
    }

    public void addProcessingSite(FoundProcessingSite aSite){
        iProcessingSites.add(aSite);
    }
}
