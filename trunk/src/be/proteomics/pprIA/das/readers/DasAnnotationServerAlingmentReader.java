package be.proteomics.pprIA.das.readers;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 25-jan-2008
 * Time: 9:01:48
 * To change this template use File | Settings | File Templates.
 */
public class DasAnnotationServerAlingmentReader {
    private String iXml;
    private int lastFeatureEndPosition = 0;

    //constuctor: creates a new reader for a xml string
    public DasAnnotationServerAlingmentReader(String aXml){
        this.iXml = aXml;
    }
    //get all alingment in the xml string
    public DasAlignment[] getAllAlignments(){
        Vector alings = new Vector();
        while(iXml.indexOf("<alignment alignType=\"PDB_SP\">",lastFeatureEndPosition + 30) != -1){
            String alignment = iXml.substring(iXml.indexOf("<alignment alignType=\"PDB_SP\">",lastFeatureEndPosition),iXml.indexOf("</alignment>",lastFeatureEndPosition) +12 );
            lastFeatureEndPosition = iXml.indexOf("</alignment>",lastFeatureEndPosition) + 5;
            DasAlignment f = new DasAlignment(alignment);
            alings.add(f);
        }
        DasAlignment[] alignments = new DasAlignment[alings.size()];
        alings.toArray(alignments);
        return alignments;
    }

    
}
