package com.crammr.studyflashcard.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.data.repository.StudyRepository;
import com.crammr.studyflashcard.data.repository.UserRepository;
import com.crammr.studyflashcard.databinding.FragmentHomeBinding;
import com.crammr.studyflashcard.service.AiGeneratorEngine;
import com.crammr.studyflashcard.service.DocumentExtractor;
import com.crammr.studyflashcard.ui.adapter.CollectionAdapter;
import com.crammr.studyflashcard.ui.ai.AiPreviewActivity;
import com.crammr.studyflashcard.ui.library.CollectionDetailActivity;
import com.crammr.studyflashcard.ui.notifications.NotificationsActivity;

import java.io.Serializable;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FlashcardRepository flashcardRepository;
    private StudyRepository studyRepository;
    private UserRepository userRepository;
    private CollectionAdapter collectionAdapter;

    private Uri selectedFileUri = null;
    private String selectedFileName = null;

    private final ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedFileUri = result.getData().getData();
                    if (selectedFileUri != null) {
                        selectedFileName = getFileNameFromUri(selectedFileUri);
                        showFileSelectedState(selectedFileName);
                    }
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        flashcardRepository = new FlashcardRepository(requireActivity().getApplication());
        studyRepository = new StudyRepository(requireActivity().getApplication());
        userRepository = new UserRepository(requireActivity().getApplication());

        setupViews();
        observeData();
    }

    private void setupViews() {
        binding.tvGreeting.setText("Hello, " + userRepository.getUserName() + " 👋");

        binding.btnNotification.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), NotificationsActivity.class));
        });

        // Setup Recent Collections RecyclerView
        collectionAdapter = new CollectionAdapter(collection -> {
            Intent intent = new Intent(requireContext(), CollectionDetailActivity.class);
            intent.putExtra("collection_id", collection.getId());
            startActivity(intent);
        });
        binding.rvRecentCollections.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvRecentCollections.setAdapter(collectionAdapter);

        binding.tvViewAllCollections.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToTab(MainActivity.TAB_LIBRARY);
            }
        });

        // Upload triggers (Frame 2.1)
        binding.btnChooseFile.setOnClickListener(v -> openDocumentPicker());

        // File actions (Frame 2.2)
        binding.btnRemoveFile.setOnClickListener(v -> resetUploadState());

        binding.btnStartGenerating.setOnClickListener(v -> startAiGeneration());
    }

    private void observeData() {
        // Quick stats
        flashcardRepository.getTotalCardsLive().observe(getViewLifecycleOwner(), total -> {
            binding.tvTotalCardsHome.setText(String.valueOf(total != null ? total : 0));
        });

        flashcardRepository.getTotalRememberedCardsLive().observe(getViewLifecycleOwner(), remembered -> {
            binding.tvRememberedHome.setText(String.valueOf(remembered != null ? remembered : 0));
        });

        studyRepository.getTotalStudyMinutesLive().observe(getViewLifecycleOwner(), mins -> {
            binding.tvStudyMinsHome.setText((mins != null ? mins : 0) + "m");
        });

        // Recent collections
        flashcardRepository.getRecentCollections(5).observe(getViewLifecycleOwner(), collections -> {
            if (collections != null && !collections.isEmpty()) {
                collectionAdapter.setCollections(collections);
                binding.rvRecentCollections.setVisibility(View.VISIBLE);
                binding.layoutEmptyRecent.setVisibility(View.GONE);
            } else {
                binding.rvRecentCollections.setVisibility(View.GONE);
                binding.layoutEmptyRecent.setVisibility(View.VISIBLE);
            }
        });
    }

    private void openDocumentPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimeTypes = {"application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "text/plain"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        filePickerLauncher.launch(intent);
    }

    private void showFileSelectedState(String fileName) {
        binding.layoutUploadEmpty.setVisibility(View.GONE);
        binding.layoutFileSelected.setVisibility(View.VISIBLE);
        binding.layoutGenerating.setVisibility(View.GONE);
        binding.tvSelectedFileName.setText(fileName);
    }

    private void resetUploadState() {
        selectedFileUri = null;
        selectedFileName = null;
        binding.layoutUploadEmpty.setVisibility(View.VISIBLE);
        binding.layoutFileSelected.setVisibility(View.GONE);
        binding.layoutGenerating.setVisibility(View.GONE);
    }

    private void startAiGeneration() {
        if (selectedFileUri == null) return;

        // Frame 2.3 - Generating State
        binding.layoutUploadEmpty.setVisibility(View.GONE);
        binding.layoutFileSelected.setVisibility(View.GONE);
        binding.layoutGenerating.setVisibility(View.VISIBLE);

        new Thread(() -> {
            // Extract text from doc
            String extracted = DocumentExtractor.extractTextFromUri(requireContext(), selectedFileUri, selectedFileName);
            if (extracted.trim().isEmpty()) {
                extracted = "Document: " + selectedFileName + " contains key definitions and study notes for exam revision.";
            }

            // Generate cards using AI engine
            List<Flashcard> cards = AiGeneratorEngine.generateFlashcardsFromText(extracted, 0);

            // Small delay for UI smoothness
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (!isAdded()) return;
                resetUploadState();

                if (cards.isEmpty()) {
                    Toast.makeText(requireContext(), "Could not generate flashcards from file", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Frame 2.4 & 8.4 - Open AI Preview Activity
                Intent intent = new Intent(requireContext(), AiPreviewActivity.class);
                intent.putExtra("generated_cards", (Serializable) cards);
                intent.putExtra("source_name", selectedFileName != null ? selectedFileName.replaceFirst("[.][^.]+$", "") : "AI Generated Deck");
                startActivity(intent);
            }, 1200);
        }).start();
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result != null ? result.lastIndexOf('/') : -1;
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result != null ? result : "Study_Document.docx";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
