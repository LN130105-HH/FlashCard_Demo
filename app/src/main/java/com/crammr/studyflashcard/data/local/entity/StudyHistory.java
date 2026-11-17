package com.crammr.studyflashcard.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "study_history")
public class StudyHistory {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long sessionId;
    private long collectionId;
    private long cardId;
    private boolean wasRemembered;
    private long timestamp;

    public StudyHistory(long sessionId, long collectionId, long cardId, boolean wasRemembered) {
        this.sessionId = sessionId;
        this.collectionId = collectionId;
        this.cardId = cardId;
        this.wasRemembered = wasRemembered;
        this.timestamp = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getSessionId() { return sessionId; }
    public void setSessionId(long sessionId) { this.sessionId = sessionId; }

    public long getCollectionId() { return collectionId; }
    public void setCollectionId(long collectionId) { this.collectionId = collectionId; }

    public long getCardId() { return cardId; }
    public void setCardId(long cardId) { this.cardId = cardId; }

    public boolean isWasRemembered() { return wasRemembered; }
    public void setWasRemembered(boolean wasRemembered) { this.wasRemembered = wasRemembered; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
