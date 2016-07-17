package ua.kozlov.madfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class OneDayPlanFragmentArrayAdapter extends ArrayAdapter<String>{
    private final Context mContext;
    private final ArrayList<String> mFoodNames;
    private final ArrayList<String> mFoodWeights;

    public OneDayPlanFragmentArrayAdapter(Context context, ArrayList<String> foodNames, ArrayList<String> foodWeights) {
        super(context, R.layout.one_day_plan_view, foodNames);
        this.mContext = context;
        this.mFoodNames = foodNames;
        this.mFoodWeights = foodWeights;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.one_day_plan_view, parent, false);
        Button button = (Button) view.findViewById(R.id.button_one_day_plan);
        button.setText(mFoodNames.get(position).toString());
        TextView textView = (TextView) view.findViewById(R.id.text_one_day_plan);
        textView.setText(mFoodWeights.get(position).toString());
        return view;
    }
}
