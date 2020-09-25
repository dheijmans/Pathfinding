package pathfinding;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    
    private final MainView mainView;
    
    public Toolbar(MainView mainView) {
        this.mainView = mainView;
        Button runButton = new Button("Run");
        Button clearButton = new Button("Clear");
        runButton.setOnAction(this::handleRun);
        clearButton.setOnAction(this::handleClear);
        this.getItems().addAll(runButton, clearButton);
    }
    
    private void handleRun(ActionEvent event) {
        this.mainView.getPathfinder().aStar();
    }
    
    private void handleClear(ActionEvent event) {
        this.mainView.getPathfinder().clearView();
    }

}