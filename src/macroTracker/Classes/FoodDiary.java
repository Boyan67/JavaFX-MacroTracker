package macroTracker.Classes;

import macroTracker.Database.Database;

import java.util.ArrayList;

public class FoodDiary {
    private int id;
    private Database database;
    private int totalCalories;
    private int totalCarbs;
    private int totalFats;
    private int totalProtein;

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
        calculateMacros();
        return database.getAllFoods();
    }

    public void addFood(Food food) {
        database.insertFood(food);
        calculateMacros();
    }

    public void addSelectedFoods(ArrayList<Integer> ids) {
        database.insertSelected(ids);
        calculateMacros();
    }

    public void removeFood(int id){
        database.deleteFood(id);
        calculateMacros();
    }


    public void searchFoods(String search){
        ArrayList<Food> foundFoods = database.search(search);
        for (Food food: foundFoods){
            System.out.println(food);
        }
    }

    public void calculateMacros(){
        totalCarbs = 0;
        totalFats = 0;
        totalProtein = 0;
        totalCalories = 0;
        for (Food food : database.getAllFoods()){
            totalCarbs += food.getCarbs();
            totalFats += food.getFats();
            totalProtein += food.getProtein();
            totalCalories += food.getCalories();
        }
    }

    public void clearDiary(){
        database.deleteAllFoods();
        calculateMacros();
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public int getTotalCarbs() {
        return totalCarbs;
    }

    public int getTotalFats() {
        return totalFats;
    }

    public int getTotalProtein() {
        return totalProtein;
    }
}
