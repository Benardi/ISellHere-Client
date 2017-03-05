package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.controllers.PointsController;
import com.example.grupoes.projetoes.custom_callbacks.PointsOperationCallback;
import com.example.grupoes.projetoes.models.PointOfSale;

import java.util.List;

public class EditPointOfSaleActivity extends AppCompatActivity {
    private EditText pSNameEditText;
    private EditText pSDescriptionEditText;

    private Button save;
    private Button pSImage;

    private ImageView imageView;

    private PointOfSale pointOfSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_point_of_sale);

        pointOfSale = PointsController.getInstance().findPointOfSaleByName(getIntent().getExtras().getString("POINT_NAME"));
        //getSupportActionBar().setTitle("Edit Point of Sale");

        pSNameEditText = (EditText) findViewById(R.id.editNamePSText);
        pSDescriptionEditText = (EditText) findViewById(R.id.editDescripptionPSText);
        save = (Button) findViewById(R.id.saveButtonEditPS);
        pSImage = (Button) findViewById(R.id.editPSImageButton);

        pSNameEditText.setText(pointOfSale.getName());
        pSDescriptionEditText.setText(pointOfSale.getComment());

        imageView = (ImageView) findViewById(R.id.edit_pos_image_view);
        imageView.setImageBitmap(pointOfSale.getImage());
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PointsController.getInstance().editPoint(pointOfSale.getName(),
                        pSNameEditText.getText().toString(),
                        pSDescriptionEditText.getText().toString(),
                        ((BitmapDrawable) imageView.getDrawable()).getBitmap(),
                        EditPointOfSaleActivity.this,
                        new PointsOperationCallback() {
                            @Override
                            public void done(List<PointOfSale> pointsOfSale) {
                                Intent i = new Intent(getApplicationContext(), PointOfSaleActivity.class);
                                i.putExtra("POINT_NAME", pointOfSale.getName());
                                startActivity(i);
                            }
                        });
            }
        });

        pSImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });


    }
}
