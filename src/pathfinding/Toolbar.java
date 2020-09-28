package pathfinding;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    
    private final MainView mainView;
        
    public Toolbar(MainView mainView) {
        this.mainView = mainView;
        Button runButton = new Button("Run");
        runButton.setOnAction(this::handleRun);
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(this::handleClear);
        this.getItems().addAll(runButton, clearButton);
    }
    
    private void handleRun(ActionEvent event) {
        if (this.mainView.mode == MainView.EDITING) {
            this.mainView.mode = MainView.RUNNING;
            this.mainView.getPathfinder().aStar(); 
        }
    }
    
    private void handleClear(ActionEvent event) {
        if (mainView.mode == MainView.EDITING) {
            this.mainView.getPathfinder().clear();
        }
    }

}