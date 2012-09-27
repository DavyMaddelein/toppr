package be.proteomics.pprIA.general;



import be.proteomics.pprIA.general.protein_info.PdbParameter;
import be.proteomics.pprIA.general.protein_info.PdbBlock;
import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.search.FoundProtein;

import java.sql.*;
import java.util.Vector;
import java.util.Properties;
import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ConnectException;

/**
 * Created by IntelliJ IDEA.
 * User: niklaas
 * Date: 19-jan-2009
 * Time: 14:13:57
 * To change this template use File | Settings | File Templates.
 */
public class SaveHtmlFileLocal {


    private Connection iConn;
    private String iUrl;
    BufferedWriter out;
    private int lResultCounter = 1;
    int lCounter = 0;

    public SaveHtmlFileLocal(Connection aConn, String aLocation, FoundProtein[] lFoundProteins, boolean lOnlyProteinsWithPdb){
        this.iConn = aConn;
        boolean onlyPdbWithSites = true;

        // make a sql query based on the requested parameters
        try{


            FoundProtein[] result = lFoundProteins;
            out = new BufferedWriter(new FileWriter(aLocation + "\\result1.html"));
            writeStart(out);

            for(int i = 0; i<result.length; i ++ ){
                
                FoundProtein lProtein = result[i];
                if(lOnlyProteinsWithPdb){

                    if(lProtein.getPdbFound()){
                        Vector sites = lProtein.getProcessingSites();
                        Vector positions = new Vector();
                        for (int p = 0; p < sites.size(); p++) {
                            FoundProcessingSite site = (FoundProcessingSite) sites.get(p);
                            positions.add(site.getPosition());
                        }
                        PreparedStatement prep = iConn.prepareStatement("select p.* from pdb as p, protein as r where r.spaccession = ? and r.proteinid = p.l_proteinid");
                        prep.setString(1, lProtein.getSpAccession());
                        ResultSet rs = prep.executeQuery();
                        Vector pdbVector = new Vector();
                        while(rs.next()){
                            pdbVector.add(new PdbParameter(rs.getString("pdbaccession"),rs.getString("title"),rs.getString("experiment_type"), rs.getString("resolution")));
                        }
                        prep.close();
                        rs.close();
                        for(int p = 0; p<pdbVector.size(); p ++){
                            PdbParameter pdb = (PdbParameter) pdbVector.get(p);
                            PreparedStatement prep1 = iConn.prepareStatement("select b.* from pdb as p, pdb_block as b, protein as r where r.spaccession = ? and r.proteinid = p.l_proteinid and p.pdbaccession = ? and p.pdbid = b.l_pdbid and b.l_proteinid = r.proteinid");
                            prep1.setString(1, lProtein.getSpAccession());
                            prep1.setString(2, pdb.getPdbaccession());
                            ResultSet rs1 = prep1.executeQuery();
                            while(rs1.next()){
                                pdb.addBlock(new PdbBlock(rs1.getString("block"), rs1.getInt("start_protein"), rs1.getInt("end_protein"), rs1.getInt("start_block"), rs1.getInt("end_block")));
                            }
                        }


                        //check if the processing sites are in the pdb file/block
                        boolean found = false;
                        for(int p = 0; p<pdbVector.size(); p ++){
                            PdbParameter pdb = (PdbParameter) pdbVector.get(p);
                            for(int b = 0; b<pdb.getBlocks().length; b ++){
                                PdbBlock block = pdb.getBlocks()[b];
                                Vector selection = new Vector();
                                for(int l = 0; l<positions.size(); l ++){
                                    int lSitePosition = (Integer) positions.get(l);
                                    if(block.getStart_protein() <=  lSitePosition && block.getEnd_protein() >= lSitePosition ){
                                        selection.add(lSitePosition - block.getDifference());

                                    }
                                }
                                if(selection.size() > 0){
                                    found = true;
                                }
                                Integer[] selectedSites = new Integer[selection.size()];
                                selection.toArray(selectedSites);
                                block.setSelectedPositions(selectedSites);
                            }

                        }

                        boolean use = false;
                        if(onlyPdbWithSites){
                            if(found){
                                use = true;
                            }
                        } else {
                            use = true;
                        }

                        use = true;
                        if(use){


                            String jmolLoad = getJmolLoad(pdbVector);
                            String jmolMenu = makePdbHtmlTable(pdbVector);


                            out.write( lProtein.getSpAccession() + " - " +lProtein.getDescription() + "<hr><br>\n");

                            String lAddition = "<table>\n" +
                                    "                <tr>\n" +
                                    "                    <td>\n" +
                                    "                        <tt><div class=\"sequence\" id=\"" + lProtein.getSpAccession() + "\">" +lProtein.getSitesInSequence() + "</div>\n" +
                                    "                        </tt>\n" +
                                    "                    </td>\n" +
                                    "                </tr>\n" +
                                    "                <tr>\n" +
                                    "                    <td colspan=\"2\">\n" +
                                    "                        <div id=\"info" + lProtein.getSpAccession() + "\">&nbsp</div>\n" +
                                    "                    </td>\n" +
                                    "                </tr>\n" +
                                    "                <tr>\n" +
                                    "                    <td colspan=\"2\"><div align=\"center\" id=\"bar" + lProtein.getSpAccession() + "\">" + lProtein.getNavigationProtein() + "</div>\n" +
                                    "                    </td>\n" +
                                    "                </tr>\n" +
                                    "            </table>\n";
                            out.write(lAddition + "<hr><br>\n");

                            for(int j = 0; j<lProtein.getProcessingSites().size(); j ++){
                                FoundProcessingSite lSite = (FoundProcessingSite) lProtein.getProcessingSites().get(j);
                                String[] lSiteTreatments = lSite.getTreatments();
                                String lTreat = "";
                                for(int k = 0; k<lSiteTreatments.length; k ++){
                                    lTreat = lTreat + lSiteTreatments[k] + ", ";
                                }
                                //out.write("    &nbsp&nbsp&nbsp&nbsp&nbsp<i>" + lSite.getPreSite() + " - " + lSite.getPosition() + " - " + lSite.getPostSite() + " &nbsp&nbsp&nbsp&nbsp&nbsp "  + lTreat+  "</i><br><br>\n");

                            }

                            out.write("    <hr><table align=\"center\">\n" +
                                    "        <tr>\n" +
                                    "            <td>\n" +
                                    "<script type=\"text/javascript\">\n");
                            out.write(jmolLoad);
                            out.write("</script>\n            </td>\n" +
                                    "</tr><tr>            <td>\n");
                            out.write(jmolMenu);
                            out.write("            </td>\n" +
                                    "        </tr>\n" +
                                    "    </table><hr><br>\n");
                            lCounter = lCounter + 1;
                            if(lCounter > 10){
                                lCounter = 0;
                                writeEnd(out);
                                out.flush();
                                out.close();

                                lResultCounter = lResultCounter + 1;
                                out = new BufferedWriter(new FileWriter(aLocation + "\\result" + lResultCounter + ".html"));
                                writeStart(out);
                            }

                        }
                    }
                } else {
                    out.write( lProtein.getSpAccession() + " - " +lProtein.getDescription() + "<hr><br>\n");

                            String lAddition = "<table>\n" +
                                    "                <tr>\n" +
                                    "                    <td>\n" +
                                    "                        <tt><div class=\"sequence\" id=\"" + lProtein.getSpAccession() + "\">" +lProtein.getSitesInSequence() + "</div>\n" +
                                    "                        </tt>\n" +
                                    "                    </td>\n" +
                                    "                </tr>\n" +
                                    "                <tr>\n" +
                                    "                    <td>\n" +
                                    "                        <div id=\"info" + lProtein.getSpAccession() + "\">&nbsp</div>\n" +
                                    "                    </td>\n" +
                                    "                </tr>\n" +
                                    "                <tr>\n" +
                                    "                    <td><div align=\"center\" id=\"bar" + lProtein.getSpAccession() + "\">" + lProtein.getNavigationProtein() + "</div>\n" +
                                    "                    </td>\n" +
                                    "                </tr>\n" +
                                    "            </table>\n";
                            out.write(lAddition + "<hr><br>\n");

                            for(int j = 0; j<lProtein.getProcessingSites().size(); j ++){
                                FoundProcessingSite lSite = (FoundProcessingSite) lProtein.getProcessingSites().get(j);
                                String[] lSiteTreatments = lSite.getTreatments();
                                String lTreat = "";
                                for(int k = 0; k<lSiteTreatments.length; k ++){
                                    lTreat = lTreat + lSiteTreatments[k] + ", ";
                                }
                                //out.write("    &nbsp&nbsp&nbsp&nbsp&nbsp<i>" + lSite.getPreSite() + " - " + lSite.getPosition() + " - " + lSite.getPostSite() + " &nbsp&nbsp&nbsp&nbsp&nbsp "  + lTreat+  "</i><br><br>\n");

                            }

                            out.write("<br>\n");
                }

            }

            writeEnd(out);
            out.flush();
            out.close();
            if(lOnlyProteinsWithPdb){
                this.savePdbFiles(result,aLocation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void writeStart(BufferedWriter  lOut) throws IOException {

        lOut.write("<html>\n" +
                "<head>\n" +
                "<title>Jmol</title>\n" +
                "    <script language=\"JavaScript\" src=\"pprIA.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"showhide.js\"></script>" +
                "    <script type=\"text/javascript\" src=\"pdbFiles/Jmol.js\"></script>\n" +
                "</head>\n" +
                "<body>\n");
    }

    public void writeEnd(BufferedWriter  lOut) throws IOException {

        lOut.write("</body>\n" + "</html>");
    }


    public String makePdbHtmlTable(Vector pdbs) {
        String html = "";

        html = html + "<table>";
        for (int i = 0; i < pdbs.size(); i++) {
            PdbParameter pdb = (PdbParameter) pdbs.get(i);
            html = html + "<tr><td>";
            html = html + "<script language=\"JavaScript\" type=\"text/javascript\"> jmolRadio('";

            html = html + "load pdbFiles/files/" + pdb.getPdbaccession() + ".pdb;wireframe off; background white; spacefill off; backbone off; cartoon structure; color cartoon structure;";
            boolean selection = false;
            String selectedSites = "";
            for(int b = 0; b<pdb.getBlocks().length; b ++){
                PdbBlock block = pdb.getBlocks()[b];
                if(block.getSelection()){

                    Integer[] selected = block.getSelectedPositions();
                    for(int s = 0; s<selected.length; s++){
                        if(s == 0){
                            if(!selection){
                                selection = true;
                                selectedSites = selectedSites + " " + selected[s] +":"+ block.getBlock();
                            } else {
                                selectedSites = selectedSites + "," + selected[s] +":"+ block.getBlock();
                            }
                        } else {
                            selectedSites = selectedSites + "," + selected[s] +":"+ block.getBlock();
                        }
                    }
                }
            }
            if(selection){
                html = html + "select " + selectedSites + "; wireframe 0.5; spacefill 0.9; color green;";
            }



            if(i == 0){
                html = html +  "', \"\", true);</script> ";
            } else {
                html = html +  "', \"\", false);</script> ";
            }
            html = html + pdb.getPdbaccession() + ": " + pdb.getTitle().toLowerCase() + " (" + pdb.getExperiment_type().toLowerCase() + ")";
            html = html + "</td></tr>";
        }
        html = html + "</table>";
        return html;

    }

    public String getJmolLoad(Vector pdbs) {
        PdbParameter pdb  = (PdbParameter) pdbs.get(0);
        String jmolLoad = "jmolInitialize(\"pdbFiles/\", false); jmolApplet(800, \"load pdbFiles/files/" + pdb.getPdbaccession() + ".pdb; wireframe off; background white; spacefill off; backbone off; cartoon structure; color cartoon structure;";

        boolean selection = false;
            String selectedSites = "";
            for(int b = 0; b<pdb.getBlocks().length; b ++){
                PdbBlock block = pdb.getBlocks()[b];
                if(block.getSelection()){
                    Integer[] selected = block.getSelectedPositions();
                    for(int s = 0; s<selected.length; s++){
                        if(s == 0){
                            if(!selection){
                                selection = true;
                                selectedSites = selectedSites + " " + selected[s] +":"+ block.getBlock();
                            } else {
                                selectedSites = selectedSites + "," + selected[s] +":"+ block.getBlock();
                            }
                        } else {
                            selectedSites = selectedSites + "," + selected[s] +":"+ block.getBlock();
                        }
                    }
                }
            }
            if(selection){
                jmolLoad = jmolLoad + "select " + selectedSites + "; wireframe 0.5; spacefill 0.9; color green;";
            }

        jmolLoad = jmolLoad + "\");";
        return jmolLoad;
    }

    public void savePdbFiles(FoundProtein[] aProteins, String aSaveLocation){
        try{
            for(int i = 0; i<aProteins.length ; i ++){
                if(aProteins[i].getPdbFound()){
                    for(int j = 0; j<aProteins[i].getPdbAccessions().length ;  j ++){

                        String saveLocation = aSaveLocation + "\\pdbFiles\\files\\" + aProteins[i].getPdbAccessions()[j].toUpperCase() + ".pdb" ;
                        //check if the file already exists
                        File f = new File(saveLocation);
                        if(f.exists()) {
                            System.out.println("Pdb file : " + aProteins[i].getPdbAccessions()[j] + " already saved to " + saveLocation);
                        } else {
                            String url = "http://www.rcsb.org/pdb/files/" + aProteins[i].getPdbAccessions()[j] + ".pdb";
                            String pdbFile =  readUrl(url);
                            //BufferedWriter out = new BufferedWriter(new FileWriter("E:\\" + aProteins[i].getPdbAccessions()[j] + ".pdb"));
                            BufferedWriter out = new BufferedWriter(new FileWriter(saveLocation));
                            out.write(new String(pdbFile));
                            System.out.println("Pdb file : " + aProteins[i].getPdbAccessions()[j] + " saved to " + saveLocation);
                            out.close();
                        }
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String readUrl(String aUrl){
        this.iUrl = aUrl;
        String htmlPage = "";
        try {
            URL myURL=new URL(aUrl);
            StringBuilder input = new StringBuilder();

            HttpURLConnection c = (HttpURLConnection)myURL.openConnection();
	        BufferedInputStream in = new BufferedInputStream(c.getInputStream());
	        Reader r = new InputStreamReader(in);

	        int i;

            while ((i = r.read()) != -1) {
	    	    input.append((char) i);
	        }

            htmlPage = input.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ConnectException e){
            System.out.println("Connect error for url: " + iUrl);
            String html = readUrl(iUrl);
            return html;

        } catch (IOException e) {
            System.out.println("Server returned HTTP response code: 500 for URL: " + iUrl);
            String html = readUrl(iUrl);
            return html;
        }
        return htmlPage;
    }




    public static void main(String[] args){
        java.sql.Connection iConnPpr = null;
        String driverClass = "com.mysql.jdbc.Driver";
        try {
            Driver driver = (Driver) Class.forName(driverClass).newInstance();
            Properties lProps = new Properties();
            lProps.put("user", "root");
            lProps.put("password", "niklaas,13*");
            iConnPpr = driver.connect("jdbc:mysql://localhost/ppr", lProps);
            if (iConnPpr == null) {
                System.out.println("Could not connect to the database ppr. Either your driver is incorrect for this database, or your URL is malformed.");
            }
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        } catch (InstantiationException ie) {
            ie.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        //savers.localPdbView.SaveHtmlFileLocal update = new savers.localPdbView.SaveHtmlFileLocal(iConnPpr, "E:\\temp\\viewerKim", "hGrB_kine");
    }


}
