package com.example.cs275.ui.launch;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cs275.DatabaseHelper;
import com.example.cs275.MainActivity;
import com.example.cs275.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LaunchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private com.example.cs275.ui.launch.LaunchViewModel launchViewModel;

    //==============================================================================================

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        launchViewModel =
                ViewModelProviders.of(this).get(com.example.cs275.ui.launch.LaunchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_launch, container, false);

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        Button doneBtn = (Button) root.findViewById(R.id.complete);
        Button searchBtn = (Button) root.findViewById(R.id.search);
        final EditText userName = (EditText) root.findViewById(R.id.text_name);
        final EditText email = (EditText) root.findViewById(R.id.text_email);
        final EditText search = (EditText) root.findViewById(R.id.text_geocodeSearch);
        final TextView statusTextView = (TextView) root.findViewById(R.id.text_status);

        final String status = "Please input the below information:";
//        String nameMsg = "Name";
//        String emailMsg = "Email";
//        userName.setText(nameMsg);
//        email.setText(emailMsg);
        statusTextView.setText(status);

//        userName.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                userName.setHint("");
//            }
//        });
//
//        email.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                email.setHint("");
//            }
//        });

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        //==========================================================================================

        //Below code executes upon "Done!" button press:
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String userNameString = "";
//                String emailString = "";

                //Retrieve strings inputted by user in EditText fields:
                String userNameString = userName.getText().toString();
                String emailString = email.getText().toString();

                //----------------------------------------------------------------------------------

                //If email is not in proper format:
                if (!emailString.contains("@") || !emailString.contains(".") || userNameString.length() == 0) {
                    String status = "Check the format of your input!";
                    statusTextView.setText(status);
                    statusTextView.setTextColor(Color.RED);

                } else { //-------------------------------------------------------------------------
                    //If email is in proper format:

                    //Add user input to database:
                    DatabaseHelper db = new DatabaseHelper(getActivity());
                    db.insertData(userNameString, emailString, "surveyTime", "homeLocality", "homeLatitude", "homeLongitude");
//                    Cursor cur = db.getAllData();
//                if (cur.moveToFirst()) {
//                    System.out.println(cur.getString(cur.getColumnIndex("NAME")));
//                }

                    //Change fragment to main view with navigation:
                    mListener.changeFragment(1);
                }

                //----------------------------------------------------------------------------------

            }
        });

        //==========================================================================================

        //Below code executes upon "search" button press:
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Retrieve strings inputted by user in EditText field:
                String searchText = search.getText().toString();
                try {
                    List<String> list = geolocate(searchText);
                    System.out.println(list.get(0));
                    System.out.println(list.get(1));
                    System.out.println(list.get(2));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //==========================================================================================

//        final TextView textView = root.findViewById(R.id.text_launch);
//        launchViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    //==============================================================================================

    public List<String> geolocate(String searchTerm) throws IOException {
        Geocoder gc = new Geocoder(getContext());
        List<Address> addressList = gc.getFromLocationName(searchTerm, 1);
        Address address = addressList.get(0);
        String locality = address.getLocality();
        double lat = address.getLatitude();
        double lon = address.getLongitude();
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(lat));
        list.add(String.valueOf(lon));
        list.add(locality);
        return list;
    }

    //==============================================================================================

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //==============================================================================================

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //==============================================================================================

}