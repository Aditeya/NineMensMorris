/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris;

/**
 * The Coin Object for the NMM Board
 * Holds Valid Moves and Possible Mill Combinations
 * @author eltojaro
 */
public class NMMCoin {
    
    private MCoinType coinType;     //Holds Where coin is empty black or white
    private String coinSlot;        //Holds the coin location(slot) on board
    public NMMCoin[] vldMvs;        //Holds valid moves (Maybe make this protected String[]?)
    public NMMCoin[][] millCombo;   //Holds possible mill combinations (Maybe make this protected String[][]?)
    
    
    /**
     * Basic constructor for b00bs lol, hahahahahaha, no srsly don't use this stuff will break.
     */
    NMMCoin()
    {
        this.coinType = MCoinType.EMPTY;
        this.coinSlot = null;
        this.vldMvs = null;
        this.millCombo = null;        
    }

    /**
     *constructor lets you initialize everything sans CoinType which is set to emoty
     * @param coinSlot
     * @param vldMvs
     * @param millCombo
     */
    public NMMCoin(String coinSlot, NMMCoin[] vldMvs, NMMCoin[][] millCombo) {
        this.coinType = MCoinType.EMPTY;
        this.coinSlot = coinSlot;
        this.vldMvs = vldMvs;
        this.millCombo = millCombo;
    }
    

    /**
     * constructor for when you want to initialize everything for some reason
     * @param coinType
     * @param coinSlot
     * @param vldMvs
     * @param millCombo
     */
    public NMMCoin(MCoinType coinType, String coinSlot, NMMCoin[] vldMvs, NMMCoin[][] millCombo) {
        this.coinType = coinType;
        this.coinSlot = coinSlot;
        this.vldMvs = vldMvs;
        this.millCombo = millCombo;
    }
    
    
}
