package com.example.grupoes.projetoes.request_handlers;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.DeletePointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditUserBean;
import com.example.grupoes.projetoes.beans.LoginBean;
import com.example.grupoes.projetoes.beans.RegistrationBean;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.util.RequestActions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wesley on 22/02/2017.
 */
public class PointOfSaleHandler {
    private static PointOfSaleHandler instance;

    private Gson gsonBuilder;

    public static synchronized PointOfSaleHandler getInstance() {
        if (instance == null) {
            instance = new PointOfSaleHandler();
        }

        return instance;
    }

    private PointOfSaleHandler() {
        gsonBuilder = new Gson();
    }


    public void requestGetAll(final String token, Response.Listener<JSONArray> okResponse, Response.ErrorListener errorResponse) {
            JsonArrayRequest request = new JsonArrayRequest(RequestActions.GET_ALL_POINTS_OF_SALE.getRequestMethod(),
                    RequestActions.GET_ALL_POINTS_OF_SALE.getUrl(),
                    null,
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

        ISellHereApplication.getInstance().addToRequestQueue(request);
    }

    public void requestEdit(EditPointOfSaleBean bodyData, final String token, Response.Listener<JSONObject> okResponse, Response.ErrorListener errorResponse) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(RequestActions.EDIT_POINT_OF_SALE.getRequestMethod(),
                                                              RequestActions.EDIT_POINT_OF_SALE.getUrl(),
                                                              new JSONObject(gsonBuilder.toJson(bodyData)),
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
            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {

        }
    }

    public void requestDelete(DeletePointOfSaleBean bodyData, final String token, Response.Listener<JSONObject> okResponse, Response.ErrorListener errorResponse) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(RequestActions.DELETE_POINT_OF_SALE.getRequestMethod(),
                    RequestActions.DELETE_POINT_OF_SALE.getUrl(),
                    new JSONObject(gsonBuilder.toJson(bodyData)),
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

            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestAdd(AddPointOfSaleBean bodyData, final String token, Response.Listener<JSONObject> okResponse, Response.ErrorListener errorResponse) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(RequestActions.ADD_POINT_OF_SALE.getRequestMethod(),
                    RequestActions.ADD_POINT_OF_SALE.getUrl(),
                    new JSONObject(gsonBuilder.toJson(bodyData)),
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

            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }


}
