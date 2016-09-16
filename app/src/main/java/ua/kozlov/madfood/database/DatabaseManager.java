package ua.kozlov.madfood.database;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kozlov.madfood.models.CategoryR;
import ua.kozlov.madfood.models.UserR;
import ua.kozlov.madfood.utils.DebugLogger;

public final class DatabaseManager {

    public static void saveUserData(@NonNull final String name,
                                    final float weight, final String date) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                realmAsync -> {
                    realmAsync.copyToRealmOrUpdate(new UserR(name, weight, date));
                },
                realm::close);
    }

    public static RealmResults<UserR> getUserData() {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<UserR> result = realm.where(UserR.class).
                findAllSorted("date");
        realm.close();
        return result;
    }

    @NonNull
    public static RealmResults<CategoryR> getAllCategories() {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<CategoryR> result = realm.where(CategoryR.class).
                findAllSorted("name");
        DebugLogger.log("Categories size: " + result.size());
        realm.close();
        return result;
    }
}
