package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginEmail, etLoginPassword;
    private Button btnLogin;
    private TextView tvRegisterHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);

        // Handle Login button click
        btnLogin.setOnClickListener(view -> {
            String email = etLoginEmail.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();

            if (validateInputs(email, password)) {
                // Create login request model
                LoginRequest loginRequest = new LoginRequest(email, password);

                // Call API
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<ApiResponse> call = apiService.loginUser(loginRequest);

                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            String message = (response.body() != null) ? response.body().getMessage() : "Login failed";
                            Toast.makeText(LoginActivity.this, "Error: " + message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // Navigate to registration page
        tvRegisterHere.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        });
    }

    private boolean validateInputs(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            etLoginEmail.setError("Email is required");
            etLoginEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etLoginEmail.setError("Enter a valid email");
            etLoginEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            etLoginPassword.setError("Password is required");
            etLoginPassword.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            etLoginPassword.setError("Password must be at least 6 characters");
            etLoginPassword.requestFocus();
            return false;
        }
        return true;
    }
}
