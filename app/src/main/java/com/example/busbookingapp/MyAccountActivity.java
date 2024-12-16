package com.example.busbookingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyAccountActivity extends AppCompatActivity {

    private EditText etFullName, etPhone, etPassword;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        Button btnSaveChanges = findViewById(R.id.btnSaveChanges);

        db = new DatabaseHelper(this);

        // Fetch username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("BusBookingApp", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null); // Retrieve the stored username

        if (username != null) {
            User user = db.getUserByUsername(username); // Fetch user details using the username

            if (user != null) {
                // Populate the fields with existing user data
                etFullName.setText(user.getFullName());
                etPhone.setText(user.getPhoneNumber());
                etPassword.setText(user.getPassword());
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
        }

        // Handle save changes button click
        btnSaveChanges.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                Toast.makeText(MyAccountActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean updated = db.updateUser(username, password, phone, fullName); // Update user details
                if (updated) {
                    Toast.makeText(MyAccountActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to the previous activity (SettingsActivity)
                } else {
                    Toast.makeText(MyAccountActivity.this, "Failed to save changes", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
