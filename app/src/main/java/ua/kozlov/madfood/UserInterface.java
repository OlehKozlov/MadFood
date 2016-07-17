package ua.kozlov.madfood;

import android.content.Context;
import java.util.ArrayList;
import java.util.Map;

public interface UserInterface {
    void setUserName(String name, Context context);

    String getUserName(Context context);

    void setUserWeight(float weight, Context context);

    float getUserCurrentWeight(Context context);

    ArrayList<Map<String, Float>> getUserEveryWeights(Context context);

}
