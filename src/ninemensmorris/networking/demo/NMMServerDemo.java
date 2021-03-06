/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking.demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.networking.NMMServiceThread;

/**
 *
 * @author aditeya
 */
public class NMMServerDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerSocket ssocket = new ServerSocket(9999);
            System.out.println("Server Listening");

            // TODO: Implement Thread Pool
            while (true) {
                Socket p1 = ssocket.accept();
                System.out.println("Player 1 Connected");
                Socket p2 = ssocket.accept();
                System.out.println("Player 2 Connected");
                
                new Thread(new NMMServiceThread(p1, p2)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(NMMServerDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
