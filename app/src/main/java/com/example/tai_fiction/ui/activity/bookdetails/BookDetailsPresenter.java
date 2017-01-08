package com.example.tai_fiction.ui.activity.bookdetails;

import android.content.Context;

import com.example.tai_fiction.DB.BookDB;
import com.example.tai_fiction.api.ApiCallback;
import com.example.tai_fiction.base.BasePresenter;
import com.example.tai_fiction.entity.BookDetailsEntity;
import com.example.tai_fiction.entity.BookIntroEntity;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by JinBao on 2017/1/8.
 */

public class BookDetailsPresenter extends BasePresenter<BookDetailsView> {

    private BookDB bookDB;

    public BookDetailsPresenter(BookDetailsView mvpView, Context context) {
        super(mvpView);
        bookDB = BookDB.getInstance(context, "Book.db");

        ShareSDK.initSDK(context,"285ce8dba339");
        HashMap<String, Object> wechat = new HashMap<String, Object>();
        wechat.put("Id", "4");
        wechat.put("SortId", "4");
        wechat.put("AppId", "wxe14c1134c293ef0e");
        wechat.put("AppSecret", "208e6ccd183fcb4b3a12a6d27302370a");
        wechat.put("BypassApproval", "false");
        wechat.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(Wechat.NAME, wechat);
        // 代码配置第三方平台
        HashMap<String, Object> WechatMoment = new HashMap<String, Object>();
        WechatMoment.put("Id", "5");
        WechatMoment.put("SortId", "5");
        WechatMoment.put("AppId", "wxe14c1134c293ef0e");
        WechatMoment.put("AppSecret", "208e6ccd183fcb4b3a12a6d27302370a");
        WechatMoment.put("BypassApproval", "false");
        WechatMoment.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(WechatMoments.NAME, WechatMoment);
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
