package ua.kozlov.madfood.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.UserR;
import ua.kozlov.madfood.utils.DebugLogger;

public class ProfileFragment extends Fragment {
    private Realm mRealm;
    Button buttonSave;
    EditText mEditUserName;
    EditText mEditUserWeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        mRealm = Realm.getDefaultInstance();
        buttonSave = (Button)view.findViewById(R.id.buttonProfileSave);
        mEditUserName = (EditText) view.findViewById(R.id.editUserName);
        mEditUserWeight = (EditText) view.findViewById(R.id.editUserWeight);
        RealmResults<UserR> user = DatabaseManager.getUserData();
        if (user.size()>1) {
            String name = user.get(user.size() - 1).getName();
            float weight = user.get(user.size() - 1).getWeight();
            mEditUserName.setText(name);
            mEditUserWeight.setText(weight + "");
        }
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mEditUserName.getText().toString();
                String userWeight = mEditUserWeight.getText().toString();
                String date = new Date().getTime() + "";
                if(!userName.isEmpty() && !userWeight.isEmpty()){
                    DatabaseManager.saveUserData(userName, Float.valueOf(userWeight), date);
                    DebugLogger.log(userName);
                    DebugLogger.log(userWeight);
                    DebugLogger.log(date);
                    DebugLogger.log("data saved");
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        mRealm.close();
        super.onDestroyView();
    }
}