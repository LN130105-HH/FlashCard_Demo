package com.crammr.studyflashcard.ui.study;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.databinding.ActivityQuizSetupBinding;

public class QuizSetupActivity extends AppCompatActivity {

    private ActivityQuizSetupBinding binding;
    private FlashcardRepository repository;
    private long collectionId;
    private int selectedCount = 5;
    private int selectedTimerMinutes = 0; // 0 = no limit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizSetupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new FlashcardRepository(getApplication());
        collectionId = getIntent().getLongExtra("collection_id", -1);

        binding.toolbarQuizSetup.setNavigationOnClickListener(v -> finish());

        loadCollectionInfo();
        setupSelectors();

        binding.btnStartQuizPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizPlayActivity.class);
            intent.putExtra("collection_id", collectionId);
            intent.putExtra("question_count", selectedCount);
            intent.putExtra("timer_minutes", selectedTimerMinutes);
            startActivity(intent);
            finish();
        });
    }

    private void loadCollectionInfo() {
        repository.getCollectionByIdLive(collectionId).observe(this, collection -> {
            if (collection != null) {
                binding.tvSetupDeckName.setText(collection.getName());
                binding.tvSetupAvailableCards.setText(collection.getCardCount() + " Flashcards available");
            }
        });
    }

    private void setupSelectors() {
        // Count selector
        binding.btnCount5.setOnClickListener(v -> {
            selectedCount = 5;
            highlightActive(binding.btnCount5, binding.btnCount10, binding.btnCountAll);
        });
        binding.btnCount10.setOnClickListener(v -> {
            selectedCount = 10;
            highlightActive(binding.btnCount10, binding.btnCount5, binding.btnCountAll);
        });
        binding.btnCountAll.setOnClickListener(v -> {
            selectedCount = 100;
            highlightActive(binding.btnCountAll, binding.btnCount5, binding.btnCount10);
        });

        // Timer selector
        binding.btnTimerNone.setOnClickListener(v -> {
            selectedTimerMinutes = 0;
            highlightActive(binding.btnTimerNone, binding.btnTimer5, binding.btnTimer10);
        });
        binding.btnTimer5.setOnClickListener(v -> {
            selectedTimerMinutes = 5;
            highlightActive(binding.btnTimer5, binding.btnTimerNone, binding.btnTimer10);
        });
        binding.btnTimer10.setOnClickListener(v -> {
            selectedTimerMinutes = 10;
            highlightActive(binding.btnTimer10, binding.btnTimerNone, binding.btnTimer5);
        });
    }

    private void highlightActive(TextView active, TextView inactive1, TextView inactive2) {
        active.setBackgroundResource(R.drawable.bg_segmented_active);
        active.setTextColor(getResources().getColor(R.color.surface_night));

        inactive1.setBackgroundColor(getResources().getColor(R.color.transparent));
        inactive1.setTextColor(getResources().getColor(R.color.neutral_muted));

        inactive2.setBackgroundColor(getResources().getColor(R.color.transparent));
        inactive2.setTextColor(getResources().getColor(R.color.neutral_muted));
    }
}
