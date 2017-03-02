package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.controllers.PointsController;
import com.example.grupoes.projetoes.controllers.ProductController;
import com.example.grupoes.projetoes.custom_callbacks.PointsOperationCallback;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.util.ProductAdapter;
import java.util.ArrayList;
import java.util.List;

public class PointOfSaleActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView descriptionTextView;

    private Button editButton;
    private Button deleteButton;
    private Button addProduct;
    private ImageView imageView;

    private PointOfSale pointOfSale;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_of_sale);

        String pointName = getIntent().getExtras().getString("POINT_NAME");
        pointOfSale = PointsController.getInstance().findPointOfSaleByName(pointName);

        ProductController.getInstance().getProducts(getApplicationContext(), pointName);

        captureComponents();
        configureComponents();
    }

    private void captureComponents() {
        editButton = (Button) findViewById(R.id.pos_edit_button);
        deleteButton = (Button) findViewById(R.id.post_delete_button);
        imageView = (ImageView) findViewById(R.id.pos_imageview);
        nameTextView = (TextView) findViewById(R.id.pos_name_textview);
        descriptionTextView = (TextView) findViewById(R.id.pos_description_textview);
        addProduct = (Button) findViewById(R.id.pos_add_product_button);
    }

    private void configureComponents() {
        if(pointOfSale != null) {
            nameTextView.setText(pointOfSale.getName());
            descriptionTextView.setText(pointOfSale.getComment());
            imageView.setImageBitmap(pointOfSale.getImage());
            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    PointsController.getInstance().deletePoint(pointOfSale.getName(), PointOfSaleActivity.this, new PointsOperationCallback() {
                        @Override
                        public void done(List<PointOfSale> pointsOfSale) {
                            Intent i = new Intent(PointOfSaleActivity.this, ContentActivity.class);
                            startActivity(i);
                        }
                    });
                }

            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(PointOfSaleActivity.this, EditPointOfSaleActivity.class);
                    i.putExtra("POINT_NAME", pointOfSale.getName());
                    startActivity(i);
                }
            });

            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(PointOfSaleActivity.this,
                            AddProductActivity.class);
                    i.putExtra("POINT_NAME", pointOfSale.getName());
                    startActivity(i);
                }
            });
        }
    }

}
