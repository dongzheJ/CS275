package com.example.cs275.ui.notifications;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs275.DatabaseHelper;
import com.example.cs275.MainActivity;
import com.example.cs275.R;
import com.example.cs275.SurveyDatabaseHelper;
import com.example.cs275.ui.submit.Submission;
import com.example.cs275.ui.submit.SubmissionAdapter;
import com.example.cs275.ui.submit.SurveyActivity;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private com.example.cs275.ui.notifications.NotificationsViewModel notificationsViewModel;
    private SurveyDatabaseHelper dbh;
    private Cursor mCursor;
    private ArrayList<Submission> mSubmissions;
//    private Button btn;

    private TextView mEmpty;
    public RecyclerView rvSubmission;
    public SubmissionAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        //Initializes database and determines if RV is populated
        dbh = new SurveyDatabaseHelper(getActivity());
        mSubmissions = new ArrayList<>();
        mCursor = dbh.getAllData();

        notificationsViewModel =
                ViewModelProviders.of(this).get(com.example.cs275.ui.notifications.NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        mEmpty = root.findViewById(R.id.no_subs);

        //Sets up empty adapter
        rvSubmission = root.findViewById(R.id.survey_rv);
        adapter = new SubmissionAdapter(mSubmissions);
        rvSubmission.setAdapter(adapter);

//        btn = root.findViewById(R.id.sub_btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().startActivity(new Intent(getActivity(), SurveyActivity.class));
//            }
//        });

        populateRV();

        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }

    @Override
    public void onClick(View v) {

    }

    private void populateRV(){
        if(mCursor.getCount() > 0){
            if(mCursor.moveToNext()){
                do{
                    mSubmissions.add(new Submission(mCursor.getInt(0),
                            mCursor.getString(1),
                            mCursor.getString(2),
                            mCursor.getString(3),
                            mCursor.getString(4)));
                } while (mCursor.moveToNext());
                mEmpty.setVisibility(View.INVISIBLE);
                loadInValues();
            }
        } else{
            mEmpty.setText("You haven't yet completed any surveys yet.  To complete a survey, you must travel away from your home location.  Once TripTracker notices this, you will receive a notification to complete a survey.  Just tap that notification, and you will be brought to a survey.");
            return;
        }

    }

    private void loadInValues(){
        rvSubmission.setAdapter(adapter);
        rvSubmission.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
    }
}