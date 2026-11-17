package com.crammr.studyflashcard.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
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
import com.crammr.studyflashcard.databinding.FragmentAiChatBinding;
import com.crammr.studyflashcard.service.AiGeneratorEngine;
import com.crammr.studyflashcard.service.DocumentExtractor;
import com.crammr.studyflashcard.ui.adapter.ChatMessageAdapter;
import com.crammr.studyflashcard.ui.ai.AiPreviewActivity;

import java.io.Serializable;
import java.util.List;

public class AiChatFragment extends Fragment {

    private FragmentAiChatBinding binding;
    private ChatMessageAdapter chatAdapter;

    private final ActivityResultLauncher<Intent> docPicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        String text = DocumentExtractor.extractTextFromUri(requireContext(), uri, "document");
                        if (!text.isEmpty()) {
                            sendMessage("Please analyze this document and generate flashcards:\n" +
                                    (text.length() > 500 ? text.substring(0, 500) + "..." : text));
                        } else {
                            Toast.makeText(requireContext(), "Could not extract text from document", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAiChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatAdapter = new ChatMessageAdapter(cards -> {
            Intent intent = new Intent(requireContext(), AiPreviewActivity.class);
            intent.putExtra("generated_cards", (Serializable) cards);
            intent.putExtra("source_name", "AI Study Deck");
            startActivity(intent);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setStackFromEnd(true);
        binding.rvChat.setLayoutManager(layoutManager);
        binding.rvChat.setAdapter(chatAdapter);

        // Initial welcome message from AI
        chatAdapter.addMessage(new ChatMessage(ChatMessage.TYPE_AI,
                "Hello! I'm your Crammr AI study assistant. Ask me to explain a concept, generate flashcards, or create quizzes from your notes."));

        // Setup suggestions
        binding.chipSuggest1.setOnClickListener(v -> sendMessage(binding.chipSuggest1.getText().toString()));
        binding.chipSuggest2.setOnClickListener(v -> sendMessage(binding.chipSuggest2.getText().toString()));
        binding.chipSuggest3.setOnClickListener(v -> sendMessage(binding.chipSuggest3.getText().toString()));

        // Send button
        binding.btnSendMessage.setOnClickListener(v -> {
            String text = binding.etChatMessage.getText() != null ? binding.etChatMessage.getText().toString().trim() : "";
            if (!TextUtils.isEmpty(text)) {
                binding.etChatMessage.setText("");
                sendMessage(text);
            }
        });

        // Attach button
        binding.btnAttachDocument.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            String[] mimeTypes = {"application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "text/plain"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            docPicker.launch(intent);
        });
    }

    private void sendMessage(String userText) {
        // Hide suggestions once user engages
        binding.scrollSuggestions.setVisibility(View.GONE);

        chatAdapter.addMessage(new ChatMessage(ChatMessage.TYPE_USER, userText));
        binding.rvChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);

        // Show generating indicator (Frame 3.2)
        binding.layoutAiThinking.setVisibility(View.VISIBLE);

        new Thread(() -> {
            // Process with AI engine
            List<Flashcard> cards = AiGeneratorEngine.generateFlashcardsFromText(userText, 0);

            String replyText;
            if (userText.toLowerCase().contains("explain")) {
                replyText = "Here is a conceptual breakdown:\n\n" +
                        "Spaced repetition is an evidence-based learning technique that incorporates increasing intervals of time between subsequent review of previously learned material in order to exploit the psychological spacing effect.\n\n" +
                        "I have also prepared flashcards below so you can reinforce this topic!";
            } else {
                replyText = "I've synthesized key principles from your request and generated " + cards.size() + " smart flashcards. You can preview, edit, and save them directly into your Library below!";
            }

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (!isAdded()) return;
                binding.layoutAiThinking.setVisibility(View.GONE);

                chatAdapter.addMessage(new ChatMessage(ChatMessage.TYPE_AI, replyText, cards));
                binding.rvChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            }, 1000);
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
