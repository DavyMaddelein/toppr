package be.proteomics.pprIA.general;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 24-jun-2008
 * Time: 8:54:21
 * To change this template use File | Settings | File Templates.
 */
public class Spectrum {
    String iTitle;
    Double[] iMzs;
    Double[] iIntensities;
    private Vector<FragmentIonToppr> iFragmentions = new Vector<FragmentIonToppr>();
    private boolean iShowFragmentIons = false;

    public Spectrum(String aTitle, Double[] aMzs, Double[] aIntensities) {
        this.iTitle = aTitle;
        this.iMzs = aMzs;
        this.iIntensities = aIntensities;
    }

    public void setShowFragmentIons(boolean lShow){
        iShowFragmentIons = lShow;
    }

    public String getTitle() {
        return iTitle;
    }

    public Double[] getMzs() {
        return iMzs;
    }

    public Double[] getIntensities() {
        return iIntensities;
    }

    public String getIntensString(){
        Double[] intens = this.getIntensities();
        String intString = intens[0] +", ";
        for(int i = 1; i<intens.length; i ++){
             intString = intString + intens[i] +", ";
        }
        intString = intString.substring(0, intString.lastIndexOf(","));
        return intString;
    }
    public String getMzString(){
        Double[] mz = this.getMzs();
        String mzString = mz[0] +", ";
        for(int i = 1; i<mz.length; i ++){
             mzString =  mzString + mz[i] +", ";
        }
        mzString = mzString.substring(0, mzString.lastIndexOf(","));
        return mzString;
    }


    public String getFragmentIonTypes(){
        String lResult = "";
        int lCounter = 0;
        for(int j = 0; j<iMzs.length; j ++){
            boolean lFound = false;
            for(int i = 0; i<iFragmentions.size(); i ++){
                if(iFragmentions.get(i).getDoubleMz() + iFragmentions.get(i).getDoubleError() == iMzs[j]){
                    lFound = true;
                    lResult = lResult + iFragmentions.get(i).getType() + ", ";
                    lCounter = lCounter + 1;
                }
            }
            if(!lFound){
                lResult = lResult + "\"-\", ";
                lCounter = lCounter + 1;
            }

        }
        lResult = lResult.substring(0, lResult.lastIndexOf(","));
        return lResult;
    }

    public String getFragmentIonColor(){
        String lResult = "";
        for(int j = 0; j<iMzs.length; j ++){
            boolean lFound = false;
            for(int i = 0; i<iFragmentions.size(); i ++){
                if(iFragmentions.get(i).getDoubleMz() + iFragmentions.get(i).getDoubleError() == iMzs[j]){
                    lFound = true;
                    lResult = lResult + iFragmentions.get(i).getColor() + ", ";
                }
            }
            if(!lFound){
                lResult = lResult + "\"gray\", ";
            }

        }
        lResult = lResult.substring(0, lResult.lastIndexOf(","));
        return lResult;
    }

    public String getFragmentIonAnnotation(){
        String lResult = "";
        for(int j = 0; j<iMzs.length; j ++){
            boolean lFound = false;
            for(int i = 0; i<iFragmentions.size(); i ++){
                if(iFragmentions.get(i).getDoubleMz() + iFragmentions.get(i).getDoubleError() == iMzs[j]){
                    lFound = true;
                    lResult = lResult + iFragmentions.get(i).getAnnotation() + ", ";
                }
            }
            if(!lFound){
                lResult = lResult + "\"-\", ";
            }

        }
        lResult = lResult.substring(0, lResult.lastIndexOf(","));
        return lResult;
    }

    public String getFragmentIonExtra(){
        String lResult = "";
        for(int j = 0; j<iMzs.length; j ++){
            boolean lFound = false;
            for(int i = 0; i<iFragmentions.size(); i ++){
                if(iFragmentions.get(i).getDoubleMz() + iFragmentions.get(i).getDoubleError() == iMzs[j]){
                    lFound = true;
                    lResult = lResult + iFragmentions.get(i).getExtra() + ", ";
                }
            }
            if(!lFound){
                lResult = lResult + "\"-\", ";
            }

        }
        lResult = lResult.substring(0, lResult.lastIndexOf(","));
        return lResult;
    }

    public void addFragmentIon(FragmentIonToppr lFragmention) {
        this.iFragmentions.add(lFragmention);
    }

    public String getFragmentIons(){
        String lResult = "";
        
        for(int i = 0; i<iFragmentions.size(); i ++){
            lResult = lResult + iFragmentions.get(i).toString() + "|";
        }

        if(lResult.indexOf("|") > 0){
            lResult = lResult.substring(0, lResult.lastIndexOf("|"));
        }
        return lResult;
    }

    public boolean showFragmentIons() {
        return iShowFragmentIons;
    }

}
