package com.crammr.studyflashcard.ui.study;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crammr.studyflashcard.data.repository.StudyRepository;
import com.crammr.studyflashcard.databinding.ActivityStudyHistoryBinding;
import com.crammr.studyflashcard.ui.adapter.StudyHistoryAdapter;

public class StudyHistoryActivity extends AppCompatActivity {

    private ActivityStudyHistoryBinding binding;
    private StudyRepository repository;
    private StudyHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new StudyRepository(getApplication());

        binding.toolbarHistory.setNavigationOnClickListener(v -> finish());

        adapter = new StudyHistoryAdapter();
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHistory.setAdapter(adapter);

        repository.getAllSessionsLive().observe(this, sessions -> {
            if (sessions != null && !sessions.isEmpty()) {
                adapter.setSessions(sessions);
                binding.rvHistory.setVisibility(View.VISIBLE);
                binding.layoutEmptyHistory.setVisibility(View.GONE);
            } else {
                binding.rvHistory.setVisibility(View.GONE);
                binding.layoutEmptyHistory.setVisibility(View.VISIBLE);
            }
        });
    }
}
