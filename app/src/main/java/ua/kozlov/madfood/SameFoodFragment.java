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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

public class SameFoodFragment extends Fragment {
    private TextView mTitleText;
    private TextView mCaloriesText;
    private TextView mFatsText;
    private TextView mCarbonatesText;
    private TextView mProteinsText;
    private TextView mGiText;
    private EditText mWeightEditText;
    private ArrayList<String> mFoodsList = new ArrayList<>();
    private ArrayList<String> mStaticFoodsList = new ArrayList<>();
    private Button mButtonAdd;
    private Button mButtonCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.same_food_fragment, container, false);
        mTitleText = (TextView) view.findViewById(R.id.text_same_food_title);
        mCaloriesText = (TextView) view.findViewById(R.id.text_same_food_calories);
        mFatsText = (TextView) view.findViewById(R.id.text_same_food_fat);
        mCarbonatesText = (TextView) view.findViewById(R.id.text_same_food_carbonates);
        mProteinsText = (TextView) view.findViewById(R.id.text_same_food_proteins);
        mGiText = (TextView) view.findViewById(R.id.text_same_food_gi);
        mWeightEditText = (EditText) view.findViewById(R.id.edit_same_food_weight);
        mButtonAdd = (Button) view.findViewById(R.id.button_same_food_add_to_plan);
        mButtonCancel = (Button) view.findViewById(R.id.button_same_food_cancel);
        mWeightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mWeightEditText.getText().toString().length() > 0) {
                    setParametersData(Integer.parseInt(mWeightEditText.getText().toString()));
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
        mStaticFoodsList = foods.getFoodParameters(groupName, foodPosition, view.getContext());
        mFoodsList = mStaticFoodsList;
        setParametersData(Integer.parseInt(mWeightEditText.getText().toString()));
        mTitleText.setText(foodName);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneDayPlan oneDayPlan = new OneDayPlan();
                oneDayPlan.setFoodToPlan(view.getContext(), foodName, mWeightEditText.getText().toString(), mFoodsList);
                OnewDayPlanFragment onewDayPlanFragment = new OnewDayPlanFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainContainer, onewDayPlanFragment).addToBackStack(null);
                transaction.commit();
            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
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

    private void setParametersData(final int weight) {
        float calories = (Float.parseFloat(mStaticFoodsList.get(0).toString())) / 100 * weight;
        float fats = (Float.parseFloat(mStaticFoodsList.get(1).toString())) / 100 * weight;
        float carbonates = (Float.parseFloat(mStaticFoodsList.get(2).toString())) / 100 * weight;
        float proteins = (Float.parseFloat(mStaticFoodsList.get(3).toString())) / 100 * weight;
        float gi = (Float.parseFloat(mStaticFoodsList.get(4).toString())) / 100 * weight;
        mCaloriesText.setText(calories + "");
        mFatsText.setText(fats + "");
        mCarbonatesText.setText(carbonates + "");
        mProteinsText.setText(proteins + "");
        mGiText.setText(gi + "");
        mFoodsList = new ArrayList<>();
        mFoodsList.add(calories + "");
        mFoodsList.add(fats + "");
        mFoodsList.add(carbonates + "");
        mFoodsList.add(proteins + "");
        mFoodsList.add(gi + "");
    }
}
