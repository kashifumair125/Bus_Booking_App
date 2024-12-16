package com.example.busbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvailableBusesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAdminBuses;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_buses); // Ensure this matches your layout file

        // Initialize RecyclerView
        recyclerViewAdminBuses = findViewById(R.id.recyclerViewAdminBuses); // Ensure the ID matches the XML
        recyclerViewAdminBuses.setLayoutManager(new LinearLayoutManager(this));

        // Initialize DatabaseHelper
        db = new DatabaseHelper(this);

        // Get the source and destination from intent
        String source = getIntent().getStringExtra("source");
        String destination = getIntent().getStringExtra("destination");

        // Load available buses
        loadAvailableBuses(source, destination);
    }

    private void loadAvailableBuses(String source, String destination) {
        List<Bus> busList = db.getAvailableBuses(source, destination);
        if (busList.isEmpty()) {
            Toast.makeText(this, "No buses available for the selected route.", Toast.LENGTH_SHORT).show();
        } else {
            BusAdapter busAdapter = new BusAdapter(busList); // Use the modified BusAdapter
            recyclerViewAdminBuses.setAdapter(busAdapter);

            // Set item click listener
            busAdapter.setOnItemClickListener(new BusAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Bus bus) {
                    // Start BusSelectionActivity and pass necessary data
                    Intent intent = new Intent(AvailableBusesActivity.this, BusSelectionActivity.class);
                    intent.putExtra("busName", bus.getBusName());
                    intent.putExtra("busType", bus.getBusType());
                    intent.putExtra("departureTime", bus.getDepartureTime());
                    intent.putExtra("arrivalTime", bus.getArrivalTime());
                    intent.putExtra("busId", bus.getBusId()); // Pass the bus ID
                    startActivity(intent);
                }
            });
        }
    }
}
