/* 
 * Copyright (C) 2021 elton
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
package ninemensmorris;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninemensmorris.enums.InputType;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PlayerTurn;
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
        
        //Negator Demo
        System.out.println("opposing time");
        PlayerTurn p = PlayerTurn.WHITE;
        System.out.println(p.getOpposeTurn());
        PlayerTurn p1 = PlayerTurn.BLACK;
        System.out.println(p1.getOpposeTurn());
        
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
            
            private void printPlayerTurn(PlayerTurn turn, int msg) {
                switch(msg){
                    case 1:
                        System.out.println(turn + " Player, Place a coin.");
                        break;
                    case 2:
                        System.out.println(turn + " Player, Select an opposing coin to be removed.\nEnter 'X' to conceed coin removal");
                        break;
                    case 3:
                    default:
                        System.out.println("Incorrect Usage. Check Docs.");
                        return;
                }
                
                System.out.println("match regex [A-H]+[1-3]");
            }

            @Override
            public void run() {
                
                String slot = new String();
                String slot2 = new String();
                NMMCoin coin = null;
                NMMCoin coin2 = null;

                while(nmm.getWinner() == MCoinType.EMPTY)
                {
                    try
                    {
                        //prints the board
                        nmm.cmdPrint(PrintType.VALUE); 
                        
                        switch(nmm.getInput())
                        {
                            case NONE:
                                while(nmm.getInput() == InputType.NONE)  //optional
                                    Thread.sleep(150); //optional
                                //InputType woah = InputType.
                                break;
                                
                            case PLACE:
                                printPlayerTurn(nmm.getNmmTurn(), 1);
                                slot = input.nextLine();
                                if(!slot.matches("[A-H]+[1-3]")) //check for regex
                                {
                                    System.out.println("Does not match regex, please try again");
                                    break;
                                }
                                
                                coin = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot, false, null, null);

                                //Sets the coin
                                sendCoin.put(coin);
                                Thread.sleep(50);
                                break;
                                
                            case REMOVE:
                                printPlayerTurn(nmm.getNmmTurn(), 2);
                                slot = input.nextLine();
                                if(!slot.matches("[A-H]+[1-3]")//check for regex
                                        //This checker is hot fix only, remove
                                        //it later
                                        && !slot.equals("X")) 
                                {
                                    System.out.println("Does not match regex, please try again");
                                    break;
                                }
                                
                                coin = new NMMCoin(nmm.getNmmTurn().toMCntyp(), slot, false, null, null);

                                //Sets the coin
                                sendCoin.put(coin);
                                Thread.sleep(50);
                                break;
                                
                            case MOVE:
                                System.out.println(nmm.getNmmTurn() + " Player, Select an coin to be moved");
                                
                                System.out.println("match regex [A-H]+[1-3]");
                                slot = input.nextLine();
                                if(!slot.matches("[A-H]+[1-3]")) //check for regex
                                {
                                    System.out.println("Does not match regex, please try again");
                                    break;
                                }
                                
                                System.out.println(nmm.getNmmTurn() + " Player, Move coin " + slot + " to?");
                                
                                System.out.println("match regex [A-H]+[1-3]");
                                slot2 = input.nextLine();
                                if(!slot.matches("[A-H]+[1-3]")) //check for regex
                                {
                                    System.out.println("Does not match regex, please try again");
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
        
        
        while(nmm.getWinner() == MCoinType.EMPTY)
        {
            nmm.nmmTurnHandle(sendCoin, true);
        }
       
        
        //NMMLogic.millLkUp("D3");
    }

    public static void test2(int[] test) {
        test[1] = 19;
    }

}
