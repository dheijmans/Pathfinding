package pathfinding;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {
    
    private Label steps;
    
    public Sidebar() {
        this.steps = new Label("Hallo");
//        this.setWidth(250d);
//        Pane spacer = new Pane();
//        spacer.setMinSize(0, 0);
//        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().addAll(this.steps);
          
    }
    
}
