package pathfinding;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToolBar;

// importing functions once again

public class Toolbar extends ToolBar {
    
    private final MainView mainView;
    
    private Button runButton = new Button("Run");
    private Button pauseButton = new Button("Pause");
    private Button clearButton = new Button("Clear");
    private Button clearAllButton = new Button("Clear All");        
    private CheckBox diagonal = new CheckBox("Diagonals");
    
    // creating buttons and such

    public Toolbar(MainView mainView) {
        this.mainView = mainView;
        this.runButton.setOnAction(this::handleRun);
        this.pauseButton.setOnAction(this::handlePause);
        this.clearButton.setOnAction(this::handleClear);
        this.clearAllButton.setOnAction(this::handleClearAll);
        this.diagonal.setSelected(true);
        this.getItems().addAll(runButton, pauseButton, clearButton, clearAllButton, diagonal);
        // sets correct actions for buttons
    }
    
    private void handleRun(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            this.mainView.setMode(MainView.RUNNING);
            this.diagonal.setDisable(true);
            this.mainView.getSidebar().setAmountOfSteps(0);
            this.mainView.getPathfinder().aStar();
        }
        // makes the program run throught the Pathfinder timeline loop
    }
    
    private void handleClear(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            this.mainView.getPathfinder().clear();
            this.pauseButton.setText("Pause");
            this.diagonal.setDisable(false);
            this.mainView.setMode(MainView.EDITING);
            this.mainView.getSidebar().setAmountOfSteps(0);
        }
        // clears the canvas from results when button is pressed
    }
    
    private void handleClearAll(ActionEvent event) {
        if (mainView.mode != MainView.RUNNING) {
            int[][] maze = this.mainView.getPathfinder().maze;
            this.mainView.getPathfinder().maze = new int[maze.length][maze[0].length];
            this.mainView.getPathfinder().clear();
            this.pauseButton.setText("Pause");
            this.diagonal.setDisable(false);
            this.mainView.setMode(MainView.EDITING);
            this.mainView.getSidebar().setAmountOfSteps(0);
            this.mainView.getSidebar().clearBlockedNode();
        }
        // clears all results and blocked nodes when the button is pressed
    }
    
    private void handlePause(ActionEvent event) {
        if (mainView.mode == MainView.RUNNING) {
            this.mainView.getPathfinder().getTimeline().pause();
            this.pauseButton.setText("Play");
            this.mainView.setMode(MainView.PAUSE);
            this.mainView.draw();
        } else if(mainView.mode == MainView.PAUSE) {
            this.mainView.getPathfinder().getTimeline().play();
            this.pauseButton.setText("Pause");
            this.mainView.setMode(MainView.RUNNING);
        }
        // pauses the timeline and displays current state
        
    }
    
    public boolean getDiagonalState() {
        return this.diagonal.isSelected();     
        // checks if diagonal function is selected or not
    }
    
    public Button getPauseButton() {
        return this.pauseButton;   
        // returns pause button when called
    }
    
    public CheckBox getDiagonal() {
        return this.diagonal;     
        // sets diagonal box to disabled when is not selected
    }
    
}