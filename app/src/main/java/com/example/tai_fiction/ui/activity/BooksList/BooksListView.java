package com.example.tai_fiction.ui.activity.BooksList;

import com.example.tai_fiction.base.BaseView;
import com.example.tai_fiction.entity.BookIntroEntity;

import java.util.List;

/**
 * Created by JinBao on 2017/1/7.
 */

public interface BooksListView extends BaseView {

    void bingListViewData(final List<BookIntroEntity.ItemsBean> booksData);//绑定列表数据

    void getDataSuccess(BookIntroEntity model);//成功获取数据

    void onFailure(String msg);//获取数据失败

    void refreshBookData();//刷新数据

    void setLabels();//流式布局

    void installData();//插入数据，判断是否已存在
}
