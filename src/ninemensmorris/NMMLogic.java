/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;
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
public class NMMLogic {

    //static json Object that stors slot index pairs
    private static JSONObject slotIndxRef = null;
    //static json Object that stors slot index pairs
    private static JSONObject millRef = null;

    //The number of men you can place in phase 1        
    private int menLeft;
    //variable to check the turn
    private PlayerTurn nmmTurn;
    //prepares the array space for gridboard
    public NMMCoin[][] nmmBoard = new NMMCoin[8][3];

    //Input stream for reading
    //ObjectInputStream coinIN;
    /**
     * Default Constructor, intializes a RTU game
     */
    NMMLogic() {

        //checks if slotLkUp has no values, initilizes it once
        if (slotIndxRef == null) {
            try {
                slotIndxRef = (JSONObject) new JSONParser().parse(
                        new InputStreamReader(getClass().getResourceAsStream(
                                "/ninemensmorris/resources/slot_index_ref.json")));

            } catch (Exception ex) {
                System.out.println("slot_index_ref.json could not be loaded.\n"
                        + " The game can be played.\n"
                        + "Some functionality may not work as intended");
                Logger.getLogger(NMMLogic.class.getName()).log(Level.WARNING, null, ex);
            }
        }
        //checks if slotLkUp has no values, initilizes it once
        if (millRef == null) {
            try {
                millRef = (JSONObject) new JSONParser().parse(
                        new InputStreamReader(getClass().getResourceAsStream(
                                "/ninemensmorris/resources/mill_ref.json")));
                 
            } catch (Exception ex) {
                System.out.println("mill_ref.json could not be loaded.\n"
                        + " The game can be played.\n"
                        + "Some functionality may not work as intended");
                Logger.getLogger(NMMLogic.class.getName()).log(Level.WARNING, null, ex);
            }
        }

        //The number of men availible at the start of the game
        menLeft = 9;

        //intializes turn to white as per game rules I swear I'm not racist
        nmmTurn = PlayerTurn.WHITE;

        char ltr = 'A'; //char to set slot, might depreciate later
        //itterates through every NMMCoin and intializes its slot, millCombos
        //and valid moves
        for (int i = 0; i < nmmBoard.length; i++) {
            int num = 1; //num to set slot, might depreciate later

            for (int j = 0; j < nmmBoard[i].length; j++) {
                String str = "" + ltr + num;  //str to set slot, might depreciate later
                nmmBoard[i][j] = new NMMCoin(str);
                nmmBoard[i][j].millCombo = millLkUp(str);
                num++;      //increments num
            }
            ltr++;      //increments ltr
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

    public static String[][] millLkUp(String slot) {
        String[][] millCombo = new String[2][2];
        //gets the value from the array and returns
        JSONArray arrIndx = (JSONArray) millRef.get(slot);
        millCombo[0][0] = (String) ((JSONArray) arrIndx.get(0)).get(0);
        millCombo[0][1] = (String) ((JSONArray) arrIndx.get(0)).get(1);
        millCombo[1][0] = (String) ((JSONArray) arrIndx.get(1)).get(0);
        millCombo[1][1] = (String) ((JSONArray) arrIndx.get(1)).get(1);

        return millCombo;
    }

    // <editor-fold defaultstate="collapsed" desc="getters-setters">
    /**
     * Gets the remaning men
     *
     * @return The men Left
     */
    public int getMenLeft() {
        return menLeft;
    }

    //no setterfor menLeft
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="turn handling">
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
        switch (this.nmmTurn) {
            case WHITE:
                this.nmmTurn = PlayerTurn.BLACK;
                return this.nmmTurn;
            case BLACK:
                this.nmmTurn = PlayerTurn.WHITE;
                return this.nmmTurn;
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
        int[] index = slotLkUp(slot);
        return nmmBoard[index[0]][index[1]].getCoin();
    }

    /**
     * Gets the coin type at slot location as integer
     *
     * @param slot location slot
     * @return coin type at slot
     */
    public int getNmmCnTypInt(String slot) {
        int[] index = slotLkUp(slot);   //looks up the index for slot
        return nmmBoard[index[0]][index[1]].getCoinInt();
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
        int[] index = slotLkUp(slot);   //looks up the index for slot
        //checks if coin is same as parameter, if so returns false
        if (nmmBoard[index[0]][index[1]].getCoin() == type) {
            return false;
        }
        nmmBoard[index[0]][index[1]].setCoin(type);
        return true;
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
        //checks if coin is filled, if so returns false
        if (nmmBoard[row][col].getCoin() == MCoinType.EMPTY) {
            nmmBoard[row][col].setCoin(type);
            return true;
        }
        return false;
    }

    /**
     * Sets the coin type at slot location
     *
     * @param type the coin type to be set
     * @param slot the slot location
     * @return false if old coin is filled, false otherwise
     */
    public boolean setNmmCnTypeIfEmpty(MCoinType type, String slot) {
        int[] index = slotLkUp(slot);   //looks up the index for slot
        //checks if coin is filled, if so returns false
        if (nmmBoard[index[0]][index[1]].getCoin() == MCoinType.EMPTY) {
            nmmBoard[index[0]][index[1]].setCoin(type);
            return true;
        }
        return false;
    }
    
    

    /**
     * Sets the coin type at index
     *
     * @param type the coin type to be set
     * @param row the row index
     * @param col the col index
     * @return false if old coin is filled, false otherwise
     */
    public boolean setNmmCnTypeIfEmpty(MCoinType type, int row, int col) {
        //checks if coin is same as parameter, if so returns false
        if (nmmBoard[row][col].getCoin() == type) {
            return false;
        }
        nmmBoard[row][col].setCoin(type);
        return true;
    }
    
    /**
     * Gets is milled at index
     *
     * @param row row index
     * @param col col index
     * @return isMilled at index
     */
    public boolean getNmmCnMill(int row, int col) {
        return nmmBoard[row][col].isMilled();
    }

    /**
     * Gets is milled at slot
     *
     * @param slot the coin slot
     * @return isMilled at slot
     */
    public boolean getNmmCnMill(String slot) {
        int[] index = slotLkUp(slot);
        return nmmBoard[index[0]][index[1]].isMilled();
    }
    
    /**
     * Sets whether isMilled at index
     *
     * @param milled the mill to be set
     * @param row the row index
     * @param col the col index
     * @return false if new coin type is the same as old turn, false otherwise
     */
    public boolean setNmmCnMill(boolean milled, int row, int col) {
        //checks if mill is same as parameter, if so returns false
        if (nmmBoard[row][col].isMilled() == milled) {
            return false;
        }
        nmmBoard[row][col].setMilled(milled);
        return true;
    }

    /**
     * Sets whether isMilled at slot location
     *
     * @param milled the state of mill to be set
     * @param slot the slot location
     * @return false if new mill type is the same as old turn, false otherwise
     */
    public boolean setNmmCnMill(boolean milled, String slot) {
        int[] index = slotLkUp(slot);   //looks up the index for slot
        //checks if mill is same as parameter, if so returns false
        if (nmmBoard[index[0]][index[1]].isMilled() == milled) {
            return false;
        }
        nmmBoard[index[0]][index[1]].setMilled(milled);
        return true;
    }


    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="mill handling">
    public String[] checkMill(String slot) {
        //Creates a return string
        String[] newMill = new String[5];
        // iterator to track new mills
        int iMill = 1;
        //Gets the index of the slot
        int[] idx = NMMLogic.slotLkUp(slot);
        //creates a reference to the coin
        NMMCoin coinCheck = nmmBoard [idx[0]] [idx[1]];
        
        
        for(int i = 0; i < 2; i++)
        {
            //sets the return var newMill first position to the current slot
            newMill[0] = coinCheck.getCoinSlot();
            /////////Needs Major Work/////////
            for(int j = 0; j < 2; j++)
            {
                String millCheck = coinCheck.millCombo[i][j];
                
                //Compares the coin vs it's possible mills
                if(coinCheck.getCoin() == 
                        getNmmCnType(millCheck))
                {
                    //if mill adds the value to the return String
                    //and changes that coin mill to true
                    newMill[iMill++] = millCheck;
                    this.setNmmCnMill(true, millCheck);                  
                }
                else    //if a single coin doesn't match the rest in this line
                {
                    //overwrites newMill to null
                    newMill = null;
                    //reset iMill to 1
                    iMill=1;
                    //sets previously set coins isMill to false
                    if(j == 0)
                        this.setNmmCnMill(false, millCheck);
                    
                }
            }
        }
        
        
        
        return null;
    }

    //</editor-fold>
    /**
     * Handles setup completely, uses a Linked Blocking Queue Put objects into
     * the queue using a separate thread Remember to check the NMM objects
     * current player turn
     *
     * @param coinIN
     * @return
     */
    public boolean nmmSetup(LinkedBlockingQueue<NMMCoin> coinIN, boolean verbose) {

        //create the array of messages
        String[] notif = new String[7];

        //populates array if verbose is true
        if (verbose == true) {
            notif[0] = "Waiting for coin...\n";
            notif[1] = "Coin received.\n";
            notif[2] = "Coin set.\n";
            notif[3] = "Unable to set coin as slot is filled\nPlayer turns have"
                    + " not been swapped\nRe-set the coin in another slot.\n";
            notif[4] = "Turn has been completed";
            notif[5] = "An Interupted Exception has occured, "
                    + "Setup terminated.\n";
            notif[6] = "Setup Completed Successfully\n";
        }

        final int menLeftF = menLeft;

        //repeats the setting process 9x2 times, alternating b/w players
        for (int i = 0; i < menLeftF * 2; i++) {

            //try to catch interrupted exceptions
            try {
                //sends verbose message
                if (verbose == true) {
                    System.out.println("setup_i = " + i);
                    System.out.println(nmmTurn + " Turn " + (10 - menLeft));
                    System.out.println("Men Left = " + menLeft);
                }

                System.out.print(notif[0]); //verbose message
                //checks for coin, blocks till found
                NMMCoin coinSet = coinIN.take();
                System.out.println(notif[1]);

                //sets the coin according to the slot
                if (setNmmCnTypeIfEmpty(coinSet.getCoin(), coinSet.getCoinSlot())) {
                    //swaps the player turn
                    this.swapNMMTurn();
                    System.out.print(notif[2]);
                } else {
                    //repeats coin setting till valid set
                    do {
                        System.out.print(notif[3]);
                        System.out.print(notif[0]); //verbose message
                        //checks for coin, blocks till found
                        coinSet = coinIN.take();
                        System.out.println(notif[1]);
                    } while (!setNmmCnTypeIfEmpty(coinSet.getCoin(), coinSet.getCoinSlot()));

                    //swaps turns
                    this.swapNMMTurn();
                    System.out.print(notif[2]);
                }

                System.out.println(notif[4]);//prints verbose message
                //checks if i is even, decrements menLeft if true
                menLeft = (i % 2 != 0) ? menLeft - 1 : menLeft;

            } //If Exceptions occurs, prints message and terminates setup
            catch (InterruptedException ex) {
                System.out.print(notif[5]);
                return false;
            }
        }
        //prints message and returns treu
        System.out.print(notif[6]);
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
