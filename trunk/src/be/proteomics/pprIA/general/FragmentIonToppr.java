package be.proteomics.pprIA.general;

import com.compomics.ppr.db.accessors.Fragmention;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas
 * Date: 15-Jun-2010
 * Time: 10:26:35
 */
public class FragmentIonToppr extends Fragmention {
    double iDoubleMz;
    double iDoubleError;
    public FragmentIonToppr(HashMap aParams) {
        super(aParams);
    }

    public FragmentIonToppr(ResultSet aRS) throws SQLException {
        super(aRS);
        iDoubleMz = aRS.getDouble("mz");
        iDoubleError = aRS.getDouble("massdelta");
    }


    public double getDoubleMz() {
        return iDoubleMz;
    }

    public double getDoubleError() {
        return iDoubleError;
    }

    public String toString(){
        String lResult = "";

        String lCharge = "1";
        if(this.getIonname().contains("++")){
            lCharge = "2";
        }
        String lColor = "green";
        String lType = "-";
        String lTitle = this.getIonname().toUpperCase();
        if(lTitle.indexOf("B") >= 0){
            lTitle = lTitle.replace("B", "B" + this.getFragmentionnumber());
        } else if(lTitle.indexOf("Y") >= 0){
            lTitle = lTitle.replace("Y", "Y" + this.getFragmentionnumber());
        } else if(lTitle.indexOf("A") >= 0){
            lTitle = lTitle.replace("A", "A" + this.getFragmentionnumber());
        } else if(lTitle.indexOf("Z") >= 0){
            lTitle = lTitle.replace("Z", "Z" + this.getFragmentionnumber());
        } else {
            lTitle = lTitle + "/" + this.getFragmentionnumber();
        }

        if(this.getIontype() <= 2 && this.getIontype()>= 1){
            lColor = "blue";
            lType = "B";
        }
        if(this.getIontype() <= 8 && this.getIontype()>= 7){
            lColor = "red";
            lType = "Y";
        }
        lResult = lResult + "\"" + this.getMz() + "\",";
        lResult = lResult + "\"" + lType + "\",";
        lResult = lResult + "\"" + lTitle + "\",";
        lResult = lResult + "\"" + lColor + "\",";
        lResult = lResult + "\"Mass Error: " + iDoubleError + "\",";
        lResult = lResult + "\"product ion charge: " + lCharge + "\"";

        return lResult; 
    }

    public String getType(){
        String lType = "\"-\"";
        if(this.getIontype() <= 2 && this.getIontype()>= 1){
            lType = "\"B\"";
        }
        if(this.getIontype() <= 8 && this.getIontype()>= 7){
            lType = "\"Y\"";
        }
        return lType;
    }

    public String getColor(){
        String lColor = "\"green\"";
        if(this.getIontype() <= 2 && this.getIontype()>= 1){
            lColor = "\"blue\"";
        }
        if(this.getIontype() <= 8 && this.getIontype()>= 7){
            lColor = "\"red\"";
        }
        return lColor;
    }

    public String getAnnotation(){
        String lTitle = this.getIonname().toUpperCase();
        if(lTitle.indexOf("B") >= 0){
            lTitle = lTitle.replace("B", "B" + this.getFragmentionnumber());
        } else if(lTitle.indexOf("Y") >= 0){
            lTitle = lTitle.replace("Y", "Y" + this.getFragmentionnumber());
        } else if(lTitle.indexOf("A") >= 0){
            lTitle = lTitle.replace("A", "A" + this.getFragmentionnumber());
        } else if(lTitle.indexOf("Z") >= 0){
            lTitle = lTitle.replace("Z", "Z" + this.getFragmentionnumber());
        } else {
            lTitle = lTitle + "/" + this.getFragmentionnumber();
        }
        lTitle = "\"" + lTitle + "\"";
        return lTitle;
    }

    public String getExtra(){
        String lExtra = "\"Mass Error: " + iDoubleError + "\"";
        return lExtra; 
    }
}
