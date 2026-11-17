package com.crammr.studyflashcard.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.crammr.studyflashcard.data.local.entity.AnswerOption;
import com.crammr.studyflashcard.data.local.entity.Exercise;
import com.crammr.studyflashcard.data.local.entity.Question;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertExercise(Exercise exercise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertQuestion(Question question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOptions(List<AnswerOption> options);

    @Query("SELECT * FROM exercises WHERE collectionId = :collectionId ORDER BY id DESC")
    LiveData<List<Exercise>> getExercisesByCollectionLive(long collectionId);

    @Query("SELECT * FROM exercises ORDER BY id DESC")
    LiveData<List<Exercise>> getAllExercisesLive();

    @Query("SELECT * FROM questions WHERE exerciseId = :exerciseId")
    List<Question> getQuestionsForExercise(long exerciseId);

    @Query("SELECT * FROM questions WHERE collectionId = :collectionId")
    List<Question> getQuestionsForCollection(long collectionId);

    @Query("SELECT * FROM answer_options WHERE questionId = :questionId")
    List<AnswerOption> getOptionsForQuestion(long questionId);

    @Query("SELECT COUNT(*) FROM exercises")
    LiveData<Integer> getTotalExercisesLive();
}
