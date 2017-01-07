package com.example.tai_fiction.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tai_fiction.DB.BookDB;
import com.example.tai_fiction.R;
import com.example.tai_fiction.entity.BookDetailsEntity;
import com.example.tai_fiction.ui.novelview.NovelViewActivity;
import com.example.tai_fiction.tool.OkHttpClientManager;
import com.example.tai_fiction.tool.PhoneStateUtil;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Tai on 2016/3/27.
 */
public class BookDetailsActivity extends Activity implements View.OnClickListener {


    private Button startReadBtn;//开始阅读
    private TextView introText;//书籍简介
    private TextView writerIntroText;//作者简介
    private ImageView arrowsAIv, arrowsBIv;//下箭头
    private LinearLayout backBtnLy;//返回键
    private Button shareBtn;//分享按钮
    private Button collectBtn;//收藏按钮
    private Button addBookBtn;//加入书架
    private ImageView coverIv;//封面
    private TextView titleTv;//书名
    private TextView authorsTv;//作者
    private TextView titleTopTv;//顶部标题
    private Button lableBtn1,lableBtn2,lableBtn3;//类别标签
    private PtrClassicFrameLayout frameLayout;//刷新

    boolean isClick = true;//是否点击内容完全展示

    private String bookId;//存储上个活动传递的数据

    private String URL;

    private BookDB bookDB;

    private BookDetailsEntity.ItemBean itemBean;

    private boolean isSuccessful;

