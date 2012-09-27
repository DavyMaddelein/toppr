package be.proteomics.pprIA.update.db_store;

import com.compomics.ppr.db.accessors.*;

import be.proteomics.pprIA.general.protein_info.GoTerm;
import be.proteomics.pprIA.general.protein_info.Homologues;
import be.proteomics.pprIA.general.protein_info.PdbBlock;
import be.proteomics.pprIA.general.protein_info.PdbParameter;
import be.proteomics.pprIA.general.protein_info.finder.*;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 09-Jun-2010
 * Time: 09:56:59
 * To change this template use File | Settings | File Templates.
 */
public class ExtraProteinInfoStorer {
    private Connection iConnPpr = null;
    private com.compomics.ppr.db.accessors.Protein iProt;
    private be.proteomics.pprIA.general.protein_info.Domain[] iPfamDomains;
    private be.proteomics.pprIA.general.protein_info.Domain[] iSmartDomains;
    private GoTerm[] iGos;
    private Homologues iHomologues;
    private String iSwissProtId;
    private Long proteinId;
    private Vector<PdbParameter> pdbs;
    private PrintWriter iOutFile;

    public ExtraProteinInfoStorer(Connection aConn, com.compomics.ppr.db.accessors.Protein aProt, PrintWriter lOutFile) {
        this.iConnPpr = aConn;
        this.iProt = aProt;
        this.iOutFile = lOutFile;
        this.iSwissProtId = iProt.getSpaccession();

        proteinId = iProt.getProteinid();
        iOutFile.write("\tFetching info for protein : " + iSwissProtId + "\n");
        System.out.println(" Fetching info for protein :" + iSwissProtId);

        iOutFile.write("\tsmart,");
        SmartFinder smart = new SmartFinder(iSwissProtId);
        iSmartDomains = smart.getSmartDomain();

        iOutFile.write("pfam,");
        PfamFinder pfam = new PfamFinder(iSwissProtId);
        iPfamDomains = pfam.getPfamDomain();

        iOutFile.write("go,");
        GoFinder go = new GoFinder(iSwissProtId);
        iGos = go.getGoTerms();

        iOutFile.write("homo,");
        SpToRefSeq spToRef = new SpToRefSeq(iSwissProtId);
        //get ncbi accession for swissprot id
        String[] ncbiAccessions = spToRef.getNcbiAccessions();
        //get homologene ids for ncbiaccessions
        HomologueFinder homo = new HomologueFinder(ncbiAccessions);
        //get ncbi homologues
        String[] ncbiHomologues = homo.getHomologueProteinAcc();
        Vector spHomologs = new Vector();
        Vector uniprotHomologs = new Vector();
        // get for every ncbi homologue the swissprot id
        for (int j = 0; j < ncbiHomologues.length; j++) {
            RefSeqToSp sp = new RefSeqToSp(ncbiHomologues[j]);
            String[] spAccs = sp.getSpAccessions();

            for (int k = 0; k < spAccs.length; k++) {
                String access = spAccs[k];
                uniprotHomologs.add(access);
                boolean found = false;
                for (int l = 0; l < uniprotHomologs.size(); l++) {
                    iOutFile.write("-");
                    String accessMatch = (String) uniprotHomologs.get(l);
                    if (accessMatch.equalsIgnoreCase(access)) {
                        found = true;
                    }
                }
                if (!found) {
                    uniprotHomologs.add(access);
                }
                iOutFile.write("-");
            }
        }

        iOutFile.write("homoSeq,");
        String[] uniprotHomologueAccessions = new String[uniprotHomologs.size()];
        uniprotHomologs.toArray(uniprotHomologueAccessions);
        String[] homologueSequences = new String[uniprotHomologueAccessions.length];
        for (int j = 0; j < uniprotHomologueAccessions.length; j++) {
            GetSequence getSeq = new GetSequence(uniprotHomologueAccessions[j]);
            homologueSequences[j] = getSeq.getSequence();
        }
        iHomologues = new Homologues(homologueSequences, uniprotHomologueAccessions);

        iOutFile.write("pdb\n");
        PdbFinder pdbFinder = new PdbFinder(iSwissProtId);
        pdbs = pdbFinder.getPdbs();

        try {
            storeDomains();
        } catch (SQLException e) {
            e.printStackTrace(iOutFile);
        }
        try {
            storeGoTerms();
        } catch (SQLException e) {
            e.printStackTrace(iOutFile);
        }
        try {
            storeOrhtologues();
        } catch (SQLException e) {
            e.printStackTrace(iOutFile);
        }
        try {
            storePdbs();
        } catch (SQLException e) {
            e.printStackTrace(iOutFile);
        }
        try {
            storeHomologeneIds(homo);
        } catch (SQLException e) {
            e.printStackTrace(iOutFile);
        }

    }

