package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.fragments.AddPointOfSaleFragment;
import com.example.grupoes.projetoes.fragments.SelectionMapFragment;
import com.example.grupoes.projetoes.fragments.ViewMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class NewPointOfSaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_point_of_sale);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select a location...");

        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fTransaction.addToBackStack(null);
        Fragment fragment = SelectionMapFragment.newInstance();
        fTransaction.replace(R.id.newpos_container, fragment);
        fTransaction.commit();
    }

    public void showAddPointOfSaleFragment(LatLng position) {

        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fTransaction.addToBackStack(null);
        Fragment fragment = AddPointOfSaleFragment.newInstance(position.longitude, position.latitude);
        fTransaction.replace(R.id.newpos_container, fragment);
        fTransaction.commit();
        getSupportActionBar().setTitle("Inform Details...");
    }
}
