package macroTracker.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import macroTracker.Classes.Food;
import macroTracker.Classes.FoodDiary;

import java.io.IOException;

public class DashboardController {
    FoodDiary foodDiary = new FoodDiary(1);
    @FXML
    ListView<GridPane> foodListView;
    @FXML
    Label lblDay;
    @FXML
    Label totalCarbs;
    @FXML
    Label totalFats;
    @FXML
    Label totalProtein;
    @FXML
    Label totalCalories;


    // ========== Initial Function ==========
    public void initialize(){
        showTable();
    }

    // ========== Button Functions ==========
    public void addButtonPressed() {
        Food food = new Food("bob", 5, 10, 15, 600);
        foodDiary.addFood(food);
        showTable();
    }

    public void removeButtonPressed() {
        foodDiary.clearDiary();
        showTable();
    }

    public void nextButtonPressed(){
        if (foodDiary.getId() !=7){
            foodDiary.changeDiary(foodDiary.getId()+1);
            showTable();
            lblDay.setText("Day " + foodDiary.getId());
        }else{
            System.out.println("You reached 7");
        }
    }

    public void prevButtonPressed(){
        if (foodDiary.getId() !=1){
            foodDiary.changeDiary(foodDiary.getId()-1);
            showTable();
            lblDay.setText("Day " + foodDiary.getId());
        }else{
            System.out.println("You reached 0");
        }
    }

    public void fromListPressed(javafx.event.ActionEvent event){

        Parent HomeParent;
        try {
            HomeParent = FXMLLoader.load(getClass().getResource("savedFoodsPage.fxml"));
            Scene HomeScene = new Scene(HomeParent,750,500);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(HomeScene);
            window.show();
        } catch (IOException e) {
            System.out.println("SignUp Button Error: ");
            e.printStackTrace();
        }


    }

    // ========== Functionality ==========
    public void fillDiary(){
        FoodGrid foodGrid;
        for (Food food : foodDiary.getEveryFood()) {
            foodGrid = new FoodGrid(food);
            foodListView.getItems().add(foodGrid.createFoodGrid());
        }
    }
    public void clearView(){
        if (foodListView.getItems().size() > 0) {
            foodListView.getItems().subList(0, foodListView.getItems().size()).clear();
        }
    }
    public void showTable(){
        clearView();
        fillDiary();
        updateMacros();
    }
    public void updateMacros(){
        totalCarbs.setText(Integer.toString(foodDiary.getTotalCarbs()));
        totalFats.setText(Integer.toString(foodDiary.getTotalFats()));
        totalProtein.setText(Integer.toString(foodDiary.getTotalProtein()));
        totalCalories.setText(Integer.toString(foodDiary.getTotalCalories()));
    }

    // ========== FoodGrid Class ==========
    class FoodGrid {
        private final Label name;
        private final Label carbs;
        private final Label fats;
        private final Label protein;
        private final Label calories;
        private final GridPane grid = new GridPane();
        private final Button btn;


        FoodGrid(Food food){
            name = new Label(food.getName());
            carbs = new Label(Integer.toString(food.getCarbs()));
            fats = new Label(Integer.toString(food.getFats()));
            protein = new Label(Integer.toString(food.getProtein()));
            calories = new Label(Integer.toString(food.getCalories()));
            btn = new Button("Delete");
            btn.setOnAction(event -> removeSelectedFood(food.getId()));
        }

        public void removeSelectedFood(int id){
            foodDiary.removeFood(id);
            showTable();
        }

        public GridPane createFoodGrid(){
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(40);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(15);
            col2.setHalignment(HPos.CENTER);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(15);
            col3.setHalignment(HPos.CENTER);
            ColumnConstraints col4 = new ColumnConstraints();
            col4.setPercentWidth(15);
            col4.setHalignment(HPos.CENTER);
            ColumnConstraints col5 = new ColumnConstraints();
            col5.setPercentWidth(15);
            col5.setHalignment(HPos.CENTER);
            ColumnConstraints col6 = new ColumnConstraints();
            col6.setPercentWidth(15);

            grid.getColumnConstraints().addAll(col1,col2,col3, col4, col5, col6);

            grid.add(name, 0, 0, 1, 1);
            grid.add(carbs, 1, 0, 1, 1);
            grid.add(fats, 2, 0, 1, 1);
            grid.add(protein, 3, 0, 1, 1);
            grid.add(calories, 4, 0, 1, 1);
            grid.add(btn, 5, 0, 1, 1);

            return grid;
        }
    }


}


