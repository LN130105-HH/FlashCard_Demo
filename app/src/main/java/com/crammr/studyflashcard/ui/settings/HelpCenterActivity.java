package com.crammr.studyflashcard.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.databinding.ActivityHelpCenterBinding;

public class HelpCenterActivity extends AppCompatActivity {

    private ActivityHelpCenterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarHelpCenter.setNavigationOnClickListener(v -> finish());
    }
}
