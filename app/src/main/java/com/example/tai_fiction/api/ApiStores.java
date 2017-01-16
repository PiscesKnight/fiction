package com.example.tai_fiction.api;

import com.example.tai_fiction.entity.BookDetailsEntity;
import com.example.tai_fiction.entity.BookIntroEntity;
import com.example.tai_fiction.entity.IndexLablesEntity;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by JinBao on 2016/11/17.
 */

public interface ApiStores {
     String URL = "http://www.duokan.com/";

     //加载书城
    @GET("hs/v0/android/store/category")
    Observable<IndexLablesEntity> loadBookCityData();

    //书籍列表
    @GET("store/v0/android/category/{sid}?start=0&count=10")
    Observable<BookIntroEntity> loadBooksListData(@Path("sid") int sid);

    //上拉加载
    @GET("store/v0/android/category/{sid}")
    Observable<BookIntroEntity> loadMoreData(@Path("sid") int sid,
                                             @Query("start") int start);

    //加载标签的图书列表
    @GET("store/v0/android/category/{categoryid}?start=0&count=10")
    Observable<BookIntroEntity> loadLableBooksData(@Path("categoryid") String categoryid);

    //获取数据内容
    @GET("http://www.duokan.com/hs/v0/android/store/book/{bookId}")
    Observable<BookDetailsEntity> getBookdetailsData(@Path("bookId") String bookId);


}
