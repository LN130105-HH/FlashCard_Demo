package com.crammr.studyflashcard.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.databinding.ActivityNotificationSettingsBinding;
import com.crammr.studyflashcard.utils.PreferenceManager;

public class NotificationSettingsActivity extends AppCompatActivity {

    private ActivityNotificationSettingsBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);

        binding.toolbarNotifSettings.setNavigationOnClickListener(v -> finish());

        binding.switchDailyReminders.setChecked(preferenceManager.isDailyReminderEnabled());
        binding.switchProgressUpdates.setChecked(preferenceManager.isProgressNotificationEnabled());
        binding.switchTips.setChecked(preferenceManager.isTipsNotificationEnabled());

        binding.switchDailyReminders.setOnCheckedChangeListener((btn, isChecked) -> {
            preferenceManager.setDailyReminderEnabled(isChecked);
        });

        binding.switchProgressUpdates.setOnCheckedChangeListener((btn, isChecked) -> {
            preferenceManager.setProgressNotificationEnabled(isChecked);
        });

        binding.switchTips.setOnCheckedChangeListener((btn, isChecked) -> {
            preferenceManager.setTipsNotificationEnabled(isChecked);
        });
    }
}
