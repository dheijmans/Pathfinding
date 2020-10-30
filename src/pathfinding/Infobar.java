package pathfinding;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;    
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class Infobar extends HBox {
    
    private Label cursor;
    private Label mode;
    
    public Infobar() {
        this.cursor = new Label("Cursor: (0,0)");
        this.mode = new Label("Mode: -");
        
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        this.getChildren().addAll(this.cursor, spacer, this.mode);
    }
    
    public void SetCursor(int x, int y) {
        this.cursor.setText("Cursor: (" + x + "," + y + ")");
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
    
}
