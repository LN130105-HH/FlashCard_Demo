package com.crammr.studyflashcard.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.crammr.studyflashcard.data.local.entity.StudyHistory;
import com.crammr.studyflashcard.data.local.entity.StudySession;
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
public final class StudySessionDao_Impl implements StudySessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StudySession> __insertionAdapterOfStudySession;

  private final EntityInsertionAdapter<StudyHistory> __insertionAdapterOfStudyHistory;

  public StudySessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudySession = new EntityInsertionAdapter<StudySession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `study_sessions` (`id`,`collectionId`,`collectionName`,`startTime`,`endTime`,`durationMinutes`,`cardsLearned`,`cardsRemembered`,`cardsNeedReview`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final StudySession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCollectionId());
        if (entity.getCollectionName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCollectionName());
        }
        statement.bindLong(4, entity.getStartTime());
        statement.bindLong(5, entity.getEndTime());
        statement.bindLong(6, entity.getDurationMinutes());
        statement.bindLong(7, entity.getCardsLearned());
        statement.bindLong(8, entity.getCardsRemembered());
        statement.bindLong(9, entity.getCardsNeedReview());
      }
    };
    this.__insertionAdapterOfStudyHistory = new EntityInsertionAdapter<StudyHistory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `study_history` (`id`,`sessionId`,`collectionId`,`cardId`,`wasRemembered`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final StudyHistory entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSessionId());
        statement.bindLong(3, entity.getCollectionId());
        statement.bindLong(4, entity.getCardId());
        final int _tmp = entity.isWasRemembered() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getTimestamp());
      }
    };
  }

  @Override
  public long insertSession(final StudySession session) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfStudySession.insertAndReturnId(session);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertHistory(final StudyHistory history) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfStudyHistory.insert(history);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<StudySession>> getAllSessionsLive() {
    final String _sql = "SELECT * FROM study_sessions ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"study_sessions"}, false, new Callable<List<StudySession>>() {
      @Override
      @Nullable
      public List<StudySession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfCollectionName = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionName");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfCardsLearned = CursorUtil.getColumnIndexOrThrow(_cursor, "cardsLearned");
          final int _cursorIndexOfCardsRemembered = CursorUtil.getColumnIndexOrThrow(_cursor, "cardsRemembered");
          final int _cursorIndexOfCardsNeedReview = CursorUtil.getColumnIndexOrThrow(_cursor, "cardsNeedReview");
          final List<StudySession> _result = new ArrayList<StudySession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySession _item;
            final long _tmpCollectionId;
            _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            final String _tmpCollectionName;
            if (_cursor.isNull(_cursorIndexOfCollectionName)) {
              _tmpCollectionName = null;
            } else {
              _tmpCollectionName = _cursor.getString(_cursorIndexOfCollectionName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final int _tmpCardsLearned;
            _tmpCardsLearned = _cursor.getInt(_cursorIndexOfCardsLearned);
            final int _tmpCardsRemembered;
            _tmpCardsRemembered = _cursor.getInt(_cursorIndexOfCardsRemembered);
            final int _tmpCardsNeedReview;
            _tmpCardsNeedReview = _cursor.getInt(_cursorIndexOfCardsNeedReview);
            _item = new StudySession(_tmpCollectionId,_tmpCollectionName,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCardsLearned,_tmpCardsRemembered,_tmpCardsNeedReview);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
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
  public List<StudySession> getAllSessions() {
    final String _sql = "SELECT * FROM study_sessions ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
      final int _cursorIndexOfCollectionName = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionName");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
      final int _cursorIndexOfCardsLearned = CursorUtil.getColumnIndexOrThrow(_cursor, "cardsLearned");
      final int _cursorIndexOfCardsRemembered = CursorUtil.getColumnIndexOrThrow(_cursor, "cardsRemembered");
      final int _cursorIndexOfCardsNeedReview = CursorUtil.getColumnIndexOrThrow(_cursor, "cardsNeedReview");
      final List<StudySession> _result = new ArrayList<StudySession>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final StudySession _item;
        final long _tmpCollectionId;
        _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
        final String _tmpCollectionName;
        if (_cursor.isNull(_cursorIndexOfCollectionName)) {
          _tmpCollectionName = null;
        } else {
          _tmpCollectionName = _cursor.getString(_cursorIndexOfCollectionName);
        }
        final long _tmpStartTime;
        _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
        final long _tmpEndTime;
        _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
        final int _tmpDurationMinutes;
        _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
        final int _tmpCardsLearned;
        _tmpCardsLearned = _cursor.getInt(_cursorIndexOfCardsLearned);
        final int _tmpCardsRemembered;
        _tmpCardsRemembered = _cursor.getInt(_cursorIndexOfCardsRemembered);
        final int _tmpCardsNeedReview;
        _tmpCardsNeedReview = _cursor.getInt(_cursorIndexOfCardsNeedReview);
        _item = new StudySession(_tmpCollectionId,_tmpCollectionName,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCardsLearned,_tmpCardsRemembered,_tmpCardsNeedReview);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getTotalStudyMinutesLive() {
    final String _sql = "SELECT SUM(durationMinutes) FROM study_sessions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"study_sessions"}, false, new Callable<Integer>() {
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
  public Integer getTotalStudyMinutes() {
    final String _sql = "SELECT SUM(durationMinutes) FROM study_sessions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
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
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getTotalCardsLearnedLive() {
    final String _sql = "SELECT SUM(cardsLearned) FROM study_sessions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"study_sessions"}, false, new Callable<Integer>() {
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
  public List<StudyHistory> getHistoryBySession(final long sessionId) {
    final String _sql = "SELECT * FROM study_history WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
      final int _cursorIndexOfCardId = CursorUtil.getColumnIndexOrThrow(_cursor, "cardId");
      final int _cursorIndexOfWasRemembered = CursorUtil.getColumnIndexOrThrow(_cursor, "wasRemembered");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final List<StudyHistory> _result = new ArrayList<StudyHistory>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final StudyHistory _item;
        final long _tmpSessionId;
        _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
        final long _tmpCollectionId;
        _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
        final long _tmpCardId;
        _tmpCardId = _cursor.getLong(_cursorIndexOfCardId);
        final boolean _tmpWasRemembered;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfWasRemembered);
        _tmpWasRemembered = _tmp != 0;
        _item = new StudyHistory(_tmpSessionId,_tmpCollectionId,_tmpCardId,_tmpWasRemembered);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        _item.setTimestamp(_tmpTimestamp);
        _result.add(_item);
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
