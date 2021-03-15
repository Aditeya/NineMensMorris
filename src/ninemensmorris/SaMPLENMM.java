/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nmmguisample;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
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
          bc.GenerateBoard(root);
        
        
        
        Coin c = new Coin(CoinType.WHITE, POS_SIZE, POS_SIZE, true);
      
        
         bc.PlaceCoin(root, CoinType.BLACK,bc.getSlot("A1"));
         bc.PlaceCoin(root, CoinType.WHITE, bc.getSlot("B2"));
//      bc.   PlaceCoin(root, CoinType.BLACK, getSlot("B1"));
//         bc.PlaceCoin(root, CoinType.WHITE, getSlot("C2"));
//       bc.  PlaceCoin(root, CoinType.WHITE, getSlot("C3"));
//        bc. PlaceCoin(root, CoinType.BLACK, getSlot("C1"));
//        bc. PlaceCoin(root, CoinType.WHITE, getSlot("H2"));

         return root;
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("OO");
        VBox root = new VBox();
        root.getChildren().addAll(btn, createContent());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("Resc/NMMBoard.css");
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
