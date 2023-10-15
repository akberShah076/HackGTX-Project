package com.example.hackgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.GregorianCalendar;

//=================================================================================================
public class Calendar extends AppCompatActivity
        implements DatePicker.OnDateChangedListener,TimePicker.OnTimeChangedListener {
    //-------------------------------------------------------------------------------------------------
    private GregorianCalendar calendar;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView secondsText;

    private long medicineId; // To store the medicine_id passed from the previous activity

    private String user;


    private CalendarEventDatabaseHelper calendarEventDbHelper;
    //-------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        Intent intent = getIntent();
        medicineId = intent.getLongExtra("medicine_id", -1); // Default value -1 if not provided
        user = intent.getStringExtra("userId");


        datePicker = findViewById(R.id.date_picker);
        datePicker.init(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),this);
        timePicker = findViewById(R.id.time_picker);
        timePicker.setOnTimeChangedListener(this);
        calendar = new GregorianCalendar(datePicker.getYear(),datePicker.getMonth(),
                datePicker.getDayOfMonth(),timePicker.getHour(),timePicker.getMinute());
        secondsText = findViewById(R.id.unix_minutes);
        calendarEventDbHelper = new CalendarEventDatabaseHelper(this);
        setUNIXSeconds();

        Button addEventButton = findViewById(R.id.add_event_button);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventToDatabase();
                Intent intent = new Intent(Calendar.this, MainActivity.class);
                intent.putExtra("user_id", user);
                startActivity(intent);
            }
        });
    }
    //-------------------------------------------------------------------------------------------------
    public void onDateChanged(DatePicker view,int year,int monthOfYear,int dayOfMonth) {

        setUNIXSeconds();
    }
    //-------------------------------------------------------------------------------------------------
    public void onTimeChanged(TimePicker view,int hourOfDay,int minute) {

        setUNIXSeconds();
    }
    //-------------------------------------------------------------------------------------------------
    private void setUNIXSeconds() {

        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),
                timePicker.getHour(),timePicker.getMinute());
        secondsText.setText(String.format("%d seconds of UNIX",calendar.getTimeInMillis()/1000));
    }

    private void addEventToDatabase() {
        String eventName = "Pill time"; // Replace with the actual event name
        long eventTimestamp = calendar.getTimeInMillis() / 1000; // Convert to seconds

        // Create a ContentValues object to store event data
        ContentValues values = new ContentValues();
        values.put(CalendarEventDatabaseHelper.COLUMN_EVENT_NAME, eventName);
        values.put(CalendarEventDatabaseHelper.COLUMN_EVENT_DATE, eventTimestamp);
        values.put(CalendarEventDatabaseHelper.COLUMN_MEDICINE_ID, medicineId); // Associate event with medicine


        // Insert the event data into the database
        long newRowId = calendarEventDbHelper.getWritableDatabase().insert(CalendarEventDatabaseHelper.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Event added to the database!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add event to the database.", Toast.LENGTH_SHORT).show();
        }

        calendarEventDbHelper.close();
    }
//-------------------------------------------------------------------------------------------------
}
//=================================================================================================