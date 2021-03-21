package macroTracker;

import macroTracker.Classes.Food;
import macroTracker.Classes.FoodDiary;

public class Main /*extends Application*/ {

    /*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
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
