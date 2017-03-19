package com.example.grupoes.projetoes.domain.interactors.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.AddPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.request_handlers.PointOfSaleHandler;

import org.json.JSONObject;

/**
 * Created by Wesley on 15/03/2017.
 */

public class AddPointInteractorImpl extends AbstractInteractor implements AddPointInteractor {
    private AddPointInteractor.Callback callback;
    private AddPointOfSaleBean body;

    public AddPointInteractorImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  Callback callback,
                                  AddPointOfSaleBean body) {
        super(threadExecutor, mainThread);

        this.callback = callback;
        this.body = body;
    }

    private void notifyError(final String msg) {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onAddFailed(msg);
            }
        });
    }

    private void postMessage() {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onAddedPoint();
            }
        });
    }

    @Override
    public void run() {
        PointOfSaleHandler.getInstance().requestAdd(body,
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
