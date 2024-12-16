package com.example.busbookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactions;
    private Context context;
    private OnItemClickListener listener;

    public TransactionAdapter(Context context, List<Transaction> transactions, OnItemClickListener listener) {
        this.context = context;
        this.transactions = transactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.tvTransactionId.setText("Transaction ID: " + transaction.getId());
        holder.tvBusId.setText("Bus ID: " + transaction.getBusId());
        holder.tvStatus.setText(transaction.isCancelled() ? "Cancelled" : "Active");
        holder.tvStatus.setVisibility(transaction.isCancelled() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTransactionId, tvBusId, tvStatus;
        Button btnShowDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransactionId = itemView.findViewById(R.id.tvTransactionId);
            tvBusId = itemView.findViewById(R.id.tvBusId);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnShowDetails = itemView.findViewById(R.id.btnShowDetails);

            btnShowDetails.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onShowDetailsClick(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onShowDetailsClick(int position);
    }
}
