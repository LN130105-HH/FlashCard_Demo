package com.crammr.studyflashcard.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quiz_results")
public class QuizResult {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long collectionId;
    private String collectionName;
    private int totalQuestions;
    private int correctCount;
    private int incorrectCount;
    private int scorePercent;
    private int durationSeconds;
    private long completedAt;

    public QuizResult(long collectionId, String collectionName, int totalQuestions,
                      int correctCount, int incorrectCount, int scorePercent, int durationSeconds) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.totalQuestions = totalQuestions;
        this.correctCount = correctCount;
        this.incorrectCount = incorrectCount;
        this.scorePercent = scorePercent;
        this.durationSeconds = durationSeconds;
        this.completedAt = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getCollectionId() { return collectionId; }
    public void setCollectionId(long collectionId) { this.collectionId = collectionId; }

    public String getCollectionName() { return collectionName; }
    public void setCollectionName(String collectionName) { this.collectionName = collectionName; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public int getCorrectCount() { return correctCount; }
    public void setCorrectCount(int correctCount) { this.correctCount = correctCount; }

    public int getIncorrectCount() { return incorrectCount; }
    public void setIncorrectCount(int incorrectCount) { this.incorrectCount = incorrectCount; }

    public int getScorePercent() { return scorePercent; }
    public void setScorePercent(int scorePercent) { this.scorePercent = scorePercent; }

    public int getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }

    public long getCompletedAt() { return completedAt; }
    public void setCompletedAt(long completedAt) { this.completedAt = completedAt; }
}
