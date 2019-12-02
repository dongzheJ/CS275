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

        DatabaseHelper db = new DatabaseHelper(getActivity());
        Cursor cur = db.getAllData();

        TextView nameTextView = (TextView) root.findViewById(R.id.text_disp_name);
        TextView emailTextView = (TextView) root.findViewById(R.id.text_disp_email);

        String textName = "Name: ";
        String textEmail = "Email: ";
        if (cur.moveToFirst()) {
            textName = textName + cur.getString(cur.getColumnIndex("FNAME"));
            textEmail = textEmail + cur.getString(cur.getColumnIndex("EMAIL"));
        }
        nameTextView.setText(textName);
        emailTextView.setText(textEmail);

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