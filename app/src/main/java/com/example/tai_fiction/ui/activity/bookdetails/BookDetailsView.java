package com.example.tai_fiction.ui.activity.bookdetails;

import com.example.tai_fiction.base.BaseView;
import com.example.tai_fiction.entity.BookDetailsEntity;

/**
 * Created by JinBao on 2017/1/8.
 */

public interface BookDetailsView extends BaseView {

    void getDataSuccess(BookDetailsEntity model);//成功获取数据

    void onFailure(String msg);//获取数据失败

    void refreshData();//刷新
}
