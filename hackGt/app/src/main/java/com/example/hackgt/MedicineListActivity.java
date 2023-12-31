package com.example.hackgt;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class MedicineListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MedicineListAdapter adapter;
    private List<Medicine> medicineList;
    private MedicineDatabaseHelper medicineDbHelper;
    private String user; // Replace with the actual user's ID

    private Button btn;

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (user != null) {
//            outState.putString("SAVED_USER_ID", user);
//        }
//    }


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.medicine_list);



        if (savedInstanceState != null) {
            user = savedInstanceState.getString("SAVED_USER_ID");
        } else {
            user = getIntent().getStringExtra("user_id");
        }

// Continue with the rest of the code...


        Log.d("MedicineListActivity", "YOOO LOG CHECKER 1");
        // Initialize the database helper
        medicineDbHelper = new MedicineDatabaseHelper(this);

        Log.d("MedicineListActivity", "YOOO LOG CHECKER 2");
        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.medicineRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d("MedicineListActivity", "YOOO LOG CHECKER 3");

        // Query medicine data for the specific user
        //user = getIntent().getStringExtra("user_id"); // Replace with the actual user's ID
        Log.d("MedicineListActivity", "YOOO LOG CHECKER 4");
        medicineList = getMedicineDataForUser(user);
        Log.d("MedicineListActivity", "YOOO LOG CHECKER 5");
        Log.d("MedicineListActivity", String.valueOf(medicineList));
        Log.d("MedicineListActivity", "Medicine count: " + medicineList.size());
        Log.d("MedicineListActivity", "Fetching medicines for user ID: " + user);


        for (Medicine medicine : medicineList) {
            Log.d("MedicineListActivity", "Medicine ID: " + medicine.getId());
            Log.d("MedicineListActivity", "Medicine Name: " + medicine.getMedicineName());
            Log.d("MedicineListActivity", "Dosage: " + medicine.getDosage());
            Log.d("MedicineListActivity", "Frequency: " + medicine.getFrequency());
            Log.d("MedicineListActivity", "Treatment Name: " + medicine.getTreatmentName());
        }

        Log.d("MedicineListActivity", "YOOO LOG CHECKER 6");

        // Create and set the adapter for the RecyclerView
        adapter = new MedicineListAdapter(MedicineListActivity.this, medicineList);
        Log.d("MedicineListActivity", "YOOO LOG CHECKER 7");
        recyclerView.setAdapter(adapter);

        btn = findViewById(R.id.add_event_buttonBack);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicineListActivity.this, MainActivity.class);
                intent.putExtra("user_id", user);
                startActivity(intent);
            }
        });
    }

    private List<Medicine> getMedicineDataForUser(String userId) {
        List<Medicine> medicineList = new ArrayList<>();

        // Query the database for medicine data related to the user
        SQLiteDatabase db = medicineDbHelper.getReadableDatabase();
        String[] projection = {
                MedicineDatabaseHelper.COLUMN_ID,
                MedicineDatabaseHelper.COLUMN_MEDICINE_NAME,
                MedicineDatabaseHelper.COLUMN_DOSAGE,
                MedicineDatabaseHelper.COLUMN_FREQUENCY,
                MedicineDatabaseHelper.COLUMN_TREATMENT_NAME
        };
        String selection = MedicineDatabaseHelper.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        Cursor cursor = db.query(
                MedicineDatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(MedicineDatabaseHelper.COLUMN_ID);
            int medicineNameColumnIndex = cursor.getColumnIndex(MedicineDatabaseHelper.COLUMN_MEDICINE_NAME);
            int dosageColumnIndex = cursor.getColumnIndex(MedicineDatabaseHelper.COLUMN_DOSAGE);
            int frequencyColumnIndex = cursor.getColumnIndex(MedicineDatabaseHelper.COLUMN_FREQUENCY);
            int treatmentNameColumnIndex = cursor.getColumnIndex(MedicineDatabaseHelper.COLUMN_TREATMENT_NAME);

            do {
                int id = cursor.getInt(idColumnIndex);
                String medicineName = cursor.getString(medicineNameColumnIndex);
                String dosage = cursor.getString(dosageColumnIndex);
                String frequency = cursor.getString(frequencyColumnIndex);
                String treatmentName = cursor.getString(treatmentNameColumnIndex);

                // Create a Medicine object and add it to the list
                Medicine medicine = new Medicine(id, medicineName, dosage, frequency, treatmentName);
                medicineList.add(medicine);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();

        }

        return medicineList;
    }
}