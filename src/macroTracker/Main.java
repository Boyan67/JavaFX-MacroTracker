package macroTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Controllers/dashboardPage.fxml"));

        primaryStage.setTitle("Macro Tracker");

        Scene scene = new Scene(root, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
