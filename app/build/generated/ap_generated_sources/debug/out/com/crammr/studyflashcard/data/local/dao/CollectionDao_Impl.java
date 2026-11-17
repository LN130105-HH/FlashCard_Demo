package com.crammr.studyflashcard.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
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
public final class CollectionDao_Impl implements CollectionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FlashcardCollection> __insertionAdapterOfFlashcardCollection;

  private final EntityDeletionOrUpdateAdapter<FlashcardCollection> __deletionAdapterOfFlashcardCollection;

  private final EntityDeletionOrUpdateAdapter<FlashcardCollection> __updateAdapterOfFlashcardCollection;

  public CollectionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFlashcardCollection = new EntityInsertionAdapter<FlashcardCollection>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `collections` (`id`,`subjectId`,`topicId`,`name`,`description`,`cardCount`,`progress`,`createdAt`,`lastStudiedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final FlashcardCollection entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSubjectId());
        statement.bindLong(3, entity.getTopicId());
        if (entity.getName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDescription());
        }
        statement.bindLong(6, entity.getCardCount());
        statement.bindLong(7, entity.getProgress());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getLastStudiedAt());
      }
    };
    this.__deletionAdapterOfFlashcardCollection = new EntityDeletionOrUpdateAdapter<FlashcardCollection>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `collections` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final FlashcardCollection entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfFlashcardCollection = new EntityDeletionOrUpdateAdapter<FlashcardCollection>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `collections` SET `id` = ?,`subjectId` = ?,`topicId` = ?,`name` = ?,`description` = ?,`cardCount` = ?,`progress` = ?,`createdAt` = ?,`lastStudiedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final FlashcardCollection entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSubjectId());
        statement.bindLong(3, entity.getTopicId());
        if (entity.getName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDescription());
        }
        statement.bindLong(6, entity.getCardCount());
        statement.bindLong(7, entity.getProgress());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getLastStudiedAt());
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public long insert(final FlashcardCollection collection) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfFlashcardCollection.insertAndReturnId(collection);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final FlashcardCollection collection) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfFlashcardCollection.handle(collection);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final FlashcardCollection collection) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfFlashcardCollection.handle(collection);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<FlashcardCollection>> getAllCollectionsLive() {
    final String _sql = "SELECT * FROM collections ORDER BY lastStudiedAt DESC, id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"collections"}, false, new Callable<List<FlashcardCollection>>() {
      @Override
      @Nullable
      public List<FlashcardCollection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCardCount = CursorUtil.getColumnIndexOrThrow(_cursor, "cardCount");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
          final List<FlashcardCollection> _result = new ArrayList<FlashcardCollection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlashcardCollection _item;
            final long _tmpSubjectId;
            _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            final long _tmpTopicId;
            _tmpTopicId = _cursor.getLong(_cursorIndexOfTopicId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item = new FlashcardCollection(_tmpSubjectId,_tmpTopicId,_tmpName,_tmpDescription);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpCardCount;
            _tmpCardCount = _cursor.getInt(_cursorIndexOfCardCount);
            _item.setCardCount(_tmpCardCount);
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            _item.setProgress(_tmpProgress);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpLastStudiedAt;
            _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
            _item.setLastStudiedAt(_tmpLastStudiedAt);
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
  public List<FlashcardCollection> getAllCollections() {
    final String _sql = "SELECT * FROM collections ORDER BY lastStudiedAt DESC, id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
      final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfCardCount = CursorUtil.getColumnIndexOrThrow(_cursor, "cardCount");
      final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
      final List<FlashcardCollection> _result = new ArrayList<FlashcardCollection>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final FlashcardCollection _item;
        final long _tmpSubjectId;
        _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
        final long _tmpTopicId;
        _tmpTopicId = _cursor.getLong(_cursorIndexOfTopicId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _item = new FlashcardCollection(_tmpSubjectId,_tmpTopicId,_tmpName,_tmpDescription);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpCardCount;
        _tmpCardCount = _cursor.getInt(_cursorIndexOfCardCount);
        _item.setCardCount(_tmpCardCount);
        final int _tmpProgress;
        _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
        _item.setProgress(_tmpProgress);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.setCreatedAt(_tmpCreatedAt);
        final long _tmpLastStudiedAt;
        _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
        _item.setLastStudiedAt(_tmpLastStudiedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<FlashcardCollection>> getRecentCollectionsLive(final int limit) {
    final String _sql = "SELECT * FROM collections ORDER BY lastStudiedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return __db.getInvalidationTracker().createLiveData(new String[] {"collections"}, false, new Callable<List<FlashcardCollection>>() {
      @Override
      @Nullable
      public List<FlashcardCollection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCardCount = CursorUtil.getColumnIndexOrThrow(_cursor, "cardCount");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
          final List<FlashcardCollection> _result = new ArrayList<FlashcardCollection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlashcardCollection _item;
            final long _tmpSubjectId;
            _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            final long _tmpTopicId;
            _tmpTopicId = _cursor.getLong(_cursorIndexOfTopicId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item = new FlashcardCollection(_tmpSubjectId,_tmpTopicId,_tmpName,_tmpDescription);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpCardCount;
            _tmpCardCount = _cursor.getInt(_cursorIndexOfCardCount);
            _item.setCardCount(_tmpCardCount);
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            _item.setProgress(_tmpProgress);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpLastStudiedAt;
            _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
            _item.setLastStudiedAt(_tmpLastStudiedAt);
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
  public FlashcardCollection getCollectionById(final long id) {
    final String _sql = "SELECT * FROM collections WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
      final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfCardCount = CursorUtil.getColumnIndexOrThrow(_cursor, "cardCount");
      final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
      final FlashcardCollection _result;
      if (_cursor.moveToFirst()) {
        final long _tmpSubjectId;
        _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
        final long _tmpTopicId;
        _tmpTopicId = _cursor.getLong(_cursorIndexOfTopicId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _result = new FlashcardCollection(_tmpSubjectId,_tmpTopicId,_tmpName,_tmpDescription);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpCardCount;
        _tmpCardCount = _cursor.getInt(_cursorIndexOfCardCount);
        _result.setCardCount(_tmpCardCount);
        final int _tmpProgress;
        _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
        _result.setProgress(_tmpProgress);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.setCreatedAt(_tmpCreatedAt);
        final long _tmpLastStudiedAt;
        _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
        _result.setLastStudiedAt(_tmpLastStudiedAt);
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
  public LiveData<FlashcardCollection> getCollectionByIdLive(final long id) {
    final String _sql = "SELECT * FROM collections WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"collections"}, false, new Callable<FlashcardCollection>() {
      @Override
      @Nullable
      public FlashcardCollection call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCardCount = CursorUtil.getColumnIndexOrThrow(_cursor, "cardCount");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
          final FlashcardCollection _result;
          if (_cursor.moveToFirst()) {
            final long _tmpSubjectId;
            _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            final long _tmpTopicId;
            _tmpTopicId = _cursor.getLong(_cursorIndexOfTopicId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _result = new FlashcardCollection(_tmpSubjectId,_tmpTopicId,_tmpName,_tmpDescription);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final int _tmpCardCount;
            _tmpCardCount = _cursor.getInt(_cursorIndexOfCardCount);
            _result.setCardCount(_tmpCardCount);
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            _result.setProgress(_tmpProgress);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result.setCreatedAt(_tmpCreatedAt);
            final long _tmpLastStudiedAt;
            _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
            _result.setLastStudiedAt(_tmpLastStudiedAt);
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
  public LiveData<List<FlashcardCollection>> getCollectionsBySubject(final long subjectId) {
    final String _sql = "SELECT * FROM collections WHERE subjectId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"collections"}, false, new Callable<List<FlashcardCollection>>() {
      @Override
      @Nullable
      public List<FlashcardCollection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCardCount = CursorUtil.getColumnIndexOrThrow(_cursor, "cardCount");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
          final List<FlashcardCollection> _result = new ArrayList<FlashcardCollection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlashcardCollection _item;
            final long _tmpSubjectId;
            _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            final long _tmpTopicId;
            _tmpTopicId = _cursor.getLong(_cursorIndexOfTopicId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item = new FlashcardCollection(_tmpSubjectId,_tmpTopicId,_tmpName,_tmpDescription);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpCardCount;
            _tmpCardCount = _cursor.getInt(_cursorIndexOfCardCount);
            _item.setCardCount(_tmpCardCount);
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            _item.setProgress(_tmpProgress);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpLastStudiedAt;
            _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
            _item.setLastStudiedAt(_tmpLastStudiedAt);
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
  public LiveData<List<FlashcardCollection>> searchCollections(final String query) {
    final String _sql = "SELECT * FROM collections WHERE name LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"collections"}, false, new Callable<List<FlashcardCollection>>() {
      @Override
      @Nullable
      public List<FlashcardCollection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCardCount = CursorUtil.getColumnIndexOrThrow(_cursor, "cardCount");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
          final List<FlashcardCollection> _result = new ArrayList<FlashcardCollection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlashcardCollection _item;
            final long _tmpSubjectId;
            _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            final long _tmpTopicId;
            _tmpTopicId = _cursor.getLong(_cursorIndexOfTopicId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item = new FlashcardCollection(_tmpSubjectId,_tmpTopicId,_tmpName,_tmpDescription);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpCardCount;
            _tmpCardCount = _cursor.getInt(_cursorIndexOfCardCount);
            _item.setCardCount(_tmpCardCount);
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            _item.setProgress(_tmpProgress);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpLastStudiedAt;
            _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
            _item.setLastStudiedAt(_tmpLastStudiedAt);
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
  public LiveData<Integer> getCollectionCountLive() {
    final String _sql = "SELECT COUNT(*) FROM collections";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"collections"}, false, new Callable<Integer>() {
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
  public int getCollectionCount() {
    final String _sql = "SELECT COUNT(*) FROM collections";
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
