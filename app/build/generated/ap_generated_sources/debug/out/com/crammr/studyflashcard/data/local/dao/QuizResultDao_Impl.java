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
import com.crammr.studyflashcard.data.local.entity.QuizResult;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
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
public final class QuizResultDao_Impl implements QuizResultDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuizResult> __insertionAdapterOfQuizResult;

  public QuizResultDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuizResult = new EntityInsertionAdapter<QuizResult>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quiz_results` (`id`,`collectionId`,`collectionName`,`totalQuestions`,`correctCount`,`incorrectCount`,`scorePercent`,`durationSeconds`,`completedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final QuizResult entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCollectionId());
        if (entity.getCollectionName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCollectionName());
        }
        statement.bindLong(4, entity.getTotalQuestions());
        statement.bindLong(5, entity.getCorrectCount());
        statement.bindLong(6, entity.getIncorrectCount());
        statement.bindLong(7, entity.getScorePercent());
        statement.bindLong(8, entity.getDurationSeconds());
        statement.bindLong(9, entity.getCompletedAt());
      }
    };
  }

  @Override
  public long insert(final QuizResult result) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfQuizResult.insertAndReturnId(result);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<QuizResult>> getAllResultsLive() {
    final String _sql = "SELECT * FROM quiz_results ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"quiz_results"}, false, new Callable<List<QuizResult>>() {
      @Override
      @Nullable
      public List<QuizResult> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfCollectionName = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionName");
          final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
          final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correctCount");
          final int _cursorIndexOfIncorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectCount");
          final int _cursorIndexOfScorePercent = CursorUtil.getColumnIndexOrThrow(_cursor, "scorePercent");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final List<QuizResult> _result = new ArrayList<QuizResult>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuizResult _item;
            final long _tmpCollectionId;
            _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            final String _tmpCollectionName;
            if (_cursor.isNull(_cursorIndexOfCollectionName)) {
              _tmpCollectionName = null;
            } else {
              _tmpCollectionName = _cursor.getString(_cursorIndexOfCollectionName);
            }
            final int _tmpTotalQuestions;
            _tmpTotalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
            final int _tmpCorrectCount;
            _tmpCorrectCount = _cursor.getInt(_cursorIndexOfCorrectCount);
            final int _tmpIncorrectCount;
            _tmpIncorrectCount = _cursor.getInt(_cursorIndexOfIncorrectCount);
            final int _tmpScorePercent;
            _tmpScorePercent = _cursor.getInt(_cursorIndexOfScorePercent);
            final int _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getInt(_cursorIndexOfDurationSeconds);
            _item = new QuizResult(_tmpCollectionId,_tmpCollectionName,_tmpTotalQuestions,_tmpCorrectCount,_tmpIncorrectCount,_tmpScorePercent,_tmpDurationSeconds);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            _item.setCompletedAt(_tmpCompletedAt);
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
  public List<QuizResult> getAllResults() {
    final String _sql = "SELECT * FROM quiz_results ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
      final int _cursorIndexOfCollectionName = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionName");
      final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
      final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correctCount");
      final int _cursorIndexOfIncorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectCount");
      final int _cursorIndexOfScorePercent = CursorUtil.getColumnIndexOrThrow(_cursor, "scorePercent");
      final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
      final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
      final List<QuizResult> _result = new ArrayList<QuizResult>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final QuizResult _item;
        final long _tmpCollectionId;
        _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
        final String _tmpCollectionName;
        if (_cursor.isNull(_cursorIndexOfCollectionName)) {
          _tmpCollectionName = null;
        } else {
          _tmpCollectionName = _cursor.getString(_cursorIndexOfCollectionName);
        }
        final int _tmpTotalQuestions;
        _tmpTotalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
        final int _tmpCorrectCount;
        _tmpCorrectCount = _cursor.getInt(_cursorIndexOfCorrectCount);
        final int _tmpIncorrectCount;
        _tmpIncorrectCount = _cursor.getInt(_cursorIndexOfIncorrectCount);
        final int _tmpScorePercent;
        _tmpScorePercent = _cursor.getInt(_cursorIndexOfScorePercent);
        final int _tmpDurationSeconds;
        _tmpDurationSeconds = _cursor.getInt(_cursorIndexOfDurationSeconds);
        _item = new QuizResult(_tmpCollectionId,_tmpCollectionName,_tmpTotalQuestions,_tmpCorrectCount,_tmpIncorrectCount,_tmpScorePercent,_tmpDurationSeconds);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpCompletedAt;
        _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
        _item.setCompletedAt(_tmpCompletedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<QuizResult>> getResultsByCollection(final long collectionId) {
    final String _sql = "SELECT * FROM quiz_results WHERE collectionId = ? ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, collectionId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"quiz_results"}, false, new Callable<List<QuizResult>>() {
      @Override
      @Nullable
      public List<QuizResult> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfCollectionName = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionName");
          final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
          final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correctCount");
          final int _cursorIndexOfIncorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectCount");
          final int _cursorIndexOfScorePercent = CursorUtil.getColumnIndexOrThrow(_cursor, "scorePercent");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final List<QuizResult> _result = new ArrayList<QuizResult>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuizResult _item;
            final long _tmpCollectionId;
            _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            final String _tmpCollectionName;
            if (_cursor.isNull(_cursorIndexOfCollectionName)) {
              _tmpCollectionName = null;
            } else {
              _tmpCollectionName = _cursor.getString(_cursorIndexOfCollectionName);
            }
            final int _tmpTotalQuestions;
            _tmpTotalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
            final int _tmpCorrectCount;
            _tmpCorrectCount = _cursor.getInt(_cursorIndexOfCorrectCount);
            final int _tmpIncorrectCount;
            _tmpIncorrectCount = _cursor.getInt(_cursorIndexOfIncorrectCount);
            final int _tmpScorePercent;
            _tmpScorePercent = _cursor.getInt(_cursorIndexOfScorePercent);
            final int _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getInt(_cursorIndexOfDurationSeconds);
            _item = new QuizResult(_tmpCollectionId,_tmpCollectionName,_tmpTotalQuestions,_tmpCorrectCount,_tmpIncorrectCount,_tmpScorePercent,_tmpDurationSeconds);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            _item.setCompletedAt(_tmpCompletedAt);
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
  public LiveData<Float> getAverageScoreLive() {
    final String _sql = "SELECT AVG(scorePercent) FROM quiz_results";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"quiz_results"}, false, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
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
  public LiveData<Integer> getTotalQuizzesLive() {
    final String _sql = "SELECT COUNT(*) FROM quiz_results";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"quiz_results"}, false, new Callable<Integer>() {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
