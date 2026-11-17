package com.crammr.studyflashcard.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "crammr_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";

    private static final String KEY_NOTIF_DAILY_REMINDER = "notif_daily_reminder";
    private static final String KEY_NOTIF_PROGRESS = "notif_progress";
    private static final String KEY_NOTIF_TIPS = "notif_tips";
    private static final String KEY_GCAL_CONNECTED = "gcal_connected";

    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean value) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply();
    }

    public boolean isRememberMe() {
        return prefs.getBoolean(KEY_REMEMBER_ME, false);
    }

    public void setRememberMe(boolean value) {
        prefs.edit().putBoolean(KEY_REMEMBER_ME, value).apply();
    }

    public String getUserEmail() {
        return prefs.getString(KEY_USER_EMAIL, "learner@crammr.app");
    }

    public void setUserEmail(String email) {
        prefs.edit().putString(KEY_USER_EMAIL, email).apply();
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, "Alex Rivera");
    }

    public void setUserName(String name) {
        prefs.edit().putString(KEY_USER_NAME, name).apply();
    }

    public boolean isDailyReminderEnabled() {
        return prefs.getBoolean(KEY_NOTIF_DAILY_REMINDER, true);
    }

    public void setDailyReminderEnabled(boolean value) {
        prefs.edit().putBoolean(KEY_NOTIF_DAILY_REMINDER, value).apply();
    }

    public boolean isProgressNotificationEnabled() {
        return prefs.getBoolean(KEY_NOTIF_PROGRESS, true);
    }

    public void setProgressNotificationEnabled(boolean value) {
        prefs.edit().putBoolean(KEY_NOTIF_PROGRESS, value).apply();
    }

    public boolean isTipsNotificationEnabled() {
        return prefs.getBoolean(KEY_NOTIF_TIPS, true);
    }

    public void setTipsNotificationEnabled(boolean value) {
        prefs.edit().putBoolean(KEY_NOTIF_TIPS, value).apply();
    }

    public boolean isGoogleCalendarConnected() {
        return prefs.getBoolean(KEY_GCAL_CONNECTED, false);
    }

    public void setGoogleCalendarConnected(boolean value) {
        prefs.edit().putBoolean(KEY_GCAL_CONNECTED, value).apply();
    }

    public void clearSession() {
        prefs.edit()
                .putBoolean(KEY_IS_LOGGED_IN, false)
                .remove(KEY_USER_EMAIL)
                .remove(KEY_USER_NAME)
                .apply();
    }
}
