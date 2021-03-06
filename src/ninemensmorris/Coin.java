/*
 */
package nmmguisample;
import java.sql.Types;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import static nmmguisample.SaMPLENMM.POS_SIZE;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 */
public class Coin extends Pane {
    private CoinType type;
    
    public CoinType getType(){
        return type;
    }

    public Coin (CoinType type , double x, double y) {
        this.type = type;
        
        Group CoinGroup = new Group();
        
         Circle bg = new Circle(POS_SIZE/15*3.125);
        bg.setFill(Color.BLUE);
        bg.setStroke(Color.WHITESMOKE);
        bg.setTranslateX(x);
        bg.setTranslateY(y);
       // bg.setStrokeWidth(POS_SIZE*0.020);
      //  bg.setTranslateX((POS_SIZE*3.125*2)/2) ;
      // bg.setTranslateY((POS_SIZE*0.26*2)/2+POS_SIZE*0.07);
        
        
        
        Circle ellipse = new Circle(POS_SIZE/15*3.125);
        ellipse.setFill(type== CoinType.WHITE ? Color.WHITESMOKE :Color.BLUEVIOLET );
        ellipse.setStroke(Color.WHITESMOKE);
         ellipse.setTranslateX(x-4);
        ellipse.setTranslateY(y-4);

         System.out.println(" X "+x+" Y "+y);
        //Sellipse.setStrokeWidth(POS_SIZE*0.020);
        //ellipse.setTranslateX(x) ;
       //ellipse.setTranslateY(y);
       CoinGroup.getChildren().addAll(bg,ellipse);
       CoinGroup.setOnMouseDragged(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                System.out.println("\"Event on Source: mouse dragged"+Types.TIME_WITH_TIMEZONE);
                System.out.println("\"Event on Source: mouse draggedr34657");
                event.setDragDetect(false);
               
            }
        });
       
       CoinGroup.setOnMouseClicked(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                System.out.println("Clicked on");
            }
        });
        
        getChildren().addAll(CoinGroup);
        
        
       //,//ellipse1);
    }
    
    
}
