package nmmguisample;


import com.almasb.checkers.CheckersApp;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/*
 *The board components ared designed using a blocked graph as referance.
 */

/**
 * @author LENOVO
 */
public class BoardComp extends Rectangle{
    private Coin coin;
    public boolean hasCoin(){
        return coin != null;
    }
    public Coin getCoin(){
        return coin;
    }
    public void setCoin(Coin coin){
        this.coin = coin;
    }
    public BoardComp(boolean posMove, int x,int y ) {
        setHeight(SaMPLENMM.POS_SIZE);
        setWidth(SaMPLENMM.POS_SIZE);
        relocate(x*SaMPLENMM.POS_SIZE,y*SaMPLENMM.POS_SIZE);
        setFill(posMove ? Color.GREEN: Color.GREY);
    }

    
}
