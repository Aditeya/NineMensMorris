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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aditeya
 */
public class Server extends Thread {

    final public static int MAX_ROOMS = 12;         // 12 rooms
    final public static int MAX_CLIENTS = 24;       // 2 clients per room
    final public static int ROOMWATCH_PERIOD = 10;  // Time taken to run RoomWatch Thread
    final public static int PORT_NUMBER = 9999;     // Port number for the server

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
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate( new RoomWatch(), 5, ROOMWATCH_PERIOD, TimeUnit.SECONDS);
        
        while (!this.isInterrupted()) {
            try {
                Socket client = this.serverSocket.accept();
                assignClientToThread(client);
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
                    NCommand command = (NCommand) pois.readObject();
                    processCommand(command);
                } catch (ClassNotFoundException | IOException ex) {
                }
            }

        }

        public void processCommand(NCommand command) {
            try {
                switch (command.getCommand()) {
                    case LIST_ROOMS:
                        listRooms();
                        break;
                    case CHOOSE_ROOM:
                        chooseRoom(command.getRoom());
                        break;
                    default:
                        System.out.println("Error");
                }
            } catch (IOException ex) {
            }
        }

        public void listRooms() throws IOException {
            NCommand reply = new NCommand();
            reply.setRooms(rooms.getRooms());

            poos.writeObject(reply);
        }

        public void chooseRoom(int room) throws IOException {
            NCommand reply = new NCommand();

            if (rooms.chooseRoom(ID, room)) {
                // room chosen tell client
                reply.setCommand(NetworkCommand.ROOM_ACQ);
            } else {
                // room taken tell client
                reply.setCommand(NetworkCommand.ROOM_FULL);
                reply.setRooms(rooms.getRooms());
            }

            poos.writeObject(reply);
        }

        public void closeConnection() {
            try {
                this.client.close();
            } catch (IOException ex) {
            }
        }

        public Socket getClient() {
            return client;
        }
    }

    //RoomWatch Section
    protected class RoomWatch extends Thread {

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                int[][] currentRooms = rooms.getRooms();

                for (int i = 0; i < currentRooms.length; i++) {
                    boolean roomFull = true;

                    for (int j = 0; j < currentRooms[i].length; j++) {
                        if (currentRooms[i][j] == 0) {
                            roomFull = false;
                        }
                    }

                    if (roomFull) {
                        int ID1 = currentRooms[i][0];
                        int ID2 = currentRooms[i][1];
                        
                        rooms.clearRoom(i);
                        
                        Socket p1 = clients[ID1].getClient();
                        Socket p2 = clients[ID2].getClient();
                        
                        clients[ID1].interrupt();
                        clients[ID2].interrupt();
                        clients[ID1] = null;
                        clients[ID2] = null;

                        new Thread(new NMMServiceThread(p1, p2)).start();
                    }
                }
            }
        }
    }
}
