package macroTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Main /*extends Application*/ {

    /*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Macro Tracker");

        Scene scene = new Scene(root, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
*/

    public static void main(String[] args) {
        //launch(args);
        FoodDiary diary = new FoodDiary(1);
        diary.changeDiary(2);
        diary.populateDiary();
        diary.addFood(new Food("potato", 200));
        diary.displayFoods();
        System.out.println(diary.getTotalCalories());



    }
}
