package com.example.busbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminAvailableBusesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_available_buses); // Create a new layout for this activity

        // Initialize components
        recyclerView = findViewById(R.id.recyclerViewAdminBuses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);

        // Load all available buses
        loadAvailableBuses();
    }

    private void loadAvailableBuses() {
        List<Bus> busList = db.getAllBuses(); // Admin views all buses, not filtered by source or destination
        if (busList.isEmpty()) {
            Toast.makeText(this, "No buses available", Toast.LENGTH_SHORT).show();
        } else {
            BusAdapter busAdapter = new BusAdapter(busList);
            recyclerView.setAdapter(busAdapter);

            // Optionally, handle bus item clicks for further admin actions
        }
    }
}
