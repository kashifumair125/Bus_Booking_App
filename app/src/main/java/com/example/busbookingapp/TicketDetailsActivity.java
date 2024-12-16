package com.example.busbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.busbookingapp.DatabaseHelper;
import com.example.busbookingapp.Transaction;

public class TicketDetailsActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TextView tvTransactionId, tvBusId, tvSeats, tvIdNumber, tvPaymentMethod;
    private long transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        dbHelper = new DatabaseHelper(this);
        
        tvTransactionId = findViewById(R.id.tvTransactionId);
        tvBusId = findViewById(R.id.tvBusId);
        tvSeats = findViewById(R.id.tvSeats);
        tvIdNumber = findViewById(R.id.tvIdNumber);
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod);
        Button btnCancel = findViewById(R.id.btnCancel);

        transactionId = getIntent().getLongExtra("transaction_id", -1);
        if (transactionId != -1) {
            Transaction transaction = dbHelper.getTransactionById(transactionId);
            if (transaction != null) {
                displayTransactionDetails(transaction);
            } else {
                Toast.makeText(this, "Transaction not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Invalid transaction ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnCancel.setOnClickListener(v -> cancelTicket());
    }

    private void displayTransactionDetails(Transaction transaction) {
        tvTransactionId.setText("Transaction ID: " + transaction.getId());
        tvBusId.setText("Bus ID: " + transaction.getBusId());
        tvSeats.setText("Seats: " + transaction.getSeats());
        tvIdNumber.setText("ID Number: " + transaction.getIdNumber());
        tvPaymentMethod.setText("Payment Method: " + transaction.getPaymentMethod());
    }

    private void cancelTicket() {
        dbHelper.cancelTransaction(transactionId);
        Toast.makeText(this, "Ticket cancelled successfully", Toast.LENGTH_SHORT).show();
        
        Intent resultIntent = new Intent();
        resultIntent.putExtra("cancelled_transaction_id", transactionId);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
