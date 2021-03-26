package macroTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import macroTracker.Classes.Food;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Controllers/home.fxml"));

        primaryStage.setTitle("Macro Tracker");

        Food food = new Food(1, "bob", 500);
        Food food2 = new Food(1, "bob", 500);
        System.out.println(food.hashCode());
        System.out.println(food2.hashCode());

        Scene scene = new Scene(root, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
