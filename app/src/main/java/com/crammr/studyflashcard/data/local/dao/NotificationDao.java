package com.crammr.studyflashcard.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.crammr.studyflashcard.data.local.entity.NotificationItem;

import java.util.List;

@Dao
public interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(NotificationItem item);

    @Query("SELECT * FROM notifications ORDER BY createdAt DESC")
    LiveData<List<NotificationItem>> getAllNotificationsLive();

    @Query("UPDATE notifications SET isRead = 1")
    void markAllAsRead();

    @Query("DELETE FROM notifications")
    void clearAll();

    @Query("SELECT COUNT(*) FROM notifications WHERE isRead = 0")
    LiveData<Integer> getUnreadCountLive();
}
