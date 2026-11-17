package com.crammr.studyflashcard.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "collections")
public class FlashcardCollection {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long subjectId;
    private long topicId;
    private String name;
    private String description;
    private int cardCount;
    private int progress; // 0 to 100%
    private long createdAt;
    private long lastStudiedAt;

    public FlashcardCollection(long subjectId, long topicId, String name, String description) {
        this.subjectId = subjectId;
        this.topicId = topicId;
        this.name = name;
        this.description = description;
        this.cardCount = 0;
        this.progress = 0;
        this.createdAt = System.currentTimeMillis();
        this.lastStudiedAt = 0;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getSubjectId() { return subjectId; }
    public void setSubjectId(long subjectId) { this.subjectId = subjectId; }

    public long getTopicId() { return topicId; }
    public void setTopicId(long topicId) { this.topicId = topicId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCardCount() { return cardCount; }
    public void setCardCount(int cardCount) { this.cardCount = cardCount; }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getLastStudiedAt() { return lastStudiedAt; }
    public void setLastStudiedAt(long lastStudiedAt) { this.lastStudiedAt = lastStudiedAt; }
}
