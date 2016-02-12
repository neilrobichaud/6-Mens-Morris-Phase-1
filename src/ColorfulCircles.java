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


public class ColorfulCircles extends Application {
	private String pickedcolor = "black";
    @Override
    public void start(Stage primaryStage) {
        
        Scene scene1 = new Scene(shellmaker(4),800,600,Color.BEIGE);
        primaryStage.setScene(scene1);	
        primaryStage.setTitle("6 mens morris");
	    primaryStage.show();      
        
    }
    
    private class Point extends StackPane{
    	double x,y;
    	double sizenode = 10; 	
    	Color nodecolor = Color.BLACK;
    	boolean canplace=true;
    	public Point(double x, double y){
    		this.x =x;
    		this.y =y;
    		Circle circle = new Circle (x, y, sizenode, nodecolor);
    		setTranslateX(x);
    		setTranslateY(y);
    		getChildren().addAll(circle);    		
    		setOnMouseClicked(new EventHandler<MouseEvent>()    		
            {
                @Override
                public void handle(MouseEvent t) {
                	if (!(circle.getFill().toString().equals("0x000000ff"))){
                		Alert alert = new Alert(AlertType.INFORMATION);
                		alert.setTitle("Information Dialog");
                		alert.setHeaderText("You cannot place one piece on top of another");
                		alert.setContentText("Please try again");
                		alert.showAndWait();
                		
                	}
                	else if (pickedcolor == "red"){                		
                		circle.setFill(Color.RED);
                	}
                	else if (pickedcolor == "blue"){                		
                		circle.setFill(Color.BLUE);
                	}
                    
                }
            });
    	}    	
    }
    
    public Parent shellmaker(int shellnum){

    	double startX = 400;
    	double startY = 300;
    	double spacing = 60;

    	Group nodes = new Group(); 
    	for (int shell=1;shell<shellnum+1;shell++){
    	for (int j=0;j<8;j++){	        	
        	
        		Point point0 = new Point (startX-spacing*shell, startY-spacing*shell);        		      		
        		nodes.getChildren().add(point0);
        	
        	
        		Point point1 = new Point (startX, startY-spacing*shell);
        		Line line1 = new Line(point0.x+20,point0.y+10,point1.x+10,point1.y+10);
        		nodes.getChildren().addAll(line1,point1);
        	
        		Point point2 = new Point (startX+spacing*shell, startY-spacing*shell);
        		Line line2 = new Line(point1.x+20,point1.y+10,point2.x+10,point2.y+10);
        		nodes.getChildren().addAll(line2,point2);
        	
        		Point point3 = new Point (startX+spacing*shell, startY);
        		Line line3 = new Line(point2.x+10,point2.y+10,point3.x+10,point3.y+10);
        		nodes.getChildren().addAll(line3,point3);
        	
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
        	
        }}
    	Button redbutton = new Button("Place a red piece");
    	redbutton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){				
				pickedcolor = "red";	
				
			}
    	});
    	nodes.getChildren().add(redbutton);
    	
    	Button bluebutton = new Button("Place a blue piece");
    	bluebutton.setTranslateX(200);    	
    	bluebutton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){				
				pickedcolor = "blue";	
				
			}
    	});
    	
    	nodes.getChildren().add(bluebutton);
    	return nodes;
    }
    
    
    
 public static void main(String[] args) {
        launch(args);
    }
}