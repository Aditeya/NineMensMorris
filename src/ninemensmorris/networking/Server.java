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
 * The Server thread accepts new incoming connections and passes them to the
 * SubServer thread. The RoomWatch thread watches for full rooms and sends the
 * sockets to a new NMMServiceThread.
 *
 * @author aditeya
 */
public class Server extends Thread {

    final public static int MAX_ROOMS = 12;         // 12 rooms
    final public static int MAX_CLIENTS = 24;       // 2 clients per room
    final public static int ROOMWATCH_PERIOD = 3;   // Time taken to run RoomWatch Thread
    final public static int PORT_NUMBER = 9999;     // Port number for the server

    private ServerSocket serverSocket;
    private SubServer[] clients;
    private Rooms rooms;
    private int clientUID;

    /**
     * Creates an instance of the server
     */
    public Server() {
        try {
            this.serverSocket = new ServerSocket(PORT_NUMBER);
            this.clients = new SubServer[MAX_CLIENTS];
            this.rooms = new Rooms(MAX_ROOMS);
            this.clientUID = 1;
        } catch (IOException ex) {

        }
    }

    @Override
    public void run() {
        // Creates and runs the RoomWatch Thread every second defined in ROOMWATCH_PERIOD
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new RoomWatch(), 0, ROOMWATCH_PERIOD, TimeUnit.SECONDS);

        // Starts listening for sockets and assigns them to a SubServer thread
        while (!this.isInterrupted()) {
            try {
                Socket client = this.serverSocket.accept();
                assignClientToThread(client);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Takes a Client socket, assigns an ID and and starts a SubServer thread
     * which is added to the clients array
     *
     * @param client Client socket to be assigned
     */
    public void assignClientToThread(Socket client) {
        // TODO: MAX_CLIENT handling
        if (this.clients[clientUID - 1] == null) {
            this.clients[clientUID - 1] = new SubServer(clientUID, client);
            this.clients[clientUID - 1].start();
            clientUID++;
        }
    }

    // SubServer Thread Class Section
    /**
     * The SubServer Class is used to deal with the client and room selection.
     */
    protected class SubServer extends Thread {

        private final int ID;
        private final Socket client;

        private ObjectOutputStream poos;
        private ObjectInputStream pois;

        /**
         * SubServer Constructor which takes in client ID and Socket
         *
         * @param ID client ID
         * @param client client Socket
         */
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
            // Accepts a NCommand Object, from the client, and processes that command.
            while (!this.isInterrupted()) {
                try {
                    NCommand command = (NCommand) pois.readObject();
                    processCommand(command);
                } catch (ClassNotFoundException | IOException ex) {
                }
            }

        }

        /**
         * Reads the command parameter of the NCommand instance and calls the
         * correct method.
         *
         * @param command NCommand Object to be processed
         */
        public void processCommand(NCommand command) {
            try {
                switch (command.getCommand()) {
                    case LIST_ROOMS:
                        listRooms();
                        break;
                    case CHOOSE_ROOM:
                        chooseRoom(command.getRoom());
                        break;
                    case CONFIRM:
                        this.interrupt();
                        break;
                    default:
                        System.out.println("Error");
                }
            } catch (IOException ex) {
            }
        }

        /**
         * Gets the room list array from the Rooms Object and sets it in a new
         * NCommand instance which is sent to Client.
         *
         * @throws java.io.IOException Object failed to send
         */
        public void listRooms() throws IOException {
            NCommand reply = new NCommand();
            reply.setRooms(rooms.getRooms());

            poos.reset();
            poos.writeObject(reply);
        }

        /**
         * Selects the room specified in the room parameter If the room is
         * chosen, a ROOM_ACQ command is sent If not, a ROOM_FULL command w/
         * room list is sent
         *
         * @param room Room number to be selected.
         * @throws java.io.IOException Object failed to send
         */
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

            poos.reset();
            poos.writeObject(reply);
        }

        /**
         * Closes the client socket connection.
         */
        public void closeConnection() {
            //try {
            System.out.println("no need");
            //} catch (IOException ex) {
            //    ex.printStackTrace();
            //}
        }

        public Socket getClient() {
            return client;
        }

        public ObjectInputStream getPois() {
            return pois;
        }

        public ObjectOutputStream getPoos() {
            return poos;
        }

    }

    //RoomWatch Thread Class Section
    /**
     * The RoomWatch Class used to watch for rooms that are full and start a new
     * game for them.
     */
    protected class RoomWatch extends Thread {

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                int[][] currentRooms = rooms.getRooms();

                // iterate through current rooms
                for (int i = 0; i < currentRooms.length; i++) {
                    boolean roomFull = true;

                    // check if any rooms are 0 which means its not full
                    for (int j = 0; j < currentRooms[i].length; j++) {
                        if (currentRooms[i][j] == 0) {
                            roomFull = false;
                            break;
                        }
                    }

                    /**
                     * If the room is full take the sockets and start a new
                     * thread and clear the client and room list. It also
                     * interrupts the SubServer thread for those clients.
                     */
                    if (roomFull) {
                        int ID1 = currentRooms[i][0];
                        int ID2 = currentRooms[i][1];

                        rooms.clearRoom(i);

                        SubServer p1 = clients[ID1 - 1];
                        SubServer p2 = clients[ID2 - 1];

                        clients[ID1 - 1] = null;
                        clients[ID2 - 1] = null;

                        Socket s1 = p1.getClient();
                        ObjectOutputStream p1oos = p1.getPoos();
                        ObjectInputStream p1ois = p1.getPois();

                        Socket s2 = p2.getClient();
                        ObjectOutputStream p2oos = p2.getPoos();
                        ObjectInputStream p2ois = p2.getPois();

                        System.out.println("ran service thread");
                        new Thread(new NMMServiceThread(s1, s2, p1oos, p1ois, p2oos, p2ois)).start();
                    }
                }
            }
        }
    }
}
