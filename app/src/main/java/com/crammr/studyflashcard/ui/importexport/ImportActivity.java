package com.crammr.studyflashcard.ui.importexport;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.databinding.ActivityImportBinding;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImportActivity extends AppCompatActivity {

    private ActivityImportBinding binding;
    private FlashcardRepository repository;
    private Uri selectedFileUri;
    private String selectedFileName;
    private long targetCollectionId = -1;

    private final ActivityResultLauncher<Intent> fileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedFileUri = result.getData().getData();
                    if (selectedFileUri != null) {
                        selectedFileName = getFileName(selectedFileUri);
                        binding.tvImportSelectedFileName.setText(selectedFileName);
                        binding.tvImportStatusHint.setText("Ready to import from " + selectedFileName);

                        // Prepopulate collection name if empty or default
                        String baseName = selectedFileName.replaceFirst("[.][^.]+$", "");
                        binding.etImportCollectionName.setText(baseName);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new FlashcardRepository(getApplication());
        targetCollectionId = getIntent().getLongExtra("collection_id", -1);

        binding.toolbarImport.setNavigationOnClickListener(v -> finish());

        binding.btnSelectImportFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            fileLauncher.launch(intent);
        });

        binding.btnConfirmImport.setOnClickListener(v -> performImport());
    }

    private void performImport() {
        if (selectedFileUri == null) {
            Toast.makeText(this, "Please choose a file to import", Toast.LENGTH_SHORT).show();
            return;
        }

        String colName = binding.etImportCollectionName.getText() != null
                ? binding.etImportCollectionName.getText().toString().trim() : "Imported Collection";

        new Thread(() -> {
            List<Flashcard> cards = new ArrayList<>();
            try {
                if (selectedFileName != null && selectedFileName.toLowerCase().endsWith(".json")) {
                    StringBuilder sb = new StringBuilder();
                    try (InputStream is = getContentResolver().openInputStream(selectedFileUri);
                         BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    }
                    cards = ImportExportHelper.importFromJson(sb.toString(), 0);
                } else {
                    cards = ImportExportHelper.importFromCsv(this, selectedFileUri, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            final List<Flashcard> finalCards = cards;
            runOnUiThread(() -> {
                if (finalCards == null || finalCards.isEmpty()) {
                    Toast.makeText(this, "No valid flashcards found in file", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (targetCollectionId != -1) {
                    for (Flashcard c : finalCards) {
                        c.setCollectionId(targetCollectionId);
                    }
                    repository.insertFlashcards(finalCards, targetCollectionId);
                    Toast.makeText(this, "Imported " + finalCards.size() + " cards successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    FlashcardCollection newCol = new FlashcardCollection(1, 1, colName, "Imported from " + selectedFileName);
                    newCol.setCardCount(finalCards.size());
                    repository.insertCollection(newCol, id -> {
                        for (Flashcard c : finalCards) {
                            c.setCollectionId(id);
                        }
                        repository.insertFlashcards(finalCards, id);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Imported " + finalCards.size() + " cards into '" + colName + "'!", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    });
                }
            });
        }).start();
    }

    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) result = cursor.getString(index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result != null ? result.lastIndexOf('/') : -1;
            if (cut != -1) result = result.substring(cut + 1);
        }
        return result != null ? result : "flashcards.csv";
    }
}
