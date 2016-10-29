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
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.CategoryR;
import ua.kozlov.madfood.ui.adapters.CategoriesRecyclerAdapter;
import ua.kozlov.madfood.utils.DebugLogger;
import ua.kozlov.madfood.utils.RecyclerItemClickListener;

public class CategoriesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonAddCategory;
    private Button mButtonDialogOk;
    private Button mButtonDialogCancel;
    private EditText mEditDialogCategoryName;
    private Realm mRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_fragment, container, false);
        mButtonAddCategory = (FloatingActionButton) view.findViewById(R.id.buttonAddCategory);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerCategories);
        mRealm = Realm.getDefaultInstance();
        List<CategoryR> result = DatabaseManager.getAllCategories(mRealm);
        ArrayList<String> categoriesList = new ArrayList<>();
        for (CategoryR category : result) {
            categoriesList.add(category.getName());
        }
        CategoriesRecyclerAdapter adapter = new CategoriesRecyclerAdapter(result);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(),
                (view1, position) -> {
                    DebugLogger.log(categoriesList.get(position));
                    openCategory(view1.getContext(), categoriesList.get(position));
                }));
        final Dialog dialogAddCategory = new Dialog(view.getContext());
        dialogAddCategory.setContentView(R.layout.new_category_dialog);
        dialogAddCategory.setTitle(R.string.categories_name);
        mButtonDialogOk = (Button) dialogAddCategory.findViewById(R.id.buttonCategoryDialogOk);
        mButtonDialogCancel = (Button) dialogAddCategory.findViewById(R.id.buttonCategoryDialogCancel);
        mEditDialogCategoryName = (EditText) dialogAddCategory.findViewById(R.id.editCategoryDialog);
        mButtonAddCategory.setOnClickListener(v -> dialogAddCategory.show());
        mButtonDialogOk.setOnClickListener(v -> {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.saveNewCategory(mEditDialogCategoryName.getText().toString());
            dialogAddCategory.dismiss();
            openCategory(v.getContext(), mEditDialogCategoryName.getText().toString());
            dialogAddCategory.dismiss();
        });
        mButtonDialogCancel.setOnClickListener(v -> dialogAddCategory.dismiss());
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
        transaction.replace(R.id.mainContainer, foodsFragment);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRealm.close();
        DebugLogger.log(getString(R.string.log_realm_closed));
    }
}