package com.example.tai_fiction.ui.fragment.bookcity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.tai_fiction.R;
import com.example.tai_fiction.ui.activity.booksList.BooksListActivity;
import com.example.tai_fiction.adapter.BookcityAdapter;
import com.example.tai_fiction.base.mvp.MvpFragment;
import com.example.tai_fiction.entity.IndexLablesEntity;
import com.example.tai_fiction.tool.PhoneStateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * Created by 泰子 on 2015/10/17.
 */
public class BookCityFragment extends MvpFragment<BookCityPresenter> implements BookCityView {
    @Bind(R.id.gv_classify)
    GridView classify_gridView;
    @Bind(R.id.bookcity_ptr_frame)
    PtrClassicFrameLayout frameLayout;//刷新
//    @Bind(R.id.bookcity_progressbar)
//    ProgressBar progressBar;


   // private String URL = "http://www.duokan.com/hs/v0/android/store/category";
  //  private List<IndexLablesEntity.BookBean> booksData;
    private List<IndexLablesEntity.BookBean.ItemsBean> itemsBeans;

    public final static String PAR_KEY = "com.tutor.objecttran.par";

    private int sid;//存储

    private PhoneStateUtil phoneStateUtil = new PhoneStateUtil();//手机状态

    @Override
    public int getLayoutId() {
        return R.layout.bookcity_content;
    }

    @Override
    public void initView() {
        mPresenter.loadBookCityData();
        refreshData();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        frameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.loadBookCityData();
            }
        });
    }


    @Override
    protected BookCityPresenter createPresenter() {
        return new BookCityPresenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void geDataSuccess(IndexLablesEntity model) {
        dataSuccess(model);
    }

    @Override
    public void getDataFail(String msg) {
        showShortToast("网络不给力");
    }

    private void dataSuccess(IndexLablesEntity model){
        final List<IndexLablesEntity.BookBean> bookCity = model.getBook();
        //手机网络状态
        if (phoneStateUtil.isNetworkAvailable(getActivity())) {
                    if (bookCity != null) {
                        BookcityAdapter bookcityAdapter = new BookcityAdapter(getActivity(), R.layout.bookcity_listview_item, bookCity);
                        classify_gridView.setAdapter(bookcityAdapter);
                        classify_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String lable = bookCity.get(position).getLabel();
                                sid = bookCity.get(position).getSid();
                                itemsBeans = bookCity.get(position).getItems();

                                Intent intent = new Intent(getActivity(), BooksListActivity.class);
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
        } else {
            frameLayout.refreshComplete();
            showShortToast("当前没有网络，请检查的你的网络！");
        }

    }
}


