package com.example.tai_fiction.ui.fragment.bookcity;

import com.example.tai_fiction.base.BaseModel;
import com.example.tai_fiction.entity.IndexLablesEntity;

import java.util.List;

/**
 * Created by JinBao on 2017/1/7.
 */

public class BookCictyModel implements BaseModel {
    private IndexLablesEntity booksData;
    private List<IndexLablesEntity.BookBean.ItemsBean> itemsBeans;


    public  IndexLablesEntity getBookCityData() {
        return booksData;
    }


    public void setBooksData(IndexLablesEntity booksData) {
        this.booksData = booksData;
    }
}
