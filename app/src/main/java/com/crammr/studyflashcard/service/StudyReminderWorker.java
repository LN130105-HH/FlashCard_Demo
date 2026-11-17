package com.crammr.studyflashcard.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class StudyReminderWorker extends Worker {

    public static final String KEY_TITLE = "reminder_title";
    public static final String KEY_COLLECTION_NAME = "reminder_col_name";

    public StudyReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String title = getInputData().getString(KEY_TITLE);
        String colName = getInputData().getString(KEY_COLLECTION_NAME);

        if (title == null || title.isEmpty()) {
            title = "Time to study!";
        }
        String msg = "Your review session" + (colName != null ? " for " + colName : "") + " is scheduled now. Keep your streak going!";

        NotificationHelper.showStudyReminderNotification(getApplicationContext(), title, msg);
        return Result.success();
    }
}
