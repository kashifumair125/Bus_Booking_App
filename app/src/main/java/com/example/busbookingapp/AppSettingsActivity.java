package com.example.busbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AppSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings); // Ensure this matches your layout file

        // Initialize buttons
        Button btnChangeTheme = findViewById(R.id.btnChangeTheme);
        Button btnReportBug = findViewById(R.id.btnReportBug);
        Button btnClearHistory = findViewById(R.id.btnClearHistory);

        // Set onClickListeners for each button
        btnChangeTheme.setOnClickListener(v -> {
            Intent intent = new Intent(AppSettingsActivity.this, ChangeThemeActivity.class);
            startActivity(intent);
        });

        btnReportBug.setOnClickListener(v -> {
            Intent intent = new Intent(AppSettingsActivity.this, ReportBugActivity.class);
            startActivity(intent);
        });

        btnClearHistory.setOnClickListener(v -> {
            // Implement functionality to clear history
            clearSearchHistory();
        });
    }

    // Function to clear search history (implement as needed)
    private void clearSearchHistory() {
        // Logic to clear search history goes here
    }
}
