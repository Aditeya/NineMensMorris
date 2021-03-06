/*
 */
package nmmguisample;
import static nmmguisample.SaMPLENMM.POS_SIZE;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 */
public class Coin extends StackPane {
    private CoinType type;
    
    public CoinType getType(){
        return type;
    }

    public Coin(CoinType type , int x, int y) {
        this.type = type;
        Ellipse bg = new Ellipse(POS_SIZE/15*3.125,POS_SIZE/15*3.125);
        bg.setFill(Color.CORNFLOWERBLUE);
        bg.setStroke(Color.WHITESMOKE);
        bg.setStrokeWidth(POS_SIZE*0.020);
        bg.setTranslateX((POS_SIZE*3.125*2)/2) ;
        System.out.println("Pos X "+(POS_SIZE*3.125*2)/2);
        bg.setTranslateY((POS_SIZE*0.26*2)/2+POS_SIZE*0.07);
        System.out.println("Pos Y "+((POS_SIZE*0.26*2)/2+POS_SIZE*0.07));

//        bg.setCenterX(300);
//        bg.setCenterY(300);
        
        
        
        Ellipse ellipse = new Ellipse(POS_SIZE/15*3.125,POS_SIZE/15*3.125);
        ellipse.setFill(type== CoinType.WHITE ? Color.ANTIQUEWHITE :Color.BLUEVIOLET );
        ellipse.setStroke(Color.WHITESMOKE);
        ellipse.setStrokeWidth(POS_SIZE*0.020);
        ellipse.setTranslateX((POS_SIZE*3.125*2)/2) ;
                System.out.println("Pos X "+(POS_SIZE*3.125*2)/2);

       ellipse.setTranslateY((POS_SIZE*0.26*2)/2);
               System.out.println("Pos X "+(POS_SIZE*0.26*2)/2);

        ellipse.setCenterX(300);
        ellipse.setCenterY(300);


        
        Ellipse ellipse1 = new Ellipse(); {
ellipse1.setCenterX(100);
ellipse1.setCenterY(100);
ellipse1.setRadiusX(10);
ellipse1.setRadiusY(10);
    }
        ellipse1.setFill(Color.AQUAMARINE);
        
        
        getChildren().addAll(bg,ellipse);//,//ellipse1);
    }
    
    
}
