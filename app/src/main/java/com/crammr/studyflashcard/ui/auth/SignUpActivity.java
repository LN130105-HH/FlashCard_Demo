package com.crammr.studyflashcard.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.data.local.entity.User;
import com.crammr.studyflashcard.data.repository.UserRepository;
import com.crammr.studyflashcard.databinding.ActivitySignupBinding;
import com.crammr.studyflashcard.ui.main.MainActivity;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = new UserRepository(getApplication());

        binding.btnSegmentLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

        binding.tvLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

        binding.btnCreateAccount.setOnClickListener(v -> handleSignUp());
    }

    private void handleSignUp() {
        String fullName = binding.etFullName.getText() != null ? binding.etFullName.getText().toString().trim() : "";
        String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
        String password = binding.etPassword.getText() != null ? binding.etPassword.getText().toString().trim() : "";
        String confirmPassword = binding.etConfirmPassword.getText() != null ? binding.etConfirmPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(fullName)) {
            binding.etFullName.setError("Full name is required");
            binding.etFullName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Please enter a valid email address");
            binding.etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            binding.etPassword.setError("Password must be at least 6 characters");
            binding.etPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            binding.etConfirmPassword.setError("Passwords do not match");
            binding.etConfirmPassword.requestFocus();
            return;
        }

        if (!binding.cbTerms.isChecked()) {
            Toast.makeText(this, "Please agree to the Terms of Service & Privacy Policy", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);

        userRepository.register(fullName, email, password, new UserRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(SignUpActivity.this, "Account created successfully! Welcome to Crammr.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void setLoading(boolean isLoading) {
        binding.btnCreateAccount.setEnabled(!isLoading);
        if (isLoading) {
            binding.btnCreateAccount.setText("Creating Account…");
            binding.progressSignUp.setVisibility(View.VISIBLE);
        } else {
            binding.btnCreateAccount.setText("Create Account");
            binding.progressSignUp.setVisibility(View.GONE);
        }
    }
}
