/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nmmguisample;

import javafx.animation.RotateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.application.Application;  
import javafx.scene.Group;  
import javafx.scene.Scene;  
import javafx.scene.paint.Color;  
import javafx.scene.shape.Rectangle;  
import javafx.scene.transform.Rotate;  
import javafx.stage.Stage;  
/**
 *
 * @author LENOVO
 */
public class AnimationComponents {

    public AnimationComponents() {
    }
    
    public void setIntroAnim(Pane root,int posX,int posY,Color colour){
     
        StackPane st = new StackPane();
       Color[] cols = {Color.ALICEBLUE,Color.CORAL,Color.BLUEVIOLET};
        
        for(int i =0;i<3;i++){
            Rectangle rect = new Rectangle (posX, posY, 100, 100);
     rect.setArcHeight(50);
     rect.setArcWidth(50);
     rect.setFill(cols[i]);
     rect.setId("Introanim");
     
     
  
     
     
     
RotateTransition rotate = new RotateTransition();        //Setting Axis of rotation   
        rotate.setFromAngle(i*10);
        
        if(i%2==0)
        rotate.setAxis(Rotate.Z_AXIS);          // setting the angle of rotation   
        else{
                    rotate.setAxis(Rotate.Y_AXIS);          // setting the angle of rotation   
        }
        rotate.setByAngle(360);          //setting cycle count of the rotation   
        rotate.setCycleCount(i*100);        //Setting duration of the transition   
        rotate.setDuration(javafx.util.Duration.seconds(5));        //the transition will be auto reversed by setting this to true   
        rotate.setAutoReverse(true);          //setting Rectangle as the node onto which the   
        rotate.setNode(rect);  
        rotate.play();  
                st.getChildren().add(rect); 

        }
        
        root.getChildren().add(st); 
        
    }
    
    
    
    
}
