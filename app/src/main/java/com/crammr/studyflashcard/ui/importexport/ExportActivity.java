package com.crammr.studyflashcard.ui.importexport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.databinding.ActivityExportBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExportActivity extends AppCompatActivity {

    private ActivityExportBinding binding;
    private FlashcardRepository repository;
    private long collectionId;
    private FlashcardCollection collection;
    private List<Flashcard> cards = new ArrayList<>();
    private String selectedFormat = "CSV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new FlashcardRepository(getApplication());
        collectionId = getIntent().getLongExtra("collection_id", -1);

        binding.toolbarExport.setNavigationOnClickListener(v -> finish());

        setupFormatSelectors();
        loadCollectionData();

        binding.btnShareExport.setOnClickListener(v -> performExport());
    }

    private void setupFormatSelectors() {
        binding.btnFormatCsv.setOnClickListener(v -> {
            selectedFormat = "CSV";
            highlightActive(binding.btnFormatCsv, binding.btnFormatJson);
        });

        binding.btnFormatJson.setOnClickListener(v -> {
            selectedFormat = "JSON";
            highlightActive(binding.btnFormatJson, binding.btnFormatCsv);
        });
    }

    private void highlightActive(TextView active, TextView inactive) {
        active.setBackgroundResource(R.drawable.bg_segmented_active);
        active.setTextColor(getResources().getColor(R.color.surface_night));

        inactive.setBackgroundColor(getResources().getColor(R.color.transparent));
        inactive.setTextColor(getResources().getColor(R.color.neutral_muted));
    }

    private void loadCollectionData() {
        new Thread(() -> {
            collection = repository.getCollectionById(collectionId);
            cards = repository.getCardsByCollection(collectionId);
            runOnUiThread(() -> {
                if (collection != null) {
                    binding.tvExportDeckName.setText(collection.getName());
                }
                binding.tvExportCardCount.setText(cards.size() + " Flashcards ready for export");
            });
        }).start();
    }

    private void performExport() {
        if (cards == null || cards.isEmpty()) {
            Toast.makeText(this, "No flashcards to export", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String colName = collection != null ? collection.getName().replaceAll("[^a-zA-Z0-9_-]", "_") : "Deck";
            String ext = "JSON".equals(selectedFormat) ? ".json" : ".csv";
            String fileName = colName + "_export" + ext;

            File file = new File(getCacheDir(), fileName);
            String content = "JSON".equals(selectedFormat)
                    ? ImportExportHelper.exportToJson(cards)
                    : ImportExportHelper.exportToCsv(cards);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(content.getBytes());
            }

            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("JSON".equals(selectedFormat) ? "application/json" : "text/csv");
            sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Crammr Flashcards Deck: " + (collection != null ? collection.getName() : ""));
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(sendIntent, "Export Flashcards via"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Export failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
