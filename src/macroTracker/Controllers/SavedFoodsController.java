package macroTracker.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import macroTracker.Classes.Food;
import macroTracker.Database.Database;

public class SavedFoodsController {

    @FXML
    ListView<GridPane> savedFoodListView;

    Database database = new Database("food_list");

    public void initialize(){
        showTable();
    }

    public void fillDiary(){
        FoodGrid foodGrid;
        for (Food food : database.getAllFoods()) {
            foodGrid = new FoodGrid(food);
            savedFoodListView.getItems().add(foodGrid.createFoodGrid());
        }
    }
    public void clearView(){
        if (savedFoodListView.getItems().size() > 0) {
            savedFoodListView.getItems().subList(0, savedFoodListView.getItems().size()).clear();
        }
    }
    public void showTable(){
        clearView();
        fillDiary();
    }

    public void addSelectedPressed(){

        System.out.println(savedFoodListView.getSelectionModel().getSelectedItem().getId());

    }

    class FoodGrid {
        private final Label name;
        private final Label carbs;
        private final Label fats;
        private final Label protein;
        private final Label calories;
        private final GridPane grid = new GridPane();
        private final Button btn;
        private final int foodID;


        FoodGrid(Food food ){
            name = new Label(food.getName());
            carbs = new Label(Integer.toString(food.getCarbs()));
            fats = new Label(Integer.toString(food.getFats()));
            protein = new Label(Integer.toString(food.getProtein()));
            calories = new Label(Integer.toString(food.getCalories()));
            btn = new Button("Delete");
            btn.setOnAction(event -> removeSelectedFood(food.getId()));
            foodID = food.getId();
        }

        public void removeSelectedFood(int id){
            database.deleteFood(id);
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

        public int getFoodID() {
            return foodID;
        }
    }


}
