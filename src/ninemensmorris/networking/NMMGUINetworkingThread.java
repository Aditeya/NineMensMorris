/*
 * Copyright (C) 2021 aditeya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninemensmorris.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.NMMLogic;
import ninemensmorris.enums.InputType;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PrintType;
import ninemensmorris.networking.demo.NMMClientDemo;
import static ninemensmorris.networking.demo.NMMClientDemo.printRooms;

/**
 *
 * @author aditeya
 */
public class NMMGUINetworkingThread extends Thread {

    public static final int PORT = 9999;
    public static final String ADDRESS = "";

    private final ReadWriteLock lock;
    private final Lock writeLock;
    private final Lock readLock;

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private boolean roomChosen;
    private final LinkedBlockingQueue input;
    private final LinkedBlockingQueue output;
    private MCoinType player;

    public NMMGUINetworkingThread(LinkedBlockingQueue input, LinkedBlockingQueue output) {
        this.lock = new ReentrantReadWriteLock();
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();

        this.roomChosen = false;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            if (ADDRESS.isEmpty()) {
                this.socket = new Socket(InetAddress.getLocalHost(), PORT);
            } else {
                this.socket = new Socket(ADDRESS, PORT);
            }

            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException ex) {
            Logger.getLogger(NMMGUINetworkingThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NMMGUINetworkingThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!this.isInterrupted()) {
            if (socket.isClosed()) {
                this.interrupt();
            }

            if (roomChosen) {
                try {
                    NMMboard p = (NMMboard) ois.readObject();
                    player = p.getTurn();
                    output.add(player);
                    
                    // Start the game
                    while (true) {
                        // Receive board and print it out
                        NMMboard board = (NMMboard) ois.readObject();
                        output.add(board);

                        // Notify if input is valid
                        if (board.isWrongMove()) {
                            System.out.println("Invalid move, Try again");
                        }

                        MCoinType turn = board.getTurn();
                        // Take input and send, if it is players turn
                        boolean move = false;
                        if (turn == player) {

                            switch (board.getiType()) {
                                case NONE:
                                    break;
                                case PLACE:
                                    //output.add(InputType.PLACE);
                                    //printPlayerTurn(turn, 1);
                                    move = true;
                                    break;
                                case REMOVE:
                                    //output.add(InputType.REMOVE);
                                    //printPlayerTurn(turn, 2);
                                    move = true;
                                    break;
                                case MOVE:
                                    //output.add(InputType.MOVE);
                                    //printPlayerTurn(turn, 3);
                                    String[] slots = new String[2];
                                    slots[0] = (String) input.take();

                                    //System.out.println(turn + " Player, Move coin " + slots[0] + " to? match regex [A-H]+[1-3]");
                                    slots[1] = (String) input.take();

                                    sendBoard(new NMMmove(slots[0]));
                                    sendBoard(new NMMmove(slots[1]));

                                    break;
                                default:
                            }

                            if (move) {
                                System.out.print("Enter Move: ");
                                sendBoard(new NMMmove((String) input.take()));
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                    Logger.getLogger(NMMGUINetworkingThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Requests the server for the room list and prints it.
     *
     * @return double array of rooms
     */
    public int[][] list() {
        if (roomChosen) {
            return null;
        }

        NCommand reply = null;
        // Create command
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.LIST_ROOMS);

        try {
            writeLock.lock();
            // Send command
            oos.reset();
            oos.writeObject(command);
            reply = (NCommand) ois.readObject();

            // this is temporary. pls remove after testing. Print room list from reply
            NMMClientDemo.printRooms(reply.getRooms());

            return reply.getRooms();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writeLock.unlock();
        }

        return reply == null ? null : reply.getRooms();
    }

    /**
     * Requests the server for the room and deals with the outcome. If the
     * request was a success true is returned. else the room list from the reply
     * is printed and a false returned.
     *
     * @param room room to be chosen
     * @return returns if operation was a success.
     */
    public boolean choose(int room) {
        boolean success = true;
        if (roomChosen) {
            return success;
        }

        // Create the command request for the room
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.CHOOSE_ROOM);

        command.setRoom(room);

        try {
            writeLock.lock();
            // Send the request and recieve reply
            oos.reset();
            oos.writeObject(command);
            NCommand reply = (NCommand) ois.readObject();

            // Process reply, either reply with confirm if success or print room and exit
            switch (reply.getCommand()) {
                case ROOM_ACQ:
                    reply.setCommand(NetworkCommand.CONFIRM);
                    oos.reset();
                    oos.writeObject(reply);
                    roomChosen = true;
                    break;
                case ROOM_FULL:
                    System.out.println("Rooms Full. Choose Another:");
                    printRooms(reply.getRooms());
                default:
                    success = false;
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writeLock.unlock();
        }

        return success;
    }

    private void sendBoard(Object obj) throws IOException {
        //resets output streams to fix empty board being received by client
        oos.reset();
        //sends board to players
        oos.writeObject(obj);
    }
}
