package macroTracker.Database;
import macroTracker.Classes.Food;

import java.sql.*;
import java.util.ArrayList;

/*******************************************************
 *
 * Database Class Responsible for handling queries
 *
 *******************************************************/

public class Database {

    Connection connection;
    final String databaseName;

    public Database(String databaseName){
        this.databaseName = databaseName;
        String url = "jdbc:mysql://127.0.0.1:3306/macro_tracker";
        String user  = "root";
        String password = "secret";
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Creating Database error - Database(): ");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getIngredientsList(){
        ArrayList<String> ingredients = new ArrayList<>();
        Statement statement;
        ResultSet rs;
        for (int i = 1; i <= 7; i++){
            try {
                String sql = "SELECT ingredients FROM " + "day"+i;
                statement = connection.createStatement();
                rs = statement.executeQuery(sql);
                while (rs.next()){
                    ingredients.add(rs.getString(1));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ingredients;
    }

    public ArrayList<Food> getAllFoods(){
        Statement statement;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM " + databaseName;
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            return getFoodList(rs);
        } catch (SQLException e) {
            System.out.println("getAllFoods() error: ");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Food> getFoodList(ResultSet resultSet){
        ArrayList<Food> foodList = new ArrayList<>();
        try {
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int carbs = resultSet.getInt("carbs");
                int fats = resultSet.getInt("fats");
                int protein = resultSet.getInt("protein");
                int calories = resultSet.getInt("calories");
                String category = resultSet.getString("category");
                String ingredients = resultSet.getString("ingredients");
                Food food = new Food(id, name, carbs, fats, protein, calories, category, ingredients);
                foodList.add(food);
        }
        } catch (SQLException e) {
            System.out.println("getFoodList() error: ");
            e.printStackTrace();
        }
        return foodList;
    }

    public void insertFood(Food food){
        try {
            String name = food.getName();
            int carbs = food.getCarbs();
            int fats = food.getFats();
            int protein = food.getProtein();
            int calories = food.getCalories();
            String category = food.getCategory();
            String ingredients = food.getIngredients();

            Statement statement = connection.createStatement();
            String values = String.format("( NULL,'%s',%d, %d, %d, %d, '%s', '%s')", name, carbs, fats, protein,  calories, category, ingredients);
            String sql = "INSERT INTO " + databaseName + " VALUES";

            statement.executeUpdate((sql + values), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                food.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("insertFood() error: ");
            e.printStackTrace();
        }
    }

    public void deleteFood(int id){
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + databaseName + " WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteFood(Food food) error: ");
            e.printStackTrace();
        }
    }

    public void deleteAllFoods(){
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + databaseName);
            statement.executeUpdate();
            PreparedStatement resetAutoIncrement = connection.prepareStatement("ALTER TABLE " + databaseName + " AUTO_INCREMENT = 1;");
            resetAutoIncrement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteAllFoods() error: ");
            e.printStackTrace();
        }
    }

    public void insertSelected(ArrayList<Integer> ids) {
        try {
            for (int i : ids) {
                Statement selectStatement;
                ResultSet rs;
                String sql = "SELECT * FROM macro_tracker.food_list WHERE id = " + i;
                selectStatement = connection.createStatement();
                rs = selectStatement.executeQuery(sql);
                Food foodToAdd = createFoodNoId(rs);
                insertFood(foodToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Food createFoodNoId(ResultSet resultSet) {
        Food food = new Food();
        try {
            resultSet.next();
            String name = resultSet.getString("name");
            int carbs = resultSet.getInt("carbs");
            int fats = resultSet.getInt("fats");
            int protein = resultSet.getInt("protein");
            int calories = resultSet.getInt("calories");
            String category = resultSet.getString("category");
            String ingredients = resultSet.getString("ingredients");
            food = new Food(name, carbs, fats, protein, calories, category, ingredients);
            return food;
        } catch (SQLException e) {
            System.out.println("getFoodList() error: ");
            e.printStackTrace();
        }
        return food;
    }
}
