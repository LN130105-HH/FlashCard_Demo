package com.crammr.studyflashcard.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.databinding.ActivityPolicyBinding;

public class PolicyActivity extends AppCompatActivity {

    private ActivityPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarPolicy.setNavigationOnClickListener(v -> finish());

        String type = getIntent().getStringExtra("type");
        if ("privacy".equalsIgnoreCase(type)) {
            binding.toolbarPolicy.setTitle("Privacy Policy");
            binding.tvPolicyTitle.setText("Privacy Policy");
            binding.tvPolicyBody.setText(
                    "1. Information We Collect\n" +
                    "Crammr stores your study decks, flashcards, session durations, and quiz scores locally on your device. Account information (name and email) is used to personalize your learning experience.\n\n" +
                    "2. Document Processing & AI\n" +
                    "When you upload Word (.docx) or PDF files, content is extracted solely to synthesize questions and flashcards. No documents are sold or shared with third parties.\n\n" +
                    "3. Device Permissions\n" +
                    "Storage permissions are used strictly to import and export study files. Calendar permissions or intents are used exclusively to schedule study reminders upon your confirmation.\n\n" +
                    "4. Your Control\n" +
                    "You may export, backup, or clear your local database and account data anytime through the Settings menu."
            );
        } else {
            binding.toolbarPolicy.setTitle("Terms of Service");
            binding.tvPolicyTitle.setText("Terms of Service");
            binding.tvPolicyBody.setText(
                    "1. Acceptance of Terms\n" +
                    "By accessing and using Crammr (Study With FlashCard), you agree to abide by these terms and conditions.\n\n" +
                    "2. Learning Purpose\n" +
                    "Crammr is designed to enhance personal learning, active recall, and spaced repetition. AI-generated cards and quiz explanations are advisory study aids.\n\n" +
                    "3. User Content\n" +
                    "You retain ownership of any notes or documents you upload. You are responsible for ensuring your uploaded files respect applicable copyright laws.\n\n" +
                    "4. Service Availability\n" +
                    "Core study features, flashcard flips, and quizzes are fully available offline. Online AI assistants require an active internet connection."
            );
        }
    }
}
