package com.crammr.studyflashcard.ui.schedule;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.Reminder;
import com.crammr.studyflashcard.data.repository.NotificationRepository;
import com.crammr.studyflashcard.databinding.ActivityAddReminderBinding;
import com.crammr.studyflashcard.service.StudyReminderWorker;
import com.crammr.studyflashcard.utils.GoogleCalendarHelper;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class AddReminderActivity extends AppCompatActivity {

    private ActivityAddReminderBinding binding;
    private NotificationRepository repository;

    private int selectedHour = 8;
    private int selectedMinute = 0;
    private String selectedRepeat = "DAILY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new NotificationRepository(getApplication());

        binding.toolbarAddReminder.setNavigationOnClickListener(v -> finish());

        setupTimePicker();
        setupRepeatSelectors();

        binding.btnSaveReminder.setOnClickListener(v -> saveReminder());
    }

    private void setupTimePicker() {
        binding.btnPickTime.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                selectedHour = hourOfDay;
                selectedMinute = minute;
                String ampm = hourOfDay >= 12 ? "PM" : "AM";
                int displayHour = hourOfDay > 12 ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);
                binding.btnPickTime.setText(String.format("Set Time: %02d:%02d %s", displayHour, minute, ampm));
            }, selectedHour, selectedMinute, false);
            dialog.show();
        });
    }

    private void setupRepeatSelectors() {
        binding.btnRepeatOnce.setOnClickListener(v -> {
            selectedRepeat = "ONCE";
            highlightActive(binding.btnRepeatOnce, binding.btnRepeatDaily, binding.btnRepeatWeekly);
        });
        binding.btnRepeatDaily.setOnClickListener(v -> {
            selectedRepeat = "DAILY";
            highlightActive(binding.btnRepeatDaily, binding.btnRepeatOnce, binding.btnRepeatWeekly);
        });
        binding.btnRepeatWeekly.setOnClickListener(v -> {
            selectedRepeat = "WEEKLY";
            highlightActive(binding.btnRepeatWeekly, binding.btnRepeatOnce, binding.btnRepeatDaily);
        });
    }

    private void highlightActive(TextView active, TextView inactive1, TextView inactive2) {
        active.setBackgroundResource(R.drawable.bg_segmented_active);
        active.setTextColor(getResources().getColor(R.color.surface_night));

        inactive1.setBackgroundColor(getResources().getColor(R.color.transparent));
        inactive1.setTextColor(getResources().getColor(R.color.neutral_muted));

        inactive2.setBackgroundColor(getResources().getColor(R.color.transparent));
        inactive2.setTextColor(getResources().getColor(R.color.neutral_muted));
    }

    private void saveReminder() {
        String title = binding.etReminderTitle.getText() != null ? binding.etReminderTitle.getText().toString().trim() : "";
        String colName = binding.etReminderColName.getText() != null ? binding.etReminderColName.getText().toString().trim() : "";

        if (TextUtils.isEmpty(title)) {
            binding.etReminderTitle.setError("Title is required");
            binding.etReminderTitle.requestFocus();
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
        calendar.set(Calendar.MINUTE, selectedMinute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        long scheduledTime = calendar.getTimeInMillis();
        long delayMillis = scheduledTime - System.currentTimeMillis();

        // Schedule WorkManager worker
        Data inputData = new Data.Builder()
                .putString(StudyReminderWorker.KEY_TITLE, title)
                .putString(StudyReminderWorker.KEY_COLLECTION_NAME, colName)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(StudyReminderWorker.class)
                .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).enqueue(request);

        // Save into Room DB
        Reminder reminder = new Reminder(1, colName, title, scheduledTime, selectedRepeat);
        repository.insertReminder(reminder);

        // Sync with Google Calendar if enabled
        if (binding.switchSyncGCal.isChecked()) {
            GoogleCalendarHelper.addToGoogleCalendar(this, title, "Review flashcards in Crammr for " + colName, scheduledTime);
        }

        Toast.makeText(this, "Reminder scheduled for " + binding.btnPickTime.getText(), Toast.LENGTH_SHORT).show();
        finish();
    }
}
