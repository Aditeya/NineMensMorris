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
    WHITE(MCoinType.WHITE),
    BLACK(MCoinType.BLACK);
    
    private final MCoinType coinType;

    //Constructor
    private PlayerTurn(MCoinType coinType) {this.coinType = coinType;}
      
    //MCoinType Converter
    public MCoinType toMCntyp() {return this.coinType;}
}
