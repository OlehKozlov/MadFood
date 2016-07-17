package ua.kozlov.madfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class OnewDayPlanFragment extends Fragment {
    private ListView mListView;
    private Button mAddButton;
    private Button mDeleteButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view  = inflater.inflate(R.layout.one_day_plan_fragment, container, false);
        mListView = (ListView) view.findViewById(R.id.list_view_one_day_plan);
        mAddButton = (Button) view.findViewById(R.id.button_one_day_plan_add);
        mDeleteButton = (Button) view.findViewById(R.id.button_one_day_plan_delete);
        OneDayPlan oneDayPlan = new OneDayPlan();
        ArrayList<String> foodNamesList = oneDayPlan.getOneDayPlanNamesList(view.getContext());
        ArrayList<String> foodWeightsList = oneDayPlan.getOneDayPlanWeightsList(view.getContext());
        OneDayPlanFragmentArrayAdapter adapter = new OneDayPlanFragmentArrayAdapter(view.getContext(),
                foodNamesList, foodWeightsList);
        mListView.setAdapter(adapter);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupsFragment fragment = new GroupsFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainContainer, fragment).addToBackStack(null);
                transaction.commit();
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneDayPlan oneDayPlan = new OneDayPlan();
                oneDayPlan.removeFoodFromPlan(view.getContext());
                OnewDayPlanFragment fragment = new OnewDayPlanFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainContainer, fragment).addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}
