import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class View extends Application {
	private static Group nodes;								//group to display in scene
	public static void main(String[] args) {
		//create the board model
		launch();
	}

	
	@Override
	public void start(Stage args0) throws Exception {
		// TODO Auto-generated method stub
		Model.initxycheck();		
		nodes = Model.shellmaker(Model.numMensMorris/3);		//call shellmaker from Model Class
		Scene scene1 = new Scene(nodes,800,600,Color.BEIGE);
		
		args0.setScene(scene1);	
		args0.setTitle("6 mens morris");
		args0.show(); 
	}}
