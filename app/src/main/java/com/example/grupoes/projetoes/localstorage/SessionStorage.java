package com.example.grupoes.projetoes.localstorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;

import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.presentation.ui.activities.LoginActivity;
import com.example.grupoes.projetoes.models.Session;
import com.example.grupoes.projetoes.models.User;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Wesley on 22/02/2017.
 */

public class SessionStorage {
    public static final String STORAGE_NAME = "sessionStorage";

    private SharedPreferences preferences;
    private Context context;

    public SessionStorage(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(STORAGE_NAME, 0);
    }

    public void createSession(Session session) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", session.getUsername());
        editor.putString("password", session.getPassword());
        editor.putString("email", session.getEmail());
        editor.putString("token", session.getToken());
        editor.putBoolean("isLogged", true);
        editor.commit();
    }

    public void saveSessionLocation(Location location) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("latitude", (float) location.getLatitude());
        editor.putFloat("longitude", (float) location.getLongitude());
        editor.commit();
    }

    public LatLng getSessionLocation() {
        return new LatLng(preferences.getFloat("latitude", 0F), preferences.getFloat("longitude", 0F));
    }

    public User getLoggedUser() {
        String username = preferences.getString("username", "");
        String password = preferences.getString("password", "");
        String email = preferences.getString("email", "");
        return new User(username, password, email);
    }

    public String getToken() {
        return preferences.getString("token", "");
    }

    public void clearData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return preferences.getBoolean("isLogged", false);
    }

    public void logoutUser(Context activity) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
    }
}
