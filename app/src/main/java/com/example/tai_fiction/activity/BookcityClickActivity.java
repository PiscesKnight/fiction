package com.example.tai_fiction.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tai_fiction.DB.BookDB;
import com.example.tai_fiction.R;
import com.example.tai_fiction.adapter.SecondpageAdapter;
import com.example.tai_fiction.entity.BookIntroEntity;
import com.example.tai_fiction.entity.IndexLablesEntity;
import com.example.tai_fiction.ui.fragment.bookcity.BookCityFragment;
import com.example.tai_fiction.tool.PhoneStateUtil;
import com.example.tai_fiction.tool.view.FlowLayout;
import com.example.tai_fiction.tool.OkHttpClientManager;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by TAI on 2015/12/5.
 */
public class BookcityClickActivity extends Activity implements View.OnClickListener {
    private ListView listView;//列表
    private LinearLayout backBtnLy;//返回按钮
    private FlowLayout firstLableFl;//第一块标签
    private TextView lableText;//返回按钮的文字
    private ProgressBar progressBar;

    private PtrClassicFrameLayout frameLayout;//下拉刷新呢


    private List<BookIntroEntity.ItemsBean> booksData = null;//记录LablesEntity.BookBean.Items里是数据
    private int count;//记录标签的个数
    private String URL;//请求图书列表的地址
    private String text;//存储上一个活动传下来的lable
    private int sid;//存储sid值

    private List<IndexLablesEntity.BookBean.ItemsBean> itemsBeans;

    private BookDB bookDB;

    private int start = 0;//url参数

    private PhoneStateUtil phoneStateUtil = new PhoneStateUtil();//手机状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookcity_click_ui);

        booksData = new ArrayList<>();

        bookDB = BookDB.getInstance(this, "Book.db");

        Intent intent = getIntent();
        itemsBeans = getIntent().getParcelableArrayListExtra(BookCityFragment.PAR_KEY);
        text = intent.getStringExtra("lable_data");
        sid = intent.getIntExtra("sid_data", 0);
        count = itemsBeans.size();

        URL = "http://www.duokan.com/store/v0/android/category/" + sid + "?start=0&count=10";
        initView();
        installData();


    }

    private void initView() {

        listView = (ListView) findViewById(R.id.classify_content_lv);
        firstLableFl = (FlowLayout) findViewById(R.id.first_lable_fl);
        backBtnLy = (LinearLayout) findViewById(R.id.back_btn_ly);
        lableText = (TextView) findViewById(R.id.classify_title_tv);
        frameLayout = (PtrClassicFrameLayout) findViewById(R.id.store_house_ptr_frame);
        //  progressBar = (ProgressBar)findViewById(R.id.bookcity_click_progressbar);

        lableText.setText(text);//将上一个活动的lable传进来

        setLabels();//设置标签布局
        refreshBookData();//刷新数据
    }


    /**
     * 动态添加textview，流式布局
     */
    private TextView textView = null;//中间变量使用
    private int i;

    private void setLabels() {
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
                    String url = "http://www.duokan.com/store/v0/android/category/" + categoryid.getText() + "?start=0&count=10";
                    getLabelBooksData(url);
                }
            });

        }
    }


    /**
     * 网络请求获取数据绑定控件
     *
     * @param URL
     */
    private void getBooksData(String URL) {
        OkHttpClientManager.getAsyn(URL, new OkHttpClientManager.ResultCallback<BookIntroEntity>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(BookIntroEntity response) {

                booksData.addAll(response.getItems());
                if (booksData != null) {
                    //  progressBar.setVisibility(View.GONE);
                    bingListViewData(booksData);//listview绑定数据
                    bookDB.installData(booksData, sid);

                }

                frameLayout.refreshComplete();
            }
        });
    }

    /**
     * 获取标签的图书列表
     */
    private void getLabelBooksData(String url) {
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<BookIntroEntity>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(BookIntroEntity response) {
                booksData = response.getItems();
                bingListViewData(booksData);//listview绑定数据
                bookDB.installData(booksData, sid);
            }
        });
    }


    private void installData() {

        boolean isTure = bookDB.isTureBook(sid);
        // 如果表中有sid值
        if (isTure) {
            booksData = bookDB.getDataForSqlite(sid);
            bingListViewData(booksData);
        } else {
            //  开始绑定，组装数据
            getBooksData(URL);

        }
        frameLayout.refreshComplete();
    }

    /**
     * ListView绑定数据
     *
     * @param booksData
     */
    private void bingListViewData(final List<BookIntroEntity.ItemsBean> booksData) {
        SecondpageAdapter secondpageAdapter = new SecondpageAdapter(BookcityClickActivity.this, R.layout.bookcity_click_listview_item, booksData);
        listView.setAdapter(secondpageAdapter);
        secondpageAdapter.notifyDataSetChanged();
        listView.setFocusable(false);//解决打开套有 ListVew的 ScrollView的页面布局 默认 起始位置不是最顶部。
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String bookId = booksData.get(position).getBook_id();
                Intent intent = new Intent(BookcityClickActivity.this, BookDetailsActivity.class);
                intent.putExtra("bookId_data", bookId);
                startActivity(intent);
            }
        });
    }

    /**
     * 上下拉刷新数据
     */
    private void refreshBookData() {
        frameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        frameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

                if (phoneStateUtil.isNetworkAvailable(BookcityClickActivity.this)) {
                    start += 10;
                    URL = "http://www.duokan.com/store/v0/android/category/" + sid + "?start="
                            + start + "&count=10";
                    getBooksData(URL);
                    frameLayout.refreshComplete();
                } else {
                    Toast.makeText(BookcityClickActivity.this, "当前没有网络，请检查你的网络！", Toast.LENGTH_LONG).show();
                    frameLayout.refreshComplete();
                }

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (phoneStateUtil.isNetworkAvailable(BookcityClickActivity.this)) {
                    installData();
                    frameLayout.refreshComplete();
                } else {
                    Toast.makeText(BookcityClickActivity.this, "当前没有网络，请检查你的网络！", Toast.LENGTH_LONG).show();
                    frameLayout.refreshComplete();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn_ly://返回键
                finish();
                break;
        }
    }
}
