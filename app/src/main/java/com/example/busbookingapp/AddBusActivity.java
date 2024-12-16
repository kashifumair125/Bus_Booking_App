package com.example.busbookingapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBusActivity extends AppCompatActivity {

    private EditText etBusId, etBusName, etBusType, etSource, etDestination, etDepartureTime, etArrivalTime;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        // Initialize views
        etBusId = findViewById(R.id.etBusId);
        etBusName = findViewById(R.id.etBusName);
        etBusType = findViewById(R.id.etBusType);
        etSource = findViewById(R.id.etSource);
        etDestination = findViewById(R.id.etDestination);
        etDepartureTime = findViewById(R.id.etDepartureTime);
        etArrivalTime = findViewById(R.id.etArrivalTime);
        Button btnAddBus = findViewById(R.id.btnAddBus);

        db = new DatabaseHelper(this);

        // Handle add bus button click
        btnAddBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busId = etBusId.getText().toString().trim(); // Get Bus ID
                String busName = etBusName.getText().toString().trim();
                String busType = etBusType.getText().toString().trim();
                String source = etSource.getText().toString().trim();
                String destination = etDestination.getText().toString().trim();
                String departureTime = etDepartureTime.getText().toString().trim();
                String arrivalTime = etArrivalTime.getText().toString().trim();

                // Validate inputs
                if (TextUtils.isEmpty(busId) || TextUtils.isEmpty(busName) || TextUtils.isEmpty(busType) ||
                        TextUtils.isEmpty(source) || TextUtils.isEmpty(destination) ||
                        TextUtils.isEmpty(departureTime) || TextUtils.isEmpty(arrivalTime)) {
                    Toast.makeText(AddBusActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add the bus to the database
                boolean isAdded = db.addBus(busId, busName, busType, source, destination, departureTime, arrivalTime); // Update method to include Bus ID
                if (isAdded) {
                    Toast.makeText(AddBusActivity.this, "Bus added successfully!", Toast.LENGTH_SHORT).show();
                    // Optionally, clear the input fields
                    clearInputs();
                } else {
                    Toast.makeText(AddBusActivity.this, "Error adding bus. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Clear input fields after adding a bus
    private void clearInputs() {
        etBusId.setText("");
        etBusName.setText("");
        etBusType.setText("");
        etSource.setText("");
        etDestination.setText("");
        etDepartureTime.setText("");
        etArrivalTime.setText("");
    }
}
