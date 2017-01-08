package com.example.tai_fiction.ui.activity.booksList;

import android.content.Context;

import com.example.tai_fiction.DB.BookDB;
import com.example.tai_fiction.api.ApiCallback;
import com.example.tai_fiction.api.ApiStores;
import com.example.tai_fiction.base.BasePresenter;
import com.example.tai_fiction.entity.BookIntroEntity;
import com.example.tai_fiction.entity.IndexLablesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JinBao on 2017/1/7.
 */

public class BooksListPresenter extends BasePresenter<BooksListView> {

    private BookDB bookDB;

    private List<BookIntroEntity.ItemsBean> booksData = null;//记录LablesEntity.BookBean.Items里是数据

    public BooksListPresenter(BooksListView mvpView, Context context) {
        super(mvpView);
        booksData = new ArrayList<>();
        bookDB = BookDB.getInstance(context, "Book.db");
    }

    //加载图书列表数据
    public void loadBooksListData(int sid){
        mvpView.showLoading();
        addSubscription(apiStores.loadBooksListData(sid),
                new ApiCallback<BookIntroEntity>() {
                    @Override
                    public void onSuccess(BookIntroEntity model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.onFailure(msg);
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }

    /**
     * 上拉加载更多，这里是十条
     */
    public void loadMoreBooksData(int sid,int start){
        addSubscription(apiStores.loadMoreData(sid, start),
                new ApiCallback<BookIntroEntity>() {
                    @Override
                    public void onSuccess(BookIntroEntity model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.onFailure(msg);
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }

    public void loadLableBooksData(String categoryid){
        addSubscription(apiStores.loadLableBooksData(categoryid),
                new ApiCallback<BookIntroEntity>() {
                    @Override
                    public void onSuccess(BookIntroEntity model) {
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

    boolean isSidTure(int sid){
       return bookDB.isTureBook(sid);
    }

    BookDB getBookDB(){
        return bookDB;
    }

    public void setBooksData(List<BookIntroEntity.ItemsBean> booksData) {
        this.booksData = booksData;
    }

    public List<BookIntroEntity.ItemsBean> getBooksData() {
        return booksData;
    }
}
