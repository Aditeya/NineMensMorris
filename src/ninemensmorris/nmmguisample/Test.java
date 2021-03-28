/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nmmguisample;

import java.util.HashMap;

/**
 *
 * @author LENOVO
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

              HashMap<String, Double[]> hCoinPos = createSlotHash();
              
              Double d[] = hCoinPos.get("A3");
              System.out.println("  "+d[0]);
    }
      public static HashMap createSlotHash(){
      HashMap<String, Double[]> hCoinPos = new HashMap<String, Double[]>();   
          char t;
         double[] dbpos = {       1,      1,      3.5,    1,       1,      6,      2,      2,      2,      3.5,2,    5,      3,      3,      3,      3.5,      3,    4,      3.5,      1,    3.5,      2,    3.5,      3,     3.5,      4,      3.5,
      5,3.5,      6,      4,      3,      4,     3.5,      4,      4,      5,2,      5,      3.5,      5,      5,      6,      1,    6,      3.5,      6,      6,};          
      int o=0;    
while(o<dbpos.length){          
    Double d[] = new Double[2];
          for (t = 'A'; t <= 'H'; t++)
          { 
              String str ="";
              for(char i='1';i<'4';i++){
                  str = String.valueOf(t)+String.valueOf(i);
                  d[0]=dbpos[o];
                  d[1]=dbpos[o+1];
                  o=o+2;
                  System.out.println("str ="+str+"  "+ " d ="+d[1]);
                  hCoinPos.put(str,d);
              }
           //   str= (char)(a+'0');;
          
          }
}
          
          return hCoinPos;
   }
    }

    

