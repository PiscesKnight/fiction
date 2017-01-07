package com.example.tai_fiction.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tai_fiction.R;
import com.example.tai_fiction.adapter.FragmentAdapter;
import com.example.tai_fiction.base.mvp.MvpActivity;
import com.example.tai_fiction.tool.view.CircleImageView;
import com.example.tai_fiction.ui.fragment.bookcity.BookCityFragment;
import com.example.tai_fiction.ui.fragment.bookrack.BookRackFragment;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by 泰子 on 2015/10/17.
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView{
    @Bind(R.id.user_btn)
    ImageButton userBtn;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.search_btn)
    ImageButton searchBtn;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.nv_main_navigation)
    NavigationView navigatitonView;
    @Bind(R.id.dl_main_drawer)
    DrawerLayout mDrawerLayout;

    private Button loginBtn;
    private TextView userName;
    private TextView introduceMyselfText;//自我介绍
    private CircleImageView userInfoIv;

    private View headerView;//add by 20151210

    private Intent intent;

    private String returnData = "";//从上一个活动返回的数据

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        //注册事件
        userInfoIv = (CircleImageView) findViewById(R.id.userInfo_iv);

        //获取抽屉头文件View
        headerView = navigatitonView.getHeaderView(0);
        setupDrawerContent(navigatitonView);
        if (viewPager != null) {
            setViewPager(viewPager);
        }
        tabLayout.setupWithViewPager(viewPager);

        loginBtn = (Button) headerView.findViewById(R.id.nv_header_login_btn);
        userName = (TextView) headerView.findViewById(R.id.nv_header_text);
        introduceMyselfText = (TextView) headerView.findViewById(R.id.introduce_myself_text);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    /**
     * 开启抽屉菜单
     *
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home://我的收藏
                                Intent intent = new Intent(MainActivity.this, MycollectActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_home2://我的分享
                                break;
                            case R.id.nav_home3:
                                break;
                            case R.id.nav_home4:
                                new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                                        .setMessage("确定要退出吗？")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    /**
     * 设置ViewPager内容数据
     *
     * @param viewPager
     */
    private void setViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragemnt(new BookCityFragment(), "书城");
        adapter.addFragemnt(new BookRackFragment(), "书架");
        viewPager.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    returnData = data.getStringExtra("data_return");
                    userName.setText(returnData);
                    loginBtn.setVisibility(View.GONE);
                    introduceMyselfText.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    returnData = data.getStringExtra("data_return");
                    userName.setText(returnData);
                    loginBtn.setVisibility(View.VISIBLE);
                    introduceMyselfText.setVisibility(View.GONE);
                    userBtn.setClickable(true);
//      mDrawerLayout.openDrawer(Gravity.START);
                }
                break;
            default:
        }
    }

    @OnClick(R.id.user_btn)
    public void onClick(View view) {
                switch (view.getId()) {
            case R.id.nv_header_login_btn:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.user_btn://用户菜单
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.userInfo_iv://个人资料
                if (userName.getText().toString().equals(getString(R.string.pleaselogin))) {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    intent = new Intent(MainActivity.this, UserActivity.class);
                    intent.putExtra("extra_data", returnData);
                    startActivityForResult(intent, 2);
                }

                break;
        }
    }
}
