package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows user to scan in items, to be added to the database, by any generated QR code
 */
public class ScanInActivity extends AppCompatActivity
{
    private final int CAMERA_REQUEST_CODE = 101;
    private CodeScanner mCodeScanner;
    private DatabaseReference reff;


    @Override
    /**
     * Analyses the scanned QR code to see if it is one that was generated within the app. If not it will
     * let the user know that it did not recognize it
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_in);
        reff = FirebaseDatabase.getInstance().getReference().child("Items"); // reference to our database

        setupPermissions();  // sets permission to use camera

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback()
        {
            @Override
            public void onDecoded(@NonNull final Result result)  // "result" contains the string that the qr code represents
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    // this method decides what happens after the qr code is scanned
                    public void run()
                    {
                        // retrieve text from qr code, seperate each word by spliting at every comma (commas where added in the AddItem class)
                        String [] arr = result.getText().split(",");

                        // will attempt to read barcode as if it was generated from within the app
                        try {
                            final String name = arr[0];
                            String type = arr[1];
                            String brand = arr[2];
                            final String condition = arr[3];
                            final String quantity = arr[4];
                            String price = arr[5];
                            final String color = arr[6];
                            String comments = arr[7];
                            Toast.makeText(ScanInActivity.this, name.toLowerCase() + " - " + color.toLowerCase(), Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct


                            final HashMap map = new HashMap();

                            // Updates the quantity of the product
                            // will traverse through the database until it reaches the right section (name>>color>>condition)
                            reff.child(name.toLowerCase()).child(color.toLowerCase()).child(condition.toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    // tries to retrieve info from database to update quantity (if it cant, it will inform user that the qr code is not in the system)
                                    try {
                                        // retriving current quantity from database
                                        Map<String, String> someMap = (Map<String, String>) snapshot.getValue();
                                        String val = someMap.get("quantity");

                                        // updating the quantity (add original quantity by the new quantity)
                                        String value = String.valueOf(Integer.valueOf(val) + Integer.valueOf(quantity)); // adds the total inside database and new quantity (value in qr code)
                                        Toast.makeText(ScanInActivity.this, "Inventory: " + value, Toast.LENGTH_SHORT).show();  // displays toast of what the QR code represents
                                        map.put("quantity", value);
                                        // updating quantity in the correct location
                                        reff.child(name.toLowerCase()).child(color.toLowerCase()).child(condition.toLowerCase()).updateChildren(map);

                                    } catch (Exception e) {
                                        Toast.makeText(ScanInActivity.this, "QR code not found!", Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        // if its unable to read barcode it will inform the user that the QR code is not in the database
                        catch(Exception ee)
                        {
                            Toast.makeText(ScanInActivity.this, "QR code not found!", Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct
                            return;
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mCodeScanner.startPreview();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    /**
     * Sets up permission to access the camera
     */
    private void setupPermissions()
    {
        int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA} , CAMERA_REQUEST_CODE );
        }
    }

    @Override
    /**
     * Requests permission to access the camera
     * @param requestCode is the request code
     * @param permissions is a string that seeks to access the camera
     * @param grantResults is a request code
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Calls the Inventory class
     * @param obj is the button that was clicked
     */
    public void gotoInventory(View obj)
    {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

}