package pathfinding;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
// importing functions 

public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView, 1850, 955);
        
        Image icon = new Image("/resources/icon.png");
        stage.getIcons().add(icon);
        
        stage.setTitle("Pathfinder");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        mainView.draw();
        // basically starts the entire program
    }

    public static void main(String[] args) {
        launch(args);
    }
    // launches

}