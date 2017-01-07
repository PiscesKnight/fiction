package com.example.tai_fiction.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Tai on 2016/4/13.
 */
public class BookDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table book ("
            + "book_id text primary key,"
            + "sid int,  "
            + "authors text, "
            + "cover text,"
            + "summary text,"
            + "title text,"
            + "iscollect text)";

    public static final String CREATE_COLLECT="create table collect(" +
            "book_id text primary key," +
            "title text," +
            "authors text," +
            "cover text," +
            "score_count intger)";

    public static final String CREATE_BOOKRACK="create table bookrack(" +
            "book_id text primary key," +
            "title text," +
            "cover text)";
    private Context context;


    public BookDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
       // db.execSQL(CREATE_LABEL);
        db.execSQL(CREATE_COLLECT);
        db.execSQL(CREATE_BOOKRACK);
        Log.d("TAG", "Create Sqlite succeeded");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Collect");
        db.execSQL("drop table if exists BookRack");
      //  db.execSQL("drop table if exists label");
        onCreate(db);
    }
}
