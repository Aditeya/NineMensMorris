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
import java.sql.Array;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.networking.NCommand;
import ninemensmorris.networking.NMMClientThread;
import ninemensmorris.networking.NetworkCommand;

/**
 *
 * @author aditeya
 */
public class NMMClientDemo {

    private final static Scanner INPUT = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

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

            NMMClientThread game = new NMMClientThread(socket);
            Thread thread = new Thread(game);
            thread.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void printHelp() {
        System.out.println("1. list\n2. choose");
    }

    private static void list(ObjectInputStream ois, ObjectOutputStream oos) {
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.LIST_ROOMS);

        try {
            oos.reset();
            oos.writeObject(command);
            NCommand reply = (NCommand) ois.readObject();

            printRooms(reply.getRooms());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean choose(ObjectInputStream ois, ObjectOutputStream oos) {
        boolean success = true;
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.CHOOSE_ROOM);

        System.out.print("Enter Room Number: ");
        int room = Integer.parseInt(INPUT.nextLine());
        command.setRoom(room);

        try {
            oos.reset();
            oos.writeObject(command);
            NCommand reply = (NCommand) ois.readObject();

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

    private static void printRooms(int[][] rooms) {
        for (int i = 0; i < rooms.length; i++) {
            System.out.print(i + 1 + ". ");
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
}
