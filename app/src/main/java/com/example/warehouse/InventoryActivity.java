package com.example.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.adapter.InventoryAdapter;
import com.example.warehouse.model.APIResponse;
import com.example.warehouse.model.Material;
import com.example.warehouse.net.MyApiEndpointInterface;
import com.example.warehouse.utils.Extension;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Displays all the items that are in the Database
 */
public class InventoryActivity extends AppCompatActivity {
    private static final String TAG = "InventoryActivity";

    private static int val = 0;
    private TextView text;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> finalList = new ArrayList<>();

    private DatabaseReference reff;
    private ListView theList;
    ArrayList<Material> items;
    MyApiEndpointInterface myApiEndpointInterface;

    private InventoryAdapter adapter;
    private RecyclerView rvInventory;


    @Override
    /**
     * Scans the entire database in order to output each item to the user
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        rvInventory = findViewById(R.id.rvInventory);

        rvInventory.setHasFixedSize(true);
        rvInventory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvInventory.setItemAnimator(null);

        myApiEndpointInterface = Extension.retrofit().create(MyApiEndpointInterface.class);
        theList = findViewById(R.id.theList);
        reff = FirebaseDatabase.getInstance().getReference().child("Items"); // reference to our database

        //the following retrieves "Items" children, which are the names of the items
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String name = ds.getKey();

                    //------------------------------------------------------------------------------------------------------------------------
                    // we are now referencing the child's child by its name (Ex: Items>>iphone 8)
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Items").child(name);
                    ValueEventListener eventListener2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds2 : snapshot.getChildren()) {
                                final String name2 = ds2.getKey();
                                //-----------------------------------------------------------------------------------------------------------
                                DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Items").child(name).child(name2);
                                ValueEventListener eventLister3 = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        for (DataSnapshot ds3 : snapshot2.getChildren()) {
                                            String name3 = ds3.getKey();
                                            // ----------------------------------------------------------------------------------------------
                                            DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Items").child(name).child(name2).child(name3);
                                            r.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot ds4 : snapshot.getChildren()) {
                                                        String details = ds4.getValue().toString();
                                                        //   Toast.makeText(Inventory.this,"|"+ details+"|", Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct

                                                        //  newList = new DateFormatSymbols().getMonths();
                                                        list.add(details);
                                                        checkList(list);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                }
                                            });
                                            // ----------------------------------------------------------------------------------------------
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                };
                                re.addListenerForSingleValueEvent(eventLister3);
                                //-------------------------------------------------------------------------------------------------------------
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    };
                    ref.addListenerForSingleValueEvent(eventListener2);
                    //---------------------------------------------------------------------------------------------------------------------------
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage()); //Don't ignore potential errors!
            }
        };
        reff.addListenerForSingleValueEvent(eventListener);
    }

    /**
     * Every single value from the database is passed through this method.
     * After every 8 elements, it combines them together as a single string.
     * Where one item is 8 elements. After being combined each item would have
     * a single string to be displayed. These strings are then placed into 'finalList'.
     *
     * @param arr arraylist containing specific properties for each item from the database
     */
    public void checkList(ArrayList<String> arr) {
        ArrayList<String> newArray = new ArrayList<>();
        String value = "";

        val++;
        if (val % 8 == 0) {
            for (int i = 0; i < 8; i++) {
                value = value + arr.get(i) + " \n";
            }
            arr.clear();
            finalList.add(value);
        }
    }

    /**
     * Called when the display button is clicked.
     * It adds the 'finalList' into the arrayAdpter in order for it to be placed within the ListView.
     *
     * @param obj
     */
    public void testing(View obj) {
        items = new ArrayList<>();
        myApiEndpointInterface.getMaterial().enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                for (int index = 0; index < response.body().getData().getMaterials().size(); index++) {
                    items = (ArrayList<Material>) response.body().getData().getMaterials();
                    Log.i(TAG, "onResponse: items " + items.get(index).getName());
                }
                adapter = new InventoryAdapter(InventoryActivity.this, items, new InventoryAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, Material material) {
                        Intent detailIntent = new Intent(InventoryActivity.this, DetailActivity.class);
                        detailIntent.putExtra("MATERIAL", material);
                        startActivity(detailIntent);
                    }
                });
                rvInventory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {

            }
        });
        Toast.makeText(InventoryActivity.this, "Items: " + finalList.size(), Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct\
        ArrayAdapter<String> arry = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, finalList);
        theList.setAdapter(arry);
    }

}