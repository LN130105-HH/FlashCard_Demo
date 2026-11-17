package com.crammr.studyflashcard.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.crammr.studyflashcard.data.local.entity.StudyHistory;
import com.crammr.studyflashcard.data.local.entity.StudySession;

import java.util.List;

@Dao
public interface StudySessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSession(StudySession session);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistory(StudyHistory history);

    @Query("SELECT * FROM study_sessions ORDER BY startTime DESC")
    LiveData<List<StudySession>> getAllSessionsLive();

    @Query("SELECT * FROM study_sessions ORDER BY startTime DESC")
    List<StudySession> getAllSessions();

    @Query("SELECT SUM(durationMinutes) FROM study_sessions")
    LiveData<Integer> getTotalStudyMinutesLive();

    @Query("SELECT SUM(durationMinutes) FROM study_sessions")
    Integer getTotalStudyMinutes();

    @Query("SELECT SUM(cardsLearned) FROM study_sessions")
    LiveData<Integer> getTotalCardsLearnedLive();

    @Query("SELECT * FROM study_history WHERE sessionId = :sessionId")
    List<StudyHistory> getHistoryBySession(long sessionId);
}
