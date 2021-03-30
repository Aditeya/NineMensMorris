/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.Serializable;

/**
 *
 * @author aditeya
 */
public class NMMmove implements Serializable{
    private final String move;

    public NMMmove(String move) {
        this.move = move.toUpperCase();
    }

    public String getMove() {
        return move;
    }
}
