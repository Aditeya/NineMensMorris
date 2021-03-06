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

/**
 *
 * @author aditeya
 */
public class NMMClientThread extends Thread {

    private Socket socket;
    private final LinkedBlockingQueue sendCoin;

    public NMMClientThread(String host, int port) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException ex) {
            System.out.println("Connection Failed: " + ex.getStackTrace());
        }

        this.sendCoin = new LinkedBlockingQueue(2);
    }

    @Override
    public void run() {
        try {
            ObjectInputStream pois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream poos = new ObjectOutputStream(socket.getOutputStream());
            
            while(true) {
                NMMmove move = null;
                NMMboard board = null;
                try {
                    board = (NMMboard) pois.readObject();
                    move = new NMMmove( (String) sendCoin.take() );
                } catch (InterruptedException | ClassNotFoundException ex) {
                    Logger.getLogger(NMMClientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                poos.writeObject(move);
                
                break;
            }
            
            socket.close();
        } catch (IOException ex) {
        }
    }

    public LinkedBlockingQueue getSendCoin() {
        return sendCoin;
    }
}
