package com.crammr.studyflashcard.ui.settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.data.local.entity.User;
import com.crammr.studyflashcard.data.repository.UserRepository;
import com.crammr.studyflashcard.databinding.ActivityPersonalInfoBinding;

public class PersonalInfoActivity extends AppCompatActivity {

    private ActivityPersonalInfoBinding binding;
    private UserRepository userRepository;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = new UserRepository(getApplication());

        binding.toolbarPersonalInfo.setNavigationOnClickListener(v -> finish());

        loadUserData();

        binding.btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void loadUserData() {
        String name = userRepository.getUserName();
        String email = userRepository.getUserEmail();

        binding.etProfileName.setText(name);
        binding.etProfileEmail.setText(email);

        if (name != null && !name.trim().isEmpty()) {
            String[] parts = name.trim().split(" ");
            if (parts.length >= 2) {
                binding.tvAvatarInitialsInfo.setText(("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase());
            } else {
                binding.tvAvatarInitialsInfo.setText(("" + name.charAt(0)).toUpperCase());
            }
        }

        userRepository.getCurrentUserLive().observe(this, user -> {
            if (user != null) {
                currentUser = user;
                if (user.getBio() != null) binding.etProfileBio.setText(user.getBio());
                if (user.getGender() != null) binding.etProfileGender.setText(user.getGender());
            }
        });
    }

    private void saveProfile() {
        String name = binding.etProfileName.getText() != null ? binding.etProfileName.getText().toString().trim() : "";
        String bio = binding.etProfileBio.getText() != null ? binding.etProfileBio.getText().toString().trim() : "";
        String gender = binding.etProfileGender.getText() != null ? binding.etProfileGender.getText().toString().trim() : "";

        if (TextUtils.isEmpty(name)) {
            binding.etProfileName.setError("Name is required");
            return;
        }

        if (currentUser != null) {
            currentUser.setFullName(name);
            currentUser.setBio(bio);
            currentUser.setGender(gender);
            userRepository.updateUser(currentUser);
        }

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
