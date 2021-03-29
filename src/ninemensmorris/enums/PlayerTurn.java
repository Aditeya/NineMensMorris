/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.enums;

/**
 * ENUM to hold player turn - Possibly just use MCoinType instead
 *
 * @author eltojaro
 */
public enum PlayerTurn {
    /** Indicates White Player's Turn */
    WHITE(MCoinType.WHITE),
    /** Indicates White Player's Turn */
    BLACK(MCoinType.BLACK);
    
    /** Holds the coinType corresponding to player turn */
    private final MCoinType coinType;

    /**
     * Basic and Only Constructor
     * @param coinType Coin type to be intialised with.
     */
    private PlayerTurn(MCoinType coinType) {this.coinType = coinType;}
      
    /**
     * Converts the Player Turn to corresponding coinType.
     * @return corresponding coinType.
     */
    public MCoinType toMCntyp() {return this.coinType;}
    
    /**
     * Negates the current player turn
     * @return the opposite player turn
     */
    public PlayerTurn getOpposeTurn()
    {
        switch(this)
        {
            case WHITE:
                return BLACK;
                
            case BLACK:
                return WHITE;
                
            default:
                return null; //error
        }
    }
}
