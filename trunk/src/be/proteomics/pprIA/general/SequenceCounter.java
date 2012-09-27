package be.proteomics.pprIA.general;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 12-jun-2008
 * Time: 16:39:15
 * To change this template use File | Settings | File Templates.
 */
public class SequenceCounter {
    private double totaleCount = 0;
    private double a = 0;
    private double b = 0;
    private double c = 0;
    private double d = 0;
    private double e = 0;
    private double f = 0;
    private double g = 0;
    private double h = 0;
    private double i = 0;
    private double j = 0;
    private double k = 0;
    private double l = 0;
    private double m = 0;
    private double n = 0;
    private double o = 0;
    private double p = 0;
    private double q = 0;
    private double r = 0;
    private double s = 0;
    private double t = 0;
    private double u = 0;
    private double v = 0;
    private double w = 0;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double _ = 0;

    //Swissprot mean
    private double aSP = 8.13;
    private double bSP = 0;
    private double cSP = 1.42;
    private double dSP = 5.41;
    private double eSP = 6.74;
    private double fSP = 3.88;
    private double gSP = 7.04;
    private double hSP = 2.28;
    private double iSP = 5.92;
    private double jSP = 0;
    private double kSP = 5.88;
    private double lSP = 9.76;
    private double mSP = 2.41;
    private double nSP = 4.05;
    private double oSP = 0;
    private double pSP = 4.77;
    private double qSP = 3.95;
    private double rSP = 5.50;
    private double sSP = 6.66;
    private double tSP = 5.36;
    private double uSP = 0;
    private double vSP = 6.83;
    private double wSP = 1.09;
    private double xSP = 0;
    private double ySP = 2.93;
    private double zSP = 0;
    private double _SP = 0;

    private double min = 99999.9;
    private double max = -99999.9;
    private double range = 99999.9;

    public SequenceCounter() {

    }

    public void setTotaleCount(double count){
        totaleCount = count;
    }

    public void addSequence(char sequence){
        totaleCount = totaleCount + 1;
        if(sequence == 'a' || sequence == 'A'){
            a= a +1;
        }
        if(sequence == 'b' || sequence == 'B'){
            b= b +1;
        }
        if(sequence == 'c' || sequence == 'C'){
            c= c +1;
        }
        if(sequence == 'd' || sequence == 'D'){
            d= d +1;
        }
        if(sequence == 'e' || sequence == 'E'){
            e= e +1;
        }
        if(sequence == 'f' || sequence == 'F'){
            f= f +1;
        }
        if(sequence == 'g' || sequence == 'G'){
            g= g +1;
        }
        if(sequence == 'h' || sequence == 'H'){
            h= h +1;
        }
        if(sequence == 'i' || sequence == 'I'){
            i= i +1;
        }
        if(sequence == 'j' || sequence == 'J'){
            j= j +1;
        }
        if(sequence == 'k' || sequence == 'K'){
            k= k +1;
        }
        if(sequence == 'l' || sequence == 'L'){
            l= l +1;
        }
        if(sequence == 'm' || sequence == 'M'){
            m= m +1;
        }
        if(sequence == 'n' || sequence == 'N'){
            n= n +1;
        }
        if(sequence == 'o' || sequence == 'O'){
            o= o +1;
        }
        if(sequence == 'p' || sequence == 'P'){
            p= p +1;
        }
        if(sequence == 'q' || sequence == 'Q'){
            q= q +1;
        }
        if(sequence == 'r' || sequence == 'R'){
            r= r +1;
        }
        if(sequence == 's' || sequence == 'S'){
            s= s +1;
        }
        if(sequence == 't' || sequence == 'T'){
            t= t +1;
        }
        if(sequence == 'u' || sequence == 'U'){
            u= u +1;
        }
        if(sequence == 'v' || sequence == 'V'){
            v= v +1;
        }
        if(sequence == 'w' || sequence == 'W'){
            w= w +1;
        }
        if(sequence == 'x' || sequence == 'X'){
            x= x +1;
        }
        if(sequence == 'y' || sequence == 'Y'){
            y= y +1;
        }
        if(sequence == 'z' || sequence == 'Z'){
            z= z +1;
        }
        if(sequence == '-' || sequence == '_'){
            _= _ +1;
        }
    }

    //getters

