package com.example.mylocationapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "location";
    private static final String TABLE_CONTACTS = "locationdata";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"+ KEY_LAT + " TEXT,"
                + KEY_LONGITUDE + " TEXT"+");";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addLocation(Location location) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, location.getName());
        values.put(KEY_LAT, ""+location.getLat());
        values.put(KEY_LONGITUDE, ""+location.getLng());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all contacts in a list view
    public List<Location> getAllLocation(String order) {
        List<Location> contactList = new ArrayList<Location>();
        // Select All Query
        String selectQuery;
        if(order.equals("DESC")){
            selectQuery    = "SELECT  * FROM " + TABLE_CONTACTS + " ORDER BY "+KEY_NAME+" DESC";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Location contact = new Location();
                    contact.set_id(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(1));
                    contact.setLat(cursor.getDouble(2));
                    contact.setLng(cursor.getDouble(3));
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
        }
         if (order.equals("ASC")){
            selectQuery   = "SELECT  * FROM " + TABLE_CONTACTS + " ORDER BY "+KEY_NAME;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Location contact = new Location();
                    contact.set_id(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(1));
                    contact.setLat(cursor.getDouble(2));
                    contact.setLng(cursor.getDouble(3));
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
        }


       /* SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Location contact = new Location();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setLat(cursor.getDouble(2));
                contact.setLng(cursor.getDouble(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }*/

        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateContact(Location contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_LAT, contact.getLat());
        values.put(KEY_LONGITUDE, contact.getLng());

        // Log.v("!!!!3", "" + contact.get_id() + contact.get_name() + contact.get_phone_number());
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.get_id())});
    }

    // Deleting single contact
    public void deleteContact(Location contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.get_id())});
        db.close();
    }
}
