/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import ninemensmorris.enums.PrintType;

/**
 * The class whose objects will implement the boards of Nine Mens Morris Handles
 * the internal logic of NMM
 *
 * @author eltojaro
 */
public class NnMnMrrs {

    //prepares the array space for gridboard
    public NMMCoin[][] nmmBoard = new NMMCoin[8][3];

    /**
     * Default Constructor, intializes a RTU game
     */
    NnMnMrrs() {
        char ltr = 'A'; //char to set slot, might depreciate later

        //itterates through every NMMCoin and intializes its
        for (int i = 0; i < nmmBoard.length; i++) {
            int num = 1; //num to set slot, might depreciate later

            for (int j = 0; j < nmmBoard[i].length; j++) {
                String str = "" + ltr + num;  //str to set slot, might depreciate later
                nmmBoard[i][j] = new NMMCoin(str);
                num++;
            }
            ltr++;
        }

    }

    public void cmdPrint(PrintType type) {
        if (type == PrintType.RAW_VALUE || type == PrintType.RAW_LOC) {
            switch (type) {
                case RAW_LOC:
                    for (int i = 0; i < nmmBoard.length; i++) {
                        for (int j = 0; j < nmmBoard[i].length; j++) {
                            System.out.print(nmmBoard[i][j].getCoinSlot());
                        }
                        System.out.println("");
                    }
                    break;

                case RAW_VALUE:
                    for (int i = 0; i < nmmBoard.length; i++) {
                        for (int j = 0; j < nmmBoard[i].length; j++) {
                            System.out.print(nmmBoard[i][j].getCoin());
                        }
                        System.out.println("");
                    }
                    break;
            }

        } else {
            try (BufferedReader in
                    = new BufferedReader(
                            new InputStreamReader(
                            getClass().getResourceAsStream("/ninemensmorris/resources/cmd_board.txt")))) {
                //Reads File and Buils String
                StringBuilder sb = new StringBuilder();
                String line = in.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = in.readLine();
                }

                String cmdBoard = sb.toString();
                //Reads File and Buils String Done

                //replaces 'o' with loc
                if (type == PrintType.LOC) {
                    for (int i = 0; i < nmmBoard.length; i++) {
                        for (int j = 0; j < nmmBoard[i].length; j++) {
                            cmdBoard = cmdBoard.replaceFirst("o", nmmBoard[i][j].getCoinSlot());
                        }
                    }
                } //replaces 'o' with value
                else if (type == PrintType.VALUE) {
                    for (int i = 0; i < nmmBoard.length; i++) {
                        for (int j = 0; j < nmmBoard[i].length; j++) {
                            cmdBoard = cmdBoard.replaceFirst("o", "" + nmmBoard[i][j].getCoinInt());
                        }
                    }
                }

                //finally prints the board
                System.out.println(cmdBoard);

            } //politely handles errors
            catch (FileNotFoundException ex) {
                System.out.println("The required file could not be found\n"
                        + "Please use RAW_LOC or RAW_VALUE instead");
            } catch (IOException ex) {
                System.out.println("An error occured while reading from the file\n"
                        + "Please use RAW_LOC or RAW_VALUE instead");
            }
        }
    }

}
