package com.example.tai_fiction.novelview;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.tai_fiction.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class NovelViewActivity extends AppCompatActivity implements View.OnClickListener {

    private NovelView novelView;
    private NovelViewAdapter adapter;
    private PopupWindow bottomPopupWindow;
    private PopupWindow topPopupWindow;
    private View topMenuView;
    private View bottomMenuView;
    private boolean isOpen = false;//是否打开菜单
    private ImageView menu, ivSetFont, ivLuminanceIv, ivDirectory, ivMore;//菜单按钮
    private Button menuBackBtn;//菜单返回按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_novelview);

        initView();

    }


    private void initView() {
        //注册菜单
        bottomMenuView = LayoutInflater.from(this).inflate(R.layout.popupwindow_bottom_menu, null, false);
        bottomPopupWindow = new PopupWindow(bottomMenuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        topMenuView = LayoutInflater.from(this).inflate(R.layout.popupwindow_top_menu, null, false);
        topPopupWindow = new PopupWindow(topMenuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);


        novelView = (NovelView) findViewById(R.id.novelView);
        menu = (ImageView) findViewById(R.id.menu);
        menuBackBtn = (Button) topMenuView.findViewById(R.id.menu_back_btn);
        ivSetFont = (ImageView) bottomMenuView.findViewById(R.id.set_font);
        ivLuminanceIv = (ImageView) bottomMenuView.findViewById(R.id.luminance);
        ivDirectory = (ImageView) bottomMenuView.findViewById(R.id.directory);
        ivMore = (ImageView) bottomMenuView.findViewById(R.id.more);


        menu.setOnClickListener(this);
        menuBackBtn.setOnClickListener(this);
        ivSetFont.setOnClickListener(this);
        ivLuminanceIv.setOnClickListener(this);
        ivDirectory.setOnClickListener(this);
        ivMore.setOnClickListener(this);

        //数据填充
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add((i + 1) + "");
            adapter = new NovelViewAdapter(this, data);
            novelView.setAdapter(adapter);
        }
    }

    /**
     * 顶部弹出菜单
     */
    private void showTopMenu() {
        topPopupWindow.setBackgroundDrawable(new ColorDrawable(0xb00000));
        topPopupWindow.setFocusable(false);
        topPopupWindow.setAnimationStyle(R.style.toppopwindow_anim_style);
        topPopupWindow.showAtLocation(novelView, Gravity.TOP, 0, 0);
        topMenuView.setAlpha(0.9F);
        topPopupWindow.update();
    }

    /**
     * 底部弹出菜单
     */

    private void showBottomMenu() {
        bottomPopupWindow.setBackgroundDrawable(new ColorDrawable(0xb00000));
        bottomPopupWindow.setFocusable(false);
        bottomPopupWindow.setAnimationStyle(R.style.buttompopwindow_anim_style);
        bottomPopupWindow.showAtLocation(novelView, Gravity.BOTTOM, 0, 0);
        bottomMenuView.setAlpha(0.9F);
        bottomPopupWindow.update();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu:
                if (isOpen) {
                    topPopupWindow.dismiss();
                    bottomPopupWindow.dismiss();

                    isOpen = false;
                } else {
                    showBottomMenu();
                    showTopMenu();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                    isOpen = true;
                }
                break;
            case R.id.menu_back_btn:
                finish();
                break;
            case R.id.set_font://设置字体
                break;
            case R.id.luminance://亮度
                break;
            case R.id.directory://目录
                break;
            case R.id.more://更多
                break;
        }
    }
}
