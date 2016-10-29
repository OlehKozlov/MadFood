package ua.kozlov.madfood.ui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.UserR;
import ua.kozlov.madfood.utils.DebugLogger;

public class ProfileFragment extends Fragment {
    private Realm mRealm;
    private Button mButtonSave;
    private EditText mEditUserName;
    private EditText mEditUserWeight;
    private EditText mEditUserHeight;
    private EditText mEditUserPlan;
    private final String PERSONAL_INFORMATION = "Personal";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        mRealm = Realm.getDefaultInstance();
        mButtonSave = (Button) view.findViewById(R.id.buttonProfileSave);
        mEditUserName = (EditText) view.findViewById(R.id.editUserName);
        mEditUserWeight = (EditText) view.findViewById(R.id.editUserWeight);
        mEditUserHeight = (EditText) view.findViewById(R.id.editUserHeight);
        mEditUserPlan = (EditText) view.findViewById(R.id.editUserPlan);
        mEditUserName.requestFocus();
        RealmResults<UserR> user = DatabaseManager.getUserData(mRealm);
        if (user.size() > 0) {
            String name = user.get(user.size() - 1).getName();
            float weight = user.get(user.size() - 1).getWeight();
            float height = user.get(user.size() - 1).getHeight();
            int plan = user.get(user.size() - 1).getPlan();
            mEditUserName.setText(name);
            mEditUserWeight.setText(weight + "");
            mEditUserHeight.setText(height + "");
            mEditUserPlan.setText(plan + "");
        }
        mButtonSave.setOnClickListener(v -> {
            String userName = mEditUserName.getText().toString();
            String userWeight = mEditUserWeight.getText().toString();
            String userHeight = mEditUserHeight.getText().toString();
            String userPlan = mEditUserPlan.getText().toString();
            String date = new Date().getTime() + "";
            if (!userWeight.isEmpty() && !userPlan.isEmpty()) {
                if (userHeight.isEmpty()) {
                    userHeight = "0";
                }
                DatabaseManager.saveUserData(userName, Float.parseFloat(userWeight),
                        Float.parseFloat(userHeight), Integer.parseInt(userPlan), date);
                SharedPreferences personalInfo = getActivity().getSharedPreferences(PERSONAL_INFORMATION, 0);
                personalInfo.edit().putBoolean(PERSONAL_INFORMATION, false).commit();
                DebugLogger.log(userName);
                DebugLogger.log(userWeight);
                DebugLogger.log(userHeight + "");
                DebugLogger.log(userPlan + "");
                DebugLogger.log(date);
                DebugLogger.log(getString(R.string.log_data_saved));
                Toast.makeText(view.getContext(), getString(R.string.log_data_saved), Toast.LENGTH_SHORT).show();
                SharedPreferences settings = view.getContext().getSharedPreferences("Launch", 0);
                settings.edit().putBoolean("personal data", false).commit();
                closeFragment();
            } else if (userWeight.isEmpty()) {
                mEditUserWeight.setBackgroundColor(Color.RED);
                mEditUserWeight.setHint(R.string.hint_user_weight);
            } else if (userPlan.isEmpty()) {
                mEditUserPlan.setBackgroundColor(Color.RED);
                mEditUserPlan.setHint(R.string.hint_user_calories);
            }
        });
        return view;
    }

    private void closeFragment() {
        PlanningFragment planningFragment = new PlanningFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, planningFragment);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        mRealm.close();
        DebugLogger.log(getString(R.string.log_realm_closed));
        super.onDestroyView();
    }
}