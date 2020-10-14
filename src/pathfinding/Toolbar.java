package pathfinding;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    
    private final MainView mainView;
    
    private Button runButton = new Button("Run");
    private Button pauseButton = new Button("Pause");
    private Button clearButton = new Button("Clear");
    private Button clearAllButton = new Button("Clear All");
        
    private CheckBox diagonal = new CheckBox("Diagonals");

    public Toolbar(MainView mainView) {
        this.mainView = mainView;
        
        runButton.setOnAction(this::handleRun);
        pauseButton.setOnAction(this::handlePause);
        clearButton.setOnAction(this::handleClear);
        clearAllButton.setOnAction(this::handleClearAll);
        this.getItems().addAll(runButton, pauseButton, clearButton, clearAllButton, diagonal);
    }
    
    private void handleRun(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            this.mainView.mode = MainView.RUNNING;
            this.mainView.getPathfinder().aStar();
            this.diagonal.setDisable(true);
        }
    }
    
    private void handleClear(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            this.mainView.getPathfinder().clear();
            this.pauseButton.setText("Pause");
            this.diagonal.setDisable(false);
            mainView.mode = MainView.EDITING;
        }
    }
    
    private void handleClearAll(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            int[][] maze = this.mainView.getPathfinder().maze;
            this.mainView.getPathfinder().maze = new int[maze.length][maze[0].length];
            this.mainView.getPathfinder().clear();
            this.pauseButton.setText("Pause");
            this.diagonal.setDisable(false);
            mainView.mode = MainView.EDITING;
        }
    }
    
    private void handlePause(ActionEvent event) {
        if (mainView.mode == MainView.RUNNING) {
            this.mainView.getPathfinder().getTimeline().pause();
            this.pauseButton.setText("Play");
            mainView.mode = MainView.PAUSE;
        } else if(mainView.mode == MainView.PAUSE) {
            this.mainView.getPathfinder().getTimeline().play();
            this.pauseButton.setText("Pause");
            mainView.mode = MainView.RUNNING;
        }
        
    }
    
    public boolean getDiagonalState() {
        return this.diagonal.isSelected();         
    }
    
    public Button getPauseButton() {
        return this.pauseButton;     
    }
    
    public CheckBox getDiagonal() {
        return this.diagonal;     
    }
    
}