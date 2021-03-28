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
 *
 * @author aditeya
 */
public class NMMClientThread extends Thread {

    private final Socket socket;
    private MCoinType player;

    public NMMClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream poos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream pois = new ObjectInputStream(socket.getInputStream());
            Scanner input = new Scanner(System.in);
            
            NMMboard p = (NMMboard) pois.readObject();
            player = p.getTurn();

            while (true) {
                NMMboard board = (NMMboard) pois.readObject();
                NMMLogic.cmdPrint(board.getNmmBoard(), PrintType.VALUE); 

                if(board.getTurn() == player) {
                    System.out.print("Enter Move: ");
                    poos.writeObject(new NMMmove(input.nextLine()));
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
