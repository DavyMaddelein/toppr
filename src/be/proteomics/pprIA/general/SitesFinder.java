package be.proteomics.pprIA.general;

import be.proteomics.pprIA.general.homologue.HomoloGeneIdFinder;
import be.proteomics.pprIA.general.homologue.PicrInfoFinder;
import be.proteomics.pprIA.general.util.NavigationProteinBar;
import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.PerformedSearches;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
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
 * Date: 16-Jun-2010
 * Time: 07:41:11
 * To change this template use File | Settings | File Templates.
 */
public class SitesFinder {

    private int proteinCount = 0;
    private int siteCount = 0;
    private Vector uniquePeptide = new Vector();
    private Connection iConn;
    private DataSource datasource = null;
    private String[] searchedParameters;
    private String[] searchedTreatments;





    public SitesFinder(String[] lProteinAccessions, int[] lPositions){

        //check the data
        if(lPositions != null){
            if(lPositions.length != lProteinAccessions.length){
                System.out.println("Different number of proteins and positions\nThis script will close");
                return;
            }
        }
        //Create the connection
        iConn = this.createConnection("updater","updater,13*");
        String userName = "niklaas";

        try{
            //create the protein string
            String lProteins = "";
            for(int i = 0; i<lProteinAccessions.length; i ++){
                lProteins = lProteins + "'" + lProteinAccessions[i] +"',";
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


            //print the results
            for(int i = 0; i<result.length; i ++){
                FoundProtein lProtein = result[i];
                //print title
                System.out.print("\n" + lProtein.getSpAccession());
                Vector<FoundProcessingSite> lSites = lProtein.getProcessingSites();
                for(int j = 0; j<lSites.size(); j ++){
                    FoundProcessingSite lSite = lSites.get(j);
                    //check if this site was in the original set
                    boolean lFound = false;
                    String lLabelFound = "";
                    if(lPositions != null){
                        for(int k = 0; k<lProteinAccessions.length; k ++){
                            if(lProteinAccessions[k].equalsIgnoreCase(lProtein.getSpAccession())){
                                if(lPositions[k] == lSite.getPosition() + 1){
                                    lFound = true;
                                    lLabelFound = "*";
                                    k = lProteinAccessions.length;
                                }
                            }
                        }
                    }
                    System.out.print("\n" + lLabelFound + "\t" + lProtein.getSpAccession() + "\t" + (lSite.getPosition() + 1) + "\t" + lSite.getPreSite() + "." + lSite.getPostSite());
                    for(int t = 0; t<lSite.getTreatments().length; t ++){
                        System.out.print("\t" + lSite.getTreatments()[t]);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();  
        }


    }


    public static void main(String[] args){
        //data for magdalena (caspase 2)
        String[] lAccession = new String[]{"Q8JZK9","Q8BFR6","P56873","Q9JI13","Q3U2P1","Q61211","Q9QXS1","Q9DC61","Q1EG27","Q9JLI8","P47963","P62915","Q921W0","O70251","P54227","Q3TLH4","P60670","Q6P5G6","Q3UZA1","Q80WT5","Q6PEB6","O35143","P39054","Q8BU04","Q61550","Q99N96","P70670","Q922B2","Q9CR51","Q6P542","P62889","P62264","Q4VAA2","Q9CZ44","Q9QYS9","P10649","P68037","Q99PT1","Q9ERE7","P09528","P56399","Q9ERR7","Q9CWV6","Q78YZ6","P50580","P10605","P63242","Q9CQR2","Q3U0V1","P56480","P29341","P97461","P84443","P99024","Q8BP67","P50518","Q9D0J8","P49710","O09161","P15864","P49717","Q99LP6","Q8VIJ6","Q8VDD5","P26041","P60122","P12265","P28658","Q9JK81","P47915","P98078","O88572","P29391","Q9CQA3","Q9CZU6","P19536","P09528","Q9CZU6","Q91VC3","Q9CZD3","P80318","P63038","P62918","Q6NVE9","Q9JIY5","P20029","Q8VEK3","P61979","P16675","Q9DCD0","P62855","P09528","Q99P91","Q99P91","P70699","Q8VEK3","P06745","O09159","P11680","P42932","P20152","P27773","Q61599","P47713","Q80Y14","P49710","P63323","Q6PDM2","Q9D1M7","Q9QYE6","P60710","P10605","Q6P6M5","Q9DB98","Q07113","Q07113","Q9D0I9","Q922B2","O35604","P63101","P26350","Q9JMH6","P49717","P62908","P06745","Q9WTI7","P08113","P61161","Q6ZWN5","P07901","Q9D0L8","Q68FD5","P97351","Q64152","P29391","P20029","P60710","Q9D6S7","P12367","Q9JKR6","Q99N90","P97493","Q80XU3","P20152","Q61792","Q62018","Q9WVA4","Q07113","Q3UMU9","P27773","Q62189","P22907","Q3U3E2","Q01853","Q61545","O88569","Q01853","P0C0S6","Q61937","Q03265","P62908","Q7TQK4","P08113","Q60848","P62827","Q3UPL0","O08585","P61290","Q9CT10","P47962","P62862","Q499X9","Q6P3B9","P09411","O08550","P63038","Q99MN1","Q9CZX8","Q9JKF1","P05202","P80316","P14148","P17225","P24668","Q61081","P62242","Q00612","O70133","P29341","Q99NB9","Q8C078","P43274","P15864","Q6PE15","P10853","P62204","P0C0S6","P63038","P35980","Q9CY58","P63158","Q9D8X2","P62900","Q99PT1","P10126","P60710","Q8R1B4","P43277","Q9CR29","P62862","P11499","P68372","P62754","P84099","Q05D44","Q8R081","P12970","P63242","Q9DCJ7","Q6ZWN5","Q8K411","P10404","Q61937","P43276","P62983","Q8BGD9","P20152","P63038","P09411","P14148","P17742","Q64525","Q922D8","P28656","Q99PT1","Q60692","P17182","Q9D0E1","Q8BFR5","P52480","P51569","O09159","Q01853","O89053","P05213","P62754","Q9CZ44","Q99K28","Q3TC93","Q9CZB0","P62991","P63017","P20152","Q9D0J8","Q71RI9","Q91W50","P61082","P26638","Q62348","P10605","O35887","Q9EQI8","Q8R0G9","Q9DCM0","Q9CZR8","Q9CXW2","O35459","Q01768","Q9JI46","O09061","Q922R8","P20152","Q8R081","P62754","P97822","Q6NTA4","Q60865","Q3U6N9","Q8VCH5","Q6PAQ4","Q91Z53","P62245","P40142","Q61598","P19253","Q9D2G2","Q9JIY2","P14148","Q8VEK3","P14685","P97450","Q60872","P62827","P62754","P80315","Q62167","Q09014","P62245","Q9WUU7","Q922D8","Q99KI0","Q8BGD9","A2AM29","O88696","P84099","P49446","Q9WTX5","P17156","Q8R3G1","Q6PGH2","P63038","P60710","P05064","Q61937","P62311","P34022","Q8C3X4","P56480","Q9CZR8","Q3THS6","P29351","Q9Z2X1","Q9CPY7","Q9JM93","Q8C0E3","P62900","Q9Z1Q9","Q8K2Q9","O08553","Q99JY0","Q8CI11","Q8BGD9","Q9ET22","P26039","Q99KV1","P50247","P08249","Q8BGR9","Q9WTP6","Q8R5H1","Q8CIG8","P28798","Q78PY7","O08528","Q9R0E1","P48036","O35639","P14824","Q9CXJ1","Q9JIG7","Q91WQ3","Q9WTP6","Q8VDJ3","P07356","P97452","Q6NZJ6","Q8CIH5","Q4VAA2","Q9D8Y7","Q8BIZ6","B1AQJ2","Q8CGC7","Q3V3R1","Q9EQP2","Q91V92","P60710","P16381","P62889","P51859","Q99KC8","Q61316","Q3UH60","Q6NTA4","Q6A0A9","Q99PT1","Q9DBD5","Q8CD15","Q60974","P35123","Q9DCA5","Q6PNC0","P27612","P25206","Q99MN1","P07901","Q01853","P47963","P05201","P97429","P67984","P62245","Q61316","A2ADY9","Q8K1G2","Q9EQI8","Q99PP9","Q3UM45","P46718","P19096","P68372","O09131","P19157","Q60710","P29341","Q8K4Q0","Q9WUK2","Q3UYH7","Q3TCN2","P08249","P14685","Q99PV0","P70677","Q9WU84","O35639","O35143","Q68FD5","Q9WUU8","P55302","Q9Z110","Q9JHU4","Q07797","Q2EMV9","P54728","Q9DCT1","Q99L45","Q8BTM8","Q7TPV4","Q9D7X3","Q6ZQ08","Q9D2Y4","Q8BTM8","Q80X82","Q8K310","Q9JM76","P11983","P39054","Q9D0G0","Q60974","Q9QZS8","Q9D0K2","Q99K85","Q9QXS1","Q9JLZ3","Q62167","Q9JKV1","P49312","Q91YT0","P59999","P97868","Q7TMY8","O88569","Q61990","Q9EQU5","P06745","Q8BJZ4","Q812A2","Q3UFK8","Q61792","Q8VDD5","P68372","P08113","P29391","Q9CXT8","P70670","Q922S8","Q9DCH6","P61161","Q03265","P06151","Q61171","P35700","Q9CWM4","Q61699","Q9CZU3","P29351","Q6P1E1","P80315","Q9EST5","Q01853","P59325","Q8VC03","Q9CQS5","P23116","Q8C1B7","Q61792","Q9WTP6","Q9CY27","P47915","Q9Z2X1","P60710","Q9CQA3","P27773","P50516","Q8BTI8","P55937","P35980","Q61598","Q8QZS1","P60710","Q6NVF4","Q61598","Q9D8N0","P45878","P26883","Q8C1A5","Q8BGD9","P47963","Q9D0J4","Q8K2T8","P26350","P61514","Q8BFS6","Q2YDW2","P80318","P20152","O70370","Q9JI10","P35980","Q8R3N6","Q60864","Q8BWU5","P05063","P62878","P26350","Q8BH86","O35955","P41105","P43276","Q8BMS1","Q99LD9","P61290","Q8CIH5","Q7TMY8","Q8BHL8","Q8K3J1","O89023","P17182","Q9WUK2","P62983","P08113","P20152","P62204","P60710","Q9WUU7","P00342","P63276","Q9CQV8","P61982","Q9Z2R6","Q61937","Q8BP47","P06745","Q9D8E6","Q8CIH5","Q921M7","Q9CPT4","P38060","Q924T2","Q9CZX8","P60710","Q68FD5","P07607","P35980","P29351","Q8K297","Q9Z2X1","P62259","Q9D8W5","Q60649","Q9CPR4","Q80WR5","P20152","P10649","P54818","P20152","P47911","Q9CWV6","Q9D7H3","P50516","P60710","Q9CQR2","P60710","Q8VDQ9","P68372","Q3UPL0","P49710","Q99N96","Q99N96","P56480","Q4VAA2","P45878","P47713","Q99MS7","Q9CPW2","Q9CY58","Q9D8B3","Q61316","Q9R020","P17225","Q9CR98","P97461","P20152","Q64475","Q6PB66","P63038","P62900","Q0VG62","P43276","Q6ZQF0","Q8QZR5","Q8K1J5","P20152","P26041","Q9CR98","P56480","Q61937","Q8CGC7","P56480","Q9CQ06","Q99N92","Q8QZT1","Q921F2","Q9D6S7","O09159","Q8BFR5","P05213","P10853","P05202","P08113","P10605","P10107","P10605","P62806","O35143","O35143","P61358","Q61792","P18155","P02301","Q91VW3","Q9D0J8","P07901","Q9CYR0","P49710","P63242","P97825","P25206","O35143","Q9D8B3","P14576","Q8BG07","O88271","Q99MN1","P49710","Q9CZX8","Q9CPW4","Q9WUD1","Q6PGL7","Q91VR5","O55234","P60710","Q99020","O08585","P60710","P60710","Q9JKF7","P10605","Q8R3B7","P51150","P01887","Q3TQI7","P57776","P60710","P43277","P60710","P08113","P62754","Q9D6S7","P83882","P84099","Q9D7N3","Q6PDM2","P47757","P68433","Q922H2","P18760","Q03265","P49710","P61358","P27773","Q6PGL7","O35943","P12265","O08807","Q99P91","Q9D0G0","P60710","P17182","Q9QZ23","P60710","Q9JII5","Q60875","Q6KAR6","Q64152","P10649","P47915","P63017","Q9QYR9","Q569Z6","Q9CQC6","P61222","P62900","Q9D0C4","P56480","P09528","Q03265","Q8BG07","Q9D964","Q9D8S9","O35639","Q9CR21","Q9EQH3","O35143","O35143","Q9CQ49","P27773","Q03147","P17918","Q8CIH5","P14824","P57780","P61979","Q62318","Q3U0V1","Q922R8","P17182","Q8BMJ2","Q8K1I7","Q9D8E6","P70677","P08113","P34884","P14869","P55937","Q99K95","P60710","Q9CQR2","Q9QZD9","Q9DCL9","P46061","P62242","Q61733","P00342","Q91YE6","P58252","Q05920","Q61699","Q61881","Q8R361","Q9CZM2","O08553","Q6P8X1","Q9D0I9","P61161","Q9CY58","Q9QUN3","P06745","Q61207","Q9CXC3","P41105","O35405","Q9CZU6","Q9DCL9","Q9DCM0","Q9ES00","O35382","Q9CZR8","Q62167","O35143","Q9CXW2","O35459","Q8VI75","Q3V3R1","P20152","Q9CWX2","Q9D0I9","P97807","Q9CQN1","Q9D964","Q8BMS4","Q5SW19","P11499","P52480","O55234","Q8BGQ7","Q8VDJ3","O55222","P09405","Q9CZ44","Q3UZA1","P56480","Q8VDJ3","P62264","P63276","O88487","P19096","Q9JKR6","O09131","Q62384","P80314","Q9CYZ2","P97351","Q9DCS3","P29758","Q61166","P97493","P70195","P16627","Q8C0M9","P97287","Q8BIJ6","Q8JZQ9","P63260","Q62159","Q99KD5","Q8R081","P52480","O35143","Q9D8S4","Q8BGD9","P63038","O35857","P61982","P29391"};
        int[] lPos = new int[]{44,121,56,361,266,244,2779,60,1015,603,145,208,165,155,18,874,112,283,14,340,35,35,353,337,129,45,2027,20,18,271,3,73,28,159,219,97,123,63,30,17,768,31,100,54,282,110,12,5,17,388,154,115,61,63,81,32,14,240,100,65,845,81,335,1484,372,318,22,28,243,59,702,20,165,31,26,31,4,28,5,84,439,50,31,14,134,285,190,360,32,289,29,16,252,367,95,60,82,987,24,156,209,26,54,186,80,189,37,143,26,318,107,76,85,29,1215,1216,18,16,171,84,19,236,799,28,28,6,103,320,59,402,21,750,245,45,12,292,21,55,31,339,65,62,16,86,98,891,183,1217,599,109,133,5,66,587,471,229,240,4,210,160,95,44,60,102,121,512,88,81,79,137,14,23,42,351,2059,43,61,3,788,326,133,189,8,28,272,78,167,856,357,853,52,26,23,43,17,22,8,28,16,72,59,4,6,135,255,326,265,22,145,24,347,252,143,43,147,34,176,27,129,139,29,499,205,23,31,529,104,405,139,32,28,21,251,105,50,209,404,608,43,33,32,986,639,451,70,144,157,230,275,30,43,50,443,17,38,179,7,108,183,80,18,30,36,13,39,53,34,35,80,201,20,52,35,217,3,39,52,63,9,24,7,4,303,424,118,69,6,46,552,498,33,4,122,146,262,22,148,5,72,3,77,342,95,53,26,86,69,319,232,3,42,49,61,208,7,192,32,51,41,293,582,104,458,140,41,15,482,387,427,35,53,340,196,625,35,40,246,153,176,14,69,360,527,445,444,275,280,630,433,372,358,8,624,296,231,1187,957,33,76,18,75,999,38,15,653,300,488,75,206,289,47,61,58,839,34,134,105,234,157,88,2368,243,292,476,407,276,122,258,245,66,31,429,60,168,31,251,323,78,581,138,8,76,169,51,1179,94,423,68,41,352,1851,150,197,298,32,131,339,138,44,2024,504,212,160,229,284,564,1323,131,2187,214,468,730,208,26,204,238,17,1560,35,381,46,1573,43,605,197,285,21,42,1392,3447,70,184,182,540,23,213,20,183,1154,371,518,10,44,2015,174,107,43,254,255,151,152,104,155,756,148,696,187,56,746,8,489,412,770,84,184,151,288,108,193,52,33,330,309,2174,43,79,194,32,106,636,157,150,21,28,527,420,93,5,4,14,5,17,505,450,441,122,325,39,4,332,313,158,9,8,27,40,80,24,30,173,167,773,3967,123,35,20,246,225,32,27,258,92,54,192,158,15,63,62,3,133,13,125,313,5,171,25,21,38,4,9,177,3,81,373,32,219,131,20,127,4,21,53,23,43,30,123,98,10,300,245,62,12,315,115,515,190,44,46,47,31,19,522,1349,44,41,10,20,47,172,96,189,91,18,60,27,4,44,65,845,45,87,332,321,15,48,203,891,50,10,31,31,90,56,884,44,65,18,171,22,77,163,79,25,31,28,113,145,36,73,16,19,47,17,242,7,30,534,25,9,392,468,41,13,119,68,30,3,308,686,167,48,259,76,42,197,24,78,834,190,21,3,84,18,23,50,547,218,58,27,152,58,174,95,19,9,22,45,224,52,63,669,78,23,38,250,16,105,244,57,47,42,28,11,43,19,57,518,43,849,3,213,3,54,49,15,44,466,37,23,301,69,500,30,27,11,25,49,54,1045,282,270,417,473,342,143,254,788,160,87,65,725,64,240,627,84,281,31,12,35,278,142,57,89,899,274,965,429,500,116,97,540,316,473,66,63,364,107,17,35,120,456,166,223,14,76,936,40,23,29,54,35,736,36,54,36,214,72,61,38,87,106,322,57,186,617,887,324,460,200,310,110,521,105,6,591,430,269,38,295,428,35,52,31,26,169,60,44,39,185,34,45,7,45,115,272,33,490,26,26,532,26,104,133,9};

        SitesFinder lFinder = new SitesFinder(lAccession, lPos);

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
            lConn = d.connect("jdbc:mysql://localhost/ppr", lProps);

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
