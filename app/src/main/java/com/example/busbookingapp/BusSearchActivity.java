package com.example.busbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class BusSearchActivity extends AppCompatActivity {

    private Spinner spinnerSource, spinnerDestination;
    private EditText editTextBusId;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_search);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Search Buses");
        }

        // Initialize views
        spinnerSource = findViewById(R.id.spinnerSource);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        Button btnSearchBuses = findViewById(R.id.btnSearchBuses);
        Button btnSearchBusId = findViewById(R.id.btnSearchBusId);
        editTextBusId = findViewById(R.id.editTextBusId);

        db = new DatabaseHelper(this);

        // Load locations into the spinner from strings.xml
        loadLocations();

        // Search by source and destination
        btnSearchBuses.setOnClickListener(v -> {
            String source = spinnerSource.getSelectedItem().toString();
            String destination = spinnerDestination.getSelectedItem().toString();

            if (source.equals(destination)) {
                Toast.makeText(BusSearchActivity.this, "Source and destination cannot be the same", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(BusSearchActivity.this, UserAvailableBusesActivity.class);
                intent.putExtra("source", source);
                intent.putExtra("destination", destination);
                startActivity(intent);
            }
        });

        // Search by Bus ID
        btnSearchBusId.setOnClickListener(v -> {
            String busId = editTextBusId.getText().toString().trim();

            if (TextUtils.isEmpty(busId)) {
                Toast.makeText(BusSearchActivity.this, "Please enter a valid Bus ID", Toast.LENGTH_SHORT).show();
            } else {
                Bus bus = db.getBusById(busId);
                if (bus != null) {
                    Intent intent = new Intent(BusSearchActivity.this, BusSelectionActivity.class);
                    intent.putExtra("busName", bus.getBusName());
                    intent.putExtra("busType", bus.getBusType());
                    intent.putExtra("departureTime", bus.getDepartureTime());
                    intent.putExtra("arrivalTime", bus.getArrivalTime());
                    intent.putExtra("busId", busId);
                    startActivity(intent);
                } else {
                    Toast.makeText(BusSearchActivity.this, "Bus not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Load locations for the spinners from strings.xml
    private void loadLocations() {
        String[] locations = getResources().getStringArray(R.array.city_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(adapter);
        spinnerDestination.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bus_search_menu, menu); // Inflate the menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(BusSearchActivity.this, SettingsActivity.class);
            startActivity(intent); // Start the Settings Activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
