package com.example.tai_fiction.ui.fragment.bookcity;

import com.example.tai_fiction.api.ApiCallback;
import com.example.tai_fiction.base.BasePresenter;
import com.example.tai_fiction.entity.IndexLablesEntity;

/**
 * Created by JinBao on 2017/1/7.
 */

public class BookCityPresenter extends BasePresenter<BookCityView> {

     BookCityPresenter(BookCityView mvpView) {
        super(mvpView);
    }

    public void loadBookCityData(){
//        mvpView.showLoading();
        addSubscription(apiStores.loadBookCityData(),
                new ApiCallback<IndexLablesEntity>() {
                    @Override
                    public void onSuccess(IndexLablesEntity model) {
                        mvpView.geDataSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }
}
