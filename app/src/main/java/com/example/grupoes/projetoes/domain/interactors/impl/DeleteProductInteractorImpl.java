package com.example.grupoes.projetoes.domain.interactors.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.DeletePointOfSaleBean;
import com.example.grupoes.projetoes.beans.DeleteProductBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.DeletePointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.DeleteProductInteractor;
import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.request_handlers.PointOfSaleHandler;
import com.example.grupoes.projetoes.request_handlers.ProductHandler;

import org.json.JSONObject;

/**
 * Created by Wesley on 15/03/2017.
 */

public class DeleteProductInteractorImpl extends AbstractInteractor implements DeleteProductInteractor {
    private Callback callback;
    private DeleteProductBean body;

    public DeleteProductInteractorImpl(Executor threadExecutor,
                                       MainThread mainThread,
                                       Callback callback,
                                       DeleteProductBean body) {
        super(threadExecutor, mainThread);

        this.callback = callback;
        this.body = body;
    }

    private void notifyError(final String msg) {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onDeleteFailed(msg);
            }
        });
    }

    private void postMessage() {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onDeleteComplete();
            }
        });
    }

    @Override
    public void run() {
        ProductHandler.getInstance().requestDelete(body,
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
                        notifyError("It was not possible to delete the product. Try again later.");
                    }
                });
    }
}
