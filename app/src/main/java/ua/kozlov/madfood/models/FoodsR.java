package ua.kozlov.madfood.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class FoodsR extends RealmObject {

    @PrimaryKey
    private int id;
    private String category;
    @Required
    private String foodName;
    private float calories;
    private float fat;
    private float carbonates;
    private float proteins;
    private float gi;

    public FoodsR() {
    }

    public FoodsR(int id, String category, String foodName,
                  float calories, float fat, float carbonates,
                  float proteins, float gi) {
        this.id = id;
        this.category = category;
        this.foodName = foodName;
        this.calories = calories;
        this.fat = fat;
        this.carbonates = carbonates;
        this.proteins = proteins;
        this.gi = gi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarbonates() {
        return carbonates;
    }

    public void setCarbonates(float carbonates) {
        this.carbonates = carbonates;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getGi() {
        return gi;
    }

    public void setGi(float gi) {
        this.gi = gi;
    }
}
