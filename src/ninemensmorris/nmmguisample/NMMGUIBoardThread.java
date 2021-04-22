/*
 * Copyright (C) 2021 aditeya
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

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ninemensmorris.NMMCoin;
import ninemensmorris.NMMLogic;
import static ninemensmorris.nmmguisample.NMMApplication.HEIGHT;
import static ninemensmorris.nmmguisample.NMMApplication.POS_SIZE;
import static ninemensmorris.nmmguisample.NMMApplication.WIDTH;
import ninemensmorris.enums.InputType;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PrintType;
import ninemensmorris.networking.NMMboard;

/**
 *
 * @author aditeya
 */
public class NMMGUIBoardThread extends Thread {

    private final LinkedBlockingQueue input;
    private final LinkedBlockingQueue output;

    private final BoardComp guiboard;

    public NMMGUIBoardThread(LinkedBlockingQueue input, LinkedBlockingQueue output, BoardComp guiboard) {
        this.input = output;
        this.output = input;
        this.guiboard = guiboard;
    }

    int numWhite_CoinsLeft = 9, numBlack_CoinsLeft = 9;
    Label tGuide = new Label("Let's Play");
    public HashMap<String, Coin> bcs = new HashMap<>();
    Label lbPlayerName = new Label("Player 1");
    TextField tfMove = new TextField();

    private Parent clearContent() {
        VBox v_root = new VBox();
        return v_root;
    }
    Button btnStat = new Button("");

    /**
     * 
     * Load Components on the Board     * 
     * @param guidetext
     * @param tfMoveVisible
     * @return 
     */
    
    private Parent createContent(String guidetext, boolean tfMoveVisible) {
        //  System.out.println("Creating COn");
        tGuide.setText(guidetext);
        tfMove.setVisible(tfMoveVisible);
        tfMove.setBackground(Background.EMPTY);
        VBox v_root = new VBox();
        Pane root = new Pane();
        tfMove.setPromptText("Enter Move");
        tfMove.getStyleClass().add("text-field");
        HBox hbMenu = new HBox(tfMove, new Label("  "), btnStat);
        Label lbInstruct = new Label("");
        hbMenu.setId("exitMenu");
        hbMenu.setAlignment(Pos.TOP_RIGHT);
        v_root.setId("vbox");
        root.setPrefSize(WIDTH * POS_SIZE, HEIGHT * POS_SIZE);
        BoardComp bc = new BoardComp();
        bc.GenerateBoard(root, numBlack_CoinsLeft, numWhite_CoinsLeft);
        v_root.getChildren().add(lbPlayerName);
        bc.CreateWithCoins(bcs, root);//Load All Coins on the board
        v_root.getChildren().addAll(hbMenu, tGuide, root, lbInstruct);
        return v_root;
    }
    BoardComp boardComp = new BoardComp();
    MCoinType player;

