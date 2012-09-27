package be.proteomics.pprIA.general;


/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 29-jan-2008
 * Time: 10:25:48
 * To change this template use File | Settings | File Templates.
 */
public class JmolLoad {
    private String jmolLoad;
    private String jmolMenu;
    private int iPosition;
    private int iProteinInt;
    private int iSession;

    public JmolLoad(String jmolLoad, String jmolMenu, int lPosition, int lProteinInt, int lSession) {
        this.jmolLoad = jmolLoad;
        this.jmolMenu = jmolMenu;
        this.iPosition = lPosition;
        this.iProteinInt = lProteinInt;
        this.iSession = lSession;
    }

    public String getJmolLoad() {
        return jmolLoad;
    }

    public String getJmolMenu() {
        return jmolMenu;
    }

    public int getPosition(){
        return iPosition; 
    }

    public int getProteinInt() {
        return iProteinInt;
    }

    public int getSession() {
        return iSession;
    }
}
