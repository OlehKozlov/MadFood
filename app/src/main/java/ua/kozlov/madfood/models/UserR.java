package ua.kozlov.madfood.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserR extends RealmObject {
   // public static final String NAME_FIELD = "name";
  //  public static final String WIEIGHT_FIELD = "weight";
//    public static final String DATE_FIELD = "date";
    @PrimaryKey
    private String name;
    private float weight;
    private String date;

    public UserR() {
    }

    public UserR(String name, float weight, String date) {
        this.name = name;
        this.weight = weight;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
