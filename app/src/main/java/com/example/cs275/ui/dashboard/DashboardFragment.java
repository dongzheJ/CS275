package com.example.cs275.ui.dashboard;

//import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cs275.DatabaseHelper;
import com.example.cs275.R;
//import com.example.cs275.ui.launch.OnFragmentInteractionListener;

//==================================================================================================

public class DashboardFragment extends Fragment {

    //    private OnFragmentInteractionListener mListener;
    private com.example.cs275.ui.dashboard.DashboardViewModel dashboardViewModel;

    //==============================================================================================

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(com.example.cs275.ui.dashboard.DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //------------------------------------------------------------------------------------------

        DatabaseHelper db = new DatabaseHelper(getActivity());
        Cursor cur = db.getAllData();

        //------------------------------------------------------------------------------------------

        TextView nameTitleTextView = (TextView) root.findViewById(R.id.text_disp_name_title);
        TextView nameTextView = (TextView) root.findViewById(R.id.text_disp_name);

        TextView emailTextView = (TextView) root.findViewById(R.id.text_disp_email);

        TextView locationTextView = (TextView) root.findViewById(R.id.text_disp_location);

        TextView aboutTextView = (TextView) root.findViewById(R.id.text_disp_about);

        //------------------------------------------------------------------------------------------

        String textNameTitle = "Name: ";
        String textName = "No name in db";

        String textEmail = "Email: ";


        String textLocation = "Location: ";

        String textAbout = "Welcome to the TripTracker application!\n" +
                "This application was developed with a primary purpose to record your home location " +
                "and check a your location in the background, daily.  If TripTracker sees that you " +
                "are not near your home location, you will receive a notification to complete a short " +
                "and easy survey about your trip.\nThe surveys are for collecting information for a " +
                "travel study being conducted by Dr.Lisa Aultman-Hall.\nTripTracker was created as a " +
                "project for CS275 by Max Peck, Vanessa White, Dongzhe Jiang, and Eric Boland at " +
                "The Universiry of Vermont.";

        //------------------------------------------------------------------------------------------

        if (cur.moveToFirst()) {
            textName = cur.getString(cur.getColumnIndex("NAME"));
            textEmail = textEmail + cur.getString(cur.getColumnIndex("EMAIL"));
            textLocation = textLocation + cur.getString(cur.getColumnIndex("HOMELOCALITY"));
        }

        //------------------------------------------------------------------------------------------
        nameTitleTextView.setText(textNameTitle);
        nameTextView.setText(textName);

        emailTextView.setText(textEmail);

        locationTextView.setText(textLocation);

        aboutTextView.setText(textAbout);

        //------------------------------------------------------------------------------------------

//        Button btn = (Button) root.findViewById(R.id.relaunchSetup);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListener.changeFragment(2);
//            }
//        });
//        final TextView textView = root.findViewById(R.id.text_dashboard);
        final TextView textView = root.findViewById(R.id.text_title);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    //==============================================================================================

//    //==============================================================================================
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    //==============================================================================================
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    //==============================================================================================
}