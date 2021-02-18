/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris;

import ninemensmorris.enums.PrintType;

/**
 * Used to demo function of the NMM internal logic class
 * @author eltojaro
 */
public class NMMLogicDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int[] test  = {5, 3, 2, 1};
        test2(test);
        System.out.println(test[1]);
        
        NnMnMrrs nmm = new NnMnMrrs();
        
        nmm.cmdPrint(PrintType.LOC);
        nmm.cmdPrint(PrintType.RAW_LOC);
        nmm.cmdPrint(PrintType.RAW_VALUE);
        nmm.cmdPrint(PrintType.VALUE);
        
        while(true)
        {
            System.out.println("Oh hi bro");
        }
        
    }
    
    public static void test2(int[] test)
    {
        test[1] = 19;
    }
    
}
