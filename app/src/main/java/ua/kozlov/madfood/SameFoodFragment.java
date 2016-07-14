package ua.kozlov.madfood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SameFoodFragment extends Fragment {
    private TextView titleText;
    private TextView caloriesText;
    private TextView fatsText;
    private TextView carbonatesText;
    private TextView proteinsText;
    private TextView giText;
    private EditText weightEditText;
    private ArrayList<String> foodsList = new ArrayList<>();
    private ArrayList<String> staticFoodsList = new ArrayList<>();
    private Button buttonAdd;
    private Button buttonCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.same_food_fragment, container, false);
        titleText = (TextView) view.findViewById(R.id.sameFoodTitle);
        caloriesText = (TextView) view.findViewById(R.id.sameFoodCalories);
        fatsText = (TextView) view.findViewById(R.id.sameFoodFat);
        carbonatesText = (TextView) view.findViewById(R.id.sameFoodCarbonates);
        proteinsText = (TextView) view.findViewById(R.id.sameFoodProteins);
        giText = (TextView) view.findViewById(R.id.sameFoodGI);
        weightEditText = (EditText) view.findViewById(R.id.sameFoodWeightEditText);
        buttonAdd = (Button) view.findViewById(R.id.sameFoodButtonAddToPlan);
        buttonCancel = (Button) view.findViewById(R.id.sameFoodButtonCancel);
        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (weightEditText.getText().toString().length() > 0) {
                    setParametersData(Integer.parseInt(weightEditText.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Foods foods = new Foods();
        SharedPreferences sPref = view.getContext().getSharedPreferences("samefood", Context.MODE_PRIVATE);
        int foodPosition = sPref.getInt("samefood", 0);
        final String foodName = sPref.getString("samefoodName", "");
        sPref = view.getContext().getSharedPreferences("FoodGroup", Context.MODE_PRIVATE);
        String groupName = sPref.getString("group", "");
        staticFoodsList = foods.getFoodParameters(groupName, foodPosition, view.getContext());
        foodsList = staticFoodsList;
        setParametersData(Integer.parseInt(weightEditText.getText().toString()));
        titleText.setText(foodName);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneDayPlan oneDayPlan = new OneDayPlan();
                oneDayPlan.setFoodToPlan(view.getContext(), foodName, weightEditText.getText().toString(), foodsList);

                OnewDayPlanFragment onewDayPlanFragment = new OnewDayPlanFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainContainer, onewDayPlanFragment).addToBackStack(null);
                transaction.commit();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnewDayPlanFragment onewDayPlanFragment = new OnewDayPlanFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainContainer, onewDayPlanFragment).addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private void setParametersData(int weight) {
        float calories = (Float.parseFloat(staticFoodsList.get(0).toString())) / 100 * weight;
        float fats = (Float.parseFloat(staticFoodsList.get(1).toString())) / 100 * weight;
        float carbonates = (Float.parseFloat(staticFoodsList.get(2).toString())) / 100 * weight;
        float proteins = (Float.parseFloat(staticFoodsList.get(3).toString())) / 100 * weight;
        float gi = (Float.parseFloat(staticFoodsList.get(4).toString())) / 100 * weight;
        caloriesText.setText(calories + "");
        fatsText.setText(fats + "");
        carbonatesText.setText(carbonates + "");
        proteinsText.setText(proteins + "");
        giText.setText(gi + "");
        foodsList = new ArrayList<>();
        foodsList.add(calories + "");
        foodsList.add(fats + "");
        foodsList.add(carbonates + "");
        foodsList.add(proteins + "");
        foodsList.add(gi + "");
    }


}
