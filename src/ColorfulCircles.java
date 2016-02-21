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

// to do:
// place and put errors where they exist :::::::: DONE
// make another one where it just changes boolean values to true and false and will spit out the errors when  validate button is pushed ::::: in progress
public class ColorfulCircles extends Application {
	private static int numMensMorris=6;	//variable to store the number of mens morris ie. 6
	
	private boolean PlayerTurn=true;
	private static int redCount = 0;
	private static int blueCount = 0;
	private static String[][] boardState;
	private boolean duplicate = false;
	private boolean numPieces = false;
	private boolean rmPiece=false;
	private boolean NewGame=false;
	private boolean isSbox = false;
	private String lastcolor = "white";
	private String pickedcolor = "black";
	private String [][][]xycheck=new String[2][4][3];//[0][][] for x [1][][] for y
	
	public void initxycheck(){//for various purposes, get rid of nulls when initialized
		for(int i=0;i<xycheck.length;i++){
			for(int j=0;j<xycheck[i].length;j++){
				for(int k=0;k<xycheck[i][j].length;k++){
					xycheck[i][j][k]="noColor";
				}
			}
		}
	}
			
	
	@Override
	public void start(Stage primaryStage) {
		initxycheck();
		Scene scene1 = new Scene(shellmaker(numMensMorris/3),800,600,Color.BEIGE);
		primaryStage.setScene(scene1);	
		primaryStage.setTitle("6 mens morris");
		primaryStage.show();      

	}
	public void check(Point x){
		
	}

