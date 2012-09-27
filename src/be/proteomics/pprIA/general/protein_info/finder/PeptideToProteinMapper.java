package be.proteomics.pprIA.general.protein_info.finder;

import com.compomics.dbtoolkit.io.UnknownDBFormatException;
import com.compomics.dbtoolkit.io.implementations.AutoDBLoader;
import be.proteomics.ppr.db.accessors.Taxonomy;
import com.compomics.dbtoolkit.io.interfaces.DBLoader;
import com.compomics.util.protein.Protein;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 08-Jun-2010
 * Time: 10:08:44
 * To change this template use File | Settings | File Templates.
 */
public class PeptideToProteinMapper {


    /**
     * Constant array of Strings that hold all the sequences for the peptides that will be identified.
     */
    protected String[] iPeptides;
    /**
     * Constant array of Protiens that hold all the found Proteins.
     */
    protected Protein[] selectedProteins;
    /**
     * Constant array that holds all groupsids from the identified peptides.
     */
    protected Long[] idsFromIdentifiedPeptides;
    /**
     * Constant array that holds all the taxonomys from the identified peptides.
     */
    protected Long[] taxonomysFromIdentifiedPeptides;
    /**
     * Constant array of Longs that holds all the groupids for the peptides that will be identified.
     */
    protected Long[] iIds;
    /**
     * Constant String with the location of the database.
     */
    protected String iSource;
    /**
     * Constant array of Longs that holds all the taxonomys for the peptides that will be identified.
     */
    protected Long[] iTaxonomys;

    protected Connection iConnPpr;
    private PrintWriter iOutFile;


    public PeptideToProteinMapper(String[] aPeptides, Long[] aIds, Long[] aTaxonomys, String aSource, Connection aConnPpr, PrintWriter lOutFile) {
        this.iPeptides = aPeptides;
        this.iIds = aIds;
        this.iSource = aSource;
        this.iTaxonomys = aTaxonomys;
        this.iConnPpr = aConnPpr;
        this.iOutFile = lOutFile;


        String[] taxonomysScientificName = new String[iTaxonomys.length];
        for (int i = 0; i < taxonomysScientificName.length; i++) {
            try {
                taxonomysScientificName[i] = Taxonomy.getScientificNameFromTaxonomyID(iConnPpr, iTaxonomys[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Vector<String> lUniqueTaxonomies = new Vector<String>();
        for (int i = 0; i < taxonomysScientificName.length; i++) {
            boolean lNew = true;
            for(int j = 0; j<lUniqueTaxonomies.size(); j ++){
                if(lUniqueTaxonomies.get(j).equalsIgnoreCase(taxonomysScientificName[i])){
                    lNew = false;
                }
            }
            if(lNew){
                lUniqueTaxonomies.add(taxonomysScientificName[i]);
            }
        }


        File input = new File(iSource);

        // Default built-in DBLoader types.
        Properties p = null;
        if (p == null || p.size() == 0) {
            p = new Properties();
            p.put("1", "com.compomics.dbtoolkit.io.implementations.SwissProtDBLoader");
            p.put("2", "com.compomics.dbtoolkit.io.implementations.FASTADBLoader");
        }
        String[] classNames = new String[p.size()];
        Iterator it = p.values().iterator();
        int counter = 0;
        while (it.hasNext()) {
            classNames[counter] = (String) it.next();
            counter++;
        }

        AutoDBLoader adb = new AutoDBLoader(classNames);
        DBLoader loader = null;
        try {
            loader = adb.getLoaderForFile(input.getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (UnknownDBFormatException udfe) {
            udfe.printStackTrace();
        }
        if (loader == null) {
            JOptionPane.showMessageDialog(new JFrame(), new String[]{"No connection could be made to " + iSource + " !"}, "ERROR", JOptionPane.ERROR_MESSAGE);

        } else {
            // now read the db and see if we can find anything.
            try {
                int lProteinCounter = 0;
                Protein prot;
                String raw = null;
                Vector proteins = new Vector();
                Vector ids = new Vector();
                Vector taxs = new Vector();
                while ((raw = loader.nextRawEntry()) != null) {
                    boolean use = true;
                    if (raw.indexOf("hydroxymyristoyl-[acyl-carrier-protein] dehydratase") > -1) {
                        use = false;
                    }
                    if (use) {
                        lProteinCounter = lProteinCounter + 1;
                        prot = new Protein(raw);
                        String sequence = prot.getSequence().getSequence();
                        boolean lCheck = false;
                        for(int j = 0; j<lUniqueTaxonomies.size(); j ++){
                            if(raw.indexOf(lUniqueTaxonomies.get(j)) >= 0){
                                lCheck = true;
                            }
                        }
                        if(lCheck){
                            //search all peptides
                            for (int i = 0; i < iPeptides.length; i++) {
                                if(raw.contains(taxonomysScientificName[i])){
                                    if (sequence.contains(iPeptides[i])) {
                                        //The sequence of the peptide is found in the protein
                                        proteins.add(prot);
                                        ids.add(iIds[i]);
                                        taxs.add(iTaxonomys[i]);
                                    }
                                }
                            }
                        }
                    }
                }

                iOutFile.write("\tChecked" + lProteinCounter + "\n");
                selectedProteins = new Protein[proteins.size()];
                proteins.toArray(selectedProteins);
                idsFromIdentifiedPeptides = new Long[ids.size()];
                ids.toArray(idsFromIdentifiedPeptides);
                taxonomysFromIdentifiedPeptides = new Long[taxs.size()];
                taxs.toArray(taxonomysFromIdentifiedPeptides);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Protein[] getSelectedProteins() {
        return selectedProteins;
    }

    public Long[] getIdsFromIdentifiedPeptides() {
        return idsFromIdentifiedPeptides;
    }

    public Long[] getTaxonomysFromIdentifiedPeptides() {
        return taxonomysFromIdentifiedPeptides;
    }
}

