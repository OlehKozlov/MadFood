package ua.kozlov.madfood.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.CategoryR;
import ua.kozlov.madfood.ui.adapters.CategoriesRecyclerAdapter;
import ua.kozlov.madfood.ui.adapters.FoodsArrayAdapter;
import ua.kozlov.madfood.ui.adapters.FoodsRecyclerAdapter;
import ua.kozlov.madfood.utils.DebugLogger;
import ua.kozlov.madfood.utils.RecyclerItemClickListener;

public class CategoriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddCategory;
    private Button buttonDialogOk;
    private Button buttonDialogCancel;
    private EditText editDialogCategoryName;
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_fragment, container, false);
        buttonAddCategory = (FloatingActionButton) view.findViewById(R.id.buttonAddCategory);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerCategories);
        realm = Realm.getDefaultInstance();
        List<CategoryR> result = DatabaseManager.getAllCategories(realm);
        ArrayList<String> categoriesList = new ArrayList<>();
        for (CategoryR category : result) {
            categoriesList.add(category.getName().toString());
        }

        CategoriesRecyclerAdapter adapter = new CategoriesRecyclerAdapter(result);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(),
                new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        DebugLogger.log(categoriesList.get(position).toString());
                        openCategory(view.getContext(), categoriesList.get(position).toString());
                    }
                }));

        final Dialog dialogAddCategory = new Dialog(view.getContext());
        dialogAddCategory.setContentView(R.layout.new_category_dialog);
        dialogAddCategory.setTitle(R.string.categories_name);
        buttonDialogOk = (Button) dialogAddCategory.findViewById(R.id.buttonCategoryDialogOk);
        buttonDialogCancel = (Button) dialogAddCategory.findViewById(R.id.buttonCategoryDialogCancel);
        editDialogCategoryName = (EditText) dialogAddCategory.findViewById(R.id.editCategoryDialog);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddCategory.show();
            }
        });
        buttonDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.saveNewCategory(editDialogCategoryName.getText().toString());
                dialogAddCategory.dismiss();
                openCategory(v.getContext(), editDialogCategoryName.getText().toString());
                dialogAddCategory.dismiss();
            }
        });
        buttonDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddCategory.dismiss();
            }
        });
        return view;
    }

    private void openCategory(Context context, String category){
        SharedPreferences sPref =
                context.getSharedPreferences("categories", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("category", category);
        editor.commit();
        FoodsFragment foodsFragment = new FoodsFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainContainer, foodsFragment).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }
}