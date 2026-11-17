package com.crammr.studyflashcard.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.crammr.studyflashcard.data.local.entity.Flashcard;

import java.util.List;

@Dao
public interface FlashcardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Flashcard flashcard);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Flashcard> flashcards);

    @Update
    void update(Flashcard flashcard);

    @Delete
    void delete(Flashcard flashcard);

    @Query("SELECT * FROM flashcards WHERE collectionId = :collectionId ORDER BY id ASC")
    LiveData<List<Flashcard>> getCardsByCollectionLive(long collectionId);

    @Query("SELECT * FROM flashcards WHERE collectionId = :collectionId ORDER BY id ASC")
    List<Flashcard> getCardsByCollection(long collectionId);

    @Query("SELECT * FROM flashcards WHERE id = :id LIMIT 1")
    Flashcard getCardById(long id);

    @Query("SELECT COUNT(*) FROM flashcards WHERE collectionId = :collectionId")
    int getCardCountByCollection(long collectionId);

    @Query("SELECT COUNT(*) FROM flashcards WHERE collectionId = :collectionId AND isRemembered = 1")
    int getRememberedCountByCollection(long collectionId);

    @Query("SELECT COUNT(*) FROM flashcards")
    LiveData<Integer> getTotalCardsLive();

    @Query("SELECT COUNT(*) FROM flashcards")
    int getTotalCards();

    @Query("SELECT COUNT(*) FROM flashcards WHERE isRemembered = 1")
    LiveData<Integer> getTotalRememberedCardsLive();

    @Query("SELECT COUNT(*) FROM flashcards WHERE isRemembered = 1")
    int getTotalRememberedCards();

    @Query("DELETE FROM flashcards WHERE collectionId = :collectionId")
    void deleteByCollection(long collectionId);
}
