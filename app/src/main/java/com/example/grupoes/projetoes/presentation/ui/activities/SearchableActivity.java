package com.example.grupoes.projetoes.presentation.ui.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.beans.SearchBean;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.request_handlers.SearchHandler;
import com.example.grupoes.projetoes.util.PointOfSaleAdapter;
import com.example.grupoes.projetoes.util.ProductSearchAdapter;
import com.example.grupoes.projetoes.util.UtilOperations;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*public class SearchableActivity extends AppCompatActivity {
    private RecyclerView pointsOfSaleRecyclerView;
    private RecyclerView productRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);

        pointsOfSaleRecyclerView = (RecyclerView) findViewById(R.id.searchable_recycler_view);
        pointsOfSaleRecyclerView.setHasFixedSize(true);

        productRecyclerView = (RecyclerView) findViewById(R.id.searchable_product_recycler_view);
        productRecyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pointsOfSaleRecyclerView.setLayoutManager(linearLayoutManager);

        Intent intent2 = getIntent();
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productRecyclerView.setLayoutManager(linearLayoutManager2);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle("Results for " + query);
            doSearch(query);
        }
    }

    public void doSearch(final String query) {
        SessionStorage storage = new SessionStorage(this);
        final List<PointOfSale> pointsOfSale = new ArrayList<>();

        SearchBean searchBean = new SearchBean(query, storage.getSessionLocation().latitude, storage.getSessionLocation().longitude, 100000);

        SearchHandler.getInstance().requestSearchPointsOfSale(searchBean,
                storage.getToken(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                PointOfSale pointOfSale = convertPointOfSaleResponse((JSONObject) response.get(i));
                                pointsOfSale.add(pointOfSale);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        PointOfSaleAdapter adapter = new PointOfSaleAdapter(SearchableActivity.this, pointsOfSale);
                        pointsOfSaleRecyclerView.setAdapter(adapter);

                        Log.d("SEARCH_INFO", "QUERY: " + query + "   " + "RESULT LIST SIZE: " + adapter.getItemCount() + "   " + "RESULT");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        final List<Product> products = new ArrayList<>();

        SearchHandler.getInstance().requestSearchProducts(searchBean,
                storage.getToken(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Product product = convertProductResponse((JSONObject) response.get(i));
                                products.add(product);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ProductSearchAdapter adapter = new ProductSearchAdapter(SearchableActivity.this, products);
                        productRecyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
    }

    private PointOfSale convertPointOfSaleResponse(JSONObject responseElement) throws JSONException {
        String creator = responseElement.getString("creator");
        String name = responseElement.getString("name");
        String comment = responseElement.getString("comment");
        String image = responseElement.getString("image");
        double longitude = responseElement.getJSONObject("location").getDouble("longitude");
        double latitude = responseElement.getJSONObject("location").getDouble("latitude");
        Bitmap imageBitmap = UtilOperations.StringToBitMap(image);
        LatLng position = new LatLng(longitude, latitude);

        return new PointOfSale(creator, name, comment, imageBitmap, position);
    }

    private Product convertProductResponse(JSONObject responseElement) throws JSONException {
        String creator = responseElement.getString("creator");
        String name = responseElement.getString("name");
        String pointOfSale = responseElement.getString("pointOfSale");
        String comment = responseElement.getString("comment");
        String image = responseElement.getString("image");
        double price = responseElement.getDouble("price");

        return new Product(creator, pointOfSale, name, comment, price, image);
    }*/

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("search", "search triggered");

        handleIntent(getIntent());
    }
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("search", "search triggered");
        setIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent)
    {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("search", query);
        }
    }

    @Override
    public boolean onSearchRequested() {

        Log.d("search", "search triggered");

        return false;  // don't go ahead and show the search box
    }*/
    public class SearchableActivity extends AppCompatActivity {
        private RecyclerView pointsOfSaleRecyclerView;
        private RecyclerView productRecyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_searchable);

            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
            setSupportActionBar(toolbar);

            pointsOfSaleRecyclerView = (RecyclerView) findViewById(R.id.searchable_recycler_view);
            pointsOfSaleRecyclerView.setHasFixedSize(true);

            productRecyclerView = (RecyclerView) findViewById(R.id.searchable_product_recycler_view);
            productRecyclerView.setHasFixedSize(true);

            Intent intent = getIntent();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            pointsOfSaleRecyclerView.setLayoutManager(linearLayoutManager);

            Intent intent2 = getIntent();
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            productRecyclerView.setLayoutManager(linearLayoutManager2);

            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                getSupportActionBar().setTitle("Results for " + query);
                doSearch(query);
            }
        }

        public void doSearch(final String query) {
            SessionStorage storage = new SessionStorage(this);
            final List<PointOfSale> pointsOfSale = new ArrayList<>();

            SearchBean searchBean = new SearchBean(query, storage.getSessionLocation().latitude, storage.getSessionLocation().longitude, 100000);

            SearchHandler.getInstance().requestSearchPointsOfSale(searchBean,
                    storage.getToken(),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    PointOfSale pointOfSale = convertPointOfSaleResponse((JSONObject) response.get(i));
                                    pointsOfSale.add(pointOfSale);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            PointOfSaleAdapter adapter = new PointOfSaleAdapter(SearchableActivity.this, pointsOfSale);
                            pointsOfSaleRecyclerView.setAdapter(adapter);

                            Log.d("SEARCH_INFO", "QUERY: " + query + "   " + "RESULT LIST SIZE: " + adapter.getItemCount() + "   " + "RESULT");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

            final List<Product> products = new ArrayList<>();

            SearchHandler.getInstance().requestSearchProducts(searchBean,
                    storage.getToken(),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    Product product = convertProductResponse((JSONObject) response.get(i));
                                    products.add(product);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("REQUEST_REPONSE", products.size() + "");
                            ProductSearchAdapter adapter = new ProductSearchAdapter(SearchableActivity.this, products);
                            productRecyclerView.setAdapter(adapter);

                            Log.d("SEARCH_INFO", "QUERY: " + query + "   " + "RESULT LIST SIZE: " + adapter.getItemCount() + "   " + "RESULT");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
        }

        private PointOfSale convertPointOfSaleResponse(JSONObject responseElement) throws JSONException {
            String creator = responseElement.getString("creator");
            String name = responseElement.getString("name");
            String comment = responseElement.getString("comment");
            String image = responseElement.getString("image");
            double longitude = responseElement.getJSONObject("location").getDouble("longitude");
            double latitude = responseElement.getJSONObject("location").getDouble("latitude");
            Bitmap imageBitmap = UtilOperations.StringToBitMap(image);
            LatLng position = new LatLng(longitude, latitude);

            return new PointOfSale(creator, name, comment, imageBitmap, position);
        }

        private Product convertProductResponse(JSONObject responseElement) throws JSONException {
            String creator = responseElement.getString("creator");
            String name = responseElement.getString("name");
            String pointOfSale = responseElement.getString("pointOfSale");
            String comment = responseElement.getString("comment");
            String image = responseElement.getString("image");
            double price = responseElement.getDouble("price");

            return new Product(creator, pointOfSale, name, comment, price, image);
        }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("search", "search triggered");
        handleIntent(getIntent());
    }
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("search", "search triggered");
        setIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent)
    {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("search", query);
        }
    }
    @Override
    public boolean onSearchRequested() {
        Log.d("search", "search triggered");
        return false;  // don't go ahead and show the search box
    }*/
    }
