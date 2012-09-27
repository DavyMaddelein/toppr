package be.proteomics.pprIA.das.readers;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 20-mrt-2008
 * Time: 8:30:45
 * To change this template use File | Settings | File Templates.
 */
public class StartEndPosition {
    private int iStart;
    private int iEnd;

    public StartEndPosition( int aStart, int aEnd){
        this.iStart = aStart;
        this.iEnd = aEnd;

    }
    public int getStartPosition(){
        return this.iStart;
    }
    public int getEndPosition(){
        return this.iEnd;
    }
}

