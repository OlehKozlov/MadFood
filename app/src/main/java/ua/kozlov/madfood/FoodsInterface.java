package ua.kozlov.madfood;

import android.content.Context;
import java.util.ArrayList;

public interface FoodsInterface {

    ArrayList<String> getFoodsList(String groupName, Context context);

    String getFoodName(String groupID, String id, Context context);

    String getFoodId(String groupID, String name, Context context);

    ArrayList<String> getFoodParameters(String groupID, int position, Context context);
}
