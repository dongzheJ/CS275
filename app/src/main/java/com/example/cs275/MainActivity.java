package com.example.cs275;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

//import com.example.cs275.ui.home.HomeFragment;
import com.example.cs275.ui.launch.LaunchFragment;
import com.example.cs275.ui.launch.OnFragmentInteractionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;
import java.util.TimeZone;

//==================================================================================================

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    //Use "prefs" below to check shared preferences for launch information:
    SharedPreferences prefs = null;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Use shared preferences to check if app has been launched previously:
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);

        //TODO: To get "LaunchFragment" to display every launch, keep the below 3 lines uncommented:
        //TODO-----------------------VV-- CODE TO TEST FOR FIRST RUN --VV---------------------------
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        //TODO-----------------------^^-- CODE TO TEST FOR FIRST RUN --^^---------------------------
        //Also note: Clearing app storage will also reset shared preferences and will execute as the first launch
        //Clearing app storage will also reset permissions, and the user will need to re enable location to prevent app crash

        //------------------------------------------------------------------------------------------
        //Set up alarm manager to go off once every 24 hours at time determined below:

        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("EST"));
        updateTime.set(Calendar.HOUR_OF_DAY, 19);
        updateTime.set(Calendar.MINUTE, 35);

        Intent intentForAlarm = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0, intentForAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) this.getSystemService(
                Context.ALARM_SERVICE);
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                updateTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        //==========================================================================================
        //If app is launched for the first time:

        if (isFirstLaunch()) {
            prefs.edit().putBoolean("firstrun", false).commit();
            super.onCreate(savedInstanceState);
//            setupMainActivityNav();
            setContentView(R.layout.fragment_launch);
            LaunchFragment launchFragment = new LaunchFragment();
            if (getSupportFragmentManager().findFragmentById(android.R.id.content)==null) {
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, launchFragment)
                        .commit();
            }
        } else { //---------------------------------------------------------------------------------
            //If app has previously been launched:

            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
            setupMainActivityNav();
        }

        ///==========================================================================================
    }

    //==============================================================================================

    //Tests if application is being launched for the first time by using shared preferences
    boolean isFirstLaunch() {
        //App is being launched for the first time:
        if (prefs.getBoolean("firstrun", true)) {
            return true;
        } else { //App has previously been launched before on the device:
            return false;
        }
    }

    //==============================================================================================

    //Creates the nav bar
    void setupMainActivityNav() {
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    //==============================================================================================

    //Switches from the initial launch fragment to the nav view tht displays 3 other fragments
    @Override
    public void changeFragment(int id) {
        if (id == 1) {
//            Fragment frag = new HomeFragment();
            setupMainActivityNav();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_host_fragment, frag);
            ft.commit();
        }// else if (id == 2) {
//            setContentView(R.layout.fragment_launch);
//            LaunchFragment launchFragment = new LaunchFragment();
//            if (getSupportFragmentManager().findFragmentById(android.R.id.content)==null) {
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, launchFragment)
//                        .commit();
//            }
//        }
    }

    //==============================================================================================

    //Overrides onNavigationItemsSelected method in NavigationView to assist in switching fragments upon button press
    //Only used when switching from LaunchFragment to main navigation view
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
