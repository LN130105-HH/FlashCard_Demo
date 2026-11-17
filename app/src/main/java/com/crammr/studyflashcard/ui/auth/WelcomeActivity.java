package com.crammr.studyflashcard.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.databinding.ActivityWelcomeBinding;
import com.crammr.studyflashcard.ui.settings.HowItWorksActivity;

public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGetStarted.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        });

        binding.btnLearnMore.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, HowItWorksActivity.class));
        });
    }
}
