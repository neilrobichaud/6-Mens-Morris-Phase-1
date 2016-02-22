import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
public class Controller {				//controller for the MVC model
	public static void redbutton(){
		if(Model.isSbox!=true){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Not in Sandbox mode");
			alert.setContentText("This button can only be used in sandbox mode.");
			alert.showAndWait();
		}
		else{
		Model.pickedcolor = "red";	
		}
	}
	
	public static void bluebutton(){
		if(Model.isSbox!=true){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Not in Sandbox mode");
			alert.setContentText("This button can only be used in sandbox mode.");
			alert.showAndWait();
		}
		else{
		Model.pickedcolor = "blue";	
		}
	}
	
	public static void newgameButton(){		
		Model.NewGame=true;
		Model.isSbox=false;
		turnRandomizer();
		if (Model.redCount > 0 || Model.blueCount >0){
			Model.reset();
			for (int i=0;i<Model.numMensMorris/3;i++){
				for(int j=0;j<8;j++){
					Model.getboardState(i,j).circle.setFill(Color.BLACK);
				}
			}
			for (int i=0;i<Model.numMensMorris;i++){

				Model.getbluepiecelist(i).setFill(Color.BLUE);
				Model.getredpiecelist(i).setFill(Color.RED);
			}
		}
		Model.pickedcolor="newgame";

		
		
		if(Model.pickedcolor.equals("newgame")||Model.NewGame==true){
			if(Model.PlayerTurn==true){
			Model.pickedcolor="blue";
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("New Game has started.");
			alert.setContentText("Blue will start the game.");
			alert.showAndWait();
			}
			else{
				Model.pickedcolor="red";
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("New Game has started.");
				alert.setContentText("Red will start the game.");
				alert.showAndWait();
			}
			
		}

	}
	public static void pointclicked(Circle g){
		/*
		 * series of methods for sandbox mode to determine whether a sequence is valid
		 */
		if (Model.pickedcolor == "sBox"| Model.isSbox == true) { //check if sandbox mode is on
			Model.isSbox = true;
			if (!(g.getFill().toString().equals("0x000000ff"))){
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
				g.setFill(Color.RED);
				Model.redCount++;
				
			}
			else if (Model.pickedcolor == "blue"){                		
				g.setFill(Color.BLUE);
				Model.blueCount++;
				
			}
			
			
		}
		/*
		 * If a player forms a mill and needs to remove a piece of the opposite color
		 */
		else if(Model.rmPiece==true){
			if(Model.pickedcolor.equals("removered")){
				if(g.getFill()==Color.RED){
					Model.getredpiecelist(Model.redCount-1).setFill(Color.RED);
					g.setFill(Color.BLACK);								
					Model.rmPiece=false;
					StartTurn();
					Model.removeFromArray(g.getCenterX(),g.getCenterY());
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
				if(g.getFill()==Color.BLUE){
					g.setFill(Color.BLACK);
					Model.getbluepiecelist(Model.blueCount-1).setFill(Color.BLUE);
					Model.rmPiece=false;
					StartTurn();
					Model.removeFromArray(g.getCenterX(),g.getCenterY());
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
		if (!(g.getFill().toString().equals("0x000000ff"))){ //if space is not blank
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("You cannot place one piece on top of another");
			alert.setContentText("Please try again");
			alert.showAndWait();

		}
		else if(Model.redCount>=Model.numMensMorris&&Model.blueCount>=Model.numMensMorris){ //if each player has played all their pieces
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
				g.setFill(Color.RED);	//set point to red
				Model.getredpiecelist(0+Model.redCount).setFill(Color.BEIGE);	//remove sidepiece
				Model.redCount++;
				
				Model.addToArrays(g.getCenterX(),g.getCenterY());
				//System.out.println(g.getCenterX()+" "+g.getCenterY());
				if(Model.formedMill(g.getCenterX(),g.getCenterY())==true){
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
				StartTurn();
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
				g.setFill(Color.BLUE);
				Model.getbluepiecelist(0+Model.blueCount).setFill(Color.BEIGE);
				Model.blueCount++;
				Model.addToArrays(g.getCenterX(),g.getCenterY());				
				if(Model.formedMill(g.getCenterX(),g.getCenterY())==true){
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
					
				
				StartTurn();			
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
	public static void validButton(){
		/*
		 * check the various state variables to find whether a valid sequence has been made
		 */
		if(Model.isSbox!=true){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Not in Sandbox mode");
			alert.setContentText("This button can only be used in sandbox mode.");
			alert.showAndWait();
			
		}
		else{
		if (Model.numPieces==true || Model.duplicate ==true){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("The sequence of pieces you placed was not valid");
			alert.setContentText("");
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("The sequence of pieces you placed was valid");
			alert.setContentText("");
			alert.showAndWait();
		}	
		}
	}
	
	public static void sBoxButtonControl(){
		/*
		 * set state variables
		 */
		Model.pickedcolor = "sBox";
		Model.isSbox=true;
		Model.NewGame=false;
	}

	public static void turnRandomizer(){
		/*
		 * create a random turn start
		 */
    	int randomnum=(int)(Math.random()*2);
    	if(randomnum==0)Model.PlayerTurn=true;//blue
    	else Model.PlayerTurn=false;//red
    }
	public static void StartTurn(){
		/*
		 * switch the player turn
		 */
    	if(Model.PlayerTurn==true){	
    		Model.PlayerTurn=false;
    		Model.pickedcolor="red";
    	}
    	else{
    		Model.PlayerTurn=true;
    		Model.pickedcolor="blue";
    	}
    }
}


