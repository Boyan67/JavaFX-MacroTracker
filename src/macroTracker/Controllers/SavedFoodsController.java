package macroTracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import macroTracker.Classes.Food;
import macroTracker.Database.Database;

import java.io.IOException;
import java.util.ArrayList;

public class SavedFoodsController {

    Database database = new Database("food_list");
    private int diaryID;

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

    public void initialize(){
        ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        carbs.setCellValueFactory(new PropertyValueFactory<>("Carbs"));
        fats.setCellValueFactory(new PropertyValueFactory<>("Fats"));
        protein.setCellValueFactory(new PropertyValueFactory<>("Protein"));
        calories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
        savedFoodsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        showTable();
    }

    public void fillDiary(){
        ObservableList<Food> foodObservableList = FXCollections.observableArrayList();
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

    public void setDiaryID(int diaryID) {
        this.diaryID = diaryID;
    }

}
