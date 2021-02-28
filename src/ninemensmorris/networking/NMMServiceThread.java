/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        try {
            ObjectInputStream p1ois = new ObjectInputStream(player1.getInputStream());
            ObjectInputStream p2ois = new ObjectInputStream(player2.getInputStream());
            ObjectOutputStream p1oos = new ObjectOutputStream(player1.getOutputStream());
            ObjectOutputStream p2oos = new ObjectOutputStream(player2.getOutputStream());
            
            while (nmm.getMenLeft() > 0) {  //CHANGE TO WHILE MEN LEFT
                PlayerTurn turn = nmm.getNmmTurn();
                System.out.println(turn + " Player, Place a coin.");
                System.out.println("match regex [A-H]+[1-3]");
                
                
                NMMmove move;
                switch (turn) {
                    case WHITE: //Play white turn
                        move = (NMMmove) p1ois.readObject();
                        break;
                    case BLACK: //Play black turn
                        move = (NMMmove) p2ois.readObject();
                        break;
                    default:    //Error
                        move = new NMMmove("A1");
                        break;
                }
                
                String slot = move.getMove();
                NMMCoin coin = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot, null, null);

                try {
                    //Sets the coin
                    sendCoin.put(coin);
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NMMLogicDemo.class.getName()).log(Level.SEVERE, null, ex);
                }

                //prints the board
                nmm.cmdPrint(PrintType.VALUE);
            }
            
            System.out.println("Game Complete");
        } catch (IOException | ClassNotFoundException ex) {
        }
    }

}
