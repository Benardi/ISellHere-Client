package com.example.grupoes.projetoes.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.activities.ContentActivity;
import com.example.grupoes.projetoes.activities.NewPointOfSaleActivity;
import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.request_handlers.PointOfSaleHandler;
import com.example.grupoes.projetoes.util.UtilOperations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddPointOfSaleFragment extends Fragment {
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

    @Override
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
    }

    private void configureComponents() {
        takePictureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (i.resolveActivity(AddPointOfSaleFragment.this.getContext().getPackageManager()) != null) {
                    startActivityForResult(i, REQUEST_CODE);
                }
            }

        });

        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    double latitude = getArguments().getDouble(LATITUDE_PARAM);
                    double longitude = getArguments().getDouble(LONGITUDE_PARAM);

                    SessionStorage storage = new SessionStorage(view.getContext());

                    String creatorName = storage.getLoggedUser().getUsername();
                    String pointName = nameEditText.getText().toString();
                    String pointComment = descriptionEditText.getText().toString();
                    double pointLatitude = Double.parseDouble(String.valueOf(latitude));
                    double pointLongitude = Double.parseDouble(String.valueOf(longitude));
                    String pointImage = UtilOperations.bitMapToString(((BitmapDrawable)imageImageView.getDrawable()).getBitmap());

                    AddPointOfSaleBean bodyData = new AddPointOfSaleBean(creatorName, pointName, pointLongitude, pointLatitude, pointComment, pointImage);
                    System.out.println("USUÁRO: " + creatorName);
                    PointOfSaleHandler.getInstance().requestAdd(bodyData,
                                                                new SessionStorage(AddPointOfSaleFragment.this.getContext()).getToken(),
                                                                new Response.Listener<JSONObject>() {
                                                                    @Override
                                                                    public void onResponse(JSONObject response) {
                                                                        Intent i = new Intent(AddPointOfSaleFragment.this.getContext(), ContentActivity.class);
                                                                        startActivity(i);
                                                                        Toast.makeText(AddPointOfSaleFragment.this.getContext(), "You have successfully created a point of sale.", Toast.LENGTH_LONG).show();
                                                                    }
                                                                },
                                                                new Response.ErrorListener() {

                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        Toast.makeText(AddPointOfSaleFragment.this.getContext(), "Something went wrong with your request.", Toast.LENGTH_LONG).show();
                                                                        error.printStackTrace();
                                                                    }
                                                            });

            }

        });
    }
}
