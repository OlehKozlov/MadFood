package ua.kozlov.madfood.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProgressDataR extends RealmObject {

    @PrimaryKey
    private String date;
    private int progress;

    public ProgressDataR() {
    }

    public ProgressDataR(String date, int progress) {
        this.date = date;
        this.progress = progress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
