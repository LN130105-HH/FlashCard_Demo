package com.crammr.studyflashcard.ui.library;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.databinding.ActivityAddEditFlashcardBinding;

public class AddEditFlashcardActivity extends AppCompatActivity {

    private ActivityAddEditFlashcardBinding binding;
    private FlashcardRepository repository;
    private long collectionId = -1;
    private long cardId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditFlashcardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new FlashcardRepository(getApplication());
        collectionId = getIntent().getLongExtra("collection_id", -1);
        cardId = getIntent().getLongExtra("card_id", -1);

        if (cardId != -1) {
            binding.toolbarCard.setTitle("Edit Flashcard");
            binding.etQuestion.setText(getIntent().getStringExtra("question"));
            binding.etAnswer.setText(getIntent().getStringExtra("answer"));
            binding.etExplanation.setText(getIntent().getStringExtra("explanation"));
        }

        binding.toolbarCard.setNavigationOnClickListener(v -> finish());
        binding.btnCancelCard.setOnClickListener(v -> finish());
        binding.btnSaveCard.setOnClickListener(v -> saveCard());
    }

    private void saveCard() {
        String q = binding.etQuestion.getText() != null ? binding.etQuestion.getText().toString().trim() : "";
        String a = binding.etAnswer.getText() != null ? binding.etAnswer.getText().toString().trim() : "";
        String exp = binding.etExplanation.getText() != null ? binding.etExplanation.getText().toString().trim() : "";

        if (TextUtils.isEmpty(q)) {
            binding.etQuestion.setError("Question is required");
            binding.etQuestion.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(a)) {
            binding.etAnswer.setError("Answer is required");
            binding.etAnswer.requestFocus();
            return;
        }

        if (cardId != -1) {
            new Thread(() -> {
                Flashcard card = new Flashcard(collectionId, q, a, exp);
                card.setId(cardId);
                repository.updateFlashcard(card);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Flashcard updated", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        } else {
            Flashcard card = new Flashcard(collectionId, q, a, exp);
            repository.insertFlashcard(card);
            Toast.makeText(this, "Flashcard added", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
