package com.example.busbookingapp;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BusDetailsActivity extends AppCompatActivity {

    private Transaction transaction;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        dbHelper = new DatabaseHelper(this);

        // Get the Transaction object from the intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            transaction = getIntent().getSerializableExtra("transaction", Transaction.class);
        } else {
            transaction = (Transaction) getIntent().getSerializableExtra("transaction");
        }

        // Initialize TextViews
        TextView tvId = findViewById(R.id.tv_id);
        TextView tvFrom = findViewById(R.id.tv_from);
        TextView tvTo = findViewById(R.id.tv_to);
        TextView tvBusNo = findViewById(R.id.tv_bus_no);
        Button btnCancel = findViewById(R.id.btnCancel);

        // Set the text for each TextView
        if (transaction != null) {
            tvId.setText(getString(R.string.transaction_id, String.valueOf(transaction.getId())));
            tvFrom.setText(getString(R.string.from, transaction.getBusId()));
            tvTo.setText(getString(R.string.to, transaction.getSeats()));
            tvBusNo.setText(getString(R.string.bus_no, transaction.getIdNumber()));

            btnCancel.setOnClickListener(v -> cancelTransaction());
            
            if (transaction.isCancelled()) {
                btnCancel.setEnabled(false);
                btnCancel.setText("Ticket Cancelled");
            }
        } else {
            // Handle the case where transaction is null
            tvId.setText(R.string.no_transaction_data);
            tvFrom.setText(R.string.no_transaction_data);
            tvTo.setText(R.string.no_transaction_data);
            tvBusNo.setText(R.string.no_transaction_data);
            btnCancel.setEnabled(false);
        }
    }

    private void cancelTransaction() {
        if (transaction != null && !transaction.isCancelled()) {
            dbHelper.cancelTransaction(transaction.getId());
            transaction.setCancelled(true);
            
            // Update the UI to reflect the cancelled status
            Button btnCancel = findViewById(R.id.btnCancel);
            btnCancel.setEnabled(false);
            btnCancel.setText("Ticket Cancelled");
            
            Toast.makeText(this, "Ticket cancelled successfully", Toast.LENGTH_SHORT).show();
            
            // Optionally, you can finish the activity or update other UI elements
            // finish();
        }
    }
}
