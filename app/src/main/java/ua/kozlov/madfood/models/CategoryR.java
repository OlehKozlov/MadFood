package ua.kozlov.madfood.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CategoryR extends RealmObject {

    @PrimaryKey
    @Required
    private String name;

    public CategoryR() {
    }

    public CategoryR(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
