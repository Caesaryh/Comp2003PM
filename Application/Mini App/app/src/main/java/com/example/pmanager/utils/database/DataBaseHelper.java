package com.example.pmanager.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.pmanager.utils.database.Contract.PasswordsEntry;
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PasswordManager.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_PASSWORDS =
            "CREATE TABLE " + PasswordsEntry.TABLE_NAME + " ("
                    + PasswordsEntry.COLUMN_ID + " " + PasswordsEntry.COLUMN_TYPE_ID + ","
                    + PasswordsEntry.COLUMN_WEBSITE + " " + PasswordsEntry.COLUMN_TYPE_TEXT + ","
                    + PasswordsEntry.COLUMN_USERNAME + " " + PasswordsEntry.COLUMN_TYPE_TEXT + ","
                    + PasswordsEntry.COLUMN_PASSWORD + " " + PasswordsEntry.COLUMN_TYPE_TEXT + ","
                    + PasswordsEntry.COLUMN_COMMENT + " " + PasswordsEntry.COLUMN_TYPE_TEXT
                    + ")";
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_PASSWORDS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PasswordsEntry.TABLE_NAME);
        onCreate(db);
    }

    public long insertPassword(String website, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PasswordsEntry.COLUMN_WEBSITE, website);
        values.put(PasswordsEntry.COLUMN_USERNAME, username);
        values.put(PasswordsEntry.COLUMN_PASSWORD, password);
        return db.insert(PasswordsEntry.TABLE_NAME, null, values);
    }

    public Cursor getAllPasswords() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(PasswordsEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getPasswordById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(PasswordsEntry.TABLE_NAME, null, PasswordsEntry.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public int updatePassword(int id, String website, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PasswordsEntry.COLUMN_WEBSITE, website);
        values.put(PasswordsEntry.COLUMN_USERNAME, username);
        values.put(PasswordsEntry.COLUMN_PASSWORD, password);
        return db.update(PasswordsEntry.TABLE_NAME, values, PasswordsEntry.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deletePassword(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PasswordsEntry.TABLE_NAME, PasswordsEntry.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}
