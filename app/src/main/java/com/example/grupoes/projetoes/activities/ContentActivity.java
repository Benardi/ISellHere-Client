package com.example.grupoes.projetoes.activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.fragments.SettingsFragment;
import com.example.grupoes.projetoes.fragments.ViewMapFragment;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class ContentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private int selectedItem;

    private ListView resultListView;
    private NavigationView nView;

    private static final int REQUEST_LOCATION = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getBackground().setColorFilter(12333, PorterDuff.Mode.DARKEN);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        nView = (NavigationView) findViewById(R.id.nav_view);
        onNavigationItemSelected(nView.getMenu().getItem(0));
        nView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).addApi(LocationServices.API).addConnectionCallbacks(this).build();
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (selectedItem == R.id.nav_settings) {
            onNavigationItemSelected(nView.getMenu().getItem(0));
        } else {
            SessionStorage sessionStorage = new SessionStorage(this);
            sessionStorage.logoutUser();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.content, menu);

       /* SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);*/

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled (true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Log.d("ONSEARCHREQEUSTED", "SEARCH WAS REQUEST");
            onSearchRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fTransaction.addToBackStack(null);
        Fragment fragment = null;

        if (selectedItem != id && id == R.id.nav_map) {
            fragment = new ViewMapFragment();
            getSupportActionBar().setTitle("Sales Map");
        } else if (id == R.id.nav_settings) {
            //Intent i = new Intent(this, SettingsActivity.class);
            //startActivity(i);
            fragment = new SettingsFragment();
            getSupportActionBar().setTitle("Settings");
        } else if (id == R.id.nav_signout) {
            SessionStorage sessionStorage = new SessionStorage(this);
            sessionStorage.logoutUser();
            finish();
        }

        if (fragment != null) {
            fTransaction.replace(R.id.container, fragment);
            fTransaction.commit();
            selectedItem = id;
            nView.getMenu().getItem(0).setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        SessionStorage storage = new SessionStorage(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView usernameTextView = (TextView) hView.findViewById(R.id.navheader_username_textview);
        TextView emailTextView = (TextView) hView.findViewById(R.id.navheader_email_textview);

        System.out.println(storage.getLoggedUser());
        System.out.println(usernameTextView);

        usernameTextView.setText(storage.getLoggedUser().getUsername());
        emailTextView.setText(storage.getLoggedUser().getEmail());

        client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
            Log.d("HELLO123", "HELLO");
        }

        SessionStorage storage = new SessionStorage(this);
        storage.saveSessionLocation(LocationServices.FusedLocationApi.getLastLocation(client));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
