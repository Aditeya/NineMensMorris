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

import javafx.animation.RotateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 *
 * @author LENOVO
 */
class AnimationComponents {

    public AnimationComponents() {
    }

    public void setIntroAnim(Pane root, int posX, int posY, Color colour) {

        StackPane st = new StackPane();
        Color[] cols = {Color.ALICEBLUE, Color.CORAL, Color.BLUEVIOLET, Color.CHOCOLATE, Color.BLUE};

        for (int i = 0; i < 5; i++) {
            Rectangle rect = new Rectangle(posX, posY, 100, 100);
            rect.setArcHeight(50);
            rect.setArcWidth(50);
            rect.setFill(cols[i]);
            rect.setId("Introanim");

            RotateTransition rotate = new RotateTransition();        //Setting Axis of rotation   
            rotate.setFromAngle(i * 10);

            if (i % 2 == 0) {
                rotate.setAxis(Rotate.Z_AXIS);          // setting the angle of rotation   
            } else {
                rotate.setAxis(Rotate.Y_AXIS);          // setting the angle of rotation   
            }
            rotate.setByAngle(360);          //setting cycle count of the rotation   
            rotate.setCycleCount(i * 100);        //Setting duration of the transition   
            rotate.setDuration(javafx.util.Duration.seconds(5));        //the transition will be auto reversed by setting this to true   
            rotate.setAutoReverse(true);          //setting Rectangle as the node onto which the   
            rotate.setNode(rect);
            rotate.play();
            st.getChildren().add(rect);

        }

        root.getChildren().add(st);

    }
}
