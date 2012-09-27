package be.proteomics.pprIA.general;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 11-feb-2008
 * Time: 15:38:45
 * To change this template use File | Settings | File Templates.
 */
public class JalviewResult {
    private String name;
    private String sequence;

    public JalviewResult(String name, String sequence) {
        this.name = name;
        this.sequence = sequence;
    }

    //getters

    public String getName() {
        return name;
    }

    public String getSequence() {
        return sequence;
    }
}
