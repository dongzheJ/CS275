package com.example.cs275;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    //Use "prefs" below to check shared preferences for launch information:
    SharedPreferences prefs = null;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Use shared preferences to check if app has been launched previously:
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);

        //Uncomment below line to get "LaunchFragment" tp display each launch, no matter if app has been launched previously
//        prefs.edit().putBoolean("firstrun", true).commit();

        //------------------------------------------------------------------------------------------

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

        //------------------------------------------------------------------------------------------
    }

    //==============================================================================================

    boolean isFirstLaunch() {
        if (prefs.getBoolean("firstrun", true)) {
            return true;
        } else {
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
