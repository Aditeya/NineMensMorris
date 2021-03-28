/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nmmguisample;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
public class SaMPLENMM extends Application {

    public static final int POS_SIZE = 100;
    public static final int WIDTH = 7;
    public static final int HEIGHT = 7;
    private Group boardCoGroup = new Group();
    private Group CoinGroup = new Group();
    private BoardComp[][] board = new BoardComp[WIDTH][HEIGHT];
    private Parent createContent() {
       Pane root = new Pane();
        root.setPrefSize(WIDTH * POS_SIZE, HEIGHT * POS_SIZE);
          BoardComp bc = new BoardComp();
          
//          { 
             bc.GenerateBoard(root);
        Coin c = new Coin(CoinType.WHITE, POS_SIZE, POS_SIZE, true);
        String [] vldMvs= {"A1","A3"};
        String [][] milled ={{"C1","C2","C3"},{"H1,H2,H3"}};
        String [] str ={"A1","A2","A3","F1","C2","C1","H2","F3"};
        
        for(int i =0;i<str.length;i++){
        NMMCoin nc = new NMMCoin("H3", true, vldMvs);
        nc.setCoinSlot(str[i]);
        nc.toString();
         if(i%2==0){
                nc.setCoin(CoinType.WHITE);
            }else{
             nc.setCoin(CoinType.BLACK);
         }
         bc.PlaceCoin(root, nc.getCoinType(),bc.getSlot(nc.getCoinSlot()));
        }
        NMMCoin nc2 = new NMMCoin(CoinType.WHITE,"Coin Slot", true, vldMvs); 
   // }
        return root;
    }
    @Override
    public void start(Stage primaryStage) {
//Scene 1 - Intro
Label lb1= new Label("Welcome to the Game");
Label tGameTitle = new Label("Nine Mens Morris");
Button btn1= new Button("Let's Play");
VBox layout1 = new VBox(20);

AnimationComponents animCom = new AnimationComponents();

animCom.setIntroAnim(layout1, 300, 300, Color.BLUE);


layout1.setAlignment(Pos.CENTER);
layout1.setSpacing(POS_SIZE);
layout1.getChildren().addAll(tGameTitle,lb1, btn1);
layout1.setId("");
Scene scene1 = new Scene(layout1);  
scene1.getStylesheets().add("Resc/NMMBoard.css"); 
        
//Scene 2 - Select Rooms
Label label2= new Label("Choose your Room");
Button btn2= new Button("Let's Play");
Label tGameTitle1 = new Label("Nine Mens Morris");
VBox layout2 = new VBox(20);
layout2.setAlignment(Pos.CENTER);
layout2.setSpacing(POS_SIZE);

            //Rooms in 2 Rows
            int num_Rooms= 6;
            ArrayList rooms = new ArrayList();
            VBox vb_roomView = new VBox();
            vb_roomView.setId("slots");
                    HBox hb1 = new HBox();
                    HBox hb2 = new HBox();
            hb1.setAlignment(Pos.CENTER);hb2.setAlignment(Pos.CENTER);

            for(int i = 0;i<num_Rooms/2;i++){
                Button enterUser = new Button("+");
                enterUser.setId("btn");
                HBox hb = new HBox(new Circle(20),new Circle(20));
                hb.setAlignment(Pos.CENTER);
                VBox v4 = new VBox(hb,enterUser);
                v4.setAlignment(Pos.CENTER);
                rooms.add(v4);
                hb1.getChildren().add(v4);
            }
            for(int i = 0;i<num_Rooms/2;i++){
                Button enterUser = new Button("+");
                enterUser.setId("btn");
                HBox hb4 = new HBox(new Circle(50),new Circle(50));
                hb4.setAlignment(Pos.CENTER);
                VBox v4 = new VBox(hb4,enterUser);
                v4.setAlignment(Pos.CENTER);
                rooms.add(v4);
                hb2.getChildren().add(v4);
            }
        
        
vb_roomView.getStyleClass().add("vbox");
vb_roomView.getChildren().addAll(hb1,hb2);
layout2.getChildren().addAll(tGameTitle1,label2,vb_roomView, btn2);
layout2.setId("intropage");
Scene scene2 = new Scene(layout2);        
scene2.getStylesheets().add("Resc/NMMBoard.css");

//Scene 3 - Game Board
        Button btn = new Button("OO");
        VBox root = new VBox();
        root.setId("vbox");
        Text tGuide = new Text("Let's Play");
        Text tPlayerName = new Text("Player 1");
        root.getChildren().addAll(btn,tGuide,createContent(),tPlayerName);
        Scene scene = new Scene(root);
        
               
 //Button Transition
 btn1.setOnAction(e -> primaryStage.setScene(scene2));    
 btn2.setOnAction(e -> primaryStage.setScene(scene));    

   
//BoardComp bc = new BoardComp();
  // HashMap<String, Double[]> hCoinPos = bc.createSlotHash();
            //  Double d[] = hCoinPos.get("A3");
             // System.out.println("  "+d[0]);       
        
        scene.getStylesheets().add("Resc/NMMBoard.css");
        primaryStage.setTitle("Nine Men's Morris");
        primaryStage.setScene(scene1);//chanfe to scene1
        primaryStage.show();
    }
    /**
     */
    public static void main(String[] args) {
        launch(args);
    }
}