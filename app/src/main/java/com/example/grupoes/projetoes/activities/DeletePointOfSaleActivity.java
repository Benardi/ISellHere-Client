package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grupoes.projetoes.R;

public class DeletePointOfSaleActivity extends AppCompatActivity {

    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_point_of_sale);

        getSupportActionBar().setTitle("Delete Point of Sale");

        /*deleteButton = (Button) findViewById(R.id.deletePSButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });*/

    }
}
