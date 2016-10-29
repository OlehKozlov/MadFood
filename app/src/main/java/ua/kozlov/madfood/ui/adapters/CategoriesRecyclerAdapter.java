package ua.kozlov.madfood.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.kozlov.madfood.R;
import ua.kozlov.madfood.models.CategoryR;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder> {
    private List<CategoryR> mCategoryRList;

    public CategoriesRecyclerAdapter(List<CategoryR> mCategoryRList) {
        this.mCategoryRList = mCategoryRList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_categories, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryR categoryR = mCategoryRList.get(position);
        holder.mCategoryTitle.setText(categoryR.getName().toString());
    }

    @Override
    public int getItemCount() {
        return mCategoryRList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mCategoryTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mCategoryTitle = (TextView) itemView.findViewById(R.id.textItemCategoriesTitle);
        }
    }
}
