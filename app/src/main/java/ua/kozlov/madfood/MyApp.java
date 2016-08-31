package ua.kozlov.madfood;

import android.app.Application;

import ua.kozlov.madfood.database.DatabaseConfigurator;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseConfigurator.configureDatabase(getApplicationContext());
    }
}
