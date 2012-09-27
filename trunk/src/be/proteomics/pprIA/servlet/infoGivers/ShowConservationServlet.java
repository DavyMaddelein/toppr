package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.GlobalAlignment;
import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.PerformedSearches;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 14-mei-2008
 * Time: 14:08:37
 * To change this template use File | Settings | File Templates.
 */
public class ShowConservationServlet extends HttpServlet {


    private Connection iConn;
    private DataSource datasource = null;


    public void init() throws ServletException {
        try {
            //Create a datasource for pooled connections.
            datasource = (DataSource) getServletContext().getAttribute("DBPool");
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            //get session
            HttpSession session = req.getSession(true);
            //get searches, session, proteins, protein number and site number
            PerformedSearches searches = (PerformedSearches) session.getValue("searches");
            String ses = req.getParameter("session");
            int sessionid = Integer.valueOf(ses);
            FoundProtein[] proteins = searches.getFoundProteins(sessionid);
            String pro = req.getParameter("protein");
            int proteinInt = Integer.valueOf(pro);
            FoundProtein protein = proteins[proteinInt];
            Vector sites = protein.getProcessingSites();
            String swissProtId = protein.getSpAccession();
            //get connection from connection pool
            iConn = this.getConnection();

            //find the proteins for the given parameters
            PreparedStatement prep = null;
            prep = iConn.prepareStatement("select o.* from protein as r , orthologue as o where r.spaccession = ? and o.l_proteinid = r.proteinid");
            prep.setString(1, swissProtId);
            ResultSet rs = prep.executeQuery();
            Vector homoAccVector = new Vector();
            Vector homoSeqVector = new Vector();
            while (rs.next()) {
                homoAccVector.add(rs.getString("entry_name"));
                homoSeqVector.add(rs.getString("sequence"));
            }
            prep.close();
            rs.close();

            String[] homoSeq = new String[homoSeqVector.size()];
            homoSeqVector.toArray(homoSeq);
            String[] homoIds = new String[homoAccVector.size()];
            homoAccVector.toArray(homoIds);
            if (homoIds.length == 0) {
                // set up the response
                res.setContentType("text/xml");
                res.setHeader("Cache-Control", "no-cache");
                // write out the response string
                res.getWriter().write("No homologue proteins found");
            }  else {
                //get matrix
                String matrix = "#  Matrix made by matblas from blosum62.iij\n" +
                        "#  * column uses minimum score\n" +
                        "#  BLOSUM Clustered Scoring Matrix in 1/2 Bit Units\n" +
                        "#  Blocks Database = /data/blocks_5.0/blocks.dat\n" +
                        "#  Cluster Percentage: >= 62\n" +
                        "#  Entropy =   0.6979, Expected =  -0.5209\n" +
                        "   A  R  N  D  C  Q  E  G  H  I  L  K  M  F  P  S  T  W  Y  V  B  Z  X  *\n" +
                        "A  4 -1 -2 -2  0 -1 -1  0 -2 -1 -1 -1 -1 -2 -1  1  0 -3 -2  0 -2 -1  0 -4 \n" +
                        "R -1  5  0 -2 -3  1  0 -2  0 -3 -2  2 -1 -3 -2 -1 -1 -3 -2 -3 -1  0 -1 -4 \n" +
                        "N -2  0  6  1 -3  0  0  0  1 -3 -3  0 -2 -3 -2  1  0 -4 -2 -3  3  0 -1 -4 \n" +
                        "D -2 -2  1  6 -3  0  2 -1 -1 -3 -4 -1 -3 -3 -1  0 -1 -4 -3 -3  4  1 -1 -4 \n" +
                        "C  0 -3 -3 -3  9 -3 -4 -3 -3 -1 -1 -3 -1 -2 -3 -1 -1 -2 -2 -1 -3 -3 -2 -4 \n" +
                        "Q -1  1  0  0 -3  5  2 -2  0 -3 -2  1  0 -3 -1  0 -1 -2 -1 -2  0  3 -1 -4 \n" +
                        "E -1  0  0  2 -4  2  5 -2  0 -3 -3  1 -2 -3 -1  0 -1 -3 -2 -2  1  4 -1 -4 \n" +
                        "G  0 -2  0 -1 -3 -2 -2  6 -2 -4 -4 -2 -3 -3 -2  0 -2 -2 -3 -3 -1 -2 -1 -4 \n" +
                        "H -2  0  1 -1 -3  0  0 -2  8 -3 -3 -1 -2 -1 -2 -1 -2 -2  2 -3  0  0 -1 -4 \n" +
                        "I -1 -3 -3 -3 -1 -3 -3 -4 -3  4  2 -3  1  0 -3 -2 -1 -3 -1  3 -3 -3 -1 -4 \n" +
                        "L -1 -2 -3 -4 -1 -2 -3 -4 -3  2  4 -2  2  0 -3 -2 -1 -2 -1  1 -4 -3 -1 -4 \n" +
                        "K -1  2  0 -1 -3  1  1 -2 -1 -3 -2  5 -1 -3 -1  0 -1 -3 -2 -2  0  1 -1 -4 \n" +
                        "M -1 -1 -2 -3 -1  0 -2 -3 -2  1  2 -1  5  0 -2 -1 -1 -1 -1  1 -3 -1 -1 -4 \n" +
                        "F -2 -3 -3 -3 -2 -3 -3 -3 -1  0  0 -3  0  6 -4 -2 -2  1  3 -1 -3 -3 -1 -4 \n" +
                        "P -1 -2 -2 -1 -3 -1 -1 -2 -2 -3 -3 -1 -2 -4  7 -1 -1 -4 -3 -2 -2 -1 -2 -4 \n" +
                        "S  1 -1  1  0 -1  0  0  0 -1 -2 -2  0 -1 -2 -1  4  1 -3 -2 -2  0  0  0 -4 \n" +
                        "T  0 -1  0 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -2 -1  1  5 -2 -2  0 -1 -1  0 -4 \n" +
                        "W -3 -3 -4 -4 -2 -2 -3 -2 -2 -3 -2 -3 -1  1 -4 -3 -2 11  2 -3 -4 -3 -2 -4 \n" +
                        "Y -2 -2 -2 -3 -2 -1 -2 -3  2 -1 -1 -2 -1  3 -3 -2 -2  2  7 -1 -3 -2 -1 -4 \n" +
                        "V  0 -3 -3 -3 -1 -2 -2 -3 -3  3  1 -2  1 -1 -2 -2  0 -3 -1  4 -3 -2 -1 -4 \n" +
                        "B -2 -1  3  4 -3  0  1 -1  0 -3 -4  0 -3 -3 -2  0 -1 -4 -3 -3  4  1 -1 -4 \n" +
                        "Z -1  0  0  1 -3  3  4 -2  0 -3 -3  1 -1 -3 -1  0 -1 -3 -2 -2  1  4 -1 -4 \n" +
                        "X  0 -1 -1 -1 -2 -1 -1 -1 -1 -1 -1 -1 -1 -1 -2  0  0 -2 -1 -1 -1 -1 -1 -4 \n" +
                        "* -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4  1 ";

                String html = "";
                // do all the alignments for all the ortho or homologues
                Vector alignments = new Vector();
                for (int j = 0; j < homoSeq.length; j++) {
                    if(homoSeq[j]!= null){
                        if (homoSeq[j].indexOf('u') > 0 || homoSeq[j].indexOf('U') > 0) {
                            alignments.add(null);
                        } else {
                            alignments.add(new GlobalAlignment(matrix, protein.getSequence(), homoSeq[j]));
                        }
                    } else {
                        alignments.add(null);
                    }
                }
                if (homoSeq.length != 0) {
                    for (int i = 0; i < sites.size(); i++) {
                        FoundProcessingSite site = (FoundProcessingSite) sites.get(i);
                        String seqToAlign = site.getPreSite() + site.getPostSite();
                        html = html + "<br>" + site.getStart() + "-" + site.getPreSite() + "<img src=\"images/scissorsSmall99even.jpg\" align=\"middle\">" + site.getPostSite() + "-" + site.getEnd();
                        html = html + "<tt>";
                        for (int j = 0; j < homoSeq.length; j++) {
                            if(homoSeq[j] != null){
                                if (homoSeq[j].indexOf('u') > 0 || homoSeq[j].indexOf('U') > 0) {
                                    // || site.getPosition() + iWidth > protein.getSequence().length()|| site.getPosition() < iWidth
                                } else if(!protein.getUpAccession().equalsIgnoreCase(homoIds[j])){

                                    ////System.out.println(protein.getSpAccession());
                                    GlobalAlignment global = (GlobalAlignment) alignments.get(j);
                                    String accession = protein.getUpAccession();
                                    for (int k = accession.length(); k <= 20; k++) {
                                        accession = accession + "&nbsp";
                                    }
                                    String accession2 = homoIds[j];
                                    for (int k = accession2.length(); k <= 20; k++) {
                                        accession2 = accession2 + "&nbsp";
                                    }
                                    String overLap = "";
                                    for (int k = overLap.length(); k <= 20; k++) {
                                        overLap = overLap + "&nbsp";
                                    }
                                    accession = accession.replace(protein.getUpAccession(), "<a href=\"http://www.uniprot.org/uniprot/" + protein.getUpAccession() + "\">" + protein.getUpAccession() + "</a>");
                                    accession2 = accession2.replace(homoIds[j], "<a href=\"http://www.uniprot.org/uniprot/" + homoIds[j] + "\">" + homoIds[j] + "</a>");
                                    int position = 0;
                                    int splitPosition = 0;
                                    String preSite = site.getPreSite().trim();
                                    //for global alignemnt
                                    String postSite = site.getPostSite().trim();
                                    for (int f = 0; f < global.getAlignSeq2().length(); f++) {
                                        String match = global.getAlignSeq1().substring(f).replace("-","");
                                        if (match.startsWith(postSite)) {
                                            splitPosition = f;
                                            f = global.getAlignSeq2().length();
                                        }
                                    }


                                    Vector pre = new Vector();
                                    Vector post = new Vector();


                                    String align1Pre = global.getAlignSeq1().substring(0, splitPosition);
                                    if (align1Pre.length() > 15) {
                                        align1Pre = align1Pre.substring((align1Pre.length() - 15));
                                    }
                                    String align2Pre = global.getAlignSeq2().substring(0, splitPosition);
                                    if (align2Pre.length() > 15) {
                                        align2Pre = align2Pre.substring((align2Pre.length() - 15));
                                    }
                                    String align1Post = global.getAlignSeq1().substring(splitPosition);
                                    if (align1Post.length() > 15) {
                                        align1Post = align1Post.substring(0, 15);
                                    }
                                    String align2Post = global.getAlignSeq2().substring(splitPosition);
                                    if (align2Post.length() > 15) {
                                        align2Post = align2Post.substring(0, 15);
                                    }

                                    overLap = overLap + " ";
                                    for(int c = 0; c<align1Pre.length() ; c ++){
                                        if(align1Pre.charAt(c) == align2Pre.charAt(c)){
                                            overLap = overLap + "|";
                                        } else {
                                            if(align1Pre.charAt(c) =='-'|| align2Pre.charAt(c) == '-'){
                                                overLap = overLap + "&nbsp;";
                                            }else {
                                                Double score = global.getScores()[splitPosition - align1Pre.length() + c];
                                                if(score > 0){
                                                    overLap = overLap + ":";
                                                } else {
                                                    overLap = overLap + ".";
                                                }
                                            }
                                        }
                                    }
                                    overLap = overLap + "&nbsp&nbsp&nbsp;";
                                    for(int c = 0; c<align1Post.length() ; c ++){
                                        if(align1Post.charAt(c) == align2Post.charAt(c)){
                                            overLap = overLap + "|";
                                        } else {
                                            if(align1Post.charAt(c) =='-'|| align2Post.charAt(c) == '-'){
                                                overLap = overLap + "&nbsp;";
                                            }else {
                                                Double score = global.getScores()[splitPosition + c];
                                                if(score > 0){
                                                    overLap = overLap + ":";
                                                } else {
                                                    overLap = overLap + ".";
                                                }
                                            }

                                        }
                                    }

                                    html = html + "<br>" + accession + " " + align1Pre + " ! " + align1Post;
                                    html = html + "<br>" + overLap;
                                    html = html + "<br>" + accession2 + " " + align2Pre + " ! " + align2Post + "<br>";


                                }
                            }
                        }

                        html = html + "</tt>";
                    }
                } else {
                    html = "No homologues or orthologues found";
                }

                // set up the response
                res.setContentType("text/xml");
                res.setHeader("Cache-Control", "no-cache");
                // write out the response string
                res.getWriter().write(html);


            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                if (iConn != null) iConn.close();
            } catch (SQLException e) {
            }
        }


    }

    private synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }


}