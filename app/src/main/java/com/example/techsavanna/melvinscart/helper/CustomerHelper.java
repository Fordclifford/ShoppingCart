package com.example.techsavanna.melvinscart.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomerHelper  extends SQLiteOpenHelper

    {
        //Constants for Database name, table name, and column names
        public static final String DB_NAME = "MelvisDB";
        public static final String TABLE_NAME = "current";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FIRST_NAME = "firstname";
        public static final String COLUMN_LAST_NAME = "lastname";
        public static final String MAILINGCITY = "mailingcity";
        public static final String PHONE = "phone";
        public static final String OTHERSTREET= "otherstreet";

        //database version
        private static final int DB_VERSION = 1;

        //Constructor
    public CustomerHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

        //creating the database
        @Override
        public void onCreate(SQLiteDatabase db) {



        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME +
                " VARCHAR, " + COLUMN_LAST_NAME+
                " VARCHAR, " + MAILINGCITY+
                " VARCHAR, " + PHONE+
                " VARCHAR, " + OTHERSTREET+
                " TINYINT);";
        db.execSQL(sql);
    }

        //upgrading the database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Persons";
        db.execSQL(sql);
        onCreate(db);
    }

        /*
         * This method is taking two arguments
         * first one is the name that is to be saved
         * second one is the status
         * 0 means the name is synced with the server
         * 1 means the name is not synced with the server
         * */
        public boolean addName(String firstname, String lastname,String mailingcity,String phone,String otherstreet ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FIRST_NAME, firstname);
        contentValues.put(COLUMN_LAST_NAME, lastname);
        contentValues.put(MAILINGCITY, mailingcity);
        contentValues.put(PHONE, phone);
        contentValues.put(OTHERSTREET, otherstreet);
System.out.println("Data entered:"+contentValues);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

        /*
         * This method taking two arguments
         * first one is the id of the name for which
         * we have to update the sync status
         * and the second one is the status that will be changed
         * */
        public boolean updateNameStatus(int id,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OTHERSTREET, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

        /*
         * this method will give us all the name stored in sqlite
         * */
        public List<Current> getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        List<Current> posts = new ArrayList();
        if (c.moveToFirst()) {
           Current contact = new Current();
            contact.setFirstName(c.getString(1));
            contact.setLastName(c.getString(2));
            contact.setCity(c.getString(3));
            contact.setPhone(c.getString(4));
            contact.setStreet(c.getString(5));
            posts.add(contact);
            System.out.println("Fetch Data Contact: :" + contact);
        }

        return posts;
    }

        /*
         * this method is for getting all the unsynced name
         * so that we can sync it with database
         * */
        public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + OTHERSTREET + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
}
