package macroTracker.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import macroTracker.Classes.Food;
import macroTracker.Classes.FoodDiary;

import java.sql.SQLException;

public class DashboardController {
    FoodDiary foodDiary = new FoodDiary(1);
    @FXML
    ListView foodListView;
    @FXML
    Label lblDay;


    // ========== Initial Function ==========
    public void initialize(){
        clearView();
        fillDiary();
    }

    // ========== Button Functions ==========
    public void addButtonPressed() throws SQLException {
        Food food = new Food("bob", 600);
        foodDiary.addFood(food);
        FoodGrid foodGrid = new FoodGrid(food);
        foodListView.getItems().add(foodGrid.createFoodGrid());
        System.out.println(foodDiary.getTotalCalories());
    }

    public void removeButtonPressed() {
        foodDiary.clearDiary();
        showTable();
    }

    public void nextButtonPressed(){
        if (foodDiary.getId() !=7){
            foodDiary.changeDiary(foodDiary.getId()+1);
            clearView();
            fillDiary();
            lblDay.setText("Day " + foodDiary.getId());
        }else{
            System.out.println("You reached 7");
        }
    }

    public void prevButtonPressed(){
        if (foodDiary.getId() !=1){
            foodDiary.changeDiary(foodDiary.getId()-1);
            clearView();
            fillDiary();
            lblDay.setText("Day " + foodDiary.getId());
        }else{
            System.out.println("You reached 0");
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
        for (int i = foodListView.getItems().size()-1; i >= 0; i--){
            foodListView.getItems().remove(i);
        }
    }
    public void showTable(){
        clearView();
        fillDiary();
    }

    // ========== FoodGrid Class ==========
    class FoodGrid {
        private int id;
        private Label name, carbs, fats, protein, calories;
        private GridPane grid = new GridPane();
        private Button btn;

        public int getId() {
            return id;
        }


        FoodGrid(Food food){
            name = new Label(food.getName());
            carbs = new Label(" - ");
            fats = new Label(" - ");
            protein = new Label(" - ");
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


