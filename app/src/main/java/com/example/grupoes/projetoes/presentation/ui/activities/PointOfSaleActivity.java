package com.example.grupoes.projetoes.presentation.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.controllers.ProductController;
import com.example.grupoes.projetoes.domain.repository.PointsRepository;
import com.example.grupoes.projetoes.domain.repository.ProductsRepository;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewPointPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.example.grupoes.projetoes.util.ProductAdapter;

import java.util.List;

public class PointOfSaleActivity extends AppCompatActivity implements ViewPointPresenter.View {
    private TextView nameTextView;
    private TextView descriptionTextView;
    private Button editButton;
    private Button deleteButton;
    private Button addProduct;
    private ImageView imageView;

    private ViewPointPresenter presenter;
    private PointOfSale pointOfSale;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_of_sale);
        PresenterFactory.getInstance().createViewPointPresenter(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);

        captureComponents();
        configureComponents();
    }

    @Override
    protected void onResume() {
        pointOfSale = PointsRepository.getInstance().findPointOfSaleByName(getPointName());
        presenter.requestFetchProducts();
        getSupportActionBar().setTitle("Point of Sale - " + pointOfSale.getName());
        fillDetails();
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PointOfSaleActivity.this, ContentActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("POINT_NAME", pointOfSale.getName());
    }



    private void captureComponents() {
        editButton = (Button) findViewById(R.id.pos_edit_button);
        deleteButton = (Button) findViewById(R.id.post_delete_button);
        imageView = (ImageView) findViewById(R.id.pos_imageview);
        nameTextView = (TextView) findViewById(R.id.pos_name_textview);
        descriptionTextView = (TextView) findViewById(R.id.pos_description_textview);
        addProduct = (Button) findViewById(R.id.pos_add_product_button);
    }

    @Override
    public void setPresenter(ViewPointPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getPointName() {
        return getIntent().getExtras().getString("POINT_NAME");
    }

    @Override
    public void onSuccessfulDeletion() {
        Intent i = new Intent(PointOfSaleActivity.this, ContentActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onSucessfulProductsFetch() {
        this.products = ProductsRepository.getInstance().getProducts().get(pointOfSale.getName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        if (products.size() > 0){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.products_recyclerView);
            recyclerView.setLayoutManager(linearLayoutManager);
            ProductAdapter adapter = new ProductAdapter(PointOfSaleActivity.this, products);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

    private void configureComponents() {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PointOfSaleActivity.this);
                    builder.setMessage("Delete " + pointOfSale.getName() + "?");
                    builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            presenter.requestDelete(pointOfSale.getName());
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

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(PointOfSaleActivity.this, EditPointOfSaleActivity.class);
                    i.putExtra("POINT_NAME", getPointName());
                    startActivity(i);
                }
            });

            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(PointOfSaleActivity.this, AddProductActivity.class);
                    i.putExtra("POINT_NAME", getPointName());
                    startActivity(i);
                }
            });
    }

    private void fillDetails() {
        nameTextView.setText(pointOfSale.getName());
        descriptionTextView.setText(pointOfSale.getComment());
        imageView.setImageBitmap(pointOfSale.getImage());
    }
}