package com.crammr.studyflashcard.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.crammr.studyflashcard.data.local.entity.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Subject subject);

    @Update
    void update(Subject subject);

    @Delete
    void delete(Subject subject);

    @Query("SELECT * FROM subjects ORDER BY name ASC")
    LiveData<List<Subject>> getAllSubjectsLive();

    @Query("SELECT * FROM subjects ORDER BY name ASC")
    List<Subject> getAllSubjects();

    @Query("SELECT * FROM subjects WHERE id = :id LIMIT 1")
    Subject getSubjectById(long id);
}
