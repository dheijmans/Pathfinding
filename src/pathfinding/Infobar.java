package pathfinding;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;    
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

// for the trillinth time, importing the functions we need

public class Infobar extends HBox {
    
    private Label cursor;
    private Label mode;
    
    // labels for displaying mouse coordinates and the current mode
    
    public Infobar() {
        this.cursor = new Label("Cursor: (0,0)");
        this.mode = new Label("Mode: -");
        
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        this.getChildren().addAll(this.cursor, spacer, this.mode);
        // sets value for the mode nd mouse coordinates, and places them in a certain position in the window
    }
    
    public void SetCursor(int x, int y) {
        this.cursor.setText("Cursor: (" + x + "," + y + ")");
        // sets cursor values
    }
    
    public void setMode(int m) {
        String mode;
        switch(m) {
            case MainView.EDITING:
                mode = "Editing";
                break;
            case MainView.RUNNING:
                mode = "Running";
                break;
            case MainView.PAUSE:
                mode = "Paused";
                break;
            case MainView.RESULTS:
                mode = "Results";
                break;
            default:
                mode = "-";
        }
        this.mode.setText("Mode: " + mode);
    }
    // sets mode in the certain spot in the window
    
}
