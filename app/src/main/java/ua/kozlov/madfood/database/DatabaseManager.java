package ua.kozlov.madfood.database;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kozlov.madfood.models.CategoryR;
import ua.kozlov.madfood.models.FoodsR;
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

    public static void saveNewCategory(@NonNull final String categoryName) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                realmAsync -> {
                    realmAsync.copyToRealmOrUpdate(new CategoryR(categoryName));
                },
                realm::close);
    }

    @NonNull
    public static void saveNewFood(int id, final String categoryName, String foodName,
                                   float calories, float fats, float carbonates,
                                   float proteins, float gi) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                realmAsync -> {
                    realmAsync.copyToRealmOrUpdate(new FoodsR(id, categoryName, foodName,
                            calories, fats, carbonates, proteins, gi));
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
    public static RealmResults<CategoryR> getAllCategories(final Realm realm) {
        final RealmResults<CategoryR> result = realm.where(CategoryR.class).
                findAllSorted("name");
        DebugLogger.log("Categories size: " + result.size());
        return result;
    }

    @NonNull
    public static RealmResults<FoodsR> getFoods(final Realm realm, String category) {
        final RealmResults<FoodsR> result = realm.where(FoodsR.class).contains("category", category).
                findAllSorted("foodName");
        DebugLogger.log("Foods size: " + result.size());
        return result;
    }

    @NonNull
    public static int getLastId(final Realm realm) {
        int id = 0;
        final RealmResults<FoodsR> result = realm.where(FoodsR.class).
                findAllSorted("id");
        id = result.get(result.size() - 1).getId();
        DebugLogger.log("ID: " + id);
        return id;
    }

    @NonNull
    public static FoodsR getSameFood(final Realm realm, String category, String food) {
        final RealmResults<FoodsR> result = realm.where(FoodsR.class).contains("category", category).
                contains("foodName", food).findAllSorted("foodName");
        FoodsR foodsR = result.get(0);
        DebugLogger.log("Food name: " + foodsR.getFoodName());
        return foodsR;
    }
}
