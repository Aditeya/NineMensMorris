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
            System.out.println(" The " + (POS_SIZE * HEIGHT - (2 * i * 100)));
            System.out.println(" The halfr " + (POS_SIZE * HEIGHT - (2 * i * 100)) / 2);

            r1.setStroke(Color.CADETBLUE);
            r1.setX(i * 100);
            r1.setY(i * 100);
            r1.setStrokeWidth(10);
            root.getChildren().add(r1);
        }

        int[] posX = {100, 200,300,350,400,500,600};
        int[] posY = {100, 350, 600, 300,};

        ArrayList arr = new ArrayList();

        for (int i = 0; i < posX.length; i++) {
            for (int j = 0; j < posX.length; j++) {
                Circle c1 = new Circle(POS_SIZE / 10);
                // c1.setCenterX(posX[i]);
                //c1.setCenterY(posY[j]);
                c1.setCenterX(posX[i]);
                c1.setCenterY(posX[j]);
                c1.setFill(Color.CORAL);
                System.out.println("X " + i * 100 + " Y " + i * 100);
                c1.setFill(Color.CORAL);
                root.getChildren().addAll(c1);
            }
        }

//        System.out.println("r1 =" + POS_SIZE*HEIGHT + " x "+ POS_SIZE*WIDTH);
//        System.out.println("r2 =" + (POS_SIZE*HEIGHT-200) + " x "+ (POS_SIZE*HEIGHT-200));
        //    System.out.println("r3 =" + (POS_SIZE*HEIGHT-400) + " x "+ (POS_SIZE*HEIGHT-400));
//        Rectangle r1 = new Rectangle(POS_SIZE*HEIGHT,POS_SIZE*WIDTH);// 700
//        Rectangle r2 = new Rectangle((POS_SIZE*HEIGHT-200),(POS_SIZE*HEIGHT-200));
//        Rectangle r3 = new Rectangle((POS_SIZE*HEIGHT-400),(POS_SIZE*HEIGHT-400));
//
//       
//             
//              
//        r2.setX(100);
//        r2.setY(100);
//        
//        r3.setX(200);
//        r3.setY(200);       
//        
//          r1.setFill(Color.BLACK);
//                r2.setFill(Color.PINK);
//               r3.setFill(Color.ANTIQUEWHITE);
//               r1.setStrokeWidth(20);
//        
//        
//        root.getChildren().addAll(r1,r2,r3);
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

}
