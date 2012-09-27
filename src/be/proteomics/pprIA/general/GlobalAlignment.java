package be.proteomics.pprIA.general;

import neobio.alignment.*;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 14-mei-2008
 * Time: 14:04:02
 * To change this template use File | Settings | File Templates.
 */
public class GlobalAlignment {
    private String iMatrix = null;
    private String iSequence1 = null;
    private String iSequence2 = null;
    private Double[] scores;
    private String alignSeq1 = "";
    private String alignSeq2 = "";
    private Double alignmentScore;



    public GlobalAlignment(String aMatrix, String aSequence1, String aSequence2) {
        this.iMatrix = aMatrix;
        this.iSequence1 = aSequence1;
        this.iSequence2 = aSequence2;
        if(iSequence1.indexOf("u") > 0){
            System.out.println(iSequence1);
        }
        if(iSequence2.indexOf("u") > 0){
            System.out.println(iSequence2);
        }
        try{
            StringReader matrixReader = new StringReader(iMatrix);
            StringReader seq1Reader = new StringReader(iSequence1);
            StringReader seq2Reader = new StringReader(iSequence2);

            PairwiseAlignmentAlgorithm algorithm = new NeedlemanWunsch();

            ScoringMatrix scoringMatrix = new ScoringMatrix(matrixReader, false);
            algorithm.setScoringScheme(scoringMatrix);
            algorithm.loadSequences(seq1Reader, seq2Reader);

            PairwiseAlignment alignment = algorithm.getPairwiseAlignment();
            alignmentScore = (double) alignment.getScore();
            alignSeq1 = algorithm.getPairwiseAlignment().getGappedSequence1();
            alignSeq2 = algorithm.getPairwiseAlignment().getGappedSequence2();

            scores = new Double[alignSeq1.length()];
            for(int i = 0; i<alignSeq1.length(); i ++){
                char char1 = alignSeq1.charAt(i);
                char char2 = alignSeq2.charAt(i);
                if(char1 == '-'){
                    char1 = '*';
                }
                if(char2 == '-'){
                    char2 = '*';
                }
                //scores[i] = ((double)scoringMatrix.scoreSubstitution(char1, char2))/((double)scoringMatrix.scoreSubstitution(char1,char1));
                scores[i] = ((double)scoringMatrix.scoreSubstitution(char1,char2));

            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidScoringMatrixException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidSequenceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IncompatibleScoringSchemeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Double[] getScores() {
        return scores;
    }

    public String getAlignSeq1() {
        return alignSeq1;
    }

    public String getAlignSeq2() {
        return alignSeq2;
    }

    public Double getAlignmentScore() {
        return alignmentScore;
    }

    public static void main(String[] args){
        String matrix ="";
        try{
            File matrixFile = new File("C:/blosum62.txt");
            BufferedReader reader = new BufferedReader(new FileReader("C:/blosum62.txt"));
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                matrix = matrix + "\n" + inputLine;
                //System.out.println(inputLine);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        GlobalAlignment align = new GlobalAlignment(matrix, "FMKASER", "FMKASERTLSDEYKLMN");

    }
}