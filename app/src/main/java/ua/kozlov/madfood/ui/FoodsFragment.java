package ua.kozlov.madfood.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.FoodsR;
import ua.kozlov.madfood.ui.adapters.FoodsRecyclerAdapter;
import ua.kozlov.madfood.utils.DebugLogger;
import ua.kozlov.madfood.utils.RecyclerItemClickListener;

public class FoodsFragment extends Fragment{
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddFood;
    private Button buttonDialogOk;
    private Button buttonDialogCancel;
    private EditText editDialogFoodName;
    private EditText editDialogCalories;
    private EditText editDialogFats;
    private EditText editDialogProteins;
    private EditText editDialogCarbonates;
    private EditText editDialogGi;
    private Realm realm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foods_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerFoods);
        buttonAddFood = (FloatingActionButton) view.findViewById(R.id.buttonAddFood);
        SharedPreferences sPref = view.getContext().getSharedPreferences("categories", Context.MODE_PRIVATE);
        final String categoryName = sPref.getString("category", "");
        realm = Realm.getDefaultInstance();
        List<FoodsR> result = DatabaseManager.getFoods(realm, categoryName);
        ArrayList<String> foodsList = new ArrayList<>();
        for (FoodsR food : result){
            foodsList.add(food.getFoodName().toString());
        }
        FoodsRecyclerAdapter adapter = new FoodsRecyclerAdapter(result);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(),
                new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                DebugLogger.log(foodsList.get(position).toString());
                openSameFood(view.getContext(), foodsList.get(position).toString());
            }
        }));
        final Dialog dialogAddFood = new Dialog(view.getContext());
        dialogAddFood.setContentView(R.layout.new_food_dialog);
        dialogAddFood.setTitle(R.string.foods_name);
        editDialogFoodName = (EditText) dialogAddFood.findViewById(R.id.editFoodNameDialog);
        editDialogCalories = (EditText) dialogAddFood.findViewById(R.id.editCaloriesDialog);
        editDialogFats = (EditText) dialogAddFood.findViewById(R.id.editFatsDialog);
        editDialogProteins = (EditText) dialogAddFood.findViewById(R.id.editProteinsDialog);
        editDialogCarbonates = (EditText) dialogAddFood.findViewById(R.id.editCarbonatesDialog);
        editDialogGi = (EditText) dialogAddFood.findViewById(R.id.editGiDialog);
        buttonDialogOk = (Button) dialogAddFood.findViewById(R.id.buttonFoodDialogOk);
        buttonDialogCancel = (Button) dialogAddFood.findViewById(R.id.buttonFoodDialogCancel);
        buttonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddFood.show();
            }
        });
        buttonDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTheData();
                DatabaseManager databaseManager = new DatabaseManager();
                int id = databaseManager.getLastId(realm) + 1;
                String category = categoryName;
                String food = editDialogFoodName.getText().toString();
                float calories = Float.parseFloat(editDialogCalories.getText().toString());
                float fats = Float.parseFloat(editDialogFats.getText().toString());
                float carbonates = Float.parseFloat(editDialogCarbonates.getText().toString());
                float proteins = Float.parseFloat(editDialogProteins.getText().toString());
                float gi = Float.parseFloat(editDialogGi.getText().toString());
                databaseManager.saveNewFood(id, category, food, calories, fats,
                        carbonates, proteins, gi);
                openSameFood(view.getContext(), food);
                dialogAddFood.dismiss();
            }
        });
        buttonDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddFood.dismiss();
            }
        });

        return view;
    }

    public void checkTheData(){
        if (editDialogFoodName.getText().toString().equals("")){
            editDialogFoodName.setText("My food");
        }
        if (editDialogCalories.getText().toString().equals("")){
            editDialogCalories.setText("1");
        }
        if (editDialogFats.getText().toString().equals("")){
            editDialogFats.setText("1");
        }
        if (editDialogCarbonates.getText().toString().equals("")){
            editDialogCarbonates.setText("1");
        }
        if (editDialogProteins.getText().toString().equals("")){
            editDialogProteins.setText("1");
        }
        if (editDialogGi.getText().toString().equals("")){
            editDialogGi.setText("1");
        }
    }

    public void openSameFood(Context context, String food){
        SharedPreferences sPref =
                context.getSharedPreferences("foods", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("food", food);
        editor.commit();
        SameFoodFragment sameFoodFragment = new SameFoodFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainContainer, sameFoodFragment).addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }
}
