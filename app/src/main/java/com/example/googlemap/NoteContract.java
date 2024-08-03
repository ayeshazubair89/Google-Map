package com.example.googlemap;

import android.provider.BaseColumns;

public class NoteContract {

    private NoteContract() {}

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String COLUMN_NAME_NOTE_TEXT = "note_text";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NOTE_TEXT + " TEXT," +
                        COLUMN_NAME_LATITUDE + " REAL," +
                        COLUMN_NAME_LONGITUDE + " REAL)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}

