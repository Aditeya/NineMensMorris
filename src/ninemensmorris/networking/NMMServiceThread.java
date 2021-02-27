/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.NMMCoin;
import ninemensmorris.NMMLogic;
import ninemensmorris.NMMLogicDemo;
import ninemensmorris.enums.PlayerTurn;
import ninemensmorris.enums.PrintType;

/**
 *
 * @author aditeya
 */
public class NMMServiceThread extends Thread {

    private final Socket player1;
    private final Socket player2;
    private final NMMLogic nmm;
    private final LinkedBlockingQueue sendCoin;

    public NMMServiceThread(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.sendCoin = new LinkedBlockingQueue(2);
        this.nmm = new NMMLogic();
        this.nmm.nmmSetup(sendCoin, true);
    }

    @Override
    public void run() {
        while (nmm.getMenLeft() > 0) {  //CHANGE TO WHILE MEN LEFT
            PlayerTurn turn = nmm.getNmmTurn();
            System.out.println(turn + " Player, Place a coin.");
            System.out.println("match regex [A-H]+[1-3]");
            String slot = "";
            
            switch (turn) {
                case WHITE: //Play white turn
                    break;
                case BLACK: //Play black turn
                    break;
                default:    //Error
                    break;
            }

            NMMCoin coin = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot, null, null);

            try {
                //Sets the coin
                sendCoin.put(coin);
            } catch (InterruptedException ex) {
                Logger.getLogger(NMMLogicDemo.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(NMMLogicDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
            //prints the board
            nmm.cmdPrint(PrintType.VALUE);

        }
        System.out.println("Game Complete");
    }

}
