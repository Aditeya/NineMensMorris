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
import java.util.concurrent.LinkedBlockingQueue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
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
    String slot, Scenario = "";
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
boolean milled;
    public Coin(MCoinType type, double posX, double posY,  String slot) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.slot = slot;
    }
  public Coin(MCoinType type, String slot,String Scenario,boolean isMilled) {
        this.type = type;
        this.slot = slot;
        this.Scenario = Scenario;
        this.milled = isMilled;
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

    public boolean isMilled() {
        return milled;
    }
    
    public Group ReturnCoin() {
        Group CoinGroup = new Group();
        this.bg = new Circle(POS_SIZE / 15 * 3.125);
        this.bg.setId("coinbg");
        this.bg.setTranslateX(posX);
        this.bg.setTranslateY(posY);
        Circle c = new Circle(POS_SIZE / 15 * 3.125);
        c.setTranslateX(this.posX - 4);
        c.setTranslateY(this.posY - 4);
        
          if(isMilled()){
                c.setCursor(Cursor.CLOSED_HAND);
                c.setStrokeWidth(5);
                System.out.println("milled Coin");
                c.setId("milledCoin");
            }else{
                 c.setCursor(Cursor.OPEN_HAND);
                 c.setStrokeWidth(0);
            }
            
        if (MCoinType.EMPTY == this.type) {
            c.setId("slot");
            c.setRadius(10);
            c.setTranslateX(this.posX);
            c.setTranslateY(this.posY);
            //Setting Milled coins to show unmovability
          
        } else switch (this.type) {
            case BLACK:
                c.setId("coinblack");
                CoinGroup.getChildren().addAll(bg);
                break;
            case WHITE:
                c.setId("coinwhite");
                CoinGroup.getChildren().addAll(bg);
                break;
            default:
                c.setId("slot");
                c.setRadius(10);
                c.setTranslateX(this.posX);
                c.setTranslateY(this.posY);
                break;
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
