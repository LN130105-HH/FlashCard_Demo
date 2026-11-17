package com.crammr.studyflashcard.ui.study;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.local.entity.StudyHistory;
import com.crammr.studyflashcard.data.local.entity.StudySession;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.data.repository.StudyRepository;
import com.crammr.studyflashcard.databinding.ActivityStudyFlashcardBinding;
import com.crammr.studyflashcard.utils.FlipCardAnimationHelper;

import java.util.ArrayList;
import java.util.List;

public class StudyFlashcardActivity extends AppCompatActivity {

    private ActivityStudyFlashcardBinding binding;
    private FlashcardRepository flashcardRepository;
    private StudyRepository studyRepository;

    private long collectionId;
    private FlashcardCollection collection;
    private List<Flashcard> cards = new ArrayList<>();
    private int currentIndex = 0;
    private boolean isShowingBack = false;

    private int rememberedCount = 0;
    private int needReviewCount = 0;
    private long sessionStartTime;
    private final List<StudyHistory> historyRecords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyFlashcardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        flashcardRepository = new FlashcardRepository(getApplication());
        studyRepository = new StudyRepository(getApplication());

        collectionId = getIntent().getLongExtra("collection_id", -1);
        sessionStartTime = System.currentTimeMillis();

        binding.toolbarStudy.setNavigationOnClickListener(v -> finish());

        setupListeners();
        loadDeck();
    }

    private void setupListeners() {
        binding.cardFlipContainer.setOnClickListener(v -> flipCard());

        binding.btnRemembered.setOnClickListener(v -> evaluateCard(true));
        binding.btnNeedReview.setOnClickListener(v -> evaluateCard(false));
    }

    private void flipCard() {
        FlipCardAnimationHelper.flipCard(binding.cardFrontView, binding.cardBackView, !isShowingBack);
        isShowingBack = !isShowingBack;
    }

    private void loadDeck() {
        new Thread(() -> {
            collection = flashcardRepository.getCollectionById(collectionId);
            List<Flashcard> list = flashcardRepository.getCardsByCollection(collectionId);
            runOnUiThread(() -> {
                if (list != null && !list.isEmpty()) {
                    cards = list;
                    currentIndex = 0;
                    showCard(currentIndex);
                } else {
                    Toast.makeText(this, "Deck is empty", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }).start();
    }

    private void showCard(int index) {
        if (index < 0 || index >= cards.size()) return;

        Flashcard card = cards.get(index);
        binding.tvQuestionFront.setText(card.getQuestion());
        binding.tvAnswerBack.setText(card.getAnswer());
        binding.tvExplanationBack.setText(card.getExplanation() != null && !card.getExplanation().isEmpty()
                ? card.getExplanation() : "No additional explanation");

        binding.tvStudyProgressCounter.setText((index + 1) + " / " + cards.size());
        binding.studyProgressBar.setProgress(((index + 1) * 100) / cards.size());

        // Reset to front view
        if (isShowingBack) {
            binding.cardBackView.setVisibility(View.GONE);
            binding.cardFrontView.setVisibility(View.VISIBLE);
            binding.cardFrontView.setRotationY(0f);
            binding.cardBackView.setRotationY(0f);
            isShowingBack = false;
        }
    }

    private void evaluateCard(boolean remembered) {
        if (currentIndex >= cards.size()) return;

        Flashcard currentCard = cards.get(currentIndex);
        if (remembered) {
            rememberedCount++;
            currentCard.setRemembered(true);
            currentCard.setRememberedCount(currentCard.getRememberedCount() + 1);
        } else {
            needReviewCount++;
            currentCard.setRemembered(false);
            currentCard.setNotRememberedCount(currentCard.getNotRememberedCount() + 1);
        }
        currentCard.setLastReviewedAt(System.currentTimeMillis());
        flashcardRepository.updateFlashcard(currentCard);

        historyRecords.add(new StudyHistory(0, collectionId, currentCard.getId(), remembered));

        currentIndex++;
        if (currentIndex < cards.size()) {
            showCard(currentIndex);
        } else {
            completeSession();
        }
    }

    private void completeSession() {
        long sessionEndTime = System.currentTimeMillis();
        int durationMins = Math.max(1, (int) ((sessionEndTime - sessionStartTime) / (1000 * 60)));

        String colName = collection != null ? collection.getName() : "Collection";
        StudySession session = new StudySession(
                collectionId,
                colName,
                sessionStartTime,
                sessionEndTime,
                durationMins,
                cards.size(),
                rememberedCount,
                needReviewCount
        );

        studyRepository.insertSession(session, historyRecords);

        // Update collection last studied time
        if (collection != null) {
            collection.setLastStudiedAt(sessionEndTime);
            flashcardRepository.updateCollection(collection);
        }

        // Show session complete dialog
        new AlertDialog.Builder(this, R.style.Theme_StudyWithFlashCard)
                .setTitle(R.string.session_complete_title)
                .setMessage("You reviewed " + cards.size() + " cards.\n\n" +
                        "✅ Remembered: " + rememberedCount + "\n" +
                        "🔁 Need Review: " + needReviewCount + "\n" +
                        "⏱️ Duration: " + durationMins + " min(s)")
                .setPositiveButton("Finish", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
