package pathfinding;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Popup {
    
    public static void noPossiblePathFound() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("No Possible Path Found!");
        alert.setContentText("Check if you enclosed either the start or end node");
        Platform.runLater(alert::showAndWait);
    }
    
}
