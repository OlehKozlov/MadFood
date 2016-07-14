package ua.kozlov.madfood;


import android.content.Context;

import java.util.ArrayList;

public interface GroupsInterface {

    ArrayList<String> getGroupsList(Context context);

    String getGroupName(String id, Context context);

    String getGroupId(String name, Context context);
}
