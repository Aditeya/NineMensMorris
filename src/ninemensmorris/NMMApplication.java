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
package ninemensmorris;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.networking.NCommand;
import ninemensmorris.networking.NMMClientThread;
import ninemensmorris.networking.NetworkCommand;
import ninemensmorris.networking.demo.NMMClientDemo;

/**
 * NMM Client Application
 *
<<<<<<< HEAD
 * @author eltojaro
=======
>>>>>>> gui
 */
public class NMMApplication extends Application {

    //Gui Component Sizing - For Scaling if needed
    public static final int POS_SIZE = 100;
    public static final int WIDTH = 7;
    public static final int HEIGHT = 7;
    public HashMap<String, Coin> bcs = new HashMap<>();
    BoardComp boardcomp = new BoardComp();
    //Rooms - To choose,Show Availibility and select Rooms
    public ArrayList<RoomsGUI> arrRoomSlot = new ArrayList<RoomsGUI>();
    int[][] dArr_room = null;

    RoomsGUI rmGUI = new RoomsGUI();
    int numWhite_CoinsLeft = 9, numBlack_CoinsLeft = 9;
    int SelectedRoomNum = -1;

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
//Scene 1 - Intro Scene
        Label lb1 = new Label("Welcome to the Game");
        Label tGameTitle = new Label("Nine Mens Morris");
        tGameTitle.setId("gameTitle");
        TextField tf_Name = new TextField();
        tf_Name.setPromptText("Enter Gamer Name");
        Button btn_GoToSelectRooms = new Button("Let's Play");
        VBox layout1 = new VBox(20);
        AnimationComponents animCom = new AnimationComponents();
        animCom.setIntroAnim(layout1, 300, 300, Color.BLUE);
        layout1.setAlignment(Pos.CENTER);
        layout1.setSpacing(POS_SIZE);
        layout1.getChildren().addAll(tGameTitle, tf_Name, lb1, btn_GoToSelectRooms);
        layout1.setId("layout1");
        Scene scene1 = new Scene(layout1);
        scene1.getStylesheets().add("Resc/NMMBoard.css");
//Scene 2 - Select Rooms 
        Label label2 = new Label("Choose your Room");
        Button btn_ChosenRooms = new Button("Let's Play");
        Label tGameTitle1 = new Label("Nine Mens Morris");
        tGameTitle1.setId("gameTitle");
        VBox layout2 = new VBox();
        layout2.setAlignment(Pos.CENTER);
        layout2.setId("layout2");
        layout2.setSpacing(POS_SIZE / 2);
        //Rooms in 2 Rows
        /*Arraging the Rooms to a Tile View with 2 rows and appropriate number of columns*/
        ArrayList rooms = new ArrayList(); //ArrayList of Room GUI Components
        VBox vb_roomView = new VBox();
        HBox hb1 = new HBox();
        HBox hb2 = new HBox();
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
        ArrayList<HBox> arrVbox = new ArrayList<HBox>();//Room Contents GUI components
        arrVbox.add(hb1);
        arrVbox.add(hb2);
        ToggleGroup roomTg = new ToggleGroup();        // Toggle Group for selecting Rooms

