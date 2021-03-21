package macroTracker;
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

    private ArrayList<Food> getFoodList(ResultSet resultSet){
        ArrayList<Food> foodList = new ArrayList<>();
        try {
            while (resultSet.next()){
                String name = resultSet.getString("name");
                int calories = resultSet.getInt("calories");
                Food food = new Food(name, calories);
                foodList.add(food);
        }
        } catch (SQLException throwables) {
            System.out.println("getFoodList() error: ");
            throwables.printStackTrace();
        }
        return foodList;
    }

    public int generateId() throws SQLException {
        ArrayList<Food> currentFoods = getAllFoods();
        if (currentFoods.size() > 0) {
            return currentFoods.size() + 1;
        }
        return 0;
    }

    public void insertFood(Food food){
        try {
            int id = generateId();
            food.setId(id);
            String name = food.getName();
            int calories = food.getCalories();
            Statement statement = connection.createStatement();
            String values = "(" + id + "," + "'" + name + "'" + "," + calories + ")";
            String sql = "INSERT INTO " + databaseName + " VALUES";
            statement.executeUpdate(sql + values);
        } catch (SQLException throwables) {
            System.out.println("insertFood() error: ");
            throwables.printStackTrace();
        }
    }

    public void deleteFood(Food food){
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + databaseName + " WHERE ID = ?");
            statement.setInt(1, food.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("deleteFood(Food food) error: ");
            throwables.printStackTrace();
        }
    }
    public void deleteFood(){
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + databaseName + " WHERE ID = ?");
            statement.setInt(1, generateId()-1);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("deleteFood() error: ");
            throwables.printStackTrace();
        }
    }
    public ArrayList<Food> search(String searchTerm){
        String query = "SELECT * FROM " +databaseName+ " WHERE name LIKE '%" + searchTerm + "%'";
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ){
            return getFoodList(resultSet);
        } catch (SQLException throwables) {
            System.out.println("search() error: ");
            throwables.printStackTrace();
        }
        return null;
    }
}
