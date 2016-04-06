import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// to do:
// place and put errors where they exist :::::::: DONE
// make another one where it just changes boolean values to true and false and will spit out the errors when  validate button is pushed ::::: in progress
public class Model {

	public static Text t;
	public static Text t2;
	public static int numMensMorris = 6; // variable to store the number of mens
											// morris ie. 6
	private static Point[][] boardState; // 2D array; inner arrays represent
											// individual shells, outer array
											// holds all shells
	private static Circle[] bluepiecelist; // keeps track of pieces on side of
											// the board
	private static Circle[] redpiecelist;
	public static boolean PlayerTurn = true; // true=blue false=red
	public static int redCount = 0; // counts number of red pieces currently
									// placed
	public static int blueCount = 0;
	public static boolean duplicate = false; // for Sandbox mode to check if
												// duplicates have been placed
	public static boolean numPieces = false; // for sandbox mode to check if too
												// many pieces have been placed
	public static boolean rmPiece = false; // when a mill is formed and a piece
											// must be removed
	public static boolean NewGame = false; // to keep track of whether newgame
											// has been pressed
	public static boolean isAIgame = false;
	public static boolean isSbox = false; // is sandbox to keep track of whether
											// it is in sandbox mode
	public static String lastcolor = "white"; // the last color picked
	public static String pickedcolor = "black"; // the color currently selected
	public static int phase = 1;

	public static Circle getbluepiecelist(int i) {
		return bluepiecelist[i];
	}

	/*
	 * getter for a circle on the side of the board
	 */
	public static Circle getredpiecelist(int i) {
		return redpiecelist[i];
	}

	/*
	 * returns a specific point in the board array
	 */
	public static Point getboardState(int i, int j) {
		return boardState[i][j];
	}

	public static boolean AIformedMill(Point x,Color c) {// check if mill was formed
													// with
		// the last click
		boolean millfound = false;
		int i = x.getI();
		int j = x.getJ();

		if (j % 2 == 0) { // if point is a corner piece
			if (j == 0) { // special case for 0 corner
				if (c.equals(boardState[i][j + 7].checkColor())
						&& c.equals(boardState[i][j + 6].checkColor())) {
					millfound = true;
				}

				if (c.equals(boardState[i][j + 1].checkColor())
						&& c.equals(boardState[i][j + 2].checkColor())) {
					millfound = true;
				}
			} else if (j == 6) { // special case for 6 corner
				if (c.equals(boardState[i][j - 1].checkColor())
						&& c.equals(boardState[i][j - 2].checkColor())) {
					millfound = true;
				}

				if (c.equals(boardState[i][j + 1].checkColor())
						&& c.equals(boardState[i][j - 6].checkColor())) {
					millfound = true;
				}

			} else {

				if (c.equals(boardState[i][j - 1].checkColor())
						&& c.equals(boardState[i][j - 2].checkColor())) {
					millfound = true;
				}

				if (c.equals(boardState[i][j + 1].checkColor())
						&& c.equals(boardState[i][j + 2].checkColor())) {
					millfound = true;
				}
			}
		}
		return millfound;

	}

	public static boolean formedMill(Point x) {// check if mill was formed with
												// the last click
		boolean millfound = false;
		int i = x.getI();
		int j = x.getJ();

		if (j % 2 == 0) { // if point is a corner piece
			if (j == 0) { // special case for 0 corner
				if (boardState[i][j].checkColor().equals(boardState[i][j + 7].checkColor())
						&& boardState[i][j].checkColor().equals(boardState[i][j + 6].checkColor())) {
					millfound = true;
				}

				if (boardState[i][j].checkColor().equals(boardState[i][j + 1].checkColor())
						&& boardState[i][j].checkColor().equals(boardState[i][j + 2].checkColor())) {
					millfound = true;
				}
			} else if (j == 6) { // special case for 6 corner
				if (boardState[i][j].checkColor().equals(boardState[i][j - 1].checkColor())
						&& boardState[i][j].checkColor().equals(boardState[i][j - 2].checkColor())) {
					millfound = true;
				}

				if (boardState[i][j].checkColor().equals(boardState[i][j + 1].checkColor())
						&& boardState[i][j].checkColor().equals(boardState[i][j - 6].checkColor())) {
					millfound = true;
				}

			} else {

				if (boardState[i][j].checkColor().equals(boardState[i][j - 1].checkColor())
						&& boardState[i][j].checkColor().equals(boardState[i][j - 2].checkColor())) {
					millfound = true;
				}

				if (boardState[i][j].checkColor().equals(boardState[i][j + 1].checkColor())
						&& boardState[i][j].checkColor().equals(boardState[i][j + 2].checkColor())) {
					millfound = true;
				}
			}

		}

		else if (j % 2 != 0) { // for 1,3,5,7 middle points
			if (boardState[i][j].checkColor().equals(boardState[i][j - 1].checkColor())
					&& boardState[i][j].checkColor().equals(boardState[i][j + 1].checkColor())) {
				millfound = true;
				System.out.print("middle");
			}
		}

		return millfound;

	}