    @Override
    public void run() {
        try {
            boolean showFromtf = false;
            player = (MCoinType) this.input.take();
            lbPlayerName.setText(player.toString());

            setNameCol();

            NMMApplication.scene.setNodeOrientation(NodeOrientation.INHERIT);
            while (true) {
                // Receive board and print it out
                NMMboard board = (NMMboard) this.input.take(); //  NMMLogic.cmdPrint(board.getNmmBoard(), PrintType.VALUE);
                NMMCoin[][] coins = board.getNmmBoard();
                NMMLogic nmm = new NMMLogic();
                Load_Display_BoardFromServer(coins);                // Notify if input is valid
                MCoinType turn = board.getTurn();// Take input and send, if it is players turn

                if (board.getWinner() == MCoinType.EMPTY) {
                    //   System.out.println(" No Winners Yet ");
                } else if (board.getWinner() == turn) {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, "You WIN!!", ButtonType.OK);
                    a1.showAndWait();
                    break;
                } else {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, "You LOOSE.", ButtonType.OK);
                    a1.showAndWait();
                    break;
                }
                if (turn == player) {
                    

                    System.out.println("you are " + player.toString());                    //Show Board
                    NMMApplication.scene.setRoot(clearContent());
                    NMMApplication.scene.setRoot(createContent("Your Turn!," + getScenario(board), true));

                    System.out.println(" player " + board.getiType().toString());

                    
                    
                     tfMove.setOnKeyPressed(e -> {
        if (e.getCode() == KeyCode.ENTER) {
           
            String Move = tfMove.getText().trim().toUpperCase();

                        boolean takeInput = false;
                        boolean takeFromToInput = false;

                        switch (board.getiType()) {
                            case NONE:
                                System.out.println("NONE");
                                break;
                            case PLACE:
                                System.out.println("PLACE!!");

                                if (Move.matches("[A-H]+[1-3]")) {
                                    takeInput = true;
                                    ReduceCoin(turn);

                                } else {
                                    NMMApplication.scene.setRoot(clearContent());
                                    NMMApplication.scene.setRoot(createContent("Invalid Input. Try Again.", true));
                                    //error
                                }
                                break;
                            case REMOVE:

                                System.out.println("Remove");
                                if (Move.matches("[A-H]+[1-3]") || Move.equals("X")) {
                                    takeInput = true;
                                    if (!Move.equals("X")) {
                                        if (player == MCoinType.WHITE) {
                                            ReduceCoin(MCoinType.BLACK);
                                        } else {
                                            ReduceCoin(MCoinType.WHITE);
                                        }
                                    }
                                } else {
                                    NMMApplication.scene.setRoot(clearContent());
                                    NMMApplication.scene.setRoot(createContent("Invalid Input. Try Again.", true));
                                    //error
                                }
                                break;
                            case MOVE:

                                if (Move.matches("[A-H]+[1-3]\\s[A-H]+[1-3]")) {
                                    System.out.println("Move");
                                    takeFromToInput = true;
                                } else {
                                    NMMApplication.scene.setRoot(clearContent());
                                    NMMApplication.scene.setRoot(createContent("Invalid Input. Try Again.", true));
                                    //error handling
                                }
                                break;

                            default:
                        }
                        if (takeFromToInput) {

                            String[] slots = Move.split("\\s");
                            output.add(slots[0]);
                            output.add(slots[1]);
                            System.out.println(" PRINT " + slots[0] + "  " + slots[1]);
                            tfMove.clear();
                            NMMApplication.scene.setRoot(clearContent());
                            NMMApplication.scene.setRoot(createContent("", true));
                        }

                        //If the input type is none, pnly for place
                        if (takeInput) {
                            NMMApplication.scene.setRoot(clearContent());
                            NMMApplication.scene.setRoot(createContent("Waiting For Opponent", false));
                            output.add(Move);
                            tfMove.clear();
                        }
        }
    });
                  
                } else {

                    System.out.println("next Turn, Not your Turn");
                    NMMApplication.scene.setRoot(clearContent());
                    NMMApplication.scene.setRoot(createContent("Waiting For Opponent", false));
                    if (turn == MCoinType.BLACK) {
                        numWhite_CoinsLeft=numWhite_CoinsLeft-1;
                    } else if(turn ==MCoinType.WHITE) {
                        numBlack_CoinsLeft=numBlack_CoinsLeft-1;
                    }
                }

            }

        } catch (Exception ex) {
        }
    }

    /**
     * Adds BoardComp to specified slot position and sets PosX and PosY
     * BoardComp should set CoinType and slot
     *
     * @param bc
     */
    public void AddCompTobsc(Coin bc) {
        BoardComp boardcomp = new BoardComp();
        double d[] = boardcomp.getSlot(bc.getSlot());
        bc.setPosX(d[0] * POS_SIZE);
        bc.setPosY(d[1] * POS_SIZE);
        bcs.put(bc.getSlot(), bc);
    }

    public void Load_Display_BoardFromServer(NMMCoin[][] coins) {
        char t;
        for (t = 'A'; t <= 'H'; t++) {
            String str = "";
            for (char i = '1'; i < '4'; i++) {
                str = String.valueOf(t) + String.valueOf(i);
                int idx[] = NMMLogic.slotLkUp(str);
                //      System.out.printf("index= %d:%d\n", idx[0], idx[1]); //should be 0:0 for A1
                NMMCoin coin = coins[idx[0]][idx[1]]; //gets coin A1 
                //    System.out.println("coin at " + str + " = " + coin.getCoin());
                AddCompTobsc(new Coin(coin.getCoin(), str, "PLACE", coin.isMilled())); //Added to ArrayList
            }
        }
    }
/**
 * Return Appropriate Instructions
 * @param board
 * @return 
 */
    public String getScenario(NMMboard board) {
        String Scenario = "";
        switch (board.getiType()) {
            case NONE:
                System.out.println("NONE");
                break;
            case PLACE:
                Scenario = " Place a Coin";
                break;
            case REMOVE:
                Scenario = " Remove a Coin from Opponent \n Enter 'X' to conceed coin removal";
                break;
            case MOVE:
                System.out.println("Move");
                Scenario = " Move a Coin";
                break;
            default:
        }
        return Scenario;
    }

    public void setNameCol() {
        if (lbPlayerName.getText() == "WHITE") {
            lbPlayerName.setId("whiteName");
        } else {
            lbPlayerName.setId("blackName");
        }
    }

    public void ReduceCoin(MCoinType turn) {
    System.out.println("Reducing ");

        if (player == turn) {
            if (player == MCoinType.WHITE) {
                System.out.println("Reducing blakc");
                numBlack_CoinsLeft = numBlack_CoinsLeft - 1;
            } else if (player == MCoinType.BLACK) {
                System.out.println("Reducing white");
                numWhite_CoinsLeft = numWhite_CoinsLeft - 1;
            }
        }
    }
}
