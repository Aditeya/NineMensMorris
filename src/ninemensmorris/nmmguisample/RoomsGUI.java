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

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.shape.Circle;

/**
 *GUI components for Room in Lobby
 * @author LENOVO
 */
public class RoomsGUI {

    Circle blackSlot;
        Circle whiteSlot;
        Circle prev_RoomSel;
      int RoomNum;
      
      boolean whiteFilled;
      boolean blackFilled;

      boolean fullRoom;

    public boolean isFullRoom() {
        if(this.whiteFilled && this.blackFilled)
        return true;
        else
        return false;
    }

    public void setFullRoom(boolean fullRoom) {
        this.fullRoom = fullRoom;
    }
       
      
    RadioButton btn;
    public RoomsGUI() {
    }

    public Circle getPrev_RoomSel() {
        return prev_RoomSel;
    }

    public void setPrev_RoomSel(Circle prev_RoomSel) {
        this.prev_RoomSel = prev_RoomSel;
    }

    public RoomsGUI(Circle blackSlot, Circle whiteSlot, RadioButton btn) {
        this.blackSlot = blackSlot;
        this.whiteSlot = whiteSlot;
        this.btn = btn;
    }
     public RoomsGUI(Circle blackSlot, Circle whiteSlot) {
        this.blackSlot = blackSlot;
        this.whiteSlot = whiteSlot;
    }

    public RoomsGUI(Circle blackSlot, Circle whiteSlot, int RoomNum, RadioButton btn) {
        this.blackSlot = blackSlot;
        this.whiteSlot = whiteSlot;
      this.RoomNum = RoomNum;
        this.btn = btn;
    }
/**
 * Sets ID of each rooms slots according to Fill 
 */
    public void setIfFilled(){
          this.getWhiteSlot().setId("roomslot");
          this.getBlackSlot().setId("roomslot");
   if(this.blackFilled)
            this.getBlackSlot().setId("blackFilledSlot");
   if(this.whiteFilled)
            this.getWhiteSlot().setId("whiteFilledSlot");
        }
    
    
    public int getRoomNum() {
        return RoomNum;
    } 

    public void setWhiteFilled(boolean whiteFilled) {
        this.whiteFilled = whiteFilled;
    }

    public void setBlackFilled(boolean blackFilled) {
        this.blackFilled = blackFilled;
    }

    public boolean isWhiteFilled() {
        return whiteFilled;
    }

    public boolean isBlackFilled() {
        return blackFilled;
    }

  

    public RadioButton getBtn() {
        return btn;
    }

    public Circle getBlackSlot() {
        return blackSlot;
    }

    public Circle getWhiteSlot() {
        return whiteSlot;
    }

    
    
}
