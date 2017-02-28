package com.example.grupoes.projetoes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grupoes.projetoes.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    public static final String INVOKING_TYPE = "INVOKING_TYPE";
    public static final String NAME_PARAM = "NAME";
    public static final String DESCRIPTION_PARAM = "DESCRIPTION";

    private View parentView;
    private GoogleMap mMap;

    // Selection mode only.
    private Marker tempMarker;

    public static MapFragment newSelectInstance(String name, String description) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(NAME_PARAM, name);
        args.putString(DESCRIPTION_PARAM, description);
        args.putString(INVOKING_TYPE, "SELECT");
        fragment.setArguments(args);

        return fragment;
    }

    public static MapFragment newViewInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(INVOKING_TYPE, "VIEW");
        fragment.setArguments(args);
        return new MapFragment();
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFrag.getMapAsync(this);
        return parentView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapConfiguration();
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("Statue of Liberty").snippet("TOP"));
        System.out.println("poir diga");
    }

    private void mapConfiguration() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                System.out.println("EXECUTED CLICK LISTENER");
                moveSelectionMarker(latLng);
            }
        });
    }

    private void generateMarkers() {
        // Melhor que a requisição dos pontos de venda seja feita diretamente por um localstorage que irá
        // salvar os pontos. O método fetchLocations() será chamado aqui para pegar todos os pontos.
        // Esse método será chamado por onMapReady.
    }

    private void moveSelectionMarker(LatLng position) {
        if (tempMarker != null) {
            tempMarker.remove();
        }

        MarkerOptions options = new MarkerOptions();

        options
                .draggable(true)
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title(getArguments().getString(NAME_PARAM))
                .snippet(getArguments().getString(DESCRIPTION_PARAM));


        tempMarker = mMap.addMarker(options);
    }
}
