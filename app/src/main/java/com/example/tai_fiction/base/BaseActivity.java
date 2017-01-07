package com.example.tai_fiction.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.tai_fiction.api.ApiStores;
import com.example.tai_fiction.tool.LogUtil;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by JinBao on 2017/1/7.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Activity mActivity;

    private ApiStores apiStores;

    private CompositeSubscription mCompositeSubscription;

    private boolean isExit;

    private long mExitTime = 0;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        mActivity = this;
        ButterKnife.bind(this);
        this.initView();
    }

    //获取布局资源
    public abstract int getLayoutId();

    //初始化控件
    public abstract void initView();

    //自定义提示语
    protected void showShortToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isExit){
                if((System.currentTimeMillis() - mExitTime)>2000){
                    showShortToast("再按一次就退出了哦！");
                    mExitTime = System.currentTimeMillis();
                }
                else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode,event);
    }

    //显示进度条
    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        return progressDialog;
    }

    //自定进度条
    public ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    //隐藏进度条
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
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

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void onUnsubscribe() {
        LogUtil.d("onUnsubscribe");
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }
    @Override
    protected void onDestroy() {
        onUnsubscribe();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
