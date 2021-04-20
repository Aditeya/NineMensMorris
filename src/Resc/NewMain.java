/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resc;

import ninemensmorris.NMMCoin;
import ninemensmorris.NMMLogic;
import ninemensmorris.enums.MCoinType;

/**
 *
 * @author LENOVO
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        NMMLogic nmm = new NMMLogic();
        
        int idx[] = NMMLogic.slotLkUp("A1"); //
        System.out.printf("index= %d:%d\n", idx[0], idx[1]); //should be 0:0 for A1
        
        NMMCoin coin = nmm.nmmBoard[ idx[0] ][ idx[1] ]; //gets coin A1 
        
        coin.setCoin(MCoinType.WHITE); //sets A1
             
        System.out.println("coin at A1 = " + coin.getCoin());
                  
    

    }
}