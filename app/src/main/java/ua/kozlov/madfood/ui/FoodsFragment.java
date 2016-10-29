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
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.FoodsR;
import ua.kozlov.madfood.ui.adapters.FoodsRecyclerAdapter;
import ua.kozlov.madfood.utils.DebugLogger;
import ua.kozlov.madfood.utils.RecyclerItemClickListener;

public class FoodsFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonAddFood;
    private Button mButtonDialogOk;
    private Button mButtonDialogCancel;
    private EditText mEditDialogFoodName;
    private EditText mEditDialogCalories;
    private EditText mEditDialogFats;
    private EditText mEditDialogProteins;
    private EditText mEditDialogCarbonates;
    private EditText mEditDialogGi;
    private Realm mRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foods_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerFoods);
        mButtonAddFood = (FloatingActionButton) view.findViewById(R.id.buttonAddFood);
        SharedPreferences sPref = view.getContext().getSharedPreferences("categories", Context.MODE_PRIVATE);
        final String categoryName = sPref.getString("category", "");
        SharedPreferences sPrefFood = view.getContext().getSharedPreferences("foods", Context.MODE_PRIVATE);
        final String foodName = sPrefFood.getString("food", "");
        DebugLogger.log(getString(R.string.log_food_name) + foodName);
        mRealm = Realm.getDefaultInstance();
        List<FoodsR> result = DatabaseManager.getFoods(mRealm, categoryName, foodName);
        ArrayList<String> foodsList = new ArrayList<>();
        mRealm.addChangeListener(element -> {
            RealmResults<FoodsR> foodsResults = DatabaseManager.getFoods(mRealm, categoryName, foodName);
            FoodsRecyclerAdapter adapter = new FoodsRecyclerAdapter(foodsResults);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(adapter);
        });
        for (FoodsR food : result){
            foodsList.add(food.getFoodName());
        }
        FoodsRecyclerAdapter adapter = new FoodsRecyclerAdapter(result);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(),
                (view1, position) -> {
                    DebugLogger.log(foodsList.get(position));
                    openSameFood(view1.getContext(), foodsList.get(position));
                }));
        final Dialog dialogAddFood = new Dialog(view.getContext());
        dialogAddFood.setContentView(R.layout.new_food_dialog);
        dialogAddFood.setTitle(R.string.foods_name);
        mEditDialogFoodName = (EditText) dialogAddFood.findViewById(R.id.editFoodNameDialog);
        mEditDialogCalories = (EditText) dialogAddFood.findViewById(R.id.editCaloriesDialog);
        mEditDialogFats = (EditText) dialogAddFood.findViewById(R.id.editFatsDialog);
        mEditDialogProteins = (EditText) dialogAddFood.findViewById(R.id.editProteinsDialog);
        mEditDialogCarbonates = (EditText) dialogAddFood.findViewById(R.id.editCarbonatesDialog);
        mEditDialogGi = (EditText) dialogAddFood.findViewById(R.id.editGiDialog);
        mButtonDialogOk = (Button) dialogAddFood.findViewById(R.id.buttonFoodDialogOk);
        mButtonDialogCancel = (Button) dialogAddFood.findViewById(R.id.buttonFoodDialogCancel);
        mButtonAddFood.setOnClickListener(v -> dialogAddFood.show());
        mButtonDialogOk.setOnClickListener(v -> {
            checkTheData();
            DatabaseManager databaseManager = new DatabaseManager();
            int id = databaseManager.getLastId(mRealm) + 1;
            String category = categoryName;
            String food = mEditDialogFoodName.getText().toString();
            float calories = Float.parseFloat(mEditDialogCalories.getText().toString());
            float fats = Float.parseFloat(mEditDialogFats.getText().toString());
            float carbonates = Float.parseFloat(mEditDialogCarbonates.getText().toString());
            float proteins = Float.parseFloat(mEditDialogProteins.getText().toString());
            float gi = Float.parseFloat(mEditDialogGi.getText().toString());
            databaseManager.saveNewFood(mRealm, id, category, food, calories, fats,
                    carbonates, proteins, gi);
            openCategory();
            dialogAddFood.dismiss();
        });
        mButtonDialogCancel.setOnClickListener(v -> dialogAddFood.dismiss());
        return view;
    }

    public void checkTheData(){
        if (mEditDialogFoodName.getText().toString().equals("")){
            mEditDialogFoodName.setText(R.string.default_food_name);
        }
        if (mEditDialogCalories.getText().toString().equals("")){
            mEditDialogCalories.setText(R.string.default_number_value);
        }
        if (mEditDialogFats.getText().toString().equals("")){
            mEditDialogFats.setText(R.string.default_number_value);
        }
        if (mEditDialogCarbonates.getText().toString().equals("")){
            mEditDialogCarbonates.setText(R.string.default_number_value);
        }
        if (mEditDialogProteins.getText().toString().equals("")){
            mEditDialogProteins.setText(R.string.default_number_value);
        }
        if (mEditDialogGi.getText().toString().equals("")){
            mEditDialogGi.setText(R.string.default_number_value);
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

    private void openCategory(){
        FoodsFragment foodsFragment = new FoodsFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainContainer, foodsFragment);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRealm.close();
        DebugLogger.log(getString(R.string.log_realm_closed));
    }
}
