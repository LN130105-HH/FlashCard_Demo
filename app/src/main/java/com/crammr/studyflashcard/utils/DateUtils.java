package com.crammr.studyflashcard.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String formatDateTime(long timestamp) {
        if (timestamp <= 0) return "Never";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String formatDateOnly(long timestamp) {
        if (timestamp <= 0) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String formatTimeOnly(long timestamp) {
        if (timestamp <= 0) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String formatDuration(int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        if (mins > 0) {
            return mins + "m " + secs + "s";
        }
        return secs + "s";
    }
}
