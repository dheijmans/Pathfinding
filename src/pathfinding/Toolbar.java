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
        this.mainView.getPathfinder().aStar();
        this.runButton.setText("Running");
    }
    
    private void handleClear(ActionEvent event) {
        this.mainView.getPathfinder().clearView();
    }

}