        ToggleGroup_ArraySelectionAnimation(roomTg, btn_ChosenRooms);
        vb_roomView.getChildren().addAll(hb1, hb2);
        vb_roomView.getStyleClass().add("vbox");
        layout2.setAlignment(Pos.CENTER);
        Label promtwithGamerName = new Label("Let's play gamer," + tf_Name.getText());
        layout2.getChildren().addAll(tGameTitle1, label2, promtwithGamerName, vb_roomView, new Label(" "), btn_ChosenRooms);
        Scene scene2 = new Scene(layout2);
        scene2.getStylesheets().add("Resc/NMMBoard.css");
//Scene 3 - Game Board
        Button btnend_game = new Button("End Game");
        Button btnaddCoin = new Button(" aDD BTN");
        HBox hbMenu = new HBox(btnend_game, btnaddCoin);
        hbMenu.setId("exitMenu");
        hbMenu.setAlignment(Pos.TOP_RIGHT);
        VBox root = new VBox();
        root.setId("vbox");
        Label tGuide = new Label("Let's Play");
        Label tPlayerName = new Label("Player 1");
        Button btnStat = new Button("Button Start");
        root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), tPlayerName);
        btnaddCoin.setOnAction(e -> {
            String Scenario = "Place Anywhere";
            switch (Scenario) {
                case "Place Anywhere":  //Place slots whereever available
                    System.out.println("dasfsa");
                    double d[] = boardcomp.getSlot("A2");
                    MCoinType clientPlayerType = MCoinType.WHITE;
                    Coin placingnewCoin = new Coin(MCoinType.EMPTY, clientPlayerType, "A2", d[0] * POS_SIZE, d[1] * POS_SIZE, Scenario, bcs);
                    System.out.println("In Client >" + placingnewCoin.getType());
                    bcs = placingnewCoin.getBcs();
                    for (Object value : bcs.values()) {
                        Coin c = (Coin) value;
                        System.out.println("Place Anywhere slot = " + c.slot + " type =" + c.getType());
                    }
                    AddCompTobsc(placingnewCoin);
                    root.getChildren().removeAll(root.getChildren());  //To avoid DuplicateChildren
                    root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), tPlayerName);        //Reload  Board
            }
            AddCompTobsc(new Coin(MCoinType.WHITE, "A3"));
            System.out.println("btn AddCOinads and Check ");
            root.getChildren().removeAll(root.getChildren());  //To avoid DuplicateChildren
            root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), tPlayerName);        //Reload  Board
        });
        Scene scene = new Scene(root);

        Go_Selected_Room(btn_ChosenRooms, scene, primaryStage, rooms, roomTg);

        btnStat.setOnAction(e -> {
            root.getChildren().removeAll(root.getChildren());  //To avoid DuplicateChildren
            root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), tPlayerName);        //Reload  Board

        });

        //Button Transition
        /* Enter Name and Going to Select Room*/
        btn_GoToSelectRooms.setOnAction(e -> {
            String concat = "";
            concat = promtwithGamerName.getText().concat(" " + tf_Name.getText());
            tf_Name.clear();
            promtwithGamerName.setText(concat);
            try {
                arrRoomSlot.clear();
                GetRoomsFromServer(arrRoomSlot);
                int Count = 0;
                for (int j = 0; j < arrVbox.size(); j++) { //ArrSlot == ArrayList of RoomsGUI
                    arrVbox.get(j).getChildren().removeAll(arrVbox.get(j).getChildren()); //Adding components to appropriate VBox
                    for (int i = 0; i < arrRoomSlot.size() / 2; i++) {//Dividing into 2 Rows
                        RadioButton enterUser = new RadioButton("Room " + (Count + 1) + " ");
                        enterUser.setToggleGroup(roomTg); //Enbling toggle function to Radio Buttons
                        enterUser.setId("btnplus");
                        rmGUI = arrRoomSlot.get(Count);
                        Count++;
                        rmGUI.setIfFilled();
                        HBox hb = new HBox(rmGUI.getWhiteSlot(), rmGUI.getBlackSlot());  //Setting Coin Slots in each Room.
                        hb.setAlignment(Pos.CENTER);
                        hb.getStyleClass().add("hbox");
                        VBox v4 = new VBox(new HBox(enterUser), hb);
                        v4.setId("room");
                        v4.setAlignment(Pos.CENTER);
                        rooms.add(v4);
                        arrVbox.get(j).getChildren().add(v4); //Adding components to appropriate VBox
                    }
                }
                primaryStage.setScene(scene2);
            } catch (Exception ex) {
                Alert a1 = new Alert(Alert.AlertType.ERROR, "Sorry! \n Server Not Available.", ButtonType.OK);
                a1.showAndWait();
            }
        });

        btnend_game.setOnAction(e -> primaryStage.setScene(scene1));
        scene.getStylesheets().add("Resc/NMMBoard.css");
        primaryStage.setTitle("Nine Men's Morris");
        primaryStage.setScene(scene1);//chanfe to scene1
        primaryStage.show();
    }
