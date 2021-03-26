package macroTracker.Classes;

import macroTracker.Database.Database;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodDiary {
    private int id;
    private Database database;
    private int totalCalories;

    public void changeDiary(int day){
        id = day;
        database = new Database("day"+day);
    }


    public FoodDiary(int id){
        this.id = id;
        database = new Database("day"+id);
        this.totalCalories = 0;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Food> getEveryFood(){
        return database.getAllFoods();
    }

    public void addFood(Food food) throws SQLException {
        database.insertFood(food);
        calculateCalories();
    }

    public void removeFood(int id){
        database.deleteFood(id);
        System.out.println("Deleted food with id: " + id);
        calculateCalories();
    }

    public void displayFoods(){
        for (Food food : database.getAllFoods()){
            System.out.println(food);
        }
    }

    public void searchFoods(String search){
        ArrayList<Food> foundFoods = database.search(search);
        for (Food food: foundFoods){
            System.out.println(food);
        }
    }

    public void calculateCalories(){
        totalCalories = 0;
        for (Food food : database.getAllFoods()){
            totalCalories += food.getCalories();
        }
    }

    public void clearDiary(){
        database.deleteAllFoods();
    }

    public int getTotalCalories() {
        return totalCalories;
    }
}
