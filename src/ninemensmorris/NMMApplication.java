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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import ninemensmorris.networking.NetworkCommand;
import ninemensmorris.networking.demo.NMMClientDemo;

/**
 *
 * @author aditeya
 */
public class NMMApplication extends Application {

    //Gui Component Sizing - For Scaling if needed
    public static final int POS_SIZE = 100;
    public static final int WIDTH = 7;
    public static final int HEIGHT = 7;
    public HashMap<String,Coin> bcs = new HashMap<>();
    //Rooms - To choose,Show Availibility and select Rooms
    public ArrayList<RoomsGUI> arrRoomSlot = new ArrayList<RoomsGUI>();
    int count_Room = 1;
    RoomsGUI rm = new RoomsGUI();
    
    int numWhite_CoinsLeft =9,numBlack_CoinsLeft =9;
    
    
    
//Creating Board in Screen    
    private Parent createContent() {
        System.out.println("Creating COn");
        Pane root = new Pane();
        root.setPrefSize(WIDTH * POS_SIZE, HEIGHT * POS_SIZE);
        BoardComp bc = new BoardComp();
        bc.GenerateBoard(root);
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
        Button btn1 = new Button("Let's Play");
        VBox layout1 = new VBox(20);
        AnimationComponents animCom = new AnimationComponents();
        animCom.setIntroAnim(layout1, 300, 300, Color.BLUE);
        layout1.setAlignment(Pos.CENTER);
        layout1.setSpacing(POS_SIZE);
        layout1.getChildren().addAll(tGameTitle, tf_Name, lb1, btn1);
        layout1.setId("layout1");
        Scene scene1 = new Scene(layout1);
        scene1.getStylesheets().add("Resc/NMMBoard.css");
//Scene 2 - Select Rooms 
        Label label2 = new Label("Choose your Room");
        Button btn2 = new Button("Let's Play");
        Label tGameTitle1 = new Label("Nine Mens Morris");
        tGameTitle1.setId("gameTitle");
        VBox layout2 = new VBox();
        layout2.setAlignment(Pos.CENTER);
        layout2.setId("layout2");
        layout2.setSpacing(POS_SIZE / 2);
 //Rooms in 2 Rows
        int num_Rooms = 12; //Hard coded, Number of Rooms available at Server
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

        // Toggle Group for selecting Rooms
        ToggleGroup roomTg = new ToggleGroup();
        for (int j = 0; j < arrVbox.size(); j++) {
            for (int i = 0; i < num_Rooms / 2; i++) {
                RadioButton enterUser = new RadioButton("Room " + count_Room + " ");
                enterUser.setToggleGroup(roomTg); //Enbling toggle function to Radio Buttons
                enterUser.setId("btnplus");
                Circle c1 = new Circle(20);
                c1.setId("roomslot");
                Circle c2 = new Circle(20);
                c2.setId("roomslot");
                HBox hb = new HBox(c1, c2);  //Setting Coin Slots in each Room.
                rm = new RoomsGUI(c1, c2, count_Room, enterUser); //Adding Components in Arraylist according to the Room
                arrRoomSlot.add(rm);
                count_Room++;
                //To set to BlACK
//                    arrRoomSlot.get(1).getBlackSlot().setId("blackFilledStot");
                //To set to WHITE
                //                  arrRoomSlot.get(2).getBlackSlot().setId("whiteFilledStot");
                hb.setAlignment(Pos.CENTER);
                hb.getStyleClass().add("hbox");
                VBox v4 = new VBox(new HBox(enterUser), hb);
                v4.setId("room");
                v4.setAlignment(Pos.CENTER);
                rooms.add(v4);
                arrVbox.get(j).getChildren().add(v4); //Adding components to appropriate VBox
            }
        }
        /**
         * Room Selection using arrRoomSlot ArrayList The coin slots are filled
         * accordingly and The game begins
         */
        roomTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
                RadioButton rb = (RadioButton) roomTg.getSelectedToggle();
                if (rb != null) {

                    for (int i = 0; i < arrRoomSlot.size(); i++) {
                        arrRoomSlot.get(i).getBlackSlot().setId("roomslot");
                    }
                    String s = rb.getText();
                    btn2.setText(s + " selected");
                    int SelectedRoomNum = Integer.parseInt(rb.getText().replaceAll("Room ", "").trim());
                    arrRoomSlot.get(SelectedRoomNum - 1).getBlackSlot().setId("blackFilledStot");
                    System.out.println();
                }
            }
        });
        vb_roomView.getChildren().addAll(hb1, hb2);
        vb_roomView.getStyleClass().add("vbox");
        layout2.setAlignment(Pos.CENTER);
        Label promtwithGamerName = new Label("Let's play gamer,"+tf_Name.getText());
        layout2.getChildren().addAll(tGameTitle1,label2,promtwithGamerName, vb_roomView, new Label(" "), btn2);
        Scene scene2 = new Scene(layout2);
        scene2.getStylesheets().add("Resc/NMMBoard.css");
