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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.MCoinType;
import ninemensmorris.enums.PlayerTurn;
import ninemensmorris.enums.PrintType;

/**
 *
 * @author aditeya
 */
public class NMMApplication extends Application {

    public static final int POS_SIZE = 100;
    public static final int WIDTH = 7;
    public static final int HEIGHT = 7;
    public HashMap<String, Coin> bcs = new HashMap<>();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * POS_SIZE, HEIGHT * POS_SIZE);
        BoardComp bc = new BoardComp();
        bc.GenerateBoard(root,bcs);
        return root;
    }

    @Override
    public void start(Stage primaryStage) {
//Scene 1 - Intro
        Label lb1 = new Label("Welcome to the Game"); 
        Label tGameTitle = new Label("Nine Mens Morris");
        tGameTitle.setId("gameTitle");
        Button btn1 = new Button("Let's Play");
        VBox layout1 = new VBox(20);
        AnimationComponents animCom = new AnimationComponents();
        animCom.setIntroAnim(layout1, 300, 300, Color.BLUE);
        layout1.setAlignment(Pos.CENTER);
        layout1.setSpacing(POS_SIZE);
        layout1.getChildren().addAll(tGameTitle, lb1, btn1);
        layout1.setId("layout1");
        Scene scene1 = new Scene(layout1);
        scene1.getStylesheets().add("Resc/NMMBoard.css");

//Scene 2 - Select Rooms 
        Label label2 = new Label("Choose your Room");
        Button btn2 = new Button("Let's Play");
        Label tGameTitle1 = new Label("Nine Mens Morris");
        tGameTitle1.setId("gameTitle");
        VBox layout2 = new VBox(20);
        layout2.setId("layout2");
        layout2.setAlignment(Pos.CENTER);
        layout2.setSpacing(POS_SIZE);

        //Rooms in 2 Rows
        int num_Rooms = 12;
        ArrayList rooms = new ArrayList();
        VBox vb_roomView = new VBox();

        HBox hb1 = new HBox();
        HBox hb2 = new HBox();
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
ArrayList<HBox> arrVbox = new ArrayList<HBox>();
arrVbox.add(hb1);
arrVbox.add(hb2);

for(int j =0,k=1 ;j<arrVbox.size();j++,k++){
      for (int i = 0; i < num_Rooms / 2; i++) {
            Button enterUser = new Button("+");
            Label lbRoomName = new Label("Room "+k); 
            enterUser.setId("btnplus");
            Circle c1 = new Circle(20);
            c1.setId("roomslot");
            Circle c2 = new Circle(20);
            c2.setId("roomslot");
            HBox hb = new HBox(c1, c2); 
            hb.setAlignment(Pos.CENTER);
            VBox v4 = new VBox(new HBox(lbRoomName,enterUser),hb);
            v4.setId("room");
            v4.setAlignment(Pos.CENTER);
            rooms.add(v4);
            arrVbox.get(j).getChildren().add(v4);
        }
}

        vb_roomView.getChildren().addAll(hb1, hb2);

        vb_roomView.getStyleClass().add("vbox");
        layout2.getChildren().addAll(tGameTitle1, label2, vb_roomView, btn2);
        Scene scene2 = new Scene(layout2);
        scene2.getStylesheets().add("Resc/NMMBoard.css");

//Scene 3 - Game Board
        Button btnend_game = new Button("End Game");        HBox hbMenu = new HBox(btnend_game);        hbMenu.setId("exitMenu");        hbMenu.setAlignment(Pos.TOP_RIGHT);
        VBox root = new VBox();        root.setId("vbox");        Label tGuide = new Label("Let's Play");
        TextField tfMove = new TextField("Try Type Here");        Label tPlayerName = new Label("Player 1");
        Button btnStat = new Button("Button Start");
        root.getChildren().addAll(hbMenu, tGuide,btnStat, createContent(), tPlayerName);
        Scene scene = new Scene(root);
        btnStat.setOnAction(e->{
        });
        
        //Button Transition
        btn1.setOnAction(e -> primaryStage.setScene(scene2));
        btn2.setOnAction(e ->{  primaryStage.setScene(scene);
        NMMLogic nmm = new NMMLogic();
        
        BoardComp bc = new BoardComp();
        
        
        });
        btnend_game.setOnAction(e -> primaryStage.setScene(scene1));

        scene.getStylesheets().add("Resc/NMMBoard.css");
        primaryStage.setTitle("Nine Men's Morris");
        primaryStage.setScene(scene2);//chanfe to scene1
        primaryStage.show();
    }

    public static void main(String[] args) throws InterruptedException {
    launch(args);
    }

    public static void test2(int[] test) {
        test[1] = 19;
    }

}
