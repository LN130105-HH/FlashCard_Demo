package com.crammr.studyflashcard.service;

import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DocumentExtractor {

    public static String extractTextFromUri(Context context, Uri uri, String fileName) {
        if (uri == null) return "";
        try {
            if (fileName != null && fileName.toLowerCase().endsWith(".docx")) {
                return extractDocxText(context, uri);
            } else if (fileName != null && fileName.toLowerCase().endsWith(".pdf")) {
                return extractPdfText(context, uri);
            } else {
                return extractPlainText(context, uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String extractDocxText(Context context, Uri uri) {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = context.getContentResolver().openInputStream(uri);
             ZipInputStream zip = new ZipInputStream(is)) {

            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if ("word/document.xml".equalsIgnoreCase(entry.getName())) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zip));
                    String line;
                    Pattern pattern = Pattern.compile("<w:t[^>]*>(.*?)</w:t>");
                    while ((line = reader.readLine()) != null) {
                        Matcher matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            sb.append(matcher.group(1)).append(" ");
                        }
                        if (line.contains("</w:p>")) {
                            sb.append("\n");
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString().trim();
    }

    private static String extractPdfText(Context context, Uri uri) {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = context.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Filter printable characters for simple text extraction
                String clean = line.replaceAll("[^\\x20-\\x7E]", " ").trim();
                if (clean.length() > 3 && !clean.startsWith("%PDF")) {
                    sb.append(clean).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString().trim();
    }

    private static String extractPlainText(Context context, Uri uri) {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = context.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString().trim();
    }
}
