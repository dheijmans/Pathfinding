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
        this.getItems().addAll(runButton);
    }
    
    private void handleRun(ActionEvent event) {
        this.mainView.getPathfinder().aStar();
    }

}