package com.example.grupoes.projetoes.presentation.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.beans.AddProductBean;
import com.example.grupoes.projetoes.domain.repository.ProductsRepository;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.presentation.presenters.api.AddProductPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.example.grupoes.projetoes.request_handlers.ProductHandler;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InvalidInput;
import com.example.grupoes.projetoes.util.UtilOperations;

import org.json.JSONObject;

import java.util.List;

public class AddProductActivity extends AppCompatActivity implements AddProductPresenter.View {

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView productImage;
    private EditText productName;
    private EditText productDescription;
    private Button selectImage;
    private Button createPoduct;
    private EditText productPrice;
    private String pointOfSale;

    private AddProductPresenter presenter;
    private AddProductBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        PresenterFactory.getInstance().createAddProductPresenter(this);

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
                        SessionStorage storage =  new SessionStorage(ISellHereApplication.getInstance());

                        final String creatorName = storage.getLoggedUser().getUsername();
                        final String nameProduct = productName.getText().toString();
                        final String descriptionProduct = productDescription.getText().toString();
                        final String imageProduct = UtilOperations.bitMapToString(((BitmapDrawable)productImage.getDrawable()).getBitmap());

                        presenter.requestAddProduct(creatorName, pointOfSale, nameProduct, descriptionProduct, productPrice.getText().toString(), imageProduct);

                        /*ProductHandler.getInstance().requestAddProduct(productBean,
                                new SessionStorage(ISellHereApplication.getInstance()).getToken(),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        ProductsRepository.getInstance().getProducts().get(pointOfSale).add(new Product(creatorName, pointOfSale, nameProduct, descriptionProduct, priceProduct, imageProduct));
                                        Toast.makeText(getApplicationContext(), "You have successfully created a product.", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(AddProductActivity.this, ProductActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.putExtra("PRODUCT_NAME", productBean.getProductName());
                                        i.putExtra("POINT_NAME", productBean.getPointOfSale());
                                        startActivity(i);
                                        finish();
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
                        );*/

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

    @Override
    public void onSuccessfulAdd() {
        Toast.makeText(getApplicationContext(), "You have successfully created a product.", Toast.LENGTH_LONG).show();
        Intent i = new Intent(AddProductActivity.this, ProductActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("PRODUCT_NAME", productName.getText().toString());
        i.putExtra("POINT_NAME", pointOfSale);
        startActivity(i);
        finish();
    }

    @Override
    public void onInvalidInput(List<InvalidInput> invalidInputs) {
        for (InvalidInput invalidInput : invalidInputs) {
            InputType type = invalidInput.getType();
            String error = invalidInput.getError();

            if (type == InputType.ADDPRODUCT_NAME) {
                productName.setError(error);
            } else if (type == InputType.ADDPRODUCT_DESCRIPTION) {
                productDescription.setError(error);
            } else if (type == InputType.ADDPRODUCT_PRICE) {
                productPrice.setError(error);
            }
        }
    }

    @Override
    public void setPresenter(AddProductPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }
}
