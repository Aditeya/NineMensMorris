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
import ninemensmorris.enums.InputType;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PlayerTurn;
import ninemensmorris.enums.PrintType;

/**
 * Service Thread which takes 2 sockets and provides them with a game. Receiving
 * (players) end should use NMMClientThread.
 *
 * @author aditeya
 */
public class NMMServiceThread extends Thread {

    private final Socket player1;
    private final Socket player2;
    private final NMMLogic nmm;
    private final LinkedBlockingQueue sendCoin;

    /**
     * Give two sockets where players intend to play.
     *
     * @param player1 Socket of player1
     * @param player2 Socket of player2
     */
    public NMMServiceThread(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.sendCoin = new LinkedBlockingQueue(2);
        this.nmm = new NMMLogic();
    }

    @Override
    public void run() {
        try {
            // Create Object IO Streams for both players
            ObjectInputStream p1ois = new ObjectInputStream(player1.getInputStream());
            ObjectOutputStream p1oos = new ObjectOutputStream(player1.getOutputStream());
            ObjectInputStream p2ois = new ObjectInputStream(player2.getInputStream());
            ObjectOutputStream p2oos = new ObjectOutputStream(player2.getOutputStream());

            // Thread Setup for the game
            Thread gameThread = new Thread(new Runnable() {

                private void printPlayerTurn(PlayerTurn turn, int msg) {
                    switch (msg) {
                        case 1:
                            System.out.println(turn + " Player, Place a coin.");
                            break;
                        case 2:
                            System.out.println(turn + " Player, Select an opposing coin to be removed.\nEnter 'X' to conceed coin removal");
                            break;
                        case 3:
                            System.out.println(turn + " Player, Select an coin to be moved");
                            break;
                        default:
                            System.out.println("Incorrect Usage. Check Docs.");
                            return;
                    }

                    System.out.println("match regex [A-H]+[1-3]");
                }

                private void sendBoard(NMMboard board) throws IOException {
                    //resets output streams to fix empty board being received by client
                    p1oos.reset();
                    p2oos.reset();
                    //sends board to players
                    p1oos.writeObject(board);
                    p2oos.writeObject(board);

                    //prints the board
                    nmm.cmdPrint(PrintType.VALUE);
                }

                private NMMmove receiveMove(PlayerTurn turn) throws IOException, ClassNotFoundException {
                    // Reading the correct move from the correct socket
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
                            //Consider throwing exception here
                            break;
                    }
                }

                @Override
                public void run() {
                    // Sends an empty board with the player information, i.e. p1 is WHITE and p2 is BLACK
                    try {
                        NMMboard turn = new NMMboard(null, MCoinType.WHITE, null, false);
                        p1oos.writeObject(turn);
                        turn = new NMMboard(null, MCoinType.BLACK, null, false);
                        p2oos.writeObject(turn);
                    } catch (IOException ex) {
                    }

                    //String slot = new String();
                    //NMMCoin coin = null;

                    PlayerTurn prevTurn = PlayerTurn.BLACK;
                    InputType prevType = InputType.NONE;
                    while (nmm.getWinner() == MCoinType.EMPTY) {
                        try {
                            PlayerTurn turn = nmm.getNmmTurn();
                            InputType input = nmm.getInput();

                            // Wrong move indicator
                            boolean wrongMove = prevTurn.equals(turn);

                            //Sends the board with turn and wrong move information
                            NMMboard board = new NMMboard(nmm.nmmBoard, turn.toMCntyp(), input,wrongMove);
                            sendBoard(board);

                            switch (input) {
                                case NONE:
                                    while (nmm.getInput() == InputType.NONE) {
                                        Thread.sleep(150);
                                    }
                                    break;

                                case PLACE:
                                    printPlayerTurn(turn, 1);
                                    break;

                                case REMOVE:
                                    printPlayerTurn(turn, 2);
                                    break;

                                case MOVE:
                                    printPlayerTurn(turn, 3);
                                    String slot = receiveMove(turn).getMove();

                                    System.out.println(turn + " Player, Move coin " + slot + " to? match regex [A-H]+[1-3]");
                                    
                                    String slot2 = receiveMove(turn).getMove();
                                    
                                    NMMCoin coin = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot, false, null, null);
                                    NMMCoin coin2 = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot2, false, null, null);

                                    //Sets the coin
                                    sendCoin.put(coin);
                                    sendCoin.put(coin2);
                                    break;

                                default:
                                    System.out.println("oh snep something broke");
                                    System.exit(-1);
                            }
                            
                            if(input == InputType.PLACE || input == InputType.REMOVE) {
                                String slot = receiveMove(turn).getMove();
                                NMMCoin coin = new NMMCoin(turn.toMCntyp(), slot, false, null, null);
                                sendCoin.put(coin);
                            }
                            
                            prevTurn = turn;
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(NMMLogicDemo.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException | ClassNotFoundException ex) {
                            Logger.getLogger(NMMServiceThread.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            });

            gameThread.start();
            this.nmm.nmmSetup(sendCoin, false);

            while (nmm.getWinner() == MCoinType.EMPTY) {
                nmm.nmmTurnHandle(sendCoin, true);
            }

            player1.close();
            player2.close();
            System.out.println("Game Complete");
        } catch (IOException ex) {
        }
    }

}
