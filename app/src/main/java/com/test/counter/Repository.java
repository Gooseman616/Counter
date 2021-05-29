package com.test.counter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Repository extends SQLiteOpenHelper {
    //DB initialisation
    public static final String DB_NAME = "Counter_db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "Counters";
    public static final String ID = "id";
    public static final String VAL = "val";
    public static final String NAME = "name";
    public static final String CREATE_TABLE_COUNTERS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY, " +
                    VAL + " INTEGER NOT NULL, " +
                    NAME + " TEXT NOT NULL" +
                    ");";
    private static Repository sInstance;
    //DB initialisation end
    private final Set<RepoListener> mListeners = new HashSet<>();

    private Repository(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    public static Repository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Repository(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COUNTERS);
//        generateCounterList(10, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addCounter(String name) {
        ContentValues cv = new ContentValues();
        cv.put(VAL, 0);
        cv.put(NAME, name);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, cv);
        notifyChanged();
    }

    public void removeCounter(long counterId) {
        getWritableDatabase().delete(TABLE_NAME, ID + " = " + counterId, null);
        notifyChanged();
    }


    //    private void generateCounterList(int amount, SQLiteDatabase db) {
//        for (int i = 0; i < amount; i++) {
//            ContentValues cv = new ContentValues();
//            cv.put(VAL, 0);
//            cv.put(NAME, "Counter " + (i + 1));
//            db.insert(TABLE_NAME, null, cv);
//        }
//    }
    @Nullable
    public Counter getCounter(long id) {
        String[] cols = {ID, VAL, NAME};
        try (Cursor cursor = getReadableDatabase().query(TABLE_NAME,
                cols,
                ID + " = " + id,
                null,
                null,
                null,
                null)) {
            if (cursor.moveToFirst()) {
                return new Counter(cursor.getLong(0), cursor.getString(2), cursor.getInt(1));
            }
        }
        return null;
    }

    public List<Counter> getCounters() {
        String[] cols = {ID, VAL, NAME};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, cols,
                null,
                null,
                null,
                null,
                ID + " DESC");
        List<Counter> list = new ArrayList<>(cursor.getColumnCount());
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            int value = cursor.getInt(1);
            String name = cursor.getString(2);
            list.add(new Counter(id, name, value));
        }
        cursor.close();
        return list;
    }

    public void setValue(Counter counter, int value) {
        ContentValues cv = new ContentValues();
        cv.put(VAL, value);
        getWritableDatabase().update(TABLE_NAME, cv, ID + " = " + counter.id, null);
        notifyChanged();
    }

    public void setName(long counterId, String name) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        getWritableDatabase().update(TABLE_NAME, cv, ID + " = " + counterId, null);
        notifyChanged();
    }

//            throw new SQLiteException("Counter with id: " + ID + " does not exist.");

    private void notifyChanged() {
        for (RepoListener listener : mListeners) {
            listener.onDataChanged();
        }
    }

    public void addListener(RepoListener listener) {
        mListeners.add(listener);
    }

    public void removeListener(RepoListener listener) {
        mListeners.remove(listener);
    }

    public interface RepoListener {
        void onDataChanged();
    }
}
