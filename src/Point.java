import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;



public class Point extends StackPane{
		double x,y;						//the points coordinates
		int i,j;
		double sizenode = 10; 			//the size of the circle
		Circle circle;					//declare a circle
		Color nodecolor = Color.BLACK;	//color is black
		public Paint checkColor(){
			return circle.getFill();
		}
		public int getI(){
			return i;
		}
		public int getJ(){
			return j;
		}
		
		public Point(double x, double y, int i, int j){		//constructor
			this.x =x;							//set x and y
			this.y =y;
			this.i = i;
			this.j = j;
			Point p = this;
			circle = new Circle (sizenode, nodecolor);	//create a new circle
			setTranslateX(x);									//translate it 
			setTranslateY(y);
			getChildren().addAll(circle);    					//add to point
			
			setOnMouseClicked(new EventHandler<MouseEvent>()   //if point is clicked 		
			{

				@Override
				public void handle(MouseEvent t) {				
					// checks Model.duplicate placing	
					if (Model.phase == 1){
						Controller.pointclicked(p);			//calls controller method	
					}  
					else if (Model.phase == 2){				//controller method for phase 2
						Controller.pointclicked2(p);
					}
					}
					  	
			});
		}
}