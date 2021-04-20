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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ninemensmorris.NMMLogic;
import static ninemensmorris.nmmguisample.NMMApplication.HEIGHT;
import static ninemensmorris.nmmguisample.NMMApplication.POS_SIZE;
import static ninemensmorris.nmmguisample.NMMApplication.WIDTH;
import ninemensmorris.enums.InputType;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PrintType;
import ninemensmorris.networking.NMMboard;
import ninemensmorris.networking.NMMmove;
import static ninemensmorris.nmmguisample.NMMApplication.createContent;

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
    private Parent createContent() {
        System.out.println("Creating COn");
        VBox v_root = new VBox();

        Pane root = new Pane();
         Button btnend_game = new Button("End Game");
        Button btnaddCoin = new Button(" aDD BTN");
        HBox hbMenu = new HBox(btnend_game, btnaddCoin);
          
      
            Label lbInstruct = new Label("");
             Button btnStat = new Button("");

        hbMenu.setId("exitMenu");
        hbMenu.setAlignment(Pos.TOP_RIGHT);
        v_root.setId("vbox");
         root.setPrefSize(WIDTH * POS_SIZE, HEIGHT * POS_SIZE);
        BoardComp bc = new BoardComp();
        bc.GenerateBoard(root, numBlack_CoinsLeft, numWhite_CoinsLeft);
        v_root.getChildren().add(lbPlayerName);
        bc.CreateWithCoins(bcs, root);
        v_root.getChildren().addAll(hbMenu, tGuide,root ,lbInstruct, btnStat);
        return v_root;
    }

    MCoinType player;

    @Override
    public void run() {
        System.out.println("Running");
        try {
            player = (MCoinType) this.input.take();
            System.out.println("player == " + player);
            lbPlayerName.setText(player.toString());
            NMMApplication.scene.setRoot(createContent());
            NMMApplication.scene.setNodeOrientation(NodeOrientation.INHERIT);
            while (true) {
                // Receive board and print it out
                NMMboard board = (NMMboard) this.input.take();
                NMMLogic.cmdPrint(board.getNmmBoard(), PrintType.VALUE);
                // Notify if input is valid
                if (board.isWrongMove()) {
                    System.out.println("Invalid move, Try again");
                    tGuide.setText("Invalid move, Try again");
                }

                MCoinType turn = board.getTurn();
                // Take input and send, if it is players turn
                if (turn == player) {

                    switch (board.getiType()) {
                        case NONE:
                            break;
                        case PLACE:
                            System.out.println("Place");//output.add(InputType.PLACE);
                            //printPlayerTurn(turn, 1);
                            break;
                        case REMOVE:
                            System.out.println("Remove");
                            //output.add(InputType.REMOVE);
                            //printPlayerTurn(turn, 2);
                            break;
                        case MOVE:
                            System.out.println("Move");
                            //output.add(InputType.MOVE);
                            //printPlayerTurn(turn, 3);
                            String[] slots = new String[2];
                            slots[0] = (String) input.take();

                            //System.out.println(turn + " Player, Move coin " + slots[0] + " to? match regex [A-H]+[1-3]");
                            slots[1] = (String) input.take();

                            break;
                        default:
                    }

                    if (board.getiType() != InputType.NONE || board.getiType() != InputType.MOVE) {
                        System.out.print("Enter Move: ");
                        this.output.add(new NMMmove((String) input.take()));
//                                oos.writeObject(new NMMmove((String) input.take()));
                    }
                }
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(NMMGUIBoardThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
