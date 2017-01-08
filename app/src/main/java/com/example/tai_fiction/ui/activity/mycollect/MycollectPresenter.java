package com.example.tai_fiction.ui.activity.mycollect;

import android.content.Context;

import com.example.tai_fiction.DB.BookDB;
import com.example.tai_fiction.base.BasePresenter;

/**
 * Created by JinBao on 2017/1/8.
 */

public class MycollectPresenter extends BasePresenter<MycollectView> {



    private BookDB bookDB;

    public MycollectPresenter(MycollectView mvpView, Context context) {
        super(mvpView);
        bookDB = BookDB.getInstance(context, "Book.db");
    }

    public BookDB getBookDB() {
        return bookDB;
    }

    public void setBookDB(BookDB bookDB) {
        this.bookDB = bookDB;
    }
}
