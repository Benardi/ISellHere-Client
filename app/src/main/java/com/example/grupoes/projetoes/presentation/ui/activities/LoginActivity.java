package com.example.grupoes.projetoes.presentation.ui.activities;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.presentation.presenters.api.LoginPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View {
    private static final int REQUEST_LOCATION = 199;
    private SessionStorage sessionStorage;

    private Button loginButton;
    private Button signUpButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PresenterFactory.getInstance().createLoginPresenter(this);

        checkPermissions();
        configureToolbar();
        captureComponents();
        configureComponents();
    }

    @Override
    protected void onResume() {
        presenter.checkLoginStatus();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.init, menu);
        return true;
    }

    @Override
    public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSuccessfulLogin() {
        Intent i = new Intent(LoginActivity.this, ContentActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onInvalidInput(List<InvalidInput> invalidInputs) {
        for (InvalidInput invalidInput : invalidInputs) {
            InputType type = invalidInput.getType();
            String error = invalidInput.getError();

            if (type == InputType.LOGIN_USERNAME) {
                usernameEditText.setError(error);
            } else if (type == InputType.LOGIN_PASSWORD) {
                passwordEditText.setError(error);
            }
        }
    }

    @Override
    public void onSkip() {
        if (!isActivityRunning(ContentActivity.class)) {
            startActivity(new Intent(this, ContentActivity.class));
        }

        finish();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void configureToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_init);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_login);
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
                presenter.requestLogin(username, password);
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

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    private boolean isActivityRunning(Class activityClass) {
        ActivityManager activityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
