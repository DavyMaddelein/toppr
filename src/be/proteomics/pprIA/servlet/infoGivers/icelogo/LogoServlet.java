package be.proteomics.pprIA.servlet.infoGivers.icelogo;


import be.proteomics.logo.core.aaindex.AAIndexMatrix;
import be.proteomics.logo.core.aaindex.AAIndexParameterMatrix;
import be.proteomics.logo.core.aaindex.AAIndexSubstitutionMatrix;
import be.proteomics.logo.core.data.MainInformationFeeder;
import be.proteomics.logo.core.data.sequenceset.RawSequenceSet;
import be.proteomics.logo.core.dbComposition.SwissProtComposition;
import be.proteomics.logo.core.enumeration.*;
import be.proteomics.logo.core.factory.AminoAcidStatisticsFactory;
import be.proteomics.logo.core.interfaces.AminoAcidStatistics;
import be.proteomics.logo.core.model.OneSampleMatrixDataModel;
import be.proteomics.logo.gui.graph.*;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Properties;
import java.util.Vector;

import be.proteomics.pprIA.search.PerformedSearches;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.fop.svg.PDFTranscoder;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGDocument;


/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 20-okt-2008
 * Time: 16:52:43
 */
public class LogoServlet extends HttpServlet {
    /**
     * Boolean that indicates if a Swiss-Prot composition must be used
     */
    private boolean useSwissprot = false;
    /**
     * The scoretype
     */
    private ScoringTypeEnum iScoreType = ScoringTypeEnum.PERCENTAGE;
    /**
     * The colorscheme
     */
    private ColorScheme iColorScheme;
    /**
     * The p-value
     */
    private double iPvalue;
    /**
     * The startposition
     */
    private int iStartPosition;
    /**
     * The height of the y axis
     */
    private int iYaxis;
    /**
     * The experimental set
     */
    private String[] iPositiveSet;
    /**
     * The reference set
     */
    private String[] iNegativeSet;
    /**
     * The species scientific name
     */
    private String iSpeciesName;
    /**
     * The save location
     */
    private String iSaveLocation = null;
    /**
     * An instance of the MainInformationFeeder singleton
     */
    private MainInformationFeeder iInfoFeeder = MainInformationFeeder.getInstance();
    /**
     * An instance of the IceLogoWepAppSingelton
     */
    private IceLogoWepAppSingelton iWiceLogoSingelton = IceLogoWepAppSingelton.getInstance();

