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
import com.crammr.studyflashcard.data.local.entity.Reminder;
import java.lang.Class;
import java.lang.Exception;
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
public final class ReminderDao_Impl implements ReminderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Reminder> __insertionAdapterOfReminder;

  private final EntityDeletionOrUpdateAdapter<Reminder> __deletionAdapterOfReminder;

  private final EntityDeletionOrUpdateAdapter<Reminder> __updateAdapterOfReminder;

  public ReminderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReminder = new EntityInsertionAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reminders` (`id`,`collectionId`,`collectionName`,`title`,`scheduledTimestamp`,`repeatType`,`isEnabled`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Reminder entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCollectionId());
        if (entity.getCollectionName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCollectionName());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        statement.bindLong(5, entity.getScheduledTimestamp());
        if (entity.getRepeatType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRepeatType());
        }
        final int _tmp = entity.isEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__deletionAdapterOfReminder = new EntityDeletionOrUpdateAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reminders` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Reminder entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfReminder = new EntityDeletionOrUpdateAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reminders` SET `id` = ?,`collectionId` = ?,`collectionName` = ?,`title` = ?,`scheduledTimestamp` = ?,`repeatType` = ?,`isEnabled` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Reminder entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCollectionId());
        if (entity.getCollectionName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCollectionName());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        statement.bindLong(5, entity.getScheduledTimestamp());
        if (entity.getRepeatType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRepeatType());
        }
        final int _tmp = entity.isEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public long insert(final Reminder reminder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfReminder.insertAndReturnId(reminder);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Reminder reminder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfReminder.handle(reminder);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Reminder reminder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfReminder.handle(reminder);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Reminder>> getAllRemindersLive() {
    final String _sql = "SELECT * FROM reminders ORDER BY scheduledTimestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"reminders"}, false, new Callable<List<Reminder>>() {
      @Override
      @Nullable
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfCollectionName = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfScheduledTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTimestamp");
          final int _cursorIndexOfRepeatType = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatType");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final long _tmpCollectionId;
            _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            final String _tmpCollectionName;
            if (_cursor.isNull(_cursorIndexOfCollectionName)) {
              _tmpCollectionName = null;
            } else {
              _tmpCollectionName = _cursor.getString(_cursorIndexOfCollectionName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final long _tmpScheduledTimestamp;
            _tmpScheduledTimestamp = _cursor.getLong(_cursorIndexOfScheduledTimestamp);
            final String _tmpRepeatType;
            if (_cursor.isNull(_cursorIndexOfRepeatType)) {
              _tmpRepeatType = null;
            } else {
              _tmpRepeatType = _cursor.getString(_cursorIndexOfRepeatType);
            }
            _item = new Reminder(_tmpCollectionId,_tmpCollectionName,_tmpTitle,_tmpScheduledTimestamp,_tmpRepeatType);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final boolean _tmpIsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp != 0;
            _item.setEnabled(_tmpIsEnabled);
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
  public List<Reminder> getActiveReminders() {
    final String _sql = "SELECT * FROM reminders WHERE isEnabled = 1 ORDER BY scheduledTimestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
      final int _cursorIndexOfCollectionName = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionName");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfScheduledTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTimestamp");
      final int _cursorIndexOfRepeatType = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatType");
      final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
      final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Reminder _item;
        final long _tmpCollectionId;
        _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
        final String _tmpCollectionName;
        if (_cursor.isNull(_cursorIndexOfCollectionName)) {
          _tmpCollectionName = null;
        } else {
          _tmpCollectionName = _cursor.getString(_cursorIndexOfCollectionName);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final long _tmpScheduledTimestamp;
        _tmpScheduledTimestamp = _cursor.getLong(_cursorIndexOfScheduledTimestamp);
        final String _tmpRepeatType;
        if (_cursor.isNull(_cursorIndexOfRepeatType)) {
          _tmpRepeatType = null;
        } else {
          _tmpRepeatType = _cursor.getString(_cursorIndexOfRepeatType);
        }
        _item = new Reminder(_tmpCollectionId,_tmpCollectionName,_tmpTitle,_tmpScheduledTimestamp,_tmpRepeatType);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final boolean _tmpIsEnabled;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsEnabled);
        _tmpIsEnabled = _tmp != 0;
        _item.setEnabled(_tmpIsEnabled);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Reminder getReminderById(final long id) {
    final String _sql = "SELECT * FROM reminders WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
      final int _cursorIndexOfCollectionName = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionName");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfScheduledTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTimestamp");
      final int _cursorIndexOfRepeatType = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatType");
      final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
      final Reminder _result;
      if (_cursor.moveToFirst()) {
        final long _tmpCollectionId;
        _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
        final String _tmpCollectionName;
        if (_cursor.isNull(_cursorIndexOfCollectionName)) {
          _tmpCollectionName = null;
        } else {
          _tmpCollectionName = _cursor.getString(_cursorIndexOfCollectionName);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final long _tmpScheduledTimestamp;
        _tmpScheduledTimestamp = _cursor.getLong(_cursorIndexOfScheduledTimestamp);
        final String _tmpRepeatType;
        if (_cursor.isNull(_cursorIndexOfRepeatType)) {
          _tmpRepeatType = null;
        } else {
          _tmpRepeatType = _cursor.getString(_cursorIndexOfRepeatType);
        }
        _result = new Reminder(_tmpCollectionId,_tmpCollectionName,_tmpTitle,_tmpScheduledTimestamp,_tmpRepeatType);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final boolean _tmpIsEnabled;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsEnabled);
        _tmpIsEnabled = _tmp != 0;
        _result.setEnabled(_tmpIsEnabled);
      } else {
        _result = null;
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
