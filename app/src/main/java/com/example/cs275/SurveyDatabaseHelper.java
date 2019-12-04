package com.example.cs275;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SurveyDatabaseHelper extends SQLiteOpenHelper {
    //Records Database variables
    public static final String DATABASE_NAME = "Survey_Db";
    public static final String TABLE_NAME = "Survey_Results";

    //Fields for database
    public static final String ID = "ID";
    public static final String LOCATION = "LOCATION";
    public static final String MODE_OF_TRANSPORTATION = "MODE_OF_TRANSPORTATION";
    public static final String LENGTH = "LENGTH";
    public static final String ENJOY = "ENJOY";

    public SurveyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LOCATION TEXT, MODE_OF_TRANSPORTATION TEXT, LENGTH TEXT, " +
                "                                                ENJOY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String location, String mode, String length, String enjoy){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION, location);
        contentValues.put(MODE_OF_TRANSPORTATION, mode);
        contentValues.put(LENGTH, length);
        contentValues.put(ENJOY, enjoy);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Integer deleteData(String row) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id=?", new String[]{row});
    }

    public Integer deleteRecord(String row, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        do{
            if(id == cursor.getInt(0)){
                return db.delete(TABLE_NAME, "id=?", new String[]{row});
            }
        } while(cursor.moveToNext());
        return -1;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}