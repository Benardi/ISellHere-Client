package com.example.grupoes.projetoes.presentation.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.presentation.ui.activities.NewPointOfSaleActivity;
import com.example.grupoes.projetoes.controllers.PointsController;
import com.example.grupoes.projetoes.custom_callbacks.PointsOperationCallback;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class SelectionMapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;

    private FloatingActionButton confirmButton;

    private View parentView;
    private Marker positioningMarker;

    public static SelectionMapFragment newInstance() {
        SelectionMapFragment fragment = new SelectionMapFragment();
        return fragment;
    }

    public SelectionMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_selection_map, container, false);

        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFrag.getMapAsync(this);

        captureComponents();
        configureComponents();

        return parentView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(false);
        configureMap();
        LatLng coordinates = new LatLng(-7.23072, -35.8817);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(coordinates).zoom(15).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.moveCamera(cameraUpdate);
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        PointsController.getInstance().fetchPoints(getContext(), new PointsOperationCallback() {
            @Override
            public void done(List<PointOfSale> pointsOfSale) {
                generateMarkers(pointsOfSale);
            }
        });
    }

    public void generateMarkers(List<PointOfSale> pointsOfSale) {
        for (PointOfSale point : pointsOfSale) {
            map.addMarker(new MarkerOptions().position(point.getPosition()).title(point.getName()).snippet(point.getComment())).showInfoWindow();
        }
    }

    private void configureMap() {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                if (positioningMarker != null) {
                    positioningMarker.remove();
                    confirmButton.setAlpha(0.5F);
                }

                positioningMarker = null;

                return false;
            }

        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                if (positioningMarker != null) {
                    positioningMarker.remove();
                }

                MarkerOptions options = new MarkerOptions();

                options.draggable(true)
                       .position(latLng)
                       .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                positioningMarker = map.addMarker(options);
                confirmButton.setAlpha(1F);
            }

        });
    }

    private void captureComponents() {
        confirmButton = (FloatingActionButton) parentView.findViewById(R.id.selection_map_confirm_button);
    }

    private void configureComponents() {
        confirmButton.setAlpha(0.5F);

        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (positioningMarker != null) {
                    NewPointOfSaleActivity s = (NewPointOfSaleActivity) getActivity();
                    s.showAddPointOfSaleFragment(positioningMarker.getPosition());
                } else {
                    Toast.makeText(getContext(), "You should provide a location.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
