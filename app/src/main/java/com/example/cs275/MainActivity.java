package com.example.cs275;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

//import com.example.cs275.ui.home.HomeFragment;
import com.example.cs275.ui.launch.LaunchFragment;
import com.example.cs275.ui.launch.OnFragmentInteractionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//==================================================================================================

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    //Use "prefs" below to check shared preferences for launch information:
    SharedPreferences prefs = null;
    private int STORAGE_PERMISSION_CODE=1;
    private int LOCATION_PERMISSION_CODE=1;
    private int requestCode2;

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
        //If app is launched for the first time:

        if (isFirstLaunch()) {
            prefs.edit().putBoolean("firstrun", false).commit();
            //Check if the storage permission had been granted if so make a toast else call the storage permission function
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
            }

            else{
                requestStoragePermission();
            }
            //Check if the location  permission had been granted if so make a toast else call the location  permission function
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
            }

            else{
                requestLocationPermission();
            }


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

    //The location permission function
    private void requestLocationPermission() {
        //Create an alert dialog for the location permission and set a message
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION )) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that ")
                    .setPositiveButton("ok ", new DialogInterface.OnClickListener() {
                        //Give the permission with the allow button
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
                            requestCode2=LOCATION_PERMISSION_CODE;
                        }
                    })
                    //Deny the permission with the deny button
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }

    //Create an alert dialog for the storage permission and set a message
    private void requestStoragePermission() {
       if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE )) {
         new AlertDialog.Builder(this)
                 .setTitle("Permission needed")
                 .setMessage("This permission is needed because of this and that ")
                 .setPositiveButton("ok ", new DialogInterface.OnClickListener() {
                     //Give the permission with the allow button
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                     }
                 })
                 //Deny the permission with the deny button
                 .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     }
                 })
                 .create().show();
       }
           else{
               ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
           }
       }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Present a toast to display if the storage permission has been denied
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length>0 && grantResults [0] == PackageManager.PERMISSION_GRANTED ){
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        //Present a toast to display if the storage permission has been denied
        if (requestCode2 == LOCATION_PERMISSION_CODE){
            if (grantResults.length>0 && grantResults [0] == PackageManager.PERMISSION_GRANTED ){
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

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
