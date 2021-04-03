/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking.demo;

import java.util.Scanner;
import ninemensmorris.networking.Server;

/**
 *
 * @author aditeya
 */
public class NMMServerDemo {

    private final static Scanner INPUT = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server();
        Thread serverThread = new Thread(server);

        serverThread.start();

        boolean run = true;
        while (run) {
            System.out.print("Server> ");
            String command = INPUT.nextLine();

            switch (command) {
                case "kill-server":
                    if (!confirmKill()) {
                        break;
                    }
                    serverThread.interrupt();
                    run = false;
                    break;
                default:
                    printHelp();
            }
        }
    }

    private static void printHelp() {
        System.out.println("1. kill-server\n2.");
    }

    private static boolean confirmKill() {
        System.out.print("Are you sure you want to shutdown the server? [y/N]: ");
        String ans = INPUT.nextLine();
        return ans.toLowerCase().equals("y");
    }

}
