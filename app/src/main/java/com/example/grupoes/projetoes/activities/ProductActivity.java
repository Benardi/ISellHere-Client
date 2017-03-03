package com.example.grupoes.projetoes.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.beans.EditProductBean;
import com.example.grupoes.projetoes.controllers.PointsController;
import com.example.grupoes.projetoes.controllers.ProductController;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.util.UtilOperations;

/**
 * Created by Hadrizia on 28/02/2017.
 */

public class ProductActivity extends AppCompatActivity {
    Button editProduct;
    Button deleteProduct;
    ImageView productImage;
    TextView productName;
    TextView productDescription;
    TextView productPrice;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        editProduct = (Button) findViewById(R.id.product_edit_button);
        deleteProduct = (Button) findViewById(R.id.post_delete_button);
        productImage = (ImageView) findViewById(R.id.product_imageview);
        productName = (TextView) findViewById(R.id.product_name_textview);
        productDescription = (TextView) findViewById(R.id.product_description_textview);
        productPrice = (TextView) findViewById(R.id.product_price_textview);

        Intent i = getIntent();
        final String productName = i.getExtras().getString("PRODUCT_NAME");
        product = ProductController.getInstance().findProductByName(productName);
        configureComponents();

        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditProductActivity.class);
                i.putExtra("PRODUCT_NAME", productName);
                startActivity(i);
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(product.getProductName());

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
                builder.setMessage("Delete " + productName + " ?");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        ProductController.getInstance().deleteProduct(productName, getApplicationContext());
                        Intent intent = new Intent(ProductActivity.this, PointOfSaleActivity.class);
                        intent.putExtra("POINT_NAME", product.getPointOfSale());
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void configureComponents() {
        if(product != null) {
            productName.setText(product.getProductName());
            productPrice.setText(String.valueOf(product.getProductPrice()));
            productDescription.setText(product.getProductComment());
            if(product.getProductImage() != null){
                productImage.setImageBitmap(UtilOperations.StringToBitMap(product.getProductImage()));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, PointOfSaleActivity.class);
                intent.putExtra("POINT_NAME", product.getPointOfSale());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProductActivity.this, PointOfSaleActivity.class);
        i.putExtra("POINT_NAME", product.getPointOfSale());
        startActivity(i);
    }
}
