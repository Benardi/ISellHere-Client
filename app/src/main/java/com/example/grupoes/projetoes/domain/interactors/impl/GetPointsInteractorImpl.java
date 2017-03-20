package com.example.grupoes.projetoes.domain.interactors.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.LoginBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.GetPointsInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.LoginInteractor;
import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;
import com.example.grupoes.projetoes.domain.repository.PointsRepository;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Session;
import com.example.grupoes.projetoes.request_handlers.PointOfSaleHandler;
import com.example.grupoes.projetoes.request_handlers.UserManagementHandler;
import com.example.grupoes.projetoes.util.UtilOperations;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 15/03/2017.
 */

public class GetPointsInteractorImpl extends AbstractInteractor implements GetPointsInteractor {
    private GetPointsInteractor.Callback callback;

    public GetPointsInteractorImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   Callback callback) {
        super(threadExecutor, mainThread);

        this.callback = callback;
    }

    private void notifyError(final String msg) {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onRetrievalFailed(msg);
            }
        });
    }

    private void postMessage(JSONArray response) {
        final List<PointOfSale> pointsOfSale = new ArrayList<>();

        try {

            for (int i = 0; i < response.length(); i++) {
                PointOfSale pointOfSale = convertResponse((JSONObject) response.get(i));
                pointsOfSale.add(pointOfSale);
            }

            PointsRepository.getInstance().setPoints(pointsOfSale);

            getMainThread().post(new Runnable() {
                @Override
                public void run() {
                    callback.onPointsRetrieved(pointsOfSale);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            notifyError("It is all wrong.");
        }

    }

    @Override
    public void run() {
        Log.d("RANGETPOINTS", "YES");
        PointOfSaleHandler.getInstance().requestGetAll(new SessionStorage(ISellHereApplication.getInstance()).getToken(),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                       postMessage(response);
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        notifyError("It was not possible to retrieve the points. Try again later.");
                    }

                });
    }

    private PointOfSale convertResponse(JSONObject responseElement) throws JSONException {
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
}
