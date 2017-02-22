package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.services.UserManagementService;
import com.example.grupoes.projetoes.util.FrontendConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText cPasswordEditText;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        configureToolbar();
        captureComponents();
        configureComponents();
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Create a New Account");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void captureComponents() {
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        cPasswordEditText = (EditText) findViewById(R.id.cPasswordEditText);
        createButton = (Button) findViewById(R.id.createButton);
    }

    private void configureComponents() {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                System.out.println("HI THERE");
                UserManagementService.getInstance().startSignupRequest(getApplicationContext(), username, password, email);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.init, menu);
        return true;
    }
}
