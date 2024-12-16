package com.example.busbookingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.busbookingapp.Transaction;

public class TransactionPagerAdapter extends RecyclerView.Adapter<TransactionPagerAdapter.TransactionViewHolder> {
    private final Context context;
    private final List<Transaction> transactions;

    public TransactionPagerAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTransactionId, tvBusId, tvStatus;
        private Button btnShowDetails;

        TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransactionId = itemView.findViewById(R.id.tvTransactionId);
            tvBusId = itemView.findViewById(R.id.tvBusId);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnShowDetails = itemView.findViewById(R.id.btnShowDetails);
        }

        void bind(Transaction transaction) {
            tvTransactionId.setText("Transaction ID: " + transaction.getId());
            tvBusId.setText("Bus ID: " + transaction.getBusId());
            tvStatus.setText(transaction.isCancelled() ? "Cancelled" : "Active");
            tvStatus.setVisibility(transaction.isCancelled() ? View.VISIBLE : View.GONE);

            btnShowDetails.setOnClickListener(v -> {
                Intent intent = new Intent(context, BusDetailsActivity.class);
                intent.putExtra("transaction", transaction);
                context.startActivity(intent);
            });
        }
    }
}
