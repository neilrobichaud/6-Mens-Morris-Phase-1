import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

// to do:
// place and put errors where they exist :::::::: DONE
// make another one where it just changes boolean values to true and false and will spit out the errors when  validate button is pushed ::::: in progress
public class Model {
	static Button redbutton;
	public static int numMensMorris=6;	//variable to store the number of mens morris ie. 6
	public static Point[][] boardState;
	public static Circle[] bluepiecelist;
	public static Circle[] redpiecelist;
	public static boolean PlayerTurn=true;
	public static int redCount = 0;
	public static int blueCount = 0;	
	public static boolean duplicate = false;
	public static boolean numPieces = false;
	public static boolean rmPiece=false;
	public static boolean NewGame=false;
	public static boolean isSbox = false;
	public static String lastcolor = "white";
	public static String pickedcolor = "black";
	public static String [][][]xycheck=new String[2][4][3];//[0][][] for x [1][][] for y
	
	public static void initxycheck(){//for various purposes, get rid of nulls when initialized
		for(int i=0;i<xycheck.length;i++){
			for(int j=0;j<xycheck[i].length;j++){
				for(int k=0;k<xycheck[i][j].length;k++){
					xycheck[i][j][k]="noColor";
				}
			}
		}
	}
				
	public static void addToArrays(double x,double y){//keeps track of color in each circle
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
	public static void removeFromArray(double x,double y){
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
	public static boolean formedMill(double x,double y){//check if mill was formed with the last click
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
	public static Group shellmaker(int shellnum){
		boardState=new Point[shellnum][8];
		double startX = 400;
		double startY = 300;
		double spacing = 60;
		Line line31;		//the four lines that connect the midpoints for each shell
		Line line71;
		Line line11;
		Line line51;
		Group nodes = new Group(); 
		for (int shell=1;shell<shellnum+1;shell++){			       	

				Point point0 = new Point (startX-spacing*shell, startY-spacing*shell);
				boardState[shell-1][0]=point0;
				Point point1 = new Point (startX, startY-spacing*shell);
				boardState[shell-1][1]=point1;
				Point point2 = new Point (startX+spacing*shell, startY-spacing*shell);
				boardState[shell-1][2]=point2;
				Point point3 = new Point (startX+spacing*shell, startY);
				boardState[shell-1][3]=point3;
				Point point4 = new Point (startX+spacing*shell, startY+spacing*shell);
				boardState[shell-1][4]=point4;
				Point point5 = new Point (startX, startY+spacing*shell);
				boardState[shell-1][5]=point5;
				Point point6 = new Point (startX-spacing*shell, startY+spacing*shell);
				boardState[shell-1][6]=point6;
				Point point7 = new Point (startX-spacing*shell, startY);
				boardState[shell-1][7]=point7;
				nodes.getChildren().addAll(point0,point1,point2,point3,point4,point5,point6,point7);//add all points to group
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
				
				Line line1 = new Line(point0.x+20,point0.y+10,point1.x+10,point1.y+10);				
				Line line2 = new Line(point1.x+20,point1.y+10,point2.x+10,point2.y+10);
				Line line3 = new Line(point2.x+10,point2.y+10,point3.x+10,point3.y+10);
				Line line4 = new Line(point3.x+10,point3.y+10,point4.x+10,point4.y+10);
				Line line5 = new Line(point4.x+10,point4.y+10,point5.x+10,point5.y+10);
				Line line6 = new Line(point5.x+10,point5.y+10,point6.x+10,point6.y+10);				
				Line line7 = new Line(point6.x+10,point6.y+10,point7.x+10,point7.y+10);
				Line line0 = new Line (point0.x+10, point0.y+10,point7.x+10,point7.y+10);  
				nodes.getChildren().addAll(line0,line1,line2,line3,line4,line5,line6,line7,line31,line11,line51,line71);
			}
		redbutton = new Button("Place a red piece");
		redbutton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){				
				Controller.redbutton();
			}
		});
		nodes.getChildren().add(redbutton);
		
		Button bluebutton = new Button("Place a blue piece");
		 
		bluebutton.setTranslateY(30);
		bluebutton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){				
				Controller.bluebutton();	

			}
		});

		nodes.getChildren().add(bluebutton);
		Circle sidepiece;						//the pieces on the side of the board
		Circle sidepiece1;
		Color sidepiececolor=Color.BLUE;
		redpiecelist = new Circle[numMensMorris];
		bluepiecelist=new Circle[numMensMorris];
		for (int i=0;i<numMensMorris;i++){
			sidepiece = new Circle (100+20*i, 200, 10, sidepiececolor);
			sidepiece1 = new Circle (100+20*i, 400, 10, Color.RED);
			bluepiecelist[i]=sidepiece;
			redpiecelist[i]=sidepiece1;
			
			nodes.getChildren().addAll(sidepiece,sidepiece1);
		}
		
		
		Button newgame = new Button("New Game");
		newgame.setTranslateX(350);
		
		newgame.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){
				Controller.newgameButton();
			}
		});

		nodes.getChildren().add(newgame);
		
		
		
		Button validButton = new Button("Validate");
		validButton.setTranslateX(700);    	
		validButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){
				Controller.validButton();
			}
		});
		

		nodes.getChildren().add(validButton);
		
		Button sboxButton = new Button("Switch to Sandbox mode");
		sboxButton.setTranslateX(340);
		sboxButton.setTranslateY(500);
		sboxButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){				
				Controller.sBoxButtonControl();
			}
		});

		nodes.getChildren().add(sboxButton);
		return nodes;
		}
	public static void reset(){	
		redCount = 0;
		blueCount = 0;	
		duplicate = false;
		numPieces = false;
		rmPiece=false;
		lastcolor = "white";
		pickedcolor = "black";
		initxycheck();
	}
	

}

//	public static void main(String[] args) {
//		createBoard(6);										//create the board model
//		launch(args);
//		
//	}
	



