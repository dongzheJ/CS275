package com.example.cs275.ui.submit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cs275.R;
import com.example.cs275.SurveyDatabaseHelper;
import com.example.cs275.ui.notifications.NotificationsFragment;

public class SurveyActivity extends AppCompatActivity {

    //Declare style-based variables
    private EditText mLocation;
    private Spinner mMode;
    private Spinner mLength;
    private Spinner mEnjoy;
    private Button mSubmitBtn;

    private ArrayAdapter<String> mModeAdapter;
    private ArrayAdapter<String> mLengthAdapter;
    private ArrayAdapter<String> mEnjoyAdapter;

    private SurveyDatabaseHelper dbh;
    private Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Survey");
        setContentView(R.layout.activity_survey);

        //Initialize style-based variables
        mLocation = findViewById(R.id.survey_travel_answer);
        mMode = findViewById(R.id.survey_mode_answer);
        mLength = findViewById(R.id.survey_length_answer);
        mEnjoy = findViewById(R.id.survey_enjoy_answer);
        mSubmitBtn = findViewById(R.id.survey_submit);

        //Call to survey db
        dbh = new SurveyDatabaseHelper(this);
        mCursor = dbh.getAllData();

        //Populate Spinners with appropriate values
        String[] modes = {"Automobile", "Train", "Plane", "Bicycle", "Boat", "Other"};
        String[] lengths = {"One Day", "Several Days (2-6)", "One Week", "Several Weeks (1-4)", "One Month", "Several Months (1-11)", "More Than One Year"};
        String[] enjoy = {"Yes", "No", "Don't Care"};

        mModeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modes);
        mMode.setAdapter(mModeAdapter);
        mLengthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lengths);
        mLength.setAdapter(mLengthAdapter);
        mEnjoyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, enjoy);
        mEnjoy.setAdapter(mEnjoyAdapter);

        //Button listener call for submit btn
        mSubmitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Prepares values for db insertion
                if(mLocation.getText().toString().isEmpty()){
                    mLocation.setText("N/A");
                }
                boolean isInserted = dbh.insertData(mLocation.getText().toString(),
                               mMode.getSelectedItem().toString(),
                               mLength.getSelectedItem().toString(),
                               mEnjoy.getSelectedItem().toString());

                if(isInserted){
                    Toast.makeText(SurveyActivity.this, "Submission Successful", Toast.LENGTH_SHORT).show();
                    SurveyActivity.this.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.survey_layout, new NotificationsFragment(), "noteFrag")
                            .commit();
                }
            }
        });

    }
}
