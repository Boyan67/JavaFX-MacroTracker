package macroTracker.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class FoodDetailsController {

    @FXML
    Label lblName;
    @FXML
    Label lblCarbs;
    @FXML
    Label lblCategory;
    @FXML
    Label lblFats;
    @FXML
    Label lblProtein;
    @FXML
    Label lblCalories;
    @FXML
    Label lblIngredients;


    public void setLblName(String name) {
        lblName.setText(name);
    }

    public void setLblCategory(String category) {
        lblCategory.setText(category);
    }

    public void setLblCarbs(String carbs) {
        lblCarbs.setText(carbs);
    }

    public void setLblFats(String fats) {
        lblFats.setText(fats);
    }

    public void setLblProtein(String protein) {
        lblProtein.setText(protein);
    }

    public void setLblCalories(String calories) {
        lblCalories.setText(calories);
    }

    public void setLblIngredients(String ingredients) {

        String[] values = ingredients.split(",");

        StringBuilder newString = new StringBuilder();
        for (String i : values){
            newString.append("◙ ").append(i.trim()).append("\n");
        }

        lblIngredients.setStyle("-fx-border-color: #333;");
        lblIngredients.setPadding(new Insets(5));
        lblIngredients.setText(newString.toString());
    }
}
