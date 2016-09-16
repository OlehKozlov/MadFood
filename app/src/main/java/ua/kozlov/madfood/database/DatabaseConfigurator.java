package ua.kozlov.madfood.database;


import android.content.Context;
import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DatabaseConfigurator {
    private static final int SCHEMA_VERSION = 1;
    private static final String REALM_NAME = "fooddatabase" + ".realm";

    private DatabaseConfigurator() {
    }

    public static void configureDatabase(@NonNull final Context context) {
        Realm.setDefaultConfiguration(getConfiguration(context));
    }

    @NonNull
    public static RealmConfiguration getConfiguration(@NonNull final Context context) {

        return new RealmConfiguration.Builder(context).name(REALM_NAME)
                .schemaVersion(SCHEMA_VERSION).build();

    }
}
