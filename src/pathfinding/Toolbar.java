package pathfinding;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    
    private final MainView mainView;
    
    public static Button runButton;
    
    public Toolbar(MainView mainView) {
        this.mainView = mainView;
        this.runButton = new Button("Run");
        Button clearButton = new Button("Clear");
        runButton.setOnAction(this::handleRun);
        clearButton.setOnAction(this::handleClear);
        this.getItems().addAll(runButton, clearButton);
    }
    
    private void handleRun(ActionEvent event) {
        if (this.mainView.mode == MainView.EDITING) {
            this.mainView.mode = MainView.RUNNING;
            this.runButton.setText("Running");
            this.mainView.getPathfinder().aStar(); 
        }
    }
    
    private void handleClear(ActionEvent event) {
        if (mainView.mode == MainView.EDITING) {
            this.mainView.getPathfinder().clearView();
        }
    }

}