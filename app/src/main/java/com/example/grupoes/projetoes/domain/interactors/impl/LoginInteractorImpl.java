package com.example.grupoes.projetoes.domain.interactors.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.LoginBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.LoginInteractor;
import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.Session;
import com.example.grupoes.projetoes.request_handlers.UserManagementHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Wesley on 15/03/2017.
 */

public class LoginInteractorImpl extends AbstractInteractor implements LoginInteractor {
    private LoginInteractor.Callback callback;
    private LoginBean body;

    public LoginInteractorImpl(Executor threadExecutor,
                               MainThread mainThread,
                               Callback callback,
                               LoginBean body) {
        super(threadExecutor, mainThread);

        this.callback = callback;
        this.body = body;
    }

    private void notifyError(final String msg) {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onLoginFailed(msg);
            }
        });
    }

    private void postMessage(JSONObject response) {
        try {
            SessionStorage sessionStorage =  new SessionStorage(ISellHereApplication.getInstance());

            if (sessionStorage.isUserLoggedIn()) {
                sessionStorage.clearData();
            }

            String token = response.getString("token");
            String username = response.getJSONObject("user").getString("username");
            String password = "";
            String email = response.getJSONObject("user").getString("email");

            Session session = new Session(token, username, password, email);
            sessionStorage.createSession(session);

            getMainThread().post(new Runnable() {
                @Override
                public void run() {
                    callback.onLoginComplete();
                }
            });
        } catch(JSONException e) {
            e.printStackTrace();
            notifyError("It is all wrong");
        }

    }

    @Override
    public void run() {
        UserManagementHandler.getInstance().requestLogin(body,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                       postMessage(response);
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        notifyError("It is all wrong");
                    }

                });
    }
}
