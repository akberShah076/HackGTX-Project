package com.example.hackgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listbtn = findViewById(R.id.listButton);
        Button addbtn = findViewById(R.id.addButton);
        String userId = getIntent().getStringExtra("user_id");

        listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MedicineListActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivityForMedicine.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });
    }
}