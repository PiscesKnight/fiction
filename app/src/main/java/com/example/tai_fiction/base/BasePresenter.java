package com.example.tai_fiction.base;

import android.view.View;

import com.example.tai_fiction.api.ApiStores;
import com.example.tai_fiction.api.AppClient;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.tai_fiction.R.styleable.View;

/**
 * Created by JinBao on 2017/1/7.
 */

public class BasePresenter<V> {

    public V mvpView;
    protected ApiStores apiStores;
    private CompositeSubscription mCompositeSubscription;

    public BasePresenter(V mvpView){
        attachView(mvpView);
    }

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = AppClient.retrofit().create(ApiStores.class);
    }


    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }
}
