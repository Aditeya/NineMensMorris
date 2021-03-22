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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.enums.InputType;
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
    //varibale to check what kind of input is required
    private InputType nmmInput;
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
        
        //intializes awaiting input type to NONE
        nmmInput = InputType.NONE;

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
        if(this.getNmmTurn() == PlayerTurn.WHITE)
        {
                this.nmmTurn = PlayerTurn.BLACK;
                return this.nmmTurn;
        }
        else if(this.getNmmTurn() == PlayerTurn.BLACK)
        {
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
        if (nmmBoard[row][col].getCoin() == type) {
            return false;
        }
        nmmBoard[row][col].setCoin(type);
        return true;
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
     * Resets the coin type at slot location to empty
     *
     * @param slot the slot location
     * @return false if new coin type is the same as old turn, true otherwise
     */
    public boolean resetNmmCnType(String slot) {
        int[] index = slotLkUp(slot);   //looks up the index for slot
        //checks if coin is empty, if so returns false
        if (nmmBoard[index[0]][index[1]].getCoin() == MCoinType.EMPTY) {
            return false;
        }
        nmmBoard[index[0]][index[1]].setCoin(MCoinType.EMPTY);
        return true;
    }

    /**
     * Resets the coin type at index
     *
     * @param row the row index
     * @param col the col index
     * @return false if new coin type is the same as old turn, true otherwise
     */
    public boolean resetNmmCnType(int row, int col) {
        //checks if coin is empty, if so returns false
        //checks if coin is filled, if so returns false
        if (nmmBoard[row][col].getCoin() == MCoinType.EMPTY) {
            return false;
        }
        nmmBoard[row][col].setCoin(MCoinType.EMPTY);
        return true;
    }
    
    /**
     * Removes the coin from the board, by setting coin type to empty.
     * Fails if the coin is milled
     * 
     * @param slot the slot of the coin to be removed
     * @return true if the coin is not milled, false otherwise
     */
    public boolean removeCoin(String slot) {
        
        int[] index = slotLkUp(slot);   //looks up the index for slot
        //checks if coin is milled, if so returns false
        if (nmmBoard[index[0]][index[1]].isMilled()) {
            return false;
        }
        nmmBoard[index[0]][index[1]].setCoin(MCoinType.EMPTY);
        return true;
    }

    /**
     * Removes the coin from the board, by setting coin type to empty.
     * Fails if the coin is milled
     * 
     * @param row the row index
     * @param col the col index
     * @return true if the coin is not milled, false otherwise
     */
    public boolean removeCoin(int row, int col) {
        
        //checks if coin is milled, if so returns false
        if (nmmBoard[row][col].isMilled()) {
            return false;
        }
        nmmBoard[row][col].setCoin(MCoinType.EMPTY);
        return true;
    }
    
     /**
     * Returns all the coins from the slots in the given array
     * @param slots The slots from which to get coins
     * @return an array list with all the coins
     */
    public ArrayList<NMMCoin> getCoinsFromSlots(String... slots)
    {
        //Creating a coin array list to be returned
        ArrayList<NMMCoin> coinList = new ArrayList<NMMCoin>();
        //Creating an int to hold the co ords
        int[] idx;
        
        //iterates thru every slot 
        for (String slot : slots) 
        {
            //looks up the index and adds that coin to the list
            idx = slotLkUp(slot);
            coinList.add(this.nmmBoard[ idx[0] ][ idx[1] ]);
        }
        
        //returns the list
        return coinList;
    }
    
     /**
     * Returns all the coins from the slots in the given matrix
     * @param slots The slots from which to get coins
     * @return an array list with all the coins
     */
    public ArrayList<NMMCoin> getCoinsFromSlots(String[][] slots)
    {
        //Creating a coin array list to be returned
        ArrayList<NMMCoin> coinList = new ArrayList<NMMCoin>();
        //Creating an int to hold the co ords
        int[] idx;
        
        //iterates thru every slot 
        for (String[] slotss : slots) 
        {
            for (String slot : slotss)
            {
                //looks up the index and adds that coin to the list
                idx = slotLkUp(slot);
                coinList.add(this.nmmBoard[ idx[0] ][ idx[1] ]);
            }
        }
        
        //returns the list
        return coinList;
    }
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="mill handling">
    
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


    /**
     * Checks whether the coin in the given slot can form a mill. 
     * Checks horizontally and vertically
     * @param slot The slot to be mill checked
     * @return A HashMap of all the *new* mills formed 
     */
    public HashMap<String, Boolean> checkMill(String slot) {
        //Creates a return map
        HashMap<String, Boolean> newMillStats = new HashMap<>();

        // iterator to track new mills
        int mill_I = 1;
        //a flag to determine if a new mill was added
        boolean isNewMill = false;
        //Gets the index of the slot
        int[] idx = NMMLogic.slotLkUp(slot);
        //creates a reference to the coin from the slot param
        NMMCoin coinCheck = nmmBoard [idx[0]] [idx[1]];
        
        //holds the oringal mill state of the coin to be checked
        boolean ogIsMill = coinCheck.isMilled();
        
        //sets the return var newMills first position to the current slot
        //newMills[0] = coinCheck.getCoinSlot();
        
        ArrayList<NMMCoin> coinList =
                this.getCoinsFromSlots(coinCheck.millCombo);
        
        //checks for the first possible mill (horizontal)
        if(coinCheck.getCoin() == coinList.get(0).getCoin() &&
                coinCheck.getCoin() == coinList.get(1).getCoin() )
        {
            //sets param coin hmill to true
            coinCheck.setMillH(true);
            
            //sets new mill to true
            isNewMill = true;
            
            //if true, sets hMill for the neightbour coins to true
            for(int i = 0; i < 2; i++)
            {
                //adds them to the return string//might need to do that later
                //newMills[mill_I++] = coinList.get(i).getCoinSlot();
                //hmills them
                coinList.get(i).setMillH(true);
                //since these coins are not changed anymore, we can update mill
                //for them here
                //also updates the mill, and adds it to the return map
                newMillStats.put(coinList.get(i).getCoinSlot(),
                        coinList.get(i).updateMill());
            }
        }
        else //if the coins aren't milled
        {
            //sets param coin hmill to false
            coinCheck.setMillH(false);
                       
            //if false, sets hMill for the neightbour coins to false
            for(int i = 0; i < 2; i++)
            {
                //adds them to the return string//might need to do that later
                //newMills[mill_I++] = coinList.get(i).getCoinSlot();
                //un hmills them
                coinList.get(i).setMillH(false);
                //since these coins are not changed anymore, we can update mill
                //for them here
                //also updates the mill, and adds it to the return map
                newMillStats.put(coinList.get(i).getCoinSlot(),
                        coinList.get(i).updateMill());
            }
            
        }
        //checks for the second possible mill (vertical)
        if(coinCheck.getCoin() == coinList.get(2).getCoin() &&
                coinCheck.getCoin() == coinList.get(3).getCoin() )
        {
            //sets param coin hmill to true
            coinCheck.setMillV(true);
            
            //sets new mill to true
            isNewMill = true;
            
            //if true, sets hMill for the neightbour coins to true
            for(int i = 2; i < 4; i++)
            {
                //adds them to the return string//might need to do that later
                //newMills[mill_I++] = coinList.get(i).getCoinSlot();
                //vmills them
                coinList.get(i).setMillV(true);
                //since these coins are not changed anymore, we can update mill
                //for them here
                //also updates the mill, and adds it to the return map
                newMillStats.put(coinList.get(i).getCoinSlot(),
                        coinList.get(i).updateMill());
            }
        }
        else //if the coins aren't milled
        {
            //sets param coin hmill to false
            coinCheck.setMillV(false);
            
            //if false, sets hMill for the neightbour coins to false
            for(int i = 2; i < 4; i++)
            {
                //adds them to the return string//might need to do that later
                //newMills[mill_I++] = coinList.get(i).getCoinSlot();
                //un vmills them
                coinList.get(i).setMillV(false);
                //since these coins are not changed anymore, we can update mill
                //for them here
                //also updates the mill, and adds it to the return map
                newMillStats.put(coinList.get(i).getCoinSlot(),
                        coinList.get(i).updateMill());
            }
            
        }
        
        //adds a flag to the return list to allow checking if a mill was 
        //formed with a better time complexity
        newMillStats.put("mill", isNewMill);
        
        //also updates the mill, and adds it to the return map
        newMillStats.put(coinCheck.getCoinSlot(),
                coinCheck.updateMill());
        
        //returns the new mill String
        return newMillStats;
        
        /*
        for(int i = 0; i < 2; i++)
        {
            //sets the return var newMills first position to the current slot
            newMills[0] = coinCheck.getCoinSlot();
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
                    newMills[mill_I++] = millCheck;
                    this.setNmmCnMill(true, millCheck);                  
                }
                else    //if a single coin doesn't match the rest in this line
                {
                    if(i==0)
                    {
                        //overwrites newMills to null
                        newMills = null;
                        //reset mill_I to 1
                        mill_I=1;
                        //sets previously set coins isCoinMilled to false
                        if(j == 0)
                            this.setNmmCnMill(ogIsMill, millCheck);
                    }
                    break;
                    
                }
            }
        }*/
        
        
        
        //return null;
    }

    //</editor-fold>
    
    /**
     * Handles all required mill actions that would need be done whenever a coin
     * is placed. Includes checking for new mills and removing a coin if mill 
     * is formed
     * @param slot the slot to be checked
     * @param coinIN A Blocking Queue to allow input providing
     * @param verbose flag as to whether the verbose
     * @return a HashMap of newly formed mills
     */
    public HashMap<String, Boolean> nmmMillHandle(String slot, LinkedBlockingQueue<NMMCoin> coinIN, boolean verbose)
    {
        //create the array of messages
        String[] notif = new String[7];
        
        //populates array if verbose is true
        if (verbose == true) {
            notif[0] = "Handling Mills..";
            notif[1] = "A new Mill has been detected.";
            notif[2] = "Awaiting Coin..";
            notif[3] = "Coin Received.";
            notif[4] = "An interuption has occured, could not remove the coin.";
            notif[5] = "The given coin is milled and can not be removed, submit "
                    + "a new coin.";
        }
        
        //sends verbose
        System.out.println(notif[0]);
        
        //flag to see if a mill was formed
        HashMap<String, Boolean> newMills = new HashMap<>();
        
        //checks the mills, gives us a hashmap with the stuff
        newMills = this.checkMill(slot);
        
        //checks wheter a new mill is made 
        if(newMills.get("mill"))
        {
            //this means a mill was formed, so you need to take input to remove an opponents coin
            System.out.println(notif[1]);
            
            //creates a variable
            NMMCoin coinRemove;
            
            //index reference 
            int[] idx = new int[2];
            //mill check flag
            boolean isCoinMilled;
            
            do
            {
                //Tries to get a coin, handles interruption
                //sets game input to awaiting input
                this.nmmInput = InputType.REMOVE;
                //sends verbose for coin awaiting
                System.out.println(notif[2]);
                try {
                    //gets coin and sends message if verbose
                    coinRemove = coinIN.take();
                    System.out.println(notif[3]);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();  // set interrupt flag
                    System.out.println(notif[4]);
                    return null;
                }
                //coin received, no longer awaiting input, updates as such
                this.nmmInput = InputType.NONE;

                //gets the index of the slot from the received coin
                idx = slotLkUp(coinRemove.getCoinSlot());

                //sets wheter the coin is milled into a variable
                isCoinMilled = this.nmmBoard[ idx[0] ][ idx[1] ].isMilled();

                if(isCoinMilled)
                        System.out.println(notif[5]);

                //repeats if submitted coin is not milled
            }while(!isCoinMilled);
            
            //sets the coin on the board correpsonding to the slot as zero
            this.nmmBoard[ idx[0] ][ idx[1] ].setCoin(MCoinType.EMPTY);
            
        }
        
        return newMills;
    }
            
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
                //Sets awaiting input type to PLACE
                this.nmmInput = InputType.PLACE;
                //checks for coin, blocks till found
                NMMCoin coinSet = coinIN.take();
                //Sets waiting input to NONE
                this.nmmInput = InputType.NONE;
                //verboses coin received
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
                        //Sets awaiting input type to PLACE
                        this.nmmInput = InputType.PLACE;
                        //checks for coin, blocks till found
                        coinSet = coinIN.take();
                        //Sets waiting input to NONE
                        this.nmmInput = InputType.NONE;
                        //verboses coin received
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
    
     /**
     * Prints the NMM Board to the cmd line based on the type RAW_LOC : Prints
     * Location in a simple grid RAW_VALUE : Prints Values in a simple grid LOC
     * : Prints Location in a NMM board VALUE : Prints Values in a NMM board
     *
     * @param type The type of print
     */
    public static void cmdPrint(NMMCoin[][] nmmBoard, PrintType type) {

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
                                    NMMLogic.class.getResourceAsStream("/ninemensmorris/resources/cmd_board.txt")))) {
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
