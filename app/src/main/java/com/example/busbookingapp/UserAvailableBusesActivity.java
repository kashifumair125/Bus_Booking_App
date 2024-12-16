package com.example.busbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAvailableBusesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUserBuses;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_available_buses);

        recyclerViewUserBuses = findViewById(R.id.recyclerViewUserBuses);
        recyclerViewUserBuses.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);

        // Get the source and destination from the intent
        String source = getIntent().getStringExtra("source");
        String destination = getIntent().getStringExtra("destination");

        loadAvailableBuses(source, destination);
    }

    private void loadAvailableBuses(String source, String destination) {
        List<Bus> busList = db.getAvailableBuses(source, destination);
        if (busList.isEmpty()) {
            Toast.makeText(this, "No buses available for the selected route.", Toast.LENGTH_SHORT).show();
        } else {
            BusAdapter busAdapter = new BusAdapter(busList);
            recyclerViewUserBuses.setAdapter(busAdapter);

            // Set click listener for the adapter
            busAdapter.setOnItemClickListener(bus -> {
                Intent intent = new Intent(UserAvailableBusesActivity.this, BusSelectionActivity.class);
                intent.putExtra("busName", bus.getBusName());
                intent.putExtra("busType", bus.getBusType());
                intent.putExtra("departureTime", bus.getDepartureTime());
                intent.putExtra("arrivalTime", bus.getArrivalTime());
                intent.putExtra("busId", bus.getBusId()); // Pass Bus ID
                startActivity(intent);
            });
        }
    }
}
