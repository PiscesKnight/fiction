package com.example.tai_fiction.ui.fragment.bookcity;

import com.example.tai_fiction.base.BaseView;
import com.example.tai_fiction.entity.IndexLablesEntity;

/**
 * Created by JinBao on 2017/1/7.
 */

public interface BookCityView extends BaseView {

    void geDataSuccess(IndexLablesEntity model);//获取数据成功

    void getDataFail(String msg);//获取数据失败
}
