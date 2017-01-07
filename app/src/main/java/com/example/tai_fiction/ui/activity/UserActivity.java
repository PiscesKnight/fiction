package com.example.tai_fiction.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tai_fiction.R;

/**
 * Created by Tai on 2016/3/30.
 */
public class UserActivity extends Activity implements View.OnClickListener {

    private LinearLayout exitLy;//退出账号
    private TextView nickName;//昵称
    private LinearLayout backBtnLy;//返回


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
    }

    private void initView() {
        exitLy = (LinearLayout) findViewById(R.id.exit_ly);
        nickName = (TextView) findViewById(R.id.nickName_text);
        backBtnLy = (LinearLayout) findViewById(R.id.userInfo_back_btn_ly);

        nickName.setText(getUserInfo());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_ly://退出账号
                Intent intent = new Intent();
                intent.putExtra("data_return", getString(R.string.pleaselogin));
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.userInfo_back_btn_ly://返回上一层
                finish();
                break;
        }
    }

    /**
     * 接收从上一个活动传递下来的数据
     */
    private String getUserInfo() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        return data;
    }
}

