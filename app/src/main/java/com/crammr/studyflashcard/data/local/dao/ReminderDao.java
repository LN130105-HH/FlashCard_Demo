package com.crammr.studyflashcard.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.crammr.studyflashcard.data.local.entity.Reminder;

import java.util.List;

@Dao
public interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("SELECT * FROM reminders ORDER BY scheduledTimestamp ASC")
    LiveData<List<Reminder>> getAllRemindersLive();

    @Query("SELECT * FROM reminders WHERE isEnabled = 1 ORDER BY scheduledTimestamp ASC")
    List<Reminder> getActiveReminders();

    @Query("SELECT * FROM reminders WHERE id = :id LIMIT 1")
    Reminder getReminderById(long id);
}