	// just for making the board and placing the lines
	public static Group shellmaker(int shellnum) { // creates the model for the
													// board
		boardState = new Point[shellnum][8]; // initialize the board array
		double startX = 400; // board size
		double startY = 300;
		double spacing = 60;
		Line line31; // the four lines that connect the midpoints for each shell
		Line line71;
		Line line11;
		Line line51;
		Group nodes = new Group();
		for (int shell = 1; shell < shellnum + 1; shell++) { // loop for the
																// number of
																// shells you
																// want

			Point point0 = new Point(startX - spacing * shell, startY - spacing * shell, shell - 1, 0); // create
																										// a
																										// point
																										// and
																										// store
																										// it
			boardState[shell - 1][0] = point0;
			Point point1 = new Point(startX, startY - spacing * shell, shell - 1, 1);
			boardState[shell - 1][1] = point1;
			Point point2 = new Point(startX + spacing * shell, startY - spacing * shell, shell - 1, 2);
			boardState[shell - 1][2] = point2;
			Point point3 = new Point(startX + spacing * shell, startY, shell - 1, 3);
			boardState[shell - 1][3] = point3;
			Point point4 = new Point(startX + spacing * shell, startY + spacing * shell, shell - 1, 4);
			boardState[shell - 1][4] = point4;
			Point point5 = new Point(startX, startY + spacing * shell, shell - 1, 5);
			boardState[shell - 1][5] = point5;
			Point point6 = new Point(startX - spacing * shell, startY + spacing * shell, shell - 1, 6);
			boardState[shell - 1][6] = point6;
			Point point7 = new Point(startX - spacing * shell, startY, shell - 1, 7);
			boardState[shell - 1][7] = point7;
			nodes.getChildren().addAll(point0, point1, point2, point3, point4, point5, point6, point7);// add
																										// all
																										// points
																										// to
																										// group
			if (shell < shellnum) {
				line31 = new Line(startX + spacing * shell + 10, startY + 10, startX + spacing * (shell + 1),
						startY + 10); // create lines between the points
				line71 = new Line(startX - spacing * shell + 10, startY + 10, startX - spacing * (shell + 1),
						startY + 10);
				line11 = new Line(startX + 10, startY - spacing * shell + 10, startX + 10,
						startY - spacing * (shell + 1) + 10);
				line51 = new Line(startX + 10, startY + spacing * shell + 10, startX + 10,
						startY + spacing * (shell + 1) + 10);
			} else {
				line31 = new Line(0, 0, 0, 0);
				line71 = new Line(0, 0, 0, 0);
				line11 = new Line(0, 0, 0, 0);
				line51 = new Line(0, 0, 0, 0);
			}

			Line line1 = new Line(point0.x + 20, point0.y + 10, point1.x + 10, point1.y + 10);
			Line line2 = new Line(point1.x + 20, point1.y + 10, point2.x + 10, point2.y + 10);
			Line line3 = new Line(point2.x + 10, point2.y + 10, point3.x + 10, point3.y + 10);
			Line line4 = new Line(point3.x + 10, point3.y + 10, point4.x + 10, point4.y + 10);
			Line line5 = new Line(point4.x + 10, point4.y + 10, point5.x + 10, point5.y + 10);
			Line line6 = new Line(point5.x + 10, point5.y + 10, point6.x + 10, point6.y + 10);
			Line line7 = new Line(point6.x + 10, point6.y + 10, point7.x + 10, point7.y + 10);
			Line line0 = new Line(point0.x + 10, point0.y + 10, point7.x + 10, point7.y + 10);
			nodes.getChildren().addAll(line0, line1, line2, line3, line4, line5, line6, line7, line31, line11, line51,
					line71);
		}
		Button redbutton = new Button("Place a red piece"); // create red piece
															// button for
															// sandbox
		redbutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Controller.redbutton(); // call controller method for redbutton
			}
		});
		nodes.getChildren().add(redbutton); // add button to group

		Button bluebutton = new Button("Place a blue piece");

		bluebutton.setTranslateY(30);
		bluebutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Controller.bluebutton();

			}
		});

		nodes.getChildren().add(bluebutton);
		Circle sidepiece; // the pieces on the side of the board
		Circle sidepiece1;
		Color sidepiececolor = Color.BLUE;
		redpiecelist = new Circle[numMensMorris]; // start piecelist
		bluepiecelist = new Circle[numMensMorris];
		for (int i = 0; i < numMensMorris; i++) { // populate piecelist
			sidepiece = new Circle(100 + 20 * i, 200, 10, sidepiececolor);
			sidepiece1 = new Circle(100 + 20 * i, 400, 10, Color.RED);
			bluepiecelist[i] = sidepiece;
			redpiecelist[i] = sidepiece1;

			nodes.getChildren().addAll(sidepiece, sidepiece1);
		}

		Button newgame = new Button("New Game"); // newgame button
		newgame.setTranslateX(350);

		newgame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Controller.newgameButton(); // call controller method
			}
		});

		nodes.getChildren().add(newgame);

		Button validButton = new Button("Validate");
		validButton.setTranslateX(700);
		validButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Controller.validButton(); // validButton controller method
			}
		});

		nodes.getChildren().add(validButton);

		Button sboxButton = new Button("Switch to Sandbox mode");
		sboxButton.setTranslateX(340);
		sboxButton.setTranslateY(500);
		sboxButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				Controller.sBoxButtonControl();
			}
		});

		nodes.getChildren().add(sboxButton);

		t = new Text();
		t.setY(100);
		t.setTextAlignment(TextAlignment.CENTER);
		t.setFont(new Font(20));
		t.setText("CLICK NEW GAME");
		t2 = new Text();
		t2.setY(150);
		t2.setTextAlignment(TextAlignment.CENTER);
		t2.setFont(new Font(20));
		t2.setText("Phase 1");
		nodes.getChildren().addAll(t, t2);

		Button savegame = new Button("Save Game"); // savegame button
		savegame.setTranslateX(700);
		savegame.setTranslateY(100);
		savegame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Controller.savegameButton();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // call controller method
			}
		});

		nodes.getChildren().add(savegame);

		Button loadgame = new Button("Load Game"); // loadgame button
		loadgame.setTranslateX(700);
		loadgame.setTranslateY(130);
		loadgame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Controller.loadgameButton();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // call controller method
			}
		});
		nodes.getChildren().add(loadgame);

		Button AIgame = new Button("AI Game"); // loadgame button
		AIgame.setTranslateX(350);
		AIgame.setTranslateY(30);
		AIgame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Controller.AIbutton();
			}
		});

		nodes.getChildren().add(AIgame);

		return nodes;
	}

	/*
	 * resets all state variables for a new game
	 */
	public static void reset() { // reset the board to the default state
		redCount = 0;
		blueCount = 0;
		duplicate = false;
		numPieces = false;
		rmPiece = false;
		lastcolor = "white";
		pickedcolor = "black";
		phase = 1;

	}

	/*
	 * stores state variables into a text file
	 */
	public static void saveGame() throws IOException {
		BufferedWriter in = new BufferedWriter(new FileWriter(new File("data.txt")));
		/*
		 * To store
		 * redcount,bluecount,duplicate,numpieces,rmpiece,lastcolor,pickedcolor,
		 * bluepiecelist,redpiecelist,boardstate
		 * 
		 */
		in.write(redCount + "");
		in.newLine();
		in.write(blueCount + "");
		in.newLine();
		in.write(phase + "");
		in.newLine();
		in.write(duplicate + "");
		in.newLine();
		in.write(numPieces + "");
		in.newLine();
		in.write(rmPiece + "");
		in.newLine();
		in.write(lastcolor);
		in.newLine();
		in.write(pickedcolor);
		in.newLine();
		String bplist = "";
		String rplist = "";
		String bstate = "";
		for (int i = 0; i < bluepiecelist.length; i++) {

			bplist = bplist + bluepiecelist[i].getFill() + ",";
		}
		for (int i = 0; i < redpiecelist.length; i++) {

			rplist = rplist + redpiecelist[i].getFill() + ",";
		}
		for (int o = 0; o < boardState.length; o++) {
			for (int p = 0; p < boardState[o].length; p++) {

				bstate = bstate + boardState[o][p].circle.getFill() + ",";
			}
		}
		in.write(bplist);
		in.newLine();
		in.write(rplist);
		in.newLine();
		in.write(bstate);
		in.newLine();
		in.close();
	}

	/*
	 * loads txt file values into state variables
	 */
	public static void loadGame() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File("data.txt")));
		isSbox = false;
		NewGame = true;
		/*
		 * To get
		 * redcount,bluecount,duplicate,numpieces,rmpiece,lastcolor,pickedcolor,
		 * bluepiecelist,redpiecelist,boardstate
		 * 
		 */

		redCount = Integer.parseInt(in.readLine());
		blueCount = Integer.parseInt(in.readLine());
		phase = Integer.parseInt(in.readLine());
		duplicate = Boolean.parseBoolean(in.readLine());
		numPieces = Boolean.parseBoolean(in.readLine());
		rmPiece = Boolean.parseBoolean(in.readLine());
		lastcolor = in.readLine();
		pickedcolor = in.readLine();

		String[] bpcolors = in.readLine().split(",");
		String[] rpcolors = in.readLine().split(",");
		String[] bscolors = in.readLine().split(",");
		for (int i = 0; i < bluepiecelist.length; i++) {
			bluepiecelist[i].setFill(Color.web(bpcolors[i]));
		}
		for (int i = 0; i < redpiecelist.length; i++) {
			redpiecelist[i].setFill(Color.web(rpcolors[i]));
		}
		int k = -1;
		for (int o = 0; o < boardState.length; o++) {
			for (int p = 0; p < boardState[o].length; p++) {
				k++;
				boardState[o][p].circle.setFill(Color.web(bscolors[k]));
			}
		}
		in.close();

	}

}
