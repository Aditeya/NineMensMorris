/*
 * Copyright (C) 2021 LENOVO
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

import java.util.HashMap;
import javafx.geometry.HPos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import static ninemensmorris.NMMApplication.HEIGHT;
import static ninemensmorris.NMMApplication.POS_SIZE;
import static ninemensmorris.NMMApplication.WIDTH;

class BoardComp {

    String whatComp;
    String Pos;

    public BoardComp() {
    }

    public BoardComp(String whatComp, String Pos) {
        this.whatComp = whatComp;
        this.Pos = Pos;
    }

    public void GlowMill() {
        System.out.println("Glow Mill");
    }

    /**
     * function description
     *
     * @param root Pane
     * @param bcs Hash Map of Board Components;
     */
    public void GenerateBoard(Pane root) {
       HashMap hpos=createSlotHash();
     
       
       /*Creating a concentric square*/
        for (int i = 0; i < 4; i++) {
            Rectangle r1 = new Rectangle(POS_SIZE * HEIGHT - (2 * i * POS_SIZE), POS_SIZE * WIDTH - (2 * i * POS_SIZE));
            r1.setStroke(Color.CADETBLUE);
            r1.setX(i * POS_SIZE);
            r1.setY(i * POS_SIZE);
            r1.setId("boardlines");
            root.getChildren().add(r1);
        }
        /*Joining the lines on the board*/
        String[] lineStr = {"A2", "C2", "D1", "D3", "E1", "E3", "F2", "H2"};
        for (int i = 0; i < lineStr.length; i = i + 2) {
            Line line = new Line(getSlot(lineStr[i])[0] * 100, getSlot(lineStr[i])[1] * 100, getSlot(lineStr[i + 1])[0] * 100, getSlot(lineStr[i + 1])[1] * 100);
            line.setId("boardlines");
            root.getChildren().add(line);
        }
           int numWhite_CoinsLeft =9,numBlack_CoinsLeft =9;
 
        Rectangle BlackCoinSlot = new Rectangle(POS_SIZE-10, POS_SIZE * WIDTH);
        BlackCoinSlot.setX(0);
        BlackCoinSlot.setY(0);
        BlackCoinSlot.setId("coinHolder");
        root.getChildren().add(BlackCoinSlot);
        Rectangle WhiteCoinSlot = new Rectangle(POS_SIZE, POS_SIZE * WIDTH-10);
       WhiteCoinSlot.setX(600);
       WhiteCoinSlot.setY(0);
       WhiteCoinSlot.setId("coinHolder");
        root.getChildren().add(WhiteCoinSlot);
        for(int i=0;i<numBlack_CoinsLeft;i++){
            Circle numC = new Circle(45,(600-(30*i)),30 );
            numC.setId("blackFilledSlot");
                    root.getChildren().add(numC);
        }
        for(int i=0;i<numWhite_CoinsLeft;i++){
            Circle numC = new Circle(600+45,(600-(30*i)),30 );
            System.out.println(" ====> "+20+"  "+(600-(30*i)));
            numC.setId("whiteFilledSlot");
                    root.getChildren().add(numC);
        }
        
        
      
        
        
        for (Object objectName : hpos.keySet()) {
            double[] d = (double[]) hpos.get(objectName);
            // Creating slots
            Circle c1 = new Circle(10);
            Text text = new Text(objectName.toString());
            c1.setCenterX(d[0] * POS_SIZE);
            text.setX(d[0] * POS_SIZE);
            text.setY(d[1] * POS_SIZE);
            c1.setCenterY(d[1] * POS_SIZE);
            c1.setFill(Color.CORAL);
            c1.setFill(Color.CORAL);
            c1.setId("slot");
            root.getChildren().addAll(c1);
        }  
    }
    /**
     * Goes through the bcs(Coins) list and places it accordingly.
     * 
     * @param bcs : HashMap of Positions and Contents(Coins)
     * @param root : Pane of GameBoard
     */
    public void CreateWithCoins(HashMap bcs,Pane root){
                   System.out.println("Creating Content with componets is bcs"); 
   System.out.println("BCS = "+bcs.keySet()); 
                            for (Object value : bcs.values()) {
                             //   System.out.println("values "+bcs.keySet());
                                Coin c = (Coin) value;
                                System.out.println("slot = "+c.slot+" type ="+c.getType()+" pos "+c.getPosX());
                              root.getChildren().add(c.ReturnCoin());
                            }
    }
    {    
//    public void setCoins(Pane root,String Scenario,HashMap bcs){
//        //Clear everything On the Board
//         for (Object value : bcs.values()) {
//                         Coin c = (Coin) value;
//                     //    root.getChildren().remove(c.ReturnCoin(bcs));
//                         }
//                switch (Scenario) {
//                    case "Place Anywhere":
////                    c1.setOnMouseClicked(e -> {
////                        System.out.println("Placing Anywhere");
////                        Coin bc = new Coin(MCoinType.WHITE, d[0] * POS_SIZE, d[1] * POS_SIZE, false, objectName.toString());
////                        bcs.put(objectName.toString(), bc);
////                        System.out.println("KeySet" + bcs.keySet());
////                                    });
//
//                    case "Remove":
//                        System.out.println("Remove Coin");
//                   //     bcs.remove(objectName);
//                        //RemoveEverything
//                         for (Object value : bcs.values()) {
//                         Coin c = (Coin) value;
//                         root.getChildren().remove(c.ReturnCoin(bcs));
//                         }
//                        
//                      //  root.getChildren().remove(bcs.get(this.));
//                }
//
//                 
//                
//                for (Object value : bcs.values()) {
//                    Coin c = (Coin) value;
//                    root.getChildren().add(c.ReturnCoin(bcs));
//                }
    }
        
        /**
 * 
   Get A double with pos Indexes on giving String of Pos.
     **/
    public double[] getSlot(String pos) {
        double[] doubleArr = new double[2];
        switch (pos) {
            case "A1":
                doubleArr[0] = 1;
                doubleArr[1] = 1;
                break;
            case "A2":
                doubleArr[0] = 3.5;
                doubleArr[1] = 1;
                break;
            case "A3":
                doubleArr[1] = 1;
                doubleArr[0] = 6;
                break;
            case "B1":
                doubleArr[1] = 2;
                doubleArr[0] = 2;
                break;
            case "B2":
                doubleArr[1] = 2;
                doubleArr[0] = 3.5;
                break;
            case "B3":
                doubleArr[1] = 2;
                doubleArr[0] = 5;
                break;
            case "C1":
                doubleArr[1] = 3;
                doubleArr[0] = 3;
                break;
            case "C2":
                doubleArr[1] = 3;
                doubleArr[0] = 3.5;
                break;
            case "C3":
                doubleArr[1] = 3;
                doubleArr[0] = 4;
                break;
            case "D1":
                doubleArr[1] = 3.5;
                doubleArr[0] = 1;
                break;
            case "D2":
                doubleArr[1] = 3.5;
                doubleArr[0] = 2;
                break;
            case "D3":
                doubleArr[1] = 3.5;
                doubleArr[0] = 3;
                break;
            case "E1":
                doubleArr[1] = 3.5;
                doubleArr[0] = 4;
                break;
            case "E2":
                doubleArr[1] = 3.5;
                doubleArr[0] = 5;
                break;
            case "E3":
                doubleArr[1] = 3.5;
                doubleArr[0] = 6;
                break;
            case "F1":
                doubleArr[1] = 4;
                doubleArr[0] = 3;
                break;
            case "F2":
                doubleArr[1] = 4;
                doubleArr[0] = 3.5;
                break;
            case "F3":
                doubleArr[1] = 4;
                doubleArr[0] = 4;
                break;
            case "G1":
                doubleArr[1] = 5;
                doubleArr[0] = 2;
                break;
            case "G2":
                doubleArr[1] = 5;
                doubleArr[0] = 3.5;
                break;
            case "G3":
                doubleArr[1] = 5;
                doubleArr[0] = 5;
                break;
            case "H1":
                doubleArr[1] = 6;
                doubleArr[0] = 1;
                break;
            case "H2":
                doubleArr[1] = 6;
                doubleArr[0] = 3.5;
                break;
            case "H3":
                doubleArr[1] = 6;
                doubleArr[0] = 6;
                break;
            default:
                doubleArr[0] = 0;
                doubleArr[1] = 0;
        }
        return doubleArr;
    }
    /**
     * Create a SlotHash to set Components
     * @return 
     */
    public HashMap createSlotHash() {
        HashMap<String, double[]> hCoinPos = new HashMap<String, double[]>();
        char t;
        for (t = 'A'; t <= 'H'; t++) {
            String str = "";
            for (char i = '1'; i < '4'; i++) {
                str = String.valueOf(t) + String.valueOf(i);
                double d[] = doublePosValues(str);
                //   System.out.println("str =" +") "+ str + "  " + " d =" + d[0] + " " + d[1]);
                hCoinPos.put(str, d);
            }
        }
        return hCoinPos;
    }
    public double[] doublePosValues(String pos) {
        double[] doubleArr = new double[2];
        switch (pos) {
            case "A1":
                doubleArr[0] = 1;
                doubleArr[1] = 1;
                break;
            case "A2":
                doubleArr[0] = 3.5;
                doubleArr[1] = 1;
                break;
            case "A3":
                doubleArr[1] = 1;
                doubleArr[0] = 6;
                break;
            case "B1":
                doubleArr[1] = 2;
                doubleArr[0] = 2;
                break;
            case "B2":
                doubleArr[1] = 2;
                doubleArr[0] = 3.5;
                break;
            case "B3":
                doubleArr[1] = 2;
                doubleArr[0] = 5;
                break;
            case "C1":
                doubleArr[1] = 3;
                doubleArr[0] = 3;
                break;
            case "C2":
                doubleArr[1] = 3;
                doubleArr[0] = 3.5;
                break;
            case "C3":
                doubleArr[1] = 3;
                doubleArr[0] = 4;
                break;
            case "D1":
                doubleArr[1] = 3.5;
                doubleArr[0] = 1;
                break;
            case "D2":
                doubleArr[1] = 3.5;
                doubleArr[0] = 2;
                break;
            case "D3":
                doubleArr[1] = 3.5;
                doubleArr[0] = 3;
                break;
            case "E1":
                doubleArr[1] = 3.5;
                doubleArr[0] = 4;
                break;
            case "E2":
                doubleArr[1] = 3.5;
                doubleArr[0] = 5;
                break;
            case "E3":
                doubleArr[1] = 3.5;
                doubleArr[0] = 6;
                break;
            case "F1":
                doubleArr[1] = 4;
                doubleArr[0] = 3;
                break;
            case "F2":
                doubleArr[1] = 4;
                doubleArr[0] = 3.5;
                break;
            case "F3":
                doubleArr[1] = 4;
                doubleArr[0] = 4;
                break;
            case "G1":
                doubleArr[1] = 5;
                doubleArr[0] = 2;
                break;
            case "G2":
                doubleArr[1] = 5;
                doubleArr[0] = 3.5;
                break;
            case "G3":
                doubleArr[1] = 5;
                doubleArr[0] = 5;
                break;
            case "H1":
                doubleArr[1] = 6;
                doubleArr[0] = 1;
                break;
            case "H2":
                doubleArr[1] = 6;
                doubleArr[0] = 3.5;
                break;
            case "H3":
                doubleArr[1] = 6;
                doubleArr[0] = 6;
                break;
            default:
        }
        return doubleArr;
    }
}
