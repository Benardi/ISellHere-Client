package com.example.grupoes.projetoes.presentation.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.presentation.ui.fragments.AddPointOfSaleFragment;
import com.example.grupoes.projetoes.presentation.ui.fragments.SelectionMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class NewPointOfSaleActivity extends AppCompatActivity {
    private String state;
    private LatLng position;

    private Fragment fSelect;
    private Fragment fDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_point_of_sale);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Point - Select a location...");
        state = "SELECTLOCATION";
    }

    @Override
    protected void onResume() {
        super.onResume();
        showFragment(position);
    }

    public void showAddPointOfSaleFragment(LatLng position) {
        state = "DETAILS";
        showFragment(position);
    }

    private void showFragment(LatLng position) {
        if (state.equals("SELECTLOCATION")) {
            if (fSelect == null) {
                fSelect = SelectionMapFragment.newInstance();
            }

            FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
            fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fTransaction.addToBackStack(null);
            fTransaction.replace(R.id.newpos_container, fSelect);
            fTransaction.commit();

            this.position = null;
        } else if (state.equals("DETAILS")) {
            if (fDetails == null) {
                fDetails = AddPointOfSaleFragment.newInstance(position.longitude, position.latitude);
            }

            FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
            fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fTransaction.addToBackStack(null);
            fTransaction.replace(R.id.newpos_container, fDetails);
            fTransaction.commit();
            getSupportActionBar().setTitle("New Point - Details...");

            this.position = position;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(NewPointOfSaleActivity.this, ContentActivity.class);
        startActivity(i);
        finish();
    }
}
