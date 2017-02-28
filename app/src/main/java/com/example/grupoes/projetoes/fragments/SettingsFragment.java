package com.example.grupoes.projetoes.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.beans.EditUserBean;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.User;
import com.example.grupoes.projetoes.request_handlers.UserManagementHandler;

import org.json.JSONObject;

public class SettingsFragment extends Fragment {
    private Button confirmButton;
    private EditText passwordEditText;
    private EditText newPasswordEditText;
    private TextView usernameEditText;
    private TextView emailEditText;

    private View parentView;

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

        confirmButton = (Button) parentView.findViewById(R.id.settings_confirm_button);
        newPasswordEditText = (EditText) parentView.findViewById(R.id.settings_newpassword_edittext);
        passwordEditText = (EditText) parentView.findViewById(R.id.settings_password_edittext);
        emailEditText = (TextView) parentView.findViewById(R.id.settings_email_textview);
        usernameEditText = (TextView) parentView.findViewById(R.id.settings_username_textview);

        SessionStorage storage = new SessionStorage(getContext());
        User user = storage.getLoggedUser();

        emailEditText.setText(user.getEmail());
        usernameEditText.setText(user.getUsername());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String oldPassword = passwordEditText.getText().toString();

                EditUserBean bodyData = new EditUserBean(username, newPassword, oldPassword);

                UserManagementHandler.getInstance().requestEdit(bodyData, new SessionStorage(SettingsFragment.this.getContext()).getToken(),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(SettingsFragment.this.getContext(), "You have successfully changed your password.", Toast.LENGTH_LONG).show();
                            }

                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SettingsFragment.this.getContext(), "Something went wrong with your request.", Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                            }

                        });
            }
        });

        return parentView;
    }
}
