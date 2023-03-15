package com.example.warehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.model.Material;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {
    private ArrayList<Material> mItems;
    private Context mContext;
    private InventoryAdapter.OnItemClickListener onItemClickListener;

    public InventoryAdapter(Context context, ArrayList<Material> items, InventoryAdapter.OnItemClickListener listener) {
        this.mContext = context;
        this.mItems = items;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public InventoryAdapter.InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_inventory, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.InventoryViewHolder holder, int position) {
        Material material = mItems.get(position);
        holder.tvName.setText(material.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(view, holder.getLayoutPosition(), material);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, Material material);
    }

    public static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
