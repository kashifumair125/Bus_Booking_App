package com.example.busbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        // Initialize buttons
        Button btnViewBuses = findViewById(R.id.btnViewBuses);
        Button btnAddBus = findViewById(R.id.btnAddBus);
        Button btnDeleteBus = findViewById(R.id.btnDeleteBus);

        // View Buses button click event - redirects to the new AdminAvailableBusesActivity
        btnViewBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPageActivity.this, AdminAvailableBusesActivity.class);
                startActivity(intent);
            }
        });

        // Add Bus button click event - redirects to AddBusActivity
        btnAddBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPageActivity.this, AddBusActivity.class);
                startActivity(intent);
            }
        });

        // Delete Bus button click event - redirects to DeleteBusActivity
        btnDeleteBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPageActivity.this, DeleteBusActivity.class);
                startActivity(intent);
            }
        });
    }
}
