package com.example.busbookingapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteBusActivity extends AppCompatActivity {

    private EditText etBusId;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bus);

        etBusId = findViewById(R.id.etBusId);
        Button btnDeleteBus = findViewById(R.id.btnDeleteBus);
        db = new DatabaseHelper(this);

        // Handle delete bus button click
        btnDeleteBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busId = etBusId.getText().toString().trim();

                if (TextUtils.isEmpty(busId)) {
                    Toast.makeText(DeleteBusActivity.this, "Please enter a valid Bus ID", Toast.LENGTH_SHORT).show();
                } else {
                    // Call the method to delete the bus from the database
                    boolean isDeleted = db.deleteBus(busId);
                    if (isDeleted) {
                        Toast.makeText(DeleteBusActivity.this, "Bus deleted successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DeleteBusActivity.this, "Bus not found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
