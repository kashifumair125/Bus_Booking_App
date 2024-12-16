package com.example.busbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    private Spinner spinnerPaymentMethod;
    private EditText editTextPaymentId;
    private String busId, seats, idNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize views
        spinnerPaymentMethod = findViewById(R.id.spinnerPaymentMethod);
        editTextPaymentId = findViewById(R.id.editTextPaymentId);
        Button btnConfirmPayment = findViewById(R.id.btnConfirmPayment);

        // Get booking details from intent
        Intent intent = getIntent();
        busId = intent.getStringExtra("busId");
        seats = intent.getStringExtra("seats");
        idNumber = intent.getStringExtra("idNumber");

        // Setup payment method dropdown
        setupPaymentMethodSpinner();

        // Confirm payment button click listener
        btnConfirmPayment.setOnClickListener(v -> confirmPayment());
    }

    private void setupPaymentMethodSpinner() {
        String[] paymentMethods = {"Select Payment Method", "Credit/Debit Card", "UPI", "Net Banking"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentMethods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(adapter);
    }

    private void confirmPayment() {
        String selectedPaymentMethod = spinnerPaymentMethod.getSelectedItem().toString();
        String paymentId = editTextPaymentId.getText().toString().trim();

        if (selectedPaymentMethod.equals("Select Payment Method")) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(paymentId)) {
            Toast.makeText(this, "Please enter your payment ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulate payment processing
        boolean paymentSuccessful = processPayment();
        
        Intent intent = new Intent(BookingActivity.this, PaymentConfirmationActivity.class);
        intent.putExtra("payment_success", paymentSuccessful);
        intent.putExtra("busId", busId);
        intent.putExtra("seats", seats);
        intent.putExtra("idNumber", idNumber);
        intent.putExtra("paymentMethod", selectedPaymentMethod);
        intent.putExtra("paymentId", paymentId);
        startActivity(intent);
        finish(); // Close the booking activity
    }

    // Dummy method for payment processing
    private boolean processPayment() {
        // Simulate success or failure
        return true; // Return true for successful payment, false for failure
    }
}
