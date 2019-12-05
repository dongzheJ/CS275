package com.example.cs275.ui.launch;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import com.example.cs275.AlarmReceiver;
import com.example.cs275.DatabaseHelper;
import com.example.cs275.MainActivity;
import com.example.cs275.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
        final TextView searchResultsTextView = (TextView) root.findViewById(R.id.text_result);
        final TextView searchResultsStatusTextView = (TextView) root.findViewById(R.id.text_result_status);

        statusTextView.setTextColor(Color.BLACK);

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

//        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

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

                String searchText = search.getText().toString();
                List<String> list;
                try {
                    list = geolocate(searchText);
                    String testForError = list.get(0);
                    if (testForError.equals("anything")) {
                        //May cause error
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    list = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        list.add(i, "error");
                    }
                }

                //----------------------------------------------------------------------------------

                //If input is not in proper format:
                if (!emailString.contains("@") || !emailString.contains(".") || userNameString.length() == 0 || list.get(0).equals("error") || list.size() < 5) {
                    String status = "Check the format of your input!";
                    statusTextView.setText(status);
                    statusTextView.setTextColor(Color.RED);

                } else { //-------------------------------------------------------------------------
                    //If input is in proper format:

                    //Add user input to database:
                    DatabaseHelper db = new DatabaseHelper(getActivity());
                    db.insertData(userNameString, emailString, "surveyTime", list.get(0), list.get(3), list.get(4));
//                    Cursor cur = db.getAllData();
//                if (cur.moveToFirst()) {
//                    System.out.println(cur.getString(cur.getColumnIndex("NAME")));
//                }

                    //------------------------------------------------------------------------------

                    //Set up alarm manager to go off once every 24 hours at time determined below:
                    Calendar updateTime = Calendar.getInstance();
                    updateTime.setTimeZone(TimeZone.getTimeZone("EST"));
                    updateTime.set(Calendar.HOUR_OF_DAY, 1);
                    updateTime.set(Calendar.MINUTE, 0);

                    Intent intentForAlarm = new Intent(getContext(), AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),
                            0, intentForAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarms = (AlarmManager) getActivity().getSystemService(
                            Context.ALARM_SERVICE);
                    alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                            updateTime.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);

                    //------------------------------------------------------------------------------

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

                String searchResultsTextViewString = "No Results, please try searching again!";
                String searchResultsStatusTextViewString = "If you are searching for a City name, try adding the state/province to be more specific!\n\nPlease ensure that you have an internet connection.";
                //Retrieve strings inputted by user in EditText field:
                String searchText = search.getText().toString();
                try {
                    List<String> list = geolocate(searchText);

                    //If no search results were found:
                    if (list.get(0).equals("No results!")) {
                        //Do something
                    } else if (list.get(0).equals("Be more specific!")) { //If multiple results were found:
                        //Do something
                    } else { //If search results were found:
                        searchResultsTextViewString = "City Found: " + list.get(0) + ", " + list.get(1) + ", " + list.get(2);
                        searchResultsStatusTextViewString = "If this location is correct, please continue by clicking Submit!\nIf the location is incorrect, try searching again!";
//                    System.out.println(list.get(0));
//                    System.out.println(list.get(1));
//                    System.out.println(list.get(2));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                searchResultsTextView.setText(searchResultsTextViewString);
                searchResultsStatusTextView.setText(searchResultsStatusTextViewString);
                searchResultsTextView.setTextColor(Color.BLACK);
                searchResultsStatusTextView.setTextColor(Color.BLACK);
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
        List<String> list = new ArrayList<>();
        List<Address> addressList = gc.getFromLocationName(searchTerm, 1);

        //If search results were found:
        if (addressList.size() > 0) {
            try {
                Address address = addressList.get(0);
                String locality = address.getLocality();
                String state = address.getAdminArea();
                String country = address.getCountryName();
                double lat = address.getLatitude();
                double lon = address.getLongitude();
                list.add(locality);
                list.add(state);
                list.add(country);
                list.add(String.valueOf(lat));
                list.add(String.valueOf(lon));
            } catch (NullPointerException e) {
                list.add(0, "Be more specific!");
            }

        } else { //If no search results were found:
            list.add("No results!");
        }
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