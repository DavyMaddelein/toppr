package be.proteomics.pprIA.search;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 17-apr-2008
 * Time: 9:15:16
 * To change this template use File | Settings | File Templates.
 */
public class FoundProtein {
    private String iSequence;
    private String iSpAccession;
    private String iUpAccession;
    private String iDescription;
    private String iPprAccession;
    private String[] iPdbAccessions;
    private String[] iHomoloGeneAccessions;
    private String iNavigationProtein;
    private String iSitesInSequence;
    private Vector iProcessingSites = new Vector();
    private boolean pdbFound = false;
    private boolean homologue = false;
    private Vector usedTreatments = new Vector();

    public FoundProtein(String aSequence, String aSpAccession, String aUpAccession, String aDescription){
        this.iSequence = aSequence;
        this.iSpAccession = aSpAccession;
        this.iUpAccession = aUpAccession;
        this.iDescription = aDescription;
    }

    //getters
    public String getSequence(){
        return this.iSequence;
    }
    public String getSpAccession(){
        return this.iSpAccession;
    }
    public String getUpAccession(){
        return this.iUpAccession;
    }
    public String getDescription(){
        return this.iDescription;
    }
    public String[] getPdbAccessions(){
        return this.iPdbAccessions;
    }

    public Vector getUsedTreatments(){
        return this.usedTreatments;
    }

    public int getSiteCount(){
        Vector sites = this.getProcessingSites();
        return sites.size();
    }

    public int getTreatmentCount(){
        Vector treats = this.getUsedTreatments();
        return treats.size();
    }

    public String getPdbLoad(){
        String loadPdb = "";
        if(iPdbAccessions.length >0){
        loadPdb = "<script type=\"text/javascript\"> jmolInitialize(\"pdbFiles/\", false); jmolApplet(400, \"load pdbFiles/files/" + iPdbAccessions[0] + ".pdb; wireframe off; background white; spacefill off; backbone off; cartoon structure; color cartoon structure; \");  </script>";
        }
        return loadPdb;
    }

    public String getPdbHtmlMenu(){
        int accessions = iPdbAccessions.length;
        String htmlPdbs = "";
        if(iPdbAccessions.length >0){
        htmlPdbs = htmlPdbs + "<script language=\"JavaScript\" type=\"text/javascript\"> jmolMenu([";

        for(int i = 0; i<iPdbAccessions.length - 1 ; i ++){
            htmlPdbs  = htmlPdbs +  "[\"load pdbFiles/files/" + iPdbAccessions[i] +".pdb; wireframe off; background white; spacefill off; backbone off; cartoon structure; color cartoon structure;\",\"load " + iPdbAccessions[i] +"\"],";

        }
        htmlPdbs = htmlPdbs +  "[\"load pdbFiles/files/" + iPdbAccessions[accessions - 1] +".pdb; wireframe off; background white; spacefill off; backbone off; cartoon structure; color cartoon structure;\",\"load " + iPdbAccessions[accessions -1] +"\"]";
        htmlPdbs = htmlPdbs + "]); </script>";
        }
        return htmlPdbs;
    }

    public String[] getPdbHtmlCheckboxes(){
        Vector treatments = new Vector();

        for(int i = 0 ; i<iProcessingSites.size();  i++){
            FoundProcessingSite site = (FoundProcessingSite) iProcessingSites.get(i);
            String[] treats = site.getTreatments();
            for(int k = 0; k<treats.length; k++){
                boolean newTreat = true;
                for(int j = 0; j<treatments.size() ; j++){
                    if(((String)treatments.get(j)).equalsIgnoreCase(treats[k])){
                        newTreat = false;
                    }
                }
                if(newTreat){
                    treatments.add(treats[k]);
                }
            }
        }


        String[] checkHtml = new String[treatments.size()];

        if(iPdbAccessions.length >0){
        for(int i = 0; i<treatments.size() ; i ++){
            Vector sites = new Vector();
            for(int j = 0 ; j<iProcessingSites.size();  j++){
                FoundProcessingSite site = (FoundProcessingSite) iProcessingSites.get(j);
                String[] treats = site.getTreatments();

                for(int k = 0; k<treats.length; k ++){
                    if(((String)treatments.get(i)).equalsIgnoreCase(treats[k])){
                        sites.add(site.getPosition());
                        k = treats.length;
                    }
                }
            }
            String html = "<script language=\"JavaScript\" type=\"text/javascript\"> jmolCheckbox(\"select ";

            for(int k = 0; k<sites.size() ; k ++){
                html = html + sites.get(k);
                if(k < sites.size() - 2){
                    html = html + ", ";
                }
            }

            html = html + "; wireframe 0.5; spacefill 0.9; color green;\", \"select";
            for(int k = 0; k<sites.size() ; k ++){
                html = html + sites.get(k);
                if(k < sites.size() - 2){
                    html = html + ", ";
                }
            }
            html = html + "; wireframe 0; spacefill 0\",\"\", false,\"\",\"\" ); </script> select "+ treatments.get(i)  +"sites";
            checkHtml[i] = html;
        }
        }

        return checkHtml;
    }


    public String[] getHomolgeneAccessions(){
        return this.iHomoloGeneAccessions;
    }
    public Vector getProcessingSites(){
        return this.iProcessingSites;
    }

    public String getNavigationProtein() {
        return iNavigationProtein;
    }

    public String getSitesInSequence() {
        return iSitesInSequence;
    }

    public String getSequence10(){
        String sequenceNormal = this.getSequence();
        int substractor = sequenceNormal.length();
        int spaceInsertPosition = 0;
        while(substractor >= 0){
            substractor = substractor - 10;
            spaceInsertPosition = spaceInsertPosition  + 1;
        }
        String sequence = "";
        for(int j = 0 ; j<spaceInsertPosition ; j++){
            if(j == spaceInsertPosition - 1){
            int start = j * 10;
            sequence = sequence + sequenceNormal.substring(start);
            } else {
                int start = j * 10;
                int end = start + 10;
                sequence = sequence + sequenceNormal.substring(start, end) + " ";
            }

        }

        return sequence;
    }

    public boolean getPdbFound(){
        return this.pdbFound;
    }

    public boolean isHomologue() {
        return homologue;
    }

    //setters

    public void setNavigationProtein(String aNavigationProtein) {
        this.iNavigationProtein = aNavigationProtein;
    }

    public void setSitesInSequence(String aSitesInSequence) {
        this.iSitesInSequence = aSitesInSequence;
    }

    public void addProcessingSite(FoundProcessingSite site){
        iProcessingSites.add(site);
    }

    public void setPdbAccession(String[] acc){
        pdbFound = true;
        iPdbAccessions = acc;
    }
    public void setHomolgeneAccession(String[] acc){
        iHomoloGeneAccessions = acc;
    }

    public void setHomologue(boolean homologue) {
        this.homologue = homologue;
    }

    public void setUsedTreatments(Vector used){
        this.usedTreatments = used;
    }
}