    /**
     * This method will load all SwissProtCompositions
     */
    public void loadCompositions() {
        try {
            //get the species list
            InputStream is = getServletContext().getResourceAsStream("/WEB-INF/speciesList.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(reader);
            String strLine;
            Vector<SwissProtComposition> lAllSpecies = new Vector<SwissProtComposition>();
            Vector<SwissProtComposition> lDoneAllSpecies = new Vector<SwissProtComposition>();
            while ((strLine = in.readLine()) != null) {
                lAllSpecies.add(new SwissProtComposition(strLine.substring(strLine.indexOf("= ") + 2), strLine.substring(0, strLine.indexOf(" = "))));
            }

            //read the composition file
            //and link all the compositions to one species from the species list
            InputStream is2 = getServletContext().getResourceAsStream("/WEB-INF/compositions.txt");
            InputStreamReader reader2 = new InputStreamReader(is2);
            BufferedReader in2 = new BufferedReader(reader2);
            String strLine2;
            String aComposition = "";
            boolean aInAComposition = false;
            SwissProtComposition lComposition = null;
            while ((strLine2 = in2.readLine()) != null) {
                if (aInAComposition) {
                    aComposition = aComposition + strLine2 + "\n";
                    if (strLine2.startsWith("total")) {
                        aInAComposition = false;
                        lComposition.setComposition(aComposition);
                        lDoneAllSpecies.add(lComposition);
                        aComposition = "";
                    }
                } else {
                    for (int i = 0; i < lAllSpecies.size(); i++) {
                        if (strLine2.equalsIgnoreCase("//" + lAllSpecies.get(i).getSpecieLink())) {
                            lComposition = lAllSpecies.get(i);
                            lAllSpecies.remove(lComposition);
                            aInAComposition = true;
                            i = lAllSpecies.size();
                        }
                    }
                }
            }
            iWiceLogoSingelton.setCompositions(lDoneAllSpecies);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    /**
     * This post method gets data from the client and makes a logo for this data
     *
     * @param req The servlet request.
     * @param res The servlet response.
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        boolean lError = false;
        String lErrorString = "";
        //Load the save location if necissary
        if(iSaveLocation == null){
            this.loadSaveLocation();
        }
        //Delete old images from the server
        new ImageDeleter(iSaveLocation).start();
        //set the SwissProtCompositions if necissary
        if (!iWiceLogoSingelton.speciesSet()) {
            this.loadCompositions();
        }
        //get start position from request
        iStartPosition = -15;
        //get x axis dimension from request
        iYaxis = Integer.valueOf(req.getParameter("yAxis"));
        //get p value from request
        iPvalue = 0.05;
        //get the session
        HttpSession session = req.getSession(true);
        String ses = req.getParameter("session");
        int sessionid = Integer.valueOf(ses);
        //get the searches
        PerformedSearches searches = (PerformedSearches) session.getValue("searches");

        // check if we must use swissprot as a negative set or the negative sequences
        if (req.getParameter("negSwChb").equalsIgnoreCase("use")) {
            //use swissprot as negative set
            useSwissprot = true;
            iSpeciesName = req.getParameter("species");

        } else {
            //use the given negative sequences as a negative set
            String treatmentsNegString = req.getParameter("negative");
            treatmentsNegString = treatmentsNegString.replace("�","");
            String[] treatmentsNeg = treatmentsNegString.split("XsplitX");
            String selectedNeg = req.getParameter("selectedNegative");
            String sequencesNeg = searches.getPeptideList(sessionid, treatmentsNeg, selectedNeg);
            if(sequencesNeg.length() == 0){
                lError = true;
                lErrorString = "Error: No peptides could be found in the negative set!";
                // set up the response
                res.setContentType("text/xml");
                res.setHeader("Cache-Control", "no-cache");
                // write out the response string to the client
                res.getWriter().write(lErrorString);
                return;
            }
            sequencesNeg = sequencesNeg.substring(1, sequencesNeg.length());
            iNegativeSet = sequencesNeg.split("\n");
            useSwissprot = false;
        }
        //get the positive sequences
        String treatmentsPosString = req.getParameter("positive");
        treatmentsPosString = treatmentsPosString.replace("�","");
        String[] treatmentsPos = treatmentsPosString.split("XsplitX");
        String selectedPos = req.getParameter("selectedPositive");
        String sequencesPos = searches.getPeptideList(sessionid, treatmentsPos, selectedPos);
        if(sequencesPos.length() == 0){
            lError = true;
            lErrorString = "Error: No peptides could be found in the positive set!";
            // set up the response
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            // write out the response string to the client
            res.getWriter().write(lErrorString);
            return;
        }
        sequencesPos = sequencesPos.substring(1, sequencesPos.length());
        iPositiveSet = sequencesPos.split("\n");

        iScoreType = ScoringTypeEnum.PERCENTAGE;
        
        //create color scheme
        iColorScheme = new ColorScheme(ColorEnum.BLACK, ColorEnum.BLACK, ColorEnum.BLUE, ColorEnum.RED, ColorEnum.RED, ColorEnum.BLACK, ColorEnum.GREEN, ColorEnum.BLUE, ColorEnum.BLACK, ColorEnum.BLACK, ColorEnum.BLUE, ColorEnum.BLACK, ColorEnum.BLACK, ColorEnum.PURPLE, ColorEnum.BLACK, ColorEnum.BLACK, ColorEnum.PURPLE, ColorEnum.BLUE, ColorEnum.GREEN, ColorEnum.GREEN, ColorEnum.BLACK,ColorEnum.BLACK, ColorEnum.BLACK, ColorEnum.BLACK, ColorEnum.GREEN, ColorEnum.BLACK);


        //Create model and matrixes for the logo
        RawSequenceSet lRawPositiveSequenceSet = new RawSequenceSet("Positive sequences");
        for (int i = 0; i < iPositiveSet.length; i++) {
            lRawPositiveSequenceSet.add(iPositiveSet[i]);
        }
        AminoAcidStatistics[] lPositiveStatistics;
        // always use the smallest set to do the statistics
        if (!req.getParameter("negSwChb").equalsIgnoreCase("use")) {
            if (iPositiveSet.length < iNegativeSet.length) {
                lPositiveStatistics = AminoAcidStatisticsFactory.createFixedStatisticsVerticalPositionAminoAcidMatrix(lRawPositiveSequenceSet, 1, 0, iPositiveSet[0].length(), iPositiveSet.length);
            } else {
                lPositiveStatistics = AminoAcidStatisticsFactory.createFixedStatisticsVerticalPositionAminoAcidMatrix(lRawPositiveSequenceSet, 1, 0, iPositiveSet[0].length(), iNegativeSet.length);
            }
        } else {
            lPositiveStatistics = AminoAcidStatisticsFactory.createFixedStatisticsVerticalPositionAminoAcidMatrix(lRawPositiveSequenceSet, 1, 0, iPositiveSet[0].length(), iPositiveSet.length);
        }

        AminoAcidStatistics[] lNegativeStatistics;
        if (useSwissprot) {
            //create a SwissProtComposition for the used species
            SwissProtComposition swComp = iWiceLogoSingelton.getComposition(iSpeciesName);
            AminoAcidStatistics lNegative = null;
            if (swComp != null) {
                lNegative = AminoAcidStatisticsFactory.createFixedAminoAcidMatrix(swComp, iPositiveSet.length);
            }

            lNegativeStatistics = new AminoAcidStatistics[iPositiveSet[0].length()];
            for (int i = 0; i < iPositiveSet[0].length(); i++) {
                lNegativeStatistics[i] = lNegative;
            }
        } else {
            //Create a raw sequence set for the negative sequences.
            RawSequenceSet lRawNegativeSequenceSet = new RawSequenceSet("Negative sequences");
            for (int i = 0; i < iNegativeSet.length; i++) {
                lRawNegativeSequenceSet.add(iNegativeSet[i]);
            }
            // always use the smallest set to do the statistics
            if (iPositiveSet.length < iNegativeSet.length) {
                lNegativeStatistics = AminoAcidStatisticsFactory.createFixedStatisticsVerticalPositionAminoAcidMatrix(lRawNegativeSequenceSet, 1, 0, iPositiveSet[0].length(), iPositiveSet.length);
            } else {
                lNegativeStatistics = AminoAcidStatisticsFactory.createFixedStatisticsVerticalPositionAminoAcidMatrix(lRawNegativeSequenceSet, 1, 0, iPositiveSet[0].length(), iNegativeSet.length);
            }

        }


        //create the datamodel
        String lReferenceID = "Static reference set";
        OneSampleMatrixDataModel dataModel = new OneSampleMatrixDataModel(lNegativeStatistics, lPositiveStatistics, lReferenceID);
        iInfoFeeder.setStartPosition(iStartPosition);
        iInfoFeeder.setPvalue(iPvalue);
        iInfoFeeder.setColorScheme(iColorScheme);
        iInfoFeeder.setYaxisValue(iYaxis);
        iInfoFeeder.setScoringType(iScoreType);


        SVGDocument lSvg = null;
        boolean lGotSVG = false;
        int lTries = 0;
        //try to get the svg
        while (!lGotSVG && lTries < 5) {
            try {
                lSvg = this.buildSVG(req, dataModel);
                if (lSvg != null) {
                    lGotSVG = true;
                }
            } catch (DOMException e) {
                lGotSVG = false;
            }
            lTries = lTries + 1;
        }


        if (lGotSVG) {

            // save the logo
            try {

                //work around to generate less errors
                //1. create svg string
                TranscoderInput inputFirstSvg = new TranscoderInput(lSvg);
                // Create the transcoder output.
                Transcoder lFirstSVGTranscoder = new SVGTranscoder();
                lFirstSVGTranscoder.addTranscodingHint(SVGTranscoder.KEY_XML_DECLARATION, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                lFirstSVGTranscoder.addTranscodingHint(SVGTranscoder.KEY_DOCTYPE, SVGTranscoder.VALUE_DOCTYPE_CHANGE);
                lFirstSVGTranscoder.addTranscodingHint(SVGTranscoder.KEY_FORMAT, SVGTranscoder.VALUE_FORMAT_OFF);
                StringWriter lStringWriter = new StringWriter();
                TranscoderOutput lSVGoutput = new TranscoderOutput(lStringWriter);
                lFirstSVGTranscoder.transcode(inputFirstSvg, lSVGoutput);
                String lResultSVG = lStringWriter.toString();

                //2. create a svg from this string
                String parser = XMLResourceDescriptor.getXMLParserClassName();
                SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
                InputStream is = null;
                is = new ByteArrayInputStream(lResultSVG.getBytes("UTF-8"));
                SVGDocument lSVGDocument = (SVGDocument) f.createDocument(null, is);

                //3. now write this newly generated svg to the different file types
                String fileLocation = iSaveLocation;
                //The name of the picture will be a time stamp. By this, the filename will always be unique
                String fileName = String.valueOf(System.currentTimeMillis());
                fileLocation = fileLocation + "\\" + fileName;
                //Create the transcoder
                Transcoder lJPEGTranscoder = new JPEGTranscoder();
                // Set the transcoding hints.
                lJPEGTranscoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(1.0));


                //get input
                TranscoderInput input = new TranscoderInput(lSVGDocument);
                // Create the transcoder output.
                OutputStream lJPEGoutstream = new FileOutputStream(fileLocation + ".jpeg");
                TranscoderOutput lJPEGoutput = new TranscoderOutput(lJPEGoutstream);

                // Save the image.
                lJPEGTranscoder.transcode(input, lJPEGoutput);
                // Flush and close the stream.
                lJPEGoutstream.flush();
                lJPEGoutstream.close();

                // set up the response
                res.setContentType("text/xml");
                res.setHeader("Cache-Control", "no-cache");
                // write out the response string to the client
                res.getWriter().write(fileName);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TranscoderException e) {
                e.printStackTrace();
            }
        } else {
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            // write out the response string to the client
            res.getWriter().write("Error: Problem creating your logo!");
        }


    }

    /**
     * The get method will send the servlet request and response to the post method.
     *
     * @param req The servlet request.
     * @param res The servlet response.
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    /**
     * This method will generate the SVGDocument
     * @param req The servelt request
     * @param dataModel The icelogo datamodel
     * @return SVGDocument
     */
    public SVGDocument buildSVG(HttpServletRequest req, OneSampleMatrixDataModel dataModel) {
        SVGDocument lSVG = null;
        //set the image size
        iInfoFeeder.setGraphableHeight(300);
        iInfoFeeder.setGraphableWidth(700);
        //create the SVGDocument depending on the request
        IceLogoComponent logo = new IceLogoComponent(dataModel, false);
        lSVG = logo.getSVG();
        return lSVG;
    }


    /**
     * This method will set the save location. This save location is given in the save.properties file that can be found on the server
     */
    public void loadSaveLocation() {
        try {

            InputStream is = this.getClass().getClassLoader().getResourceAsStream("info.properties");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(reader);
            String strLine;
            while ((strLine = in.readLine()) != null) {
                if (strLine.startsWith("location")) {
                    iSaveLocation = strLine.substring(strLine.indexOf("=") + 2);
                }
            }
            iSaveLocation = iSaveLocation + "\\logo";
            //check if this folder exists
            File f =new File(iSaveLocation);
            if(!f.isDirectory()){
                (new File(iSaveLocation)).mkdir();
                System.out.println("Created a new folder : " + iSaveLocation);
            }
        } catch (IOException e) {
            System.err.println("Failing!");
            e.printStackTrace();
        }
    }
}

