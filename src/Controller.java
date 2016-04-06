import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Controller { // controller for the MVC model
	private static Boolean secondclick = false;
	private static Point firstpoint;
	private static Paint firstcolor;
	private static int redcounter;
	private static int bluecounter;

	/*
	 * method to count number of blue and red pieces on the board at the end of phase 1
	 */
	public static void getColorCount() {
		for (int i = 0; i < Model.numMensMorris / 3; i++) {
			for (int j = 0; j < 8; j++) {
				if (Model.getboardState(i, j).circle.getFill().equals(Color.RED)) {
					redcounter++;
				} else if (Model.getboardState(i, j).circle.getFill().equals(Color.BLUE)) {
					bluecounter++;
				}
			}

		}
	}

	public static void AIbutton() {
		Model.NewGame = true;
		Model.isAIgame = true;
		Model.isSbox = false;
		turnRandomizer();
		Model.t.setText("Game in progress.");
		Model.reset();
		if (Model.redCount >= 0 || Model.blueCount >= 0) {
			for (int i = 0; i < Model.numMensMorris / 3; i++) {
				for (int j = 0; j < 8; j++) {
					Model.getboardState(i, j).circle.setFill(Color.BLACK);
				}
			}
			for (int i = 0; i < Model.numMensMorris; i++) {

				Model.getbluepiecelist(i).setFill(Color.BLUE);
				Model.getredpiecelist(i).setFill(Color.RED);
			}
		}
		Model.pickedcolor = "newgame";

		if (Model.pickedcolor.equals("newgame") || Model.NewGame == true) {
			if (Model.PlayerTurn == true) {
				Model.pickedcolor = "blue";
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("New Game has started.");
				alert.setContentText("Blue will start the game.");
				alert.showAndWait();
			} else {
				Model.pickedcolor = "red";
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("New Game has started.");
				alert.setContentText("Computer will start the game.");
				alert.showAndWait();
			}

		}
	}

	/*
	 * redbutton control to be used in sandbox mode
	 */
	public static void redbutton() {
		if (Model.isSbox != true) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Not in Sandbox mode");

			alert.setContentText("This button can only be used in sandbox mode.");
			alert.showAndWait();
		} else {
			Model.pickedcolor = "red";
		}
	}

	/*
	 * same as redbutton
	 */
	public static void bluebutton() {
		if (Model.isSbox != true) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Not in Sandbox mode");
			alert.setContentText("This button can only be used in sandbox mode.");
			alert.showAndWait();
		} else {
			Model.pickedcolor = "blue";
		}
	}

	/*
	 * resets the board and calls reset() method to reset variables. Displays a
	 * message that picks which player will go first.
	 */
	public static void newgameButton() {

		Model.NewGame = true;
		Model.isSbox = false;
		turnRandomizer();
		Model.t.setText("Game in progress.");
		Model.reset();
		if (Model.redCount >= 0 || Model.blueCount >= 0) {
			for (int i = 0; i < Model.numMensMorris / 3; i++) {
				for (int j = 0; j < 8; j++) {
					Model.getboardState(i, j).circle.setFill(Color.BLACK);
				}
			}
			for (int i = 0; i < Model.numMensMorris; i++) {

				Model.getbluepiecelist(i).setFill(Color.BLUE);
				Model.getredpiecelist(i).setFill(Color.RED);
			}
		}
		Model.pickedcolor = "newgame";

		if (Model.pickedcolor.equals("newgame") || Model.NewGame == true) {
			if (Model.PlayerTurn == true) {
				Model.pickedcolor = "blue";
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("New Game has started.");
				alert.setContentText("Blue will start the game.");
				alert.showAndWait();
			} else {
				Model.pickedcolor = "red";
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("New Game has started.");
				alert.setContentText("Red will start the game.");
				alert.showAndWait();
			}

		}

	}

	/*
	 * Deletes the selected point and colors the corresponding sidepiece green
	 */
	public static void Delete(Point p) {

		if (Model.pickedcolor.equals("removered")) {
			if (p.circle.getFill().equals(Color.RED)) {
				p.circle.setFill(Color.BLACK);
				Model.getredpiecelist(Model.redCount - 1).setFill(Color.GREEN);
				Model.rmPiece = false;
				StartTurn();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Invalid move");
				alert.setContentText("You can only remove red discs");
				alert.showAndWait();
			}

		}

		else {
			if (p.circle.getFill().equals(Color.BLUE)) {
				p.circle.setFill(Color.BLACK);
				Model.getbluepiecelist(Model.blueCount - 1).setFill(Color.GREEN);
				Model.rmPiece = false;
				StartTurn();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Invalid move");
				alert.setContentText("You can only remove blue discs");
				alert.showAndWait();
			}

		}
	}

	/*
	 * all controller logic for a point in phase 1. Accepts cases for sandbox
	 * mode and removing a piece
	 */
	public static void pointclicked(Point p) { // if a point is clicked in phase
		// 1 use this method
		/*
		 * series of methods for sandbox mode to determine whether a sequence is
		 * valid
		 */

		if (Model.pickedcolor.equals("sBox") | Model.isSbox == true) { // check
			// if
			// sandbox
			// mode is
			// on

			Model.isSbox = true;
			if (!(p.circle.getFill().toString().equals("0x000000ff"))) {
				Model.duplicate = true;
			}
			// check for placing more than 6 pieces in total
			else if (Model.redCount >= Model.numMensMorris && Model.pickedcolor.equals("red")) {
				Model.numPieces = true;
			} else if (Model.blueCount >= Model.numMensMorris && Model.pickedcolor.equals("blue")) {
				Model.numPieces = true;
			}
			if (Model.pickedcolor.equals("red")) {
				p.circle.setFill(Color.RED);
				Model.redCount++;

			} else if (Model.pickedcolor.equals("blue")) {
				p.circle.setFill(Color.BLUE);
				Model.blueCount++;

			}

		}
		/*
		 * If a player forms a mill and needs to remove a piece of the opposite
		 * color
		 */
		else {
			
			if (Model.isAIgame == true && Model.PlayerTurn == false){ //if its an AI game and its reds turn
				boolean placed = false;
				boolean aimill = false;
				for (int i = 0; i < Model.numMensMorris/3; i++) { // loop through
					// boardstate
					for (int j = 0; j < 8; j++) {
						if (Model.getboardState(i, j).checkColor().equals(Color.BLACK)) { //check if spot is empty
							if (Model.AIformedMill(Model.getboardState(i, j),Color.RED) == true && placed==false) {	//check if placing will form a mill 	
								Model.getboardState(i, j).circle.setFill(Color.RED);	
								placed=true;
								aimill=true;
								
								Model.getredpiecelist(0 + Model.redCount).setFill(Color.BEIGE); // remove
								// sidepiece
								Model.redCount++;
								
							}
						}

					}

				}
				if(aimill==true){ //removing pieces
					boolean removed=false;
					int[] numBlueOnShell = new int[Model.numMensMorris/3];
					for(int i=0;i<Model.numMensMorris/3;i++){
						for (int j = 0; j < 8; j++) {
							if (Model.getboardState(i, j).checkColor().equals(Color.BLUE)) { //check if spot is empty
								numBlueOnShell[i]++;							
							}

						}
					}
					int max=0;
					int index=0;
					for (int k =0; k<Model.numMensMorris/3; k++){
						if (numBlueOnShell[k] > max){
							max = numBlueOnShell[k];
							index=k;
						}
					}
					for (int j = 0; j < 8; j++) {
						if(Model.getboardState(index, j).checkColor().equals(Color.BLUE) && removed==false){
							
							Model.getboardState(index, j).circle.setFill(Color.BLACK);	
							removed=true;
							
						}
						
					}
					
				}
				if(placed==false){
					for (int i = 0; i < Model.numMensMorris/3; i++) { // loop through
						// boardstate
						for (int j = 0; j < 8; j++) {
							if (Model.getboardState(i, j).checkColor().equals(Color.BLACK)) { //check if spot is empty
								if (Model.AIformedMill(Model.getboardState(i, j),Color.BLUE) == true && placed==false) {	//block enemy mills	
									Model.getboardState(i, j).circle.setFill(Color.RED);	
									placed=true;									
									Model.getredpiecelist(0 + Model.redCount).setFill(Color.BEIGE); // remove
									// sidepiece
									Model.redCount++;
								}
							}

						}

					}
				}
				if(placed==false){
					int[] numRedOnShell = new int[Model.numMensMorris/3];
					int[] numBlackOnShell = new int[Model.numMensMorris/3];
					for(int i=0;i<Model.numMensMorris/3;i++){
						for (int j = 0; j < 8; j++) {
							if (Model.getboardState(i, j).checkColor().equals(Color.RED)) { //check if spot is empty
								numRedOnShell[i]++;							
							}
							else if(Model.getboardState(i, j).checkColor().equals(Color.BLACK)) { //check if spot is empty
								numBlackOnShell[i]++;							
							}

						}
					}
					int max=0;
					int index=0;
					for (int k =0; k<Model.numMensMorris/3; k++){
						if (numRedOnShell[k] > max && numBlackOnShell[k]!=0){
							max = numRedOnShell[k];
							index=k;
						}
					}
					for (int j = 0; j < 8; j++) {
						if(Model.getboardState(index, j).checkColor().equals(Color.BLACK) && placed==false){
							Model.getboardState(index, j).circle.setFill(Color.RED);	
							placed=true;
							Model.getredpiecelist(0 + Model.redCount).setFill(Color.BEIGE); // remove
							// sidepiece
							Model.redCount++;
						}
						
					}
				}
				StartTurn();
				Model.lastcolor = "red";
				if (Model.redCount >= Model.numMensMorris && Model.blueCount >= Model.numMensMorris) {
					Model.phase = 2;
					getColorCount();
				}
				
				
				// check for rows with possible millform, if not found check for
				// blocking mills
			} else {
				if (Model.rmPiece) {
					Delete(p);
				} else {

					if (!(p.circle.getFill().equals(Color.BLACK))) { // if space
						// is
						// not
						// black
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("You cannot place one piece on top of another");
						alert.setContentText("Please try again");
						alert.showAndWait();

					} else if (Model.redCount >= Model.numMensMorris && Model.blueCount >= Model.numMensMorris) { // if
						// each
						// player
						// has
						// played
						// all
						// their
						// pieces
						Model.phase = 2;
						getColorCount(); // color count for determining winner
						// of phase 2. counts number of
						// pieces on board after phase 1.
					}
					// check for placing more than 6 pieces in total
					else if (Model.redCount >= Model.numMensMorris && Model.pickedcolor.equals("red")) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("You cannot place more than 6 pieces");
						alert.setContentText("Please try using another color");
						alert.showAndWait();

					} else if (Model.blueCount >= Model.numMensMorris && Model.pickedcolor.equals("blue")) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("You cannot place more than 6 pieces");
						alert.setContentText("Please try using another color");
						alert.showAndWait();

					}

					else if (Model.pickedcolor.equals("red")) {

						p.circle.setFill(Color.RED); // set point to red
						Model.getredpiecelist(0 + Model.redCount).setFill(Color.BEIGE); // remove
						// sidepiece
						Model.redCount++;
						if (Model.formedMill(p)) {
							Model.rmPiece = true;
							Model.pickedcolor = "removeblue";
							Model.lastcolor = "red";
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Red formed a mill");
							alert.setHeaderText("You can now remove a blue disc");
							alert.setContentText("Click on a blue disc to remove it.");
							alert.showAndWait();
						} else {
							StartTurn();
							Model.lastcolor = "red";
							if (Model.redCount >= Model.numMensMorris && Model.blueCount >= Model.numMensMorris) {
								Model.phase = 2;
								getColorCount();
							}

						}
					}

					else if (Model.pickedcolor.equals("blue")) { // for regular
						// play,
						// cannot
						// place two
						// of the
						// same
						// piece in
						// a row
						p.circle.setFill(Color.BLUE);
						Model.getbluepiecelist(0 + Model.blueCount).setFill(Color.BEIGE);
						Model.blueCount++;

						if (Model.formedMill(p)) {
							Model.rmPiece = true;
							Model.pickedcolor = "removered";
							Model.lastcolor = "blue";
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Blue formed a mill");
							alert.setHeaderText("You can now remove a red disc");
							alert.setContentText("Click on a red disc to remove it.");
							alert.showAndWait();
						} else {

							StartTurn();
							Model.lastcolor = "blue";
							if (Model.redCount >= Model.numMensMorris && Model.blueCount >= Model.numMensMorris) {
								Model.phase = 2;
								getColorCount();
							}
						}

					}

				}
			}
		}

	}

	/*
	 * the validation button for sandbox mode
	 */
	public static void validButton() {
		/*
		 * check the various state variables to find whether a valid sequence
		 * has been made
		 */
		if (Model.isSbox != true) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Not in Sandbox mode");
			alert.setContentText("This button can only be used in sandbox mode.");
			alert.showAndWait();

		} else {
			if (Model.numPieces == true || Model.duplicate == true) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("The sequence of pieces you placed was not valid");
				alert.setContentText("");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("The sequence of pieces you placed was valid");
				alert.setContentText("");
				alert.showAndWait();
			}
		}
	}

	/*
	 * button used to switch to sandbox mode
	 */
	public static void sBoxButtonControl() {
		/*
		 * set state variables
		 */
		Model.t.setText("Place pieces in any order");
		Model.t2.setText("Sandbox Mode");
		Model.pickedcolor = "sBox";
		Model.isSbox = true;
		Model.NewGame = false;
	}

	/*
	 * randomly pick starting player
	 */
	public static void turnRandomizer() {
		/*
		 * create a random turn start
		 */
		int randomnum = (int) (Math.random() * 2);
		if (randomnum == 0)
			Model.PlayerTurn = true;// blue
		else
			Model.PlayerTurn = false;// red
	}

	/*
	 * switches which players turn it is
	 */
	public static void StartTurn() {
		/*
		 * switch the player turn
		 */
		if (Model.PlayerTurn == true) {
			Model.PlayerTurn = false;
			Model.pickedcolor = "red";
		} else {
			Model.PlayerTurn = true;
			Model.pickedcolor = "blue";
		}
	}

	/*
	 * all controller logic for a point clicked in phase 2
	 */
	public static void pointclicked2(Point p) { // if a point is clicked in
		// phase 2 use this method
		// click: change color 1
		// take click2: check if click2==valid
		if (redcounter == 2) {
			Alert alert = new Alert(AlertType.INFORMATION); // game ends if
			// there is only 2
			// pieces left from
			// one player
			alert.setTitle("Information Dialog");
			alert.setHeaderText("CELEBRATION");
			alert.setContentText("GAME OVER: BLUE WINS!!!");
			Model.t.setText("Blue won");
			alert.showAndWait();

		} else if (bluecounter == 2) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("CELEBRATION");
			alert.setContentText("GAME OVER: RED WINS!!!");
			Model.t.setText("Red won");
			alert.showAndWait();

		}
		if (Model.rmPiece) { // if rmPiece is set to true, run delete
			Delete(p);
			Model.t.setText("Phase 2: Click a piece to move");
		} else if (secondclick == false) { // if on the first click
			Model.t.setText("Phase 2: Click a piece to move");
			Model.t2.setText("Phase 2");
			if (p.circle.getFill().equals(Color.BLACK)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Invalid Piece");
				alert.setContentText("You can only move red or blue discs");
				alert.showAndWait();
				return;
			}

			if (Model.PlayerTurn == true && !p.circle.getFill().equals(Color.BLUE)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Invalid Piece");
				alert.setContentText("It's blue's turn!");
				alert.showAndWait();
				return;
			}
			if (Model.PlayerTurn == false && !p.circle.getFill().equals(Color.RED)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Invalid Piece");
				alert.setContentText("It's red's turn");
				alert.showAndWait();
				return;
			}
			firstcolor = p.circle.getFill();
			p.circle.setFill(Color.PALEVIOLETRED); // highlight to show
			// selection
			firstpoint = p;
			secondclick = true;
			return;
		}
		if (secondclick == true) {

			if (p.circle.getFill().equals(Color.BLACK) && inReach(firstpoint, p)) { // if
				// the
				// piece
				// is
				// black
				p.circle.setFill(firstcolor);
				firstpoint.circle.setFill(Color.BLACK);

				if (Model.formedMill(p) == true) {
					Model.rmPiece = true;
					if (Model.PlayerTurn == true) {
						Model.t.setText("Remove a red piece");
						Model.pickedcolor = "removered";
						Model.lastcolor = "blue";
						redcounter--;
					} else {
						Model.t.setText("Remove a blue piece");
						Model.pickedcolor = "removeblue";
						Model.lastcolor = "red";
						bluecounter--;

					}
				} else {

					StartTurn();
				}

			} else {
				firstpoint.circle.setFill(firstcolor); // if the second point is
				// not valid reset first
				// point
			}
			secondclick = false;
		}

	}

	/*
	 * checks whether a point can move to another location, checking for
	 * distance and board restraints
	 */
	public static boolean inReach(Point x, Point y) {

		if (x.getI() == y.getI()) { // if on the same shell
			if (x.getJ() == 0 && (y.getJ() == 7 || y.getJ() == 1)) {// check if
				// J is
				// within
				// one
				// special
				// case
				return true;
			} else if (y.getJ() == 0 && (x.getJ() == 7 || x.getJ() == 1)) {// check
				// if
				// J
				// is
				// within
				// one
				return true;
			} else if (Math.abs(x.getJ() - y.getJ()) == 1) { // else if within
				// one space of
				// eachother
				return true;
			}
		} else if (x.getI() != y.getI()) { // not on the same shell
			if (x.getJ() % 2 != 0 && y.getJ() % 2 != 0 && Math.abs(x.getI() - y.getI()) == 1) { // if
				// the
				// j
				// is
				// odd
				// and
				// they
				// are
				// only
				// one
				// shell
				// away
				if (x.getJ() == y.getJ()) { // if the j's are equal
					return true;
				}
			}
		}
		return false; // if none of the above cases happen return false
	}

	/*
	 * save button to save current boardstate
	 */
	public static void savegameButton() throws IOException {

		Model.saveGame();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Game has been saved.");
		alert.setContentText("Click Load game to access the saved game.");
		alert.showAndWait();

	}

	/*
	 * load game to load game state from the text file
	 */
	public static void loadgameButton() throws IOException {
		Model.loadGame();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Game has been loaded.");
		if (Model.pickedcolor.equals("red")) {
			alert.setContentText("It is now red's turn.");
		} else {
			alert.setContentText("It is now blue's turn.");
		}

		alert.showAndWait();
	}
}
