package ua.kozlov.madfood.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.realm.Realm;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.FoodsR;
import ua.kozlov.madfood.utils.CircleView;

public class SameFoodFragment extends Fragment {
    private FrameLayout frameLayout;
    private Realm realm;
    private TextView foodTitle;
    private TextView foodCalories;
    private TextView foodFats;
    private TextView foodCarbonates;
    private TextView foodProteins;
    private TextView foodGi;
    private EditText foodWeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.same_food_fragment, container, false);
        frameLayout = (FrameLayout) view.findViewById(R.id.frammeSameFoodContainer);
        foodTitle = (TextView) view.findViewById(R.id.textSameFoodTitle);
        foodCalories = (TextView) view.findViewById(R.id.textSameFoodCalories);
        foodFats = (TextView) view.findViewById(R.id.textSameFoodFats);
        foodCarbonates = (TextView) view.findViewById(R.id.textSameFoodCarbonates);
        foodProteins = (TextView) view.findViewById(R.id.textSameFoodProteins);
        foodGi = (TextView) view.findViewById(R.id.textSameFoodGi);
        foodWeight = (EditText) view.findViewById(R.id.editSameFoodWeight);
        SharedPreferences sPrefCategory = view.getContext().getSharedPreferences("categories", Context.MODE_PRIVATE);
        final String categoryName = sPrefCategory.getString("category", "");
        SharedPreferences sPrefFood = view.getContext().getSharedPreferences("foods", Context.MODE_PRIVATE);
        final String foodName = sPrefFood.getString("foods", "");
        realm = Realm.getDefaultInstance();
        FoodsR foodsR = DatabaseManager.getSameFood(realm, categoryName, foodName);
        CircleView circleView = new CircleView(view.getContext());
        circleView.setValues(foodsR);
        foodTitle.setText(foodsR.getFoodName().toString());
        foodCalories.setText(foodsR.getCalories() + "");
        foodFats.setText(foodsR.getFat() + "");
        foodCarbonates.setText(foodsR.getCarbonates() + "");
        foodProteins.setText(foodsR.getProteins() + "");
        foodGi.setText(foodsR.getGi() + "");
        frameLayout.addView(circleView);
        foodWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float calories = foodsR.getCalories();
                float fats = foodsR.getFat();
                float carbonates = foodsR.getCarbonates();
                float proteins = foodsR.getProteins();
                float gi = foodsR.getGi();
                if (s.toString().length() > 0) {
                    int weight = Integer.parseInt(s.toString());
                    foodCalories.setText((calories / 100) * weight + "");
                    foodFats.setText((fats / 100) * weight + "");
                    foodCarbonates.setText((carbonates / 100) * weight + "");
                    foodProteins.setText((proteins / 100) * weight + "");
                    foodGi.setText((gi / 100) * weight + "");
                } else {
                    foodCalories.setText(calories + "");
                    foodFats.setText(fats + "");
                    foodCarbonates.setText(carbonates + "");
                    foodProteins.setText(proteins + "");
                    foodGi.setText(gi + "");
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }
}
