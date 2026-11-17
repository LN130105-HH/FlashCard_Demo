package com.crammr.studyflashcard.ui.importexport;

import android.content.Context;
import android.net.Uri;

import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImportExportHelper {

    public static String exportToJson(List<Flashcard> cards) {
        Gson gson = new Gson();
        return gson.toJson(cards);
    }

    public static String exportToCsv(List<Flashcard> cards) {
        StringBuilder sb = new StringBuilder();
        sb.append("Question,Answer,Explanation\n");
        for (Flashcard card : cards) {
            String q = escapeCsv(card.getQuestion());
            String a = escapeCsv(card.getAnswer());
            String exp = escapeCsv(card.getExplanation() != null ? card.getExplanation() : "");
            sb.append(q).append(",").append(a).append(",").append(exp).append("\n");
        }
        return sb.toString();
    }

    public static List<Flashcard> importFromJson(String json, long collectionId) {
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Flashcard>>() {}.getType();
            List<Flashcard> imported = gson.fromJson(json, listType);
            if (imported != null) {
                for (Flashcard c : imported) {
                    c.setId(0); // auto-generate
                    c.setCollectionId(collectionId);
                }
                return imported;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<Flashcard> importFromCsv(Context context, Uri uri, long collectionId) {
        List<Flashcard> list = new ArrayList<>();
        try (InputStream is = context.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine && line.toLowerCase().contains("question")) {
                    firstLine = false;
                    continue;
                }
                firstLine = false;

                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length >= 2) {
                    String q = unescapeCsv(parts[0]);
                    String a = unescapeCsv(parts[1]);
                    String exp = parts.length > 2 ? unescapeCsv(parts[2]) : "";
                    if (!q.isEmpty() && !a.isEmpty()) {
                        list.add(new Flashcard(collectionId, q, a, exp));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String escapeCsv(String text) {
        if (text == null) return "";
        if (text.contains(",") || text.contains("\"") || text.contains("\n")) {
            return "\"" + text.replace("\"", "\"\"") + "\"";
        }
        return text;
    }

    private static String unescapeCsv(String text) {
        if (text == null) return "";
        String trimmed = text.trim();
        if (trimmed.startsWith("\"") && trimmed.endsWith("\"") && trimmed.length() >= 2) {
            trimmed = trimmed.substring(1, trimmed.length() - 1).replace("\"\"", "\"");
        }
        return trimmed;
    }
}
