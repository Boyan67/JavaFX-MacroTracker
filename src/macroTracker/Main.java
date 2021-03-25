package macroTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Pages/home.fxml"));

        primaryStage.setTitle("Macro Tracker");

        Scene scene = new Scene(root, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


//    public static void main(String[] args) {
//        //launch(args);
//        FoodDiary diary = new FoodDiary(1);
//        diary.changeDiary(1);
//
//        System.out.println("Before clear");
//
//        diary.removeFood(0);
//
//        diary.displayFoods();
//        System.out.println(diary.getTotalCalories());
//    }
}
