package com.example.hackgt;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CalendarEventDatabaseHelper extends SQLiteOpenHelper {
    // Define your table name and column names
    public static final String TABLE_NAME = "calendar_events";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EVENT_NAME = "event_name";
    public static final String COLUMN_EVENT_DATE = "event_date";
    // Add more columns as needed

    // Define your database name and version
    public static final String DATABASE_NAME = "CalendarEvents.db";
    public static final int DATABASE_VERSION = 1;

    public static final String COLUMN_MEDICINE_ID = "medicine_id";;
    // Define your table creation SQL statement
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MEDICINE_ID + " INTEGER, " + // Foreign key reference to medicine table
            COLUMN_EVENT_NAME + " TEXT, " +
            COLUMN_EVENT_DATE + " DATETIME, " +
            "FOREIGN KEY(" + COLUMN_MEDICINE_ID + ") REFERENCES " +
            MedicineDatabaseHelper.getTableName() + "(" + MedicineDatabaseHelper.getColumnId() + "));";

    public CalendarEventDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
    }
}
