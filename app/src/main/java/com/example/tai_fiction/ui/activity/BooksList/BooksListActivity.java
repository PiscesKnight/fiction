package com.example.tai_fiction.ui.activity.booksList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tai_fiction.R;
import com.example.tai_fiction.adapter.SecondpageAdapter;
import com.example.tai_fiction.base.mvp.MvpActivity;
import com.example.tai_fiction.entity.BookIntroEntity;
import com.example.tai_fiction.entity.IndexLablesEntity;
import com.example.tai_fiction.tool.view.FlowLayout;
import com.example.tai_fiction.tool.view.ListViewForScrollView;
import com.example.tai_fiction.ui.activity.bookdetails.BookDetailsActivity;
import com.example.tai_fiction.ui.fragment.bookcity.BookCityFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by TAI on 2015/12/5.
 */
public class BooksListActivity extends MvpActivity<BooksListPresenter> implements BooksListView {
    @Bind(R.id.classify_back_btn)
    ImageButton classifyBackBtn;
    @Bind(R.id.classify_title_tv)
    TextView lableText;//返回按钮的文字
    @Bind(R.id.back_btn_ly)
    LinearLayout backBtnLy;//返回按钮
    @Bind(R.id.first_lable_fl)
    FlowLayout firstLableFl;//第一块标签
    @Bind(R.id.classify_content_lv)
    ListViewForScrollView listView;//列表
    @Bind(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout frameLayout;//下拉刷新呢


    private int count;//记录标签的个数
    private String text;//存储上一个活动传下来的lable
    private int sid;//存储sid值

    private List<IndexLablesEntity.BookBean.ItemsBean> itemsBeans;

    private int start = 0;//url参数

    private String isNull;//存储参数categoryid

    @Override
    public int getLayoutId() {
        return R.layout.bookcity_click_ui;
    }

    @Override
    protected BooksListPresenter createPresenter() {
        return new BooksListPresenter(this, this);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        itemsBeans = getIntent().getParcelableArrayListExtra(BookCityFragment.PAR_KEY);
        text = intent.getStringExtra("lable_data");
        sid = intent.getIntExtra("sid_data", 0);
        count = itemsBeans.size();

        lableText.setText(text);//将上一个活动的lable传进来

        installData();
        setLabels();//设置标签布局
        refreshBookData();//刷新数据
    }


    /**
     * 动态添加textview，流式布局
     */
    private TextView textView = null;//中间变量使用
    private int i;

    @Override
    public void setLabels() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final TextView all = (TextView) layoutInflater.inflate(R.layout.textview, firstLableFl, false);
        all.setText("全部");
        all.setTypeface(Typeface.DEFAULT_BOLD);
        all.setTextColor(Color.parseColor("#00BFFF"));
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setTypeface(Typeface.DEFAULT_BOLD);
                all.setTextColor(Color.parseColor("#00BFFF"));
                textView.setTypeface(Typeface.DEFAULT);
                textView.setTextColor(Color.parseColor("#969696"));
                installData();

            }
        });
        firstLableFl.addView(all);

        for (i = 0; i < count; i++) {
            final TextView tabel = (TextView) layoutInflater.inflate(R.layout.textview,
                    firstLableFl, false);
            tabel.setText(itemsBeans.get(i).getLabel().toString());
            final TextView categoryid = (TextView) layoutInflater.inflate(R.layout.textview,
                    firstLableFl, false);
            categoryid.setText(itemsBeans.get(i).getCategory_id().toString());//接收categoriId来发送请求
            categoryid.setVisibility(View.GONE);
            firstLableFl.addView(tabel);
            tabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    all.setTypeface(Typeface.DEFAULT);
                    all.setTextColor(Color.parseColor("#969696"));
                    if (textView == null) {//使用中间变量实现上一个点击的textView功能更新
                        tabel.setTypeface(Typeface.DEFAULT_BOLD);
                        tabel.setTextColor(Color.parseColor("#00BFFF"));
                        textView = (TextView) v;
                    } else {
                        tabel.setTypeface(Typeface.DEFAULT);
                        textView.setTextColor(Color.parseColor("#969696"));
                        tabel.setTypeface(Typeface.DEFAULT_BOLD);
                        tabel.setTextColor(Color.parseColor("#00BFFF"));
                        textView = tabel;
                    }
                    isNull = categoryid.getText().toString();
                    mPresenter.loadLableBooksData(categoryid.getText().toString());

                }
            });

        }
    }

    /**
     * 插入数据
     */
    @Override
    public void installData() {
        // 如果表中有sid值
        if (mPresenter.isSidTure(sid)) {
            mPresenter.setBooksData(mPresenter.getBookDB().getDataForSqlite(sid));
            bingListViewData(mPresenter.getBooksData());
        } else {
            //  开始绑定，组装数据
            mPresenter.loadBooksListData(sid);

        }
        frameLayout.refreshComplete();
    }

    /**
     * ListView绑定数据
     *
     * @param booksData
     */
    @Override
    public void bingListViewData(final List<BookIntroEntity.ItemsBean> booksData) {
        SecondpageAdapter secondpageAdapter = new SecondpageAdapter(BooksListActivity.this, R.layout.bookcity_click_listview_item, booksData);
        listView.setAdapter(secondpageAdapter);
        secondpageAdapter.notifyDataSetChanged();
        listView.setFocusable(false);//解决打开套有 ListVew的 ScrollView的页面布局 默认 起始位置不是最顶部。
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String bookId = booksData.get(position).getBook_id();
                Intent intent = new Intent(BooksListActivity.this, BookDetailsActivity.class);
                intent.putExtra("bookId_data", bookId);
                startActivity(intent);
            }
        });
    }


    @Override
    public void getDataSuccess(BookIntroEntity model) {
        if (isNull != null) {
            mPresenter.setBooksData(model.getItems()) ;//用于获取点击标签后的图书列表数据
        } else {
            mPresenter.getBooksData().addAll(model.getItems());
        }

            bingListViewData(mPresenter.getBooksData());//listview绑定数据
            mPresenter.getBookDB().installData(mPresenter.getBooksData(), sid);
            frameLayout.refreshComplete();
    }

    @Override
    public void onFailure(String msg) {
        showShortToast("你的网络吃屎啊！");
    }

    /**
     * 上下拉刷新数据
     */
    @Override
    public void refreshBookData() {
        frameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        frameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                start += 10;
                mPresenter.loadMoreBooksData(sid, start);
                frameLayout.refreshComplete();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                installData();
                frameLayout.refreshComplete();

            }
        });
    }


    @OnClick(R.id.back_btn_ly)//返回键
    public void onClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
