package com.example.hackgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginbtn = findViewById(R.id.loginButton);
        EditText userEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button signupbtn = findViewById(R.id.signupButton);
        UserDataBaseHelper dbHelper = new UserDataBaseHelper(this);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (user.isEmpty() || password.isEmpty()) {
                    // Display an alert or toast message indicating that fields are empty.
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    String[] projection = { "username", "password" };
                    String selection = "username = ? AND password = ?";
                    String[] selectionArgs = { user, password };

                    Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);

                    if (cursor.getCount() > 0) {
                        // User found, login successful
                    } else {
                        // User not found, login failed
                    }
                    cursor.close();
                    db.close();
                    startActivity(new Intent(LoginPage.this, MainActivity.class));
                }
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, SignUpPage.class));
            }
        });
    }
}
