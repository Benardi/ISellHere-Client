package com.example.grupoes.projetoes.domain.interactors.impl;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.AddPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.EditPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.request_handlers.PointOfSaleHandler;

import org.json.JSONObject;

/**
 * Created by Wesley on 15/03/2017.
 */

public class EditPointInteractorImpl extends AbstractInteractor implements EditPointInteractor {
    private Callback callback;
    private EditPointOfSaleBean body;

    public EditPointInteractorImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   Callback callback,
                                   EditPointOfSaleBean body) {
        super(threadExecutor, mainThread);

        this.callback = callback;
        this.body = body;
    }

    private void notifyError(final String msg) {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onEditFailed(msg);
            }
        });
    }

    private void postMessage() {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onEditedPoint();
            }
        });
    }

    @Override
    public void run() {
        Log.d("BODYDATA", body.getRequester());
        Log.d("BODYDATA", body.getPointName());
        Log.d("BODYDATA", body.getSelectedPoint());
        PointOfSaleHandler.getInstance().requestEdit(body,
                new SessionStorage(ISellHereApplication.getInstance()).getToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        postMessage();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        notifyError("It is all wrong.");
                    }
                });
    }
}
