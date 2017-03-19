package com.example.grupoes.projetoes.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.presentation.ui.activities.NewPointOfSaleActivity;
import com.example.grupoes.projetoes.presentation.ui.activities.PointOfSaleActivity;
import com.example.grupoes.projetoes.controllers.ProductController;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewMapFragmentPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ViewMapFragment extends Fragment implements OnMapReadyCallback, ViewMapFragmentPresenter.View {
    private FloatingActionButton newPointOfSaleButton;
    private SupportMapFragment mapFragment;

    private GoogleMap map;
    private View parentView;

    private ViewMapFragmentPresenter presenter;

    public ViewMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_view_map, container, false);
        PresenterFactory.getInstance().createViewMapFragmentPresenter(this);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        captureComponents();
        configureComponents();

        return parentView;
    }

    @Override
    public void onResume() {
        presenter.resume();
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(false);

        LatLng coordinates = new LatLng(-7.23072, -35.8817);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(coordinates).zoom(15).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        map.moveCamera(cameraUpdate);
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        configureMap();

        presenter.requestFetch();
    }

    @Override
    public void setPresenter(ViewMapFragmentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSuccessfulFetch(List<PointOfSale> pointsOfSale) {
        if (map != null) {
            for (PointOfSale point : pointsOfSale) {
                map.addMarker(new MarkerOptions().position(point.getPosition()).title(point.getName()).snippet(point.getComment()));
            }
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
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

    private void configureMap() {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                /* REVIEW001 */
                //ProductController.getInstance().getProducts(getActivity().getApplicationContext(), marker.getTitle());
                return false;
            }

        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i = new Intent(getContext(), PointOfSaleActivity.class);
                i.putExtra("POINT_NAME", marker.getTitle());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();
            }

        });
    }
}
