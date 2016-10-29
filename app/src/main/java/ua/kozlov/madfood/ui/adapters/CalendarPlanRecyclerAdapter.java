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
import ua.kozlov.madfood.models.PlanDataR;
import ua.kozlov.madfood.utils.CircleView;

public class CalendarPlanRecyclerAdapter extends RecyclerView.Adapter<CalendarPlanRecyclerAdapter.ViewHolder>{
    private List<PlanDataR> mFoodsList;
    private Context mContext;

    public CalendarPlanRecyclerAdapter(List<PlanDataR> mFoodsList) {
        this.mFoodsList = mFoodsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_foods_plan, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlanDataR foods = mFoodsList.get(position);
        holder.mFoodTitle.setText(foods.getFoodName());
        holder.mFoodWeight.setText(foods.getFoodWeight());
        holder.mFoodCalories.setText(foods.getCalories() + "");
        holder.mFoodFats.setText(foods.getFat() + "");
        holder.mFoodCarbonates.setText(foods.getCarbonates() + "");
        holder.mFoodProteins.setText(foods.getProteins() + "");
        holder.mFoodGi.setText(foods.getGi() + "");
        CircleView circleView = new CircleView(mContext);
        circleView.setValues(foods);
        holder.mFrameView.addView(circleView);
    }

    @Override
    public int getItemCount() {
        return mFoodsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout mFrameView;
        public TextView mFoodWeight;
        public TextView mFoodTitle;
        public TextView mFoodCalories;
        public TextView mFoodFats;
        public TextView mFoodCarbonates;
        public TextView mFoodProteins;
        public TextView mFoodGi;

        public ViewHolder(View itemView) {
            super(itemView);
            mFrameView = (FrameLayout) itemView.findViewById(R.id.frameItemPlanFood);
            mFoodTitle = (TextView) itemView.findViewById(R.id.textItemPlanFoodTitle);
            mFoodWeight = (TextView) itemView.findViewById(R.id.textItemPlanWeight);
            mFoodCalories = (TextView) itemView.findViewById(R.id.textItemPlanFoodCalories);
            mFoodFats = (TextView) itemView.findViewById(R.id.textItemPlanFoodFats);
            mFoodCarbonates = (TextView) itemView.findViewById(R.id.textItemPlanFoodCarbonates);
            mFoodProteins = (TextView) itemView.findViewById(R.id.textItemPlanFoodProteins);
            mFoodGi = (TextView) itemView.findViewById(R.id.textItemPlanFoodGi);
        }
    }
}
