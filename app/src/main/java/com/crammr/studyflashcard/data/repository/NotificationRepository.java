package com.crammr.studyflashcard.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.crammr.studyflashcard.data.local.AppDatabase;
import com.crammr.studyflashcard.data.local.dao.NotificationDao;
import com.crammr.studyflashcard.data.local.dao.ReminderDao;
import com.crammr.studyflashcard.data.local.entity.NotificationItem;
import com.crammr.studyflashcard.data.local.entity.Reminder;

import java.util.List;

public class NotificationRepository {
    private final NotificationDao notificationDao;
    private final ReminderDao reminderDao;

    public NotificationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        notificationDao = db.notificationDao();
        reminderDao = db.reminderDao();
    }

    public LiveData<List<NotificationItem>> getAllNotificationsLive() {
        return notificationDao.getAllNotificationsLive();
    }

    public LiveData<Integer> getUnreadCountLive() {
        return notificationDao.getUnreadCountLive();
    }

    public void insertNotification(NotificationItem item) {
        AppDatabase.databaseWriteExecutor.execute(() -> notificationDao.insert(item));
    }

    public void markAllAsRead() {
        AppDatabase.databaseWriteExecutor.execute(notificationDao::markAllAsRead);
    }

    public void clearAllNotifications() {
        AppDatabase.databaseWriteExecutor.execute(notificationDao::clearAll);
    }

    public LiveData<List<Reminder>> getAllRemindersLive() {
        return reminderDao.getAllRemindersLive();
    }

    public void insertReminder(Reminder reminder) {
        AppDatabase.databaseWriteExecutor.execute(() -> reminderDao.insert(reminder));
    }

    public void updateReminder(Reminder reminder) {
        AppDatabase.databaseWriteExecutor.execute(() -> reminderDao.update(reminder));
    }

    public void deleteReminder(Reminder reminder) {
        AppDatabase.databaseWriteExecutor.execute(() -> reminderDao.delete(reminder));
    }
}
