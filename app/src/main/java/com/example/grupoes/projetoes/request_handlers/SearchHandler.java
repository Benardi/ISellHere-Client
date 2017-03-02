package com.example.grupoes.projetoes.request_handlers;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.DeletePointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.beans.SearchBean;
import com.example.grupoes.projetoes.util.CustomJsonArrayRequest;
import com.example.grupoes.projetoes.util.FrontendConstants;
import com.example.grupoes.projetoes.util.RequestActions;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wesley on 22/02/2017.
 */
public class SearchHandler {
    private static SearchHandler instance;

    private Gson gsonBuilder;

    public static synchronized SearchHandler getInstance() {
        if (instance == null) {
            instance = new SearchHandler();
        }

        return instance;
    }

    private SearchHandler() {
        gsonBuilder = new Gson();
    }


    public void requestSearchPointsOfSale(SearchBean bodyData, final String token, Response.Listener<JSONArray> okResponse, Response.ErrorListener errorResponse) {
        try {
            CustomJsonArrayRequest request = new CustomJsonArrayRequest(RequestActions.SEARCH_POINTS_OF_SALE.getRequestMethod(),
                    RequestActions.SEARCH_POINTS_OF_SALE.getUrl(),
                    new JSONObject(gsonBuilder.toJson(bodyData)),
                    okResponse,
                    errorResponse) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String auth = "Bearer " + token;
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };

            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestSearchProducts(SearchBean bodyData, final String token, Response.Listener<JSONArray> okResponse, Response.ErrorListener errorResponse) {
        try {
            CustomJsonArrayRequest request = new CustomJsonArrayRequest(RequestActions.SEARCH_PRODUCTS.getRequestMethod(),
                    RequestActions.SEARCH_PRODUCTS.getUrl(),
                    new JSONObject(gsonBuilder.toJson(bodyData)),
                    okResponse,
                    errorResponse) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String auth = "Bearer " + token;
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };

            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
