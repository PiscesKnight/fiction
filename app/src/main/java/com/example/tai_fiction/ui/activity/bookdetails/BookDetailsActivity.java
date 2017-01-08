package com.example.tai_fiction.ui.activity.bookdetails;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tai_fiction.R;
import com.example.tai_fiction.base.mvp.MvpActivity;
import com.example.tai_fiction.entity.BookDetailsEntity;
import com.example.tai_fiction.tool.OkHttpClientManager;
import com.example.tai_fiction.tool.PhoneStateUtil;
import com.example.tai_fiction.ui.novelview.NovelViewActivity;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Tai on 2016/3/27.
 */
public class BookDetailsActivity extends MvpActivity<BookDetailsPresenter> implements BookDetailsView ,View.OnClickListener{


    @Bind(R.id.bookdetails_title_tv)
    TextView titleTopTv;//顶部标题
    @Bind(R.id.bookdetails_back_ly)
    LinearLayout backBtnLy;//返回键
    @Bind(R.id.bookcity_content_iv)
    ImageView coverIv;//封面
    @Bind(R.id.bookdetails_title)
    TextView titleTv;///书名
    @Bind(R.id.bookdetails_authors)
    TextView authorsTv;//作者
    @Bind(R.id.bookdetails_startread_btn)
    Button startReadBtn;//开始阅读
    @Bind(R.id.bookcitydetails_collect_btn)
    Button collectBtn;//收藏按钮
    @Bind(R.id.bookdetails_share_btn)
    Button shareBtn;//分享按钮
    @Bind(R.id.bookdetails_addbook_btn)
    Button addBookBtn;//加入书架
    @Bind(R.id.bookdetails_content_tv)
    TextView introText;//书籍简介
    @Bind(R.id.arrows_a_iv)
    ImageView arrowsAIv;//下箭头
    @Bind(R.id.bookdetails_writerIntro_tv)
    TextView writerIntroText;//作者简介
    @Bind(R.id.arrows_b_iv)
    ImageView arrowsBIv;//下箭头
    @Bind(R.id.lable_btn1)
    Button lableBtn1;//类别标签
    @Bind(R.id.lable_btn2)
    Button lableBtn2;//类别标签
    @Bind(R.id.lable_btn3)
    Button lableBtn3;//类别标签
    @Bind(R.id.bookdetails_store_house_ptr_frame)
    PtrClassicFrameLayout frameLayout;

    boolean isClick = true;//是否点击内容完全展示

    private String bookId;//存储上个活动传递的数据

    private String URL;

    private BookDetailsEntity.ItemBean itemBean;

    private boolean isSuccessful;

    private PhoneStateUtil phoneStateUtil = new PhoneStateUtil();//手机状态


    @Override
    public int getLayoutId() {
        return R.layout.bookcity_details;
    }

    @Override
    protected BookDetailsPresenter createPresenter() {
        return new BookDetailsPresenter(this, this);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId_data");

        mPresenter.getBookDetalisData(bookId);

        if (writerIntroText.getLineCount() < 5) {
            arrowsBIv.setVisibility(View.GONE);
        } else {
            arrowsBIv.setVisibility(View.VISIBLE);
        }
        if (introText.getLineCount() < 7) {
            arrowsAIv.setVisibility(View.GONE);
        } else {
            arrowsAIv.setVisibility(View.VISIBLE);
        }
        refreshData();//刷新数据
    }


    /**
     * 刷新
     *
     * @param
     */
    @Override
    public void refreshData() {
        frameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                    mPresenter.getBookDetalisData(bookId);
                    frameLayout.refreshComplete();
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
                isSuccessful = mPresenter.getBookDB().addCollect(itemBean);//存储数据到数据库
                if (isSuccessful) {
                    Toast.makeText(BookDetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookDetailsActivity.this, "已经收藏过", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bookdetails_share_btn://分享按钮
//                share = new ShareUtils(this);
//                share.SendBinaryImageToWX(this);

                if (phoneStateUtil.isNetworkAvailable(this)) {
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
                } else {
                    Toast.makeText(this, "分享失败！请检查你的网络！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bookdetails_addbook_btn:
                isSuccessful = mPresenter.getBookDB().addBookRack(itemBean);//判断数据库有没有相同的数据（被收藏过）
                if (isSuccessful) {
                    Toast.makeText(BookDetailsActivity.this, "成功加入到书架", Toast.LENGTH_SHORT).show();
                } else {
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

    @Override
    public void getDataSuccess(BookDetailsEntity model) {
        itemBean = model.getItem();

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

        lableBtn1.setText(tags.get(tags.size() - 1).toString());
        lableBtn2.setText(tags.get(0).toString());
        lableBtn3.setText(tags.get(1).toString());

        frameLayout.refreshComplete();
    }

    @Override
    public void onFailure(String msg) {
        showShortToast("你网络不给力啊");
    }
}
