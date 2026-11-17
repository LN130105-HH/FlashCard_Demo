package com.crammr.studyflashcard.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "study_sessions")
public class StudySession {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long collectionId;
    private String collectionName;
    private long startTime;
    private long endTime;
    private int durationMinutes;
    private int cardsLearned;
    private int cardsRemembered;
    private int cardsNeedReview;

    public StudySession(long collectionId, String collectionName, long startTime, long endTime,
                        int durationMinutes, int cardsLearned, int cardsRemembered, int cardsNeedReview) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.cardsLearned = cardsLearned;
        this.cardsRemembered = cardsRemembered;
        this.cardsNeedReview = cardsNeedReview;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getCollectionId() { return collectionId; }
    public void setCollectionId(long collectionId) { this.collectionId = collectionId; }

    public String getCollectionName() { return collectionName; }
    public void setCollectionName(String collectionName) { this.collectionName = collectionName; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public int getCardsLearned() { return cardsLearned; }
    public void setCardsLearned(int cardsLearned) { this.cardsLearned = cardsLearned; }

    public int getCardsRemembered() { return cardsRemembered; }
    public void setCardsRemembered(int cardsRemembered) { this.cardsRemembered = cardsRemembered; }

    public int getCardsNeedReview() { return cardsNeedReview; }
    public void setCardsNeedReview(int cardsNeedReview) { this.cardsNeedReview = cardsNeedReview; }
}
