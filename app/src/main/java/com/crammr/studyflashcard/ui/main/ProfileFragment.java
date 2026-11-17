package com.crammr.studyflashcard.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.repository.UserRepository;
import com.crammr.studyflashcard.databinding.FragmentProfileBinding;
import com.crammr.studyflashcard.ui.auth.LoginActivity;
import com.crammr.studyflashcard.ui.settings.AboutActivity;
import com.crammr.studyflashcard.ui.settings.HelpCenterActivity;
import com.crammr.studyflashcard.ui.settings.HowItWorksActivity;
import com.crammr.studyflashcard.ui.settings.NotificationSettingsActivity;
import com.crammr.studyflashcard.ui.settings.PersonalInfoActivity;
import com.crammr.studyflashcard.ui.settings.PolicyActivity;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private UserRepository userRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRepository = new UserRepository(requireActivity().getApplication());

        setupViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserInfo();
    }

    private void loadUserInfo() {
        String name = userRepository.getUserName();
        String email = userRepository.getUserEmail();

        binding.tvProfileName.setText(name);
        binding.tvProfileEmail.setText(email);

        if (name != null && !name.trim().isEmpty()) {
            String[] parts = name.trim().split(" ");
            if (parts.length >= 2) {
                binding.tvAvatarInitials.setText(("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase());
            } else {
                binding.tvAvatarInitials.setText(("" + name.charAt(0)).toUpperCase());
            }
        }
    }

    private void setupViews() {
        binding.btnEditProfileHeader.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), PersonalInfoActivity.class));
        });

        binding.itemPersonalInfo.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), PersonalInfoActivity.class));
        });

        binding.itemNotificationSettings.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), NotificationSettingsActivity.class));
        });

        binding.itemHelpCenter.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), HelpCenterActivity.class));
        });

        binding.itemHowItWorks.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), HowItWorksActivity.class));
        });

        binding.itemTerms.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), PolicyActivity.class);
            intent.putExtra("type", "terms");
            startActivity(intent);
        });

        binding.itemPrivacy.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), PolicyActivity.class);
            intent.putExtra("type", "privacy");
            startActivity(intent);
        });

        binding.itemAbout.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AboutActivity.class));
        });

        binding.btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext(), R.style.Theme_StudyWithFlashCard)
                .setTitle(R.string.logout)
                .setMessage(R.string.logout_confirm)
                .setPositiveButton("Logout", (dialog, which) -> {
                    userRepository.logout();
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
