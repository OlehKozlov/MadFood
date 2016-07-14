package ua.kozlov.madfood;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class UserFragment extends Fragment{
    private Button buttonOk;
    private Button buttonCancel;
    private EditText userName;
    private EditText userWeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.user_fragment, container, false);
        userName = (EditText) view.findViewById(R.id.userNameEditText);
        userWeight = (EditText) view.findViewById(R.id.userWeightEditText);
        buttonOk = (Button) view.findViewById(R.id.userFragmentButtonOK);
        buttonCancel = (Button) view.findViewById(R.id.userFragmentButtonCansel);
        getData(view.getContext());
        return view;
    }

    private void getData(Context context) {
        User user = new User();
        String name = user.getUserName(context);
        userName.setText(name);
        String weight = user.getUserCurrentWeight(context) + "";
        userWeight.setText(weight);
    }

    protected void setData(Context context) {
        User user = new User();
        user.setUserName(userName.getText().toString(), context);
        user.setUserWeight(Float.parseFloat(userWeight.getText().toString()), context);
        getData(context);
    }
}
