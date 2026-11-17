package com.crammr.studyflashcard.ui.study;

import java.io.Serializable;
import java.util.List;

public class QuizQuestionModel implements Serializable {
    private String question;
    private List<String> options;
    private int correctOptionIndex;
    private String explanation;
    private int selectedOptionIndex = -1;

    public QuizQuestionModel(String question, List<String> options, int correctOptionIndex, String explanation) {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.explanation = explanation;
    }

    public String getQuestion() { return question; }
    public List<String> getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }
    public String getExplanation() { return explanation; }

    public int getSelectedOptionIndex() { return selectedOptionIndex; }
    public void setSelectedOptionIndex(int selectedOptionIndex) { this.selectedOptionIndex = selectedOptionIndex; }

    public boolean isAnswered() {
        return selectedOptionIndex != -1;
    }

    public boolean isCorrect() {
        return selectedOptionIndex == correctOptionIndex;
    }
}
