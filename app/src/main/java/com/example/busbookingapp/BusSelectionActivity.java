package com.example.busbookingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BusSelectionActivity extends AppCompatActivity {

    private EditText editTextSeats, editTextIdNumber;
    private RecyclerView recyclerView;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_selection);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bus Details");
        }

        // Initialize views
        TextView busName = findViewById(R.id.busName);
        TextView busId = findViewById(R.id.busId);
        TextView busDepartureTime = findViewById(R.id.busDepartureTime);
        TextView busArrivalTime = findViewById(R.id.busArrivalTime);
        Spinner spinnerBusType = findViewById(R.id.spinnerBusType);
        editTextSeats = findViewById(R.id.editTextSeats);
        Spinner spinnerIdProof = findViewById(R.id.spinnerIdProof);
        editTextIdNumber = findViewById(R.id.editTextIdNumber);
        Button btnBookNow = findViewById(R.id.btnBookNow);
        Button btnChart = findViewById(R.id.btnChart);

        // Get the bus details from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("busName");
        String id = intent.getStringExtra("busId");
        String departure = intent.getStringExtra("departureTime");
        String arrival = intent.getStringExtra("arrivalTime");

        // Set the bus details
        busName.setText(name);
        busId.setText("Bus ID: " + id);
        busDepartureTime.setText("Departure Time: " + departure);
        busArrivalTime.setText("Arrival Time: " + arrival);

        // Set up the bus type spinner
        String[] busTypes = {"AC", "Non-AC"}; // Sample data
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, busTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBusType.setAdapter(adapter);

        // Set up the ID proof spinner
        String[] idProofs = {"Passport", "Driver's License", "Aadhar Card"}; // Sample data
        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idProofs);
        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdProof.setAdapter(idAdapter);

        // Set up the Book Now button
        btnBookNow.setOnClickListener(v -> {
            String seats = editTextSeats.getText().toString().trim();
            String idNumber = editTextIdNumber.getText().toString().trim();

            if (seats.isEmpty() || idNumber.isEmpty()) {
                Toast.makeText(BusSelectionActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Proceed to the payment page or next step
                Intent bookingIntent = new Intent(BusSelectionActivity.this, BookingActivity.class);
                bookingIntent.putExtra("busId", id); // Pass Bus ID to Booking Activity
                bookingIntent.putExtra("seats", seats);
                bookingIntent.putExtra("idNumber", idNumber);
                startActivity(bookingIntent);
            }
        });

        // Set up the Chart button
        btnChart.setOnClickListener(v -> {
            // Show seating arrangement (implement your logic here)
            Toast.makeText(BusSelectionActivity.this, "Showing seating arrangement", Toast.LENGTH_SHORT).show();
            // Redirect to seating arrangement activity if needed
        });
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Go back to the previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
