package com.crammr.studyflashcard.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.databinding.ActivityHowItWorksBinding;

public class HowItWorksActivity extends AppCompatActivity {

    private ActivityHowItWorksBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHowItWorksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarHowItWorks.setNavigationOnClickListener(v -> finish());
    }
}
