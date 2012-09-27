package be.proteomics.pprIA.servlet.infoGivers;

import be.proteomics.pprIA.general.FragmentIonToppr;
import be.proteomics.pprIA.general.Spectrum;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 24-jun-2008
 * Time: 8:57:40
 * To change this template use File | Settings | File Templates.
 */
public class ShowSpectrumServlet  extends HttpServlet {

    private String userName;
    private java.sql.Connection iConn;
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
            Spectrum spectrum = null;
            iConn = this.getConnection();
            HttpSession session = req.getSession(true);
            userName = (String) session.getValue("userName");
            if (userName == null) {
                userName = "guest";
            }
            //identificationid
            String idString = req.getParameter("id");
            String lShow = req.getParameter("show");
            boolean lShowFragmentIons = new Boolean(lShow);
            int id = Integer.valueOf(idString);
            String resultString = "";

            PreparedStatement prep = null;
                        prep = iConn.prepareStatement("select spectrumfile, modified_sequence from peptide where identificationid = " + id);
                        ResultSet rs = prep.executeQuery();
            String lModSeq = "Spectrum";
            while(rs.next()){
                lModSeq = rs.getString(2);
                            byte[] zipped = rs.getBytes(1);
                            ByteArrayInputStream bais = new ByteArrayInputStream(zipped);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            BufferedInputStream bis = new BufferedInputStream(new GZIPInputStream(bais));
                            BufferedOutputStream bos = new BufferedOutputStream(baos);
                            int read = -1;
                            while((read = bis.read()) != -1) {
                                bos.write(read);
                            }
                            bos.flush();
                            baos.flush();
                            byte[] result = baos.toByteArray();
                            bos.close();
                            bis.close();
                            bais.close();
                            baos.close();

                            resultString = new String(result);
                        }

            String[] file = resultString.split("\n");
            Vector mz = new Vector();
            Vector intens = new Vector();
            for(int f = 0; f<file.length; f ++){
                String line = file[f];
                boolean use = true;
                for(int l = 0; l<line.length() ; l ++){
                    if(line.charAt(l) < 123 && line.charAt(l) > 64){
                        use = false;
                        l = line.length();
                    }
                }
                if(line.length() == 0){
                    use = false;
                }
                if(use){
                    mz.add(Double.valueOf(line.substring(0,line.indexOf(' '))));
                    String intensi = line.substring(line.indexOf(' ') + 1);
                    if(intensi.indexOf("\t") > -1){
                        intensi = intensi.substring(0, intensi.indexOf("\t"));
                    }
                    intens.add(Double.valueOf(intensi));
                }
            }
            Double[] mzs = new Double[mz.size()];
            Double[] intenss = new Double[intens.size()];
            mz.toArray(mzs);
            intens.toArray(intenss);
            spectrum = new Spectrum(lModSeq,mzs, intenss);
            spectrum.setShowFragmentIons(lShowFragmentIons);
            prep.close();
            rs.close();

            PreparedStatement prep1 = null;
                        prep1 = iConn.prepareStatement("select * from fragmention where l_peptideid = " + id);
                        ResultSet rs1 = prep1.executeQuery();
            while(rs1.next()){
                FragmentIonToppr lFragmention = new FragmentIonToppr(rs1);
                spectrum.addFragmentIon(lFragmention);
            }
            prep1.close();
            rs1.close();
            session.putValue("spectrumView", spectrum);
            String url = "/spectrumView.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(req, res);

        }
        catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
        finally {
            try {
                if (iConn != null) iConn.close();
            } catch (SQLException e) {
            }
        }
    }

    private synchronized java.sql.Connection getConnection() throws SQLException {
        return datasource.getConnection();    // Allocate and use a connection from the pool
    }

}