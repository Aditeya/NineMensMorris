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
package ninemensmorris;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import static ninemensmorris.NMMApplication.HEIGHT;
import static ninemensmorris.NMMApplication.POS_SIZE;
import static ninemensmorris.NMMApplication.WIDTH;
import ninemensmorris.enums.InputType;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PrintType;
import ninemensmorris.networking.NMMboard;
import ninemensmorris.networking.NMMmove;

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

    public HashMap<String, Coin> bcs = new HashMap<>();

    private Parent createContent() {
        System.out.println("Creating COn");
        Pane root = new Pane();
        root.setPrefSize(WIDTH * POS_SIZE, HEIGHT * POS_SIZE);
        BoardComp bc = new BoardComp();
        bc.GenerateBoard(root, numBlack_CoinsLeft, numWhite_CoinsLeft);
        bc.CreateWithCoins(bcs, root);
        return root;
    }

    MCoinType player;

    @Override
    public void run() {
        System.out.println("Running");
        try {
            player = (MCoinType) this.input.take();

            System.out.println("player == " + player);

            while (true) {
                // Receive board and print it out
                NMMboard board = (NMMboard) this.input.take();
                NMMLogic.cmdPrint(board.getNmmBoard(), PrintType.VALUE);

                // Notify if input is valid
                if (board.isWrongMove()) {
                    System.out.println("Invalid move, Try again");
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
