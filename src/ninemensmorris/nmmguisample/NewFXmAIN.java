/* 
<<<<<<< HEAD
 * Copyright (C) 2021 elton
=======
 * Copyright (C) 2021 aditeya
>>>>>>> gui
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
package ninemensmorris.nmmguisample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ninemensmorris.enums.MCoinType;

/**
 * NMM Client Application
 *
 * <<<<<<< HEAD @a
 *
 *
 * uthor eltojaro ======= >>>>>>> gui
 */
public class NewFXmAIN extends Application {

    Scanner input = new Scanner(System.in); // Scanner for input, might be changed in GUI section

    //Gui Component Sizing - For Scaling if needed
    public static final int POS_SIZE = 100;
    public static final int WIDTH = 7;
    public static final int HEIGHT = 7;
    public HashMap<String, Coin> bcs = new HashMap<>();
    BoardComp boardcomp = new BoardComp();
    //Rooms - To choose,Show Availibility and select Rooms
    public ArrayList<RoomsGUI> arrRoomSlot = new ArrayList<RoomsGUI>();
    RoomsGUI rmGUI = new RoomsGUI();
    int numWhite_CoinsLeft = 9, numBlack_CoinsLeft = 9;
    int SelectedRoomNum = -1;

    MCoinType player;
    Label lbInstruct = new Label("");
    

//Creating Board in Screen    
    private Parent createContent() {
        System.out.println("Creating COn");
        Pane root = new Pane();
        root.setPrefSize(WIDTH * POS_SIZE, HEIGHT * POS_SIZE);
        BoardComp bc = new BoardComp();
        bc.GenerateBoard(root, numBlack_CoinsLeft, numWhite_CoinsLeft);
        bc.CreateWithCoins(bcs, root);
        return root;
    }

    @Override
    public void start(Stage primaryStage) {
//Scene 3 - Game Board
        Button btnend_game = new Button("End Game");
        Button btnaddCoin = new Button(" aDD BTN");
        HBox hbMenu = new HBox(btnend_game, btnaddCoin);
        hbMenu.setId("exitMenu");
        hbMenu.setAlignment(Pos.TOP_RIGHT);
        VBox root = new VBox();
        root.setId("vbox");
        Label tGuide = new Label("Let's Play");
        Label lbPlayerName = new Label("Player 1");

        Button btnStat = new Button("");
         double d[] = boardcomp.getSlot("A2");
         double d1[] = boardcomp.getSlot("A1");
         double d2[] = boardcomp.getSlot("A3");

                    MCoinType clientPlayerType = MCoinType.WHITE;
                    Coin placingnewCoin = new Coin(MCoinType.EMPTY, clientPlayerType, "A2", d[0] * POS_SIZE, d[1] * POS_SIZE,"Place Anywhere" , bcs);
                    Coin pc1 = new Coin(MCoinType.EMPTY, clientPlayerType, "A1", d1[0] * POS_SIZE, d1[1] * POS_SIZE,"Place Anywhere" , bcs);
                    Coin pc2 = new Coin(MCoinType.EMPTY, clientPlayerType, "A3", d2[0] * POS_SIZE, d2[1] * POS_SIZE,"Place Anywhere" , bcs);
                    
                    System.out.println("In Client >" + placingnewCoin.getType());
                    bcs = placingnewCoin.getBcs();
                    for (Object value : bcs.values()) {
                        Coin c = (Coin) value;
                        System.out.println("Place Anywhere slot = " + c.slot + " type =" + c.getType());
                    }
                    AddCompTobsc(placingnewCoin);
        root.getChildren().addAll(hbMenu, tGuide, lbInstruct, btnStat, createContent(), lbPlayerName);
        btnaddCoin.setOnAction(e -> {
            String Scenario = "Place Anywhere";
            switch (Scenario) {
                case "Place Anywhere":  //Place slots whereever available
         //           double d1[] = boardcomp.getSlot("A2");
                    MCoinType clientPlayerType1 = MCoinType.WHITE;
                    Coin placingnewCoin1 = new Coin(MCoinType.EMPTY, clientPlayerType1, "A2", d1[0] * POS_SIZE, d1[1] * POS_SIZE, Scenario, bcs);
                    System.out.println("In Client >" + placingnewCoin1.getType());
                    bcs = placingnewCoin1.getBcs();
                    for (Object value : bcs.values()) {
                        Coin c = (Coin) value;
                        System.out.println("Place Anywhere slot = " + c.slot + " type =" + c.getType());
                    }
                    AddCompTobsc(placingnewCoin1);
                    root.getChildren().removeAll(root.getChildren());  //To avoid DuplicateChildren
                    root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), lbPlayerName);        //Reload  Board
            }
 //           AddCompTobsc(new Coin(MCoinType.WHITE, "A3"));
            System.out.println("btn AddCOinads and Check ");
            root.getChildren().removeAll(root.getChildren());  //To avoid DuplicateChildren
            root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), lbPlayerName);        //Reload  Board
        });
        Scene scene = new Scene(root);

        btnStat.setOnAction(e -> {
            root.getChildren().removeAll(root.getChildren());  //To avoid DuplicateChildren
            root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), lbPlayerName);        //Reload  Board
        });
      

        scene.getStylesheets().add("Resc/NMMBoard.css");
        primaryStage.setTitle("Nine Men's Morris");
        primaryStage.setScene(scene);//chanfe to scene1
        primaryStage.show();
    }

    /**
     * Adds BoardComp to specified slot position and sets PosX and PosY
     * BoardComp should set CoinType and slot
     *
     * @param slot
     * @param bc
     */
    public void AddCompTobsc(Coin bc) {
        boardcomp = new BoardComp();
        double d[] = boardcomp.getSlot(bc.getSlot());
        bc.setPosX(d[0] * POS_SIZE);
        bc.setPosY(d[1] * POS_SIZE);
//                   String [] vldMvs = {"A2","B3"};
//                   bc.setVldMvs(vldMvs);
        bcs.put(bc.getSlot(), bc);
    }

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    private void printPlayerTurn(MCoinType turn, int msg) {
        switch (msg) {
            case 1:
                System.out.println(turn + " Player, Place a coin.");
                break;
            case 2:
                System.out.println(turn + " Player, Select an opposing coin to be removed.\nEnter 'X' to conceed coin removal");
                break;
            case 3:
                System.out.println(turn + " Player, Select an coin to be moved");
                break;
            default:
                System.out.println("Incorrect Usage. Check Docs.");
                return;
        }

        System.out.println("match regex [A-H]+[1-3]");
    }

}
