package be.proteomics.pprIA.general.protein_info.finder;

import be.proteomics.pprIA.general.protein_info.SecondaryStructurePrediction;
import org.strbio.mol.lib.pred2ary.*;
import org.strbio.util.JMC;
import org.strbio.io.PrintfStream;
import org.strbio.local.BlastP;
import org.strbio.local.PSIBlastP;
import org.strbio.mol.lib.Blast;
import org.strbio.mol.lib.SSPredictor;
import org.strbio.net.BlastNCBI;
import org.strbio.net.BlastWeb;
import org.strbio.net.PSIBlastNCBI;
import org.strbio.net.PSIBlastWeb;
import org.strbio.util.StringUtil;

import java.io.*;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 08-Jun-2010
 * Time: 14:17:36
 * To change this template use File | Settings | File Templates.
 */
public class SecStruPredictor {
    private String iSequence;
    private String iName;
    private String iPredictor;
    private String iPredictionResult = "";
    private SecondaryStructurePrediction secPred;
    private PredictionSet predictionSet = null;
    private Jury currentJury = null;
    private String iLocation;

    public SecStruPredictor(String aName, String aSequence, String aLocation) {
        this.iName = aName;
        this.iSequence = aSequence;
        this.iLocation = aLocation;
        this.iPredictor = iLocation + "large.bin";

        //create args
        String[] args = new String[6];
        args[0] = "-pr";
        args[1] = iLocation + "protein.fasta";
        args[2] = "-op";
        args[3] = iLocation + "prediction.yapf";
        args[4] = "-file";
        args[5] = iPredictor;

        // create protein fasta
        try {
            FileOutputStream stream = new FileOutputStream(iLocation + "protein.fasta");

            // Print a line of text
            PrintStream out = new PrintStream(stream);
            out.println(">" + iName);
            String seq = iSequence;
            while (seq.indexOf("\n") > 0) {
                String seqToWrite = seq.substring(0, seq.indexOf("\n"));
                seq = seq.substring(seq.indexOf("\n") + 1);
                out.println(seqToWrite);
            }
            out.println(seq);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // do prediction
        predict(args);

        // read prediction

        try {
            File result = new File(iLocation + "prediction.yapf");
            BufferedReader reader = new BufferedReader(new FileReader(iLocation + "prediction.yapf"));
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                iPredictionResult = iPredictionResult + "\n" + inputLine;
                //System.out.println(inputLine);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        YapfFile yapf = new YapfFile(iPredictionResult);
        System.out.println(yapf.getPredictions());
        System.out.println(yapf.getSequence());
        // delete create files
        File result = new File(iLocation + "prediction.yapf");
        result.delete();
        File fasta = new File(iLocation + "protein.fasta");
        fasta.delete();

        //make secPred
        secPred = new SecondaryStructurePrediction();
        secPred.setPredictions(yapf.getPredictions());
        secPred.setHperc(yapf.getHperc());
        secPred.setEperc(yapf.getEperc());

    }

    public static void main(String[] args) {
        SecStruPredictor prediction = new SecStruPredictor("Caspase 7", "MTDDQDCAAELEKVDSSSEDGVDAKPDRSSIISSILLKKKRNASAGPVRTGRDRVPTYLY\n" +
                "RMDFQKMGKCIIINNKNFDKATGMDVRNGTDKDAGALFKCFQNLGFEVTVHNDCSCAKMQ\n" +
                "DLLRKASEEDHSNSACFACVLLSHGEEDLIYGKDGVTPIKDLTAHFRGDRCKTLLEKPKL\n" +
                "FFIQACRGTELDDGIQADSGPINDIDANPRNKIPVEADFLFAYSTVPGYYSWRNPGKGSW\n" +
                "FVQALCSILNEHGKDLEIMQILTRVNDRVARHFESQSDDPRFNEKKQIPCMVSMLTKELY\n" +
                "FSR", "C:\\Program Files\\Apache Software Foundation\\Tomcat-7.0\\webapps\\toppr\\");
    }

    /**
     * This constructor handles the non-interactive text
     * version of the program.
     */
    public void predict(String[] argv) {
        PrintfStream outfile;
        String out_file = null;
        String pred_file = null;
        String pred_out_file = null;
        boolean individual = false;
        boolean combine = false;
        boolean localJury = false;
        Blast blast = null;
        int choose = -1;
        int i = 0;

        try {
            outfile = new PrintfStream(System.out);

            // print informative message and quit if syntax wrong
            while (i < argv.length) {
                if ((argv[i].indexOf("-?") == 0) ||
                        (argv[i].indexOf("--") == 0) ||
                        (argv[i].indexOf("-h") == 0)) {
                    outfile.flush();
                    System.exit(1);
                } else if (argv[i].indexOf("-op") == 0)
                    pred_out_file = new String(argv[++i]);
                else if (argv[i].indexOf("-ch") == 0)
                    choose = StringUtil.atoi(argv[++i]);
                else if (argv[i].indexOf("-co") == 0)
                    combine = true;
                else if (argv[i].indexOf("-blastlocal") == 0)
                    blast = new BlastP();
                else if (argv[i].indexOf("-psiblastlocal") == 0)
                    blast = new PSIBlastP();
                else if (argv[i].indexOf("-blastnet") == 0)
                    blast = new BlastWeb(argv[++i]);
                else if (argv[i].indexOf("-psiblastnet") == 0)
                    blast = new PSIBlastWeb(argv[++i]);
                else if (argv[i].indexOf("-blast") == 0)
                    blast = new BlastNCBI();
                else if (argv[i].indexOf("-psiblast") == 0)
                    blast = new PSIBlastNCBI();
                else if (argv[i].indexOf("-ind") == 0)
                    individual = true;
                else if (argv[i].indexOf("-o") == 0)
                    out_file = new String(argv[++i]);
                else if (argv[i].indexOf("-p") == 0)
                    pred_file = new String(argv[++i]);
                else if (argv[i].indexOf("-file") == 0)
                    localJury = true;
                else { // must be jury set name
                    if (localJury)
                        currentJury = new Jury(argv[i]);
                    else if (argv[i].compareTo("small") == 0) {
                        currentJury = new SmallJury();
                    } else if (argv[i].compareTo("medium") == 0) {
                        currentJury = new MediumJury();
                    } else if (argv[i].compareTo("large") == 0) {
                        currentJury = new LargeJury();
                    } else {
                        currentJury = new StandardJury(argv[i]);
                        outfile.printf("Use -file to specify a local file containing networks.\n");
                        outfile.flush();
                    }
                }
                i++;
            }
            if (pred_file == null) {
                outfile.flush();
                JMC.fatal("No input proteins specified.  Try 'java pred2ary --help'");
            }

            // set up output file
            if (out_file != null) {
                outfile.flush();
                try {
                    outfile = new PrintfStream(out_file, true);
                }
                catch (FileNotFoundException e) {
                    JMC.fatal("Couldn't open output file " + out_file);
                }
                System.out.println("Writing output to file " + out_file);
            }

            // show invocation string
            JMC.printArgs(argv, outfile);

            // show more startup stuff.
            JMC.programTimer(outfile);

            // set up jury stuff.
            if (currentJury == null) {
                currentJury = new LargeJury();
                if (currentJury.exists())
                    outfile.printf("\nJury size not specified; using large jury.\n");
                else {
                    currentJury = new MediumJury();
                    outfile.printf("\nJury size not specified; using medium jury.\n");
                }
                outfile.flush();
            }

            // make sure jury is ok.
            if (!currentJury.exists()) {
                outfile.printf("\nThere is no jury named '%s' on your machine.\n", currentJury.getName());
                outfile.printf("Try 'java pred2ary --help' for more info on using the program.\n");
                outfile.flush();
                System.exit(1);
            }

            // make sure output file is ok.
            if (pred_out_file == null) {
                outfile.printf("\nYou didn't ask to save the results (-op file).  Hope that's ok.\n");
                outfile.printf("Try 'java pred2ary --help' for more info on using the program.\n\n");
                outfile.flush();
            }

            SSPredictor engine;
            engine = new org.strbio.mol.lib.Pred2ary(currentJury);

            predictionSet = new PredictionSet();

            if (combine) individual = false;

            if (individual) {
                outfile.printf("\nReading protein chains from prediction set '%s'.\n",
                        pred_file);

                PrintfStream predOut = null;
                if (pred_out_file != null) {
                    outfile.printf("\nWriting consensus secondary structure predictions to file '%s'.\n\n",
                            pred_out_file);
                    predOut = new PrintfStream(pred_out_file);
                }

                Enumeration pe;
                pe = predictionSet.polymersInFile(pred_file, outfile);

                while (pe.hasMoreElements()) {
                    PredClassProfile pr = (PredClassProfile) pe.nextElement();

                    if ((pr != null) && (pr.residues() > 0)) {

                        // pick a sequence if necessary
                        if (choose != -1) {
                            outfile.printf("\n");
                            PredClassProfile chosen = (PredClassProfile) pr.choose(choose);

                            if (chosen != null) {
                                outfile.printf("Choosing sequence ");
                                if (chosen.name != null)
                                    outfile.printf("'%s'", chosen.name);
                                else
                                    outfile.printf("#%d", choose);
                                outfile.printf(" from profile ");
                                if (pr.name != null)
                                    outfile.printf("'%s'", pr.name);
                                else
                                    outfile.printf("#%d", i);
                                outfile.printf(".\n");

                                pr = chosen;
                            }
                        }

                        // blast
                        if (blast != null) pr.blast(outfile, blast);

                        // do the prediction
                        outfile.printf("Doing secondary structure prediction.\n");
                        outfile.flush();
                        pr.predictSS(null, engine);

                        // save the prediction
                        if (predOut != null) {
                            outfile.printf("Saving results.\n\n");
                            if (pred_out_file.indexOf(".yapf") != -1)
                                pr.writeYAPF(predOut);
                            else if (pred_out_file.indexOf(".pdb") != -1)
                                pr.writePDB(predOut);
                            else if (pred_out_file.indexOf(".casp") != -1)
                                pr.writeCASP(predOut);
                            else
                                pr.writeEA(predOut);
                            predOut.flush();
                        }
                    }
                }

                if (predOut != null) {
                    predOut.close();
                    predOut = null;
                }
            } else {
                // do everything at once
                predictionSet.read(pred_file, outfile);

                if (combine) predictionSet.combine();

                outfile.printf("Found %d protein chains", predictionSet.n());
                outfile.printf(" in prediction set '%s'.\n",
                        pred_file);

                // pick a particular sequence
                if (choose != -1) {
                    for (i = 0; i < predictionSet.n(); i++) {
                        outfile.printf("\n");
                        PredClassProfile chosen = (PredClassProfile) predictionSet.pcp(i).choose(choose);
                        if (chosen != null) {
                            outfile.printf("Choosing sequence ");
                            if (chosen.name != null)
                                outfile.printf("'%s'", chosen.name);
                            else
                                outfile.printf("#%d", choose);
                            outfile.printf(" from profile ");
                            if (predictionSet.pcp(i).name != null)
                                outfile.printf("'%s'", predictionSet.pcp(i).name);
                            else
                                outfile.printf("#%d", i);
                            outfile.printf(".\n");

                            predictionSet.setElementAt(chosen, i);
                        }
                    }
                }

                // blast
                if (blast != null) predictionSet.blast(outfile, blast);

                // do prediction
                currentJury.outfile = outfile;
                currentJury.doPrediction(predictionSet);

                // save the prediction
                if (pred_out_file != null) {
                    outfile.printf("\nWriting consensus secondary structure predictions to file '%s'.\n",
                            pred_out_file);
                    if (pred_out_file.indexOf(".yapf") != -1)
                        predictionSet.writeYAPF(pred_out_file);
                    else if (pred_out_file.indexOf(".pdb") != -1)
                        predictionSet.writePDB(pred_out_file);
                    else if (pred_out_file.indexOf(".casp") != -1)
                        predictionSet.writeCASP(pred_out_file);
                    else
                        predictionSet.writeEA(pred_out_file);
                }
            }

            JMC.programTimer(outfile);

            outfile.flush();
            if (out_file != null) outfile.close();
        }
        catch (IOException e) {
            System.out.println("IOException: ");
            e.printStackTrace();
        }

    }

    public SecondaryStructurePrediction getSecPred() {
        return secPred;
    }
}

