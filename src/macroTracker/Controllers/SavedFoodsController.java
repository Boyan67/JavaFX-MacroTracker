package macroTracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import macroTracker.Classes.Food;
import macroTracker.Database.Database;

import java.io.IOException;
import java.util.ArrayList;

public class SavedFoodsController {

    final Database database = new Database("food_list");
    private int diaryID;
    final ObservableList<Food> foodObservableList = FXCollections.observableArrayList();

    @FXML
    public TextField filterField;
    @FXML
    TableView<Food> savedFoodsTable;

    @FXML
    public TableColumn<Food, Integer> ID;
    @FXML
    public TableColumn<Food, String> name;
    @FXML
    public TableColumn<Food, Integer> carbs;
    public TableColumn<Food, Integer> fats;
    public TableColumn<Food, Integer> protein;
    public TableColumn<Food, Integer> calories;

    public void setDiaryID(int diaryID) {
        this.diaryID = diaryID;
    }
    public int getDiaryID() {
        return diaryID;
    }

    public void initialize(){
        ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        carbs.setCellValueFactory(new PropertyValueFactory<>("Carbs"));
        fats.setCellValueFactory(new PropertyValueFactory<>("Fats"));
        protein.setCellValueFactory(new PropertyValueFactory<>("Protein"));
        calories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
        savedFoodsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        showTable();
        searchFoods();
    }

    public void searchFoods(){
        FilteredList<Food> filteredData = new FilteredList<>(foodObservableList, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(food -> {
            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();
            return food.getName().toLowerCase().contains(lowerCaseFilter); // Filter matches first name.
// Does not match.
        }));
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Food> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(savedFoodsTable.comparatorProperty());
        savedFoodsTable.setItems(sortedData);
    }

    public void fillDiary(){
        foodObservableList.addAll(database.getAllFoods());
        savedFoodsTable.setItems(foodObservableList);
    }

    public void clearView() {
        savedFoodsTable.getItems().removeAll();
    }

    public void showTable(){
        clearView();
        fillDiary();
    }

    public ArrayList<Integer> getSelectedFoods(){
        ArrayList<Integer> indexes = new ArrayList<>();

        for (Food food : savedFoodsTable.getSelectionModel().getSelectedItems()){
            indexes.add(food.getId());
        }
        return indexes;
    }


    // ========== Button Functionality ==========
    public void addSelectedFoodsPressed(javafx.event.ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardPage.fxml"));
            Parent root = loader.load();

            DashboardController controller2 = loader.getController();
            controller2.setIdsToAdd((getSelectedFoods()));

            controller2.addSelectedFoods(diaryID);

            Scene HomeScene = new Scene(root,750,500);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(HomeScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void newFoodPressed(javafx.event.ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newFoodScene.fxml"));
            Parent root = loader.load();

            NewFoodSceneController newFoodSceneController = loader.getController();
            newFoodSceneController.setDiaryID(getDiaryID());

            Scene HomeScene = new Scene(root,500,500);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(HomeScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
