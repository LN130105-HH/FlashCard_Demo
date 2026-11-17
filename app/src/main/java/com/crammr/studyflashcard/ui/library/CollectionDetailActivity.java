package com.crammr.studyflashcard.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.databinding.ActivityCollectionDetailBinding;
import com.crammr.studyflashcard.ui.adapter.FlashcardAdapter;
import com.crammr.studyflashcard.ui.importexport.ExportActivity;
import com.crammr.studyflashcard.ui.importexport.ImportActivity;
import com.crammr.studyflashcard.ui.study.QuizSetupActivity;
import com.crammr.studyflashcard.ui.study.StudyFlashcardActivity;

import java.util.ArrayList;
import java.util.List;

public class CollectionDetailActivity extends AppCompatActivity {

    private ActivityCollectionDetailBinding binding;
    private FlashcardRepository repository;
    private FlashcardAdapter adapter;
    private long collectionId;
    private FlashcardCollection currentCollection;
    private List<Flashcard> currentCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCollectionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new FlashcardRepository(getApplication());
        collectionId = getIntent().getLongExtra("collection_id", -1);

        if (collectionId == -1) {
            Toast.makeText(this, "Collection not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupViews();
        observeCollection();
    }

    private void setupViews() {
        binding.toolbarCollection.setNavigationOnClickListener(v -> finish());

        adapter = new FlashcardAdapter(new FlashcardAdapter.OnCardActionListener() {
            @Override
            public void onEdit(Flashcard card) {
                Intent intent = new Intent(CollectionDetailActivity.this, AddEditFlashcardActivity.class);
                intent.putExtra("collection_id", collectionId);
                intent.putExtra("card_id", card.getId());
                intent.putExtra("question", card.getQuestion());
                intent.putExtra("answer", card.getAnswer());
                intent.putExtra("explanation", card.getExplanation());
                startActivity(intent);
            }

            @Override
            public void onDelete(Flashcard card) {
                new AlertDialog.Builder(CollectionDetailActivity.this, R.style.Theme_StudyWithFlashCard)
                        .setTitle("Delete Flashcard")
                        .setMessage("Are you sure you want to delete this card?")
                        .setPositiveButton("Delete", (dialog, which) -> repository.deleteFlashcard(card))
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        binding.rvFlashcards.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFlashcards.setAdapter(adapter);

        binding.btnAddCardText.setOnClickListener(v -> openAddCard());
        binding.fabAddFlashcard.setOnClickListener(v -> openAddCard());

        binding.btnStudyNow.setOnClickListener(v -> {
            if (currentCards.isEmpty()) {
                Toast.makeText(this, "Add flashcards before studying", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, StudyFlashcardActivity.class);
            intent.putExtra("collection_id", collectionId);
            startActivity(intent);
        });

        binding.btnTakeQuiz.setOnClickListener(v -> {
            if (currentCards.isEmpty()) {
                Toast.makeText(this, "Add flashcards before taking a quiz", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, QuizSetupActivity.class);
            intent.putExtra("collection_id", collectionId);
            startActivity(intent);
        });

        binding.btnEditCollection.setOnClickListener(v -> {
            if (currentCollection != null) {
                Intent intent = new Intent(this, AddEditCollectionActivity.class);
                intent.putExtra("collection_id", currentCollection.getId());
                intent.putExtra("name", currentCollection.getName());
                intent.putExtra("description", currentCollection.getDescription());
                startActivity(intent);
            }
        });

        binding.btnDeleteCollection.setOnClickListener(v -> {
            if (currentCollection != null) {
                new AlertDialog.Builder(this, R.style.Theme_StudyWithFlashCard)
                        .setTitle("Delete Collection")
                        .setMessage("Delete collection '" + currentCollection.getName() + "' and all its cards?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            repository.deleteCollection(currentCollection);
                            finish();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        binding.btnImport.setOnClickListener(v -> {
            Intent intent = new Intent(this, ImportActivity.class);
            intent.putExtra("collection_id", collectionId);
            startActivity(intent);
        });

        binding.btnExport.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExportActivity.class);
            intent.putExtra("collection_id", collectionId);
            startActivity(intent);
        });
    }

    private void openAddCard() {
        Intent intent = new Intent(this, AddEditFlashcardActivity.class);
        intent.putExtra("collection_id", collectionId);
        startActivity(intent);
    }

    private void observeCollection() {
        repository.getCollectionByIdLive(collectionId).observe(this, collection -> {
            if (collection != null) {
                currentCollection = collection;
                binding.tvDetailCollectionName.setText(collection.getName());
                binding.tvDetailCollectionDesc.setText(collection.getDescription());
            }
        });

        repository.getCardsByCollectionLive(collectionId).observe(this, cards -> {
            currentCards = cards != null ? cards : new ArrayList<>();
            adapter.setCards(currentCards);
            binding.tvCardsHeader.setText("Cards in Deck (" + currentCards.size() + ")");

            if (currentCards.isEmpty()) {
                binding.rvFlashcards.setVisibility(View.GONE);
                binding.layoutEmptyCards.setVisibility(View.VISIBLE);
            } else {
                binding.rvFlashcards.setVisibility(View.VISIBLE);
                binding.layoutEmptyCards.setVisibility(View.GONE);
            }
        });
    }
}
