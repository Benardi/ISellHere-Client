package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grupoes.projetoes.R;

public class PointOfSaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_of_sale);

        getSupportActionBar().setTitle("Point of  Sale");

        /*editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditPointOfSaleActivity.class);
                startActivity(i);
            }
        });

        evaluationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EvaluationsActivity.class);
                startActivity(i);
            }
        });*/

    }

}
