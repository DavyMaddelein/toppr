package be.proteomics.pprIA.das;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 17-apr-2008
 * Time: 8:12:15
 * To change this template use File | Settings | File Templates.
 */
public class UniprotSequence {

    private String iSequence;
    private String iVersion;

    public UniprotSequence(String aSequence, String aVersion) {
        this.iSequence = aSequence;
        this.iVersion = aVersion;
    }

    public String compareSequence(String seqToCompare){
        String answer = "Older version than latest uniprot release";
        if(seqToCompare.equalsIgnoreCase(iSequence)){
            answer = iVersion;
        }
        return answer;
    }
}
