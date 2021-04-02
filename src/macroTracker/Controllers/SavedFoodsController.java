package macroTracker.Controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import macroTracker.Classes.Food;
import macroTracker.Database.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class SavedFoodsController {

    final Database database = new Database("food_list");
    private int diaryID;
    final ObservableList<Food> foodObservableList = FXCollections.observableArrayList();

    @FXML
    public TextField filterField;
    @FXML
    TableView<Food> savedFoodsTable;

    @FXML
    private TableColumn<Food, Integer> ID;
    @FXML
    private TableColumn<Food, String> name;
    @FXML
    private TableColumn<Food, Integer> carbs;
    @FXML
    private TableColumn<Food, Integer> fats;
    @FXML
    private TableColumn<Food, Integer> protein;
    @FXML
    private TableColumn<Food, Integer> calories;
    @FXML
    private TableColumn<Food, String> category;
    @FXML
    private TableColumn<Food, String> ingredients;

    @FXML
    private ComboBox<String> pickCategory;
    @FXML
    private ComboBox<String> pickMacroFilter;

    // ========== Class Specific ==========
    public void setDiaryID(int diaryID) {
        this.diaryID = diaryID;
    }
    public int getDiaryID() {
        return diaryID;
    }

    // ========== Initial Function ==========
    public void initialize(){
        ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        carbs.setCellValueFactory(new PropertyValueFactory<>("Carbs"));
        fats.setCellValueFactory(new PropertyValueFactory<>("Fats"));
        protein.setCellValueFactory(new PropertyValueFactory<>("Protein"));
        calories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
        category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        ingredients.setCellValueFactory(new PropertyValueFactory<>("Ingredients"));
        savedFoodsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        pickCategory.getItems().add("Breakfast");
        pickCategory.getItems().add("Lunch/Dinner");
        pickCategory.getItems().add("Snacks");
        pickCategory.getItems().add("All");


        pickMacroFilter.getItems().add("High Carbs");
        pickMacroFilter.getItems().add("High Fats");
        pickMacroFilter.getItems().add("High Protein");
        pickMacroFilter.getItems().add("No Filter");

        showTable();

    }

    // ========== Button Functions ==========
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

    public void deleteSelected(){
        ArrayList<Integer> selectedIDs = getSelectedFoods();
        for (int i : selectedIDs){
            database.deleteFood(i);
        }
        showTable();
    }


    // ========== Functionality ==========
    public void multipleFilters(){
        ObjectProperty<Predicate<Food>> nameFilter = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Food>> categoryFilter = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Food>> macroFilter = new SimpleObjectProperty<>();

        nameFilter.bind(Bindings.createObjectBinding(() ->
                food -> food.getName().toLowerCase().contains(filterField.getText().toLowerCase()),
                filterField.textProperty()));

        categoryFilter.bind(Bindings.createObjectBinding(() ->
                food -> pickCategory.getValue() == null || pickCategory.getValue().equals("All") ||
                        pickCategory.getValue().equals(food.getCategory()),
                pickCategory.valueProperty()));

        macroFilter.bind(Bindings.createObjectBinding(() ->
                food -> pickMacroFilter.getValue() == null || pickMacroFilter.getValue().equals("No Filter") ||
                pickMacroFilter.getValue().equals("High Carbs")
                        && ((float)food.getCarbs() / (food.getCarbs()+food.getFats()+food.getProtein())) > .4 ||
                pickMacroFilter.getValue().equals("High Fats")
                        && ((float)food.getFats() / (food.getCarbs()+food.getFats()+food.getProtein())) > .4 ||
                pickMacroFilter.getValue().equals("High Protein")
                        && ((float)food.getProtein() / (food.getCarbs()+food.getFats()+food.getProtein())) > .2,
                pickMacroFilter.valueProperty()
                ));


        FilteredList<Food> filteredItems = new FilteredList<>(foodObservableList);
        savedFoodsTable.setItems(filteredItems);

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(
                () -> nameFilter.get().and(categoryFilter.get()).and(macroFilter.get()),
                nameFilter, categoryFilter, macroFilter));

    }


    public void fillDiary(){
        foodObservableList.addAll(database.getAllFoods());
        savedFoodsTable.setItems(foodObservableList);
    }

    public void clearView() {
        foodObservableList.clear();
    }

    public void showTable(){
        clearView();
        fillDiary();
        multipleFilters();
    }

    public ArrayList<Integer> getSelectedFoods(){
        ArrayList<Integer> indexes = new ArrayList<>();

        for (Food food : savedFoodsTable.getSelectionModel().getSelectedItems()){
            indexes.add(food.getId());
        }
        return indexes;
    }


}
