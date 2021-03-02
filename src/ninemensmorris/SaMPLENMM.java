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

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * POS_SIZE, HEIGHT * POS_SIZE);
        //  root.getChildren().addAll(boardCoGroup,CoinGroup);
//       for (int y = 0; y < HEIGHT; y++) {
//            for (int x = 0; x < WIDTH; x++) {
//              BoardComp bc = new BoardComp((x + y) % 2 == 0, x, y);
//           boardCoGroup.getChildren().add(bc);
//           }
//       }

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
                Circle c1 = new Circle(POS_SIZE / 10);
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
    
}
