package nmmguisample;


import com.almasb.checkers.CheckersApp;
import java.util.HashMap;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import static nmmguisample.SaMPLENMM.HEIGHT;
import static nmmguisample.SaMPLENMM.POS_SIZE;
import static nmmguisample.SaMPLENMM.WIDTH;

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

    public BoardComp() {
    }
    
      public void PlaceCoin(Pane root,CoinType ct,double [] xy){
        double x= xy[0]*100;        double y= xy[1]*100;
         Coin c = new Coin(ct, x,y,true);
         root.getChildren().addAll(c.ReturnCoin());
    }
   public void GenerateBoard(Pane root){
        for (int i = 0; i < 4; i++) {
            Rectangle r1 = new Rectangle(POS_SIZE * HEIGHT - (2 * i * POS_SIZE), POS_SIZE * WIDTH - (2 * i * POS_SIZE));
            r1.setStroke(Color.CADETBLUE);
            r1.setX(i * POS_SIZE);         r1.setY(i * POS_SIZE);
            r1.setStrokeWidth(10); root.getChildren().add(r1);
        }
        String[] lineStr = {"A2","C2","D1","D3","E1","E3","F2","H2"};
        
        for(int i = 0;i<lineStr.length;i=i+2){
            System.out.println("i="+i);
        Line line = new Line(getSlot(lineStr[i])[0]*100,getSlot(lineStr[i])[1]*100,
        getSlot(lineStr[i+1])[0]*100,getSlot(lineStr[i+1])[1]*100);
      //  System.out.println("= "+"\n  "+lineStr[i]+" "+bc.getSlot(lineStr[i])[0]*100+""    + " "+bc.getSlot(lineStr[i])[1]*100+ "\n "+lineStr[i+1]+" "+    bc.getSlot(lineStr[i+1])[0]*100+" "+bc.getSlot(lineStr[i+1])[1]*100);
        line.setId("boardlines");  
        root.getChildren().add(line);
        }

        int[] posX = {100,600};      int[] posY = {100,350,600};
        int[] posX1 = {200,500};        int[] posY1 = {200,350,500};
        int[] posX2 = {300,400};        int[] posY2 = {300,350,400};
        int[] posX3 = {350};       int[] posY3 = {100,200,300,400,500,600};
        
        CreateSlots(posX,posY,root);
        CreateSlots(posX1,posY1,root);
        CreateSlots(posX2,posY2,root);
        CreateSlots(posX3,posY3,root);
       
       
  
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
      
    
    public double[] getSlotHash(String pos){
        
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
