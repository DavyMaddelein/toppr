package be.proteomics.pprIA.general.protein_info.finder;


import be.proteomics.pprIA.update.db_store.StoreNewPeptideLocationAndProtein;
import be.proteomics.pprIA.db.accessors.Peptide_group;
import com.compomics.util.protein.Protein;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 08-Jun-2010
 * Time: 10:06:38
 * To change this template use File | Settings | File Templates.
 */
public class PeptideGroupIdentifier {

    private Peptide_group[] iPeptideGroups;
    private Vector peptideGroupNotIdentified = new Vector();
    private Connection iConnPpr;
    private PeptideToProteinMapper iMapper;
    private String iDbSource;
    private PrintWriter iOutFile;
    private String iLocation;
    private boolean iStoreOnlyNewProteins;


    public PeptideGroupIdentifier(Peptide_group[] aPeptideGroups, Connection aConnPpr, String aDbSource, PrintWriter lOutFile, String lLocation, boolean lStoreOnlyNewProteins) {
        this.iConnPpr = aConnPpr;
        this.iDbSource = aDbSource;
        this.iOutFile = lOutFile;
        this.iPeptideGroups = aPeptideGroups;
        this.iLocation = lLocation;
        this.iStoreOnlyNewProteins = lStoreOnlyNewProteins;
        //this.iStoreOnlyNewProteins = false;
    }

    public void identifyPeptideGroups() throws SQLException {
        Vector ids = new Vector();
        Vector sequences = new Vector();
        Vector taxonomy = new Vector();
        for (int i = 0; i < iPeptideGroups.length; i++) {
            ids.add(iPeptideGroups[i].getGroupid());
            sequences.add(iPeptideGroups[i].getPeptide_sequence());
            taxonomy.add(iPeptideGroups[i].getL_taxonomy());
        }
        Long[] idsToIdentify;
        String[] sequencesToIdentify;
        Long[] taxonomyToIdentify;
        idsToIdentify = new Long[ids.size()];
        ids.toArray(idsToIdentify);
        sequencesToIdentify = new String[sequences.size()];
        sequences.toArray(sequencesToIdentify);
        taxonomyToIdentify = new Long[taxonomy.size()];
        taxonomy.toArray(taxonomyToIdentify);


        //Identify and store a peptide_location and a protein for the identified sequences
        if (idsToIdentify.length != 0) {//identify the peptides_group sequences
            iOutFile.write("\tAll the peptide_groups will be identified\n");
            PeptideToProteinMapper mapper = new PeptideToProteinMapper(sequencesToIdentify, idsToIdentify, taxonomyToIdentify, iDbSource, iConnPpr, iOutFile);
            storeIdentifiedPeptides(mapper, idsToIdentify);
        }

    }

    /**
     * This method checkes if all the new peptide_groups were identified.
     * It will call a method that will create and store all the peptide_location and proteins.
     */
    private void storeIdentifiedPeptides(PeptideToProteinMapper map, Long[] groupidsToStore) throws SQLException {
        Long[] groupidsFromIdentifiedPeptides = new Long[0];
        com.compomics.util.protein.Protein[] proteinsFromIdentifiedPeptides = new Protein[0];
        Long[] taxonomysFromIdenitfiedPeptides = new Long[0];

        groupidsFromIdentifiedPeptides = map.getIdsFromIdentifiedPeptides();
        proteinsFromIdentifiedPeptides = map.getSelectedProteins();
        taxonomysFromIdenitfiedPeptides = map.getTaxonomysFromIdentifiedPeptides();


        iOutFile.write("\tIdentification done => Storing new proteins and peptide_locations\n");

        boolean error = false;
        String identificationIdError = "";

        //check if all peptide_groups were identified!
        iOutFile.write("\tChecking if all peptide_groups were identified\n");
        int notstored = 0;
        if (groupidsToStore.length != groupidsFromIdentifiedPeptides.length) {
            for (int i = 0; i < groupidsToStore.length; i++) {
                boolean found = false;
                for (int j = 0; j < groupidsFromIdentifiedPeptides.length; j++) {
                    if (groupidsToStore[i].equals(groupidsFromIdentifiedPeptides[j])) {
                        found = true;
                    }
                }
                if (!found) {
                    error = true;
                    notstored = notstored + 1;
                    iOutFile.write("\tPeptide_group (" + groupidsToStore[i] + ") could not be identified\n");
                    identificationIdError = identificationIdError + groupidsToStore[i] + ", ";
                    peptideGroupNotIdentified.add(iPeptideGroups[i]);

                }
            }
        } else {
            iOutFile.write("\tAll peptide_groups were identified\n");
        }

        //store a peptide_location and a protein for the identified peptideGroups
        for (int i = 0; i < groupidsFromIdentifiedPeptides.length; i++) {
            iOutFile.write("\tProtein " + (1 + i) + "/" + groupidsFromIdentifiedPeptides.length + "\n");
            Peptide_group peptideGroup = null;
            long groupidToMatch = groupidsFromIdentifiedPeptides[i];
            for (int j = 0; j < iPeptideGroups.length; j++) {
                long groupid = iPeptideGroups[j].getGroupid();
                if (groupid == groupidToMatch) {
                    peptideGroup = iPeptideGroups[j];
                }

            }
            String peptide = peptideGroup.getPeptide_sequence();
            StoreNewPeptideLocationAndProtein store = new StoreNewPeptideLocationAndProtein(iConnPpr, proteinsFromIdentifiedPeptides[i], peptide, groupidsFromIdentifiedPeptides[i], taxonomysFromIdenitfiedPeptides[i], iOutFile, iLocation, iStoreOnlyNewProteins);
        }
    }
}

