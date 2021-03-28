/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.Serializable;
import ninemensmorris.NMMCoin;
import ninemensmorris.enums.MCoinType;

/**
 *
 * @author aditeya
 */
public class NMMboard implements Serializable {

    private final NMMCoin[][] nmmBoard;
    private final MCoinType turn;

    public NMMboard(NMMCoin[][] nmmBoard, MCoinType turn) {
        this.nmmBoard = nmmBoard;
        this.turn = turn;
    }

    public NMMCoin[][] getNmmBoard() {
        return nmmBoard;
    }

    public MCoinType getTurn() {
        return turn;
    }
}
