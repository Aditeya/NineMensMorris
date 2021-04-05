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
 * Shows what kind of input an object of NMMLogic is currently awaiting 
 * @author eltojaro
 */
public enum InputType {
    /**
     * NONE means the game is not awaiting an input from the associated 
     * blocking queue.
     */
    NONE,
    
    /**
     * PLACE means the game is expecting exactly one coin as input from the 
     * associated blocking queue, This coin will then be placed on the nmm board,
     * The coin given only needs to have coinSlot and coinType initialized.
     * 
     * If the coin's given coinSlot corresponds to a coinSlot on the board that
     * is not empty, the queue will be cleared and new input will be needed. 
     * 
     * Please check the coins before submitting them to the queue.
     */
    PLACE,
    
    /**
     * REMOVE means the game is expecting exactly one coin as input from the 
     * associated blocking queue, This coin will then be removed from the nmm board,
     * The coin given only needs to have coinSlot initialized.
     * 
     * If the coin given is not the opposite of the coinType in accordance with
     * the current player turn, the queue will be cleared and new input will be 
     * needed.
     * 
     * Please check the coins before submitting them to the queue.
     */
    REMOVE,
    
    /**
     * MOVE means the game is expecting exactly two coins as input from the 
     * associated blocking queue, The first coin given will be removed from the nmm
     * board, and the second coin given will be placed on the nmm board as coin
     * type corresponding to current player turn,
     * 
     * The coin only needs to have the coinSlot initialized.
     * 
     * If the first coin given does not correspond to current player turn, the 
     * queue will be cleared and new input will be needed, 
     * 
     * if the second coin given is not a valid move in accordance with the first
     * coin, the queue will be cleared and new input will be needed, 
     * 
     * Please check the coins before submitting them to the queue
     */
    MOVE
    
}
