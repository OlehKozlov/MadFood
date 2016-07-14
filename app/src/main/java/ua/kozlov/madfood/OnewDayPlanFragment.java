package ua.kozlov.madfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class OnewDayPlanFragment extends Fragment {
    ListView listView;
    Button addButton;
    Button deleteButton;
    private int foodId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view  = inflater.inflate(R.layout.one_day_plan_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.oneDayPlanListView);
        addButton = (Button) view.findViewById(R.id.oneDayPlanAddButton);
        deleteButton = (Button) view.findViewById(R.id.oneDayPlanDeleteButton);
        OneDayPlan oneDayPlan = new OneDayPlan();
        ArrayList<String> foodNamesList = oneDayPlan.getOneDayPlanNamesList(view.getContext());
        ArrayList<String> foodWeightsList = oneDayPlan.getOneDayPlanWeightsList(view.getContext());
        OneDayPlanFragmentArrayAdapter adapter = new OneDayPlanFragmentArrayAdapter(view.getContext(),
                foodNamesList, foodWeightsList);
        listView.setAdapter(adapter);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.food_list_item, foodsList);
        //listView.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupsFragment fragment = new GroupsFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainContainer, fragment).addToBackStack(null);
                transaction.commit();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
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
