package com.crammr.studyflashcard.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class NotificationItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String message;
    private String type; // "REMINDER", "SYSTEM", "ACHIEVEMENT"
    private boolean isRead;
    private long createdAt;
    private String actionPayload;

    public NotificationItem(String title, String message, String type) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.isRead = false;
        this.createdAt = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public String getActionPayload() { return actionPayload; }
    public void setActionPayload(String actionPayload) { this.actionPayload = actionPayload; }
}
