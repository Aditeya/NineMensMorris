/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking.demo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.networking.NMMClientThread;
import ninemensmorris.networking.NMMmove;

/**
 *
 * @author aditeya
 */
public class NMMClientDemo {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            NMMClientThread game = new NMMClientThread(socket);
            
            Thread thread = new Thread(game);
            thread.start();
            
            LinkedBlockingQueue sendCoin = game.getSendCoin();
            Scanner input = new Scanner(System.in);
            String move;
            
            while(true) {
                Thread.sleep(1000);
                System.out.print("Enter Move: ");
                move = input.nextLine();
                sendCoin.put(move);
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
