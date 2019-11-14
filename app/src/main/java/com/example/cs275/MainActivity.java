package com.example.cs275;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cs275.ui.launch.LaunchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    //Use "prefs" below to check shared preferences for launch information:
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Use shared preferences to check if app has been launched previously:
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);

        //Uncomment below line to get "LaunchFragment" tp display each launch, no matter if app has been launched previously
//        prefs.edit().putBoolean("firstrun", true).commit();

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        //If app is launched for the first time:
        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).commit();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_launch);
            LaunchFragment launchFragment = new LaunchFragment();
            if (getSupportFragmentManager().findFragmentById(android.R.id.content)==null) {
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, launchFragment)
                        .commit();
            }

        } else { // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

            //If app has previously been launched:
            super.onCreate(savedInstanceState);
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

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    }

}