	private class Point extends StackPane{
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
					// checks duplicate placing
					if (pickedcolor == "sBox"| isSbox == true) {
						isSbox = true;
						if (!(circle.getFill().toString().equals("0x000000ff"))){
							duplicate = true;
						}
						//check for placing more than 6 pieces in total 
						else if (redCount >numMensMorris && pickedcolor == "red") {
							numPieces = true;
						}
						else if (blueCount> numMensMorris && pickedcolor == "blue") {
							numPieces = true;
						}
						if (pickedcolor == "red"){                		
							circle.setFill(Color.RED);
							redCount++;
							
						}
						else if (pickedcolor == "blue"){                		
							circle.setFill(Color.BLUE);
							blueCount++;
							
						}
						
						
					}					
					else if(rmPiece==true){
						if(pickedcolor.equals("removered")){
							if(circle.getFill()==Color.RED){
								circle.setFill(Color.BLACK);								
								rmPiece=false;
								StartTurn();
								removeFromArray(circle.getCenterX(),circle.getCenterY());
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
								rmPiece=false;
								StartTurn();
								removeFromArray(circle.getCenterX(),circle.getCenterY());
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
					else if(redCount>=numMensMorris&&blueCount>=numMensMorris){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("Phase 1 has ended");
						alert.setContentText("Game will resume with Phase 2");
						alert.showAndWait();
					}
					//check for placing more than 6 pieces in total 
					else if (redCount >numMensMorris && pickedcolor == "red") {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("You cannot place more than 6 pieces");
						alert.setContentText("Please try using another color");
						alert.showAndWait();

					}
					else if (blueCount> numMensMorris && pickedcolor == "blue") {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("You cannot place more than 6 pieces");
						alert.setContentText("Please try using another color");
						alert.showAndWait();

					}
					
					else if (pickedcolor == "red"){ 
						if (lastcolor.equals("red")){												//for regular play, cannot place two of the same piece in a row
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("It is the blue players turn");
							alert.setContentText("Please try using another color");
							alert.showAndWait();
						}
						else{
							circle.setFill(Color.RED);
							redCount++;
							
							addToArrays(circle.getCenterX(),circle.getCenterY());
							//System.out.println(circle.getCenterX()+" "+circle.getCenterY());
							if(formedMill(circle.getCenterX(),circle.getCenterY())==true){
								rmPiece=true;
								pickedcolor="removeblue";
								lastcolor = "red";
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Red formed a mill");
								alert.setHeaderText("You can now remove a blue disc");
								alert.setContentText("Click on a blue disc to remove it.");
								alert.showAndWait();
							}
							else{
							StartTurn();
							//System.out.println(pickedcolor);
							lastcolor = "red";
							if(redCount>numMensMorris&&blueCount>numMensMorris){
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("Phase 1 has ended");
								alert.setContentText("Game will resume with Phase 2");
								alert.showAndWait();
							}
							}
						}
					}
					
					else if(pickedcolor == "blue"){												//for regular play, cannot place two of the same piece in a row
						if (lastcolor.equals("blue")){
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("It is the red players turn");
							alert.setContentText("Please try using another color");
							alert.showAndWait();
						}
						else{
							circle.setFill(Color.BLUE);
							blueCount++;
							addToArrays(circle.getCenterX(),circle.getCenterY());
							//System.out.println(circle.getCenterX()+" "+circle.getCenterY());
							if(formedMill(circle.getCenterX(),circle.getCenterY())==true){
								rmPiece=true;
								pickedcolor="removered";
								lastcolor = "blue";
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Blue formed a mill");
								alert.setHeaderText("You can now remove a red disc");
								alert.setContentText("Click on a red disc to remove it.");
								alert.showAndWait();
							}
							else{
								
							
							StartTurn();
							//System.out.println(pickedcolor);
							lastcolor = "blue";
							if(redCount>numMensMorris&&blueCount>numMensMorris){
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
	public void addToArrays(double x,double y){//keeps track of color in each circle
		if(x==280.0){
			if(y==180.0){
				xycheck[0][0][0]=pickedcolor;
				xycheck[1][0][0]=pickedcolor;
			}
			if(y==300.0){
				xycheck[0][0][1]=pickedcolor;
			}
			if(y==420.0){
				xycheck[0][0][2]=pickedcolor;
				xycheck[1][3][0]=pickedcolor;
			}
		}
		else if(x==340.0){
			if(y==240.0){
				xycheck[0][1][0]=pickedcolor;
				xycheck[1][1][0]=pickedcolor;
			}
			if(y==300.0){
				xycheck[0][1][1]=pickedcolor;
			}
			if(y==360.0){
				xycheck[0][1][2]=pickedcolor;
				xycheck[1][2][0]=pickedcolor;
			}
		}
		else if(x==400.0){
			if(y==180.0){
				xycheck[1][0][1]=pickedcolor;
			}
			if(y==240.0){
				xycheck[1][1][1]=pickedcolor;
			}
			
			if(y==360.0){
				xycheck[1][2][1]=pickedcolor;
			}
			if(y==420.0){
				xycheck[1][3][1]=pickedcolor;
			}
		}
		else if(x==460.0){
			
			if(y==240.0){
				xycheck[0][2][0]=pickedcolor;
				xycheck[1][1][2]=pickedcolor;
			}
			if(y==300.0){
				xycheck[0][2][1]=pickedcolor;
			}
			if(y==360.0){
				xycheck[0][2][2]=pickedcolor;
				xycheck[1][2][2]=pickedcolor;
			}
			
		}
		else if(x==520.0){
			
			if(y==180.0){
				xycheck[0][3][0]=pickedcolor;
				xycheck[1][0][2]=pickedcolor;
			}
			if(y==300.0){
				xycheck[0][3][1]=pickedcolor;
			}
			if(y==420.0){
				xycheck[0][3][2]=pickedcolor;
				xycheck[1][3][2]=pickedcolor;
			}
			
		}
		
	}
	public void removeFromArray(double x,double y){
		if(x==280.0){
		if(y==180.0){
			xycheck[0][0][0]="noColor";
			xycheck[1][0][0]="noColor";
		}
		if(y==300.0){
			xycheck[0][0][1]="noColor";
		}
		if(y==420.0){
			xycheck[0][0][2]="noColor";
			xycheck[1][3][0]="noColor";
		}
	}
	else if(x==340.0){
		if(y==240.0){
			xycheck[0][1][0]="noColor";
			xycheck[1][1][0]="noColor";
		}
		if(y==300.0){
			xycheck[0][1][1]="noColor";
		}
		if(y==360.0){
			xycheck[0][1][2]="noColor";
			xycheck[1][2][0]="noColor";
		}
	}
	else if(x==400.0){
		if(y==180.0){
			xycheck[1][0][1]="noColor";
		}
		if(y==240.0){
			xycheck[1][1][1]="noColor";
		}
		
		if(y==360.0){
			xycheck[1][2][1]="noColor";
		}
		if(y==420.0){
			xycheck[1][3][1]="noColor";
		}
	}
	else if(x==460.0){
		
		if(y==240.0){
			xycheck[0][2][0]="noColor";
			xycheck[1][1][2]="noColor";
		}
		if(y==300.0){
			xycheck[0][2][1]="noColor";
		}
		if(y==360.0){
			xycheck[0][2][2]="noColor";
			xycheck[1][2][2]="noColor";
		}
		
	}
	else if(x==520.0){
		
		if(y==180.0){
			xycheck[0][3][0]="noColor";
			xycheck[1][0][2]="noColor";
		}
		if(y==300.0){
			xycheck[0][3][1]="noColor";
		}
		if(y==420.0){
			xycheck[0][3][2]="noColor";
			xycheck[1][3][2]="noColor";
		}
		
	}
		
	}
	public boolean formedMill(double x,double y){//check if mill was formed with the last click
		boolean millfound=false;
		
		for(int i=0;i<xycheck.length;i++){
			for(int j=0;j<xycheck[i].length;j++){
				
				if((xycheck[i][j][0].equals(xycheck[i][j][1]))&&(xycheck[i][j][1].equals(xycheck[i][j][2]))&&xycheck[i][j][0].equals(pickedcolor)){
					if(x==280.0){
						if(y==180.0){
							if(j==0)millfound=true;
							
						}
						if(y==300.0){
							if(i==0&&j==0)millfound=true;
							
						}
						if(y==420.0){
							if((i==0&&j==0)||(i==1&&j==3))millfound=true;
							
						}
					}
					else if(x==340.0){
						if(y==240.0){
							if(j==1)millfound=true;
						}
						if(y==300.0){
							if(i==0&&j==1)millfound=true;
						}
						if(y==360.0){
							if((i==0&&j==1)||(i==1&&j==2))millfound=true;
							
						}
					}
					else if(x==400.0){
						if(y==180.0){
							if(i==1&&j==0)millfound=true;
						}
						if(y==240.0){
							if(i==1&&j==1)millfound=true;
						}
						
						if(y==360.0){
							if(i==1&&j==2)millfound=true;
						}
						if(y==420.0){
							if(i==1&&j==3)millfound=true;
						}
					}
					else if(x==460.0){
						
						if(y==240.0){
							if((i==0&&j==2)||(i==1&&j==1))millfound=true;
						}
						if(y==300.0){
							if(i==0&&j==2)millfound=true;
						}
						if(y==360.0){
							if((i==0&&j==2)||(i==1&&j==2))millfound=true;
						}
						
					}
					else if(x==520.0){
						
						if(y==180.0){
							if((i==0&&j==3)||(i==1&&j==0))millfound=true;
						}
						if(y==300.0){
							if(i==0&&j==3)millfound=true;
						}
						if(y==420.0){
							if((i==0&&j==3)||(i==1&&j==3))millfound=true;
						}
						
					}
					
				}
				
			}
		}
		
		
		return millfound;
	}
	// just for making the board and placing the lines
	public Parent shellmaker(int shellnum){

		double startX = 400;
		double startY = 300;
		double spacing = 60;
		Line line31;
		Line line71;
		Line line11;
		Line line51;
		Group nodes = new Group(); 
		for (int shell=1;shell<shellnum+1;shell++){
			       	

				Point point0 = new Point (startX-spacing*shell, startY-spacing*shell);        		      		
				nodes.getChildren().add(point0);


				Point point1 = new Point (startX, startY-spacing*shell);
				Line line1 = new Line(point0.x+20,point0.y+10,point1.x+10,point1.y+10);
				nodes.getChildren().addAll(line1,point1);

				Point point2 = new Point (startX+spacing*shell, startY-spacing*shell);
				Line line2 = new Line(point1.x+20,point1.y+10,point2.x+10,point2.y+10);
				nodes.getChildren().addAll(line2,point2);

				Point point3 = new Point (startX+spacing*shell, startY);
				if (shell < shellnum){
					line31 = new Line(startX+spacing*shell+10, startY+10,startX+spacing*(shell+1), startY+10);
					line71 = new Line(startX-spacing*shell+10, startY+10,startX-spacing*(shell+1), startY+10);
					line11 = new Line(startX+10, startY-spacing*shell+10,startX+10, startY-spacing*(shell+1)+10);
					line51 = new Line(startX+10, startY+spacing*shell+10,startX+10, startY+spacing*(shell+1)+10);
					}
				else{
					line31 = new Line(0,0,0,0);
					line71 = new Line(0,0,0,0);
					line11 = new Line(0,0,0,0);
					line51 = new Line(0,0,0,0);
					}
				Line line3 = new Line(point2.x+10,point2.y+10,point3.x+10,point3.y+10);
				nodes.getChildren().addAll(line3,line31,line71,line11,line51,point3);

				Point point4 = new Point (startX+spacing*shell, startY+spacing*shell);
				Line line4 = new Line(point3.x+10,point3.y+10,point4.x+10,point4.y+10);
				nodes.getChildren().addAll(line4,point4);

				Point point5 = new Point (startX, startY+spacing*shell);
				Line line5 = new Line(point4.x+10,point4.y+10,point5.x+10,point5.y+10);
				nodes.getChildren().addAll(line5,point5);

				Point point6 = new Point (startX-spacing*shell, startY+spacing*shell);
				Line line6 = new Line(point5.x+10,point5.y+10,point6.x+10,point6.y+10);
				nodes.getChildren().addAll(line6,point6);

				Point point7 = new Point (startX-spacing*shell, startY);
				Line line7 = new Line(point6.x+10,point6.y+10,point7.x+10,point7.y+10);
				Line line0 = new Line (point0.x+10, point0.y+10,point7.x+10,point7.y+10);  
				nodes.getChildren().addAll(line7,line0, point7);

			}
		Button redbutton = new Button("Place a red piece");
		redbutton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){				
				if(isSbox!=true){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Not in Sandbox mode");
					alert.setContentText("This button can only be used in sandbox mode.");
					alert.showAndWait();
				}
				else{
				pickedcolor = "red";	
				}
			}
		});
		nodes.getChildren().add(redbutton);

		Button bluebutton = new Button("Place a blue piece");
		 
		bluebutton.setTranslateY(30);
		bluebutton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){				
				if(isSbox!=true){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Not in Sandbox mode");
					alert.setContentText("This button can only be used in sandbox mode.");
					alert.showAndWait();
				}
				else{
				pickedcolor = "blue";	
				}	

			}
		});

		nodes.getChildren().add(bluebutton);
		
		
		Button newgame = new Button("New Game");
		newgame.setTranslateX(350);
		
		newgame.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){
				pickedcolor="newgame";
				NewGame=true;
				isSbox=false;
				newGame();
				if(pickedcolor.equals("newgame")||NewGame==true){
					if(PlayerTurn==true){
						pickedcolor="blue";
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("New Game has started.");
					alert.setContentText("Blue will start the game.");
					alert.showAndWait();
					}
					else{
						pickedcolor="red";
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("New Game has started.");
						alert.setContentText("Red will start the game.");
						alert.showAndWait();
					}
					
				}

			}
		});

