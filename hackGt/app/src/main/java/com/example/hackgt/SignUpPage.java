package com.example.hackgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpPage extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText userEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText repPasswordEditText = findViewById(R.id.repPasswordEditText);
        Button signupbtn = findViewById(R.id.signupButton);
        UserDataBaseHelper dbHelper = new UserDataBaseHelper(this);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString().trim();
                String username = userEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String rep = repPasswordEditText.getText().toString().trim();
                if (name.isEmpty() || username.isEmpty() || password.isEmpty() || rep.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();

                } else if (! password.equals(rep)) {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("username", username);
                    values.put("password", password);
                    values.put("name", name);

                    long newRowId = db.insert("users", null, values);
                    db.close();
                    startActivity(new Intent(SignUpPage.this, LoginPage.class));
                }
            }
        });
    }
}
