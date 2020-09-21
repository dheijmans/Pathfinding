/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinding;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Dennis
 */
public class Main extends Application {
    
    int width = 1600;
    int height = 900;
    
    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView(width, height);
        Scene scene = new Scene(mainView, width, height);
        stage.setScene(scene);
        stage.show();
        mainView.draw();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
