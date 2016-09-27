package ua.kozlov.madfood.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ua.kozlov.madfood.R;

public class FoodsArrayAdapter extends ArrayAdapter<String>{
    private final Context context;
    private final ArrayList<String> values;

    public FoodsArrayAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.item_foods, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View foodsView = inflater.inflate(R.layout.item_foods, parent, false);
        TextView textView = (TextView) foodsView.findViewById(R.id.textItemFoodTitle);
        textView.setText(values.get(position).toString());
        return foodsView;

    }
}
