package macroTracker.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import macroTracker.Classes.Food;
import macroTracker.Database.Database;

import javafx.scene.control.TextField;
import java.io.IOException;

public class NewFoodSceneController {

    final Database database = new Database("food_list");
    private int diaryID;

    @FXML
    TextField textfieldName;
    @FXML
    TextField fieldCarbs;
    @FXML
    TextField fieldFats;
    @FXML
    TextField fieldProtein;
    @FXML
    TextField fieldCalories;

    public void setDiaryID(int diaryID) {
        this.diaryID = diaryID;
    }
    public int getDiaryID() {
        return diaryID;
    }

    public void createFoodPressed(javafx.event.ActionEvent event){
        createNewFood();
        backToFoodList(event);
    }

    public void backToFoodList(javafx.event.ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("savedFoodsPage.fxml"));
            Parent root = loader.load();

            SavedFoodsController savedFoodsController = loader.getController();
            savedFoodsController.setDiaryID(getDiaryID());

            Scene FoodListScene = new Scene(root,810,500);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(FoodListScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNewFood(){
        String name = textfieldName.getText().trim();
        int carbs = Integer.parseInt(fieldCarbs.getText());
        int fats = Integer.parseInt(fieldFats.getText());
        int protein = Integer.parseInt(fieldProtein.getText());
        int calories = Integer.parseInt(fieldCalories.getText());

        Food food = new Food(name, carbs, fats, protein, calories);
        database.insertFood(food);

    }
}
