package be.proteomics.pprIA.general.protein_info.finder;



import be.proteomics.pprIA.das.readers.AlignmentBlock;
import be.proteomics.pprIA.das.readers.DasAlignment;
import be.proteomics.pprIA.das.readers.DasAnnotationServerAlingmentReader;
import be.proteomics.pprIA.general.protein_info.PdbBlock;
import be.proteomics.pprIA.general.protein_info.PdbParameter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 09-Jun-2010
 * Time: 10:11:24
 */
public class PdbFinder {
    private String iProteinAccession;
    private DasAlignment[] alignments;
    Vector<PdbParameter> pdbs = new Vector<PdbParameter>();
    private DasAnnotationServerAlingmentReader reader = new DasAnnotationServerAlingmentReader("empty");
    private String iUrl;
    private boolean firstTry = true;


    public PdbFinder(String aProteinAccession){
        this.iProteinAccession = aProteinAccession;
        //find features
        String urlMake = "http://www.rcsb.org/pdb/rest/das/pdb_uniprot_mapping/alignment?query=" + iProteinAccession;
        readUrl(urlMake);
        alignments = reader.getAllAlignments();
        try{
            for(int a = 0; a<alignments.length; a ++){
                DasAlignment align = alignments[a];
                String pdb = align.getPdbAccession().substring(0,4);
                pdb = pdb.toUpperCase();
                boolean newPdb = true;
                PdbParameter pdbParamToAddBlock = null;
                for(int v = 0; v<pdbs.size(); v ++){
                    PdbParameter pdbParam = pdbs.get(v);
                    if(pdb.equalsIgnoreCase(pdbParam.getPdbaccession())){
                        newPdb = false;
                        v = pdbs.size();
                        pdbParamToAddBlock = pdbParam;
                    }
                }

                if(newPdb){
                    pdbParamToAddBlock = new PdbParameter(pdb, align.getTitle(), align.getExperiment_type(), align.getResolution());
                    for(int i = 0; i<align.getAlignmentBlocks().length ; i++){
                        AlignmentBlock alignBlock = align.getAlignmentBlocks()[i];
                        PdbBlock block = new PdbBlock(alignBlock.getPdbAccession().substring(5), alignBlock.getSpStart(), alignBlock.getSpEnd(), alignBlock.getPdbStart(), alignBlock.getPdbEnd());
                        pdbParamToAddBlock.addBlock(block);
                    }
                    pdbs.add(pdbParamToAddBlock);
                } else {
                    for(int i = 0; i<align.getAlignmentBlocks().length ; i++){
                        AlignmentBlock alignBlock = align.getAlignmentBlocks()[i];
                        PdbBlock block = new PdbBlock(alignBlock.getPdbAccession().substring(5), alignBlock.getSpStart(), alignBlock.getSpEnd(), alignBlock.getPdbStart(), alignBlock.getPdbEnd());
                        pdbParamToAddBlock.addBlock(block);
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException e){
            System.out.println("Error in reading das pdb alignment");
        }
    }

    public Vector getPdbs(){
        return pdbs;
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
        } catch (ConnectException e){
            System.out.println("Connect exception for url " + iUrl);
            if(firstTry){
                this.readUrl(iUrl);
            }
            firstTry = false;
        } catch (IOException e) {
            System.out.println("I/O exception for url " + iUrl);
        }
    }

    public static void main(String[] args) {
        PdbFinder lF = new PdbFinder("O75369");
        System.out.println(lF.getPdbs());

    }
}