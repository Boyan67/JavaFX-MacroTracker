package macroTracker.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import macroTracker.Database.Database;

import java.io.IOException;
import java.util.ArrayList;

public class ShoppingListController {

    @FXML
    ListView<CheckBox> shoppingListView;

    Database database = new Database("1");

    public void savedFoodLink(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("savedFoodsCopy.fxml"));
            Parent root = loader.load();
            Scene HomeScene = new Scene(root);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            //set Stage boundaries to visible bounds of the main screen
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            window.setWidth(primaryScreenBounds.getWidth());
            window.setHeight(primaryScreenBounds.getHeight());

            window.setScene(HomeScene);
            window.show();
        } catch (IOException e) {
            System.out.println("savedFoodLink Button Error: ");
            e.printStackTrace();
        }
    }

    public void dashboardLinkPressed(javafx.event.ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene HomeScene = new Scene(root,750,500);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            //window.setMaximized(true);
            window.setScene(HomeScene);
            window.show();
        } catch (IOException e) {
            System.out.println("dashBoardLinkPressed() Error: ");
            e.printStackTrace();
        }
    }

    public void fillDiary(ArrayList<String> ingredients){
        for (String string : ingredients){
            String[] values = string.split(",");
            for (String value : values){
                shoppingListView.getItems().add(new CheckBox(value.trim()));
            }
        }
        addDuplicates(ingredients);
    }
    public void clearView(){
        if (shoppingListView.getItems().size() > 0) {
            shoppingListView.getItems().subList(0, shoppingListView.getItems().size()).clear();
        }
    }
    public void generateListPressed(){
        clearView();
        fillDiary(database.getIngredientsList());
    }


    public void addDuplicates(ArrayList<String> ingredients){
        for (String string : ingredients){
            String[] values = string.split(",");
            for (String value : values){
                String[] splitValues = value.split("-");
                for (String j : splitValues){
                    char c = j.trim().charAt(0);
                    if (c >= 'a' && c <= 'z') {

                    }
                }
            }
        }
    }


    public void removeCheckedPressed(){
        shoppingListView.getItems().removeIf(CheckBox::isSelected);
    }



}
