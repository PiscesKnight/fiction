package com.example.tai_fiction.ui.fragment.bookcity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tai_fiction.R;

import com.example.tai_fiction.activity.BookcityClickActivity;
import com.example.tai_fiction.adapter.BookcityAdapter;
import com.example.tai_fiction.entity.IndexLablesEntity;
import com.example.tai_fiction.tool.OkHttpClientManager;
import com.example.tai_fiction.tool.PhoneStateUtil;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * Created by 泰子 on 2015/10/17.
 */
public class BookCityFragment extends Fragment {
    private GridView classify_gridView;
    private String URL = "http://www.duokan.com/hs/v0/android/store/category";
    private List<IndexLablesEntity.BookBean> booksData;
    private List<IndexLablesEntity.BookBean.ItemsBean> itemsBeans;

    public final static String PAR_KEY = "com.tutor.objecttran.par";

    private int sid;//存储

    //private BookDB bookDB;
    private ProgressBar progressBar;
    private PtrClassicFrameLayout frameLayout;//刷新

    private PhoneStateUtil phoneStateUtil = new PhoneStateUtil();//手机状态

    @Nullable
    @Override

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookcity_content, container, false);

        classify_gridView = (GridView) view.findViewById(R.id.gv_classify);
        progressBar = (ProgressBar) view.findViewById(R.id.bookcity_progressbar);
        frameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.bookcity_ptr_frame);

        getOkHttpBooksData();
        refreshData();

        return view;
    }


    public void getOkHttpBooksData() {

        //手机网络状态
        if (phoneStateUtil.isNetworkAvailable(getActivity())) {
            OkHttpClientManager.getAsyn(URL, new OkHttpClientManager.ResultCallback<IndexLablesEntity>() {
                @Override
                public void onError(Request request, Exception e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(IndexLablesEntity response) {
                    booksData = response.getBook();
                    if (booksData != null) {
                        progressBar.setVisibility(View.GONE);
                        //bookDB.installLabelData(booksData);//组装数据
                        BookcityAdapter bookcityAdapter = new BookcityAdapter(getActivity(), R.layout.bookcity_listview_item, booksData);
                        classify_gridView.setAdapter(bookcityAdapter);
                        classify_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String lable = booksData.get(position).getLabel();
                                sid = booksData.get(position).getSid();
                                itemsBeans = booksData.get(position).getItems();

                                Intent intent = new Intent(getActivity(), BookcityClickActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList(PAR_KEY, (ArrayList<? extends Parcelable>) itemsBeans);
                                intent.putExtra("lable_data", lable);
                                intent.putExtra("sid_data", sid);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                    frameLayout.refreshComplete();

                }
            });
        } else {
            frameLayout.refreshComplete();
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "当前没有网络，请检查的你的网络！",Toast.LENGTH_LONG ).show();


        }


    }

    /**
     * 刷新
     */
    private void refreshData() {
        frameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getOkHttpBooksData();
            }
        });
    }

}


