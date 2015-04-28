package com.codequest.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Falcon on 4/25/2015.
 * Derived from http://stackoverflow.com/questions/22209046/fix-android-studio-login-activity-template-generated-activity
 */
public class DBHandler extends SQLiteOpenHelper{

    private final static int DB_VERSION = 13;
    private static DBHandler handler;

    private DBHandler(Context context) {
        super(context, "CodeQuest.db", null, DB_VERSION);
    }

    public static DBHandler getDBHandler(Context context) {
        if (handler == null)
            handler = new DBHandler(context);
        return handler;
    }

    @Override
    // Create userdata table
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE userdata (userId Integer primary key autoincrement, "+
                " login text, password text)";
        db.execSQL(query);
        query = "CREATE TABLE highscores (userId Integer, score Integer)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS userdata";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS highscores";
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

    public User getUser (String login) {
        String query = "SELECT userId, password from userdata WHERE " +
            "login = '"+login+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        long userId = -1;
        String password = "";
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getLong(0);
                password = cursor.getString(1);
            } while (cursor.moveToNext());
        }
        return new User(userId, login, password);
    }

    // Insert or replace highscore
    public long insertHighscore(User user, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", user.userId);
        values.put("score", score);
        return db.insert("highscores", null, values);
    }

    // Retrieve highscore information
    public Highscore getHighscore(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT score FROM highscores WHERE " +
                "userId = '"+user.userId+"'";
        Cursor cursor = db.rawQuery(query, null);
        int score = 0;
        if (cursor.moveToFirst()) {
            do {
                score = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return new Highscore(user.login, score);
    }

    // Fetch all highscores in the table
    public Highscore[] getAllHighscores() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Highscore> list = new ArrayList<>();
        String query = "SELECT login, score FROM highscores INNER JOIN userdata ON " +
                "userdata.userId=highscores.userId ORDER BY score DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Highscore(cursor.getString(0), cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        return list.toArray(new Highscore[list.size()]);
    }

}
