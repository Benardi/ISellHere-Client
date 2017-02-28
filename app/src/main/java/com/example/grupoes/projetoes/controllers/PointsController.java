package com.example.grupoes.projetoes.controllers;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.DeletePointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.custom_callbacks.PointsOperationCallback;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.request_handlers.PointOfSaleHandler;
import com.example.grupoes.projetoes.util.UtilOperations;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 27/02/2017.
 */
public class PointsController {
    private static PointsController instance;

    private List<PointOfSale> pointsOfSale;

    public static PointsController getInstance() {
        if (instance == null) {
            instance = new PointsController();
        }

        return instance;
    }

    private PointsController() {

    }

    public void fetchPoints(Context context, final PointsOperationCallback callback) {
        pointsOfSale = new ArrayList<>();
        System.out.println("EXECUTOU1");
        PointOfSaleHandler.getInstance().requestGetAll(new SessionStorage(context).getToken(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            System.out.println("EXECUTOU2");
                            for (int i = 0; i < response.length(); i++) {
                                PointOfSale pointOfSale = convertResponse((JSONObject) response.get(i));
                                pointsOfSale.add(pointOfSale);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println("EXECUTOU3");
                        callback.done(pointsOfSale);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.done(pointsOfSale);
                    }

                });
    }

    public void fetchPoints(Context context) {
        fetchPoints(context, new PointsOperationCallback() {
                                @Override
                                public void done(List<PointOfSale> pointsOfSale) {

                                }
                            });
    }

    public PointOfSale findPointOfSaleByName(String name) {
        if (pointsOfSale != null) {
            for (PointOfSale pointOfSale : pointsOfSale) {
                if (pointOfSale.getName().equals(name)) {
                    return pointOfSale;
                }

            }
        }

        return null;
    }

    public void editPoint(final String pointName, String newName, String pointDescription, Bitmap pointImage, Context context, final PointsOperationCallback callback) {
        SessionStorage storage = new SessionStorage(context);
        String requester = storage.getLoggedUser().getUsername();
        String token = storage.getToken();

        String pointImageStr = UtilOperations.bitMapToString(pointImage);

        EditPointOfSaleBean bodyData = new EditPointOfSaleBean(requester, pointName, newName, pointDescription, pointImageStr);

        PointOfSaleHandler.getInstance().requestEdit(bodyData, token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PointOfSale pointOfSale = findPointOfSaleByName(pointName);
                            pointOfSale.setName(response.getString("name"));
                            pointOfSale.setComment(response.getString("comment"));
                            pointOfSale.setImage(UtilOperations.StringToBitMap(response.getString("image")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        callback.done(pointsOfSale);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.done(pointsOfSale);
                    }

                });
    }

    public void deletePoint(String pointName, Context context, final PointsOperationCallback callback) {
        SessionStorage storage = new SessionStorage(context);
        String username = storage.getLoggedUser().getUsername();
        String token = storage.getToken();

        DeletePointOfSaleBean bodyData = new DeletePointOfSaleBean(username, pointName);

        PointOfSaleHandler.getInstance().requestDelete(bodyData, token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            pointsOfSale.remove(findPointOfSaleByName(response.getString("name")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        callback.done(pointsOfSale);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.done(pointsOfSale);
                    }

                });
    }

    public List<PointOfSale> getPointsOfSale() {
        return pointsOfSale;
    }

    private PointOfSale convertResponse(JSONObject responseElement) throws JSONException {
        String creator = responseElement.getJSONObject("creator").getString("username");
        String name = responseElement.getString("name");
        String comment = responseElement.getString("comment");
        String image = responseElement.getString("image");
        double longitude = responseElement.getJSONObject("location").getDouble("longitude");
        double latitude = responseElement.getJSONObject("location").getDouble("latitude");
        Bitmap imageBitmap = UtilOperations.StringToBitMap(image);
        LatLng position = new LatLng(longitude, latitude);

        return new PointOfSale(creator, name, comment, imageBitmap, position);
    }
}
