package macroTracker;

public class Food {

    int id;
    private String name;
    private int calories;

    public Food(){
        this.name = "-";
        this.calories = 0;
    }

    public Food(String name, int calories){
        this.name = name;
        this.calories = calories;
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
}
