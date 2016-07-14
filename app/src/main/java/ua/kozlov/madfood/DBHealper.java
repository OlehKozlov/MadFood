package ua.kozlov.madfood;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHealper extends SQLiteOpenHelper {
    private static final String databaseName = "madFoodDb.db";
    private static final int databaseVersion = 1;

    public DBHealper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userNameTable ("
                + "id integer primary key autoincrement,"
                + " name text" + ");");
        db.execSQL("create table userWeightTable ("
                + "id integer primary key autoincrement,"
                + " weight text,"
                + " date text" + ");");
        db.execSQL("create table oneDayPlan ("
                + "id integer primary key autoincrement,"
                + " foodname text,"
                + " foodweight text,"
                + " calories text,"
                + " fats text,"
                + " carbonates text,"
                + " proteins text,"
                + " gi text,"
                + " date text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
