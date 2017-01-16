package com.example.tai_fiction.ui.activity.webview;

import android.app.Activity;
import android.os.Bundle;

import com.example.tai_fiction.R;
import com.example.tai_fiction.base.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JinBao on 2017/1/9.
 */

public class WebViewActivity extends MvpActivity<WebViewPresenter> implements WebView{

    @Bind(R.id.webview)
    WebView webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {

    }

    @Override
    protected WebViewPresenter createPresenter() {
        return new WebViewPresenter(this);
    }


}
