package com.example.grupoes.projetoes;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

/**
 * Created by Wesley on 19/02/2017.
 */

public class ISellHereApplication extends Application {
    private static ISellHereApplication instance;
    private RequestQueue requestQueue;
    private static final String TAG = "Default";

    public ISellHereApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static synchronized ISellHereApplication getInstance() {
        if (instance == null) {
            instance = new ISellHereApplication();
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequest(String tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
