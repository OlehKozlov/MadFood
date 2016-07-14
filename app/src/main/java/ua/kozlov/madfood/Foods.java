package ua.kozlov.madfood;


import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class Foods implements FoodsInterface {
    @Override
    public ArrayList<String> getFoodsList(String groupName, Context context) {
        XmlPullParser parser = getParser(groupName, context);
        ArrayList<String> foodsList = setFoodNamesToList(parser);
        return foodsList;
    }

    @Override
    public String getFoodName(String groupID, String id, Context context) {
        return null;
    }

    @Override
    public String getFoodId(String groupID, String name, Context context) {
        return null;
    }

    @Override
    public ArrayList<String> getFoodParameters(String groupName, int position, Context context) {
        XmlPullParser parser = getParserFacts(groupName, context);
        ArrayList<String> foodsList = setFoodParametersToList(parser, position);
        return foodsList;
    }

    private ArrayList<String> setFoodNamesToList(XmlPullParser parser) {
        ArrayList<String> list = new ArrayList<>();
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("Food")) {
                    final String foodName = parser.getAttributeValue(1);
                    list.add(foodName);
                }
                parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private ArrayList<String> setFoodParametersToList(XmlPullParser parser, int position) {
        ArrayList<String> list = new ArrayList<>();
        int count = 0;
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("Food")) {
                    if (count == position){
                        list.add(parser.getAttributeValue(1));
                        list.add(parser.getAttributeValue(2));
                        list.add(parser.getAttributeValue(3));
                        list.add(parser.getAttributeValue(4));
                        list.add(parser.getAttributeValue(5));
                        break;
                    } else {
                        count++;
                    }
                }
                parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private XmlPullParser getParser(String group, Context context) {
        XmlPullParser parser = null;
        if (group.equals("Vegetables")) {
            parser = context.getResources().getXml(R.xml.vegetables);
            return parser;
        } else if (group.equals("Baked products")) {
            parser = context.getResources().getXml(R.xml.baked_products);
            return parser;
        } else if (group.equals("Beverages nonalcoholic")) {
            parser = context.getResources().getXml(R.xml.beverages_nonalcoholic);
            return parser;
        } else if (group.equals("Bread flour products")) {
            parser = context.getResources().getXml(R.xml.bread_flour_products);
            return parser;
        } else if (group.equals("Cannedfish, seafoods")) {
            parser = context.getResources().getXml(R.xml.cannedfish_seafoods);
            return parser;
        } else if (group.equals("Cannedfruits, vegetables, mushrooms")) {
            parser = context.getResources().getXml(R.xml.cannedfruits_vegetables_mushrooms);
            return parser;
        } else if (group.equals("Cannedeat products")) {
            parser = context.getResources().getXml(R.xml.cannedeat_products);
            return parser;
        } else if (group.equals("Cerealgrains, cereals, flakes")) {
            parser = context.getResources().getXml(R.xml.cerealgrains_cereals_flakes);
            return parser;
        } else if (group.equals("Cerealgrains cooked")) {
            parser = context.getResources().getXml(R.xml.cereal_grains_cooked);
            return parser;
        } else if (group.equals("Fastfoods")) {
            parser = context.getResources().getXml(R.xml.fastfoods);
            return parser;
        } else if (group.equals("Fish, seafoods")) {
            parser = context.getResources().getXml(R.xml.fish_seafoods);
            return parser;
        } else if (group.equals("Fruits")) {
            parser = context.getResources().getXml(R.xml.fruits);
            return parser;
        } else if (group.equals("Fruitsdried")) {
            parser = context.getResources().getXml(R.xml.fruitsdried);
            return parser;
        } else if (group.equals("Icecream")) {
            parser = context.getResources().getXml(R.xml.icecream);
            return parser;
        } else if (group.equals("Jams")) {
            parser = context.getResources().getXml(R.xml.jams);
            return parser;
        } else if (group.equals("Legumes")) {
            parser = context.getResources().getXml(R.xml.legumes);
            return parser;
        } else if (group.equals("Meels entrees sidedishes")) {
            parser = context.getResources().getXml(R.xml.meels_entrees_sidedishes);
            return parser;
        } else if (group.equals("Meats, byproducts")) {
            parser = context.getResources().getXml(R.xml.meats_byproducts);
            return parser;
        } else if (group.equals("Mushrooms")) {
            parser = context.getResources().getXml(R.xml.mushrooms);
            return parser;
        } else if (group.equals("Nut and seed products")) {
            parser = context.getResources().getXml(R.xml.nut_and_seed_products);
            return parser;
        } else if (group.equals("Oils, fats")) {
            parser = context.getResources().getXml(R.xml.oils_fats);
            return parser;
        } else if (group.equals("Poultry products")) {
            parser = context.getResources().getXml(R.xml.poultry_products);
            return parser;
        } else if (group.equals("Sausages")) {
            parser = context.getResources().getXml(R.xml.sausages);
            return parser;
        } else if (group.equals("Snacks")) {
            parser = context.getResources().getXml(R.xml.snacks);
            return parser;
        } else if (group.equals("Soups gravies")) {
            parser = context.getResources().getXml(R.xml.soups_gravies);
            return parser;
        } else if (group.equals("Soy products")) {
            parser = context.getResources().getXml(R.xml.soy_products);
            return parser;
        } else if (group.equals("Sushi")) {
            parser = context.getResources().getXml(R.xml.sushi);
            return parser;
        } else if (group.equals("Sweets desserts")) {
            parser = context.getResources().getXml(R.xml.sweets_desserts);
            return parser;
        }
        return parser;
    }

    private XmlPullParser getParserFacts(String group, Context context) {
        XmlPullParser parser = null;
        if (group.equals("Vegetables")) {
            parser = context.getResources().getXml(R.xml.vegetables_facts);
            return parser;
        } else if (group.equals("Baked products")) {
            parser = context.getResources().getXml(R.xml.baked_products_facts);
            return parser;
        } else if (group.equals("Beverages nonalcoholic")) {
            parser = context.getResources().getXml(R.xml.beverages_nonalcoholic_facts);
            return parser;
        } else if (group.equals("Bread flour products")) {
            parser = context.getResources().getXml(R.xml.bread_flour_products_facts);
            return parser;
        } else if (group.equals("Cannedfish, seafoods")) {
            parser = context.getResources().getXml(R.xml.cannedfish_seafoods_facts);
            return parser;
        } else if (group.equals("Cannedfruits, vegetables, mushrooms")) {
            parser = context.getResources().getXml(R.xml.cannedfruits_vegetables_mushrooms_facts);
            return parser;
        } else if (group.equals("Cannedeat products")) {
            parser = context.getResources().getXml(R.xml.cannedeat_products_facts);
            return parser;
        } else if (group.equals("Cerealgrains, cereals, flakes")) {
            parser = context.getResources().getXml(R.xml.cerealgrains_cereals_flakes_facts);
            return parser;
        } else if (group.equals("Cerealgrains cooked")) {
            parser = context.getResources().getXml(R.xml.cereal_grains_cooked_facts);
            return parser;
        } else if (group.equals("Fastfoods")) {
            parser = context.getResources().getXml(R.xml.fastfoods_facts);
            return parser;
        } else if (group.equals("Fish, seafoods")) {
            parser = context.getResources().getXml(R.xml.fish_seafoods_facts);
            return parser;
        } else if (group.equals("Fruits")) {
            parser = context.getResources().getXml(R.xml.fruits_facts);
            return parser;
        } else if (group.equals("Fruitsdried")) {
            parser = context.getResources().getXml(R.xml.fruitsdried_facts);
            return parser;
        } else if (group.equals("Icecream")) {
            parser = context.getResources().getXml(R.xml.icecream_facts);
            return parser;
        } else if (group.equals("Jams")) {
            parser = context.getResources().getXml(R.xml.jams_facts);
            return parser;
        } else if (group.equals("Legumes")) {
            parser = context.getResources().getXml(R.xml.legumes_facts);
            return parser;
        } else if (group.equals("Meels entrees sidedishes")) {
            parser = context.getResources().getXml(R.xml.meels_entrees_sidedishes_facts);
            return parser;
        } else if (group.equals("Meats, byproducts")) {
            parser = context.getResources().getXml(R.xml.meats_byproducts_facts);
            return parser;
        } else if (group.equals("Mushrooms")) {
            parser = context.getResources().getXml(R.xml.mushrooms_facts);
            return parser;
        } else if (group.equals("Nut and seed products")) {
            parser = context.getResources().getXml(R.xml.nut_and_seed_products_facts);
            return parser;
        } else if (group.equals("Oils, fats")) {
            parser = context.getResources().getXml(R.xml.oils_fats_facts);
            return parser;
        } else if (group.equals("Poultry products")) {
            parser = context.getResources().getXml(R.xml.poultry_products_facts);
            return parser;
        } else if (group.equals("Sausages")) {
            parser = context.getResources().getXml(R.xml.sausages_facts);
            return parser;
        } else if (group.equals("Snacks")) {
            parser = context.getResources().getXml(R.xml.snacks_facts);
            return parser;
        } else if (group.equals("Soups gravies")) {
            parser = context.getResources().getXml(R.xml.soups_gravies_facts);
            return parser;
        } else if (group.equals("Soy products")) {
            parser = context.getResources().getXml(R.xml.soy_products_facts);
            return parser;
        } else if (group.equals("Sushi")) {
            parser = context.getResources().getXml(R.xml.sushi_facts);
            return parser;
        } else if (group.equals("Sweets desserts")) {
            parser = context.getResources().getXml(R.xml.sweets_desserts_facts);
            return parser;
        }
        return parser;
    }
}
