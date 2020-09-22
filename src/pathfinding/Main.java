package pathfinding;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    private final int width = 1600;
    private final int height = 900;
    
    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView(width, height);
        Scene scene = new Scene(mainView, width, height);
        stage.setScene(scene);
        stage.show();
        mainView.draw();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}