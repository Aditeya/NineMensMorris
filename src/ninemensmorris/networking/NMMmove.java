/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.Serializable;

/**
 * NMMmove is a network wrapper class used to send the move from a player.
 *
 * @author aditeya
 */
public class NMMmove implements Serializable{
    private final String move;

    /**
     * Provide the move as a string. 
     * Can accept lowercase inputs.
     * 
     * @param move  A String with the pattern [A-H][1-3]
     */
    public NMMmove(String move) {
        this.move = move.toUpperCase();
    }

    public String getMove() {
        return move;
    }
}
