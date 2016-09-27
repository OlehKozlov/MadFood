package ua.kozlov.madfood.ui.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import ua.kozlov.madfood.R;
import ua.kozlov.madfood.models.CategoryR;
import ua.kozlov.madfood.models.FoodsR;
import ua.kozlov.madfood.utils.CircleView;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder> {
    private List<CategoryR> mCategoryRList;
    private Context context;

    public CategoriesRecyclerAdapter(List<CategoryR> mCategoryRList) {
        this.mCategoryRList = mCategoryRList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_categories, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryR categoryR = mCategoryRList.get(position);
        holder.categoryTitle.setText(categoryR.getName().toString());
    }

    @Override
    public int getItemCount() {
        return mCategoryRList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            categoryTitle = (TextView) itemView.findViewById(R.id.textItemCategoriesTitle);
        }
    }
}
