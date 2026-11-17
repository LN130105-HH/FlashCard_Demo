package com.crammr.studyflashcard.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "answer_options")
public class AnswerOption {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long questionId;
    private String optionText;
    private boolean isCorrect;

    public AnswerOption(long questionId, String optionText, boolean isCorrect) {
        this.questionId = questionId;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getQuestionId() { return questionId; }
    public void setQuestionId(long questionId) { this.questionId = questionId; }

    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }

    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }
}
