/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris;

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
    public static void main(String[] args) {

        int[] test = {5, 3, 2, 1};
        test2(test);
        System.out.println(test[1]);

        NnMnMrrs nmm = new NnMnMrrs();

        nmm.cmdPrint(PrintType.LOC);
        nmm.cmdPrint(PrintType.RAW_LOC);
        nmm.cmdPrint(PrintType.RAW_VALUE);
        nmm.cmdPrint(PrintType.VALUE);

        NMMCoin coin = new NMMCoin(MCoinType.WHITE, null, null, null);
        System.out.println(coin.getCoinInt());

        System.out.println(NnMnMrrs.slotLkUp("E3")[0]);
        System.out.println(NnMnMrrs.slotLkUp("E3")[1]);
        System.out.println("\n\n\n");

        //Use these to set coins
        //nmm.setNmmCnType(MCoinType.BLACK, slot) //will throw unsupportedException      
        int[] numslot = NnMnMrrs.slotLkUp("A1");
        nmm.setNmmCnType(MCoinType.WHITE, numslot[0], numslot[1]);
        numslot = NnMnMrrs.slotLkUp("G3");
        nmm.setNmmCnType(MCoinType.BLACK, numslot[0], numslot[1]);
        nmm.cmdPrint(PrintType.VALUE);

    }

    public static void test2(int[] test) {
        test[1] = 19;
    }

}
