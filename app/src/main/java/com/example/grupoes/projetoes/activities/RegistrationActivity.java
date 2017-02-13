package com.example.grupoes.projetoes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.grupoes.projetoes.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