    private PhoneStateUtil phoneStateUtil = new PhoneStateUtil();//手机状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookcity_details);
        bookDB = BookDB.getInstance(this, "Book.db");
        ShareSDK.initSDK(this,"285ce8dba339");
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

        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId_data");
        URL = "http://www.duokan.com/hs/v0/android/store/book/" + bookId;
        getBookData(URL);
        initView();
    }

    private void initView() {
        startReadBtn = (Button) findViewById(R.id.bookdetails_startread_btn);
        introText = (TextView) findViewById(R.id.bookdetails_content_tv);
        writerIntroText = (TextView) findViewById(R.id.bookdetails_writerIntro_tv);
        arrowsAIv = (ImageView) findViewById(R.id.arrows_a_iv);
        arrowsBIv = (ImageView) findViewById(R.id.arrows_b_iv);
        backBtnLy = (LinearLayout) findViewById(R.id.bookdetails_back_ly);
        shareBtn = (Button) findViewById(R.id.bookdetails_share_btn);
        coverIv = (ImageView) findViewById(R.id.bookcity_content_iv);
        titleTv = (TextView) findViewById(R.id.bookdetails_title);
        authorsTv = (TextView) findViewById(R.id.bookdetails_authors);
        collectBtn = (Button) findViewById(R.id.bookcitydetails_collect_btn);
        addBookBtn = (Button)findViewById(R.id.bookdetails_addbook_btn);
        titleTopTv = (TextView)findViewById(R.id.bookdetails_title_tv);
        lableBtn1 = (Button)findViewById(R.id.lable_btn1);
        lableBtn2 = (Button)findViewById(R.id.lable_btn2);
        lableBtn3 = (Button)findViewById(R.id.lable_btn3);
        frameLayout = (PtrClassicFrameLayout)findViewById(R.id.bookdetails_store_house_ptr_frame);

        if(writerIntroText.getLineCount()<5){
            arrowsBIv.setVisibility(View.GONE);
        }
        else {
            arrowsBIv.setVisibility(View.VISIBLE);
        }
        if(introText.getLineCount()<7){
            arrowsAIv.setVisibility(View.GONE);
        }
        else {
            arrowsAIv.setVisibility(View.VISIBLE);
        }
        refreshData();//刷新数据
    }


    private void getBookData(String URL) {
        OkHttpClientManager.getAsyn(URL, new OkHttpClientManager.ResultCallback<BookDetailsEntity>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(BookDetailsEntity response) {
                itemBean = response.getItem();

                titleTv.setText(itemBean.getTitle());
                titleTopTv.setText(itemBean.getTitle());
                String cover = itemBean.getCover();
                authorsTv.setText(itemBean.getAuthors());
                introText.setText(itemBean.getContent());
                writerIntroText.setText(itemBean.getAuthor_detail().get(0).getIntroduction());

                Picasso.with(BookDetailsActivity.this)
                        .load(cover)
                        .placeholder(R.mipmap.abv123456)
                        .error(R.mipmap.abv123456)
                        .centerCrop()
                        .resize(100, 130)
                        .into(coverIv);

                List<String> tags = itemBean.getTags();//取得类别标签

                lableBtn1.setText(tags.get(tags.size()-1).toString());
                lableBtn2.setText(tags.get(0).toString());
                lableBtn3.setText(tags.get(1).toString());

                frameLayout.refreshComplete();
            }
        });
    }

    /**
     * 刷新
     * @param
     */
    private void refreshData(){
        frameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                if(phoneStateUtil.isNetworkAvailable(BookDetailsActivity.this)){
                    getBookData(URL);
                    frameLayout.refreshComplete();
                }
                else {
                    Toast.makeText(BookDetailsActivity.this,"当前没有网络，请检查你的网络！",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bookdetails_startread_btn://开始阅读
                Intent intent = new Intent(BookDetailsActivity.this, NovelViewActivity.class);
                startActivity(intent);
                break;
            case R.id.bookdetails_content_tv://书籍简介
                if (isClick) {
                    introText.setEllipsize(TextUtils.TruncateAt.START);
                    introText.setMaxLines(100);
                    isClick = false;
                    arrowsAIv.setVisibility(View.GONE);
                } else {
                    introText.setEllipsize(TextUtils.TruncateAt.END);
                    introText.setMaxLines(7);
                    isClick = true;
                    arrowsAIv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bookdetails_writerIntro_tv://作者信息

                if (isClick) {
                    writerIntroText.setEllipsize(TextUtils.TruncateAt.START);
                    writerIntroText.setMaxLines(100);
                    isClick = false;
                    arrowsBIv.setVisibility(View.GONE);
                } else {
                    writerIntroText.setEllipsize(TextUtils.TruncateAt.END);
                    writerIntroText.setMaxLines(5);
                    isClick = true;
                    arrowsBIv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bookdetails_back_ly://返回键
                finish();
                break;

            case R.id.bookcitydetails_collect_btn://收藏按钮

                isSuccessful =  bookDB.addCollect(itemBean);//存储数据到数据库
                if(isSuccessful){
                    Toast.makeText(BookDetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(BookDetailsActivity.this, "已经收藏过", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bookdetails_share_btn://分享按钮
//                share = new ShareUtils(this);
//                share.SendBinaryImageToWX(this);

                if(phoneStateUtil.isNetworkAvailable(this)){
                    OnekeyShare oks = new OnekeyShare();
                    //关闭sso授权
                    oks.disableSSOWhenAuthorize();
                    oks.setImageUrl(itemBean.getCover().toString());
                    // text是分享文本，所有平台都需要这个字段
                    oks.setTitle(itemBean.getTitle().toString());
                    oks.setText(itemBean.getContent().toString());
                    // url仅在微信（包括好友和朋友圈）中使用
                    oks.setUrl(itemBean.getCover().toString());
                    // 启动分享GUI
                    oks.show(this);
                }
                else {
                 Toast.makeText(this,"分享失败！请检查你的网络！",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.bookdetails_addbook_btn:
                isSuccessful = bookDB.addBookRack(itemBean);//判断数据库有没有相同的数据（被收藏过）
                if(isSuccessful){
                    Toast.makeText(BookDetailsActivity.this, "成功加入到书架", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(BookDetailsActivity.this, "抱歉，书架已经有啦", Toast.LENGTH_SHORT).show();
                }

                break;
//            case R.id.arrows_a_iv://点击上箭头
//                if (isClick) {
//                    introText.setEllipsize(TextUtils.TruncateAt.START);
//                    introText.setMaxLines(100);
//                    isClick = false;
//                    arrowsAIv.setVisibility(View.GONE);
//                } else {
//                    introText.setEllipsize(TextUtils.TruncateAt.END);
//                    introText.setMaxLines(7);
//                    isClick = true;
//                    arrowsAIv.setVisibility(View.VISIBLE);
//                }
//                break;
//            case R.id.arrows_b_iv://下点击箭头
//                if (isClick) {
//                    writerIntroText.setEllipsize(TextUtils.TruncateAt.START);
//                    writerIntroText.setMaxLines(100);
//                    isClick = false;
//                    arrowsBIv.setVisibility(View.GONE);
//                } else {
//                    writerIntroText.setEllipsize(TextUtils.TruncateAt.END);
//                    writerIntroText.setMaxLines(5);
//                    isClick = true;
//                    arrowsBIv.setVisibility(View.VISIBLE);
//                }
//                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }
}
