package ua.kozlov.madfood.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.OneDayPlanR;
import ua.kozlov.madfood.utils.DebugLogger;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String DATABASE_NAME = "fooddatabase.realm";
    private final String PREFS_NAME = "Launch";
    public static EditText mEditSearch;
    public static TextView mTextProgress;
    private Realm mRealm;
    private RealmChangeListener mRealmChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextProgress = (TextView) findViewById(R.id.textAppBarProgress);
        mRealm = Realm.getDefaultInstance();
        mRealmChangeListener = element -> {
            DebugLogger.log(getString(R.string.log_data_changed));
            loadPlanningProgressData();
            //replaceFragment();
        };
        mRealm.addChangeListener(mRealmChangeListener);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mEditSearch = (EditText) findViewById(R.id.editMainSearch);
        mEditSearch.clearFocus();
        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sPrefCategorie =
                        getBaseContext().getSharedPreferences("categories", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorCategorie = sPrefCategorie.edit();
                editorCategorie.putString("category", "");
                editorCategorie.commit();
                SharedPreferences sPrefFood =
                        getBaseContext().getSharedPreferences("foods", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorFood = sPrefFood.edit();
                editorFood.putString("food", mEditSearch.getText().toString());
                editorFood.commit();
                DebugLogger.log(mEditSearch.getText().toString());
                FoodsFragment foodsFragment = new FoodsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainContainer, foodsFragment);
                transaction.commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("first_launch", true)) {
            DebugLogger.log("First time launch");
            SharedPreferences dateSettings = getSharedPreferences("Date", 0);
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            dateSettings.edit().putInt("date", day).commit();
            copyBundledRealmFile();
            settings.edit().putBoolean("first_launch", false).commit();
            AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                    PendingIntent.getActivity(this.getBaseContext(), 0,
                            new Intent(getIntent()), getIntent().getFlags()));
            System.exit(2);
        } else {
            DebugLogger.log("Second time launch");
            if (settings.getBoolean("personal data", true)){
                replaceFragmentToProfile();
                Toast.makeText(getBaseContext(), R.string.toast_profile_information, Toast.LENGTH_LONG).show();
            } else {
                commitPlanning();
                replaceFragment();
            }
        }

    }

    private void loadPlanningProgressData() {
        RealmResults<OneDayPlanR> results = DatabaseManager.getOneDayPlanData(mRealm);
        float progress = 0;
        for (int i = 0; i < results.size(); i++) {
            progress += results.get(i).getProgress();
        }
        DebugLogger.log(getString(R.string.log_result_size) + results.size());
        String strProgress = progress + "";
        if (strProgress.length() > 3) {
            strProgress = strProgress.substring(0, 4);
        }
        if (strProgress.endsWith(".")) {
            strProgress = strProgress.substring(0, strProgress.length() - 1 );
        }
        strProgress += "%";
        mTextProgress.setText(strProgress);
    }

    public String copyBundledRealmFile() {
        try {
            InputStream inputStream = this.getResources().openRawResource(R.raw.fooddatabase);
            DebugLogger.log(inputStream.toString());
            File file = new File(getBaseContext().getFilesDir(), DATABASE_NAME);
            DebugLogger.log(file.getPath());
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            DebugLogger.log(file.getTotalSpace() + "");
            outputStream.close();
            DebugLogger.log(getString(R.string.log_database_copied));
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mEditSearch.setText("");
            SharedPreferences sPrefFood = getBaseContext().getSharedPreferences("foods", Context.MODE_PRIVATE);
            sPrefFood.edit().putString("food", "").commit();
            replaceFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.profile_item: {
                Fragment fragment = new ProfileFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.foods_item: {
                Fragment fragment = new CategoriesFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.planning_item: {
                Fragment fragment = new PlanningFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.statistic_item: {
                Fragment fragment = new StatisticFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.calendar_item: {
                Fragment fragment = new CalendarFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.exit_item: {
                finish();
            }
        }
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment() {
        loadPlanningProgressData();
        PlanningFragment planningFragment = new PlanningFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, planningFragment);
        transaction.commit();
    }

    private void replaceFragmentToProfile() {
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, profileFragment);
        transaction.commit();
    }

    private void commitPlanning() {
        SharedPreferences dateSettings = getSharedPreferences("Date", 0);
        SharedPreferences timeSettings = getSharedPreferences("Time", 0);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        DebugLogger.log(getString(R.string.log_day) + day);
        DebugLogger.log(getString(R.string.log_hour) + hour);
        DebugLogger.log(getString(R.string.log_settings_day) + dateSettings.getInt("date", 0));
        if (day != dateSettings.getInt("date", 0) && hour >= timeSettings.getInt("time", 3)) {
            DebugLogger.log(Calendar.HOUR + "");
            Realm realm = Realm.getDefaultInstance();
            RealmResults<OneDayPlanR> results = DatabaseManager.getOneDayPlanData(realm);
            for (int i = 0; i < results.size(); i++) {
                String foodName = results.get(i).getFoodName();
                String foodWeight = results.get(i).getFoodWeight();
                String calories = results.get(i).getCalories();
                String fat = results.get(i).getFat();
                String carbonates = results.get(i).getCarbonates();
                String proteins = results.get(i).getProteins();
                String gi = results.get(i).getGi();
                float progress = results.get(i).getProgress();
                long date = results.get(i).getDate();
                DatabaseManager.moveToPlan(foodName, foodWeight, calories, fat, carbonates,
                        proteins, gi, progress, date);
                dateSettings.edit().putInt("date", day).commit();
            }
            realm.close();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}