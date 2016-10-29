package ua.kozlov.madfood.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.PlanDataR;
import ua.kozlov.madfood.ui.adapters.CalendarPlanRecyclerAdapter;
import ua.kozlov.madfood.utils.DebugLogger;
import ua.kozlov.madfood.utils.RecyclerItemClickListener;

public class CalendarPlanningFragment extends Fragment {
    private RecyclerView mRecyclerPlan;
    private Realm mRealm;
    private final String mSharedDate = "date";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_planning_fragment, container, false);
        SharedPreferences sPrefCategorie = view.getContext().getSharedPreferences(mSharedDate, Context.MODE_PRIVATE);
        final long date = sPrefCategorie.getLong(mSharedDate, 0);
        mRecyclerPlan = (RecyclerView) view.findViewById(R.id.recyclerCalendarPlanFoods);
        mRealm = Realm.getDefaultInstance();
        List<PlanDataR> result = DatabaseManager.getPlanData(mRealm, date);
        ArrayList<String> foodsList = new ArrayList<>();
        for (PlanDataR food : result) {
            foodsList.add(food.getFoodName());
        }
        CalendarPlanRecyclerAdapter adapter = new CalendarPlanRecyclerAdapter(result);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerPlan.setLayoutManager(layoutManager);
        mRecyclerPlan.setItemAnimator(new DefaultItemAnimator());
        mRecyclerPlan.setAdapter(adapter);
        mRecyclerPlan.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(),
                (view1, position) -> DebugLogger.log(foodsList.get(position))));
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mRealm.close();
        DebugLogger.log(getString(R.string.log_realm_closed));
    }
}
