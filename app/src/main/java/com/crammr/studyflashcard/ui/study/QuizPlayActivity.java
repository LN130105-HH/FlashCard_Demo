package com.crammr.studyflashcard.ui.study;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.databinding.ActivityQuizPlayBinding;
import com.crammr.studyflashcard.service.AiGeneratorEngine;
import com.crammr.studyflashcard.ui.adapter.QuizOptionAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuizPlayActivity extends AppCompatActivity {

    private ActivityQuizPlayBinding binding;
    private FlashcardRepository repository;
    private QuizOptionAdapter optionAdapter;

    private long collectionId;
    private int questionCountSetting;
    private int timerMinutesSetting;

    private FlashcardCollection collection;
    private List<QuizQuestionModel> quizQuestions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private boolean isCurrentQuestionSubmitted = false;
    private int correctAnswersCount = 0;

    private CountDownTimer countDownTimer;
    private long startTimeMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new FlashcardRepository(getApplication());

        collectionId = getIntent().getLongExtra("collection_id", -1);
        questionCountSetting = getIntent().getIntExtra("question_count", 5);
        timerMinutesSetting = getIntent().getIntExtra("timer_minutes", 0);
        startTimeMillis = System.currentTimeMillis();

        binding.toolbarQuizPlay.setNavigationOnClickListener(v -> finish());

        setupViews();
        loadAndGenerateQuiz();
    }

    private void setupViews() {
        optionAdapter = new QuizOptionAdapter(index -> {
            if (!isCurrentQuestionSubmitted) {
                QuizQuestionModel current = quizQuestions.get(currentQuestionIndex);
                current.setSelectedOptionIndex(index);
                optionAdapter.setOptions(current.getOptions(), index, current.getCorrectOptionIndex(), false);
            }
        });

        binding.rvQuizOptions.setLayoutManager(new LinearLayoutManager(this));
        binding.rvQuizOptions.setAdapter(optionAdapter);

        binding.btnNextOrSubmit.setOnClickListener(v -> onNextClicked());

        if (timerMinutesSetting > 0) {
            binding.ivTimerIcon.setVisibility(View.VISIBLE);
            binding.tvTimerCountdown.setVisibility(View.VISIBLE);
            startTimer(timerMinutesSetting * 60 * 1000L);
        }
    }

    private void startTimer(long totalMillis) {
        countDownTimer = new CountDownTimer(totalMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long m = seconds / 60;
                long s = seconds % 60;
                binding.tvTimerCountdown.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
            }

            @Override
            public void onFinish() {
                Toast.makeText(QuizPlayActivity.this, "Time's up! Submitting quiz...", Toast.LENGTH_SHORT).show();
                finishQuiz();
            }
        }.start();
    }

    private void loadAndGenerateQuiz() {
        new Thread(() -> {
            collection = repository.getCollectionById(collectionId);
            List<Flashcard> cards = repository.getCardsByCollection(collectionId);
            if (cards == null || cards.isEmpty()) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            quizQuestions = AiGeneratorEngine.generateQuizFromCards(cards, questionCountSetting);
            runOnUiThread(() -> {
                currentQuestionIndex = 0;
                showQuestion(currentQuestionIndex);
            });
        }).start();
    }

    private void showQuestion(int index) {
        if (index < 0 || index >= quizQuestions.size()) return;

        QuizQuestionModel q = quizQuestions.get(index);
        isCurrentQuestionSubmitted = false;

        binding.tvQuizQuestionIndex.setText("QUESTION " + (index + 1));
        binding.tvQuizQuestionText.setText(q.getQuestion());
        binding.tvQuestionProgress.setText((index + 1) + " / " + quizQuestions.size());
        binding.quizProgressBar.setProgress(((index + 1) * 100) / quizQuestions.size());

        binding.cardQuizFeedback.setVisibility(View.GONE);
        binding.btnNextOrSubmit.setText("Check Answer");

        optionAdapter.setOptions(q.getOptions(), q.getSelectedOptionIndex(), q.getCorrectOptionIndex(), false);
    }

    private void onNextClicked() {
        QuizQuestionModel current = quizQuestions.get(currentQuestionIndex);

        if (!isCurrentQuestionSubmitted) {
            if (!current.isAnswered()) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            // Frame 5.8: Show instant feedback
            isCurrentQuestionSubmitted = true;
            optionAdapter.setOptions(current.getOptions(), current.getSelectedOptionIndex(), current.getCorrectOptionIndex(), true);

            binding.cardQuizFeedback.setVisibility(View.VISIBLE);
            if (current.isCorrect()) {
                correctAnswersCount++;
                binding.tvFeedbackStatus.setText("Correct! 🎉");
                binding.tvFeedbackStatus.setTextColor(getResources().getColor(R.color.status_success));
            } else {
                binding.tvFeedbackStatus.setText("Incorrect ❌");
                binding.tvFeedbackStatus.setTextColor(getResources().getColor(R.color.status_error));
            }
            binding.tvFeedbackExplanation.setText(current.getExplanation());

            if (currentQuestionIndex == quizQuestions.size() - 1) {
                binding.btnNextOrSubmit.setText(getString(R.string.submit_quiz));
            } else {
                binding.btnNextOrSubmit.setText(getString(R.string.next_question));
            }
        } else {
            // Advance to next question or complete
            currentQuestionIndex++;
            if (currentQuestionIndex < quizQuestions.size()) {
                showQuestion(currentQuestionIndex);
            } else {
                finishQuiz();
            }
        }
    }

    private void finishQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        int durationSeconds = (int) ((System.currentTimeMillis() - startTimeMillis) / 1000);
        String colName = collection != null ? collection.getName() : "Collection";

        Intent intent = new Intent(this, QuizResultActivity.class);
        intent.putExtra("collection_id", collectionId);
        intent.putExtra("collection_name", colName);
        intent.putExtra("total_questions", quizQuestions.size());
        intent.putExtra("correct_count", correctAnswersCount);
        intent.putExtra("duration_seconds", durationSeconds);
        intent.putExtra("quiz_questions", (Serializable) quizQuestions);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
