package com.example.grupoes.projetoes.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.activities.ContentActivity;
import com.example.grupoes.projetoes.util.FrontendConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UserManagementService extends IntentService {
    private static UserManagementService INSTANCE;

    private static final String LOGIN_REQUEST = "com.example.grupoes.projetoes.services.action.LOGIN_REQUEST";
    private static final String SIGNUP_REQUEST = "com.example.grupoes.projetoes.services.action.SIGNUP_REQUEST";

    public UserManagementService() {
        super(UserManagementService.class.getName());
    }

    public static synchronized UserManagementService getInstance() {
        if (UserManagementService.INSTANCE == null) {
            UserManagementService.INSTANCE = new UserManagementService();
        }

        return UserManagementService.INSTANCE;
    }

    public void startSignupRequest(Context context, String username, String email, String password) {
        Intent intent = new Intent(context, UserManagementService.class);
        intent.setAction(UserManagementService.SIGNUP_REQUEST);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        context.startService(intent);
    }

    public void startLoginRequest(Context context, String username, String password) {
        Intent intent = new Intent(context, UserManagementService.class);
        intent.setAction(UserManagementService.LOGIN_REQUEST);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (LOGIN_REQUEST.equals(action)) {
                final String username = intent.getStringExtra("username");
                final String password = intent.getStringExtra("password");
                handleLoginRequest(username, password);
            } else if (SIGNUP_REQUEST.equals(action)) {
                final String username = intent.getStringExtra("username");
                final String password = intent.getStringExtra("password");
                final String email = intent.getStringExtra("email");
                handleSignupRequest(username, email, password);
            }
        }
    }

    private void handleSignupRequest(String username, String password, String email) {
        JSONObject body = new JSONObject();

        try {
            body.put("username", username);
            body.put("password", password);
            body.put("email", email);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, FrontendConstants.SIGNUP_REQUEST_URL, body,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Hello");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            });

            ISellHereApplication.getInstance().addToRequestQueue(request);
    }

    private void handleLoginRequest(String username, String password) {
        JSONObject body = new JSONObject();

        try {
            body.put("username", username);
            body.put("password", password);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, FrontendConstants.LOGIN_REQUEST_URL, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent i = new Intent(getApplicationContext(), ContentActivity.class);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                });
        ISellHereApplication.getInstance().addToRequestQueue(request);
    }

    private void handleEditRequest(String oldPassword) {

    }

}
