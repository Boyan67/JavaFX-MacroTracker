package macroTracker.Classes;

import macroTracker.Classes.Food;
import macroTracker.Database.Database;

import java.util.ArrayList;

public class FoodDiary {
    private int id;
    private ArrayList<Food> foods;
    Database database;
    private int totalCalories;

    public void changeDiary(int diaryNum){
        database = new Database("day"+diaryNum);
    }

    public FoodDiary(int id){
        this.id = id;
        foods = new ArrayList<>();
        database = new Database("day"+id);
        this.totalCalories = 0;
    }

    public void addFood(Food food){
        foods.add(food);
        database.insertFood(food);
        totalCalories += food.getCalories();
    }
    public void removeFood(Food food){
        foods.remove(food);
        totalCalories -= food.getCalories();
    }
    public void removeFood(){
        totalCalories -= foods.get(foods.size()-1).getCalories();
        foods.remove(foods.size()-1);
        database.deleteFood();

    }

    public void displayFoods(){
        for (Food food : foods){
            System.out.println(food);
        }
    }

    public void populateDiary(){
        foods = database.getAllFoods();
        calculateCalories();
    }

    public void searchFoods(String search){
        ArrayList<Food> foundFoods = database.search(search);
        for (Food food: foundFoods){
            System.out.println(food);
        }
    }

    public void calculateCalories(){
        for (Food food : foods){
            totalCalories += food.getCalories();
        }
    }

    public int getTotalCalories() {
        return totalCalories;
    }
}
