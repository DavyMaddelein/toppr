package be.proteomics.pprIA.general.protein_info;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 31-mrt-2008
 * Time: 9:19:43
 * To change this template use File | Settings | File Templates.
 */
public class Homologues {
    private String[] iSequences;
    private String[] iHomologueAccessions;

    public Homologues(String[] aSequences, String[] aHomologueAccessions) {
        this.iSequences = aSequences;
        this.iHomologueAccessions = aHomologueAccessions;
    }

    //getters

    public String[] getSequences() {
        return iSequences;
    }

    public String[] getHomologueAccessions() {
        return iHomologueAccessions;
    }

}
