package com.example.tai_fiction.ui.activity.bookdetails;

import android.content.Context;

import com.example.tai_fiction.DB.BookDB;
import com.example.tai_fiction.api.ApiCallback;
import com.example.tai_fiction.base.BasePresenter;
import com.example.tai_fiction.entity.BookDetailsEntity;
import com.example.tai_fiction.entity.BookIntroEntity;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by JinBao on 2017/1/8.
 */

public class BookDetailsPresenter extends BasePresenter<BookDetailsView> {

    private BookDB bookDB;

    public BookDetailsPresenter(BookDetailsView mvpView, Context context) {
        super(mvpView);
        bookDB = BookDB.getInstance(context, "Book.db");
    }

    /**
     * 获取书籍内容
     *
     */
    public void getBookDetalisData(String bookId){
        addSubscription(apiStores.getBookdetailsData(bookId),
                new ApiCallback<BookDetailsEntity>() {
                    @Override
                    public void onSuccess(BookDetailsEntity model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.onFailure(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public BookDB getBookDB() {
        return bookDB;
    }

    public void setBookDB(BookDB bookDB) {
        this.bookDB = bookDB;
    }
}
