/*
 * Copyright (C) 2021 LENOVO
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninemensmorris.nmmguisample;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Circle;
import static ninemensmorris.nmmguisample.NMMApplication.POS_SIZE;
import ninemensmorris.enums.MCoinType;

/**
 *
 * @author LENOVO
 */
class Coin {

    private MCoinType type;
    MCoinType toPlacetype;
    double posX, posY;
    boolean movable;
    String slot, Scenario = "";
    public String[] vldMvs;
    Circle bg;
    HashMap<String, Coin> bcs;
    LinkedBlockingQueue<Object> lb_in = new LinkedBlockingQueue<>();

    public Coin(MCoinType type, MCoinType toPlacetype, String slot, double posX, double posY, String Scenario, HashMap<String, Coin> bcs) {
        this.type = type;
        this.toPlacetype = toPlacetype;
        this.posX = posX;
        this.posY = posY;
        this.slot = slot;
        this.Scenario = Scenario;
        this.bcs = bcs;
    }

    public MCoinType getType() {
        return type;
    }

    public Coin(MCoinType type, double posX, double posY, boolean movable, String slot) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.movable = movable;
        this.slot = slot;
    }

    public Coin(MCoinType type, String slot,String Scenario) {
        this.type = type;
        this.slot = slot;
        this.Scenario = Scenario;
    }

    public Coin(MCoinType type, String slot, double posX, double posY) {
        this.type = type;
        this.slot = slot;
        this.posX = posX;
        this.posY = posY;
    }

    public void setVldMvs(String[] vldMvs) {
        this.vldMvs = vldMvs;
    }

    public String[] getVldMvs() {
        return vldMvs;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public MCoinType getToPlacetype() {
        return toPlacetype;
    }

    public void setToPlacetype(MCoinType toPlacetype) {
        this.toPlacetype = toPlacetype;
    }

    public void setScenario(String Scenario) {
        this.Scenario = Scenario;
    }
    
    
    
    
  //  public returnCor_PlaceCoin 
    
    
    
    
    
    
    
    
    
    
    public Group ReturnCoin() {
        Group CoinGroup = new Group();
        this.bg = new Circle(POS_SIZE / 15 * 3.125);
        this.bg.setId("coinbg");
        this.bg.setTranslateX(posX);
        this.bg.setTranslateY(posY);
        Circle c = new Circle(POS_SIZE / 15 * 3.125);
        c.setTranslateX(this.posX - 4);
        c.setTranslateY(this.posY - 4);
        if (this.type != MCoinType.EMPTY) {
            c.setOnDragDetected((MouseEvent event) -> {
                System.out.println("Circle 1 drag detected");
                Dragboard db = c.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(this.slot + "," + this.type);
                db.setContent(content);
            });
            c.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
        }
        if (this.type == MCoinType.BLACK) {
            c.setId("coinblack");
            CoinGroup.getChildren().addAll(bg);
        } else if (this.type == MCoinType.WHITE) {
            c.setId("coinwhite");
            CoinGroup.getChildren().addAll(bg);
        } else {
            c.setId("slot");
            c.setRadius(10);
            c.setTranslateX(this.posX);
            c.setTranslateY(this.posY);
            if (this.Scenario.equals("PLACE")) {
                c.setOnMouseClicked(e -> {
                    System.out.println("Clicked ON SLOT");
                    System.out.println("  >" + this.bcs.keySet());
                    BoardComp bc = new BoardComp();
                    double d[] = bc.getSlot(this.getSlot());
                    Coin newCoin = new Coin(this.toPlacetype, this.slot, d[0] * POS_SIZE, d[1] * POS_SIZE);
                    HashMap temp = this.bcs;
                    temp.put(this.slot, newCoin);
                    newCoin.setBcs(temp);
                      System.out.println("In Coin >"+newCoin.getType());
                    for (Object value : bcs.values()) {
                           System.out.println("values "+bcs.keySet());
                        Coin coin = (Coin) value;
                                         System.out.println("In coin slot = "+coin.slot+" type ="+coin.getType());
                    }
                    this.bcs.put(slot, newCoin);
                });
            } else {
                c.setOnDragOver(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        if (event.getGestureSource() != c && event.getDragboard().hasString()) {
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }
                        event.consume();
                    }
                });
                c.setOnDragDropped((DragEvent event) -> {
                    Dragboard db = event.getDragboard();
                    db.setDragViewOffsetX(this.posX);
                    db.setDragViewOffsetY(this.posY);
                    System.out.println(" >  " + db.getDragViewOffsetX());
                    System.out.println(" >  " + db.getDragViewOffsetY());
                    if (db.hasString()) {
                System.out.println("Dropped: "+db.getString());
                        if (false) {//db.getString().isPartofValidMoves){
                            event.setDropCompleted(true);
                            System.out.println("Dropped: " + db.getString());
                        }
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });
            }
        }
        CoinGroup.getChildren().addAll(c);
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

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public HashMap<String, Coin> getBcs() {
        return bcs;
    }

    public void setBcs(HashMap<String, Coin> bcs) {
        this.bcs = bcs;
    }

}
