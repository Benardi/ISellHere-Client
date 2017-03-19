package com.example.grupoes.projetoes.presentation.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.presentation.ui.activities.ContentActivity;
import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.presentation.presenters.api.AddPointFragmentPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InvalidInput;
import com.example.grupoes.projetoes.util.UtilOperations;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddPointOfSaleFragment extends Fragment implements AddPointFragmentPresenter.View {
    private static final int RESULT_LOAD_IMAGE = 1;
    public static final String LATITUDE_PARAM = "LATITUDE";
    public static final String LONGITUDE_PARAM = "LONGITUDE";

    public static final int REQUEST_CODE = 1;

    private EditText nameEditText;
    private EditText descriptionEditText;

    private Button takePictureButton;
    private Button createButton;

    private ImageView imageImageView;

    private View view;

    private File imageFile;

    private AddPointFragmentPresenter presenter;

    public static AddPointOfSaleFragment newInstance(double latitude, double longitude) {
        AddPointOfSaleFragment fragment = new AddPointOfSaleFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(LATITUDE_PARAM, latitude);
        bundle.putDouble(LONGITUDE_PARAM, longitude);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddPointOfSaleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_point_of_sale, container, false);
        PresenterFactory.getInstance().createAddPointFragmentPresenter(this);

        captureComponents();
        configureComponents();

        return view;
    }

    private void captureComponents() {
        nameEditText = (EditText) view.findViewById(R.id.add_pos_name_edittext);
        descriptionEditText = (EditText) view.findViewById(R.id.add_pos_description_edittext);

        takePictureButton = (Button) view.findViewById(R.id.add_pos_takepicture_button);
        createButton = (Button) view.findViewById(R.id.add_pos_create_button);
        takePictureButton =  (Button) view.findViewById(R.id.add_pos_takepicture_button);

        imageImageView = (ImageView) view.findViewById(R.id.add_pos_image_imageview);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            switch(resultCode) {
                case Activity.RESULT_OK:
                    Bundle bundle = data.getExtras();

                    Bitmap bmp = (Bitmap) bundle.get("data");
                    imageImageView.setImageBitmap(bmp);
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) view.findViewById(R.id.add_pos_image_imageview);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

    }

    private void configureComponents() {
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        /*takePictureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (i.resolveActivity(AddPointOfSaleFragment.this.getContext().getPackageManager()) != null) {
                    startActivityForResult(i, REQUEST_CODE);
                }
            }

        });*/

        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    double latitude = getArguments().getDouble(LATITUDE_PARAM);
                    double longitude = getArguments().getDouble(LONGITUDE_PARAM);

                    SessionStorage storage = new SessionStorage(ISellHereApplication.getInstance());

                    String creatorName = storage.getLoggedUser().getUsername();
                    String pointName = nameEditText.getText().toString();
                    String pointComment = descriptionEditText.getText().toString();
                    double pointLatitude = Double.parseDouble(String.valueOf(latitude));
                    double pointLongitude = Double.parseDouble(String.valueOf(longitude));
                    String pointImage = UtilOperations.bitMapToString(((BitmapDrawable)imageImageView.getDrawable()).getBitmap());

                    presenter.requestAddPoint(creatorName, pointName, pointLongitude, pointLatitude, pointComment, pointImage);
            }

        });
    }

    @Override
    public void onSuccessfulAdd() {
        Intent i = new Intent(AddPointOfSaleFragment.this.getContext(), ContentActivity.class);
        startActivity(i);
        getActivity().finish();
        Toast.makeText(AddPointOfSaleFragment.this.getContext(), "You have successfully created a point of sale.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInvalidInput(List<InvalidInput> invalidInputs) {
        for (InvalidInput invalidInput : invalidInputs) {
            InputType type = invalidInput.getType();
            String error = invalidInput.getError();

            if (type == InputType.ADDPOINT_NAME) {
                nameEditText.setError(error);
            } else if (type == InputType.ADDPOINT_DESCRIPTION) {
                descriptionEditText.setError(error);
            }
        }
    }

    @Override
    public void setPresenter(AddPointFragmentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
