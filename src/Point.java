import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;



public class Point extends StackPane{
		double x,y;
		double sizenode = 10; 	
		Circle circle;
		Color nodecolor = Color.BLACK;

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
					Controller.pointclicked(circle);		
					}    	
			});
		}
}