package be.proteomics.pprIA.search;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 17-apr-2008
 * Time: 9:15:05
 * To change this template use File | Settings | File Templates.
 */
public class PerformedSearches {
    private Vector foundProteins = new Vector();
    private Vector searchParameters = new Vector();
    private Vector proteinSearch = new Vector();
    private Vector parameterSearch = new Vector();
    private Vector proteinCount = new Vector();
    private Vector uniquePeptide = new Vector();
    private Vector processingSiteCount = new Vector();
    private Vector searchedTreatments = new Vector();
    private Vector compilation = new Vector();
    private int listCounted = 0;

    public PerformedSearches(){

    }
    
    public void addSearch(FoundProtein[] proteins, String[] searchPara, Boolean protSearch, Boolean paraSearched, Boolean comp, Integer proteinCou, Integer siteCount, String[] treatSearch, Vector uniquePeptides){
        foundProteins.add(proteins);
        searchParameters.add(searchPara);
        proteinSearch.add(protSearch);
        parameterSearch.add(paraSearched);
        compilation.add(comp);
        proteinCount.add(proteinCou);
        processingSiteCount.add(siteCount);
        searchedTreatments.add(treatSearch);
        uniquePeptide.add(uniquePeptides);
    }

    public FoundProtein[] getFoundProteins(int session) {
        FoundProtein[] proteinsFound = (FoundProtein[]) foundProteins.get(session);
        return proteinsFound;
    }

    public String[] getSearchParameters(int session) {
        String[] params = (String[]) searchParameters.get(session);
        return params;
    }

    public Boolean getProteinSearch(int session) {
        Boolean proSearch = (Boolean) proteinSearch.get(session);
        return proSearch;
    }

    public Boolean getParameterSearch(int session) {
        Boolean paraSearched = (Boolean) parameterSearch.get(session);
        return paraSearched;
    }

    public Boolean getCompilation(int session) {
        Boolean comp = (Boolean) compilation.get(session);
        return comp;
    }
    public Integer getProteinCount(int session) {
        Integer proCount = (Integer) proteinCount.get(session);
        return proCount;
    }

    public Integer getProcessingSiteCount(int session) {
        Integer proCount = (Integer) processingSiteCount.get(session);
        return proCount;
    }

    public Vector getUniquePeptide(int session) {
        Vector unique = (Vector) uniquePeptide.get(session);
        return unique;
    }

    public Integer getUniquePeptideCount(int session) {
        Vector unique = (Vector) uniquePeptide.get(session);
        return unique.size();
    }

    public String[] getSearchedTreatments(int session) {
        String[] searchTreat = (String[]) searchedTreatments.get(session);
        return searchTreat;
    }

    public int getSessionCount(){
        int count = foundProteins.size();
        return count;
    }

    public FoundProcessingSite[] getAllProcessingSites(int session){
        FoundProtein[] proteins = this.getFoundProteins(session);
                Vector proSites = new Vector();
                for(int i = 0; i<proteins.length ; i ++){
                    FoundProtein protein = proteins[i];
                    Vector siteVector = protein.getProcessingSites();
                    for(int j = 0 ; j < siteVector.size(); j++){
                        proSites.add(siteVector.get(j));
                    }
                }
                FoundProcessingSite[] sites = new FoundProcessingSite[proSites.size()];
                proSites.toArray(sites);
        return sites;

    }

    //setters

