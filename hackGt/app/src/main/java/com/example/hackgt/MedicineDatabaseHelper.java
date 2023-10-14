package com.example.hackgt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicineDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MedicineDatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "medicine";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MEDICINE_NAME = "medicine_name";
    public static final String COLUMN_DOSAGE = "dosage";
    public static final String COLUMN_FREQUENCY = "frequency";
    public static final String COLUMN_TREATMENT_NAME = "treatment_name";

    public static final String COLUMN_USER_ID = "user_id";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " INTEGER, " + // Foreign key reference to users table
                    COLUMN_MEDICINE_NAME + " TEXT, " +
                    COLUMN_DOSAGE + " INTEGER, " +
                    COLUMN_FREQUENCY + " INTEGER, " +
                    COLUMN_TREATMENT_NAME + " TEXT, " +
                    "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " +
                    UserDataBaseHelper.getTableName() + "(" + UserDataBaseHelper.getColumnId() + "));";

    public MedicineDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
