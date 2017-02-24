package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.grupoes.projetoes.R;

public class EditPointOfSaleActivity extends AppCompatActivity {

    private PointOfSaleActivity pS;

    private EditText pSNameEditText;
    private EditText pSEvaluationEditText;
    private EditText pSDescriptionEditText;

    private Button save;
    private Button pSImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_point_of_sale);

        getSupportActionBar().setTitle("Edit Point of Sale");

        pSNameEditText = (EditText) findViewById(R.id.editNamePSText);
        pSEvaluationEditText = (EditText) findViewById(R.id.editEvaluationPSText);
        pSDescriptionEditText = (EditText) findViewById(R.id.editDescripptionPSText);
        save = (Button) findViewById(R.id.saveButtonEditPS);
        pSImage = (Button) findViewById(R.id.editPSImageButton);
        pS = new PointOfSaleActivity();

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), PointOfSaleActivity.class);
                startActivity(i);
            }
        });

        pSImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

    }
}
