package ua.kozlov.madfood.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OneDayPlanR extends RealmObject{

    private String foodName;
    private String foodWeight;
    private String calories;
    private String fat;
    private String carbonates;
    private String proteins;
    private String gi;
    private float progress;
    @PrimaryKey
    private long date;

    public OneDayPlanR() {
    }

    public OneDayPlanR(String foodName, String foodWeight, String calories,
                       String fat, String carbonates, String proteins,
                       String gi, float progress, long date) {
        this.foodName = foodName;
        this.foodWeight = foodWeight;
        this.calories = calories;
        this.fat = fat;
        this.carbonates = carbonates;
        this.proteins = proteins;
        this.gi = gi;
        this.progress = progress;
        this.date = date;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(String foodWeight) {
        this.foodWeight = foodWeight;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbonates() {
        return carbonates;
    }

    public void setCarbonates(String carbonates) {
        this.carbonates = carbonates;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }

    public String getGi() {
        return gi;
    }

    public void setGi(String gi) {
        this.gi = gi;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
