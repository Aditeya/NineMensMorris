/*
 */
package nmmguisample;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import static nmmguisample.SaMPLENMM.POS_SIZE;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 */
public class Coin extends Pane {
    private CoinType type;
    double posX,posY;
    boolean movable;
    double orgSceneX, orgSceneY;
    String slot;
    Circle bg;
    public CoinType getType(){
        return type;
    }
    public Coin(CoinType type, double posX, double posY, boolean movable) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.movable = movable;
    }
     public Group HashReturnCoin(String pos) {
        Group CoinGroup = new Group();
        bg = new Circle(POS_SIZE/15*3.125);
        bg.setId("coinbg");
        bg.setTranslateX(posX);
        bg.setTranslateY(posY);
       Circle c = new Circle(POS_SIZE/15*3.125);
       c.setTranslateX(posX-4);
       c.setTranslateY(posY-4);
       CoinGroup.getChildren().addAll(bg,c);
       if(type==CoinType.WHITE){
                 c.setId("coin-black");
       }else{
           c.setId("coin-white");
       }
       return CoinGroup;
    }
    public Circle getBg() {
        return bg;
    }
    public void setBg(Circle bg) {
        this.bg = bg;
    }
    public String getSlot() {
        return slot;
    }
    
    public void setSlot(String slot) {
        this.slot = slot;
    }
     
    
    
    
    
    
    public Group ReturnCoin () {
        Group CoinGroup = new Group();
        Circle bg = new Circle(POS_SIZE/15*3.125);
        bg.setId("coinbg");
        bg.setTranslateX(posX);
        bg.setTranslateY(posY);
        
       Circle c = new Circle(POS_SIZE/15*3.125);
       c.setTranslateX(posX-4);
       c.setTranslateY(posY-4);
       CoinGroup.getChildren().addAll(bg,c);
       
       if(type==CoinType.WHITE){
                 c.setId("coin-black");
       }else{
           c.setId("coin-white");
       }
       
//      if(movable){
//          CoinGroup.setCursor(Cursor.HAND);
//           CoinGroup.setOnDragDetected((MouseEvent event) -> {
//            System.out.println("Circle 1 drag detected");
//            Dragboard db = CoinGroup.startDragAndDrop(TransferMode.ANY);
//            ClipboardContent content = new ClipboardContent();
//            content.putString("");
//            db.setContent(content);
//        });
//        CoinGroup.setOnMouseDragged((MouseEvent event) -> {
//            event.setDragDetect(true);
//        });
    //  }
                      return CoinGroup;

    }
    
    
    }

    
    
    
