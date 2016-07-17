package ua.kozlov.madfood;

import android.content.Context;
import java.util.ArrayList;

public class Groups implements GroupsInterface {
    private ArrayList<String> mGroupsList;
    private String[] mGroupsNames = {"Vegetables", "Baked products",
            "Beverages nonalcoholic", "Bread flour products", "Cannedfish, seafoods",
            "Cannedfruits, vegetables, mushrooms", "Cannedeat products",
            "Cerealgrains, cereals, flakes", "Cerealgrains cooked", "Fastfoods",
            "Fish, seafoods", "Fruits", "Fruitsdried", "Icecream",
            "Jams", "Legumes", "Meels entrees sidedishes", "Meats, byproducts",
            "Mushrooms", "Nut and seed products", "Oils, fats",
            "Poultry products", "Sausages", "Snacks", "Soups gravies",
            "Soy products", "Sushi", "Sweets desserts"};

    @Override
    public ArrayList<String> getGroupsList(Context context) {
        setList();
        return mGroupsList;
    }

    @Override
    public String getGroupName(final String id, Context context) {
        setList();
        int groupId = Integer.parseInt(id) - 1;
        return mGroupsList.get(groupId).toString();
    }

    @Override
    public String getGroupId(final String name, Context context) {
        setList();
        String id = "";
        for (int i = 0; i < mGroupsList.size(); i++) {
            if (mGroupsList.get(i).toString().equals(name)) {
                id = i + 1 + "";
                return id;
            }
        }
        return null;
    }

    public void setList() {
        mGroupsList = new ArrayList<>();
        for (int i = 0; i < mGroupsNames.length; i++) {
            mGroupsList.add(mGroupsNames[i]);
        }
    }
}
