package ua.kozlov.madfood;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class UserFragment extends Fragment{
    private Button mButtonOk;
    private Button mButtonCancel;
    private EditText mUserName;
    private EditText mUserWeight;
    final String TAG = "mylog";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.user_fragment, container, false);
        mUserName = (EditText) view.findViewById(R.id.edit_user_name);
        mUserWeight = (EditText) view.findViewById(R.id.edit_user_weight);
        mButtonOk = (Button) view.findViewById(R.id.button_user_fragment_ok);
        mButtonCancel = (Button) view.findViewById(R.id.button_user_fragment_cancel);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(view.getContext());
            }
        });
        getData(view.getContext());
        return view;
    }

    private void getData(Context context) {
        User user = new User();
        String name = user.getUserName(context);
        mUserName.setText(name);
        String weight = user.getUserCurrentWeight(context) + "";
        mUserWeight.setText(weight);
    }

    private void setData(Context context) {
        User user = new User();
        Log.d(TAG, "setData: " + mUserName.getText().toString());
        user.setUserName(mUserName.getText().toString(), context);
        user.setUserWeight(Float.parseFloat(mUserWeight.getText().toString()), context);
        getData(context);
    }
}
