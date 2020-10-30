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
        this.runButton.setOnAction(this::handleRun);
        this.pauseButton.setOnAction(this::handlePause);
        this.clearButton.setOnAction(this::handleClear);
        this.clearAllButton.setOnAction(this::handleClearAll);
        this.getItems().addAll(runButton, pauseButton, clearButton, clearAllButton, diagonal);
    }
    
    private void handleRun(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            this.mainView.mode = MainView.RUNNING;
            this.diagonal.setDisable(true);
            this.mainView.getPathfinder().aStar();
        }
    }
    
    private void handleClear(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            this.mainView.getPathfinder().clear();
            this.pauseButton.setText("Pause");
            this.diagonal.setDisable(false);
            this.mainView.mode = MainView.EDITING;
        }
    }
    
    private void handleClearAll(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            int[][] maze = this.mainView.getPathfinder().maze;
            this.mainView.getPathfinder().maze = new int[maze.length][maze[0].length];
            this.mainView.getPathfinder().clear();
            this.pauseButton.setText("Pause");
            this.diagonal.setDisable(false);
            this.mainView.mode = MainView.EDITING;
        }
    }
    
    private void handlePause(ActionEvent event) {
        if (mainView.mode == MainView.RUNNING) {
            this.mainView.getPathfinder().getTimeline().pause();
            this.pauseButton.setText("Play");
            this.mainView.mode = MainView.PAUSE;
            this.mainView.draw();
        } else if(mainView.mode == MainView.PAUSE) {
            this.mainView.getPathfinder().getTimeline().play();
            this.pauseButton.setText("Pause");
            this.mainView.mode = MainView.RUNNING;
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