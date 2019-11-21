package com.example.cs275.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.cs275.MainActivity;
import com.example.cs275.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomeFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    //    private HomeViewModel homeViewModel;
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView tv;
    private double lat = 0.0, lon = 0.0;
    private LatLng libraryLoc = new LatLng(44.477239, -73.196687);
    private Location library = new Location("Library");
    private Location curr = new Location("curr");
    // distance to store distance between home and current location in meters
    private double distance = 0.0;
    private boolean userInTravel = false;

    private Button mButton;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        userPermission();
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //
        tv = root.findViewById(R.id.text);
        library.setLatitude(44.477239);
        library.setLongitude(-73.196687);

        // get user current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            curr.setLatitude(lat);
                            curr.setLongitude(lon);
                            distance = library.distanceTo(curr);
                            String str = String.format("%.02f", distance);
                            tv.append(str + " meters away from Library");
                            // determine whether user in travel
                            if (distance >= 500 && !userInTravel) {
                                tv.append("\nUser in travel");
                                userInTravel = true;
                            }
                            if (userInTravel && distance <= 100) {
                                tv.append("\nYou are back home");
                                userInTravel = false;
                            }
//                            tv.append("\nCurrent local: " + String.format(Locale.US, "%s : %s", lat, lon));
                        }
                    }
                });

        // Button
        mButton = root.findViewById(R.id.button_send);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_send:
//                userPermission();
                // Do something
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Title");
//                final EditText input = new EditText(getActivity());
//                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                builder.setView(input);
//                // Set up the buttons
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        m_Text = input.getText().toString();
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builder.show();
        }
    }

//    private void userPermission() {
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                    new AlertDialog.Builder(getActivity())
//                            .setTitle("Required Location Permission")
//                            .setMessage("You have to give this app permission to access feature")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ActivityCompat.requestPermissions(getActivity(),
//                                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//
//                                }
//                            })
//                            .create()
//                            .show();
//
//
//        } else {
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//
//        }
//
//    } else {
//        // Permission has already been granted
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                            lat = location.getLatitude();
//                            lon = location.getLongitude();
//                            curr.setLatitude(lat);
//                            curr.setLongitude(lon);
//                            distance = library.distanceTo(curr);
//                            String str = String.format("%.02f", distance);
//                            tv.append(str + " meters away from Library");
//                            // determine whether user in travel
//                            if (distance >= 500 && !userInTravel) {
//                                tv.append("\nUser in travel");
//                                userInTravel = true;
//                            }
//                            if (userInTravel && distance <= 100) {
//                                tv.append("\nYou are back home");
//                                userInTravel = false;
//                            }
////                            tv.append("\nCurrent local: " + String.format(Locale.US, "%s : %s", lat, lon));
//                        }
//                    }
//                });
//    }
//    }
//
//


    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        LatLng vermont = new LatLng(44, -73);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vermont));
        googleMap.addMarker(new MarkerOptions().position(libraryLoc).title("Library"));

        // current location
        LatLng current = new LatLng(curr.getLatitude(),curr.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(current);
        markerOptions.title("My Location");

        googleMap.addMarker(markerOptions);
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }



}