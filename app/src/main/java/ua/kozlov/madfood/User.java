package ua.kozlov.madfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class User implements UserInterface {
    private DBHealper dbHealper;
    private SQLiteDatabase db;

    public User() {
    }

    @Override
    public void setUserName(final String name, Context context) {
        dbHealper = new DBHealper(context);
        db = dbHealper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        db.insert("userNameTable", null, cv);
        db.close();
        dbHealper.close();
    }

    @Override
    public String getUserName(Context context) {
        dbHealper = new DBHealper(context);
        db = dbHealper.getReadableDatabase();
        String name = "";
        Cursor c = db.query("userNameTable", null, null, null, null, null, null);
        if (c.moveToLast()) {
            int nameColIndex = c.getColumnIndex("name");
            name = c.getString(nameColIndex);
        }
        c.close();
        db.close();
        dbHealper.close();
        return name;
    }

    @Override
    public void setUserWeight(final float weight, Context context) {
        dbHealper = new DBHealper(context);
        db = dbHealper.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.DAY_OF_MONTH) + ":" +
                calendar.get(Calendar.MONTH) + ":" + calendar.get(Calendar.YEAR);
        ContentValues cv = new ContentValues();
        cv.put("weight", weight);
        cv.put("date", date);
        db.insert("userWeightTable", null, cv);
        db.close();
        dbHealper.close();
    }

    @Override
    public float getUserCurrentWeight(Context context) {
        dbHealper = new DBHealper(context);
        db = dbHealper.getReadableDatabase();
        float weight = 0;
        Cursor c = db.query("userWeightTable", null, null, null, null, null, null);
        if (c.moveToLast()) {
            int weightColIndex = c.getColumnIndex("weight");
            weight = c.getFloat(weightColIndex);
        }
        c.close();
        db.close();
        dbHealper.close();
        return weight;
    }

    @Override
    public ArrayList<Map<String, Float>> getUserEveryWeights(Context context) {
        dbHealper = new DBHealper(context);
        db = dbHealper.getReadableDatabase();
        ArrayList<Map<String, Float>> weightList = new ArrayList<>();
        Cursor c = db.query("userWeightTable", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int weightColIndex = c.getColumnIndex("weight");
            int dateColIndex = c.getColumnIndex("date");
            Map<String, Float> map = new HashMap<>();
            map.put(c.getString(dateColIndex), c.getFloat(weightColIndex));
            weightList.add(map);
        }
        c.close();
        db.close();
        dbHealper.close();
        return weightList;
    }
}
