/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.Serializable;
import ninemensmorris.NMMCoin;
import ninemensmorris.enums.InputType;
import ninemensmorris.enums.MCoinType;

/**
 * NMMboard is used to send board and other game related data over the network.
 *
 * @author aditeya
 */
public class NMMboard implements Serializable {

    private final NMMCoin[][] nmmBoard;
    private final MCoinType turn;
    private final InputType iType;
    private final boolean wrongMove;

    /**
     * Set these parameters before sending the board.
     * 
     * @param nmmBoard  Double array of NMMCoin for board state
     * @param turn      Current turn of player of the game
     * @param iType     Current InputType
     * @param wrongMove To be set if a move is invalid
     */
    public NMMboard(NMMCoin[][] nmmBoard, MCoinType turn, InputType iType, boolean wrongMove) {
        this.nmmBoard = nmmBoard;
        this.turn = turn;
        this.iType = iType;
        this.wrongMove = wrongMove;
    }

    public NMMCoin[][] getNmmBoard() {
        return nmmBoard;
    }

    public MCoinType getTurn() {
        return turn;
    }

    public InputType getiType() {
        return iType;
    }

    public boolean isWrongMove() {
        return wrongMove;
    }
}
