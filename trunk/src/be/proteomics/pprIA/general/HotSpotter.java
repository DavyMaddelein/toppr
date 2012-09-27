package be.proteomics.pprIA.general;

import be.proteomics.ppr.db.accessors.Protein;
import be.proteomics.pprIA.general.util.NavigationProteinBar;
import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.PerformedSearches;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 17-Jun-2010
 * Time: 10:55:32
 * To change this template use File | Settings | File Templates.
 */
public class HotSpotter {
    private int proteinCount = 0;
    private int siteCount = 0;
    private Vector uniquePeptide = new Vector();
    private Connection iConn;
    private DataSource datasource = null;
    private String[] searchedParameters;
    private String[] searchedTreatments;





    public HotSpotter(){


        //Create the connection
        iConn = this.createConnection("updater","updater,13*");
        //String userName = "analysis";
        String userName = "niklaas";

        try{
            //create the protein string
            Protein[] lProteinsDb = Protein.getAllProteins(iConn);
            String lProteins = "";
            for(int i = 0; i<lProteinsDb.length; i ++){
                lProteins = lProteins + "'" + lProteinsDb[i].getSpaccession() +"',";
            }
            lProteins = lProteins.substring(0, lProteins.lastIndexOf(","));

            //find the protein in the database
            String query = "";
            query = "select p.proteinid from protein as p where p.spaccession in (" + lProteins + ") ";
            query = query + "AND p.proteinid IN (select l_proteinid from peptide_location as l where l.l_groupid in (select d.l_groupid from peptide as d where d.l_projectid IN (select r.projectid from project as r where r.projectid in(select t.l_projectid from project_to_usergroup as t where t.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))))";

            PreparedStatement prep = null;
            prep = iConn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            PreparedStatement prep2 = null;
            prep2 = iConn.prepareStatement("Select l.l_groupid, count(l.l_groupid) from peptide_location as l group by l.l_groupid");
            ResultSet rs2 = prep2.executeQuery();
            FoundProtein[] result = this.findProteinResults(rs2, rs, userName);
            prep.close();
            rs.close();
            prep2.close();
            rs2.close();

            PerformedSearches searches = new PerformedSearches();
            searches.addSearch(result, searchedParameters, true, false, false, proteinCount, siteCount, searchedTreatments, uniquePeptide);



            Vector<FoundProtein> lHotSpotProteins  = new Vector<FoundProtein>();

            System.out.println("count,sites,border,inter,totalSites,hotspotSites");


            //for(int ut = 1; ut<=5; ut ++){
            for(int s = 4; s<=4; s ++){
            //for(int s = 2; s<=7; s ++){
                int lNumberOfPositions = s;

                for(int b = 18; b<=18; b ++){
                //for(int b = 10; b<=100; b ++){

                    int lLeftBorder = b;
                    int lRightBorder = lLeftBorder;
                    for(int inter = 6; inter<=6; inter ++){
                    //for(int inter = 2; inter<=40; inter ++){

                        if(inter<b){

                            int lInterPosition = inter;

                            int lCounter = 0;

                            int lNumberOfSitesInAllProteins = 0;
                            int lNumberOfHotSpotSitesInAllProteins = 0;

                            for(int i = 0; i<result.length; i ++){

                                FoundProtein lProtein = result[i];

                                int lPreviousPosition = 0;
                                boolean lStarted = false;
                                boolean lEnded = false;
                                int lSiteCounter = 0;
                                int lNumberOfHotSpotSites = 0;
                                int lNumberOfUsedSites = 0;
                                int lProteinHotSpotCounter = 0;
                                String lResult = "";
                                int lStart = 0;
                                int lStartHotSpot = 0;
                                int lEndHotSpot = 0;
                                int lEnd = 0;

                                Vector<String> lUsedTreatments = new Vector<String>();



                                ///print title
                                Vector<FoundProcessingSite> lSites = lProtein.getProcessingSites();

                                lNumberOfSitesInAllProteins = lNumberOfSitesInAllProteins + lSites.size();

                                if(lSites.size() >= lNumberOfPositions){

                                    for(int j = 0; j<lSites.size(); j ++){

                                        FoundProcessingSite lSite = lSites.get(j);
                                        int lPosition = lSite.getPosition();

                                        if(lStarted){
                                            //check if it should end
                                            if(lPosition - lPreviousPosition >= lInterPosition){
                                                //it could be no hotspot or a hotspot that should end
                                                //first check the number of sites
                                                if(lSiteCounter >= lNumberOfPositions){
                                                    //check if the difference is large enough
                                                    if(lPosition - lPreviousPosition >= lRightBorder){
                                                        int lDifference = 0;
                                                        lDifference = lPosition - lPreviousPosition - 1;
                                                        if(lDifference <= 10){
                                                            for(int f = 0; f<lDifference; f ++){
                                                                lResult = lResult + "-";
                                                            }
                                                        } else {
                                                            lResult = lResult + "-----(" + lDifference + ")";
                                                        }
                                                        lResult = lResult + lSite.getTreatments().length;
                                                        lEnd = lPosition;
                                                        lEnded = true;
                                                        lEndHotSpot = lPreviousPosition;
                                                    } else {
                                                        //reset everything
                                                        lResult = "";
                                                        lSiteCounter = 0;
                                                        lStarted = false;
                                                        lEnded = false;
                                                        lNumberOfUsedSites = 0;
                                                        lUsedTreatments.removeAllElements();
                                                        lStartHotSpot = 0;
                                                        lEndHotSpot = 0;
                                                    }

                                                } else {
                                                    //set all the things back
                                                    lResult = "";
                                                    lSiteCounter = 0;
                                                    lStarted = false;
                                                    lEnded = false;
                                                    lStartHotSpot = 0;
                                                    lEndHotSpot = 0;
                                                    lNumberOfUsedSites = 0;
                                                    lUsedTreatments.removeAllElements();
                                                }

                                            } else {
                                                //this site is very close to the previous one
                                                int lDifference = 0;
                                                lDifference = lPosition - lPreviousPosition - 1;
                                                lSiteCounter = lSiteCounter + lSite.getTreatments().length;
                                                //lSiteCounter = lSiteCounter + 1;
                                                lNumberOfUsedSites = lNumberOfUsedSites + 1;
                                                for(int t = 0; t<lSite.getTreatments().length; t ++){
                                                    boolean lUsed = false;
                                                    for(int u = 0; u < lUsedTreatments.size(); u ++){
                                                        if(lSite.getTreatments()[t].equalsIgnoreCase(lUsedTreatments.get(u))){
                                                            lUsed  = true;
                                                        }
                                                    }

                                                    if(!lUsed){
                                                        lUsedTreatments.add(lSite.getTreatments()[t]);
                                                    }
                                                }
                                                for(int f = 0; f<lDifference; f ++){
                                                    lResult = lResult + "-";
                                                }
                                                lResult = lResult + lSite.getTreatments().length;

                                                /***************************************
                                                 * TREATMENT COUNTER AND NOT SITE COUNTER
                                                 */
                                                //lSiteCounter = lUsedTreatments.size();
                                                /****************************************
                                                 * 
                                                 */



                                                //if its the last position check if it is not ok
                                                if(j == lSites.size() - 1){
                                                    //first check the number of sites
                                                    if(lSiteCounter >= lNumberOfPositions){
                                                        //add the end
                                                        int lProteinLenght = lProtein.getSequence().length();
                                                        lDifference = lProteinLenght - lPosition - 1;
                                                        lSiteCounter = lSiteCounter + lSite.getTreatments().length;
                                                        //lSiteCounter = lSiteCounter + 1;
                                                        lNumberOfUsedSites = lNumberOfUsedSites + 1;
                                                        for(int t = 0; t<lSite.getTreatments().length; t ++){
                                                            boolean lUsed = false;
                                                            for(int u = 0; u < lUsedTreatments.size(); u ++){
                                                                if(lSite.getTreatments()[t].equalsIgnoreCase(lUsedTreatments.get(u))){
                                                                    lUsed  = true;
                                                                }
                                                            }

                                                            if(!lUsed){
                                                                lUsedTreatments.add(lSite.getTreatments()[t]);
                                                            }
                                                        }
                                                        if(lDifference <= 10){
                                                            for(int f = 0; f<lDifference; f ++){
                                                                lResult = lResult + "-";
                                                            }
                                                        } else {
                                                            lResult = lResult + "-----(" + lDifference + ")";
                                                        }
                                                        //set the end
                                                        lEnd = lProteinLenght;
                                                        lEnded = true;
                                                        lEndHotSpot = lPosition;
                                                    }
                                                }
                                            }
                                        } else {
                                            //check if it can start here
                                            int lDifference = 0;
                                            if(lPreviousPosition == 0){

                                                lStarted = true;
                                                lDifference = lPosition - 1;
                                            } else if(lPosition - lPreviousPosition >= lLeftBorder) {
                                                lStarted = true;
                                                lDifference = lPosition - lPreviousPosition - 1;
                                            }
                                            if(lStarted){
                                                //set the start
                                                lStart = lPreviousPosition;
                                                lStartHotSpot = lPosition;
                                                lSiteCounter = lSiteCounter + lSite.getTreatments().length;
                                                //lSiteCounter = lSiteCounter + 1;
                                                lNumberOfUsedSites = lNumberOfUsedSites + 1;
                                                for(int t = 0; t<lSite.getTreatments().length; t ++){
                                                    boolean lUsed = false;
                                                    for(int u = 0; u < lUsedTreatments.size(); u ++){
                                                        if(lSite.getTreatments()[t].equalsIgnoreCase(lUsedTreatments.get(u))){
                                                            lUsed  = true;
                                                        }
                                                    }

                                                    if(!lUsed){
                                                        lUsedTreatments.add(lSite.getTreatments()[t]);
                                                    }
                                                }
                                                if(lDifference <= 10){
                                                    for(int f = 0; f<lDifference; f ++){
                                                        lResult = lResult + "-";
                                                    }
                                                } else {
                                                    lResult = lResult + "(" + lDifference + ")-----";
                                                }
                                                lResult = lResult + lSite.getTreatments().length;
                                            }
                                        }


                                        //Print the result if it is necessary
                                        if(lEnded){
                                            if(lEndHotSpot - lStartHotSpot <= (lLeftBorder + lRightBorder)/2){
                                                //if(lUsedTreatments.size() >= ut){
                                                    //System.out.println(lProtein.getSpAccession() + "\t" + lStart + lResult + lEnd);
                                                    lProteinHotSpotCounter = lProteinHotSpotCounter + 1;
                                                    //reset everything
                                                    lResult = "";
                                                    lSiteCounter = 0;
                                                    lStarted = false;
                                                    lEnded = false;
                                                    lStartHotSpot = 0;
                                                    lEndHotSpot = 0;
                                                    lUsedTreatments.removeAllElements();
                                                    lNumberOfHotSpotSites = lNumberOfHotSpotSites + lNumberOfUsedSites;
                                                    lNumberOfUsedSites = 0;
                                                //}

                                            }
                                        }

                                        //set this position as the previous position
                                        lPreviousPosition = lPosition;
                                    }
                                }

                                //check if the correct percentage of sites is used in a hotspot
                                if(lNumberOfHotSpotSites > 0){
                                    //if((double)lNumberOfHotSpotSites > ((double)lSites.size()/2.0)){
                                        lCounter = lCounter + lProteinHotSpotCounter;
                                        if(!lHotSpotProteins.contains(lProtein)){
                                            lHotSpotProteins.add(lProtein);
                                        }
                                    lNumberOfHotSpotSitesInAllProteins = lNumberOfHotSpotSitesInAllProteins + lNumberOfHotSpotSites;
                                    //}
                                }


                            }
                            //System.out.println(lCounter + "," + lNumberOfPositions + "," + lLeftBorder + "," + lInterPosition + "," + ut);
                            System.out.println(lCounter + "," + lNumberOfPositions + "," + lLeftBorder + "," + lInterPosition + "," + lNumberOfSitesInAllProteins + "," + lNumberOfHotSpotSitesInAllProteins);
                        }
                    }
                }
            //}
            }




            String lLocation = "C:\\temp\\ppr";

            FoundProtein[] lHotSpotProteinArray = new FoundProtein[lHotSpotProteins.size()];
            lHotSpotProteins.toArray(lHotSpotProteinArray);
            for(int i = 0; i< lHotSpotProteinArray.length; i ++){
                FoundProtein protein = lHotSpotProteinArray[i];
                Vector sites = protein.getProcessingSites();
                NavigationProteinBar bar = new NavigationProteinBar(sites, protein);
                protein.setNavigationProtein(bar.getNavigationBar());
                protein.setSitesInSequence(this.getSitesInSequence(protein));
            }

            //new SaveHtmlFileLocal(iConn, lLocation, lHotSpotProteinArray, false);



        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args){
        new HotSpotter();

    }

    public Connection createConnection(String lUser, String lPass) {
        Connection lConn = null;
        String driverClass = "com.mysql.jdbc.Driver";
        String userName = lUser;
        String pass = lPass;
        try {
            com.mysql.jdbc.Driver d = (com.mysql.jdbc.Driver) Class.forName(driverClass).newInstance();
            Properties lProps = new Properties();
            lProps.put("user", userName);
            lProps.put("password", pass);
            lConn = d.connect("jdbc:mysql://iomics.ugent.be/ppr", lProps);

        } catch (ClassNotFoundException cnfe) {
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return lConn;
    }


    private FoundProtein[] findProteinResults(ResultSet aRs, ResultSet aaRs, String userName) {

        String proteinsIds = "";
        HashMap idsCount = new HashMap(3);
        ResultSet ids = aRs;
        ResultSet proteins = aaRs;
        Vector results = new Vector();

        try {
            while (ids.next()) {

                idsCount.put(ids.getInt(1), ids.getInt(2));
            }
            while (proteins.next()) {
                proteinsIds = proteinsIds + proteins.getString(1) + ",";
            }
            if (proteinsIds.length() != 0) {
                proteinsIds = proteinsIds.substring(0, proteinsIds.lastIndexOf(","));
            }
            if (proteinsIds.equalsIgnoreCase("")) {
                proteinsIds = "0";
            }
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("select r.proteinid, r.spaccession, r.sequence, r.entry_name, r.name, l.start, l.end, l.l_groupid ,group_concat(distinct t.scientific_name separator ' , ') as treatments ,c.description as type, g.peptide_sequence, l.location as loc\n" +
                    "from peptide as p, peptide_location as l, protein as r, project as j, cofradic as c, peptide_treatment_and_inhibitor as a, treatment as t, peptide_group as g\n" +
                    "where p.identificationid in (select b.identificationid from peptide as b where b.l_groupid in (select i.l_groupid from peptide_location as i where i.l_proteinid in (" + proteinsIds + ")))\n" +
                    "and p.identificationid in (select b.identificationid from peptide as b where b.l_projectid in (select o.l_projectid from project_to_usergroup as o where o.l_usergroupid in (select u.usergroupid from usergroup as u where u.usergroupid in ( select e.l_usergroupid from usergroup_to_groupusers as e where e.l_groupusersid in (select g.groupusersid from groupusers as g where g.groupusersname = '" + userName + "')))))\n" +
                    "and p.l_projectid = j.projectid and j.l_cofradicid = c.cofradicid and p.l_groupid = l.l_groupid and l.l_proteinid = r.proteinid and l.l_groupid = g.groupid and r.proteinid in (" + proteinsIds + ") and a.l_identificationid = p.identificationid and a.l_treatmentid = t.treatmentid group by loc, r.spaccession order by r.spaccession, loc");

            ResultSet iRs = prep.executeQuery();

            long lastProteinId = Long.MIN_VALUE;
            Vector usedTreatments = new Vector();
            while (iRs.next()) {
                FoundProtein result;

                long id = iRs.getLong("proteinid");
                String accession = iRs.getString("spaccession");
                String seqProt = iRs.getString("sequence");
                String entry = iRs.getString("entry_name");
                String description = iRs.getString("name");
                int start = iRs.getInt("start");
                int end = iRs.getInt("end");
                int loc = iRs.getInt("loc");
                loc = loc -1;
                int groupId = iRs.getInt("l_groupid");
                String treatmentsString = iRs.getString("treatments");
                String type = iRs.getString("type");
                String[] treatments = treatmentsString.split(" , ");
                String peptide = iRs.getString("peptide_sequence");


                boolean use = true;


                if (use) {

                    //add peptide_groupid to the unique peptide vector
                    boolean foundGroupid = false;
                    for (int q = 0; q < uniquePeptide.size(); q++) {
                        if (groupId == (Integer) uniquePeptide.get(q)) {
                            foundGroupid = true;
                        }
                    }
                    if (!foundGroupid) {
                        uniquePeptide.add(groupId);
                    }

                    boolean newProtein = false;
                    //create protein result
                    if (lastProteinId == id) {
                        result = (FoundProtein) results.get(results.size() - 1);
                    } else {
                        result = new FoundProtein(seqProt, accession, entry, description);
                        newProtein = true;
                        usedTreatments = new Vector();
                        proteinCount = proteinCount + 1;
                    }



                        int pos = loc;
                        String proSite = "";
                        String proSite2 = "";
                        int startSite = 0;
                        int endSite = 0;
                        if ((pos - 15) < 0) {
                            proSite = seqProt.substring(0, pos);
                            proSite2 = seqProt.substring(pos, pos + 15);
                            int length = proSite.length();
                            int diffInt = 15 - length;
                            for (int l = 0; l < diffInt; l++) {
                                proSite = " " + proSite;
                            }
                            startSite = 0;
                            endSite = pos + 15;
                        } else {
                            if ((pos + 15) > seqProt.length()) {
                                proSite = seqProt.substring(pos - 15, pos);
                                proSite2 = seqProt.substring(pos);
                                int length = proSite2.length();
                                int diffInt = 15 - length;
                                for (int l = 0; l < diffInt; l++) {
                                    proSite2 = proSite2 + " ";
                                }
                                startSite = pos - 15;
                                endSite = seqProt.length();
                            } else {
                                proSite = seqProt.substring(pos - 15, pos);
                                proSite2 = seqProt.substring(pos, pos + 15);
                                startSite = pos - 15;
                                endSite = pos + 15;
                            }
                        }
                        result.addProcessingSite(new FoundProcessingSite(treatments, pos, proSite, proSite2, startSite, endSite, type, (Integer) idsCount.get(groupId), true, peptide));
                        lastProteinId = id;
                        siteCount = siteCount + 1;

                    //add the treatments to the found treatment vector
                    for (int p = 0; p < treatments.length; p++) {
                        boolean found = false;
                        for (int q = 0; q < usedTreatments.size(); q++) {
                            if (treatments[p].equalsIgnoreCase((String) usedTreatments.get(q))) {
                                found = true;
                            }
                        }
                        if (!found) {
                            usedTreatments.add(treatments[p]);
                        }
                    }
                    result.setUsedTreatments(usedTreatments);
                    if (newProtein) {
                        //get pdb accessions
                        PreparedStatement prepPdb = null;
                        prepPdb = iConn.prepareStatement("select b.pdbaccession from pdb as b , protein as p where b.l_proteinid = p.proteinid and p.SPaccession = ?");
                        prepPdb.setString(1, accession);
                        ResultSet rsPdb = prepPdb.executeQuery();
                        Vector pdbAccV = new Vector();
                        while (rsPdb.next()) {
                            pdbAccV.add(rsPdb.getString(1));
                        }
                        if (pdbAccV.size() > 0) {
                            String[] pdbAccessions = new String[pdbAccV.size()];
                            pdbAccV.toArray(pdbAccessions);
                            result.setPdbAccession(pdbAccessions);
                        }

                        rsPdb.close();
                        prepPdb.close();
                        // get homologene accessions
                        PreparedStatement prepHomo = null;
                        prepHomo = iConn.prepareStatement("select h.homologeneid from homologene_accession as h , protein as p where h.l_proteinid = p.proteinid and p.SPaccession = ?");
                        prepHomo.setString(1, accession);
                        ResultSet rsHomo = prepHomo.executeQuery();
                        Vector homoAccV = new Vector();
                        while (rsHomo.next()) {
                            homoAccV.add(rsHomo.getString(1));
                        }
                        String[] homoAccessions = new String[homoAccV.size()];
                        homoAccV.toArray(homoAccessions);
                        result.setHomolgeneAccession(homoAccessions);
                        rsHomo.close();
                        prepHomo.close();
                        results.add(result);
                    }

                }


            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        FoundProtein[] proteinsFound = new FoundProtein[results.size()];
        results.toArray(proteinsFound);
        for (int i = 0; i < proteinsFound.length; i++) {
            FoundProtein protein = proteinsFound[i];
            Vector sites = protein.getProcessingSites();
            NavigationProteinBar bar = new NavigationProteinBar(sites, protein);
            protein.setNavigationProtein(bar.getNavigationBar());
            protein.setSitesInSequence(this.getSitesInSequence(protein));
        }
        return proteinsFound;

    }

    private FoundProtein[] getHomologueProteins(int homologeneAccession, String userName) {
        FoundProtein[] foundProteins = null;
        try {
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("select * from homologene_accession where homoloGeneid = ?");
            prep.setInt(1, homologeneAccession);
            ResultSet rs = prep.executeQuery();
            Vector proteinIds = new Vector();
            while (rs.next()) {
                proteinIds.add(rs.getInt("l_proteinid"));
            }
            rs.close();
            prep.close();

            Vector results = new Vector();
            for (int i = 0; i < proteinIds.size(); i++) {
                PreparedStatement prep1 = null;
                prep1 = iConn.prepareStatement("select proteinid from protein where proteinid = ?");
                prep1.setInt(1, (Integer) proteinIds.get(i));
                ResultSet rs1 = prep1.executeQuery();
                PreparedStatement prep2 = null;
                prep2 = iConn.prepareStatement("Select l.l_groupid, count(l.l_groupid) from peptide_location as l group by l.l_groupid");
                ResultSet rs2 = prep2.executeQuery();
                FoundProtein[] result = this.findProteinResults(rs2, rs1, userName);
                prep1.close();
                rs1.close();
                prep2.close();
                rs2.close();
                for (int j = 0; j < result.length; j++) {
                    result[j].setHomologue(true);
                    results.add(result[j]);
                }

            }
            foundProteins = new FoundProtein[results.size()];
            results.toArray(foundProteins);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundProteins;
    }

    public String makeNavigationProtein(Vector sites, FoundProtein protein) {
        String html = "";
        int[] sitesLocation = new int[sites.size()];
        Vector treatments = new Vector();
        for (int i = 0; i < sites.size(); i++) {
            FoundProcessingSite site = (FoundProcessingSite) sites.get(i);
            sitesLocation[i] = site.getPosition();
            treatments.add(site.getTreatments());
        }

        //sort
        int value;
        int position;
        for (int i = 0; i < sitesLocation.length; i++) {
            value = sitesLocation[i];
            String[] valueTreat = (String[]) treatments.get(i);
            position = i;
            while ((position > 0) && (sitesLocation[position - 1] < value)) {
                sitesLocation[position] = sitesLocation[position - 1];
                treatments.set(position, treatments.get(position - 1));
                position--;
            }
            sitesLocation[position] = value;
            treatments.set(position, valueTreat);

        }

        double seqLength = protein.getSequence().length();
        double figAaLength = 924.0 / seqLength;
        Vector pictureElements = new Vector();
        for (int i = 0; i < sitesLocation.length; i++) {
            if (i == 0) {
                int length = (int) ((figAaLength * (seqLength - sitesLocation[i])) - 1); //-3 to make the splice site picture a bit bigger
                String link = "noLink";
                SearchOptions.PictureElement element = new SearchOptions.PictureElement(length, "normale", link, "normale.JPG", 0, 0);
                pictureElements.add(element);
            }
            if (sitesLocation.length == 1 || i == (sitesLocation.length - 1)) {
                int length = 5;
                String link = "noLink";
                String[] treat = (String[]) treatments.get(i);
                String treatment = "";
                for (int j = 0; j < treat.length - 1; j++) {
                    treatment = treatment + treat[j] + ", ";
                }
                treatment = treatment + treat[(treat.length - 1)];
                SearchOptions.PictureElement element;
                if (treat.length == 1) {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site.jpg", sitesLocation[i], sitesLocation[i]);
                } else {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site2.jpg", sitesLocation[i], sitesLocation[i]);
                }
                element.setTreatments(treat);
                element.setPosition(sitesLocation[i] - 1);
                pictureElements.add(element);

                int lengthNorm = (int) ((figAaLength * sitesLocation[i]) - 5);
                String linka = "noLink";
                SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, "normale", linka, "normale.JPG", 0, 0);
                pictureElements.add(elementNorm);
            } else {

                int length = 5;
                String link = "noLink";
                String[] treat = (String[]) treatments.get(i);
                String treatment = "";
                for (int j = 0; j < treat.length - 1; j++) {
                    treatment = treatment + treat[j] + ", ";
                }
                treatment = treatment + treat[(treat.length - 1)];
                SearchOptions.PictureElement element;
                if (treat.length == 1) {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site.jpg", sitesLocation[i], sitesLocation[i]);
                } else {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site2.jpg", sitesLocation[i], sitesLocation[i]);
                }

                element.setTreatments(treat);
                element.setPosition(sitesLocation[i] - 1);
                pictureElements.add(element);

                int lengthNorm = (int) ((figAaLength * (sitesLocation[i] - sitesLocation[i + 1])) - 5);
                if (lengthNorm > 0) {
                    String linka = "noLink";
                    SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, "normale", linka, "normale.JPG", 0, 0);
                    pictureElements.add(elementNorm);
                }


            }
        }

        for (int i = 0; i < pictureElements.size(); i++) {
            SearchOptions.PictureElement ele = (SearchOptions.PictureElement) pictureElements.get(i);
            if (!ele.getName().equalsIgnoreCase("normale")) {
                String eleInfo = "Treatment: " + ele.getName() + " Site: " + ele.getStart();
                String picture = "<img style=\"cursor:pointer;\" src =\"images/" + ele.getPicture() + "\" onmouseover=\"replaceProteinInfo('" + protein.getSpAccession() + "','" + eleInfo + "')\"";
                picture = picture + "\" onclick=\"";
                for (int j = 0; j < ele.getTreatments().length; j++) {
                    picture = picture + "showHidePosition('" + protein.getSpAccession() + "_" + ele.getTreatments()[j] + "." + ele.getPosition() + "','img'),";
                }
                picture = picture.substring(0, picture.lastIndexOf(",")) + "\"";
                picture = picture + " width =\"" + ele.getLength() + "\" height =\"46\"/>";
                for (int j = 0; j < ele.getTreatments().length; j++) {
                    picture = picture + "<span id=\"" + protein.getSpAccession() + "_" + ele.getTreatments()[j] + "." + ele.getPosition() + ".Navigator\"style=\"display:none;\">none</span>";
                }
                html = picture + html;
            } else {
                html = "<img src =\"images/" + ele.getPicture() + "\" width =\"" + ele.getLength() + "\" height =\"46\"/>" + html;
            }
        }
        html = "<img src=\"images/start.JPG\" height =\"46\"/>" + html + "<img src=\"images/end.JPG\" height =\"46\"/>";
        return html;
    }

    public String getSitesInSequence(FoundProtein protein) {
        // check which sites are coupled to which treatements
        Vector proteinSites = protein.getProcessingSites();
        Vector usedTreatments = protein.getUsedTreatments();
        boolean[][] siteForTreatment = new boolean[proteinSites.size()][usedTreatments.size()];

        //set all to false
        for (int k = 0; k < usedTreatments.size(); k++) {
            for (int l = 0; l < proteinSites.size(); l++) {
                siteForTreatment[l][k] = false;
            }
        }

        for (int k = 0; k < usedTreatments.size(); k++) {
            for (int l = 0; l < proteinSites.size(); l++) {
                FoundProcessingSite site = (FoundProcessingSite) proteinSites.get(l);
                String[] treatmensFotThisSite = site.getTreatments();
                for (int m = 0; m < treatmensFotThisSite.length; m++) {
                    if (treatmensFotThisSite[m].equalsIgnoreCase((String) usedTreatments.get(k))) {
                        siteForTreatment[l][k] = true;
                    }
                }
            }
        }
        //make an array of (processing site)positions
        int[] positions = new int[proteinSites.size()];
        for (int i = 0; i < positions.length; i++) {
            FoundProcessingSite site = (FoundProcessingSite) proteinSites.get(i);
            positions[i] = site.getPosition();
        }

        String sequence = protein.getSequence();
        //put a space every 10th position
        int substractor = sequence.length();
        StringBuffer seq = new StringBuffer();
        seq.append(sequence);
        int spaceInsertPosition = 0;
        while (substractor >= 0) {
            substractor = substractor - 10;
            spaceInsertPosition = spaceInsertPosition + 1;
        }
        for (int i = 0; i < spaceInsertPosition; i++) {
            seq.insert((i) * 10 + i, " ");
        }
        String sequence10 = seq.toString();

        //sort the sites array
        int position;
        int value;
        //also sort the booleans that say if a site is found with a treatment
        boolean[] positionForTreatment;
        for (int i = 0; i < positions.length; i++) {
            value = positions[i];
            position = i;
            int length = siteForTreatment[i].length;
            positionForTreatment = new boolean[length];
            for (int m = 0; m < siteForTreatment[i].length; m++) {
                positionForTreatment[m] = siteForTreatment[i][m];
            }

            while ((position > 0) && (positions[position - 1] < value)) {
                positions[position] = positions[position - 1];
                for (int j = 0; j < usedTreatments.size(); j++) {
                    siteForTreatment[position][j] = siteForTreatment[position - 1][j];
                }
                position--;
            }
            positions[position] = value;
            siteForTreatment[position] = positionForTreatment;
        }

        for (int j = 0; j < positions.length; j++) {
            int insertPositionPlusSpaces = (positions[j] - 1) / 10;

            String sub1 = sequence10.substring(0, positions[j] + insertPositionPlusSpaces + 1);
            String sub2 = sequence10.substring(positions[j] + insertPositionPlusSpaces + 1);
            String insert = "";
            for (int k = 0; k < usedTreatments.size(); k++) {
                if (siteForTreatment[j][k]) {
                    insert = insert + "<img style=\"display:none\" title=\"" + usedTreatments.get(k) + " (position: " + positions[j] + ")" + "\"id=\"" + protein.getSpAccession() + "_" + usedTreatments.get(k) + "." + (positions[j] - 1) + "\" src =\"images/scissorsSmall" + k + ".jpg\" align=\"middle\">";
                }
            }
            sequence10 = sub1 + insert + sub2;


        }
        return sequence10;
    }

}
