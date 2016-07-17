package ua.kozlov.madfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class OneDayPlan {
    private DBHealper dbHealper;
    private SQLiteDatabase db;
    private static ArrayList<String> nameList = new ArrayList<>();
    private static ArrayList<String> weightList = new ArrayList<>();

    public OneDayPlan(){
    }

    public void setFoodToPlan(Context context, final String foodName, final String weight, final ArrayList<String> parametersList){
        dbHealper = new DBHealper(context);
        db = dbHealper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("foodname", foodName);
        cv.put("foodweight", weight);
        cv.put("calories", parametersList.get(0).toString());
        cv.put("fats", parametersList.get(1).toString());
        cv.put("carbonates", parametersList.get(2).toString());
        cv.put("proteins", parametersList.get(3).toString());
        cv.put("gi", parametersList.get(4).toString());
        cv.put("date", "today");
        Log.d("mylog", foodName);
        Log.d("mylog", weight);
        Log.d("mylog", parametersList.get(4).toString());
        db.insert("oneDayPlan", null, cv);
        db.close();
        dbHealper.close();
    }

    public ArrayList<String> getOneDayPlanNamesList(Context context){
        dbHealper = new DBHealper(context);
        db = dbHealper.getReadableDatabase();
        String name = "";
        Cursor c = db.query("oneDayPlan", null, null, null, null, null, null);
        ArrayList<String> list = new ArrayList<>();
        c.moveToLast();
        do {
            int nameColIndex = c.getColumnIndex("foodname");
            name = c.getString(nameColIndex);
            list.add(name);
        } while (c.moveToPrevious());
        c.close();
        db.close();
        dbHealper.close();
        return list;
    }

    public ArrayList<String> getOneDayPlanWeightsList(Context context){
        dbHealper = new DBHealper(context);
        db = dbHealper.getReadableDatabase();
        String weight = "";
        Cursor c = db.query("oneDayPlan", null, null, null, null, null, null);
        ArrayList<String> list = new ArrayList<>();
        c.moveToLast();
        do {
            int weightColIndex = c.getColumnIndex("foodweight");
            weight = c.getString(weightColIndex);
            list.add(weight);
        } while (c.moveToPrevious());
        c.close();
        db.close();
        dbHealper.close();
        return list;
    }

    public View setChoosenFoods(View view, final String name, final String weight, Context context){
        Log.d("mylog", name);
        Log.d("mylog", weight);
        if (nameList.contains(name)){
            for (int i = 0; i < nameList.size(); i++) {
                if (nameList.get(i).toString().equals(name) && weightList.get(i).toString().equals(weight)){
                    Button button = (Button) view;
                    Drawable icon = context.getResources().getDrawable(R.drawable.ic_item_unselected);
                    button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                    Log.d("mylog", "remove btn");
                    view = button;
                    nameList.remove(i);
                    weightList.remove(i);
                }
            }
        } else {
            Drawable icon = context.getResources().getDrawable(R.drawable.ic_item_selected);
            Button button = (Button) view;
            button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            view = button;
            Log.d("mylog", "add btn");
            nameList.add(name);
            weightList.add(weight);
        }
        return view;
    }

    public void removeFoodFromPlan(Context context){
        dbHealper = new DBHealper(context);
        db = dbHealper.getWritableDatabase();
        for (int i = 0; i < nameList.size(); i++){
            db.execSQL("delete from oneDayPlan where foodname='"+nameList.get(i).toString()+"' AND foodweight='"+weightList.get(i).toString()+"'" );
        }
        db.close();
        dbHealper.close();
    }
}
