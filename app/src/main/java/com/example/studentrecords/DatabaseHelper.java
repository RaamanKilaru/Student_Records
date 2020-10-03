package com.example.studentrecords;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "StudentRecord.db";
    public static final String TABLE_NAME = "student_record";
    public static final String COL0 = "ID";
    public static final String COL1 = "NAME";
    public static final String COL2 = "ROLL_NO";
    public static final String COL3 = "GENDER";
    public static final String COL4 = "QUALIFICATION";
    public static final String COL5 = "DOB";
    public static final String COL6 = "AGE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " TEXT," + COL2 + " TEXT," + COL3 + " TEXT," + COL4 + " TEXT," + COL5 + " TEXT," + COL6 + " INTEGER" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
    }

    public boolean addData(String name, String rollno, String gender, String qualification, String dob, int age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2, rollno);
        contentValues.put(COL3, gender);
        contentValues.put(COL4, qualification);
        contentValues.put(COL5, dob);
        contentValues.put(COL6, age);

        long result = db.insert(TABLE_NAME,null, contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }
}
