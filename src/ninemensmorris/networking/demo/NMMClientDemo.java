/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking.demo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.networking.NCommand;
import ninemensmorris.networking.NMMClientThread;
import ninemensmorris.networking.NetworkCommand;

/**
 * An example of how the client would function using the networking classes.
 * It would need to be adapted for the GUI.
 *
 * @author aditeya
 */
public class NMMClientDemo {

    private final static Scanner INPUT = new Scanner(System.in);

    /**
     * Main method to start the client.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Create the socket and its Object IO streams
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Client CLI interface
            boolean run = true;
            while (run) {
                System.out.print("Client> ");
                String command = INPUT.nextLine();

                switch (command) {
                    case "list":
                        list(ois, oos);
                        break;
                    case "choose":
                        run = !choose(ois, oos);
                        break;
                    default:
                        printHelp();
                }
            }

            // Create and start the game thread after room is chosen.
            NMMClientThread game = new NMMClientThread(socket);
            Thread thread = new Thread(game);
            thread.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Requests the server for the room list and prints it.
     * 
     * @param ois   Client ObjectInputStream
     * @param oos   Client ObjectOutputStream
     */
    private static void list(ObjectInputStream ois, ObjectOutputStream oos) {
        // Create command
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.LIST_ROOMS);

        try {
            // Send command
            oos.reset();
            oos.writeObject(command);
            NCommand reply = (NCommand) ois.readObject();

            // Print room list from reply
            printRooms(reply.getRooms());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Requests the server for the room and deals with the outcome.
     * If the request was a success true is returned.
     * else the room list from the reply is printed and a false returned.
     * 
     * @param ois   Client ObjectInputStream
     * @param oos   Client ObjectOutputStream
     * @return      returns if operation was a success.
     */
    private static boolean choose(ObjectInputStream ois, ObjectOutputStream oos) {
        boolean success = true;
        
        // Create the command request for the room
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.CHOOSE_ROOM);

        // Ask for the room number and set it in the request
        System.out.print("Enter Room Number: ");
        int room = Integer.parseInt(INPUT.nextLine());
        command.setRoom(room);

        try {
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
                    return success;
                case ROOM_FULL:
                    System.out.println("Rooms Full. Choose Another:");
                    printRooms(reply.getRooms());
                default:
                    success = false;
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return success;
    }

    //<editor-fold defaultstate="collapsed" desc="print methods">
    /**
     * Print help information.
     */
    private static void printHelp() {
        System.out.println("1. list\n2. choose");
    }
    
    /**
     * Prints the rooms with each line per room with room numbers.
     * 
     * @param rooms Double Array to be printed out
     */
    private static void printRooms(int[][] rooms) {
        for (int i = 0; i < rooms.length; i++) {
            System.out.print(i + ". ");
            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j] == 0) {
                    System.out.print("0 ");
                } else {
                    System.out.print("1 ");
                }
            }
            
            System.out.println();
        }
    }
    //</editor-fold>

}
