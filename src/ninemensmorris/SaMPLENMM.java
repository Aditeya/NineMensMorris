/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nmmguisample;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author LENOVO
 */
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

       
        
        
        

        for (int i = 0; i < 4; i++) {
            Rectangle r1 = new Rectangle(POS_SIZE * HEIGHT - (2 * i * 100), POS_SIZE * WIDTH - (2 * i * 100));
           // System.out.println(" The " + (POS_SIZE * HEIGHT - (2 * i * 100)));
          //  System.out.println(" The halfr " + (POS_SIZE * HEIGHT - (2 * i * 100)) / 2);
            r1.setStroke(Color.CADETBLUE);
            r1.setX(i * 100);         r1.setY(i * 100);
            r1.setStrokeWidth(10); root.getChildren().add(r1);
        }

        int[] posX = {100,600};      int[] posY = {100,350,600};
        int[] posX1 = {200,500};        int[] posY1 = {200,350,500};
        int[] posX2 = {300,400};        int[] posY2 = {300,350,400};
        int[] posX3 = {350};       int[] posY3 = {100,200,300,400,500,600};
        
        CreateSlots(posX,posY,root);
        CreateSlots(posX1,posY1,root);
        CreateSlots(posX2,posY2,root);
        CreateSlots(posX3,posY3,root);

         PlaceCoin(root, CoinType.BLACK, getSlot("A1"));
         PlaceCoin(root, CoinType.WHITE, getSlot("A2"));
         PlaceCoin(root, CoinType.BLACK, getSlot("B1"));
         PlaceCoin(root, CoinType.WHITE, getSlot("C2"));

         return root;
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("OO");
        VBox root = new VBox();
        root.getChildren().addAll(btn, createContent());

        Scene scene = new Scene(root);
        primaryStage.setTitle("Nine Men's Morris");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void CreateSlots(int[] posX,int[] posY,Pane root){
      for (int i = 0; i < posX.length; i++) {
            for (int j = 0; j < posY.length; j++) {
                Circle c1 = new Circle(10);
                // c1.setCenterX(posX[i]);
                //c1.setCenterY(posY[j]);
                c1.setCenterX(posX[i]);
                c1.setCenterY(posY[j]);
                c1.setFill(Color.CORAL);
                System.out.println("X " +posX[i] + " Y " +posY[j]);
                c1.setFill(Color.CORAL);
                root.getChildren().addAll(c1);
            }
        }
    }
    
    public void PlaceCoin(Pane root,CoinType ct,double [] xy){
        double x= xy[0]*100;        double y= xy[1]*100;

         Coin c = new Coin(ct, x,y);
         root.getChildren().addAll(c);
    }
    
    public double[] getSlot(String pos){
        
        double [] doubleArr = new double[2];
        switch(pos) {
  case "A1":
    doubleArr[0]= 1;
    doubleArr[1]= 1;
    break;
  case "A2":
    doubleArr[0]= 3.5;
    doubleArr[1]= 1;
    break;
  case "A3":
    doubleArr[1]= 1;
    doubleArr[0]= 6;
    break;
  case "B1":
    doubleArr[1]= 2;
    doubleArr[0]= 2;
    break;
  case "B2":
    doubleArr[1]= 2;
    doubleArr[0]= 3.5;
    break;
  case "B3":
    doubleArr[1]= 2;
    doubleArr[0]= 5;
    break;
  case "C1":
    doubleArr[1]= 3;
    doubleArr[0]= 3;
    break;
  case "C2":
    doubleArr[1]= 3;
    doubleArr[0]= 3.5;
    break;
  case "C3":
    doubleArr[1]= 3;
    doubleArr[0]= 4;
    break;
  case "D1":
    doubleArr[1]= 3.5;
    doubleArr[0]= 1;
    break;
  case "D2":
    doubleArr[1]= 3.5;
    doubleArr[0]= 2;
    break;
  case "D3":
    doubleArr[1]= 3.5;
    doubleArr[0]= 3;
    break;
  case "E1":
    doubleArr[1]= 3.5;
    doubleArr[0]= 4;
    break;
  case "E2":
    doubleArr[1]= 3.5;
    doubleArr[0]= 5;
    break;
  case "E3":
    doubleArr[1]= 3.5;
    doubleArr[0]= 6;
    break;
  case "F1":
    doubleArr[1]= 4;
    doubleArr[0]= 3;
    break;
  case "F2":
    doubleArr[1]= 4;
    doubleArr[0]= 3.5;
    break;
  case "F3":
    doubleArr[1]= 4;
    doubleArr[0]= 4;
    break;
  case "G1":
    doubleArr[1]= 5;
    doubleArr[0]= 2;
    break;
  case "G2":
    doubleArr[1]= 5;
    doubleArr[0]= 3.5;
    break;
  case "G3":
    doubleArr[1]= 5;
    doubleArr[0]= 5;
    break;  
  case "H1":
    doubleArr[1]= 6;
    doubleArr[0]= 1;
    break;
  case "H2":
    doubleArr[1]= 6;
    doubleArr[0]= 3.5;
    break;
  case "H3":
    doubleArr[1]= 6;
    doubleArr[0]= 6;
    break;  
  default:
}
        return doubleArr;
    }
    
    
}
