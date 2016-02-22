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



public class Point extends StackPane{
		double x,y;
		double sizenode = 10; 	
		Circle circle;
		Color nodecolor = Color.BLACK;	
		private void changeNodeColor(Color x){									//method to change the color of a given node
			circle.setFill(x);
		}
		public Point(double x, double y){
			this.x =x;
			this.y =y;
			circle = new Circle (x, y, sizenode, nodecolor);
			setTranslateX(x);
			setTranslateY(y);
			getChildren().addAll(circle);    
			
			setOnMouseClicked(new EventHandler<MouseEvent>()    		
			{

				@Override
				public void handle(MouseEvent t) {
					// checks Model.duplicate placing
					if (Model.pickedcolor == "sBox"| Model.isSbox == true) {
						Model.isSbox = true;
						if (!(circle.getFill().toString().equals("0x000000ff"))){
							Model.duplicate = true;
						}
						//check for placing more than 6 pieces in total 
						else if (Model.redCount >Model.numMensMorris && Model.pickedcolor == "red") {
							Model.numPieces = true;
						}
						else if (Model.blueCount> Model.numMensMorris && Model.pickedcolor == "blue") {
							Model.numPieces = true;
						}
						if (Model.pickedcolor == "red"){                		
							circle.setFill(Color.RED);
							Model.redCount++;
							
						}
						else if (Model.pickedcolor == "blue"){                		
							circle.setFill(Color.BLUE);
							Model.blueCount++;
							
						}
						
						
					}					
					else if(Model.rmPiece==true){
						if(Model.pickedcolor.equals("removered")){
							if(circle.getFill()==Color.RED){
								Model.redpiecelist[Model.redCount-1].setFill(Color.RED);
								circle.setFill(Color.BLACK);								
								Model.rmPiece=false;
								Model.StartTurn();
								Model.removeFromArray(circle.getCenterX(),circle.getCenterY());
							}
							else{
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("Invalid move");
								alert.setContentText("You can only remove red discs");
								alert.showAndWait();
							}
							
						}
						
						else{
							if(circle.getFill()==Color.BLUE){
								circle.setFill(Color.BLACK);
								Model.bluepiecelist[Model.blueCount-1].setFill(Color.BLUE);
								Model.rmPiece=false;
								Model.StartTurn();
								Model.removeFromArray(circle.getCenterX(),circle.getCenterY());
							}
							else{
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("Invalid move");
								alert.setContentText("You can only remove blue discs");
								alert.showAndWait();
							}
							
						}
					}
					else {
					if (!(circle.getFill().toString().equals("0x000000ff"))){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("You cannot place one piece on top of another");
						alert.setContentText("Please try again");
						alert.showAndWait();

					}
					else if(Model.redCount>=Model.numMensMorris&&Model.blueCount>=Model.numMensMorris){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("Phase 1 has ended");
						alert.setContentText("Game will resume with Phase 2");
						alert.showAndWait();
					}
					//check for placing more than 6 pieces in total 
					else if (Model.redCount >Model.numMensMorris && Model.pickedcolor == "red") {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("You cannot place more than 6 pieces");
						alert.setContentText("Please try using another color");
						alert.showAndWait();

					}
					else if (Model.blueCount> Model.numMensMorris && Model.pickedcolor == "blue") {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("You cannot place more than 6 pieces");
						alert.setContentText("Please try using another color");
						alert.showAndWait();

					}
					
					else if (Model.pickedcolor == "red"){ 
						if (Model.lastcolor.equals("red")){												//for regular play, cannot place two of the same piece in a row
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("It is the blue players turn");
							alert.setContentText("Please try using another color");
							alert.showAndWait();
						}
						else{
							circle.setFill(Color.RED);
							Model.redpiecelist[0+Model.redCount].setFill(Color.BEIGE);
							Model.redCount++;
							
							Model.addToArrays(circle.getCenterX(),circle.getCenterY());
							//System.out.println(circle.getCenterX()+" "+circle.getCenterY());
							if(Model.formedMill(circle.getCenterX(),circle.getCenterY())==true){
								Model.rmPiece=true;
								Model.pickedcolor="removeblue";
								Model.lastcolor = "red";
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Red formed a mill");
								alert.setHeaderText("You can now remove a blue disc");
								alert.setContentText("Click on a blue disc to remove it.");
								alert.showAndWait();
							}
							else{
							Model.StartTurn();
							//System.out.println(Model.pickedcolor);
							Model.lastcolor = "red";
							if(Model.redCount>Model.numMensMorris&&Model.blueCount>Model.numMensMorris){
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("Phase 1 has ended");
								alert.setContentText("Game will resume with Phase 2");
								alert.showAndWait();
							}
							}
						}
					}
					
					else if(Model.pickedcolor == "blue"){												//for regular play, cannot place two of the same piece in a row
						if (Model.lastcolor.equals("blue")){
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("It is the red players turn");
							alert.setContentText("Please try using another color");
							alert.showAndWait();
						}
						else{
							circle.setFill(Color.BLUE);
							Model.bluepiecelist[0+Model.blueCount].setFill(Color.BEIGE);
							Model.blueCount++;
							Model.addToArrays(circle.getCenterX(),circle.getCenterY());
							//System.out.println(circle.getCenterX()+" "+circle.getCenterY());
							if(Model.formedMill(circle.getCenterX(),circle.getCenterY())==true){
								Model.rmPiece=true;
								Model.pickedcolor="removered";
								Model.lastcolor = "blue";
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Blue formed a mill");
								alert.setHeaderText("You can now remove a red disc");
								alert.setContentText("Click on a red disc to remove it.");
								alert.showAndWait();
							}
							else{
								
							
							Model.StartTurn();
							//System.out.println(Model.pickedcolor);
							Model.lastcolor = "blue";
							if(Model.redCount>Model.numMensMorris&&Model.blueCount>Model.numMensMorris){
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("Phase 1 has ended");
								alert.setContentText("Game will resume with Phase 2");
								alert.showAndWait();
							}
							}
						}
					}										
					
					}
					
				}
				
				
			});
		}    	
	}