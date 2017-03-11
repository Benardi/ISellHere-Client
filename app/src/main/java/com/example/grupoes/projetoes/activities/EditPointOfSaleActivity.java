package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.controllers.PointsController;
import com.example.grupoes.projetoes.custom_callbacks.PointsOperationCallback;
import com.example.grupoes.projetoes.models.PointOfSale;

import java.util.List;

public class EditPointOfSaleActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;

    private EditText pSNameEditText;
    private EditText pSDescriptionEditText;

    private Button save;
    private TextView pSImage;

    private ImageView imageView;
    private Button close;

    private PointOfSale pointOfSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_point_of_sale);

        pointOfSale = PointsController.getInstance().findPointOfSaleByName(getIntent().getExtras().getString("POINT_NAME"));
        //getSupportActionBar().setTitle("Edit Point of Sale");

        close = (Button) findViewById(R.id.close_edit_pointofsale);
        pSNameEditText = (EditText) findViewById(R.id.editNamePSText);
        pSDescriptionEditText = (EditText) findViewById(R.id.editDescripptionPSText);
        save = (Button) findViewById(R.id.saveButtonEditPS);
        pSImage = (TextView) findViewById(R.id.editPSImageButton);

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
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
