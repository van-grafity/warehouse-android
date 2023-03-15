package com.example.warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.warehouse.model.Material;
import com.google.gson.Gson;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private Material material;
    private Button savingImage;
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    private TextView tvName;
    private TextView tvType;
    private TextView tvPartNumber;
    private TextView tvCondition;
    private TextView tvQuantity;
    private TextView tvPrice;
    private TextView tvColor;
    private TextView tvComment;

    private String typeName;
    private String conditionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        savingImage = findViewById(R.id.saveImage);
        qrCodeIV = findViewById(R.id.idIVQrcode);
        tvName = findViewById(R.id.tvName);
        tvType = findViewById(R.id.tvType);
        tvPartNumber = findViewById(R.id.tvPartNumber);
        tvCondition = findViewById(R.id.tvCondition);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvPrice = findViewById(R.id.tvPrice);
        tvColor = findViewById(R.id.tvColor);
        tvComment = findViewById(R.id.tvComment);

        material = (Material) getIntent().getParcelableExtra("MATERIAL");
//        Log.i(TAG, "onCreate: typeee "+material.getType());

        tvName.setText(material.getName());
        tvType.setText(material.getType().getName());
        tvPartNumber.setText(material.getPartNumber());
        tvCondition.setText(material.getCondition().getName());
        tvQuantity.setText(String.valueOf(material.getQuantity()));
        tvPrice.setText(String.valueOf(material.getPrice()));
        tvColor.setText(material.getColor());
        tvComment.setText(material.getComment());

        String jsonInString = new Gson().toJson(material);
        JSONObject mJSONObject = null;
        try {
            mJSONObject = new JSONObject(jsonInString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onCreate: MATERIALX "+material.getName());
        //request permission to read and write to the gallery
        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // initiallizing point object.
        Point point = new Point();
        display.getSize(point);

        // getting width and height
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(jsonInString, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set up in a way that the it can be seen as an actual image
            qrCodeIV.setImageBitmap(bitmap);

        } catch (WriterException e)  {
            Log.e("Tag", e.toString());
        }

        // saves new barcode into the gallery/photos
        savingImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ImageView iv = (ImageView)findViewById(R.id.idIVQrcode);
                BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
                Bitmap newBitmap = draw.getBitmap();
                FileOutputStream outStream = null;
                File file = Environment.getExternalStorageDirectory();
                File a = new File(file.getAbsolutePath() + "/Pictures");
                a.mkdirs();

                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(a, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();

                    Intent intent2 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent2.setData(Uri.fromFile(outFile));
                    sendBroadcast(intent2);
                }catch (IOException e)
                {
                    Log.e("Tag", e.toString());
                }
            }
        });
    }

    public void clickMainMenu(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}