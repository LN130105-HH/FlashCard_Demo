package com.crammr.studyflashcard.ui.notifications;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crammr.studyflashcard.data.repository.NotificationRepository;
import com.crammr.studyflashcard.databinding.ActivityNotificationsBinding;
import com.crammr.studyflashcard.ui.adapter.NotificationAdapter;

public class NotificationsActivity extends AppCompatActivity {

    private ActivityNotificationsBinding binding;
    private NotificationRepository repository;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new NotificationRepository(getApplication());

        binding.toolbarNotifications.setNavigationOnClickListener(v -> finish());

        adapter = new NotificationAdapter();
        binding.rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotifications.setAdapter(adapter);

        binding.btnMarkAllRead.setOnClickListener(v -> repository.markAllAsRead());
        binding.btnClearAllNotifications.setOnClickListener(v -> repository.clearAllNotifications());

        repository.getAllNotificationsLive().observe(this, items -> {
            if (items != null && !items.isEmpty()) {
                adapter.setItems(items);
                binding.rvNotifications.setVisibility(View.VISIBLE);
                binding.layoutEmptyNotifications.setVisibility(View.GONE);
            } else {
                binding.rvNotifications.setVisibility(View.GONE);
                binding.layoutEmptyNotifications.setVisibility(View.VISIBLE);
            }
        });
    }
}