    private void storeGoTerms() throws SQLException {

        for (int g = 0; g < iGos.length; g++) {
            GoTerm go = iGos[g];
            HashMap aParams = new HashMap(3);
            aParams.put(Go.GOTERMID, go.getId());
            aParams.put(Go.L_PROTEINID, proteinId);
            aParams.put(Go.TYPE, go.getCategory());
            aParams.put(Go.NAME, go.getName());
            com.compomics.ppr.db.accessors.Go goToStore = new Go(aParams);
            goToStore.persist(iConnPpr);
        }

    }

    private void storeHomologeneIds(HomologueFinder homo) throws SQLException {
        String[] homoleGeneIds = homo.getHomologueIds();
        for (int g = 0; g < homoleGeneIds.length; g++) {
            if (!homoleGeneIds[g].equalsIgnoreCase("0")) {
                HashMap aParams = new HashMap(2);
                aParams.put(Homologene_accession.L_PROTEINID, proteinId);
                aParams.put(Homologene_accession.HOMOLOGENEID, Long.valueOf(homoleGeneIds[g]));
                com.compomics.ppr.db.accessors.Homologene_accession homologene = new Homologene_accession(aParams);
                homologene.persist(iConnPpr);
            }
        }
    }

    private void storePdbs() throws SQLException {

        for (int g = 0; g < pdbs.size(); g++) {
            PdbParameter pdb = (PdbParameter) pdbs.get(g);
            HashMap aParams = new HashMap(3);
            aParams.put(Pdb.EXPERIMENT_TYPE, pdb.getExperiment_type());
            aParams.put(Pdb.PDBACCESSION, pdb.getPdbaccession());
            aParams.put(Pdb.RESOLUTION, pdb.getResolution());
            aParams.put(Pdb.TITLE, pdb.getTitle());
            aParams.put(Pdb.L_PROTEINID, proteinId);
            com.compomics.ppr.db.accessors.Pdb pdbToStore = new Pdb(aParams);
            pdbToStore.persist(iConnPpr);

            Object[] keys = pdbToStore.getGeneratedKeys();
            Long pdbid = Long.valueOf(keys[0].toString());
            PdbBlock[] blocks = pdb.getBlocks();
            for (int b = 0; b < blocks.length; b++) {
                PdbBlock block = blocks[b];
                HashMap bParams = new HashMap(3);
                bParams.put(Pdb_block.L_PDBID, pdbid);
                bParams.put(Pdb_block.L_PROTEINID, proteinId);
                bParams.put(Pdb_block.BLOCK, block.getBlock());
                bParams.put(Pdb_block.START_PROTEIN, (long) block.getStart_protein());
                bParams.put(Pdb_block.END_PROTEIN, (long) block.getEnd_protein());
                bParams.put(Pdb_block.END_BLOCK, (long) block.getEnd_block());
                bParams.put(Pdb_block.START_BLOCK, (long) block.getStart_block());
                com.compomics.ppr.db.accessors.Pdb_block pdbBlockToStore = new Pdb_block(bParams);
                pdbBlockToStore.persist(iConnPpr);
            }
        }

    }

    private void storeDomains() throws SQLException {

        for (int g = 0; g < iPfamDomains.length; g++) {
            be.proteomics.pprIA.general.protein_info.Domain dom = iPfamDomains[g];
            HashMap aParams = new HashMap(3);
            aParams.put(be.proteomics.ppr.db.accessors.Domain.L_PROTEINID, proteinId);
            aParams.put(be.proteomics.ppr.db.accessors.Domain.TYPE, dom.getType());
            aParams.put(be.proteomics.ppr.db.accessors.Domain.NAME, dom.getLabel());
            aParams.put(be.proteomics.ppr.db.accessors.Domain.DOMAINTERMID, dom.getId());
            aParams.put(be.proteomics.ppr.db.accessors.Domain.START, (long) dom.getStart());
            aParams.put(be.proteomics.ppr.db.accessors.Domain.END, (long) dom.getEnd());
            com.compomics.ppr.db.accessors.Domain domToStore = new com.compomics.ppr.db.accessors.Domain(aParams);
            domToStore.persist(iConnPpr);
        }
        for (int g = 0; g < iSmartDomains.length; g++) {
            be.proteomics.pprIA.general.protein_info.Domain dom = iSmartDomains[g];
            HashMap aParams = new HashMap(3);
            aParams.put(be.proteomics.ppr.db.accessors.Domain.L_PROTEINID, proteinId);
            aParams.put(be.proteomics.ppr.db.accessors.Domain.TYPE, dom.getType());
            aParams.put(be.proteomics.ppr.db.accessors.Domain.NAME, dom.getLabel());
            aParams.put(be.proteomics.ppr.db.accessors.Domain.DOMAINTERMID, dom.getId());
            aParams.put(be.proteomics.ppr.db.accessors.Domain.START, (long) dom.getStart());
            aParams.put(be.proteomics.ppr.db.accessors.Domain.END, (long) dom.getEnd());
            com.compomics.ppr.db.accessors.Domain domToStore = new com.compomics.ppr.db.accessors.Domain(aParams);
            domToStore.persist(iConnPpr);
        }

    }

