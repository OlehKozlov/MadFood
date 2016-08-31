package ua.kozlov.madfood.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PlanDataR extends RealmObject{
    @PrimaryKey
    private String date;
    private String foodName;
    private int weight;

    public PlanDataR() {
    }

    public PlanDataR(String date, String foodName, int weight) {
        this.date = date;
        this.foodName = foodName;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
