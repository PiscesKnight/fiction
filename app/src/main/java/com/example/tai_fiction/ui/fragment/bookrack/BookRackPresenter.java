package com.example.tai_fiction.ui.fragment.bookrack;

import android.content.Context;

import com.example.tai_fiction.DB.BookDB;
import com.example.tai_fiction.base.BasePresenter;
import com.example.tai_fiction.entity.MyCollectEntity;

import java.util.List;

/**
 * Created by JinBao on 2017/1/7.
 */

public class BookRackPresenter extends BasePresenter<BookRackView>
{
    private BookDB bookDB;

    public BookRackPresenter(BookRackView mvpView,Context context) {
        super(mvpView);
        bookDB = BookDB.getInstance(context, "Book.db");
    }

    public List<MyCollectEntity> getBookRackData(){
       return bookDB.getBookRackData();
    }



}
