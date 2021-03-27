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
    String databaseName;

    public Database(String databaseName){
        this.databaseName = databaseName;
        String url = "jdbc:mysql://127.0.0.1:3306/macro_tracker";
        String user  = "root";
        String password = "secret";
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public ArrayList<Food> getAllFoods(){
        Statement statement;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM " + databaseName;
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            return getFoodList(rs);
        } catch (SQLException throwables) {
            System.out.println("getAllFoods() error: ");
            throwables.printStackTrace();
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
                Food food = new Food(id, name, carbs, fats, protein, calories);
                foodList.add(food);
        }
        } catch (SQLException throwables) {
            System.out.println("getFoodList() error: ");
            throwables.printStackTrace();
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
            Statement statement = connection.createStatement();
            String values = String.format("( NULL,'%s',%d, %d, %d, %d)", name, carbs, fats, protein,  calories);
            String sql = "INSERT INTO " + databaseName + " VALUES";

            statement.executeUpdate((sql + values), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                food.setId(rs.getInt(1));
            }
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteFood(int id){
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + databaseName + " WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
//            System.out.println(statement.toString());
        } catch (SQLException throwables) {
            System.out.println("deleteFood(Food food) error: ");
            throwables.printStackTrace();
        }
    }

    public void deleteAllFoods(){
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + databaseName);
            statement.executeUpdate();
            PreparedStatement resetAutoIncrement = connection.prepareStatement("ALTER TABLE " + databaseName + " AUTO_INCREMENT = 1;");
            resetAutoIncrement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("deleteAllFoods() error: ");
            throwables.printStackTrace();
        }
    }

    public ArrayList<Food> search(String searchTerm){
        String query = "SELECT * FROM " +databaseName+ " WHERE name LIKE '%" + searchTerm + "%'";
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ){
            return getFoodList(resultSet);
        } catch (SQLException throwables) {
            System.out.println("search() error: ");
            throwables.printStackTrace();
        }
        return null;
    }
}
