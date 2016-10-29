package ua.kozlov.madfood.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserR extends RealmObject {

    private String name;
    private float weight;
    private float height;
    private int plan;
    @PrimaryKey
    private String date;

    public UserR() {
    }

    public UserR(String name, float weight, float height, int plan, String date) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.plan = plan;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
