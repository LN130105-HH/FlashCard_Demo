package com.crammr.studyflashcard.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.data.repository.StudyRepository;
import com.crammr.studyflashcard.databinding.FragmentProgressBinding;
import com.crammr.studyflashcard.ui.schedule.StudyScheduleActivity;
import com.crammr.studyflashcard.ui.study.StudyHistoryActivity;

public class ProgressFragment extends Fragment {

    private FragmentProgressBinding binding;
    private FlashcardRepository flashcardRepository;
    private StudyRepository studyRepository;

    private int totalCards = 0;
    private int rememberedCards = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProgressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        flashcardRepository = new FlashcardRepository(requireActivity().getApplication());
        studyRepository = new StudyRepository(requireActivity().getApplication());

        setupViews();
        observeProgress();
    }

    private void setupViews() {
        binding.btnOpenStudyHistory.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), StudyHistoryActivity.class));
        });

        binding.btnOpenSchedule.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), StudyScheduleActivity.class));
        });
    }

    private void observeProgress() {
        studyRepository.getTotalStudyMinutesLive().observe(getViewLifecycleOwner(), mins -> {
            binding.tvStatStudyTime.setText((mins != null ? mins : 0) + "m");
        });

        flashcardRepository.getTotalCardsLive().observe(getViewLifecycleOwner(), total -> {
            totalCards = total != null ? total : 0;
            updateRetention();
        });

        flashcardRepository.getTotalRememberedCardsLive().observe(getViewLifecycleOwner(), remembered -> {
            rememberedCards = remembered != null ? remembered : 0;
            binding.tvStatCardsLearned.setText(String.valueOf(rememberedCards));
            updateRetention();
        });

        studyRepository.getAverageScoreLive().observe(getViewLifecycleOwner(), avg -> {
            if (avg != null && avg > 0) {
                binding.tvStatQuizAverage.setText(Math.round(avg) + "%");
            } else {
                binding.tvStatQuizAverage.setText("—");
            }
        });
    }

    private void updateRetention() {
        if (totalCards > 0) {
            int rate = (rememberedCards * 100) / totalCards;
            binding.tvStatRetention.setText(rate + "%");
        } else {
            binding.tvStatRetention.setText("0%");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
