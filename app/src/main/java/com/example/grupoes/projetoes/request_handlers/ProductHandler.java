package com.example.grupoes.projetoes.request_handlers;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.AddProductBean;
import com.example.grupoes.projetoes.beans.DeletePointOfSaleBean;
import com.example.grupoes.projetoes.beans.DeleteProductBean;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditProductBean;
import com.example.grupoes.projetoes.beans.GetProductsInPointBean;
import com.example.grupoes.projetoes.util.CustomJsonArrayRequest;
import com.example.grupoes.projetoes.util.RequestActions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hadrizia on 28/02/2017.
 */

public class ProductHandler {
    private static ProductHandler instance;

    private Gson gsonBuilder;

    public static synchronized ProductHandler getInstance() {
        if (instance == null) {
            instance = new ProductHandler();
        }

        return instance;
    }

    private ProductHandler() {
        gsonBuilder = new Gson();
    }

    public void requestAddProduct(AddProductBean body, final String token, Response.Listener<JSONObject> okResponse, Response.ErrorListener errorResponse) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(RequestActions.ADD_PRODUCT.getRequestMethod(),
                    RequestActions.ADD_PRODUCT.getUrl(),
                    new JSONObject(createProductBody(body)),
                    okResponse,
                    errorResponse)
            {
                @Override
                public Map<String, String> getHeaders ()throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String auth = "Bearer " + token;
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            Log.d("ADD",""+ new JSONObject(createProductBody(body)).getString("productPrice"));
            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Map createProductBody(AddProductBean body) {
        Map<String, String> hash = new HashMap<>();
        hash.put("productImage", body.getProductImage());
        hash.put("productPrice", String.valueOf(body.getProductPrice()));
        hash.put("productName", body.getProductName());
        hash.put("productComment", body.getProductComment());
        hash.put("pointOfSale", body.getPointOfSale());
        hash.put("creator", body.getCreator());
        return  hash;
    }

    public void requestEditProduct(EditProductBean body, final String token, Response.Listener<JSONObject> okResponse, Response.ErrorListener errorResponse) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(RequestActions.EDIT_PRODUCT.getRequestMethod(),
                    RequestActions.EDIT_PRODUCT.getUrl(),
                    new JSONObject(gsonBuilder.toJson(body)),
                    okResponse,
                    errorResponse)
            {
                @Override
                public Map<String, String> getHeaders ()throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String auth = "Bearer " + token;
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            Log.d("EDIT", ""+new JSONObject(gsonBuilder.toJson(body)));
            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {

        }
    }

    public void requestDelete(DeleteProductBean body, final String token, Response.Listener<JSONObject> okResponse, Response.ErrorListener errorResponse) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(RequestActions.DELETE_PRODUCT.getRequestMethod(),
                    RequestActions.DELETE_PRODUCT.getUrl(),
                    new JSONObject(gsonBuilder.toJson(body)),
                    okResponse,
                    errorResponse)
            {
                @Override
                public Map<String, String> getHeaders ()throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String auth = "Bearer " + token;
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };

            Log.d("DELETE",""+new JSONObject(gsonBuilder.toJson(body)));
            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestGetProducts(String pointOfSale, final String token, Response.Listener<JSONArray> okResponse, Response.ErrorListener errorResponse) {
        GetProductsInPointBean body = new GetProductsInPointBean(pointOfSale);
        try {
            CustomJsonArrayRequest request = new CustomJsonArrayRequest(RequestActions.GET_PRODUCTS.getRequestMethod(),
                    RequestActions.GET_PRODUCTS.getUrl(),
                    new JSONObject(gsonBuilder.toJson(body)),
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
