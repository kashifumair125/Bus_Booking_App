package com.example.busbookingapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Iterator;

public class PreviousTransactionsActivity extends AppCompatActivity implements TransactionAdapter.OnItemClickListener {

    private DatabaseHelper dbHelper;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_transactions);

        dbHelper = new DatabaseHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        transactions = dbHelper.getAllTransactions();
        adapter = new TransactionAdapter(this, transactions, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onShowDetailsClick(int position) {
        Transaction transaction = transactions.get(position);
        Intent intent = new Intent(this, TicketDetailsActivity.class);
        intent.putExtra("transaction_id", transaction.getId());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                long cancelledTransactionId = data.getLongExtra("cancelled_transaction_id", -1);
                if (cancelledTransactionId != -1) {
                    removeTransaction(cancelledTransactionId);
                }
            }
        }
    }

    private void removeTransaction(long transactionId) {
        Iterator<Transaction> iterator = transactions.iterator();
        while (iterator.hasNext()) {
            Transaction transaction = iterator.next();
            if (transaction.getId() == transactionId) {
                iterator.remove();
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }
}
