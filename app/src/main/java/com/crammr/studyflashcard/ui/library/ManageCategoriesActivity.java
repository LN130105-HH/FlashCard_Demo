package com.crammr.studyflashcard.ui.library;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.databinding.ActivityManageCategoriesBinding;

public class ManageCategoriesActivity extends AppCompatActivity {

    private ActivityManageCategoriesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarCategory.setNavigationOnClickListener(v -> finish());
    }
}
