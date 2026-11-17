package com.crammr.studyflashcard.ui.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crammr.studyflashcard.data.local.entity.Reminder;
import com.crammr.studyflashcard.data.repository.NotificationRepository;
import com.crammr.studyflashcard.databinding.ActivityStudyScheduleBinding;
import com.crammr.studyflashcard.ui.adapter.ReminderAdapter;
import com.crammr.studyflashcard.utils.PreferenceManager;

public class StudyScheduleActivity extends AppCompatActivity {

    private ActivityStudyScheduleBinding binding;
    private NotificationRepository repository;
    private PreferenceManager preferenceManager;
    private ReminderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new NotificationRepository(getApplication());
        preferenceManager = new PreferenceManager(this);

        binding.toolbarSchedule.setNavigationOnClickListener(v -> finish());

        adapter = new ReminderAdapter((reminder, isEnabled) -> {
            reminder.setEnabled(isEnabled);
            repository.updateReminder(reminder);
        });

        binding.rvReminders.setLayoutManager(new LinearLayoutManager(this));
        binding.rvReminders.setAdapter(adapter);

        binding.fabAddReminder.setOnClickListener(v -> {
            startActivity(new Intent(this, AddReminderActivity.class));
        });

        updateCalendarConnectionUi();

        binding.btnConnectGoogleCalendar.setOnClickListener(v -> {
            boolean connected = !preferenceManager.isGoogleCalendarConnected();
            preferenceManager.setGoogleCalendarConnected(connected);
            updateCalendarConnectionUi();
            Toast.makeText(this, connected ? "Google Calendar Connected" : "Google Calendar Disconnected", Toast.LENGTH_SHORT).show();
        });

        repository.getAllRemindersLive().observe(this, reminders -> {
            if (reminders != null && !reminders.isEmpty()) {
                adapter.setReminders(reminders);
                binding.rvReminders.setVisibility(View.VISIBLE);
                binding.layoutEmptyReminders.setVisibility(View.GONE);
            } else {
                binding.rvReminders.setVisibility(View.GONE);
                binding.layoutEmptyReminders.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateCalendarConnectionUi() {
        boolean isConnected = preferenceManager.isGoogleCalendarConnected();
        if (isConnected) {
            binding.tvGCalSyncStatus.setText("Connected • Syncing active reminders to Google Calendar");
            binding.btnConnectGoogleCalendar.setText("Disconnect Google Calendar");
        } else {
            binding.tvGCalSyncStatus.setText("Sync study sessions with your external calendar");
            binding.btnConnectGoogleCalendar.setText("Connect Google Calendar");
        }
    }
}
