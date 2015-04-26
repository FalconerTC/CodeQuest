package com.codequest.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Falcon on 4/25/2015.
 * Derived from http://stackoverflow.com/questions/22209046/fix-android-studio-login-activity-template-generated-activity
 */
public class DBHandler extends SQLiteOpenHelper{

    private final static int DB_VERSION = 10;

    public DBHandler(Context context) {
        super(context, "CodeQuest.db", null, DB_VERSION);
    }

    @Override
    // Create userdata table
    public void onCreate(SQLiteDatabase db) {
        String query = "create table userdata (userId Integer primary key autoincrement, "+
                " login text, password text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists userdata";
        db.execSQL(query);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public User insertUser(User queryValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("login", queryValues.login);
        values.put("password", queryValues.password);
        queryValues.userId = db.insert("userdata", null, values);
        db.close();
        return queryValues;
    }

    public int updateUserPassword(User queryValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("login", queryValues.login);
        values.put("password", queryValues.password);
        queryValues.userId = db.insert("userdata", null, values);
        db.close();
        return db.update("userdata", values, "userId = ?", new String[] {String.valueOf(queryValues.userId)});
    }

    public User getUser (String login) {
        String query = "select userId, password from userdata where " +
            "login = '"+login+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        long userId = 0;
        String password = "";
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getLong(0);
                password = cursor.getString(1);
            } while (cursor.moveToNext());
        }
        return new User(userId, login, password);
    }
}
