package com.example.testapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etFullName, etEmail, etPhone, etPassword, etConfirmPassword;
    Button btnRegister;
    TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure this matches your layout XML name

        // Initialize views
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink); // <- Add this TextView in your XML layout

        // Register button logic
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                // Validation
                if (TextUtils.isEmpty(fullName)) {
                    etFullName.setError("Full Name is required");
                    return;
                }
                if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Valid Email is required");
                    return;
                }
                if (TextUtils.isEmpty(phone) || phone.length() < 11) {
                    etPhone.setError("Valid Phone Number is required");
                    return;
                }
                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    etPassword.setError("Password must be at least 6 characters");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    etConfirmPassword.setError("Passwords do not match");
                    return;
                }

                Toast.makeText(MainActivity.this, "Registered Successfully!\n" +
                        "Name: " + fullName + "\nEmail: " + email + "\nPhone: " + phone, Toast.LENGTH_LONG).show();
            }
        });

        // Make "Login" part clickable
        SpannableString ss = new SpannableString("Do you have any account? Login");
        ClickableSpan loginClickable = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Navigate to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.YELLOW); // change this color as needed
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(loginClickable, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLoginLink.setText(ss);
        tvLoginLink.setMovementMethod(LinkMovementMethod.getInstance());
        tvLoginLink.setHighlightColor(Color.TRANSPARENT);
    }
}
