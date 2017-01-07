package com.example.tai_fiction.activity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tai_fiction.R;

/**
 * Created by TAI on 2015/12/9.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private LinearLayout backLL;
    private ImageButton pwdVisibleBtn;
    private EditText userEdit, pwdLoginEdit;
    private boolean isVisible = true;//密码是否可见
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initView();
    }

    private void initView() {
        backLL = (LinearLayout) findViewById(R.id.login_back_btn_ly);
        pwdVisibleBtn = (ImageButton) findViewById(R.id.pwd_visible_btn);
        userEdit = (EditText) findViewById(R.id.user_edit);
        pwdLoginEdit = (EditText) findViewById(R.id.login_pwd_edit);
        loginBtn = (Button) findViewById(R.id.login_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back_btn_ly:
                finish();
                break;
            case R.id.pwd_visible_btn:
                if (isVisible) {
                    //设置EditText的密码为可见的
                    pwdLoginEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisible = false;
                } else {
                    //设置密码为隐藏的
                    pwdLoginEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isVisible = true;
                }
                break;
            case R.id.login_btn:
                if(userEdit.getText().toString().equals("")){
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (userEdit.getText().toString().equals("123") && !pwdLoginEdit.getText().toString().equals("")) {
                    if (pwdLoginEdit.getText().toString().equals("123")) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("data_return", "林泰");
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(this, "用户名或密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                        pwdLoginEdit.setText("");
                    }
                }
                else if (!userEdit.getText().toString().equals("") && pwdLoginEdit.getText().toString().equals("")){
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                Toast.makeText(this, "用户名或密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                pwdLoginEdit.setText("");
            }

                break;
        }
    }
}
