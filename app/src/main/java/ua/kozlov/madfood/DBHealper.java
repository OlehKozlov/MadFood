package ua.kozlov.madfood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHealper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "madFoodDb.db";
    private static final int DATABASE_VERSION = 1;
    private final String USER_NAME_TABLE_CREATE_SCRIPT = "create table " +
            "userNameTable (id integer primary key autoincrement, name text);";
    private final String USER_WEIGHT_TABLE_CREATE_SCRIPT = "create table " +
            "userWeightTable (id integer primary key autoincrement, weight text, " +
            "date text" + ");";
    private final String ONE_DAY_PLANE_TABLE_CREATE_SCRIPT = "create table " +
            "oneDayPlan (id integer primary key autoincrement,"
            + " foodname text,"
            + " foodweight text,"
            + " calories text,"
            + " fats text,"
            + " carbonates text,"
            + " proteins text,"
            + " gi text,"
            + " date text" + ");";

    public DBHealper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_NAME_TABLE_CREATE_SCRIPT);
        db.execSQL(USER_WEIGHT_TABLE_CREATE_SCRIPT);
        db.execSQL(ONE_DAY_PLANE_TABLE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_NAME_TABLE_CREATE_SCRIPT);
        db.execSQL("DROP TABLE IF EXISTS " + USER_WEIGHT_TABLE_CREATE_SCRIPT);
        db.execSQL("DROP TABLE IF EXISTS " + ONE_DAY_PLANE_TABLE_CREATE_SCRIPT);
    }
}
