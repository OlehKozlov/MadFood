package ua.kozlov.madfood.utils;

import android.util.Log;

import ua.kozlov.madfood.BuildConfig;

public class DebugLogger {
    private static final String TAG = "mylog";

    public static void log(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }
}