package com.example.busbookingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentConfirmationActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        // Initialize views
        TextView textViewMessage = findViewById(R.id.textViewMessage);
        Button buttonBackToSearch = findViewById(R.id.buttonBackToSearch);

        // Get the payment result and booking details from intent
        Intent intent = getIntent();
        boolean paymentSuccess = intent.getBooleanExtra("payment_success", false);
        String busId = intent.getStringExtra("busId");
        String seats = intent.getStringExtra("seats");
        String idNumber = intent.getStringExtra("idNumber");
        String paymentMethod = intent.getStringExtra("paymentMethod");
        String paymentId = intent.getStringExtra("paymentId");

        // Set the message based on payment success
        if (paymentSuccess) {
            textViewMessage.setText("✅ Payment Successful!");
            // Save transaction to database or shared preferences
            saveTransaction(busId, seats, idNumber, paymentMethod, paymentId);
        } else {
            textViewMessage.setText("❌ Payment Failed. Please try again.");
        }

        // Handle back to search button click
        buttonBackToSearch.setOnClickListener(v -> {
            Intent backIntent = new Intent(PaymentConfirmationActivity.this, BusSearchActivity.class);
            backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(backIntent);
            finish();
        });
    }

    private void saveTransaction(String busId, String seats, String idNumber, String paymentMethod, String paymentId) {
        // Here you would typically save the transaction to a database
        // For this example, we'll use SharedPreferences
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.addTransaction(busId, seats, idNumber, paymentMethod, paymentId);
    }
}
