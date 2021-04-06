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
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PrintType;

/**
 * NMMClientThread is used by the client to interact with a NMMServiceThread over the network.
 *
 * @author aditeya
 */
public class NMMClientThread extends Thread {

    private final Socket socket;
    private MCoinType player;

    /**
     * Provide a socket and the thread will handle the rest.
     * 
     * @param socket    Provide the client socket
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

                // Take input and send, if it is players turn
                if (board.getTurn() == player) {
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

}
