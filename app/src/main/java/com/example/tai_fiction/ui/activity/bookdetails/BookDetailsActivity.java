package com.example.tai_fiction.ui.activity.bookdetails;

import android.content.Intent;
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
import com.example.tai_fiction.tool.PhoneStateUtil;
import com.example.tai_fiction.ui.novelview.NovelViewActivity;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import butterknife.Bind;
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

                UMShareListener umShareListener = new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        showShortToast("成功了");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        showShortToast("发生错误");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        showShortToast("取消了");
                    }
                };
                new ShareAction(BookDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText("hello")
                        .setCallback(umShareListener)
                        .share();
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
    public void getDataSuccess(BookDetailsEntity model) {
        itemBean = model.getItem();

        titleTv.setText(itemBean.getTitle());
        titleTopTv.setText(itemBean.getTitle());
        String cover = itemBean.getCover();
        String newCover = cover.substring(0,cover.length()-2);
        authorsTv.setText(itemBean.getAuthors());
        introText.setText(itemBean.getContent());
//        writerIntroText.setText(itemBean.getAuthor_detail().get(1).getIntroduction());

        Picasso.with(BookDetailsActivity.this)
                .load(newCover)
                .placeholder(R.mipmap.abv123456)
                .error(R.mipmap.abv123456)
                .centerCrop()
                .resize(100, 130)
                .into(coverIv);

        List<String> tags = itemBean.getTags();//取得类别标签

        lableBtn1.setText(tags.get(0));
        lableBtn2.setText(tags.get(1));
        lableBtn3.setText(tags.get(2));

        frameLayout.refreshComplete();
    }

    @Override
    public void onFailure(String msg) {
        showShortToast("你网络不给力啊");
    }
}
