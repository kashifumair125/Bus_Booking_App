package com.example.busbookingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewBusesActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_buses); // Ensure this matches your layout file

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewBuses); // Ensure the ID matches the XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize DatabaseHelper
        DatabaseHelper db = new DatabaseHelper(this);

        // Fetch the list of buses from the database
        List<Bus> busList = db.getAllBuses();

        // Set up adapter for the RecyclerView
        BusAdapter busAdapter = new BusAdapter(busList);
        recyclerView.setAdapter(busAdapter);
    }
}
