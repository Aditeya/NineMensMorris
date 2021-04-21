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

    int numWhite_CoinsLeft = 0, numBlack_CoinsLeft = 0;
    Label tGuide = new Label("Let's Play");
    public HashMap<String, Coin> bcs = new HashMap<>();
    Label lbPlayerName = new Label("Player 1");
    TextField tfMove = new TextField();
    TextField tfFrom = new TextField();

    private Parent clearContent() {
        VBox v_root = new VBox();
        return v_root;
    }
    Button btnStat = new Button("");

    private Parent createContent(String guidetext, boolean tfMoveVisible, boolean tfFromVisible) {
        System.out.println("Creating COn");
        tGuide.setText(guidetext);
        tfMove.setVisible(tfMoveVisible);
        VBox v_root = new VBox();
        Pane root = new Pane();
        tfMove.setPromptText("Enter Move");
        tfMove.getStyleClass().add("text-field");
        HBox hbMenu = new HBox(tfMove,new Label("  "), tfFrom, btnStat);
        Label lbInstruct = new Label("");
        hbMenu.setId("exitMenu");
        hbMenu.setAlignment(Pos.TOP_RIGHT);
        v_root.setId("vbox");
        root.setPrefSize(WIDTH * POS_SIZE, HEIGHT * POS_SIZE);
        BoardComp bc = new BoardComp();
        bc.GenerateBoard(root, numBlack_CoinsLeft, numWhite_CoinsLeft);
        v_root.getChildren().add(lbPlayerName);
        bc.CreateWithCoins(bcs, root);
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
            NMMApplication.scene.setNodeOrientation(NodeOrientation.INHERIT);
            while (true) {
                // Receive board and print it out
                NMMboard board = (NMMboard) this.input.take();                //   NMMLogic.cmdPrint(board.getNmmBoard(), PrintType.VALUE);
                NMMCoin[][] coins = board.getNmmBoard();
                NMMLogic nmm = new NMMLogic();
                Load_Display_BoardFromServer(coins);                // Notify if input is valid
                MCoinType turn = board.getTurn();// Take input and send, if it is players turn

                if (board.getWinner() == MCoinType.EMPTY) {

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
                    System.out.println("you are " + player.toString());
                    //Show Board
                    NMMApplication.scene.setRoot(clearContent());
                    NMMApplication.scene.setRoot(createContent("Your Turn!", true, showFromtf));
                    btnStat.setOnAction(e -> {
                        String Move = tfMove.getText().trim().toUpperCase();
                        if (Move.matches("[A-H]+[1-3]") || Move.equals("X")) {
                            switch (board.getiType()) {
                                case NONE:
                                    break;
                                case PLACE:
                         
                                    // NMMApplication.scene.getRoot().getOnKeyPressed();
                                    System.out.println("PLACE!!");
                                    // input.add(new NMMCoin(tfMove.getText().trim()));
                                    break;
                                case REMOVE:
                                    System.out.println("Remove");
                                    break;
                                case MOVE:
                                    System.out.println("Move");
                                    String[] slots = new String[2];
                                    slots[0] = tfFrom.getText().trim().toUpperCase();
                                    slots[1] = tfMove.getText().trim().toUpperCase();
                                    ReduceCoin(turn, board);
                                    NMMApplication.scene.setRoot(clearContent());
                                    NMMApplication.scene.setRoot(createContent("", true,true));
                                    output.add(tfMove.getText().trim());
                                    tfMove.clear();
                                    break;
                                default:
                            }
                            //If the input type is none, pnly for place
                            if (board.getiType() != InputType.NONE || board.getiType() != InputType.MOVE) {
                                ReduceCoin(turn, board);
                                NMMApplication.scene.setRoot(clearContent());
                                NMMApplication.scene.setRoot(createContent("Waiting For Opponent", false, true));
                                output.add(tfMove.getText().trim());
                                tfMove.clear();
                            }
                        } else {
                            NMMApplication.scene.setRoot(clearContent());
                            NMMApplication.scene.setRoot(createContent("Invalid Input. Try Again.", true, true));
                        }
                    });

//                        this.output.add(new NMMmove((String) input.take()));
//                                oos.writeObject(new NMMmove((String) input.take()));
                } else {
                    System.out.println("next Turn , Not your Turn");
                    NMMApplication.scene.setRoot(clearContent());
                    NMMApplication.scene.setRoot(createContent("Waiting For Opponent", false, false));
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(NMMGUIBoardThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Adds BoardComp to specified slot position and sets PosX and PosY
     * BoardComp should set CoinType and slot
     *
     * @param slot
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
                int idx[] = NMMLogic.slotLkUp(str); //
                //      System.out.printf("index= %d:%d\n", idx[0], idx[1]); //should be 0:0 for A1
                NMMCoin coin = coins[idx[0]][idx[1]]; //gets coin A1 
                //    System.out.println("coin at " + str + " = " + coin.getCoin());
                AddCompTobsc(new Coin(coin.getCoin(), str, "Place On Click")); //Added to ArrayList
            }
        }
    }

    public void ReduceCoin(MCoinType turn, NMMboard board) {
        numBlack_CoinsLeft = board.getMenLeft();
        numWhite_CoinsLeft = board.getMenLeft();;
    }
}
