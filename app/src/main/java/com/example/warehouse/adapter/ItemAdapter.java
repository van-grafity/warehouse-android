package com.example.warehouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.DetailsActivity;
import com.example.warehouse.R;
import com.example.warehouse.model.Item;
import com.example.warehouse.model.Material;

import java.util.ArrayList;

/**
 * Acts an a "middle man" for the RecyclerView to be used
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    Context context;
    ArrayList<Material> list;

    /**
     * a constructor for MyAdpter class that will hold a class' context and Arraylist
     *
     * @param context the context of a class
     * @param list an ArryList that will be passed through
     */
    public ItemAdapter(Context context, ArrayList<Material> list) {
        this.context = context;
        this.list = list;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return MyViewHolder
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    /**
     * This class will be used to hold values retrieved from the database and will also have an onClickListener
     * to open a new class
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Material user = list.get(position);
        holder.name.setText(user.getName());
        holder.brand.setText(user.getPartNumber());
        holder.quantity.setText(String.valueOf(user.getQuantity()));

        // will the the data for an item as a string separated by ',' to be split later
        final String a =user.getId()+""+","+ user.getName() + "," + user.getPartNumber() + "," + user.getQuantity() + "," + user.getPrice() + "," + user.getColor() + "," + user.getCondition().getName()
                + "," + user.getComment();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, user.getId()+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailsActivity.class);
                // creates an extra intent that will be gotten from the Details class to get the data above
                intent.putExtra("key", a);
                context.startActivity(intent);

            }
        });

    }


    /**
     * will get an item count
     *
     * @return the size of the ArrayList list
     */
    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Initialize values
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, brand, quantity, price, color, condition, comments;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            brand = itemView.findViewById(R.id.brand);
            quantity = itemView.findViewById(R.id.quantity);

        }
    }

}
