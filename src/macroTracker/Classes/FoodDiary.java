package macroTracker.Classes;

import macroTracker.Classes.Food;
import macroTracker.Database.Database;

import java.util.ArrayList;

public class FoodDiary {
    private int id;
    private Database database;
    private int totalCalories;

    public void changeDiary(int day){
        database = new Database("day"+day);
    }

    public FoodDiary(int id){
        this.id = id;
        database = new Database("day"+id);
        this.totalCalories = 0;
    }

    public void addFood(Food food){
        database.insertFood(food);
        totalCalories += food.getCalories();
    }

    public void removeFood(int id){
        database.deleteFood(id);
        calculateCalories();
    }
    public void removeFood(){
        database.deleteFood();
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
        for (Food food : database.getAllFoods()){
            totalCalories += food.getCalories();
        }
    }

    public void clearDiary(){
        for (Food ignored : database.getAllFoods()){
            database.deleteFood();
        }
    }

    public int getTotalCalories() {
        return totalCalories;
    }
}
