package ua.kozlov.madfood;

import android.content.Context;

import java.util.ArrayList;

public class Groups implements GroupsInterface{
    private ArrayList<String> groupsList;
    private String[] groupsNames = {"Vegetables", "Baked products",
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
        return groupsList;
    }

    @Override
    public String getGroupName(String id, Context context) {
        setList();
        int groupId = Integer.parseInt(id) - 1;
        return groupsList.get(groupId).toString();
    }

    @Override
    public String getGroupId(String name, Context context) {
        setList();
        String id = "";
        for (int i = 0; i < groupsList.size(); i++){
            if (groupsList.get(i).toString().equals(name)){
                id = i + 1 +"";
                return id;
            }
        }
        return null;
    }

    public void setList(){
        groupsList = new ArrayList<>();
        for (int i = 0; i < groupsNames.length; i++){
            groupsList.add(groupsNames[i]);
        }
    }
}