		nodes.getChildren().add(newgame);
		
		
		
		Button validButton = new Button("Validate");
		validButton.setTranslateX(700);    	
		validButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){
				if(isSbox!=true){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Not in Sandbox mode");
					alert.setContentText("This button can only be used in sandbox mode.");
					alert.showAndWait();
					
				}
				else{
				if (numPieces==true || duplicate ==true){
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
		});

		nodes.getChildren().add(validButton);
		
		Button sboxButton = new Button("Switch to Sandbox mode");
		sboxButton.setTranslateX(340);
		sboxButton.setTranslateY(500);
		sboxButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){				
				pickedcolor = "sBox";
				isSbox=true;
				NewGame=false;
			}
		});

		nodes.getChildren().add(sboxButton);
		return nodes;
	}
	
	
	public void newGame(){
    	int randomnum=(int)(Math.random()*2);
    	if(randomnum==0)PlayerTurn=true;//blue
    	else PlayerTurn=false;//red
    }
	public void StartTurn(){
    	if(PlayerTurn==true){
    		PlayerTurn=false;
    		pickedcolor="red";
    	}
    	else{
    		PlayerTurn=true;
    		pickedcolor="blue";
    	}
    }

	public static void main(String[] args) {
		createBoard(6);										//create the board model
		launch(args);
		
	}
	public static void updateBoard(){
		
		
	}
	public static void createBoard(int x){	//will create the model for the board
		numMensMorris = x;					//sets the global mens morris variable
	    redCount=0;
	    blueCount=0;
	    boardState = new String[numMensMorris][8];	//3d array, each entry is a list which represents an entire square. for exampe boardState[0][2] would be the top right corner of the inner most shell.   
			
	}
	
}
