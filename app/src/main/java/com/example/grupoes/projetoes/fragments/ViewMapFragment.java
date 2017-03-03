package com.example.grupoes.projetoes.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.activities.NewPointOfSaleActivity;
import com.example.grupoes.projetoes.activities.PointOfSaleActivity;
import com.example.grupoes.projetoes.controllers.PointsController;
import com.example.grupoes.projetoes.controllers.ProductController;
import com.example.grupoes.projetoes.custom_callbacks.PointsOperationCallback;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.request_handlers.PointOfSaleHandler;
import com.example.grupoes.projetoes.util.UtilOperations;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

public class ViewMapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    private View parentView;

    private FloatingActionButton newPointOfSaleButton;


    public static ViewMapFragment newInstance() {
        return new ViewMapFragment();
    }

    public ViewMapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_view_map, container, false);
        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFrag.getMapAsync(this);

        captureComponents();
        configureComponents();

        return parentView;
    }

    @Override
    public void onResume() {
        PointsController.getInstance().fetchPoints(getContext(), new PointsOperationCallback() {
            @Override
            public void done(List<PointOfSale> pointsOfSale) {
                generateMarkers(pointsOfSale);
            }
        });
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(false);
        LatLng coordinates = new LatLng(-7.23072, -35.8817);
        map.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("Statue of Liberty").snippet("TOP"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(coordinates).zoom(15).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.moveCamera(cameraUpdate);
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        configureMap();
    }

    public void generateMarkers(List<PointOfSale> pointsOfSale) {
        for (PointOfSale point : pointsOfSale) {
            map.addMarker(new MarkerOptions().position(point.getPosition()).title(point.getName()).snippet(point.getComment()));
        }
    }

    private void configureMap() {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                ProductController.getInstance().getProducts(getActivity().getApplicationContext(), marker.getTitle());
                return false;
            }

        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i = new Intent(getContext(), PointOfSaleActivity.class);
                i.putExtra("POINT_NAME", marker.getTitle());
                startActivity(i);
            }

        });
    }

    private void captureComponents() {
        newPointOfSaleButton = (FloatingActionButton) parentView.findViewById(R.id.view_map_new_point_of_sale_floating_button);
    }

    private void configureComponents() {
        newPointOfSaleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewMapFragment.this.getContext(), NewPointOfSaleActivity.class);
                startActivity(intent);
            }

        });
    }

    /*public void fetchLocations() {
        if (pointsOfSale == null) {
            pointsOfSale = new ArrayList<>();
        }

        System.out.println("Response length: asdsads");

        PointOfSaleHandler.getInstance().requestGetAll(new SessionStorage(getContext()).getToken(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            System.out.println("Response length: " + response.length());

                            for (int i = 0; i < response.length(); i++) {
                                String creator = ((JSONObject) response.get(i)).getJSONObject("creator").getString("username");
                                String name = ((JSONObject) response.get(i)).getString("name");
                                String comment = ((JSONObject) response.get(i)).getString("comment");
                                String image = ((JSONObject) response.get(i)).getString("image");
                                double longitude = ((JSONObject) response.get(i)).getJSONObject("location").getDouble("longitude");
                                double latitude = ((JSONObject) response.get(i)).getJSONObject("location").getDouble("latitude");
                                Bitmap imageBitmap = UtilOperations.StringToBitMap(image);
                                LatLng position = new LatLng(longitude, latitude);

                                PointOfSale pointOfSale = new PointOfSale(creator, name, comment, imageBitmap, position);
                                pointsOfSale.add(pointOfSale);
                            }

                            generateMarkers();
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }

                });
    }*/
}
