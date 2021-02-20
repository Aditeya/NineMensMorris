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
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PlayerTurn;
import ninemensmorris.enums.PrintType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The class whose objects will implement the boards of Nine Mens Morris Handles
 * the internal logic of NMM
 *
 * @author eltojaro
 */
public class NnMnMrrs {

    //static json Object that stors slot index pairs
    private static JSONObject slotIndxRef = null;

    //variable to check the turn
    private PlayerTurn nmmTurn;
    //prepares the array space for gridboard
    public NMMCoin[][] nmmBoard = new NMMCoin[8][3];

    //Input stream for reading
    //ObjectInputStream coinIN;
    /**
     * Default Constructor, intializes a RTU game
     */
    NnMnMrrs() {

        //intializes turn to white as per game rules I swear I'm not racist
        nmmTurn = PlayerTurn.WHITE;

        char ltr = 'A'; //char to set slot, might depreciate later
        //itterates through every NMMCoin and intializes its
        for (int i = 0; i < nmmBoard.length; i++) {
            int num = 1; //num to set slot, might depreciate later

            for (int j = 0; j < nmmBoard[i].length; j++) {
                String str = "" + ltr + num;  //str to set slot, might depreciate later
                nmmBoard[i][j] = new NMMCoin(str);
                num++;      //increments num
            }
            ltr++;      //increments ltr
        }

        //checks if slotLkUp has no values, initilizes it once
        if (slotIndxRef == null) {
            try {
                slotIndxRef = (JSONObject) new JSONParser().parse(
                        new FileReader(
                                "src/ninemensmorris/resources/slot_index_ref.json"));
            } catch (Exception ex) {
                Logger.getLogger(NnMnMrrs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
    public ObjectInputStream getStrmIn() {
        return coinIN;  }*/

    /**
     * Looks up the index assosciated with the given slot
     *
     * @param slot the slot to be looked up
     * @return the associated index
     */
    public static int[] slotLkUp(String slot) {
        int[] index = new int[2];  //variable to store the index
        //gets the value from the array and returns
        JSONArray arrIndx = (JSONArray) slotIndxRef.get(slot);
        index[0] = Math.toIntExact((long) arrIndx.get(0));
        index[1] = Math.toIntExact((long) arrIndx.get(1));
        return index;
    }

    // <editor-fold defaultstate="collapsed" desc="trun handling">
    /**
     * Gets the current turn
     *
     * @return the current players turn
     */
    public PlayerTurn getNmmTurn() {
        return nmmTurn;
    }

    /**
     * Sets the player turn
     *
     * @param nmmTurn the turn to be set
     * @return false if new turn is the same as old turn, false otherwise
     */
    public boolean setNmmTurn(PlayerTurn nmmTurn) {
        //checks if curr turn is same as parameter, if so returns false
        if (this.nmmTurn == nmmTurn) {
            return false;
        }
        this.nmmTurn = nmmTurn;
        return true;
    }

    /**
     * Detects and swaps turn
     *
     * @return the opposite turn, null if unable to swap
     */
    public PlayerTurn swapNMMTurn() {
        //swaps the turn
        switch (nmmTurn) {
            case WHITE:
                nmmTurn = PlayerTurn.BLACK;
                return nmmTurn;
            case BLACK:
                nmmTurn = PlayerTurn.WHITE;
                return nmmTurn;
        }
        return null;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="coin handling">
    /**
     * Gets the coin type at slot location
     *
     * @param slot location slot
     * @return coin type at slot
     */
    public MCoinType getNmmCnType(String slot) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the coin type at slot location as integer
     *
     * @param slot location slot
     * @return coin type at slot
     */
    public MCoinType getNmmCnTypInt(String slot) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the coin type at index
     *
     * @param row row index
     * @param col col index
     * @return coin type at index
     */
    public MCoinType getNmmCnType(int row, int col) {
        return nmmBoard[row][col].getCoin();
    }

    /**
     * Gets the coin type at index as integer
     *
     * @param row row index
     * @param col col index
     * @return empty:0, white:1, black:2
     */
    public int getNmmCnTypInt(int row, int col) {
        return nmmBoard[row][col].getCoinInt();
    }

    /**
     * Sets the coin type at slot location
     *
     * @param type the coin type to be set
     * @param slot the slot location
     * @return false if new coin type is the same as old turn, false otherwise
     */
    public boolean setNmmCnType(MCoinType type, String slot) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the coin type at index
     *
     * @param type the coin type to be set
     * @param row the row index
     * @param col the col index
     * @return false if new coin type is the same as old turn, false otherwise
     */
    public boolean setNmmCnType(MCoinType type, int row, int col) {
        //checks if coin is same as parameter, if so returns false
        if (nmmBoard[row][col].getCoin() == type) {
            return false;
        }
        nmmBoard[row][col].setCoin(type);
        return true;
    }

    //</editor-fold>
    /**
     * Handles setup completely, send in objects from an input stream
     *
     * @return
     */
    public boolean nmmSetup() {
        int menLeft = 9; //The number of men you can place in phase 1

        for (int i = 0; i < menLeft; i++) {
            //inputStream.transferTo(targetStream);

        }

        return true;
    }

    /**
     * Prints the NMM Board to the cmd line based on the type RAW_LOC : Prints
     * Location in a simple grid RAW_VALUE : Prints Values in a simple grid LOC
     * : Prints Location in a NMM board VALUE : Prints Values in a NMM board
     *
     * @param type The type of print
     */
    public void cmdPrint(PrintType type) {

        //checks if it's raw print, no need to read file then
        if (type == PrintType.RAW_VALUE || type == PrintType.RAW_LOC) {
            //Checks raw type, iterates an outputs accordingly 
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
            //if the type is not raw, reads the board and inserts values, then prints
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
