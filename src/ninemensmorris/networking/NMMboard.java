/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.Serializable;
import ninemensmorris.NMMCoin;

/**
 *
 * @author aditeya
 */
public class NMMboard implements Serializable{
    private final NMMCoin[][] nmmBoard;

    public NMMboard(NMMCoin[][] nmmBoard) {
        this.nmmBoard = nmmBoard;
    }

    public NMMCoin[][] getNmmBoard() {
        return nmmBoard;
    }
}
