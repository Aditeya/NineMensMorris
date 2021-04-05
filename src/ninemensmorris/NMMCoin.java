/* 
 * Copyright (C) 2021 elton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninemensmorris;

import ninemensmorris.enums.MCoinType;

/**
 * The Coin Object for the NMM Board Holds Valid Moves and Possible Mill
 * Combinations
 *
 * @author eltojaro
 */
public class NMMCoin {

    /** Holds Where coin is empty black or white */
    private MCoinType coinType;
    /** Holds the coin location(slot) on board */
    private String coinSlot;   
    /** Holds whether the coin is milled or not */
    private boolean milled;  
    /** Holds whether the coin is horizontally milled or not */
    private boolean millH;   
    /** Holds whether the coin is vertically milled or not */
    private boolean millV;          
    /** Holds valid moves (Maybe make this protected String[]?) */ 
    public String[] vldMvs;        
    /** Holds possible mill combinations (Maybe make this protected String[][]?) */
    public String[][] millCombo;   

    // <editor-fold defaultstate="collapsed" desc="constructors">
    /**
     * Basic constructor for n00bs lol, hahahahahaha, no srsly don't use this
     * stuff will break.
     */
    NMMCoin() {
        this.coinType = MCoinType.EMPTY;
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
        this.coinType = MCoinType.EMPTY;
        this.coinSlot = coinSlot;
        this.milled = false;
        this.vldMvs = null;
        this.millCombo = null;
    }

    /**
     * constructor lets you initialize everything sans CoinType which is set to
     * empty, horizonal and vertical mill, which are set to false
     *
     * @param coinSlot
     * @param milled
     * @param vldMvs
     * @param millCombo
     */
    public NMMCoin(String coinSlot, boolean milled, String[] vldMvs, String[][] millCombo) {
        this.coinType = MCoinType.EMPTY;
        this.coinSlot = coinSlot;
        this.milled = milled;
        this.millH = false;
        this.millV = false;
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
    public NMMCoin(MCoinType coinType, String coinSlot, boolean milled, String[] vldMvs, String[][] millCombo) {
        this.coinType = coinType;
        this.coinSlot = coinSlot;
        this.milled = milled;
        this.vldMvs = vldMvs;
        this.millCombo = millCombo;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getters-setters">
    /**
     * Sets the coin type to the one specified
     *
     * @param coinType The coin type to be set
     * @return false if same type. true otherwise
     */
    public boolean setCoin(MCoinType coinType) {
        //checks if old type same as new type, returns false
        if (this.coinType == coinType) {
            return false;
        }

        //since type not same, sets type and returns true
        this.coinType = coinType;
        return true;
    }

    /**
     * Gets the coin type of an object
     *
     * @return the coin type
     */
    public MCoinType getCoin() {
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
    
    /**
     * Returns whether the coin is milled
     * @return mill state of coin
     */
    public boolean isMilled() {
        return milled;
    }

    /**
     * Sets the mill state of the coin
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
    
    /**
     * Returns whether the coin is milled horizontally 
     * @return whether the coin is milled horizontally 
     */
    public boolean isMillH() {
    return millH;
    }

    /**
     * Sets the horizontal mill state of the coin
     * @param millH The status of horizontal mill to be set
     * @return false if new mill is same as old mill, true otherwise
     */
    public boolean setMillH(boolean millH) {
        //checks if old type same as new type, returns false
        if (this.millH == millH) {
            return false;
        }
        //if not sets and returns true
        this.millH = millH;
        return true;
    }

    /**
     * Returns whether the coin is milled horizontally 
     * @return whether the coin is milled horizontally 
     */
    public boolean isMillV() {
    return millV;
    }

    /**
     * Sets the horizontal mill state of the coin
     * @param millV The status of horizontal mill to be set
     * @return false if new mill is same as old mill, true otherwise
     */
    public boolean setMillV(boolean millV) {
        //checks if old type same as new type, returns false
        if (this.millV == millV) {
            return false;
        }
        //if not sets and returns true
        this.millV = millV;
        return true;
    }   

    // </editor-fold>
    
    /**
     * Checks millH and millV and determines if the coin is milled, sets that value
     * @return the new nill state of the coin
     */
    public boolean updateMill()
    { 
        //a boolean variable to keep millState
        boolean millState;
        
        //checks H and V mill, if either true, then the coin is milled
        if(this.isMillH() || this.isMillV())
            millState = true;
        else
            millState = false;
        
        //Assigns and Returns mill state.
        this.setMilled(millState);
        return millState;
    }

}
