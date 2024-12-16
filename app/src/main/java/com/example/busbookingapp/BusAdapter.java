package com.example.busbookingapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private final List<Bus> busList;
    private OnItemClickListener onItemClickListener;

    public BusAdapter(List<Bus> busList) {
        this.busList = busList;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_card, parent, false);
        return new BusViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        Bus bus = busList.get(position);
        holder.textViewBusName.setText(bus.getBusName());
        holder.textViewBusType.setText(bus.getBusType());
        holder.textViewDepartureTime.setText(bus.getDepartureTime());
        holder.textViewArrivalTime.setText(bus.getArrivalTime());
        holder.textViewBusId.setText("Bus ID: " + bus.getBusId());

        // Handle item clicks
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(bus);
            }
        });
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public static class BusViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBusName, textViewBusType, textViewDepartureTime, textViewArrivalTime, textViewBusId;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBusName = itemView.findViewById(R.id.textViewBusName);
            textViewBusType = itemView.findViewById(R.id.textViewBusType);
            textViewDepartureTime = itemView.findViewById(R.id.textViewDepartureTime);
            textViewArrivalTime = itemView.findViewById(R.id.textViewArrivalTime);
            textViewBusId = itemView.findViewById(R.id.textViewBusId);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Bus bus);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
