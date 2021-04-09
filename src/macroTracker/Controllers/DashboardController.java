package macroTracker.Controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import macroTracker.Classes.Food;
import macroTracker.Classes.FoodDiary;

import java.io.IOException;
import java.util.ArrayList;

public class DashboardController {
    private ArrayList<Integer> idsToAdd;
    final FoodDiary foodDiary = new FoodDiary(1);
    @FXML
    private ListView<GridPane> foodListView;
    @FXML
    private Label lblDay;
    @FXML
    private Label totalCarbs;
    @FXML
    private Label totalFats;
    @FXML
    private Label totalProtein;
    @FXML
    private Label totalCalories;


    // ========== Initial Function ==========
    public void initialize(){
        showTable();
    }

    // ========== Button Functions ==========
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

    public void addFoodPressed(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("savedFoodsPage.fxml"));
            Parent root = loader.load();

            //The following both lines are the only addition we need to pass the arguments
            SavedFoodsController savedFoodsController = loader.getController();
            savedFoodsController.setDiaryID(foodDiary.getId());

            Scene HomeScene = new Scene(root);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            //set Stage boundaries to visible bounds of the main screen
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            window.setWidth(primaryScreenBounds.getWidth());
            window.setHeight(primaryScreenBounds.getHeight());

            window.setScene(HomeScene);
            window.show();
        } catch (IOException e) {
            System.out.println("addFood Button Error: ");
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
    public void setIdsToAdd(ArrayList<Integer> idsToAdd){
        this.idsToAdd = idsToAdd;
    }
    public void addSelectedFoods(int diaryID){
        foodDiary.changeDiary(diaryID);
        lblDay.setText("Day " + foodDiary.getId());
        foodDiary.addSelectedFoods(idsToAdd);
        showTable();
    }

    // ========== FoodGrid Class ==========
    class FoodGrid {
        private final Pane category;
        private final String categoryName;
        private final String ingredientList;
        private final Label name;
        private final Label carbs;
        private final Label fats;
        private final Label protein;
        private final Label calories;
        private final GridPane grid = new GridPane();
        private final Button btn;


        public String  determineColor(Food food){
            String colorVal = "#333";
            colorVal = switch (food.getCategory()) {
                case "Lunch/Dinner" -> "#0837d1";
                case "Breakfast" -> "#34eb83";
                case "Snacks" -> "#7008d1";
                default -> colorVal;
            };

            return colorVal;
        }

        FoodGrid(Food food){
            category = new VBox();
            Pane a = new Pane();
            a.setStyle("-fx-background-color: " + determineColor(food));
            a.setPadding(new Insets(10, 0, 30, 0));
            category.getChildren().add(a);


            name = new Label(food.getName());
            carbs = new Label(Integer.toString(food.getCarbs()));
            fats = new Label(Integer.toString(food.getFats()));
            protein = new Label(Integer.toString(food.getProtein()));
            calories = new Label(Integer.toString(food.getCalories()));
            btn = new Button();
            Image img = new Image("macroTracker\\Icons\\delete.png");
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            btn.setGraphic(imageView);
            btn.setOnAction(event -> removeSelectedFood(food.getId()));
            categoryName = food.getCategory();

            ingredientList = food.getIngredients();
        }

        public void removeSelectedFood(int id){
            foodDiary.removeFood(id);
            showTable();
        }

        public GridPane createFoodGrid(){
            ColumnConstraints col0 = new ColumnConstraints();
            col0.setPrefWidth(13);
            category.setPadding(new Insets(0, 7, 0, 0));
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(37);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(14);
            col2.setHalignment(HPos.CENTER);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(14);
            col3.setHalignment(HPos.CENTER);
            ColumnConstraints col4 = new ColumnConstraints();
            col4.setPercentWidth(13);
            col4.setHalignment(HPos.CENTER);
            ColumnConstraints col5 = new ColumnConstraints();
            col5.setPercentWidth(12);
            col5.setHalignment(HPos.CENTER);
            ColumnConstraints col6 = new ColumnConstraints();
            col6.setPercentWidth(12);
            col6.setHalignment(HPos.RIGHT);
            btn.setPrefWidth(70);


            grid.getColumnConstraints().addAll(col0, col1,col2,col3, col4, col5, col6);

            grid.add(category, 0, 0, 1, 1);
            grid.add(name, 1, 0, 1, 1);
            grid.add(carbs, 2, 0, 1, 1);
            grid.add(fats, 3, 0, 1, 1);
            grid.add(protein, 4, 0, 1, 1);
            grid.add(calories, 5, 0, 1, 1);
            grid.add(btn, 6, 0, 1, 1);
            grid.getStyleClass().addAll("grid");

            grid.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


            return grid;
        }

        EventHandler<MouseEvent> eventHandler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("foodDetailsPage.fxml"));
                    Parent root = loader.load();

                    FoodDetailsController foodDetailsController = loader.getController();
                    foodDetailsController.setLblName(name.getText());
                    foodDetailsController.setLblCategory(categoryName);
                    foodDetailsController.setLblCarbs(carbs.getText());
                    foodDetailsController.setLblFats(fats.getText());
                    foodDetailsController.setLblProtein(protein.getText());
                    foodDetailsController.setLblCalories(calories.getText());
                    foodDetailsController.setLblIngredients(ingredientList);

                    Scene HomeScene = new Scene(root,400,500);
                    Stage window = new Stage();
                    window.setScene(HomeScene);
                    window.showAndWait();
                } catch (IOException ioException) {
                    System.out.println("Creating Food Details Window error: ");
                    ioException.printStackTrace();
                }

            }
        };


    }

}


