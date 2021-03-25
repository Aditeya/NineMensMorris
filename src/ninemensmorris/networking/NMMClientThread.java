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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.NMMLogic;
import ninemensmorris.enums.PrintType;

/**
 *
 * @author aditeya
 */
public class NMMClientThread extends Thread {

    private Socket socket;

    public NMMClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream poos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream pois = new ObjectInputStream(socket.getInputStream());
            Scanner input = new Scanner(System.in);

            while (socket.isConnected()) {
                NMMmove move = null;
                
                try {
                    NMMboard board = (NMMboard) pois.readObject();
                    NMMLogic.cmdPrint(board.getNmmBoard(), PrintType.VALUE);
                    System.out.println(board.getNmmBoard());
                    
                    System.out.print("Enter Move: ");
                    move = new NMMmove(input.nextLine());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(NMMClientThread.class.getName()).log(Level.SEVERE, null, ex);
                }

                poos.writeObject(move);
            }

            socket.close();
        } catch (IOException ex) {
        }
    }

}
