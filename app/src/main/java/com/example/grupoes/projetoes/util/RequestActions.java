package com.example.grupoes.projetoes.util;

import com.android.volley.Request;
import com.example.grupoes.projetoes.util.FrontendConstants;

/**
 * Created by Wesley on 21/02/2017.
 */

public enum RequestActions {
    LOGIN("login_request",
           FrontendConstants.LOGIN_REQUEST_URL,
           Request.Method.POST),
    SIGNUP("signup_request",
            FrontendConstants.SIGNUP_REQUEST_URL,
            Request.Method.POST);

    private String name;
    private String url;
    private int method;

    RequestActions(String name, String url, int method) {
        this.name = name;
        this.url = url;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getRequestMethod() {
        return method;
    }


}