    private void storeOrhtologues() throws SQLException {

        String[] orthAcc = iHomologues.getHomologueAccessions();
        String[] otrhSeq = iHomologues.getSequences();
        for (int g = 0; g < otrhSeq.length; g++) {
            HashMap aParams = new HashMap(3);
            aParams.put(Orthologue.ENTRY_NAME, orthAcc[g]);
            if (orthAcc[g] == null) {
                aParams.put(Orthologue.SEQUENCE, "NOSEQUENCEFOUND");
            } else {
                aParams.put(Orthologue.SEQUENCE, otrhSeq[g]);
            }

            aParams.put(Orthologue.L_PROTEINID, proteinId);
            com.compomics.ppr.db.accessors.Orthologue orthToStore = new Orthologue(aParams);
            orthToStore.persist(iConnPpr);
        }

    }
        public ExtraProteinInfoStorer(Connection aConn, com.compomics.ppr.db.accessors.Protein aProt) {
        this.iConnPpr = aConn;
        this.iProt = aProt;
        this.iSwissProtId = iProt.getSpaccession();

        proteinId = iProt.getProteinid();
        System.out.println(" Fetching info for protein :" + iSwissProtId);

        SmartFinder smart = new SmartFinder(iSwissProtId);
        iSmartDomains = smart.getSmartDomain();

        PfamFinder pfam = new PfamFinder(iSwissProtId);
        iPfamDomains = pfam.getPfamDomain();

        GoFinder go = new GoFinder(iSwissProtId);
        iGos = go.getGoTerms();

        SpToRefSeq spToRef = new SpToRefSeq(iSwissProtId);
        //get ncbi accession for swissprot id
        String[] ncbiAccessions = spToRef.getNcbiAccessions();
        //get homologene ids for ncbiaccessions
        HomologueFinder homo = new HomologueFinder(ncbiAccessions);
        //get ncbi homologues
        String[] ncbiHomologues = homo.getHomologueProteinAcc();
        Vector spHomologs = new Vector();
        Vector uniprotHomologs = new Vector();
        // get for every ncbi homologue the swissprot id
        for (int j = 0; j < ncbiHomologues.length; j++) {
            RefSeqToSp sp = new RefSeqToSp(ncbiHomologues[j]);
            String[] spAccs = sp.getSpAccessions();

            for (int k = 0; k < spAccs.length; k++) {
                String access = spAccs[k];
                uniprotHomologs.add(access);
                boolean found = false;
                for (int l = 0; l < uniprotHomologs.size(); l++) {
                    String accessMatch = (String) uniprotHomologs.get(l);
                    if (accessMatch.equalsIgnoreCase(access)) {
                        found = true;
                    }
                }
                if (!found) {
                    uniprotHomologs.add(access);
                }
            }
        }
        System.out.println("done");
        String[] uniprotHomologueAccessions = new String[uniprotHomologs.size()];
        uniprotHomologs.toArray(uniprotHomologueAccessions);
        String[] homologueSequences = new String[uniprotHomologueAccessions.length];
        for (int j = 0; j < uniprotHomologueAccessions.length; j++) {
            GetSequence getSeq = new GetSequence(uniprotHomologueAccessions[j]);
            homologueSequences[j] = getSeq.getSequence();
        }
        iHomologues = new Homologues(homologueSequences, uniprotHomologueAccessions);

        PdbFinder pdbFinder = new PdbFinder(iSwissProtId);
        pdbs = pdbFinder.getPdbs();

        try {
            storeDomains();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            storeGoTerms();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            storeOrhtologues();
            System.out.println("number of orthologues: "+iHomologues.getHomologueAccessions().length);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            storePdbs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            storeHomologeneIds(homo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
