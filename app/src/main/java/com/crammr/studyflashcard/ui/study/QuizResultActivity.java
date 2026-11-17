package com.crammr.studyflashcard.ui.study;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.data.local.entity.QuizResult;
import com.crammr.studyflashcard.data.repository.StudyRepository;
import com.crammr.studyflashcard.databinding.ActivityQuizResultBinding;
import com.crammr.studyflashcard.utils.DateUtils;

public class QuizResultActivity extends AppCompatActivity {

    private ActivityQuizResultBinding binding;
    private StudyRepository studyRepository;

    private long collectionId;
    private String collectionName;
    private int totalQuestions;
    private int correctCount;
    private int durationSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        studyRepository = new StudyRepository(getApplication());

        collectionId = getIntent().getLongExtra("collection_id", -1);
        collectionName = getIntent().getStringExtra("collection_name");
        totalQuestions = getIntent().getIntExtra("total_questions", 0);
        correctCount = getIntent().getIntExtra("correct_count", 0);
        durationSeconds = getIntent().getIntExtra("duration_seconds", 0);

        int incorrectCount = Math.max(0, totalQuestions - correctCount);
        int percent = totalQuestions > 0 ? (correctCount * 100 / totalQuestions) : 0;

        binding.tvFinalScorePercent.setText(percent + "%");
        binding.tvResultCollectionName.setText(collectionName);
        binding.tvCorrectCount.setText(String.valueOf(correctCount));
        binding.tvIncorrectCount.setText(String.valueOf(incorrectCount));
        binding.tvQuizDuration.setText(DateUtils.formatDuration(durationSeconds));

        if (percent >= 80) {
            binding.tvResultTitle.setText("Outstanding Performance! 🌟");
        } else if (percent >= 50) {
            binding.tvResultTitle.setText("Good Effort! Keep Reviewing 👍");
        } else {
            binding.tvResultTitle.setText("Keep Practicing! You Got This 💪");
        }

        // Save result in Room database
        QuizResult result = new QuizResult(
                collectionId,
                collectionName,
                totalQuestions,
                correctCount,
                incorrectCount,
                percent,
                durationSeconds
        );
        studyRepository.insertQuizResult(result);

        binding.btnTryAgainQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizPlayActivity.class);
            intent.putExtra("collection_id", collectionId);
            startActivity(intent);
            finish();
        });

        binding.btnReturnLibrary.setOnClickListener(v -> finish());
    }
}
