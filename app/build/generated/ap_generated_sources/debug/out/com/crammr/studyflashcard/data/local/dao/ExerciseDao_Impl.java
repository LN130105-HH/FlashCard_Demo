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
import com.crammr.studyflashcard.data.local.entity.AnswerOption;
import com.crammr.studyflashcard.data.local.entity.Exercise;
import com.crammr.studyflashcard.data.local.entity.Question;
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
public final class ExerciseDao_Impl implements ExerciseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Exercise> __insertionAdapterOfExercise;

  private final EntityInsertionAdapter<Question> __insertionAdapterOfQuestion;

  private final EntityInsertionAdapter<AnswerOption> __insertionAdapterOfAnswerOption;

  public ExerciseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExercise = new EntityInsertionAdapter<Exercise>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `exercises` (`id`,`collectionId`,`title`,`description`,`timeLimitMinutes`,`questionCount`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Exercise entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCollectionId());
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        statement.bindLong(5, entity.getTimeLimitMinutes());
        statement.bindLong(6, entity.getQuestionCount());
        statement.bindLong(7, entity.getCreatedAt());
      }
    };
    this.__insertionAdapterOfQuestion = new EntityInsertionAdapter<Question>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `questions` (`id`,`exerciseId`,`collectionId`,`questionText`,`explanation`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Question entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getExerciseId());
        statement.bindLong(3, entity.getCollectionId());
        if (entity.getQuestionText() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getQuestionText());
        }
        if (entity.getExplanation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getExplanation());
        }
      }
    };
    this.__insertionAdapterOfAnswerOption = new EntityInsertionAdapter<AnswerOption>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `answer_options` (`id`,`questionId`,`optionText`,`isCorrect`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final AnswerOption entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getQuestionId());
        if (entity.getOptionText() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getOptionText());
        }
        final int _tmp = entity.isCorrect() ? 1 : 0;
        statement.bindLong(4, _tmp);
      }
    };
  }

  @Override
  public long insertExercise(final Exercise exercise) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfExercise.insertAndReturnId(exercise);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertQuestion(final Question question) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfQuestion.insertAndReturnId(question);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertOptions(final List<AnswerOption> options) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAnswerOption.insert(options);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Exercise>> getExercisesByCollectionLive(final long collectionId) {
    final String _sql = "SELECT * FROM exercises WHERE collectionId = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, collectionId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, new Callable<List<Exercise>>() {
      @Override
      @Nullable
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTimeLimitMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLimitMinutes");
          final int _cursorIndexOfQuestionCount = CursorUtil.getColumnIndexOrThrow(_cursor, "questionCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exercise _item;
            final long _tmpCollectionId;
            _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final int _tmpTimeLimitMinutes;
            _tmpTimeLimitMinutes = _cursor.getInt(_cursorIndexOfTimeLimitMinutes);
            final int _tmpQuestionCount;
            _tmpQuestionCount = _cursor.getInt(_cursorIndexOfQuestionCount);
            _item = new Exercise(_tmpCollectionId,_tmpTitle,_tmpDescription,_tmpTimeLimitMinutes,_tmpQuestionCount);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
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
  public LiveData<List<Exercise>> getAllExercisesLive() {
    final String _sql = "SELECT * FROM exercises ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, new Callable<List<Exercise>>() {
      @Override
      @Nullable
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTimeLimitMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLimitMinutes");
          final int _cursorIndexOfQuestionCount = CursorUtil.getColumnIndexOrThrow(_cursor, "questionCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exercise _item;
            final long _tmpCollectionId;
            _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final int _tmpTimeLimitMinutes;
            _tmpTimeLimitMinutes = _cursor.getInt(_cursorIndexOfTimeLimitMinutes);
            final int _tmpQuestionCount;
            _tmpQuestionCount = _cursor.getInt(_cursorIndexOfQuestionCount);
            _item = new Exercise(_tmpCollectionId,_tmpTitle,_tmpDescription,_tmpTimeLimitMinutes,_tmpQuestionCount);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
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
  public List<Question> getQuestionsForExercise(final long exerciseId) {
    final String _sql = "SELECT * FROM questions WHERE exerciseId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, exerciseId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseId");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
      final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Question _item;
        final long _tmpExerciseId;
        _tmpExerciseId = _cursor.getLong(_cursorIndexOfExerciseId);
        final long _tmpCollectionId;
        _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
        final String _tmpQuestionText;
        if (_cursor.isNull(_cursorIndexOfQuestionText)) {
          _tmpQuestionText = null;
        } else {
          _tmpQuestionText = _cursor.getString(_cursorIndexOfQuestionText);
        }
        final String _tmpExplanation;
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _tmpExplanation = null;
        } else {
          _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        _item = new Question(_tmpExerciseId,_tmpCollectionId,_tmpQuestionText,_tmpExplanation);
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
  public List<Question> getQuestionsForCollection(final long collectionId) {
    final String _sql = "SELECT * FROM questions WHERE collectionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, collectionId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseId");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
      final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Question _item;
        final long _tmpExerciseId;
        _tmpExerciseId = _cursor.getLong(_cursorIndexOfExerciseId);
        final long _tmpCollectionId;
        _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
        final String _tmpQuestionText;
        if (_cursor.isNull(_cursorIndexOfQuestionText)) {
          _tmpQuestionText = null;
        } else {
          _tmpQuestionText = _cursor.getString(_cursorIndexOfQuestionText);
        }
        final String _tmpExplanation;
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _tmpExplanation = null;
        } else {
          _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        _item = new Question(_tmpExerciseId,_tmpCollectionId,_tmpQuestionText,_tmpExplanation);
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
  public List<AnswerOption> getOptionsForQuestion(final long questionId) {
    final String _sql = "SELECT * FROM answer_options WHERE questionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, questionId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfQuestionId = CursorUtil.getColumnIndexOrThrow(_cursor, "questionId");
      final int _cursorIndexOfOptionText = CursorUtil.getColumnIndexOrThrow(_cursor, "optionText");
      final int _cursorIndexOfIsCorrect = CursorUtil.getColumnIndexOrThrow(_cursor, "isCorrect");
      final List<AnswerOption> _result = new ArrayList<AnswerOption>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final AnswerOption _item;
        final long _tmpQuestionId;
        _tmpQuestionId = _cursor.getLong(_cursorIndexOfQuestionId);
        final String _tmpOptionText;
        if (_cursor.isNull(_cursorIndexOfOptionText)) {
          _tmpOptionText = null;
        } else {
          _tmpOptionText = _cursor.getString(_cursorIndexOfOptionText);
        }
        final boolean _tmpIsCorrect;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCorrect);
        _tmpIsCorrect = _tmp != 0;
        _item = new AnswerOption(_tmpQuestionId,_tmpOptionText,_tmpIsCorrect);
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
  public LiveData<Integer> getTotalExercisesLive() {
    final String _sql = "SELECT COUNT(*) FROM exercises";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, new Callable<Integer>() {
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
