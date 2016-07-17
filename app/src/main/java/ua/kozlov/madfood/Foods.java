package ua.kozlov.madfood;

import android.content.Context;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;

public class Foods implements FoodsInterface {

    @Override
    public ArrayList<String> getFoodsList(final String group_name, final Context context) {
        XmlPullParser parser = getParser(group_name, context);
        ArrayList<String> foodsList = setFoodNamesToList(parser);
        return foodsList;
    }

    @Override
    public String getFoodName(final String groupId, final String id, final Context context) {
        return null;
    }

    @Override
    public String getFoodId(final String groupId, final String name, final Context context) {
        return null;
    }

    @Override
    public ArrayList<String> getFoodParameters(final String groupName, final int position, final Context context) {
        XmlPullParser parser = getParserFacts(groupName, context);
        ArrayList<String> foodsList = setFoodParametersToList(parser, position);
        return foodsList;
    }

    private ArrayList<String> setFoodNamesToList(final XmlPullParser parser) {
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

    private ArrayList<String> setFoodParametersToList(final XmlPullParser parser, final int position) {
        ArrayList<String> list = new ArrayList<>();
        int count = 0;
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("Food")) {
                    if (count == position) {
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

    private XmlPullParser getParser(final String group, Context context) {
        XmlPullParser parser = null;
        if (group.equals(context.getString(R.string.vegetables))) {
            parser = context.getResources().getXml(R.xml.vegetables);
            return parser;
        } else if (group.equals(context.getString(R.string.baked_products))) {
            parser = context.getResources().getXml(R.xml.baked_products);
            return parser;
        } else if (group.equals(context.getString(R.string.beverages_nonalcoholic))) {
            parser = context.getResources().getXml(R.xml.beverages_nonalcoholic);
            return parser;
        } else if (group.equals(context.getString(R.string.bread_flour_products))) {
            parser = context.getResources().getXml(R.xml.bread_flour_products);
            return parser;
        } else if (group.equals(context.getString(R.string.cannedfish_seafoods))) {
            parser = context.getResources().getXml(R.xml.cannedfish_seafoods);
            return parser;
        } else if (group.equals(context.getString(R.string.cannedfruits_vegetables_mushrooms))) {
            parser = context.getResources().getXml(R.xml.cannedfruits_vegetables_mushrooms);
            return parser;
        } else if (group.equals(context.getString(R.string.cannedeat_products))) {
            parser = context.getResources().getXml(R.xml.cannedeat_products);
            return parser;
        } else if (group.equals(context.getString(R.string.cerealgrains_cereals_flakes))) {
            parser = context.getResources().getXml(R.xml.cerealgrains_cereals_flakes);
            return parser;
        } else if (group.equals(context.getString(R.string.cerealgrains_cooked))) {
            parser = context.getResources().getXml(R.xml.cereal_grains_cooked);
            return parser;
        } else if (group.equals(context.getString(R.string.fastfoods))) {
            parser = context.getResources().getXml(R.xml.fastfoods);
            return parser;
        } else if (group.equals(context.getString(R.string.fish_seafoods))) {
            parser = context.getResources().getXml(R.xml.fish_seafoods);
            return parser;
        } else if (group.equals(context.getString(R.string.fruits))) {
            parser = context.getResources().getXml(R.xml.fruits);
            return parser;
        } else if (group.equals(context.getString(R.string.fruitsdried))) {
            parser = context.getResources().getXml(R.xml.fruitsdried);
            return parser;
        } else if (group.equals(context.getString(R.string.icecream))) {
            parser = context.getResources().getXml(R.xml.icecream);
            return parser;
        } else if (group.equals(context.getString(R.string.jams))) {
            parser = context.getResources().getXml(R.xml.jams);
            return parser;
        } else if (group.equals(context.getString(R.string.legumes))) {
            parser = context.getResources().getXml(R.xml.legumes);
            return parser;
        } else if (group.equals(context.getString(R.string.meels_entrees_sidedishes))) {
            parser = context.getResources().getXml(R.xml.meels_entrees_sidedishes);
            return parser;
        } else if (group.equals(context.getString(R.string.meats_byproducts))) {
            parser = context.getResources().getXml(R.xml.meats_byproducts);
            return parser;
        } else if (group.equals(context.getString(R.string.mushrooms))) {
            parser = context.getResources().getXml(R.xml.mushrooms);
            return parser;
        } else if (group.equals(context.getString(R.string.nut_and_seed_products))) {
            parser = context.getResources().getXml(R.xml.nut_and_seed_products);
            return parser;
        } else if (group.equals(context.getString(R.string.oils_fats))) {
            parser = context.getResources().getXml(R.xml.oils_fats);
            return parser;
        } else if (group.equals(context.getString(R.string.poultry_products))) {
            parser = context.getResources().getXml(R.xml.poultry_products);
            return parser;
        } else if (group.equals(context.getString(R.string.sausages))) {
            parser = context.getResources().getXml(R.xml.sausages);
            return parser;
        } else if (group.equals(context.getString(R.string.snacks))) {
            parser = context.getResources().getXml(R.xml.snacks);
            return parser;
        } else if (group.equals(context.getString(R.string.soups_gravies))) {
            parser = context.getResources().getXml(R.xml.soups_gravies);
            return parser;
        } else if (group.equals(context.getString(R.string.soy_products))) {
            parser = context.getResources().getXml(R.xml.soy_products);
            return parser;
        } else if (group.equals(context.getString(R.string.sushi))) {
            parser = context.getResources().getXml(R.xml.sushi);
            return parser;
        } else if (group.equals(context.getString(R.string.sweets_desserts))) {
            parser = context.getResources().getXml(R.xml.sweets_desserts);
            return parser;
        }
        return parser;
    }

    private XmlPullParser getParserFacts(final String group, Context context) {
        XmlPullParser parser = null;
        if (group.equals(context.getString(R.string.vegetables))) {
            parser = context.getResources().getXml(R.xml.vegetables_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.baked_products))) {
            parser = context.getResources().getXml(R.xml.baked_products_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.beverages_nonalcoholic))) {
            parser = context.getResources().getXml(R.xml.beverages_nonalcoholic_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.bread_flour_products))) {
            parser = context.getResources().getXml(R.xml.bread_flour_products_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.cannedfish_seafoods))) {
            parser = context.getResources().getXml(R.xml.cannedfish_seafoods_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.cannedfruits_vegetables_mushrooms))) {
            parser = context.getResources().getXml(R.xml.cannedfruits_vegetables_mushrooms_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.cannedeat_products))) {
            parser = context.getResources().getXml(R.xml.cannedeat_products_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.cerealgrains_cereals_flakes))) {
            parser = context.getResources().getXml(R.xml.cerealgrains_cereals_flakes_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.cerealgrains_cooked))) {
            parser = context.getResources().getXml(R.xml.cereal_grains_cooked_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.fastfoods))) {
            parser = context.getResources().getXml(R.xml.fastfoods_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.fish_seafoods))) {
            parser = context.getResources().getXml(R.xml.fish_seafoods_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.fruits))) {
            parser = context.getResources().getXml(R.xml.fruits_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.fruitsdried))) {
            parser = context.getResources().getXml(R.xml.fruitsdried_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.icecream))) {
            parser = context.getResources().getXml(R.xml.icecream_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.jams))) {
            parser = context.getResources().getXml(R.xml.jams_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.legumes))) {
            parser = context.getResources().getXml(R.xml.legumes_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.meels_entrees_sidedishes))) {
            parser = context.getResources().getXml(R.xml.meels_entrees_sidedishes_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.meats_byproducts))) {
            parser = context.getResources().getXml(R.xml.meats_byproducts_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.mushrooms))) {
            parser = context.getResources().getXml(R.xml.mushrooms_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.nut_and_seed_products))) {
            parser = context.getResources().getXml(R.xml.nut_and_seed_products_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.oils_fats))) {
            parser = context.getResources().getXml(R.xml.oils_fats_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.poultry_products))) {
            parser = context.getResources().getXml(R.xml.poultry_products_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.sausages))) {
            parser = context.getResources().getXml(R.xml.sausages_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.snacks))) {
            parser = context.getResources().getXml(R.xml.snacks_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.soups_gravies))) {
            parser = context.getResources().getXml(R.xml.soups_gravies_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.soy_products))) {
            parser = context.getResources().getXml(R.xml.soy_products_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.sushi))) {
            parser = context.getResources().getXml(R.xml.sushi_facts);
            return parser;
        } else if (group.equals(context.getString(R.string.sweets_desserts))) {
            parser = context.getResources().getXml(R.xml.sweets_desserts_facts);
            return parser;
        }
        return parser;
    }
}
