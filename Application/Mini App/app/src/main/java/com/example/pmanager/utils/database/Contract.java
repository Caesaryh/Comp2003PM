package com.example.pmanager.utils.database;

import android.provider.BaseColumns;

public final class Contract {
    private Contract(){}


    public static class PasswordsEntry implements BaseColumns {
        public static final String TABLE_NAME = "passwords";
        public static final String COLUMN_ID = BaseColumns._ID; // 使用 BaseColumns._ID 作为主键
        public static final String COLUMN_WEBSITE = "website";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";

        public static final String COLUMN_COMMENT = "comment";

        // Types
        public static final String COLUMN_TYPE_ID = "INTEGER PRIMARY KEY AUTOINCREMENT";
        public static final String COLUMN_TYPE_TEXT = "TEXT NOT NULL";
    }
}
