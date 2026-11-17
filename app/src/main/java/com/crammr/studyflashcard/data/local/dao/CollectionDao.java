package com.crammr.studyflashcard.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;

import java.util.List;

@Dao
public interface CollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FlashcardCollection collection);

    @Update
    void update(FlashcardCollection collection);

    @Delete
    void delete(FlashcardCollection collection);

    @Query("SELECT * FROM collections ORDER BY lastStudiedAt DESC, id DESC")
    LiveData<List<FlashcardCollection>> getAllCollectionsLive();

    @Query("SELECT * FROM collections ORDER BY lastStudiedAt DESC, id DESC")
    List<FlashcardCollection> getAllCollections();

    @Query("SELECT * FROM collections ORDER BY lastStudiedAt DESC LIMIT :limit")
    LiveData<List<FlashcardCollection>> getRecentCollectionsLive(int limit);

    @Query("SELECT * FROM collections WHERE id = :id LIMIT 1")
    FlashcardCollection getCollectionById(long id);

    @Query("SELECT * FROM collections WHERE id = :id LIMIT 1")
    LiveData<FlashcardCollection> getCollectionByIdLive(long id);

    @Query("SELECT * FROM collections WHERE subjectId = :subjectId")
    LiveData<List<FlashcardCollection>> getCollectionsBySubject(long subjectId);

    @Query("SELECT * FROM collections WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    LiveData<List<FlashcardCollection>> searchCollections(String query);

    @Query("SELECT COUNT(*) FROM collections")
    LiveData<Integer> getCollectionCountLive();

    @Query("SELECT COUNT(*) FROM collections")
    int getCollectionCount();
}
