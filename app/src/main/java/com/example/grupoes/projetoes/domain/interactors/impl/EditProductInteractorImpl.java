package com.example.grupoes.projetoes.domain.interactors.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditProductBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.EditPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.EditProductInteractor;
import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.request_handlers.PointOfSaleHandler;
import com.example.grupoes.projetoes.request_handlers.ProductHandler;

import org.json.JSONObject;

/**
 * Created by Wesley on 15/03/2017.
 */

public class EditProductInteractorImpl extends AbstractInteractor implements EditProductInteractor {
    private Callback callback;
    private EditProductBean body;

    public EditProductInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     Callback callback,
                                     EditProductBean body) {
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
                callback.onEditedProduct();
            }
        });
    }

    @Override
    public void run() {
        ProductHandler.getInstance().requestEditProduct(body,
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
                        notifyError("It was not possible to edit the product. Try again later.");
                    }
                });
    }
}
