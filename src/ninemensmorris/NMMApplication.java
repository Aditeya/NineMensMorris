/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 *
 * @author aditeya
 */
public class NMMApplication extends Application {

   
    private char marker;
    private boolean myTurn = false;
    private char whoseTurn = 'X';
            int row_colnum = 5;

    private Cell[][] cell = new Cell[row_colnum][row_colnum];
    private Label lblStatus = new Label("X's turn to play");
    private Label lblIdentification = new Label();

    public void start(Stage primaryStage) {
  
	GridPane pane = new GridPane();
	for (int i = 0; i < row_colnum; ++i)
	    for (int j = 0; j < row_colnum; ++j) 
		pane.add(cell[i][j] = new Cell(i,j), j, i);
Button btn = new Button("OO");
	BorderPane borderPane = new BorderPane();
	borderPane.setCenter(pane);
        borderPane.setLeft(btn);
	borderPane.setTop(lblStatus);
	borderPane.setBottom(lblIdentification);
	
	Scene scene = new Scene(borderPane, 750, 750);
	primaryStage.setTitle("Nine Men's Morris");
	primaryStage.setScene(scene);
	primaryStage.show();
	
        
        btn.onMousePressedProperty(e -> {
        
	new Thread( () -> {
		try {
		    initializeRMI();
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}).start();
    });

    protected boolean initializeRMI() throws Exception {
	String host = "";
	try {
            
	} catch (Exception ex) {
	    System.out.println(ex.getMessage());
	}
        return false;

    }

    public void setMyTurn(boolean myTurn) {
	this.myTurn = myTurn;
    }

    public void setMessage(String message) {
    }

    public void mark(int row, int column, char token) {
    }

    public class Cell extends Pane {
	private boolean marked = false;
	int row, column;
	private char token = ' ';

	public Cell(final int row, final int column) {
	    this.row = row;
	    this.column = column;
	    setStyle("-fx-border-color: black");
	    this.setPrefSize(2000,2000);
	    this.setOnMouseClicked(e -> handleMouseClick());
	}

	public char getToken() {
	    return token;
	}

	public void setToken(char c) {
	    token = c;
	    marked = true;

	    if (token == 'X') {
		Line line1 = new Line(10, 10, this.getWidth() - 10,
				      this.getHeight() - 10);
		line1.endXProperty().bind(this.widthProperty().subtract(10));
		line1.endYProperty().bind(this.heightProperty().subtract(10));

		Line line2 = new Line(10, this.getHeight() - 10,
				      this.getWidth() - 10, 10);
		line2.startYProperty().bind(this.heightProperty().subtract(10));
		line2.endXProperty().bind(this.widthProperty().subtract(10));

		Platform.runLater(() -> this.getChildren().addAll(line1, line2));
	    } else if (token == 'O') {
		Ellipse ellipse = new Ellipse(this.getWidth() / 2,
					      this.getHeight() / 2,
					      this.getWidth() / 2 - 10,
					      this .getHeight() / 2 - 10);
		ellipse.centerXProperty().bind(this.widthProperty().divide(2));
		ellipse.centerYProperty().bind(this.heightProperty().divide(2));
		ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
		ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
		ellipse.setStroke(Color.BLACK);
		ellipse.setFill(Color.WHITE);

		Platform.runLater(() -> getChildren().add(ellipse));
	    }
	}

	private void handleMouseClick() {
	    if (myTurn && !marked) {
		setToken(marker);
	    }
	}
    }

    public static void main(String[] args) {
	launch(args);
    }
}

