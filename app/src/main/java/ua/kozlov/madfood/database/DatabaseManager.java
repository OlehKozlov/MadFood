package ua.kozlov.madfood.database;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.models.CategoryR;
import ua.kozlov.madfood.models.FoodsR;
import ua.kozlov.madfood.models.OneDayPlanR;
import ua.kozlov.madfood.models.PlanDataR;
import ua.kozlov.madfood.models.UserR;
import ua.kozlov.madfood.utils.DebugLogger;

public final class DatabaseManager {

    public static void saveUserData(@NonNull final String name, final float weight,
                                    final float height, final int plan, final String date) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                realmAsync -> {
                    realmAsync.copyToRealmOrUpdate(new UserR(name, weight, height, plan, date));
                }, realm::close);
    }

    public static void saveNewCategory(@NonNull final String categoryName) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                realmAsync -> {
                    realmAsync.copyToRealmOrUpdate(new CategoryR(categoryName));
                }, realm::close);
    }

    @NonNull
    public static void saveNewFood(final Realm realm, final int id, final String categoryName,
                                   final String foodName, final float calories, final float fats,
                                   final float carbonates, final float proteins, final float gi) {

        realm.executeTransactionAsync(
                realmAsync -> {
                    realmAsync.copyToRealmOrUpdate(new FoodsR(id, categoryName, foodName,
                            calories, fats, carbonates, proteins, gi));
                });
    }

    public static void saveToOneDayPlan(@NonNull final String foodName,
                                        final String foodWeight, final String calories, final String fat,
                                        final String carbonates, final String proteins, final String gi,
                                        final float progress, final long date) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                realmAsync -> {
                    realmAsync.copyToRealmOrUpdate(new OneDayPlanR(foodName, foodWeight,
                            calories, fat, carbonates, proteins, gi, progress, date));
                }, realm::close);
        DebugLogger.log(foodName);
        DebugLogger.log(foodWeight);
        DebugLogger.log("Saved");
    }

    public static void moveToPlan(@NonNull final String foodName, final String foodWeight,
                                  final String calories, final String fat, final String carbonates,
                                  final String proteins, final String gi, final float progress,
                                  final long date) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                realmAsync -> {
                    realmAsync.copyToRealmOrUpdate(new PlanDataR(foodName, foodWeight,
                            calories, fat, carbonates, proteins, gi, progress, date));
                    realmAsync.where(OneDayPlanR.class).findAll().deleteAllFromRealm();
                }, realm::close);
        DebugLogger.log(foodName);
        DebugLogger.log(foodWeight);
        DebugLogger.log("Moved");
    }

    public static RealmResults<UserR> getUserData(final Realm realm) {
        final RealmResults<UserR> result = realm.where(UserR.class).
                findAllSorted("date");
        return result;
    }

    public static RealmResults<OneDayPlanR> getOneDayPlanData(final Realm realm) {
        final RealmResults<OneDayPlanR> result = realm.where(OneDayPlanR.class).
                findAllSorted("date");
        return result;
    }

    public static RealmResults<PlanDataR> getPlanData(final Realm realm, final long date) {
        long start = date + (3600000 * 4);
        long end = start + (3600000 * 24);
        DebugLogger.log("Start: " + start);
        DebugLogger.log("End: " + end);
        final RealmResults<PlanDataR> result = realm.where(PlanDataR.class).
                between("date", start, end).findAllSorted("date");
        return result;
    }

    public static RealmResults<PlanDataR> getAllPlanData(final Realm realm) {
        final RealmResults<PlanDataR> result = realm.where(PlanDataR.class).
                findAllSorted("date");
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
    public static RealmResults<FoodsR> getFoods(final Realm realm, final String category, final String food) {
        final RealmResults<FoodsR> result = realm.where(FoodsR.class).contains("category", category).
                contains("foodName", food).findAllSorted("foodName");
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
    public static FoodsR getSameFood(final Realm realm, final String category, final String food) {
        final RealmResults<FoodsR> result = realm.where(FoodsR.class).contains("category", category).
                contains("foodName", food).findAllSorted("foodName");
        FoodsR foodsR = result.get(0);
        DebugLogger.log("Food name: " + foodsR.getFoodName());
        return foodsR;
    }
}
