package pathfinding;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {
    
    private Label steps;
    private final MainView mainView;
    private final Pathfinder pathfinder;
    
    public Sidebar(MainView mv) {
        this.mainView = mv;
        this.pathfinder = this.mainView.getPathfinder();
        
        this.steps = new Label("Amount of steps:");        
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().addAll(this.steps);          
    }
    
    public void SetAmountOfSteps(int s) {
        this.steps.setText("Amount of steps: " + s);
    }
    
}
