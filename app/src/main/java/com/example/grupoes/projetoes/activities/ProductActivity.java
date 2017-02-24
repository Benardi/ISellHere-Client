package com.example.grupoes.projetoes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.grupoes.projetoes.R;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getSupportActionBar().setTitle("Product");
    }
}
