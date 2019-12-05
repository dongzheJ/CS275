package com.example.cs275;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.cs275.ui.submit.SurveyActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Executor;

//==================================================================================================

public class AlarmReceiver extends BroadcastReceiver {

    private FusedLocationProviderClient fusedLocationclient;
    private double mLat, mLon;
    private Context mContext;
    //==============================================================================================

    //Executes when alarm is triggered, even when app is open in background:
    @Override
    public void onReceive(final Context context, Intent arg1) {

        //TODO: Below code is for testing only: It just exists as a "flag" of sorts that shows up in the "Run" tab to indicate when this onReceive function executes:
        //TODO--------------------------------------------------------------------------------------
//        System.out.println("========= " + DateFormat.getDateTimeInstance().format(new Date()) + " =========");
//        System.out.println("1================================================TESTING================================================================");
//        System.out.println("2================================================TESTING================================================================");
//        System.out.println("3================================================TESTING================================================================");
//        System.out.println("4================================================TESTING================================================================");
//        System.out.println("5================================================TESTING================================================================");
//        System.out.println("6================================================TESTING================================================================");
//        System.out.println("7================================================TESTING================================================================");
//        System.out.println("8================================================TESTING================================================================");
//        System.out.println("9================================================TESTING================================================================");
        //TODO--------------------------------------------------------------------------------------

        fusedLocationclient = LocationServices.getFusedLocationProviderClient(context);


        fusedLocationclient.getLastLocation()
                .addOnSuccessListener(new MainActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            mLat = location.getLatitude();
                            mLon = location.getLongitude();
                            System.out.println(mLat);
                            System.out.println(mLon);
                            mContext = context;
                            setUpAlarm(mContext);


                        }else{
                            System.out.println("Is Null");
                        }
                    }
                });

//        if (checkLocationChange()) {
//            int NOTIFICATION_ID = 234;
//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            String CHANNEL_ID = "CS275Channel";
//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
////            String CHANNEL_ID = "CS275Channel";
//                CharSequence name = "CS275Channel";
//                String Description = "CS275Channel";
//                int importance = NotificationManager.IMPORTANCE_HIGH;
//                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//                mChannel.setDescription(Description);
//                mChannel.enableLights(true);
//                mChannel.setLightColor(Color.RED);
//                mChannel.enableVibration(true);
//                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//                mChannel.setShowBadge(true);
//                notificationManager.createNotificationChannel(mChannel);
//            }
//
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.map)
//                    .setContentTitle("TripTracker can see that you are traveling!")
//                    .setContentText(Double.toString(mLat) + ", " + Double.toString(mLon));
//
//            Intent resultIntent = new Intent(context, SurveyActivity.class);
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//            stackBuilder.addParentStack(MainActivity.class);
//            stackBuilder.addNextIntent(resultIntent);
//            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(resultPendingIntent);
//            notificationManager.notify(NOTIFICATION_ID, builder.build());
//        }
//
////        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();

    }

    //==============================================================================================

    //Checks if the user is currently traveling by comparing current location to home location:
    private boolean checkLocationChange() {
        //TODO: Insert location logic below to check user location:
        //TODO--------------------------------------------------------------------------------------

        return true;

        //TODO--------------------------------------------------------------------------------------
    }

    private void setUpAlarm(Context context){
        if (checkLocationChange()) {
            int NOTIFICATION_ID = 234;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            String CHANNEL_ID = "CS275Channel";

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            String CHANNEL_ID = "CS275Channel";
                CharSequence name = "CS275Channel";
                String Description = "CS275Channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription(Description);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setShowBadge(true);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.map)
                    .setContentTitle("TripTracker can see that you are traveling!")
                    .setContentText(Double.toString(mLat) + ", " + Double.toString(mLon));

            Intent resultIntent = new Intent(context, SurveyActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    //==============================================================================================

}