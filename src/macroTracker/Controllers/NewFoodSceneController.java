package macroTracker.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import macroTracker.Classes.Food;
import macroTracker.Database.Database;

import java.io.IOException;

public class NewFoodSceneController {

    final Database database = new Database("food_list");
    private int diaryID;

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField fieldCarbs;
    @FXML
    private TextField fieldFats;
    @FXML
    private TextField fieldProtein;
    @FXML
    private TextField fieldCalories;
    @FXML
    private Label nameError;
    @FXML
    private Label carbsError;
    @FXML
    private Label fatsError;
    @FXML
    private Label proteinError;
    @FXML
    private Label caloriesError;
    @FXML
    private ChoiceBox<String> choiceCategory;
    @FXML
    private TextArea txtAreaIngredients;

    // ========== Class Specific ==========
    public void initialize(){
        choiceCategory.getItems().add("Breakfast");
        choiceCategory.getItems().add("Lunch/Dinner");
        choiceCategory.getItems().add("Snacks");
    }
    public void setDiaryID(int diaryID) {
        this.diaryID = diaryID;
    }
    public int getDiaryID() {
        return diaryID;
    }

    // ========== Button Function ==========
    public void createFoodPressed(javafx.event.ActionEvent event){
        if (validation()){
            createNewFood();
            backToFoodList(event);
        }
    }

    public void cancelPressed(javafx.event.ActionEvent event){
        backToFoodList(event);
    }


    // ========== Functionality ==========
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
        String name = textFieldName.getText().trim();
        int carbs = Integer.parseInt(fieldCarbs.getText());
        int fats = Integer.parseInt(fieldFats.getText());
        int protein = Integer.parseInt(fieldProtein.getText());
        int calories = Integer.parseInt(fieldCalories.getText());
        String category = String.valueOf(choiceCategory.getSelectionModel().getSelectedItem());
        String ingredients = txtAreaIngredients.getText();

        Food food = new Food(name, carbs, fats, protein, calories, category, ingredients);
        database.insertFood(food);
    }

    public boolean validation(){

        if (isValidName() && isValidInt(fieldCarbs.getText()) &&
            isValidInt(fieldFats.getText()) && isValidInt(fieldProtein.getText()) &&
            isValidInt(fieldCalories.getText()))
        {
            return true;
        }else{
            if (isValidName()){
                nameError.setText("");
            }else{
                nameError.setText("Enter a valid name");
            }

            if (isValidInt(fieldCarbs.getText())){
                carbsError.setText("");
            }else {
                carbsError.setText("Enter valid value for carbs");
            }

            if (isValidInt(fieldFats.getText())) {
                fatsError.setText("");
            }else{
                fatsError.setText("Enter valid value for fats");
            }

            if (isValidInt(fieldProtein.getText())) {
                proteinError.setText("");
            }else{
                proteinError.setText("Enter valid value for protein");
            }

            if (isValidInt(fieldCalories.getText())) {
                caloriesError.setText("");
            }else{
                caloriesError.setText("Enter valid value for calories");
            }
        }

        return false;

    }

    public boolean isValidName(){
        return !textFieldName.getText().isEmpty();
    }

    public boolean isValidInt(String input){
        return input.matches("[0-9]+") && input.length() > 0;
    }

}
