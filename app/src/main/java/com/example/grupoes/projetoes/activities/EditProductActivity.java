package com.example.grupoes.projetoes.activities;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.controllers.ProductController;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.util.UtilOperations;

public class EditProductActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView productImage;
    EditText productName;
    EditText productDescription;
    EditText productPrice;
    TextView buttonGetImageProduct;
    Button close;
    Button save;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        final Intent intent = getIntent();
        final String nameProduct = intent.getExtras().getString("PRODUCT_NAME");
        product = ProductController.getInstance().findProductByName(nameProduct);

        configureComponents();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

        buttonGetImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductController.getInstance().editProduct(
                        product.getProductName(),
                        productName.getText().toString(),
                        productDescription.getText().toString(), Double.valueOf(productPrice.getText().toString()),
                        ((BitmapDrawable) productImage.getDrawable()).getBitmap(),
                        getApplicationContext());

                Intent intent1 = new Intent(EditProductActivity.this, ProductActivity.class);
                intent1.putExtra("PRODUCT_NAME", product.getProductName());
                startActivity(intent1);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void configureComponents() {
        if(product != null){
            productName = (EditText) findViewById(R.id.editNameProductText);
            productImage = (ImageView) findViewById(R.id.editImageProductView);
            productDescription = (EditText) findViewById(R.id.editDescriptionProductText);
            productPrice = (EditText) findViewById(R.id.editPriceProductText);
            buttonGetImageProduct = (TextView) findViewById(R.id.editProductImagemButton);
            save = (Button) findViewById(R.id.saveButtonEditProduct);
            close = (Button) findViewById(R.id.close_edit_product);

            productName.setText(product.getProductName());
            productPrice.setText(String.valueOf(product.getProductPrice()));

            if(product.getProductImage() != null){
                productImage.setImageBitmap(UtilOperations.StringToBitMap(product.getProductImage()));
            }
            productDescription.setText(product.getProductComment());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA };

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(EditProductActivity.this, ProductActivity.class);
                intent.putExtra("PRODUCT_NAME", product.getProductName());
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(EditProductActivity.this, ProductActivity.class);
        i.putExtra("PRODUCT_NAME", product.getProductName());
        startActivity(i);
    }
}
