package macroTracker.Classes;

public class Food {

    int id;
    private String name;
    private int carbs;
    private int fats;
    private int protein;
    private int calories;

    public Food(){
        this.name = "-";
        this.carbs = 0;
        this.fats = 0;
        this.protein = 0;
        this.calories = 0;
    }

    public Food(String name, int carbs, int fats, int protein, int calories) {
        this.name = name;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
        this.calories = calories;
    }

    public Food(int id, String name, int carbs, int fats, int protein, int calories) {
        this.name = name;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
        this.calories = calories;
        this.id = id;
    }

    @Override
    public String toString() {
        return "name: " + name + ", calories: " + calories;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCalories() {
        return calories;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    public int getCarbs() {
        return carbs;
    }
    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }
    public int getFats() {
        return fats;
    }
    public void setFats(int fats) {
        this.fats = fats;
    }
    public int getProtein() {
        return protein;
    }
    public void setProtein(int protein) {
        this.protein = protein;
    }
}
