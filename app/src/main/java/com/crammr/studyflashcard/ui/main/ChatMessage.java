package com.crammr.studyflashcard.ui.main;

import com.crammr.studyflashcard.data.local.entity.Flashcard;

import java.util.List;

public class ChatMessage {
    public static final int TYPE_USER = 1;
    public static final int TYPE_AI = 2;

    private int type;
    private String text;
    private long timestamp;
    private List<Flashcard> generatedCards;

    public ChatMessage(int type, String text) {
        this.type = type;
        this.text = text;
        this.timestamp = System.currentTimeMillis();
    }

    public ChatMessage(int type, String text, List<Flashcard> generatedCards) {
        this.type = type;
        this.text = text;
        this.timestamp = System.currentTimeMillis();
        this.generatedCards = generatedCards;
    }

    public int getType() { return type; }
    public String getText() { return text; }
    public long getTimestamp() { return timestamp; }
    public List<Flashcard> getGeneratedCards() { return generatedCards; }
}
