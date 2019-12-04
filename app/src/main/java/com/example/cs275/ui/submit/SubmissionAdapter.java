package com.example.cs275.ui.submit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs275.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SubmissionAdapter extends RecyclerView.Adapter<SubmissionAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView locationTextView;
        public TextView modeTextView;
        public TextView lengthTextView;
        public TextView enjoyTextView;

        public ViewHolder(View itemView){
            super(itemView);

            locationTextView = itemView.findViewById(R.id.rv_location);
            modeTextView = itemView.findViewById(R.id.rv_mode);
            lengthTextView = itemView.findViewById(R.id.rv_length);
            enjoyTextView = itemView.findViewById(R.id.rv_enjoy);

        }
    }

    private List<Submission> mSubs;
    public List<Submission> mSubsCopy = new ArrayList<>();

    public SubmissionAdapter(ArrayList<Submission> subs) {
        mSubs = subs;
        mSubsCopy.addAll(mSubs);
    }

    @NonNull
    @Override
    public SubmissionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.survey_rv_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final SubmissionAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Submission subs = mSubs.get(position);

        // Set item views based on your views and data model
        final TextView textView1 = viewHolder.locationTextView;
        textView1.setText("Location: " + subs.getLocation());
        final TextView textView2 = viewHolder.modeTextView;
        textView2.setText("Mode: " + subs.getMode());
        final TextView textView3 = viewHolder.lengthTextView;
        textView3.setText("Length of Trip: " + subs.getLength());
        final TextView textView4 = viewHolder.enjoyTextView;
        textView4.setText("Did you Enjoy this Trip?: " + subs.getEnjoyed());
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                MovePage m = new MovePage();
//                Log.d("Word", "Up");
//                RecordsPage.idTemp = buf.getId();
//                RecordsPage.albumTemp = buf.getTitle();
//                RecordsPage.artistTemp = buf.getArtist();
//                RecordsPage.ratingTemp = buf.getRating();
//                RecordsPage.photoTemp = buf.getPhoto();
//                RecordsPage.genreTemp = buf.getGenre();
//                RecordsPage.descTemp = buf.getDesc();
//                m.moveActivity(viewHolder.itemView.getContext(), RecordsPage.class);
//            }
//        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mSubs.size();
    }

}