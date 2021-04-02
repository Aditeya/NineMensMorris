/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aditeya
 */
public class Server extends Thread {

    final public static int MAX_CLIENTS = 24;   // 12 rooms
    final public static int MAX_ROOMS = 12;   // 12 rooms
    final public static int PORT_NUMBER = 9999;

    private ServerSocket serverSocket;
    private SubServer[] clients;
    private Rooms rooms;

    /**
     */
    public Server() {
        try {
            this.serverSocket = new ServerSocket(PORT_NUMBER);
            this.clients = new SubServer[MAX_CLIENTS];
            this.rooms = new Rooms(MAX_ROOMS);
        } catch (IOException ex) {

        }
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                Socket client = this.serverSocket.accept();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void assignClientToThread(Socket client) {
        for (int i = 0; i < MAX_CLIENTS; i++) {
            if (this.clients[i] == null) {
                this.clients[i] = new SubServer(i, client);
                this.clients[i].start();
                return;
            }
        }
    }

    // SubServer Section
    protected class SubServer extends Thread {

        private final int ID;
        private final Socket client;

        private ObjectOutputStream poos;
        private ObjectInputStream pois;

        public SubServer(int ID, Socket client) {
            this.ID = ID;
            this.client = client;

            try {
                poos = new ObjectOutputStream(client.getOutputStream());
                pois = new ObjectInputStream(client.getInputStream());
            } catch (IOException ex) {
            }
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                try {
                    NetworkCommand command = (NetworkCommand) pois.readObject();
                    processCommand(command);
                } catch (ClassNotFoundException | IOException ex) {
                }
            }

        }

        public void processCommand(NetworkCommand command) {
            switch (command) {
                case LIST_ROOMS:
                    break;
                case CHOOSE_ROOM:
                    break;
                default:
                    System.out.println("Error");
            }
        }
        
        public void listRooms() throws IOException {
            NCommand reply = new NCommand();
            reply.setRooms(rooms.getRooms());
            
            poos.writeObject(reply);
        }
        
        public void chooseRoom(int room) {
            if(rooms.chooseRoom(ID, room)) {
                // room chosen tell client
            } else {
                // room taken tell client
            }
        }

        public void closeConnection() {
            try {
                this.client.close();
            } catch (IOException ex) {
            }
        }
    }
}
