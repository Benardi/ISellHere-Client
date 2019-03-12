package com.example.grupoes.projetoes.presentation.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.domain.repository.ProductsRepository;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewProductPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.example.grupoes.projetoes.util.UtilOperations;

/**
 * Created by Hadrizia on 28/02/2017.
 */

public class ProductActivity extends AppCompatActivity implements ViewProductPresenter.View {
    Button editProduct;
    Button deleteProduct;
    ImageView productImage;
    TextView productName;
    TextView productDescription;
    TextView productPrice;

    private Product product;
    private ViewProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        PresenterFactory.getInstance().createViewProductPresenter(this);

        editProduct = (Button) findViewById(R.id.product_edit_button);
        deleteProduct = (Button) findViewById(R.id.post_delete_button);
        productImage = (ImageView) findViewById(R.id.product_imageview);
        productName = (TextView) findViewById(R.id.product_name_textview);
        productDescription = (TextView) findViewById(R.id.product_description_textview);
        productPrice = (TextView) findViewById(R.id.product_price_textview);

        Intent i = getIntent();
        final String productName = i.getExtras().getString("PRODUCT_NAME");
        final String pointName = i.getExtras().getString("POINT_NAME");

        product = ProductsRepository.getInstance().findProductByPointOfSale(pointName, productName);
        configureComponents();

        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductActivity.this, EditProductActivity.class);
                i.putExtra("PRODUCT_NAME", productName);
                i.putExtra("POINT_NAME", pointName);
                startActivity(i);
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Product - " + product.getProductName());

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
                builder.setMessage("Delete " + productName + " ?");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        presenter.requestDelete(productName);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
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
        finish();
    }

    @Override
    public String getPointName() {
        return null;
    }

    @Override
    public void onSuccessfulDeletion() {
        Intent i = new Intent(ProductActivity.this, PointOfSaleActivity.class);
        i.putExtra("POINT_NAME", product.getPointOfSale());
        startActivity(i);
        finish();
    }

    @Override
    public void setPresenter(ViewProductPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }
}
