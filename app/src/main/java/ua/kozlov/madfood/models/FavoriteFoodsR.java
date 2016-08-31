package ua.kozlov.madfood.models;

import io.realm.RealmObject;

public class FavoriteFoodsR extends RealmObject {
    private String foodName;
    private int count;

    public FavoriteFoodsR() {
    }

    public FavoriteFoodsR(String foodName, int count) {
        this.foodName = foodName;
        this.count = count;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
