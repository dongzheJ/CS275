package com.example.cs275;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

//==================================================================================================

public class AlarmReceiver extends BroadcastReceiver {

    //----------------------------------------------------------------------------------------------

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        //This code executes when alarm is triggered, even when app is open in background:

//        Toast.makeText(arg0, "I'm running", Toast.LENGTH_SHORT).show();

        System.out.println("========= " + DateFormat.getDateTimeInstance().format(new Date()) + " =========");
        System.out.println("1================================================TESTING================================================================");
        System.out.println("2================================================TESTING================================================================");
        System.out.println("3================================================TESTING================================================================");
        System.out.println("4================================================TESTING================================================================");
        System.out.println("5================================================TESTING================================================================");
        System.out.println("6================================================TESTING================================================================");
        System.out.println("7================================================TESTING================================================================");
        System.out.println("8================================================TESTING================================================================");
        System.out.println("9================================================TESTING================================================================");

    }

    //----------------------------------------------------------------------------------------------

}