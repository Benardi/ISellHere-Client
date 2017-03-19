package com.example.grupoes.projetoes.request_handlers;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.EditUserBean;
import com.example.grupoes.projetoes.beans.LoginBean;
import com.example.grupoes.projetoes.beans.RegistrationBean;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.util.RequestActions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wesley on 22/02/2017.
 */
public class UserManagementHandler {
    private static final int MY_SOCKET_TIMEOUT_MS = 50000;
    private static UserManagementHandler instance;

    private Gson gsonBuilder;

    public static synchronized UserManagementHandler getInstance() {
        if (instance == null) {
            instance = new UserManagementHandler();
        }

        return instance;
    }

    private UserManagementHandler() {
        gsonBuilder = new Gson();
    }

    public void requestSignup(RegistrationBean bodyData, Response.Listener<JSONObject> okResponse, Response.ErrorListener errorResponse) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(RequestActions.SIGNUP.getRequestMethod(),
                                                              RequestActions.SIGNUP.getUrl(),
                                                              new JSONObject(gsonBuilder.toJson(bodyData)),
                                                              okResponse,
                                                              errorResponse);

            request.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestLogin(LoginBean bodyData, Response.Listener<JSONObject> okResponse, Response.ErrorListener errorResponse) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(RequestActions.LOGIN.getRequestMethod(),
                                                              RequestActions.LOGIN.getUrl(),
                                                              new JSONObject(gsonBuilder.toJson(bodyData)),
                                                              okResponse,
                                                              errorResponse);
            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestEdit(EditUserBean bodyData, final  String token, Response.Listener<JSONObject> okResponse, Response.ErrorListener errorResponse) {
        System.out.println(gsonBuilder.toJson(bodyData) + "      asldkaskldjaskldjklasjdklasjdkljklssdadas");

        try {
            JsonObjectRequest request = new JsonObjectRequest(RequestActions.EDIT_USER.getRequestMethod(),
                    RequestActions.EDIT_USER.getUrl(),
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
