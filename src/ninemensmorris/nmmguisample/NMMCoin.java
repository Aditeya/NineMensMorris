/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nmmguisample;

/**
 * The Coin Object for the NMM Board Holds Valid Moves and Possible Mill
 * Combinations
 *
 * @author eltojaro
 */
public class NMMCoin {

    private CoinType coinType;     //Holds Where coin is empty black or white
    private String coinSlot;        //Holds the coin location(slot) on board
    private boolean milled;         //Holds whether the coin is milled or not
    public String[] vldMvs;        //Holds valid moves (Maybe make this protected String[]?)
    public String[][] millCombo;   //Holds possible mill combinations (Maybe make this protected String[][]?)

    // <editor-fold defaultstate="collapsed" desc="constructors">
    /**
     * Basic constructor for n00bs lol, hahahahahaha, no srsly don't use this
     * stuff will break.
     */
    NMMCoin() {
        this.coinType = CoinType.EMPTY;
        this.coinSlot = null;
        this.milled = false;
        this.vldMvs = null;
        this.millCombo = null;
    }

    /**
     * constructor lets you initialize only coin slot with the arrays set to
     * null
     *
     * @param coinSlot
     */
    public NMMCoin(String coinSlot) {
        this.coinType = CoinType.EMPTY;
        this.coinSlot = coinSlot;
        this.milled = false;
        this.vldMvs = null;
        this.millCombo = null;
    }

    /**
     * constructor lets you initialize everything sans CoinType which is set to
     * empty
     *
     * @param coinSlot
     * @param milled
     * @param vldMvs
     * @param millCombo
     */
    public NMMCoin(String coinSlot, boolean milled, String[] vldMvs, String[][] millCombo) {
        this.coinType = CoinType.EMPTY;
        this.coinSlot = coinSlot;
        this.milled = milled;
        this.vldMvs = vldMvs;
        this.millCombo = millCombo;
    }

    /**
     * constructor for when you want to initialize everything for some reason
     *
     * @param coinType
     * @param coinSlot
     * @param milled
     * @param vldMvs
     * @param millCombo
     */
    public NMMCoin(CoinType coinType, String coinSlot, boolean milled, String[] vldMvs, String[][] millCombo) {
        this.coinType = coinType;
        this.coinSlot = coinSlot;
        this.milled = milled;
        this.vldMvs = vldMvs;
        this.millCombo = millCombo;
    }

    NMMCoin(CoinType coinType, String coinSlot, boolean milled, String[] vldMvs) {

  this.coinType = coinType;
        this.milled = milled;
        this.vldMvs = vldMvs;

    }

    NMMCoin(String coinSlot, boolean milled, String[] vldMvs) {
        this.milled = milled;
        this.vldMvs = vldMvs;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getters-setters">
    /**
     * Sets the coin type to the one specified
     *
     * @param coinType The coin type to be set
     * @return false if same type. true otherwise
     */
    public boolean setCoin(CoinType coinType) {
        //checks if old type same as new type, returns false
        if (this.coinType == coinType) {
            return false;
        }

        //since type not same, sets type and returns true
        this.coinType = coinType;
        return true;
    }

    public void setCoinSlot(String coinSlot) {
        this.coinSlot = coinSlot;
    }

    @Override
    public String toString() {
       
        
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    /**
     * Gets the coin type of an object
     *
     * @return the coin type
     */
    public CoinType getCoin() {
        return this.coinType;  //lol di I even have to explain this
    }

    /**
     * Returns the coin type
     *
     * @return empty:0, white:1, black:2
     */
    public int getCoinInt() {
        return coinType.ordinal();
    }

    /**
     * Gets the coin slot
     *
     * @return the coin slot
     */
    public String getCoinSlot() {
        return coinSlot;
    }

    public CoinType getCoinType() {
        return coinType;
    }
    
    
    /**
     * 
     * @return whether the coin is milled
     */
    public boolean isMilled() {
        return milled;
    }

    /**
     * 
     * @param milled The status of mill to be set
     * @return false if new mill is same as old mill, true otherwise
     */
    public boolean setMilled(boolean milled) {
        //checks if old type same as new type, returns false
        if (this.milled == milled) {
            return false;
        }
        //if not sets and returns true
        this.milled = milled;
        return true;
    }

    // </editor-fold>
}