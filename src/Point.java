import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;



public class Point extends StackPane{
		double x,y;						//the points coordinates
		double sizenode = 10; 			//the size of the circle
		Circle circle;					//declare a circle
		Color nodecolor = Color.BLACK;	//color is black

		public Point(double x, double y){		//constructor
			this.x =x;							//set x and y
			this.y =y;
			circle = new Circle (sizenode, nodecolor);	//create a new circle
			setTranslateX(x);									//translate it 
			setTranslateY(y);
			getChildren().addAll(circle);    					//add to point
			
			setOnMouseClicked(new EventHandler<MouseEvent>()   //if point is clicked 		
			{

				@Override
				public void handle(MouseEvent t) {				
					// checks Model.duplicate placing	
					Controller.pointclicked(circle);			//calls controller method	
					}    	
			});
		}
}