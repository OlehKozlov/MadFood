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
import ua.kozlov.madfood.models.FoodsR;
import ua.kozlov.madfood.utils.CircleView;

public class FoodsRecyclerAdapter extends RecyclerView.Adapter<FoodsRecyclerAdapter.ViewHolder> {
    private List<FoodsR> mFoodsRList;
    private Context mContext;

    public FoodsRecyclerAdapter(List<FoodsR> foodsRList) {
        this.mFoodsRList = foodsRList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_foods, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodsR foodsR = mFoodsRList.get(position);
        holder.mFoodTitle.setText(foodsR.getFoodName());
        holder.mFoodCalories.setText(foodsR.getCalories() + "");
        holder.mFoodFats.setText(foodsR.getFat() + "");
        holder.mFoodCarbonates.setText(foodsR.getCarbonates() + "");
        holder.mFoodProteins.setText(foodsR.getProteins() + "");
        holder.mFoodGi.setText(foodsR.getGi() + "");
        CircleView circleView = new CircleView(mContext);
        circleView.setValues(foodsR);
        holder.mFrameView.addView(circleView);
    }

    @Override
    public int getItemCount() {
        return mFoodsRList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout mFrameView;
        public TextView mFoodTitle;
        public TextView mFoodCalories;
        public TextView mFoodFats;
        public TextView mFoodCarbonates;
        public TextView mFoodProteins;
        public TextView mFoodGi;

        public ViewHolder(View itemView) {
            super(itemView);
            mFrameView = (FrameLayout) itemView.findViewById(R.id.frameItemFood);
            mFoodTitle = (TextView) itemView.findViewById(R.id.textItemFoodTitle);
            mFoodCalories = (TextView) itemView.findViewById(R.id.textItemFoodCalories);
            mFoodFats = (TextView) itemView.findViewById(R.id.textItemFoodFats);
            mFoodCarbonates = (TextView) itemView.findViewById(R.id.textItemFoodCarbonates);
            mFoodProteins = (TextView) itemView.findViewById(R.id.textItemFoodProteins);
            mFoodGi = (TextView) itemView.findViewById(R.id.textItemFoodGi);
        }
    }
}
