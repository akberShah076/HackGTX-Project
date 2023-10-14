package com.example.hackgt;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivityForMedicine extends AppCompatActivity {
    private EditText editMedicineName;
    private EditText editDosage;
    private EditText editTreatment;
    private Button btnSubmit;
    private Button btnTestDatabase;
    private Spinner spinnerFrequency;

    private MedicineDatabaseHelper medicineDbHelper = new MedicineDatabaseHelper(MainActivityForMedicine.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicine);

        // Initialize UI elements
        editMedicineName = findViewById(R.id.editMedicineName);
        editDosage = findViewById(R.id.editDosage);
        editTreatment = findViewById(R.id.editTreatment);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initialize the Spinner and frequency options
        spinnerFrequency = findViewById(R.id.spinnerFrequency);

        // Set up a click listener for the submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String medicineName = editMedicineName.getText().toString();
                String dosage = editDosage.getText().toString();
                String frequency = spinnerFrequency.getSelectedItem().toString(); // Get selected frequency
                String treatment = editTreatment.getText().toString();
                String user = getIntent().getStringExtra("user_id");

                // Check if any field is empty
                if (medicineName.isEmpty() || dosage.isEmpty() || treatment.isEmpty()) {
                    Toast.makeText(MainActivityForMedicine.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Create an instance of the MedicineDatabaseHelper
                    SQLiteDatabase db = medicineDbHelper.getWritableDatabase();

                    // Insert the data into the MedicineDatabase
                    ContentValues values = new ContentValues();
                    values.put(MedicineDatabaseHelper.COLUMN_MEDICINE_NAME, medicineName);
                    values.put(MedicineDatabaseHelper.COLUMN_DOSAGE, dosage + " mg"); // Store dosage as "X mg"
                    values.put(MedicineDatabaseHelper.COLUMN_FREQUENCY, frequency); // Store selected frequency
                    values.put(MedicineDatabaseHelper.COLUMN_TREATMENT_NAME, treatment);
                    values.put(MedicineDatabaseHelper.COLUMN_USER_ID, user);

                    long newRowId = db.insert(MedicineDatabaseHelper.TABLE_NAME, null, values);

                    if (newRowId != -1) {
                        Toast.makeText(MainActivityForMedicine.this, "Medicine data inserted successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivityForMedicine.this, Calendar.class);
                        intent.putExtra("medicine_id", newRowId); // newRowId is the ID of the inserted medicine
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivityForMedicine.this, "Error inserting medicine data", Toast.LENGTH_SHORT).show();
                    }

                    db.close();
                }
            }
        });

    }


}
