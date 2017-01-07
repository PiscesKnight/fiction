package com.example.tai_fiction.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tai_fiction.DB.BookDB;
import com.example.tai_fiction.DB.BookDatabaseHelper;
import com.example.tai_fiction.R;
import com.example.tai_fiction.adapter.MyCollectAdapter;
import com.example.tai_fiction.adapter.SecondpageAdapter;
import com.example.tai_fiction.entity.MyCollectEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tai on 2016/4/13.
 */
public class MycollectActivity extends Activity {

    private ListView listView;
    private TextView write, title, time, bookcount;//作者，标题，时间，读过人数
    private ImageView cover;//封面
    private LinearLayout backBtnLy;

    private List<MyCollectEntity> booksData;

    private BookDB bookDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycollect_content);
        bookDB = BookDB.getInstance(this, "Book.db");
        initView();
        bingData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.mycollect_listview);
        write = (TextView) findViewById(R.id.mycollect_write_text);
        title = (TextView) findViewById(R.id.mycollect_title_text);
        time = (TextView) findViewById(R.id.mycollect_time_text);
        bookcount = (TextView) findViewById(R.id.mycollect_bookcount_text);
        cover = (ImageView) findViewById(R.id.mycollect_cover_iv);
        backBtnLy = (LinearLayout) findViewById(R.id.collect_back_btn_ly);

        backBtnLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        test();
    }

    private void bingData() {
        booksData = bookDB.getMyCollectData();

        MyCollectAdapter myCollectAdapter = new MyCollectAdapter(MycollectActivity.this,
                R.layout.mycollect_listview_item, booksData);
        listView.setAdapter(myCollectAdapter);
        listView.setFocusable(false);//解决打开套有 ListVew的 ScrollView的页面布局 默认 起始位置不是最顶部。
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String bookId = booksData.get(position).getBookId();

                Intent intent = new Intent(MycollectActivity.this, BookDetailsActivity.class);
                intent.putExtra("bookId_data", bookId);
                startActivity(intent);
            }
        });

    }


    private void test() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String bookId = booksData.get(position).getBookId();
                AlertDialog.Builder builder = new AlertDialog.Builder(MycollectActivity.this);

                //    指定下拉列表的显示数据
                final String[] cities = {"删除此收藏", "删除全部", "返回"};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            bookDB.deleteMycollectData(bookId);
                            Toast.makeText(MycollectActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            bingData();
                        } else if (which == 1) {
                            new AlertDialog.Builder(MycollectActivity.this).setTitle("提示")
                                    .setMessage("确定要全部删除吗？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            bookDB.deleteAllMycollect();
                                            Toast.makeText(MycollectActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                            bingData();
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                        } else if (which == 2) {
                            dialog.cancel();
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }

}