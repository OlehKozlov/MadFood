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

public class GroupsFragment extends Fragment {
    private Groups mGroups;
    private ListView mListView;
    private SharedPreferences mSPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.groups_fragment, container, false);
        mGroups = new Groups();
        mListView = (ListView) view.findViewById(R.id.list_view_groups);
        final ArrayList<String> groupList = mGroups.getGroupsList(view.getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.group_list_item, groupList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSPref = view.getContext().getSharedPreferences("FoodGroup", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSPref.edit();
                editor.putString("group", groupList.get(position).toString());
                editor.commit();
                Log.d("mylog", groupList.get(position).toString());
                FoodsFragment foodsFragment = new FoodsFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainContainer, foodsFragment).addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}