    public void deleteProteinFromResult(int session, String protein){
        FoundProtein[] proteins = this.getFoundProteins(session);
        //reset site count
        int sitesFound = this.getProcessingSiteCount(session);

            for(int p = 0; p<proteins.length; p ++){
                if(proteins[p].getSpAccession().equalsIgnoreCase(protein)){
                    sitesFound = sitesFound - proteins[p].getSiteCount();
                }
            }
            this.setProcessingSiteCount(session, sitesFound);
        //delete protein from found proteins
        Vector proteinNew = new Vector();
        FoundProtein[] proteinOld = this.getFoundProteins(session);
        for(int i = 0; i<proteinOld.length ; i ++ ){
            if(!proteinOld[i].getSpAccession().equalsIgnoreCase(protein)){
                proteinNew.add(proteinOld[i]);
            }
        }
        proteins = new FoundProtein[proteinNew.size()];
        proteinNew.toArray(proteins);
        setFoundProteins(session, proteins);

        //reset protein count
        int proteinsfound = this.getProteinCount(session);
        this.setProteinCount(session, proteinsfound - 1);
        //set unique peptides
        Vector unique = new Vector();
        for(int p = 0; p<proteins.length ; p ++){
            Vector sites = proteins[p].getProcessingSites();
            for(int s = 0; s<sites.size(); s ++){
                FoundProcessingSite site = (FoundProcessingSite) sites.get(s);
                String peptide = site.getPeptide();
                boolean found = false;
                for(int u = 0; u<unique.size(); u ++){
                    if(((String)unique.get(u)).equalsIgnoreCase(peptide)) {
                        found = true;
                    }
                }
                if(!found){
                    unique.add(peptide);
                }
            }
        }
        this.setUniquePeptide(session, unique);

        //reset searched treatments
        Vector treatmentsUsed = new Vector();
        for(int p = 0; p<proteins.length; p ++){
            for(int t = 0; t<proteins[p].getUsedTreatments().size(); t ++){
                boolean found = false;
                for(int u = 0; u<treatmentsUsed.size(); u ++){
                    if(((String)treatmentsUsed.get(u)).equalsIgnoreCase((String)proteins[p].getUsedTreatments().get(t))){
                        found = true;
                    }
                }
                if(!found){
                    treatmentsUsed.add(proteins[p].getUsedTreatments().get(t));
                }
            }
        }
        String[] treats = new String[treatmentsUsed.size()];
        treatmentsUsed.toArray(treats);
        this.setSearchedTreatments(session, treats);


        if(getCompilation(session)){
            String[] parameters = this.getSearchParameters(session);
            Vector newParams = new Vector();
            for(int p = 0; p<parameters.length; p++){
                if(!parameters[p].equalsIgnoreCase(protein)){
                    newParams.add(parameters[p]);
                }
            }
            parameters = new String[newParams.size()];
            newParams.toArray(parameters);
            this.setSearchParameters(session, parameters);
        }
    }


    public void setFoundProteins(int session, FoundProtein[] proteins) {
        foundProteins.set(session, proteins);
    }

    public void setSearchParameters(int session, String[] params) {
        searchParameters.set(session, params);
    }

    public void setProteinSearch(int session, boolean searchProtein) {
        proteinSearch.set(session, searchProtein);
    }

    public void setParameterSearch(int session, boolean searchPara) {
        parameterSearch.set(session, searchPara);
    }

    public void setProteinCount(int session, int countProtein) {
        proteinCount.set(session, countProtein);
    }

    public void setProcessingSiteCount(int session, int countProcessing) {
        processingSiteCount.set(session, countProcessing);
    }

    public void setUniquePeptide(int session, Vector countUnique) {
        uniquePeptide.set(session, countUnique);
    }

    public void setSearchedTreatments(int session, String[] searchTreatments) {
        searchedTreatments.set(session, searchTreatments);
    }

    public String getPeptideList(int session, String[] treatments, String selected){
        listCounted = 0;
        FoundProtein[] proteins = this.getFoundProteins(session);
        if(selected == null){
            selected = "";
        }
        Vector sequenceVector = new Vector();
        for (int j = 0; j < proteins.length; j++) {
            //String sequence = proteins[j].getSequence();
            Vector sitesVector = proteins[j].getProcessingSites();
            for (int k = 0; k < sitesVector.size(); k++) {
                FoundProcessingSite site = (FoundProcessingSite) sitesVector.get(k);
                String[] treats = site.getTreatments();
                boolean use = false;
                for (int l = 0; l < treats.length; l++) {
                    for (int m = 0; m < treatments.length; m++)
                        if (treats[l].equalsIgnoreCase(treatments[m])) {
                            use = true;
                        }
                }

                if (use) {
                    if (selected.equalsIgnoreCase("use")) {
                        //only the selected proteins will be used to make the iceLogo
                        if (site.getSelected()) {
                            String start = site.getPreSite();
                            String end = site.getPostSite();
                            String processing = start + end;
                            processing = processing.replace(' ', 'X');
                            sequenceVector.add(processing);
                        }
                    } else {
                        if(site.isShow()){
                            String start = site.getPreSite();
                            String end = site.getPostSite();
                            String processing = start + end;
                            processing = processing.replace(' ', 'X');
                            boolean found = false;
                            for (int l = 0; l < sequenceVector.size(); l++) {

                                if (processing.equalsIgnoreCase((String) sequenceVector.get(l))) {
                                    found = true;
                                }
                            }
                            if (!found) {
                                sequenceVector.add(processing);
                            }
                        }
                    }
                }
            }
        }
        String sequences = "";
        for (int i = 0; i < sequenceVector.size(); i++) {
            sequences = sequences + "\n" + sequenceVector.get(i);
        }
        listCounted = sequenceVector.size();
        return sequences;
    }

    public int getListCounted(){
        return listCounted;
    }
}
