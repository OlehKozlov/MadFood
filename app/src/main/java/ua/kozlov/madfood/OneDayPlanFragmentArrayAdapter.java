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
    private final Context context;
    private final ArrayList<String> foodNames;
    private final ArrayList<String> foodWeights;


    public OneDayPlanFragmentArrayAdapter(Context context, ArrayList<String> foodNames, ArrayList<String> foodWeights) {
        super(context, R.layout.one_day_plan_view, foodNames);
        this.context = context;
        this.foodNames = foodNames;
        this.foodWeights = foodWeights;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.one_day_plan_view, parent, false);
        Button button = (Button) view.findViewById(R.id.oneDayPlanListButoon);
        button.setText(foodNames.get(position).toString());
        TextView textView = (TextView) view.findViewById(R.id.oneDayPlanListText);
        textView.setText(foodWeights.get(position).toString());
        return view;
    }
}
