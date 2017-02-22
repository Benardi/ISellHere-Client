package com.example.grupoes.projetoes.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.activities.ContentActivity;
import com.example.grupoes.projetoes.util.FrontendConstants;
import com.example.grupoes.projetoes.util.RequestActions;

import org.json.JSONException;
import org.json.JSONObject;

public class UserManagementService extends IntentService {
    private static UserManagementService INSTANCE;

   // private static String LOG_TAG = "BoundService";
   // private IBinder mBinder = new MyBinder();

    public UserManagementService() {

        super(UserManagementService.class.getName());
    }

   /* @Override
    public void onCreate() {
        super.onCreate();

        Log.v(LOG_TAG, "in onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");
        return super.onBind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
    }*/

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            System.out.println(action);
            if (RequestActions.LOGIN.getName().equals(action)) {
                final String username = intent.getStringExtra("username");
                final String password = intent.getStringExtra("password");
                handleLoginRequest(username, password);
            } else if (RequestActions.SIGNUP.getName().equals(action)) {
                final String username = intent.getStringExtra("username");
                final String password = intent.getStringExtra("password");
                final String email = intent.getStringExtra("email");
                handleSignupRequest(username, email, password);
            }
        }
    }

    public static synchronized UserManagementService getInstance() {
        if (UserManagementService.INSTANCE == null) {
            UserManagementService.INSTANCE = new UserManagementService();
        }

        return UserManagementService.INSTANCE;
    }

    public void startSignupRequest(Context context, String username, String email, String password) {
        System.out.println("CHEGOU");
        Intent intent = new Intent(context, UserManagementService.class);
        intent.setAction(RequestActions.SIGNUP.getName());
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        context.startService(intent);
    }

    public void startLoginRequest(Context context, String username, String password) {
        Intent intent = new Intent(context, UserManagementService.class);
        intent.setAction(RequestActions.SIGNUP.getName());
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        context.startService(intent);
    }

    private void handleSignupRequest(String username, String password, String email) {
        try {
            JSONObject requestBody = new JSONObject();

            requestBody.put("username", username);
            requestBody.put("password", password);
            requestBody.put("email", email);

            JsonObjectRequest request = new JsonObjectRequest(RequestActions.SIGNUP.getRequestMethod(),
                                                              RequestActions.SIGNUP.getUrl(),
                                                              requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("Signup Operation Completed!");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.getMessage());
                        }
                    });

            ISellHereApplication.getInstance().addToRequestQueue(request);
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleLoginRequest(String username, String password) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("username", username);
            requestBody.put("password", password);

            JsonObjectRequest request = new JsonObjectRequest(RequestActions.LOGIN.getRequestMethod(),
                                                              RequestActions.LOGIN.getUrl(),
                                                              requestBody,
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
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public class MyBinder extends Binder {
        UserManagementService getService() {
            return UserManagementService.this;
        }
    }
}
