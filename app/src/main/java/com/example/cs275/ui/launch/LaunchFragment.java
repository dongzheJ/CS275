package com.example.cs275.ui.launch;

import android.app.Activity;
import android.database.Cursor;
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

        Button btn = (Button) root.findViewById(R.id.complete);
        final EditText userName = (EditText) root.findViewById(R.id.text_name);
        final EditText email = (EditText) root.findViewById(R.id.text_email);

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameString = userName.getText().toString();
                String emailString = email.getText().toString();

                DatabaseHelper db = new DatabaseHelper(getActivity());
                db.insertData(userNameString, "Last Name", emailString, "test", "test");
                Cursor cur = db.getAllData();
//                if (cur.moveToFirst()) {
//                    System.out.println(cur.getString(cur.getColumnIndex("FNAME")));
//                }

                mListener.changeFragment(1);
            }
        });

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