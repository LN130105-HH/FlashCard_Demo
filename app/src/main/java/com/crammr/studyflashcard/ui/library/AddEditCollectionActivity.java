package com.crammr.studyflashcard.ui.library;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.databinding.ActivityAddEditCollectionBinding;

public class AddEditCollectionActivity extends AppCompatActivity {

    private ActivityAddEditCollectionBinding binding;
    private FlashcardRepository repository;
    private long collectionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditCollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new FlashcardRepository(getApplication());

        collectionId = getIntent().getLongExtra("collection_id", -1);
        if (collectionId != -1) {
            binding.toolbarAddCol.setTitle("Edit Collection");
            binding.etColName.setText(getIntent().getStringExtra("name"));
            binding.etColDesc.setText(getIntent().getStringExtra("description"));
        }

        binding.toolbarAddCol.setNavigationOnClickListener(v -> finish());
        binding.btnCancelCollection.setOnClickListener(v -> finish());
        binding.btnSaveCollection.setOnClickListener(v -> saveCollection());
    }

    private void saveCollection() {
        String name = binding.etColName.getText() != null ? binding.etColName.getText().toString().trim() : "";
        String desc = binding.etColDesc.getText() != null ? binding.etColDesc.getText().toString().trim() : "";

        if (TextUtils.isEmpty(name)) {
            binding.etColName.setError("Collection name is required");
            binding.etColName.requestFocus();
            return;
        }

        if (collectionId != -1) {
            // Update
            new Thread(() -> {
                FlashcardCollection col = repository.getCollectionById(collectionId);
                if (col != null) {
                    col.setName(name);
                    col.setDescription(desc);
                    repository.updateCollection(col);
                }
                runOnUiThread(() -> {
                    Toast.makeText(this, "Collection updated", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        } else {
            // Create
            FlashcardCollection newCol = new FlashcardCollection(1, 1, name, desc);
            repository.insertCollection(newCol, id -> runOnUiThread(() -> {
                Toast.makeText(this, "Collection created", Toast.LENGTH_SHORT).show();
                finish();
            }));
        }
    }
}
