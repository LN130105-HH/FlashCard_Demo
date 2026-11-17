package com.crammr.studyflashcard.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long collectionId;
    private String collectionName;
    private String title;
    private long scheduledTimestamp;
    private String repeatType; // "ONCE", "DAILY", "WEEKLY"
    private boolean isEnabled;

    public Reminder(long collectionId, String collectionName, String title, long scheduledTimestamp, String repeatType) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.title = title;
        this.scheduledTimestamp = scheduledTimestamp;
        this.repeatType = repeatType;
        this.isEnabled = true;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getCollectionId() { return collectionId; }
    public void setCollectionId(long collectionId) { this.collectionId = collectionId; }

    public String getCollectionName() { return collectionName; }
    public void setCollectionName(String collectionName) { this.collectionName = collectionName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public long getScheduledTimestamp() { return scheduledTimestamp; }
    public void setScheduledTimestamp(long scheduledTimestamp) { this.scheduledTimestamp = scheduledTimestamp; }

    public String getRepeatType() { return repeatType; }
    public void setRepeatType(String repeatType) { this.repeatType = repeatType; }

    public boolean isEnabled() { return isEnabled; }
    public void setEnabled(boolean enabled) { isEnabled = enabled; }
}
