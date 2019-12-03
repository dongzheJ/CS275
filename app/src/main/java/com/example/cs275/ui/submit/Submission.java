package com.example.cs275.ui.submit;

public class Submission {
    private int mId;
    private String mLocation;
    private String mMode;
    private String mLength;
    private String mEnjoyed;

    public Submission(int id, String location, String mode, String length, String enjoyed){
        mId = id;
        mLocation = location;
        mMode = mode;
        mLength = length;
        mEnjoyed = enjoyed;
    }

    public int getId(){
        return mId;
    }

    public String getLocation(){
        return mLocation;
    }

    public String getMode(){
        return mMode;
    }

    public String getLength(){
        return mLength;
    }

    public String getEnjoyed(){
        return mEnjoyed;
    }
}
