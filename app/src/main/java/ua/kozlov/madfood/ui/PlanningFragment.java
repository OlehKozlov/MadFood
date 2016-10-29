package ua.kozlov.madfood.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import ua.kozlov.madfood.models.OneDayPlanR;
import ua.kozlov.madfood.ui.adapters.PlanRecyclerAdapter;
import ua.kozlov.madfood.utils.DebugLogger;
import ua.kozlov.madfood.utils.RecyclerItemClickListener;

public class PlanningFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonAddFood;
    private Realm mRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.planning_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerPlanFoods);
        mButtonAddFood = (FloatingActionButton) view.findViewById(R.id.buttonPlanAddFood);
        mRealm = Realm.getDefaultInstance();
        List<OneDayPlanR> result = DatabaseManager.getOneDayPlanData(mRealm);
        ArrayList<String> foodsList = new ArrayList<>();
        for (OneDayPlanR food : result) {
            foodsList.add(food.getFoodName());
        }
        PlanRecyclerAdapter adapter = new PlanRecyclerAdapter(result);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(),
                (view1, position) -> DebugLogger.log(foodsList.get(position))));
        mButtonAddFood.setOnClickListener(v -> {
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.mainContainer, categoriesFragment);
            transaction.commit();
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRealm.close();
        DebugLogger.log(getString(R.string.log_realm_closed));
    }
}