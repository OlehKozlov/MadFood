package ua.kozlov.madfood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class FoodsFragment extends Fragment{
    private Foods mFoods;
    private ListView mListView;
    private SharedPreferences mSPref;
    private String mGroupName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foods_fragment, container, false);
        mFoods = new Foods();
        mListView = (ListView) view.findViewById(R.id.listview_foods);
        mSPref = view.getContext().getSharedPreferences("FoodGroup", Context.MODE_PRIVATE);
        mGroupName = mSPref.getString("group", "");
        final ArrayList<String> foodsList = mFoods.getFoodsList(mGroupName, view.getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.food_list_item, foodsList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("mylog", foodsList.get(position).toString());
                mSPref = view.getContext().getSharedPreferences("samefood", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSPref.edit();
                editor.putInt("samefood", position);
                editor.putString("samefoodName", foodsList.get(position).toString());
                editor.commit();
                SameFoodFragment sameFoodFragment = new SameFoodFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainContainer, sameFoodFragment).addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}
