package be.proteomics.pprIA.general;


import be.proteomics.pprIA.das.readers.AlignmentBlock;
import be.proteomics.pprIA.das.readers.DasAlignment;
import be.proteomics.pprIA.das.readers.DasAnnotationServerAlingmentReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 25-jan-2008
 * Time: 8:57:09
 * To change this template use File | Settings | File Templates.
 */
public class SpToPdbSites {
    private String iProteinAccession;
    private Vector iSpPositions;
    private int pdbPosition;
    private DasAlignment[] alignments;

    private DasAnnotationServerAlingmentReader reader;
    private String iUrl;


    public SpToPdbSites(String aProteinAccession, Vector aSpPositions){
        this.iProteinAccession = aProteinAccession;
        this.iSpPositions = aSpPositions;

        //find features
        String urlMake = "http://das.sanger.ac.uk/das/msdpdbsp/alignment?query=" + iProteinAccession;
        readUrl(urlMake);
        alignments = reader.getAllAlignments();


    }

    public String getSelection(String pdbAccession){
        //select all features
        String select = "";
        for(int i = 0; i<alignments.length;i++){
            String selectedPositions = "";
            //check if it's the alignment for the pdb accession
            if(alignments[i].getPdbAccession().toUpperCase().startsWith(pdbAccession)){
                for(int j  = 0; j<iSpPositions.size() ; j ++){
                    Integer position = (Integer) iSpPositions.get(j);
                    int pdbPosition = 0;
                    //from sp position to pdb position
                    AlignmentBlock[] blocks = alignments[i].getAlignmentBlocks();
                    for(int k = 0; k<blocks.length; k ++){
                        if(blocks[k].getSpStart() <= position && position <=blocks[k].getSpEnd()){
                            pdbPosition = position + blocks[k].getDifference();
                            //pdbPosition = position + blocks[k].getDifference();
                        }
                    }
                    //check if the position > 0
                    if(pdbPosition > 0){
                        //insert no , if it's the first position
                        if(selectedPositions.length() == 0){
                            selectedPositions = selectedPositions + pdbPosition + ":"  +alignments[i].getPdbGroup();
                        } else {
                            selectedPositions =  selectedPositions + ", " + pdbPosition  + ":"  +alignments[i].getPdbGroup();
                        }

                    }

                }
            }
            //only add things to the selection if there are selected positions
            if(selectedPositions.length() > 0){
                if(select.length() == 0){
                    select = selectedPositions;
                } else {
                    select = select + ", " + selectedPositions;
                }
            }

        }
        //if there's nothing selected select 0, otherwise the whole molecule while be selected
        if(select.length() == 0){
            select = "0";
        }
        return select;
    }

    public void readUrl(String aUrl){
        this.iUrl = aUrl;
        try {
            URL myURL = new URL(aUrl);
            StringBuilder input = new StringBuilder();
    	    HttpURLConnection c = (HttpURLConnection)myURL.openConnection();
	        BufferedInputStream in = new BufferedInputStream(c.getInputStream());
	        Reader r = new InputStreamReader(in);

	        int i;
	        while ((i = r.read()) != -1) {
	    	    input.append((char) i);
	        }

            reader = new DasAnnotationServerAlingmentReader(input.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Vector sites = new Vector();
        sites.add(70);
        SpToPdbSites fing = new SpToPdbSites("Q9NX46", sites);
    }


}
