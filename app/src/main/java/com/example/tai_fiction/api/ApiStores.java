package com.example.tai_fiction.api;

import com.example.tai_fiction.entity.BookDetailsEntity;
import com.example.tai_fiction.entity.IndexLablesEntity;
import com.squareup.okhttp.Call;


import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by JinBao on 2016/11/17.
 */

public interface ApiStores {
     String URL = "http://www.duokan.com/";

     //加载书城
    @GET("hs/v0/android/store/category")
    Observable<IndexLablesEntity> loadBookCityData();
}
