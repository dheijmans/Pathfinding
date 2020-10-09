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
        Button clearAllButton = new Button("Clear All");
        clearAllButton.setOnAction(this::handleClearAll);
        this.getItems().addAll(runButton, clearButton, clearAllButton);
    }
    
    private void handleRun(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            this.mainView.mode = MainView.RUNNING;
            this.mainView.getPathfinder().aStar(); 
        }
    }
    
    private void handleClear(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            this.mainView.getPathfinder().clear();
            mainView.mode = MainView.EDITING;
        }
    }
    
    private void handleClearAll(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            int[][] maze = this.mainView.getPathfinder().maze;
            this.mainView.getPathfinder().maze = new int[maze.length][maze[0].length];
            this.mainView.getPathfinder().clear();
            mainView.mode = MainView.EDITING;
        }
    }

}