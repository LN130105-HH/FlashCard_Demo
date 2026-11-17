package com.crammr.studyflashcard.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class Question {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long exerciseId;
    private long collectionId;
    private String questionText;
    private String explanation;

    public Question(long exerciseId, long collectionId, String questionText, String explanation) {
        this.exerciseId = exerciseId;
        this.collectionId = collectionId;
        this.questionText = questionText;
        this.explanation = explanation;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getExerciseId() { return exerciseId; }
    public void setExerciseId(long exerciseId) { this.exerciseId = exerciseId; }

    public long getCollectionId() { return collectionId; }
    public void setCollectionId(long collectionId) { this.collectionId = collectionId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}
