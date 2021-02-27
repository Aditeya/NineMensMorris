/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author aditeya
 */
public class NMMClient {
    private Socket socket;
    
    public NMMClient(String host, int port) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException ex) {
            System.out.println("Connection Failed: " + ex.getStackTrace());
        }
    }
    
    
}