/**
 * 
 * @param btn_ChosenRooms
 * @param scene
 * @param primaryStage
 * @param arrVbox
 * @param roomTg 
 */
    public void Go_Selected_Room(Button btn_ChosenRooms, Scene scene, Stage primaryStage, ArrayList arrVbox, ToggleGroup roomTg) {
        /* Selected Room and Going to Game*/
        btn_ChosenRooms.setOnAction(e -> {
            arrVbox.clear();
            RadioButton rb = (RadioButton) roomTg.getSelectedToggle();
            try {
                if (rb != null) {
                    Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    // Client CLI interface
                    if (choose(ois, oos, SelectedRoomNum)) {
                        // Create and start the game thread after room is chosen.
                        NMMClientThread game = new NMMClientThread(socket);
                        Thread thread = new Thread(game);
                        System.out.println("thread "+thread.getName());
                        thread.start();

                        primaryStage.setScene(scene);

                    } else {
                        throw new UnknownHostException();
                    }
                }
            } catch (Exception ex) {
                Alert a1 = new Alert(Alert.AlertType.ERROR, "Sorry Room unavailable,Please try again", ButtonType.OK);
                a1.showAndWait();
            }
        });
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

    public void ToggleGroup_ArraySelectionAnimation(ToggleGroup roomTg, Button btn_ChosenRooms) {
        roomTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
                RadioButton rb = (RadioButton) roomTg.getSelectedToggle();
                if (rb != null) {
                    String s = rb.getText();
                    btn_ChosenRooms.setText(s + " selected");
                    for (RoomsGUI roomsGUI : arrRoomSlot) {
                        roomsGUI.getBlackSlot().setRadius(20);
                        roomsGUI.getBlackSlot().setStrokeWidth(0);
                        roomsGUI.getWhiteSlot().setRadius(20);
                        roomsGUI.getWhiteSlot().setStrokeWidth(0);

                    }
                    SelectedRoomNum = Integer.parseInt(rb.getText().replaceAll("Room ", "").trim());
                    System.out.println("Selected Room Num  " + SelectedRoomNum);
                    RoomsGUI rm2 = arrRoomSlot.get(SelectedRoomNum - 1);
                    if (rm2.isWhiteFilled()) { //White is filled
                        System.out.println("Black selected ");
                        rm2.getBlackSlot().setRadius(25);
                        rm2.getBlackSlot().setStrokeWidth(5);
                        rm2.getBlackSlot().setStroke(Color.BLACK);
                    } else if (rm2.isBlackFilled() || !(rm2.isBlackFilled() && rm2.isWhiteFilled())) {
                        //White is HIghlighted
                        //      System.out.println("white selected ");
                        rm2.getWhiteSlot().setRadius(25);
                        rm2.getWhiteSlot().setStrokeWidth(5);
                        rm2.getWhiteSlot().setStroke(Color.WHITE);
                    } else if (rm2.isBlackFilled() && rm2.isWhiteFilled()) { //Full Room
                        System.out.println("Not  selected ");
                    }
                }
            }

        });
    }

    /**
     * Returns Room Availability from Server
     *
     * @param ois
     * @param oos
     * @return
     */
    private static int[][] list(ObjectInputStream ois, ObjectOutputStream oos) {
        // Create command
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.LIST_ROOMS);

        try {
            // Send command
            oos.reset();
            oos.writeObject(command);
            NCommand reply = (NCommand) ois.readObject();
            // printRooms(reply.getRooms());
            return reply.getRooms();
            // Print room list from reply
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    /**
     * *
     * Adds Rooms from Server to Arr
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public void GetRoomsFromServer(ArrayList array) throws UnknownHostException, IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        int[][] room = list(ois, oos);

        System.out.println(Arrays.deepToString(room));

        for (int row = 0; row < room.length; row++) {
            //Set room number + 1;
            int col = 0;
            rmGUI = new RoomsGUI(new Circle(20), new Circle(20));
            if (room[row][col] != 0) {
                rmGUI.setWhiteFilled(true);
            }
            col++;
            if (room[row][col] != 0) {
                rmGUI.setBlackFilled(true);
            }
            array.add(row, rmGUI);
        }
    }

    private static boolean choose(ObjectInputStream ois, ObjectOutputStream oos, int roomSelected) {
        boolean success = true;
        // Create the command request for the room
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.CHOOSE_ROOM);

        // Ascount_Roomfor the room number and set it in the request
        int room = roomSelected - 1;
        System.out.println("Selected Room Num  " + roomSelected);

        System.out.println("Room Selected is " + room);
        command.setRoom(room);

        try {
            // Send the request and recieve reply
            oos.reset();
            oos.writeObject(command);
            NCommand reply = (NCommand) ois.readObject();

            // Process reply, either reply with confirm if success or print room and exit
            switch (reply.getCommand()) {
                case ROOM_ACQ:
                    reply.setCommand(NetworkCommand.CONFIRM);
                    oos.reset();
                    oos.writeObject(reply);
                    return success;
                case ROOM_FULL:
                    System.out.println("Rooms Full. Choose Another:");
                //        printRooms(reply.getRooms());
                default:
                    success = false;
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return success;
    }

    /**
     * Prints the rooms with each line per room with room numbers.
     *
     * @param rooms Double Array to be printed out
     */
    //</editor-fold>
    public static void test2(int[] test) {
        test[1] = 19;
    }

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }
}
