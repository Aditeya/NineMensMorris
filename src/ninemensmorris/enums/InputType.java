/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