    public double getMaxCount(){
        if(max == -99999.9){
            if(a > max){
                max = a;
            }
            if(b > max){
                max = b;
            }
            if(c > max){
                max = c;
            }
            if(d > max){
                max = d;
            }
            if(e > max){
                max = e;
            }
            if(f > max){
                max = f;
            }
            if(g > max){
                max = g;
            }
            if(h > max){
                max = h;
            }
            if(i > max){
                max = i;
            }
            if(j > max){
                max = j;
            }
            if(k > max){
                max = k;
            }
            if(l > max){
                max = l;
            }
            if(m > max){
                max = m;
            }
            if(n > max){
                max = n;
            }
            if(o > max){
                max = o;
            }
            if(p > max){
                max = p;
            }
            if(q > max){
                max = q;
            }
            if(r > max){
                max = r;
            }
            if(s > max){
                max = s;
            }
            if(t > max){
                max = t;
            }
            if(u > max){
                max = u;
            }
            if(v > max){
                max = v;
            }
            if(w > max){
                max = w;
            }
            if(x > max){
                max = x;
            }
            if(y > max){
                max = y;
            }
            if(z > max){
                max = z;
            }
            if(_ > max){
                max = _;
            }
        }

        return max;
    }
    public double getMinCount(){
        if(min == 99999.9){
            if(a < min){
                min = a;
            }
            if(b < min){
                min = b;
            }
            if(c < min){
                min = c;
            }
            if(d < min){
                min = d;
            }
            if(e < min){
                min = e;
            }
            if(f < min){
                min = f;
            }
            if(g < min){
                min = g;
            }
            if(h < min){
                min = h;
            }
            if(i < min){
                min = i;
            }
            if(j < min){
                min = j;
            }
            if(k < min){
                min = k;
            }
            if(l < min){
                min = l;
            }
            if(m < min){
                min = m;
            }
            if(n < min){
                min = n;
            }
            if(o < min){
                min = o;
            }
            if(p < min){
                min = p;
            }
            if(q < min){
                min = q;
            }
            if(r < min){
                min = r;
            }
            if(s < min){
                min = s;
            }
            if(t < min){
                min = t;
            }
            if(u < min){
                min = u;
            }
            if(v < min){
                min = v;
            }
            if(w < min){
                min = w;
            }
            if(x < min){
                min = x;
            }
            if(y < min){
                min = y;
            }
            if(z < min){
                min = z;
            }
            if(_ < min){
                min = _;
            }
        }

        return min;
    }

    public double getRange(){
            range = this.getMaxCount() - this.getMinCount();
        return range;
    }

