package com.example.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehouse.model.APIResponse;
import com.example.warehouse.model.Item;
import com.example.warehouse.model.Material;
import com.example.warehouse.net.MyApiEndpointInterface;
import com.example.warehouse.utils.Extension;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Contains essential details for a specific item
 */
public class DetailsActivity extends AppCompatActivity {
    Button button;
    EditText txtName, txtBrand, txtQuant, txtPrice, txtColor, txtCondition, txtComments;
    DatabaseReference database;
    ArrayList<Item> list;
    private int val = 0;
    MyApiEndpointInterface myApiEndpointInterface;

    /**
     * Initialize values, and add eventListeners for the activity's functionality
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        button = findViewById(R.id.homeBtn);
        txtName = findViewById(R.id.itemTxt);
        txtBrand = findViewById(R.id.brand_input);
        txtQuant = findViewById(R.id.quantity_input);
        txtPrice = findViewById(R.id.price_input);
        txtColor = findViewById(R.id.color_input);
        txtCondition = findViewById(R.id.condition);
        txtComments = findViewById(R.id.comments);

        myApiEndpointInterface = Extension.retrofit().create(MyApiEndpointInterface.class);
        /**
         * create an onClickListener for when the button is pressed to go back to the main menu
         */


        list = new ArrayList<>();
        final ArrayList<String> list1 = new ArrayList<>();

        // establish a connection to the database
        database = FirebaseDatabase.getInstance().getReference("Items");

        // get intent from MyAdapter class which holds the data for an item inside string key
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        database.orderByKey().equalTo(key);

        // split up the data
        String b[] = key.split(",");
        final String name = b[1];
        String brand = b[2];
        String quantity = b[3];
        String price = b[4];
        String color = b[5];
        String condition = b[6];
        String comments = b[7];

        // set the data to the corresponding textView box
        txtName.setText(name);
        txtBrand.setText(brand);
        txtQuant.setText(quantity);
        txtPrice.setText(price);
        txtColor.setText(color);
        txtCondition.setText(condition);
        txtComments.setText(comments);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApiEndpointInterface.updateMaterial(Integer.parseInt(b[0]), new Material(txtName.getText().toString(), 1, txtBrand.getText().toString(), 1, Integer.parseInt(txtQuant.getText().toString()), Double.parseDouble(txtPrice.getText().toString()), txtColor.getText().toString(), txtComments.getText().toString())).enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        Toast.makeText(DetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });

            }
        });

    }
}
