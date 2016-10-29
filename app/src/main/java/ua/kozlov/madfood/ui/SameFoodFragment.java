package ua.kozlov.madfood.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.FoodsR;
import ua.kozlov.madfood.models.UserR;
import ua.kozlov.madfood.utils.CircleView;
import ua.kozlov.madfood.utils.DebugLogger;

public class SameFoodFragment extends Fragment {
    private FrameLayout mFrameLayout;
    private Realm mRealm;
    private TextView mFoodTitle;
    private TextView mFoodCalories;
    private TextView mFoodFats;
    private TextView mFoodCarbonates;
    private TextView mFoodProteins;
    private TextView mFoodGi;
    private EditText mFoodWeight;
    private Button mButtonAdd;
    private SharedPreferences mSPrefCategory;
    private SharedPreferences mSPrefFood;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.same_food_fragment, container, false);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.frammeSameFoodContainer);
        mFoodTitle = (TextView) view.findViewById(R.id.textSameFoodTitle);
        mFoodCalories = (TextView) view.findViewById(R.id.textSameFoodCalories);
        mFoodFats = (TextView) view.findViewById(R.id.textSameFoodFats);
        mFoodCarbonates = (TextView) view.findViewById(R.id.textSameFoodCarbonates);
        mFoodProteins = (TextView) view.findViewById(R.id.textSameFoodProteins);
        mFoodGi = (TextView) view.findViewById(R.id.textSameFoodGi);
        mFoodWeight = (EditText) view.findViewById(R.id.editSameFoodWeight);
        mButtonAdd = (Button) view.findViewById(R.id.buttonSameFoodAdd);
        mSPrefCategory = view.getContext().getSharedPreferences("categories", Context.MODE_PRIVATE);
        final String categoryName = mSPrefCategory.getString("category", "");
        mSPrefFood = view.getContext().getSharedPreferences("foods", Context.MODE_PRIVATE);
        final String foodName = mSPrefFood.getString("food", "");
        mRealm = Realm.getDefaultInstance();
        FoodsR foodsR = DatabaseManager.getSameFood(mRealm, categoryName, foodName);
        CircleView circleView = new CircleView(view.getContext());
        circleView.setValues(foodsR);
        mFoodTitle.setText(foodsR.getFoodName());
        mFoodCalories.setText(foodsR.getCalories() + "");
        mFoodFats.setText(foodsR.getFat() + "");
        mFoodCarbonates.setText(foodsR.getCarbonates() + "");
        mFoodProteins.setText(foodsR.getProteins() + "");
        mFoodGi.setText(foodsR.getGi() + "");
        mFrameLayout.addView(circleView);
        mFoodWeight.addTextChangedListener(new TextWatcher() {
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
                    mFoodCalories.setText((calories / 100) * weight + "");
                    mFoodFats.setText((fats / 100) * weight + "");
                    mFoodCarbonates.setText((carbonates / 100) * weight + "");
                    mFoodProteins.setText((proteins / 100) * weight + "");
                    mFoodGi.setText((gi / 100) * weight + "");
                } else {
                    mFoodCalories.setText(calories + "");
                    mFoodFats.setText(fats + "");
                    mFoodCarbonates.setText(carbonates + "");
                    mFoodProteins.setText(proteins + "");
                    mFoodGi.setText(gi + "");
                }
            }
        });
        mButtonAdd.setOnClickListener(v -> {
            String name = mFoodTitle.getText().toString();
            String weight = mFoodWeight.getText().toString();
            if (weight.equals("")){
                weight = getString(R.string.food_default_weight);
            }
            String calories = mFoodCalories.getText().toString();
            String fats = mFoodFats.getText().toString();
            String carbonates = mFoodCarbonates.getText().toString();
            String proteins = mFoodProteins.getText().toString();
            String gi = mFoodGi.getText().toString();
            long date = new Date().getTime();
            float progress = 0;
            RealmResults<UserR> userData = DatabaseManager.getUserData(mRealm);
            if (userData.size() == 0 || (userData.get(userData.size() - 1).getPlan() == 0)){
                replaceFragmentToProfile();
                Toast.makeText(view.getContext(), R.string.toast_profile_information, Toast.LENGTH_LONG).show();
            } else {
                int plan = userData.get(userData.size() - 1).getPlan();
                progress += Float.parseFloat(calories) * 100 / plan;
                DatabaseManager.saveToOneDayPlan(name, weight, calories, fats, carbonates,
                        proteins, gi, progress, date);
                DebugLogger.log(getString(R.string.log_data_saved));
                DebugLogger.log(getString(R.string.log_progress) + progress);
                mSPrefCategory.edit().putString("category", "").commit();
                mSPrefFood.edit().putString("food", "").commit();
                closeFragment();
            }
        });
        return view;
    }

    private void closeFragment(){
        PlanningFragment planningFragment = new PlanningFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, planningFragment);
        transaction.commit();
    }

    private void replaceFragmentToProfile(){
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, profileFragment);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSPrefCategory.edit().putString("category", "").commit();
        mSPrefFood.edit().putString("food", "").commit();
        mRealm.close();
        DebugLogger.log(getString(R.string.log_realm_closed));
    }
}
