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
