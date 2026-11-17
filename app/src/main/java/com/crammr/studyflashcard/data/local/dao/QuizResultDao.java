package com.crammr.studyflashcard.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.crammr.studyflashcard.data.local.entity.QuizResult;

import java.util.List;

@Dao
public interface QuizResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(QuizResult result);

    @Query("SELECT * FROM quiz_results ORDER BY completedAt DESC")
    LiveData<List<QuizResult>> getAllResultsLive();

    @Query("SELECT * FROM quiz_results ORDER BY completedAt DESC")
    List<QuizResult> getAllResults();

    @Query("SELECT * FROM quiz_results WHERE collectionId = :collectionId ORDER BY completedAt DESC")
    LiveData<List<QuizResult>> getResultsByCollection(long collectionId);

    @Query("SELECT AVG(scorePercent) FROM quiz_results")
    LiveData<Float> getAverageScoreLive();

    @Query("SELECT COUNT(*) FROM quiz_results")
    LiveData<Integer> getTotalQuizzesLive();
}
