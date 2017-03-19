package com.example.grupoes.projetoes.presentation.ui.activities;

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

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.presentation.presenters.api.RegistrationPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements RegistrationPresenter.View {
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText cPasswordEditText;
    private Button createButton;

    private RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        PresenterFactory.getInstance().createRegistrationPresenter(this);

        configureToolbar();
        captureComponents();
        configureComponents();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.init, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void setPresenter(RegistrationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSuccessfulRegistration() {
        Toast.makeText(RegistrationActivity.this, R.string.registration_successful_msg, Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onInvalidInput(List<InvalidInput> invalidInputs) {
        for (InvalidInput invalidInput : invalidInputs) {
            InputType type = invalidInput.getType();
            String error = invalidInput.getError();

            if (type == InputType.REG_EMAIL) {
                emailEditText.setError(error);
            } else if (type == InputType.REG_USERNAME) {
                usernameEditText.setError(error);
            } else if (type == InputType.REG_PASSWORD) {
                passwordEditText.setError(error);
            } else if (type == InputType.REG_CONFIRM_PASSWORD) {
                cPasswordEditText.setError(error);
            }
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.title_activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
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
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = cPasswordEditText.getText().toString();
                presenter.requestRegistration(username, email, password, confirmPassword);
            }
        });
    }
}
