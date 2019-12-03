package com.example.cs275.ui.home;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.cs275.MainActivity;
import com.example.cs275.R;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;


public class HomeFragment extends Fragment implements OnMapReadyCallback{

    // private HomeViewModel homeViewModel;
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private Dialog popupHome;
    private GoogleMap mMap;
    private Button homeButton;
    private TextView close;
    private Marker homeAddress;
    private Marker searchMarker;
    private SupportMapFragment mapFragment;
    private SearchView searchView;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView tv;
    private String home;
    private double lat = 0.0, lon = 0.0;
    private Location curr = new Location("curr");
    // distance to store distance between home and current location in meters
    private double distance = 0.0;
    private boolean userInTravel = false;
    private Button mButton;
    private Address homeStop;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button HomeButton = (Button) root.findViewById(R.id.homeAddressEnter);
        final EditText userHome = (EditText) root.findViewById(R.id.userAddress);
        final TextView statusTextView = (TextView) root.findViewById(R.id.text_status);
        searchView = (SearchView) root.findViewById(R.id.sv_location);
        //final TextView statusTextView = (TextView) root.findViewById(R.id.text_status);
        //final String status = "Please input the below information:";
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);

        //Add the popup
        popupHome = new Dialog(getActivity());
        popupHome.setContentView(R.layout.set_home);
        //homeButton.setOnClickListener(this);
        close = (TextView) popupHome.findViewById(R.id.closePopup);
        //close.setOnClickListener(this);
        popupHome.show();

        homeButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              TextView tv = (TextView) popupHome.findViewById(R.id.userAddress);
              if (tv.getText().length()==0) {
                  final String status = "Please input the below information:";
                  statusTextView.setText(status);
                  statusTextView.setTextColor(Color.RED);


      }}});


        //Listen to the search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                //Get the location if not empty
                if (location != null|| !location.equals("")){
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    //Add marker to the map location searched

                    searchMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        //Redo the map
        mapFragment.getMapAsync(this);
        tv = root.findViewById(R.id.text);

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
                            String str = String.format("%.02f", distance);
                           tv.append(str + " meters away from Library");
                            // determine whether user in travel
                            if (distance >= 500 && !userInTravel){
                                tv.append("\nUser in travel");
                                userInTravel = true;
                            }
                            if (userInTravel && distance <= 100){
                                tv.append("\nYou are back home");
                                userInTravel = false;
                            }
//                            tv.append("\nCurrent local: " + String.format(Locale.US, "%s : %s", lat, lon));
                        }
                    }
                });

        // Button
       // mButton = root.findViewById(R.id.button_send);

        return root;
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng vermont = new LatLng(44, -73);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vermont));

        // current location
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