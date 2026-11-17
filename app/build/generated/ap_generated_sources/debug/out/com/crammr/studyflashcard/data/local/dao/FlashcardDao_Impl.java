package com.crammr.studyflashcard.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.crammr.studyflashcard.data.local.entity.Flashcard;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FlashcardDao_Impl implements FlashcardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Flashcard> __insertionAdapterOfFlashcard;

  private final EntityDeletionOrUpdateAdapter<Flashcard> __deletionAdapterOfFlashcard;

  private final EntityDeletionOrUpdateAdapter<Flashcard> __updateAdapterOfFlashcard;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByCollection;

  public FlashcardDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFlashcard = new EntityInsertionAdapter<Flashcard>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `flashcards` (`id`,`collectionId`,`question`,`answer`,`explanation`,`rememberedCount`,`notRememberedCount`,`isRemembered`,`lastReviewedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Flashcard entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCollectionId());
        if (entity.getQuestion() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getQuestion());
        }
        if (entity.getAnswer() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAnswer());
        }
        if (entity.getExplanation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getExplanation());
        }
        statement.bindLong(6, entity.getRememberedCount());
        statement.bindLong(7, entity.getNotRememberedCount());
        final int _tmp = entity.isRemembered() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getLastReviewedAt());
      }
    };
    this.__deletionAdapterOfFlashcard = new EntityDeletionOrUpdateAdapter<Flashcard>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `flashcards` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Flashcard entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfFlashcard = new EntityDeletionOrUpdateAdapter<Flashcard>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `flashcards` SET `id` = ?,`collectionId` = ?,`question` = ?,`answer` = ?,`explanation` = ?,`rememberedCount` = ?,`notRememberedCount` = ?,`isRemembered` = ?,`lastReviewedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Flashcard entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCollectionId());
        if (entity.getQuestion() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getQuestion());
        }
        if (entity.getAnswer() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAnswer());
        }
        if (entity.getExplanation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getExplanation());
        }
        statement.bindLong(6, entity.getRememberedCount());
        statement.bindLong(7, entity.getNotRememberedCount());
        final int _tmp = entity.isRemembered() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getLastReviewedAt());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByCollection = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM flashcards WHERE collectionId = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Flashcard flashcard) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfFlashcard.insertAndReturnId(flashcard);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<Flashcard> flashcards) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFlashcard.insert(flashcards);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Flashcard flashcard) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfFlashcard.handle(flashcard);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Flashcard flashcard) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfFlashcard.handle(flashcard);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteByCollection(final long collectionId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByCollection.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, collectionId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteByCollection.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Flashcard>> getCardsByCollectionLive(final long collectionId) {
    final String _sql = "SELECT * FROM flashcards WHERE collectionId = ? ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, collectionId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"flashcards"}, false, new Callable<List<Flashcard>>() {
      @Override
      @Nullable
      public List<Flashcard> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final int _cursorIndexOfRememberedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "rememberedCount");
          final int _cursorIndexOfNotRememberedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "notRememberedCount");
          final int _cursorIndexOfIsRemembered = CursorUtil.getColumnIndexOrThrow(_cursor, "isRemembered");
          final int _cursorIndexOfLastReviewedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewedAt");
          final List<Flashcard> _result = new ArrayList<Flashcard>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Flashcard _item;
            final long _tmpCollectionId;
            _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpAnswer;
            if (_cursor.isNull(_cursorIndexOfAnswer)) {
              _tmpAnswer = null;
            } else {
              _tmpAnswer = _cursor.getString(_cursorIndexOfAnswer);
            }
            final String _tmpExplanation;
            if (_cursor.isNull(_cursorIndexOfExplanation)) {
              _tmpExplanation = null;
            } else {
              _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            }
            _item = new Flashcard(_tmpCollectionId,_tmpQuestion,_tmpAnswer,_tmpExplanation);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpRememberedCount;
            _tmpRememberedCount = _cursor.getInt(_cursorIndexOfRememberedCount);
            _item.setRememberedCount(_tmpRememberedCount);
            final int _tmpNotRememberedCount;
            _tmpNotRememberedCount = _cursor.getInt(_cursorIndexOfNotRememberedCount);
            _item.setNotRememberedCount(_tmpNotRememberedCount);
            final boolean _tmpIsRemembered;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsRemembered);
            _tmpIsRemembered = _tmp != 0;
            _item.setRemembered(_tmpIsRemembered);
            final long _tmpLastReviewedAt;
            _tmpLastReviewedAt = _cursor.getLong(_cursorIndexOfLastReviewedAt);
            _item.setLastReviewedAt(_tmpLastReviewedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Flashcard> getCardsByCollection(final long collectionId) {
    final String _sql = "SELECT * FROM flashcards WHERE collectionId = ? ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, collectionId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final int _cursorIndexOfRememberedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "rememberedCount");
      final int _cursorIndexOfNotRememberedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "notRememberedCount");
      final int _cursorIndexOfIsRemembered = CursorUtil.getColumnIndexOrThrow(_cursor, "isRemembered");
      final int _cursorIndexOfLastReviewedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewedAt");
      final List<Flashcard> _result = new ArrayList<Flashcard>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Flashcard _item;
        final long _tmpCollectionId;
        _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
        final String _tmpQuestion;
        if (_cursor.isNull(_cursorIndexOfQuestion)) {
          _tmpQuestion = null;
        } else {
          _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
        }
        final String _tmpAnswer;
        if (_cursor.isNull(_cursorIndexOfAnswer)) {
          _tmpAnswer = null;
        } else {
          _tmpAnswer = _cursor.getString(_cursorIndexOfAnswer);
        }
        final String _tmpExplanation;
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _tmpExplanation = null;
        } else {
          _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        _item = new Flashcard(_tmpCollectionId,_tmpQuestion,_tmpAnswer,_tmpExplanation);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpRememberedCount;
        _tmpRememberedCount = _cursor.getInt(_cursorIndexOfRememberedCount);
        _item.setRememberedCount(_tmpRememberedCount);
        final int _tmpNotRememberedCount;
        _tmpNotRememberedCount = _cursor.getInt(_cursorIndexOfNotRememberedCount);
        _item.setNotRememberedCount(_tmpNotRememberedCount);
        final boolean _tmpIsRemembered;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsRemembered);
        _tmpIsRemembered = _tmp != 0;
        _item.setRemembered(_tmpIsRemembered);
        final long _tmpLastReviewedAt;
        _tmpLastReviewedAt = _cursor.getLong(_cursorIndexOfLastReviewedAt);
        _item.setLastReviewedAt(_tmpLastReviewedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Flashcard getCardById(final long id) {
    final String _sql = "SELECT * FROM flashcards WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final int _cursorIndexOfRememberedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "rememberedCount");
      final int _cursorIndexOfNotRememberedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "notRememberedCount");
      final int _cursorIndexOfIsRemembered = CursorUtil.getColumnIndexOrThrow(_cursor, "isRemembered");
      final int _cursorIndexOfLastReviewedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewedAt");
      final Flashcard _result;
      if (_cursor.moveToFirst()) {
        final long _tmpCollectionId;
        _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
        final String _tmpQuestion;
        if (_cursor.isNull(_cursorIndexOfQuestion)) {
          _tmpQuestion = null;
        } else {
          _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
        }
        final String _tmpAnswer;
        if (_cursor.isNull(_cursorIndexOfAnswer)) {
          _tmpAnswer = null;
        } else {
          _tmpAnswer = _cursor.getString(_cursorIndexOfAnswer);
        }
        final String _tmpExplanation;
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _tmpExplanation = null;
        } else {
          _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        _result = new Flashcard(_tmpCollectionId,_tmpQuestion,_tmpAnswer,_tmpExplanation);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpRememberedCount;
        _tmpRememberedCount = _cursor.getInt(_cursorIndexOfRememberedCount);
        _result.setRememberedCount(_tmpRememberedCount);
        final int _tmpNotRememberedCount;
        _tmpNotRememberedCount = _cursor.getInt(_cursorIndexOfNotRememberedCount);
        _result.setNotRememberedCount(_tmpNotRememberedCount);
        final boolean _tmpIsRemembered;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsRemembered);
        _tmpIsRemembered = _tmp != 0;
        _result.setRemembered(_tmpIsRemembered);
        final long _tmpLastReviewedAt;
        _tmpLastReviewedAt = _cursor.getLong(_cursorIndexOfLastReviewedAt);
        _result.setLastReviewedAt(_tmpLastReviewedAt);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCardCountByCollection(final long collectionId) {
    final String _sql = "SELECT COUNT(*) FROM flashcards WHERE collectionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, collectionId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getRememberedCountByCollection(final long collectionId) {
    final String _sql = "SELECT COUNT(*) FROM flashcards WHERE collectionId = ? AND isRemembered = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, collectionId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getTotalCardsLive() {
    final String _sql = "SELECT COUNT(*) FROM flashcards";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"flashcards"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getTotalCards() {
    final String _sql = "SELECT COUNT(*) FROM flashcards";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getTotalRememberedCardsLive() {
    final String _sql = "SELECT COUNT(*) FROM flashcards WHERE isRemembered = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"flashcards"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getTotalRememberedCards() {
    final String _sql = "SELECT COUNT(*) FROM flashcards WHERE isRemembered = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
