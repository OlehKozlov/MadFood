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
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ua.kozlov.madfood.R;
import ua.kozlov.madfood.utils.DebugLogger;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String PREFS_NAME = "Launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("first_launch", true)) {
            DebugLogger.log("Firts time launch");
            copyBundledRealmFile();
            settings.edit().putBoolean("first_launch", false).commit();
            AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                    PendingIntent.getActivity(this.getBaseContext(), 0,
                            new Intent(getIntent()), getIntent().getFlags()));
            System.exit(2);
        } else {
            DebugLogger.log("Second time launch");
        }
    }

    public String copyBundledRealmFile() {
        try {
            InputStream inputStream = this.getResources().openRawResource(R.raw.fooddatabase);
            DebugLogger.log(inputStream.toString());
            File file = new File(getBaseContext().getFilesDir(), "fooddatabase.realm");
            DebugLogger.log(file.getPath());
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            DebugLogger.log(file.getTotalSpace()+"");
            outputStream.close();
            DebugLogger.log("database copied");
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
            super.onBackPressed();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.profile_item:{
                Fragment fragment = new ProfileFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.foods_item: {
                Fragment fragment = new CategoriesFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.planning_item:{
                Fragment fragment = new PlanningFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.statistic_item:{
                Fragment fragment = new StatisticFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.calendar_item:{
                Fragment fragment = new CalendarFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.calculate_item:{
                Fragment fragment = new CalculateFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.share_item:{
                Fragment fragment = new ShareFragment();
                transaction.replace(R.id.mainContainer, fragment);
                break;
            }
            case R.id.conversation_item:{
                break;
            }
            case R.id.settings_item:{
                break;
            }
            case R.id.exit_item:{
                finish();
            }
        }
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}