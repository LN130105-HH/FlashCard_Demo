package com.crammr.studyflashcard.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flashcards")
public class Flashcard {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long collectionId;
    private String question;
    private String answer;
    private String explanation;
    private int rememberedCount;
    private int notRememberedCount;
    private boolean isRemembered;
    private long lastReviewedAt;

    public Flashcard(long collectionId, String question, String answer, String explanation) {
        this.collectionId = collectionId;
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.rememberedCount = 0;
        this.notRememberedCount = 0;
        this.isRemembered = false;
        this.lastReviewedAt = 0;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getCollectionId() { return collectionId; }
    public void setCollectionId(long collectionId) { this.collectionId = collectionId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public int getRememberedCount() { return rememberedCount; }
    public void setRememberedCount(int rememberedCount) { this.rememberedCount = rememberedCount; }

    public int getNotRememberedCount() { return notRememberedCount; }
    public void setNotRememberedCount(int notRememberedCount) { this.notRememberedCount = notRememberedCount; }

    public boolean isRemembered() { return isRemembered; }
    public void setRemembered(boolean remembered) { isRemembered = remembered; }

    public long getLastReviewedAt() { return lastReviewedAt; }
    public void setLastReviewedAt(long lastReviewedAt) { this.lastReviewedAt = lastReviewedAt; }
}
