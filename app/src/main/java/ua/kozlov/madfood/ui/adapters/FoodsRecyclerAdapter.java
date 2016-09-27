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
    private Context context;

    public FoodsRecyclerAdapter(List<FoodsR> foodsRList) {
        this.mFoodsRList = foodsRList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_foods, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodsR foodsR = mFoodsRList.get(position);
        holder.foodTitle.setText(foodsR.getFoodName());
        holder.foodCalories.setText(foodsR.getCalories() + "");
        holder.foodFats.setText(foodsR.getFat() + "");
        holder.foodCarbonates.setText(foodsR.getCarbonates() + "");
        holder.foodProteins.setText(foodsR.getProteins() + "");
        holder.foodGi.setText(foodsR.getGi() + "");
        CircleView circleView = new CircleView(context);
        circleView.setValues(foodsR);
        holder.frameView.addView(circleView);
    }

    @Override
    public int getItemCount() {
        return mFoodsRList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout frameView;
        public TextView foodTitle;
        public TextView foodCalories;
        public TextView foodFats;
        public TextView foodCarbonates;
        public TextView foodProteins;
        public TextView foodGi;

        public ViewHolder(View itemView) {
            super(itemView);
            frameView = (FrameLayout) itemView.findViewById(R.id.frameItemFood);
            foodTitle = (TextView) itemView.findViewById(R.id.textItemFoodTitle);
            foodCalories = (TextView) itemView.findViewById(R.id.textItemFoodCalories);
            foodFats = (TextView) itemView.findViewById(R.id.textItemFoodFats);
            foodCarbonates = (TextView) itemView.findViewById(R.id.textItemFoodCarbonates);
            foodProteins = (TextView) itemView.findViewById(R.id.textItemFoodProteins);
            foodGi = (TextView) itemView.findViewById(R.id.textItemFoodGi);
        }
    }
}
