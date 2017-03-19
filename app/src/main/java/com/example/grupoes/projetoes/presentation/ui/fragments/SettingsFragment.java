package com.example.grupoes.projetoes.presentation.ui.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.User;
import com.example.grupoes.projetoes.presentation.presenters.api.SettingsPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.List;

public class SettingsFragment extends Fragment implements SettingsPresenter.View {
    private Button confirmButton;
    private EditText passwordEditText;
    private EditText newPasswordEditText;
    private TextView usernameEditText;
    private TextView emailEditText;

    private View parentView;

    private SettingsPresenter presenter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_settings, container, false);
        PresenterFactory.getInstance().createSettingsPresenter(this);

        captureComponents();
        configureComponents();

        return parentView;
    }

    @Override
    public void onResume() {
        SessionStorage storage = new SessionStorage(getActivity());
        User user = storage.getLoggedUser();

        emailEditText.setText(user.getEmail());
        usernameEditText.setText(user.getUsername());

        super.onResume();
    }

    @Override
    public void setPresenter(SettingsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSuccessfulEdit() {
        Toast.makeText(SettingsFragment.this.getContext(), R.string.changepassword_successful_msg, Toast.LENGTH_LONG).show();
        new SessionStorage(getActivity()).logoutUser(getActivity());
    }

    @Override
    public void onInvalidInput(List<InvalidInput> invalidInputs) {
        for (InvalidInput invalidInput : invalidInputs) {
            InputType type = invalidInput.getType();
            String error = invalidInput.getError();

            if (type == InputType.SETTINGS_OLDPASSWORD) {
                passwordEditText.setError(error);
            } else if (type == InputType.SETTINGS_NEWPASSWORD) {
                newPasswordEditText.setError(error);
            }
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void captureComponents() {
        confirmButton = (Button) parentView.findViewById(R.id.settings_confirm_button);
        newPasswordEditText = (EditText) parentView.findViewById(R.id.settings_newpassword_edittext);
        passwordEditText = (EditText) parentView.findViewById(R.id.settings_password_edittext);
        emailEditText = (TextView) parentView.findViewById(R.id.settings_email_textview);
        usernameEditText = (TextView) parentView.findViewById(R.id.settings_username_textview);
    }

    private void configureComponents() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String oldPassword = passwordEditText.getText().toString();
                presenter.requestEditPassword(username, newPassword, oldPassword);
            }
        });
    }
}
