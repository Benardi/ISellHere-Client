package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.beans.LoginBean;
import com.example.grupoes.projetoes.models.Session;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.request_handlers.UserManagementHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private SessionStorage sessionStorage;

    private Button loginButton;
    private Button signUpButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionStorage = new SessionStorage(this);

        configureToolbar();
        captureComponents();
        configureComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.init, menu);
        return true;
    }

    private void configureToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.login_title);
    }

    private void captureComponents() {
        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passEditText);
    }

    private void configureComponents() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                System.out.println(username);
                System.out.println(password);
                LoginBean bodyData = new LoginBean(username, password);

                UserManagementHandler.getInstance().requestLogin(bodyData,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (sessionStorage.isUserLoggedIn()) {
                                        sessionStorage.clearData();
                                    }

                                    String token = response.getString("token");
                                    String username = response.getJSONObject("user").getString("username");
                                    String password = response.getJSONObject("user").getString("password");
                                    String email = response.getJSONObject("user").getString("email");

                                    Session session = new Session(token, username, password, email);
                                    sessionStorage.createSession(session);

                                    Intent i = new Intent(LoginActivity.this, ContentActivity.class);
                                    startActivity(i);
                                } catch(JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(LoginActivity.this, "Not matching username or password.", Toast.LENGTH_LONG).show();
                            }

                        });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);
            }
        });
    }

}
