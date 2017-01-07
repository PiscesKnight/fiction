package com.example.tai_fiction.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.tai_fiction.base.BaseActivity;
import com.example.tai_fiction.base.BasePresenter;

/**
 * Created by JinBao on 2017/1/7.
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {

    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
    }

}
