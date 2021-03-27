/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.enums.InputType;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PrintType;

/**
 * Used to demo function of the NMM internal logic class
 *
 * @author eltojaro
 */
public class NMMLogicDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        Scanner input = new Scanner(System.in);
             

        int[] test = {5, 3, 2, 1};
        test2(test);
        System.out.println(test[1]);

        NMMLogic nmm = new NMMLogic();
        
        //VldMvsDemo
        String slot = "H2";
        System.out.println("Valid Moves for "+slot +" : " + nmm.getVldMvsAsString(slot));
        
        System.out.println(nmm.checkVldMv("H2", "H1"));
        
        //Function as requested
        NMMLogic.cmdPrint(nmm.nmmBoard, PrintType.VALUE);
        
        System.out.println("Player " + nmm.getNmmTurn());
        nmm.swapNMMTurn();
        System.out.println("Player " + nmm.getNmmTurn());
        nmm.swapNMMTurn();
        System.out.println("Player " + nmm.getNmmTurn());

        nmm.cmdPrint(PrintType.LOC);
        nmm.cmdPrint(PrintType.RAW_LOC);
        nmm.cmdPrint(PrintType.RAW_VALUE);
        nmm.cmdPrint(PrintType.VALUE);

        NMMCoin coin = new NMMCoin(MCoinType.WHITE, null, false, null, null);
        System.out.println(coin.getCoinInt());

        System.out.println(NMMLogic.slotLkUp("E3")[0]);
        System.out.println(NMMLogic.slotLkUp("E3")[1]);
        System.out.println("\n\n\n");
        
        //Use these to set coins
        //nmm.setNmmCnType(MCoinType.BLACK, slot) //will throw unsupportedException      
        /*int[] numslot = NMMLogic.slotLkUp("A1");
        nmm.setNmmCnType(MCoinType.WHITE, numslot[0], numslot[1]);
        numslot = NMMLogic.slotLkUp("G3");
        nmm.setNmmCnType(MCoinType.BLACK, numslot[0], numslot[1]);
        nmm.cmdPrint(PrintType.VALUE);*/
         
        
        //Required for setup
        LinkedBlockingQueue sendCoin = new LinkedBlockingQueue(2);
        
        /*
        //starts a new thread
        Thread setupTh = new Thread(new Runnable() {

            @Override
            public void run() {

                while (nmm.getMenLeft() > 0) {  //CHANGE TO WHILE MEN LEFT
                    System.out.println(nmm.getNmmTurn() + " Player, Place a "
                            + "coin.");
                    System.out.println("match regex [A-H]+[1-3]");
                    String slot = input.nextLine();
                    
                    NMMCoin coin = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot, false, null, null);

                    try {
                        //Sets the coin
                        sendCoin.put(coin);
                        
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(NMMLogicDemo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //prints the board
                    nmm.cmdPrint(PrintType.VALUE);

                }
                System.out.println("setup fin");
                
                
            }

        });

        setupTh.start();
        */
        
        
        Thread turnTh = new Thread(new Runnable() {

            @Override
            public void run() {
                
                String slot = new String();
                String slot2 = new String();
                NMMCoin coin = null;
                NMMCoin coin2 = null;

                while(true)
                {
                    try
                    {
                        //prints the board
                        nmm.cmdPrint(PrintType.VALUE); 
                        
                        switch(nmm.getInput())
                        {
                            case NONE:
                                Thread.sleep(150); //optional
                                //InputType woah = InputType.
                                break;
                                
                            case PLACE:
                                System.out.println(nmm.getNmmTurn() + " Player, Place a "
                                        + "coin.");
                                System.out.println("match regex [A-H]+[1-3]");
                                slot = input.nextLine();
                                if(!slot.matches("[A-H]+[1-3]")) //check for regex
                                {
                                    System.out.println("Does not match regex, "
                                            + "please try again");
                                    break;
                                }
                                
                                coin = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot, false, null, null);

                                //Sets the coin
                                sendCoin.put(coin);
                                Thread.sleep(50);
                                break;
                                
                            case REMOVE:
                                System.out.println(nmm.getNmmTurn() + " Player, "
                                        + "Select an opposing coin to be removed");
                                
                                System.out.println("match regex [A-H]+[1-3]");
                                slot = input.nextLine();
                                if(!slot.matches("[A-H]+[1-3]")) //check for regex
                                {
                                    System.out.println("Does not match regex, "
                                            + "please try again");
                                    break;
                                }
                                
                                coin = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot, false, null, null);

                                //Sets the coin
                                sendCoin.put(coin);
                                Thread.sleep(50);
                                break;
                                
                            case MOVE:
                                System.out.println(nmm.getNmmTurn() + " Player, "
                                        + "Select an coin to be moved");
                                
                                System.out.println("match regex [A-H]+[1-3]");
                                slot = input.nextLine();
                                if(!slot.matches("[A-H]+[1-3]")) //check for regex
                                {
                                    System.out.println("Does not match regex, "
                                            + "please try again");
                                    break;
                                }
                                
                                System.out.println(nmm.getNmmTurn() + " Player, "
                                        + "Move coin " + slot + " to?");
                                
                                System.out.println("match regex [A-H]+[1-3]");
                                slot2 = input.nextLine();
                                if(!slot.matches("[A-H]+[1-3]")) //check for regex
                                {
                                    System.out.println("Does not match regex, "
                                            + "please try again");
                                    break;
                                }
                                
                                coin = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot, false, null, null);
                                coin2 = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot2, false, null, null);

                                //Sets the coin
                                sendCoin.put(coin);
                                sendCoin.put(coin2);
                                Thread.sleep(50);
                                break;
                                
                                
                            default:
                                System.out.println("oh snep something broke");
                                System.exit(-1);
                                
                            
                                
                        }
                    
                    } catch (InterruptedException ex) {
                        Logger.getLogger(NMMLogicDemo.class.getName()).log(Level.SEVERE, null, ex);
                    }


                }
             
                
            }

        });        
        
        turnTh.start();

        nmm.nmmSetup(sendCoin, true);

        ////////////////////////
        //////setup done////////
        ////////////////////////
        
        ////////////////////////
        /////////turns//////////
        ////////////////////////
        
        
        while(true)
        {
            nmm.nmmTurnHandle(sendCoin, true);
        }
       
        
        //NMMLogic.millLkUp("D3");
    }

    public static void test2(int[] test) {
        test[1] = 19;
    }

}
