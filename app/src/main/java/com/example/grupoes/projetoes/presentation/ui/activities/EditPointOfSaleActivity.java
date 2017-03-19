package com.example.grupoes.projetoes.presentation.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.domain.repository.PointsRepository;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.presentation.presenters.api.EditPointPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InvalidInput;
import com.example.grupoes.projetoes.util.UtilOperations;

import java.util.List;

public class EditPointOfSaleActivity extends AppCompatActivity implements EditPointPresenter.View {
    private static final int RESULT_LOAD_IMAGE = 1;

    private EditText pSNameEditText;
    private EditText pSDescriptionEditText;

    private Button save;
    private TextView pSImage;

    private ImageView imageView;
    private Button close;

    private PointOfSale pointOfSale;

    private EditPointPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_point_of_sale);
        presenter = PresenterFactory.getInstance().createEditPointPresenter(this);
        pointOfSale = PointsRepository.getInstance().findPointOfSaleByName(getIntent().getExtras().getString("POINT_NAME"));

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
                String pointName = pointOfSale.getName();
                String newName = pSNameEditText.getText().toString();
                String requester =  new SessionStorage(ISellHereApplication.getInstance()).getLoggedUser().getUsername();
                String pointDescription = pSDescriptionEditText.getText().toString();
                String pointImageStr = UtilOperations.bitMapToString(((BitmapDrawable) imageView.getDrawable()).getBitmap());

                presenter.requestEditPoint(requester, pointName, newName, pointDescription, pointImageStr);
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

    @Override
    public void onSuccessfulEdit() {
        Intent i = new Intent(getApplicationContext(), ContentActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void onInvalidInput(List<InvalidInput> invalidInputs) {
        for (InvalidInput invalidInput : invalidInputs) {
            InputType type = invalidInput.getType();
            String error = invalidInput.getError();

            if (type == InputType.ADDPOINT_NAME) {
                pSNameEditText.setError(error);
            } else if (type == InputType.ADDPOINT_DESCRIPTION) {
                pSDescriptionEditText.setError(error);
            }
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
