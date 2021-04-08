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

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.shape.Circle;

/**
 *
 * @author LENOVO
 */
public class RoomsGUI {

    Circle blackSlot;
        Circle whiteSlot;
      int RoomNum;
    RadioButton btn;
    public RoomsGUI() {
    }

    public RoomsGUI(Circle blackSlot, Circle whiteSlot, RadioButton btn) {
        this.blackSlot = blackSlot;
        this.whiteSlot = whiteSlot;
        this.btn = btn;
    }

    public RoomsGUI(Circle blackSlot, Circle whiteSlot, int RoomNum, RadioButton btn) {
        this.blackSlot = blackSlot;
        this.whiteSlot = whiteSlot;
      this.RoomNum = RoomNum;
        this.btn = btn;
    }

    public int getRoomNum() {
        return RoomNum;
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

    


    /**
     * @param args the command line arguments
     */
   
    
    
}
