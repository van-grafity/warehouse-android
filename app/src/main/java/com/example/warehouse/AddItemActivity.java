package com.example.warehouse;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehouse.model.APIResponse;
import com.example.warehouse.model.Material;
import com.example.warehouse.net.MyApiEndpointInterface;
import com.example.warehouse.utils.Extension;
import com.google.firebase.database.DatabaseReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class takes specific properties of an item from the user. Then
 * it calls the Barcode class.
 */
public class AddItemActivity extends AppCompatActivity {

    public static final String MESSAGE = "Something"; // key used to pass data using 'intent extras'
    private String name = "";
    private String type = "";
    private String partNumber = "";
    private String condition = "";
    private String quantity = "";
    private String price = "";
    private String color = "";
    private String comments = "";
    Intent intent;
    private int id;

    private DatabaseReference reff;
    MyApiEndpointInterface myApiEndpointInterface;

    @Override
    /**
     * Creates the activity layout
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        myApiEndpointInterface = Extension.retrofit().create(MyApiEndpointInterface.class);
    }

    /**
     * Will open new activity when 'enter' button is clicked
     *
     * @param obj is the object clicked
     */
    public void clickEnter(View obj) {
//        reff = FirebaseDatabase.getInstance().getReference().child("Items"); // reference to our database

        name = ((TextView) findViewById(R.id.name_input)).getText().toString();
        partNumber = ((TextView) findViewById(R.id.brand_input)).getText().toString();
        quantity = ((TextView) findViewById(R.id.quantity_input)).getText().toString();
        price = ((TextView) findViewById(R.id.price_input)).getText().toString();
        color = ((TextView) findViewById(R.id.color_input)).getText().toString();
        comments = ((TextView) findViewById(R.id.comments_input)).getText().toString();

        myApiEndpointInterface.addMaterial(new Material(name, Integer.parseInt(type), partNumber, Integer.parseInt(condition), Integer.parseInt(quantity), Double.parseDouble(price), color, comments)).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                Toast.makeText(AddItemActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(AddItemActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT);
//
//                intent.putExtra("name", response.body().getData().getMaterials().get(0).getName());
//                intent.putExtra("type", response.body().getData().getMaterials().get(0).getType().getName());
//                intent.putExtra("partNumber",response.body().getData().getMaterials().get(0).getPartNumber());
//                intent.putExtra("condition", response.body().getData().getMaterials().get(0).getCondition().getName());
//                intent.putExtra("quantity", response.body().getData().getMaterials().get(0).getQuantity());
//                intent.putExtra("price", response.body().getData().getMaterials().get(0).getPrice());
//                intent.putExtra("color", response.body().getData().getMaterials().get(0).getColor());
//                intent.putExtra("comment", response.body().getData().getMaterials().get(0).getComment());
//                startActivity(intent);

            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {

            }
        });
        // if any option is blank then do it again
        if (name.equals("") || type.equals("") || partNumber.equals("") || condition.equals("") || quantity.equals("") || price.equals("") || color.equals("") || comments.equals("")) {
            TextView text = (TextView) findViewById(R.id.error_message);
            text.setText("Enter all Fields!");
            text.setTypeface(null, Typeface.BOLD);                 // makes text bold
            text.setTextColor(Color.parseColor("#FF0000")); // changes text color to red
        } else {


//            // Write a message to the database
//            // Creating a new new 'Item' object (will be placed into database)
//            Item newItem = new Item(name, type, brand, condition, quantity, price, color, comments);
//
//            // Adding 'newItem' - placed into new child(bracket) by it name
//            // The values are using toLowerCase(), this will help organize them in the database
//            reff.child(name.toLowerCase()).child(color.toLowerCase()).child(condition.toLowerCase()).setValue(newItem);
//            Intent intent = new Intent(this, BarcodeActivity.class);
//
//
//            // passing the item's name (as a intent extra bundle) so that it can be used to create a qr code
//            String message = name + "," + type + "," + brand+ ","+ condition+ ","+ quantity+","+ price +","+ color + "," + comments;
//            intent.putExtra(MESSAGE, message);
//            startActivity(intent);


            String message = name + "," + type + "," + partNumber + "," + condition + "," + quantity + "," + price + "," + color + "," + comments;
            intent = new Intent(AddItemActivity.this, BarcodeActivity.class);
            intent.putExtra(MESSAGE, message);
            startActivity(intent);
        }
    }

    /**
     * Sets the type, based on the button clicked by user
     *
     * @param obj is the button that was clicked
     */
    public void chooseType(View obj) {
        Button clickedButton = (Button) obj;
        if (clickedButton.getText().equals("Android")) {
            type = "1";
        }
        if (clickedButton.getText().equals("Iphone")) {
            type = "2";
        }
    }

    /**
     * Sets the condition, based on the button clicked by user
     *
     * @param obj is the button that was clicked
     */
    public void chooseCondition(View obj) {
        Button clickedButton = (Button) obj;
        if (clickedButton.getText().equals("New")) {
            condition = "1";
        }
        if (clickedButton.getText().equals("Used")) {
            condition = "2";
        }
    }

}