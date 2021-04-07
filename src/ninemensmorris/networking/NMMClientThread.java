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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.NMMLogic;
import ninemensmorris.enums.InputType;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PlayerTurn;
import ninemensmorris.enums.PrintType;

/**
 * NMMClientThread is used by the client to interact with a NMMServiceThread
 * over the network.
 *
 * @author aditeya
 */
public class NMMClientThread extends Thread {

    private final Socket socket;
    private MCoinType player;

    /**
     * Provide a socket and the thread will handle the rest.
     *
     * @param socket Provide the client socket
     */
    public NMMClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Set up Socket IO Streams
            ObjectOutputStream poos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream pois = new ObjectInputStream(socket.getInputStream());
            Scanner input = new Scanner(System.in); // Scanner for input, might be changed in GUI section

            // Read the board and get the players assigned coin
            NMMboard p = (NMMboard) pois.readObject();
            player = p.getTurn();

            // Start the game
            while (true) {
                // Receive board and print it out
                NMMboard board = (NMMboard) pois.readObject();
                NMMLogic.cmdPrint(board.getNmmBoard(), PrintType.VALUE);

                MCoinType turn = board.getTurn();
                // Take input and send, if it is players turn
                if (turn == player) {

                    switch (board.getiType()) {
                        case NONE:
                            break;
                        case PLACE:
                            printPlayerTurn(turn, 1);
                            break;
                        case REMOVE:
                            printPlayerTurn(turn, 2);
                            break;
                        case MOVE:
                            printPlayerTurn(turn, 3);
                            String slot = input.nextLine();
                            System.out.println(turn + " Player, Move coin " + slot + " to? match regex [A-H]+[1-3]");
                            break;
                        default:
                    }

                    // Notify if input is valid
                    if (board.isWrongMove()) {
                        System.out.println("Invalid move, Try again");
                    }

                    System.out.print("Enter Move: ");
                    poos.writeObject(new NMMmove(input.nextLine()));
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printPlayerTurn(MCoinType turn, int msg) {
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
}
