package pathfinding;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Popup {
    
    public static void impossiblePath() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Impossible Path");
        alert.setHeaderText("Impossible Path!");
        alert.setContentText("Ooops, there was an error finding a path!");
        Platform.runLater(alert::showAndWait);
    }
    
}