//Scene 3 - Game Board
        Button btnend_game = new Button("End Game");
        Button btnaddCoin = new Button(" aDD BTN");
       
        
        HBox hbMenu = new HBox(btnend_game,btnaddCoin);
        hbMenu.setId("exitMenu");
        hbMenu.setAlignment(Pos.TOP_RIGHT);
        VBox root = new VBox();
        root.setId("vbox");
        Label tGuide = new Label("Let's Play");
        Label tPlayerName = new Label("Player 1");
        Button btnStat = new Button("Button Start");
        root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), tPlayerName);
         btnaddCoin.setOnAction(e->{
             String Scenario = "Place Anywhere";
             switch(Scenario){
                     case "Place Anywhere":  //Place slots whereever available
                         System.out.println("dasfsa");
                         double d[] = getSlot("A2");
                         MCoinType clientPlayerType = MCoinType.WHITE;
                         Coin placingnewCoin = new Coin(MCoinType.EMPTY,clientPlayerType, "A2",d[0]*POS_SIZE,d[1]*POS_SIZE,Scenario,bcs);
                         System.out.println("In Client >"+placingnewCoin.getType());
                         bcs = placingnewCoin.getBcs();
                         for (Object value : bcs.values()) {
                                Coin c = (Coin) value;
                                System.out.println("Place Anywhere slot = "+c.slot+" type ="+c.getType());
                            }
                         AddCompTobsc(placingnewCoin);
                         root.getChildren().removeAll(root.getChildren());  //To avoid DuplicateChildren
                         root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), tPlayerName);        //Reload  Board
             }
                   AddCompTobsc( new Coin(MCoinType.WHITE, "A3"));
//                   AddCompTobsc( new Coin(MCoinType.BLACK, "A1"));
//                   AddCompTobsc( new Coin(MCoinType.EMPTY, "H3"));
//                   AddCompTobsc( new Coin(MCoinType.WHITE, "F3"));
//                   AddCompTobsc( new Coin(MCoinType.BLACK, "D3"));

                   System.out.println("btn AddCOinads and Check ");
        root.getChildren().removeAll(root.getChildren());  //To avoid DuplicateChildren
        root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), tPlayerName);        //Reload  Board
    });
        Scene scene = new Scene(root);
        btnStat.setOnAction(e -> {
              root.getChildren().removeAll(root.getChildren());  //To avoid DuplicateChildren
                         root.getChildren().addAll(hbMenu, tGuide, btnStat, createContent(), tPlayerName);        //Reload  Board
           
        });

        //Button Transition
        btn1.setOnAction(e -> {
            String concat = "";
            concat=promtwithGamerName.getText().concat(" "+tf_Name.getText());
            promtwithGamerName.setText(concat);
            primaryStage.setScene(scene2);
        });
        btn2.setOnAction(e -> {
            primaryStage.setScene(scene);
            RadioButton rb = (RadioButton) roomTg.getSelectedToggle();
         //   if (rb != null) {
//                ObjectInputStream ois = null;
//                try {
//                    primaryStage.setScene(scene2);
//                    Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
                    //   ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    // ois = new ObjectInputStream(socket.getInputStream());
                    //list(ois, oos);
//
//                } catch (IOException ex) {
//                    Logger.getLogger(NMMApplication.class.getName()).log(Level.SEVERE, null, ex);
//                } finally {
//                    try {
//                        ois.close();
//                    } catch (IOException ex) {
//                        Logger.getLogger(NMMApplication.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
            BoardComp bc = new BoardComp();

       // }
        });
        btnend_game.setOnAction(e -> primaryStage.setScene(scene1));
        scene.getStylesheets().add("Resc/NMMBoard.css");
        primaryStage.setTitle("Nine Men's Morris");
        primaryStage.setScene(scene);//chanfe to scene1
        primaryStage.show();
    }
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
    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }
    /**
     * Adds BoardComp to specified slot position and sets PosX and PosY
     * BoardComp should set CoinType and slot
     * @param slot
     * @param bc 
     */
    public void AddCompTobsc(Coin bc){
         double d[] = getSlot(bc.getSlot());
                    bc.setPosX(d[0] * POS_SIZE);
                    bc.setPosY(d[1] * POS_SIZE);
//                   String [] vldMvs = {"A2","B3"};
//                   bc.setVldMvs(vldMvs);
                   bcs.put(bc.getSlot(),bc);
     }
    public static void test2(int[] test) {
        test[1] = 19;
    }
    private static void list(ObjectInputStream ois, ObjectOutputStream oos) {
        // Create command
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.LIST_ROOMS);

        try {
            // Send command
            oos.reset();
            oos.writeObject(command);
            NCommand reply = (NCommand) ois.readObject();

            // Get room list from reply
            //    printRooms(reply.getRooms());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NMMClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static boolean choose(ObjectInputStream ois, ObjectOutputStream oos) {
        boolean success = true;

        // Create the command request for the room
        NCommand command = new NCommand();
        command.setCommand(NetworkCommand.CHOOSE_ROOM);

        // Ascount_Roomfor the room number and set it in the request
        System.out.print("Enter Room Number: ");
        //  int room = Integer.parseInt(INPUT.nextLine());
        ///    command.setRoom(room);

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
}
