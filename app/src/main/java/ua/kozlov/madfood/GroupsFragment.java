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
    Groups groups;
    ListView listView;
    SharedPreferences sPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.groups_fragment, container, false);
        groups = new Groups();
        listView = (ListView) view.findViewById(R.id.groupsListView);
        final ArrayList<String> groupList = groups.getGroupsList(view.getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.group_list_item, groupList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sPref = view.getContext().getSharedPreferences("FoodGroup", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
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
