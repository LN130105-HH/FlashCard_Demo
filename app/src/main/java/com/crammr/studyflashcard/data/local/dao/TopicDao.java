package com.crammr.studyflashcard.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.crammr.studyflashcard.data.local.entity.Topic;

import java.util.List;

@Dao
public interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Topic topic);

    @Update
    void update(Topic topic);

    @Delete
    void delete(Topic topic);

    @Query("SELECT * FROM topics WHERE subjectId = :subjectId ORDER BY name ASC")
    LiveData<List<Topic>> getTopicsBySubjectLive(long subjectId);

    @Query("SELECT * FROM topics WHERE subjectId = :subjectId ORDER BY name ASC")
    List<Topic> getTopicsBySubject(long subjectId);

    @Query("SELECT * FROM topics ORDER BY name ASC")
    LiveData<List<Topic>> getAllTopicsLive();
}
