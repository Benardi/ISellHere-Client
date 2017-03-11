package com.example.grupoes.projetoes.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.beans.AddProductBean;
import com.example.grupoes.projetoes.controllers.ProductController;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.request_handlers.ProductHandler;
import com.example.grupoes.projetoes.util.UtilOperations;

import org.json.JSONObject;

public class AddProductActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView productImage;
    EditText productName;
    EditText productDescription;
    Button selectImage;
    Button createPoduct;
    EditText productPrice;
    String pointOfSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Product");


        Intent intent = getIntent();
        pointOfSale = intent.getExtras().getString("POINT_NAME");
        Log.d("POINT_OF_SALE",pointOfSale);

        productName = (EditText) findViewById(R.id.add_product_name);
        productDescription = (EditText) findViewById(R.id.add_product_description);
        selectImage = (Button) findViewById(R.id.add_gallery_button);
        createPoduct = (Button) findViewById(R.id.create_product_button);
        productImage = (ImageView) findViewById(R.id.product_image);
        productPrice = (EditText) findViewById(R.id.add_product_price);

            selectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            });

            createPoduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(productName.getText().toString().equals("") || productDescription.getText().toString().equals("") || productPrice.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Field cannot be empty!", Toast.LENGTH_LONG).show();
                    }else{

                        SessionStorage storage = new SessionStorage(getApplicationContext());

                        final String creatorName = storage.getLoggedUser().getUsername();
                        final String nameProduct = productName.getText().toString();
                        final String descriptionProduct = productDescription.getText().toString();
                        final String imageProduct = UtilOperations.bitMapToString(((BitmapDrawable)productImage.getDrawable()).getBitmap());
                        final double priceProduct = Double.parseDouble(String.valueOf(productPrice.getText().toString()));

                        final AddProductBean productBean = new AddProductBean(creatorName, pointOfSale, nameProduct, descriptionProduct, String.valueOf(priceProduct), imageProduct);

                        ProductHandler.getInstance().requestAddProduct(productBean,
                                new SessionStorage(getApplicationContext()).getToken(),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        ProductController.getInstance().getProductsList().add(new Product(creatorName, pointOfSale, nameProduct, descriptionProduct, priceProduct, imageProduct));
                                        Toast.makeText(getApplicationContext(), "You have successfully created a product.", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), ProductActivity.class);
                                        i.putExtra("PRODUCT_NAME", productBean.getProductName());
                                        startActivity(i);

                                    }
                                },
                                new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        Log.d("ISELLHERE", ""+error.networkResponse);
                                        if(error.networkResponse != null && error.networkResponse.data != null){
                                            VolleyError newError = new VolleyError(new String(error.networkResponse.data));
                                            Log.d("ERROR_MESSAGE", ""+newError);
                                        }
                                    }
                                }
                        );
                    }
                }
            });

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ImageView imageView = (ImageView) findViewById(R.id.product_image);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }

        }
}
