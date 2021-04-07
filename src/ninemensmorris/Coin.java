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
package ninemensmorris;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Circle;
import static ninemensmorris.NMMApplication.POS_SIZE;
import ninemensmorris.enums.MCoinType;

/**
 *
 * @author LENOVO
 */
class Coin {
  private MCoinType type;
    double posX,posY;
    boolean movable;
    String slot;
    Circle bg;
    public MCoinType getType(){
        return type;
    }
    public Coin(MCoinType type, double posX, double posY, boolean movable,String slot) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.movable = movable;
        this.slot = slot;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
    
     public Group ReturnCoin() {
        Group CoinGroup = new Group();
        
        
        
        this.bg = new Circle(POS_SIZE/15*3.125);
        this.bg.setId("coinbg");
        this.bg.setTranslateX(posX);
        this.bg.setTranslateY(posY);
       
        
        Circle c = new Circle(POS_SIZE/15*3.125);
       c.setTranslateX(this.posX-4);
       c.setTranslateY(this.posY-4);
       if(this.type != MCoinType.EMPTY){
           c.setOnDragDetected((MouseEvent event) -> {
            System.out.println("Circle 1 drag detected");
            Dragboard db = c.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
        });
        c.setOnMouseDragged((MouseEvent event) -> {
            event.setDragDetect(true);
        });
           
           
       }
       if(this.type==MCoinType.BLACK){
                 c.setId("coinblack");
                CoinGroup.getChildren().addAll(bg);
                
                
                

       }else if(this.type==MCoinType.WHITE) {
           c.setId("coinwhite");
                  CoinGroup.getChildren().addAll(bg);
       }
       else{
           c.setId("slot");
           c.setRadius(10);
           c.setTranslateX(this.posX);
           c.setTranslateY(this.posY);
           
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
            if (db.hasString()) {
                System.out.println("Dropped: ");
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
           
           
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
    }

    
    
    
