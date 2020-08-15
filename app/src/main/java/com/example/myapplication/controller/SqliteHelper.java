package com.example.myapplication.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {

    public SqliteHelper (@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, firstname TEXT NOT NULL, lastname TEXT NOT NULL, birth TEXT NOT NULL, height REAL NOT NULL, password TEXT NOT NULL)");
        db.execSQL("CREATE TABLE evaluations (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, date DATE NOT NULL, weight REAL NOT NULL, height REAL NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
