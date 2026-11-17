package com.crammr.studyflashcard.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.widget.Toast;

public class GoogleCalendarHelper {

    public static void addToGoogleCalendar(Context context, String title, String description, long startTimeMillis) {
        try {
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeMillis)
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startTimeMillis + 30 * 60 * 1000) // 30 min session
                    .putExtra(CalendarContract.Events.TITLE, "Crammr Study: " + title)
                    .putExtra(CalendarContract.Events.DESCRIPTION, description)
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "No calendar app found to sync event", Toast.LENGTH_SHORT).show();
        }
    }
}
