package com.example.grupoes.projetoes.domain.interactors.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.beans.RegistrationBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.RegistrationInteractor;
import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;
import com.example.grupoes.projetoes.request_handlers.UserManagementHandler;

import org.json.JSONObject;

/**
 * Created by Wesley on 15/03/2017.
 */

public class RegistrationInteractorImpl extends AbstractInteractor implements RegistrationInteractor {
    private RegistrationInteractor.Callback callback;
    private RegistrationBean body;

    public RegistrationInteractorImpl(Executor threadExecutor,
                                      MainThread mainThread,
                                      RegistrationInteractor.Callback callback,
                                      RegistrationBean body) {
        super(threadExecutor, mainThread);

        this.callback = callback;
        this.body = body;
    }

    private void notifyError(final String msg) {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onRegistrationFailed(msg);
            }
        });
    }

    private void postMessage() {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onRegistrationComplete();
            }
        });
    }

    @Override
    public void run() {
        UserManagementHandler.getInstance().requestSignup(body,
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
                        notifyError("It was not possible to create the user. Try a different username and email.");
                    }

                });
    }
}
