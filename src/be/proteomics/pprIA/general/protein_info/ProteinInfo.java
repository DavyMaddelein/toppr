package be.proteomics.pprIA.general.protein_info;


/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 6-mei-2008
 * Time: 8:09:26
 * To change this template use File | Settings | File Templates.
 */
public class ProteinInfo {
    private String iInfo;
    private String iSwissProtId;
    private String iSequence;
    private SecondaryStructurePrediction iSecPred;
    private Domain[] iDomainsPfam;
    private Domain[] iDomainsSmart;
    private GoTerm[] iGos;
    private String iSpSecStructure;
    private Homologues iHomologues;

    public ProteinInfo(String aInfo){
        /*this.iInfo = aInfo;
        String info = iInfo;
        iSwissProtId = info.substring(info.indexOf("\n") + 1, info.indexOf("//", info.indexOf("\n")) -1);
        info = info.substring(info.indexOf("//sequence"));

        iSequence = info.substring(info.indexOf("\n") + 1, info.indexOf("//", info.indexOf("\n")) -1 );
        info = info.substring(info.indexOf("//SecStructurePrediction"));

        String secStructPred = info.substring(info.indexOf("\n") + 1, info.indexOf("//", info.indexOf("\n")) -1 );
        info = info.substring(info.indexOf("//SecStructurePrediction E-perc"));

        String ePercString = info.substring(info.indexOf("\n") + 1, info.indexOf("//", info.indexOf("\n")));
        ePercString = ePercString.substring(0, ePercString.lastIndexOf(","));
        String[] ePercArray = ePercString.split(",");
        Double[] ePerc = new Double[ePercArray.length];
        for(int i = 0; i<ePercArray.length ; i++){
            ePerc[i] = Double.valueOf(ePercArray[i]);
        }
        info = info.substring(info.indexOf("//SecStructurePrediction H-perc"));

        String hPercString = info.substring(info.indexOf("\n") + 1, info.indexOf("//", info.indexOf("\n")));
        hPercString = hPercString.substring(0, hPercString.lastIndexOf(","));
        String[] hPercArray = hPercString.split(",");
        Double[] hPerc = new Double[hPercArray.length];
        for(int i = 0; i<hPercArray.length ; i++){
            hPerc[i] = Double.valueOf(hPercArray[i]);
        }
        info = info.substring(info.indexOf("//SecStructureSwissprot"));

        iSecPred = new SecondaryStructurePrediction();
        iSecPred.setEperc(ePerc);
        iSecPred.setHperc(hPerc);
        iSecPred.setPredictions(secStructPred);

        iSpSecStructure = info.substring(info.indexOf("\n") + 1, info.indexOf("//", info.indexOf("\n")) - 1);
        info = info.substring(info.indexOf("//Asa"));

        String asaPred = info.substring(info.indexOf("\n") + 1, info.indexOf("//", info.indexOf("\n")));
        info = info.substring(info.indexOf("//Asa score"));

        String asaString = info.substring(info.indexOf("\n") + 1, info.indexOf("//", info.indexOf("\n")));
        try{
            asaString = asaString.substring(0, asaString.lastIndexOf(","));
            String[] asaArray = asaString.split(",");
            Double[] asaPerc = new Double[asaArray.length];
            for(int i = 0; i<asaArray.length ; i++){
                asaPerc[i] = Double.valueOf(asaArray[i]);
            }
        } catch (StringIndexOutOfBoundsException e){

        }

        info = info.substring(info.indexOf("//GO") + 5);
        Vector gos = new Vector();
        while(!info.startsWith("//")){
            String goInfo = info;
            String goCat = goInfo.substring(0, goInfo.indexOf(','));
            goInfo = goInfo.substring(goInfo.indexOf(',') + 1);
            String goName = goInfo.substring(0, goInfo.indexOf(','));
            goInfo = goInfo.substring(goInfo.indexOf(',') +1);
            String goId = goInfo.substring(0, goInfo.indexOf(','));
            String goEvidenc = goInfo.substring(goInfo.indexOf(',') +1, goInfo.indexOf("\n"));
            GoTerm go = new GoTerm();
            go.setCategory(goCat);
            go.setId(goId);
            go.setName(goName);
            gos.add(go);
            info = info.substring(info.indexOf("\n", info.indexOf("\n") + 2) + 1);
        }

        iGos = new GoTerm[gos.size()];
        gos.toArray(iGos);
        info = info.substring(info.indexOf("//Pfam") + 7);
        Vector domainsPfam = new Vector();
        while(!info.startsWith("//")){
            //String domInfo = info.substring(info.indexOf("\n") + 1, info.indexOf("\n", info.indexOf("\n") + 2));
            String domInfo = info;
            String domLabel = domInfo.substring(0, domInfo.indexOf(','));
            domInfo = domInfo.substring(domInfo.indexOf(',') + 1);
            Integer domStart = Integer.valueOf(domInfo.substring(0, domInfo.indexOf(',')));
            domInfo = domInfo.substring(domInfo.indexOf(',') +1);
            Integer domEnd = Integer.valueOf(domInfo.substring(0, domInfo.indexOf(',')));
            String[] domLink = new String[1];
            domLink[0] = domInfo.substring(domInfo.indexOf(',') +1 , domInfo.indexOf("\n"));
            Domain dom = new Domain();
            dom.setLabel(domLabel);
            dom.setStart(domStart);
            dom.setEnd(domEnd);
            dom.setLink(domLink);
            domainsPfam.add(dom);
            info = info.substring(info.indexOf("\n") + 1);
        }
        Vector domainsSmart = new Vector();
        info = info.substring(info.indexOf("//Smart") + 8);
        while(!info.startsWith("//")){
            //String domInfo = info.substring(info.indexOf("\n") + 1, info.indexOf("\n", info.indexOf("\n") + 2));
            String domInfo = info;
            String domLabel = domInfo.substring(0, domInfo.indexOf(','));
            domInfo = domInfo.substring(domInfo.indexOf(',') + 1);
            Integer domStart = Integer.valueOf(domInfo.substring(0, domInfo.indexOf(',')));
            domInfo = domInfo.substring(domInfo.indexOf(',') +1);
            Integer domEnd = Integer.valueOf(domInfo.substring(0, domInfo.indexOf(',')));
            String[] domLink = new String[1];
            domLink[0] = domInfo.substring(domInfo.indexOf(',') +1 , domInfo.indexOf("\n"));
            Domain dom = new Domain();
            dom.setLabel(domLabel);
            dom.setStart(domStart);
            dom.setEnd(domEnd);
            dom.setLink(domLink);
            domainsSmart.add(dom);
            info = info.substring(info.indexOf("\n") + 1);
        }

        //make iDomains
        iDomainsSmart = new Domain[domainsSmart.size()];
        domainsSmart.toArray(iDomainsSmart);
        iDomainsPfam = new Domain[domainsPfam.size()];
        domainsPfam.toArray(iDomainsPfam);

        info = info.substring(info.indexOf("//Homologues") + 13);
        Vector homologuesIds = new Vector();
        Vector homologuesSeqs = new Vector();
        while(!info.startsWith("//") && info.length() > 0){
            //String homInfo = info.substring(info.indexOf("\n") + 1, info.indexOf("\n", info.indexOf("\n") + 2));
            String homInfo = info;
            String homId = homInfo.substring(0, homInfo.indexOf(','));
            String homSeq = homInfo.substring(homInfo.indexOf(',') +1, homInfo.indexOf("\n"));
            if(!homSeq.equalsIgnoreCase("NO_SEQUENCE_FOUND")){
                homologuesIds.add(homId);
                homologuesSeqs.add(homSeq);
            }
            info = info.substring(info.indexOf("\n") + 1);
        }

        //make homologues
        String[] homIds = new String[homologuesIds.size()];
        String[] homSeqs = new String[homologuesSeqs.size()];
        homologuesIds.toArray(homIds);
        homologuesSeqs.toArray(homSeqs);
        iHomologues = new Homologues(homSeqs,  homIds);  */
    }

    //getters

    public String getInfo() {
        return iInfo;
    }

    public String getSwissProtId() {
        return iSwissProtId;
    }

    public String getSequence() {
        return iSequence;
    }

    public SecondaryStructurePrediction getSecPred() {
        return iSecPred;
    }

    public Domain[] getDomainsSmart() {
        return iDomainsSmart;
    }

    public Domain[] getDomainsPfam() {
        return iDomainsPfam;
    }

    public GoTerm[] getGos() {
        return iGos;
    }

    public String getSpSecStructure() {
        return iSpSecStructure;
    }

    public Homologues getHomologues() {
        return iHomologues;
    }
}


