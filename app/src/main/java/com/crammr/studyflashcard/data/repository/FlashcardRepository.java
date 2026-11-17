package com.crammr.studyflashcard.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.crammr.studyflashcard.data.local.AppDatabase;
import com.crammr.studyflashcard.data.local.dao.CollectionDao;
import com.crammr.studyflashcard.data.local.dao.FlashcardDao;
import com.crammr.studyflashcard.data.local.dao.SubjectDao;
import com.crammr.studyflashcard.data.local.dao.TopicDao;
import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.local.entity.Subject;
import com.crammr.studyflashcard.data.local.entity.Topic;

import java.util.List;
import java.util.concurrent.Future;

public class FlashcardRepository {
    private final CollectionDao collectionDao;
    private final FlashcardDao flashcardDao;
    private final SubjectDao subjectDao;
    private final TopicDao topicDao;

    public FlashcardRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        collectionDao = db.collectionDao();
        flashcardDao = db.flashcardDao();
        subjectDao = db.subjectDao();
        topicDao = db.topicDao();
    }

    public LiveData<List<FlashcardCollection>> getAllCollections() {
        return collectionDao.getAllCollectionsLive();
    }

    public LiveData<List<FlashcardCollection>> getRecentCollections(int limit) {
        return collectionDao.getRecentCollectionsLive(limit);
    }

    public LiveData<FlashcardCollection> getCollectionByIdLive(long id) {
        return collectionDao.getCollectionByIdLive(id);
    }

    public FlashcardCollection getCollectionById(long id) {
        return collectionDao.getCollectionById(id);
    }

    public LiveData<List<FlashcardCollection>> searchCollections(String query) {
        return collectionDao.searchCollections(query);
    }

    public void insertCollection(FlashcardCollection collection, OnCollectionInsertedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long id = collectionDao.insert(collection);
            if (listener != null) {
                listener.onInserted(id);
            }
        });
    }

    public void updateCollection(FlashcardCollection collection) {
        AppDatabase.databaseWriteExecutor.execute(() -> collectionDao.update(collection));
    }

    public void deleteCollection(FlashcardCollection collection) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            flashcardDao.deleteByCollection(collection.getId());
            collectionDao.delete(collection);
        });
    }

    public LiveData<List<Flashcard>> getCardsByCollectionLive(long collectionId) {
        return flashcardDao.getCardsByCollectionLive(collectionId);
    }

    public List<Flashcard> getCardsByCollection(long collectionId) {
        return flashcardDao.getCardsByCollection(collectionId);
    }

    public void insertFlashcard(Flashcard card) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            flashcardDao.insert(card);
            updateCollectionCardCount(card.getCollectionId());
        });
    }

    public void insertFlashcards(List<Flashcard> cards, long collectionId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            flashcardDao.insertAll(cards);
            updateCollectionCardCount(collectionId);
        });
    }

    public void updateFlashcard(Flashcard card) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            flashcardDao.update(card);
            updateCollectionCardCount(card.getCollectionId());
        });
    }

    public void deleteFlashcard(Flashcard card) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            flashcardDao.delete(card);
            updateCollectionCardCount(card.getCollectionId());
        });
    }

    private void updateCollectionCardCount(long collectionId) {
        int count = flashcardDao.getCardCountByCollection(collectionId);
        int remembered = flashcardDao.getRememberedCountByCollection(collectionId);
        FlashcardCollection col = collectionDao.getCollectionById(collectionId);
        if (col != null) {
            col.setCardCount(count);
            col.setProgress(count > 0 ? (remembered * 100 / count) : 0);
            collectionDao.update(col);
        }
    }

    public LiveData<Integer> getTotalCardsLive() {
        return flashcardDao.getTotalCardsLive();
    }

    public LiveData<Integer> getTotalRememberedCardsLive() {
        return flashcardDao.getTotalRememberedCardsLive();
    }

    public LiveData<List<Subject>> getAllSubjects() {
        return subjectDao.getAllSubjectsLive();
    }

    public void insertSubject(Subject subject) {
        AppDatabase.databaseWriteExecutor.execute(() -> subjectDao.insert(subject));
    }

    public LiveData<List<Topic>> getTopicsBySubject(long subjectId) {
        return topicDao.getTopicsBySubjectLive(subjectId);
    }

    public interface OnCollectionInsertedListener {
        void onInserted(long collectionId);
    }
}
