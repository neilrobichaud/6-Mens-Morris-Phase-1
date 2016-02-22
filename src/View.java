import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
public class View extends Application {
	public static void main(String[] args) {
		//create the board model
		Model.launch();

	}

	@Override
	public void start(Stage Model.primaryStage) {
		initxycheck();
		Scene scene1 = new Scene(shellmaker(numMensMorris/3),800,600,Color.BEIGE);
		primaryStage.setScene(scene1);	
		primaryStage.setTitle("6 mens morris");
		primaryStage.show();      

	}
}