    public double getTotaleCount(){
        return totaleCount;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public double getE() {
        return e;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public double getI() {
        return i;
    }

    public double getJ() {
        return j;
    }

    public double getK() {
        return k;
    }

    public double getL() {
        return l;
    }

    public double getM() {
        return m;
    }

    public double getN() {
        return n;
    }

    public double getO() {
        return o;
    }

    public double getP() {
        return p;
    }

    public double getQ() {
        return q;
    }

    public double getR() {
        return r;
    }

    public double getS() {
        return s;
    }

    public double getT() {
        return t;
    }

    public double getU() {
        return u;
    }

    public double getV() {
        return v;
    }

    public double getW() {
        return w;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double get_() {
        return _;
    }

    public double getASp() {
        return aSP;
    }

    public double getBSp() {
        return bSP;
    }

    public double getCSp() {
        return cSP;
    }

    public double getDSp() {
        return dSP;
    }

    public double getESp() {
        return eSP;
    }

    public double getFSp() {
        return fSP;
    }

    public double getGSp() {
        return gSP;
    }

    public double getHSp() {
        return hSP;
    }

    public double getISp() {
        return iSP;
    }

    public double getJSp() {
        return jSP;
    }

    public double getKSp() {
        return kSP;
    }

    public double getLSp() {
        return lSP;
    }

    public double getMSp() {
        return mSP;
    }

    public double getNSp() {
        return nSP;
    }

    public double getOSp() {
        return oSP;
    }

    public double getPSp() {
        return pSP;
    }

    public double getQSp() {
        return qSP;
    }

    public double getRSp() {
        return rSP;
    }

    public double getSSp() {
        return sSP;
    }

    public double getTSp() {
        return tSP;
    }

    public double getUSp() {
        return uSP;
    }

    public double getVSp() {
        return vSP;
    }

    public double getWSp() {
        return wSP;
    }

    public double getXSp() {
        return xSP;
    }

    public double getYSp() {
        return ySP;
    }

    public double getZSp() {
        return zSP;
    }

    public double get_Sp() {
        return _SP;
    }

    public double getAcidic(){
        double score = 0.0;
        score = (this.getE() + this.getD())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }

    public double getAcylic(){
        double score = 0.0;
        score = (this.getA() + this.getR()+ this.getN() + this.getD() + this.getE() + this.getC() + this.getQ() + this.getG() + this.getI() + this.getL() + this.getK() + this.getM() + this.getS() + this.getT() + this.getV())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getAliphatic(){
        double score = 0.0;
        score = (this.getA() + this.getG() + this.getI() + this.getL() + this.getV())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }

    public double getAromatic(){
        double score = 0.0;
        score = (this.getH() + this.getF()+ this.getW() + this.getY())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getBasic(){
        double score = 0.0;
        score = (this.getK() + this.getR()+ this.getH())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getBuried(){
        double score = 0.0;
        score = (this.getA() + this.getC()+ this.getI() + this.getL() + this.getM() + this.getF() + this.getW() + this.getV())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getCharged(){
        double score = 0.0;
        score = (this.getK() + this.getR()+ this.getH() + this.getD() + this.getE())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getCyclic(){
        double score = 0.0;
        score = (this.getH() + this.getF()+ this.getP() + this.getW() + this.getY())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getHydrophobic(){
        double score = 0.0;
        score = (this.getA() + this.getG()+ this.getI() + this.getL() + this.getM() + this.getF() + this.getP() + this.getW() + this.getY() + this.getV())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getLarge(){
        double score = 0.0;
        score = (this.getR() + this.getE()+ this.getQ() + this.getH() + this.getI() + this.getL() + this.getK() + this.getM() + this.getF() + this.getW() + this.getY())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getMedium(){
        double score = 0.0;
        score = (this.getN() + this.getD()+ this.getC() + this.getP() + this.getT() + this.getV())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getNegative(){
        double score = 0.0;
        score = (this.getD() + this.getE())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getNeutral(){
        double score = 0.0;
        score = (this.getA() + this.getN() + this.getC() + this.getQ() + this.getG() +  this.getH() + this.getI() + this.getL() + this.getM() + this.getF() + this.getP() + this.getS() + this.getT() + this.getW() + this.getY() +  this.getV())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getPolar(){
        double score = 0.0;
        score = (this.getR() + this.getN() + this.getC() + this.getD() + this.getE() +  this.getH() + this.getQ()+ this.getK() + this.getS() + this.getT())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getPositive(){
        double score = 0.0;
        score = (this.getR() + this.getH() + this.getK())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getSmall(){
        double score = 0.0;
        score = (this.getA() + this.getG() + this.getS())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }
    public double getSurface(){
        double score = 0.0;
        score = (this.getR() + this.getN() + this.getD() + this.getQ() + this.getE() +  this.getH() + this.getG() + this.getK() + this.getP() + this.getS() + this.getT() + this.getY())/this.getTotaleCount();
        return Math.round(score*1000)/10.0;
    }


    //Swissprot getters
    public double getAcidicSp(){
        double score = 0.0;
        score = this.getESp() + this.getDSp();
        return Math.round(score*100)/100.0 ;
    }

    public double getAcylicSp(){
        double score = 0.0;
        score = this.getASp() + this.getRSp()+ this.getNSp() + this.getDSp() + this.getESp() + this.getCSp() + this.getQSp() + this.getGSp() + this.getISp() + this.getLSp() + this.getKSp() + this.getMSp() + this.getSSp() + this.getTSp() + this.getVSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getAliphaticSp(){
        double score = 0.0;
        score = this.getASp() + this.getGSp() + this.getISp() + this.getLSp() + this.getVSp();
        return Math.round(score*100)/100.0 ;
    }

    public double getAromaticSp(){
        double score = 0.0;
        score = this.getHSp() + this.getFSp()+ this.getWSp() + this.getYSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getBasicSp(){
        double score = 0.0;
        score = this.getKSp() + this.getRSp()+ this.getHSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getBuriedSp(){
        double score = 0.0;
        score = this.getASp() + this.getCSp()+ this.getISp() + this.getLSp() + this.getMSp() + this.getFSp() + this.getWSp() + this.getVSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getChargedSp(){
        double score = 0.0;
        score = this.getKSp() + this.getRSp()+ this.getHSp() + this.getDSp() + this.getESp();
        return Math.round(score*100)/100.0 ;
    }
    public double getCyclicSp(){
        double score = 0.0;
        score = this.getHSp() + this.getFSp()+ this.getPSp() + this.getWSp() + this.getYSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getHydrophobicSp(){
        double score = 0.0;
        score = this.getASp() + this.getGSp()+ this.getISp() + this.getLSp() + this.getMSp() + this.getFSp() + this.getPSp() + this.getWSp() + this.getYSp() + this.getVSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getLargeSp(){
        double score = 0.0;
        score = this.getRSp() + this.getESp()+ this.getQSp() + this.getHSp() + this.getISp() + this.getLSp() + this.getKSp() + this.getMSp() + this.getFSp() + this.getWSp() + this.getYSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getMediumSp(){
        double score = 0.0;
        score = this.getNSp() + this.getDSp()+ this.getCSp() + this.getPSp() + this.getTSp() + this.getVSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getNegativeSp(){
        double score = 0.0;
        score = this.getDSp() + this.getESp();
        return Math.round(score*100)/100.0 ;
    }
    public double getNeutralSp(){
        double score = 0.0;
        score = this.getASp() + this.getNSp() + this.getCSp() + this.getQSp() + this.getGSp() +  this.getHSp() + this.getISp() + this.getLSp() + this.getMSp() + this.getFSp() + this.getPSp() + this.getSSp() + this.getTSp() + this.getWSp() + this.getYSp() +  this.getVSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getPolarSp(){
        double score = 0.0;
        score = this.getRSp() + this.getNSp() + this.getCSp() + this.getDSp() + this.getESp() +  this.getHSp() + this.getQSp()+ this.getKSp() + this.getSSp() + this.getTSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getPositiveSp(){
        double score = 0.0;
        score = this.getRSp() + this.getHSp() + this.getKSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getSmallSp(){
        double score = 0.0;
        score = this.getASp() + this.getGSp() + this.getSSp();
        return Math.round(score*100)/100.0 ;
    }
    public double getSurfaceSp(){
        double score = 0.0;
        score = this.getRSp() + this.getNSp() + this.getDSp() + this.getQSp() + this.getESp() +  this.getHSp() + this.getGSp() + this.getKSp() + this.getPSp() + this.getSSp() + this.getTSp() + this.getYSp();
        return Math.round(score*100)/100.0 ;
    }

    public String getRandomAa(){
        String aa = "";
        double randomInt = Math.floor(Math.random()*10000);
        if(814.0 >randomInt){
            aa = "A";
        }
        if(955.0 >randomInt && randomInt > 813.0){
            aa = "C";
        }
        if(1496.0 >randomInt && randomInt > 954.0){
            aa = "D";
        }
        if(2170.0 >randomInt && randomInt > 1495.0){
            aa = "E";
        }
        if(2558.0 >randomInt && randomInt > 2169.0){
            aa = "F";
        }
        if(3262.0 >randomInt && randomInt > 2557.0){
            aa = "G";
        }
        if(3490.0 >randomInt && randomInt > 3261.0){
            aa = "H";
        }
        if(4082.0 >randomInt && randomInt > 3489.0){
            aa = "I";
        }
        if(4670.0 >randomInt && randomInt > 4081.0){
            aa = "K";
        }
        if(5646.0 >randomInt && randomInt > 4669.0){
            aa = "L";
        }
        if(5887.0 >randomInt && randomInt > 5645.0){
            aa = "M";
        }
        if(6292.0 >randomInt && randomInt > 5886.0){
            aa = "N";
        }
        if(6769.0 >randomInt && randomInt > 6291.0){
            aa = "P";
        }
        if(7164.0 >randomInt && randomInt > 6768.0){
            aa = "Q";
        }
        if(7714.0 >randomInt && randomInt > 7163.0){
            aa = "R";
        }
        if(8380.0 >randomInt && randomInt > 7713.0){
            aa = "S";
        }
        if(8916.0 >randomInt && randomInt > 8379.0){
            aa = "T";
        }
        if(9599.0 >randomInt && randomInt > 8915.0){
            aa = "V";
        }
        if(9708.0 >randomInt && randomInt > 9598.0){
            aa = "W";
        }
        if(10001.0 >randomInt && randomInt > 9707.0){
            aa = "Y";
        }
        return aa;
    }




    public static void main(String[] args){
        SequenceCounter counter = new SequenceCounter();
        for(int i = 0; i< 30; i ++){
            String peptide = "";
            for(int j = 0; j< 31; j ++){
                peptide= peptide + counter.getRandomAa();
            }
            System.out.println(peptide);
        }
    }

}