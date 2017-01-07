package com.example.tai_fiction.adapter;

import android.content.Context;

import com.example.tai_fiction.R;
import com.example.tai_fiction.entity.IndexLablesEntity;

import java.util.List;

/**
 * Created by 泰子 on 2015/11/4.
 */
public class BookcityAdapter extends SimpleBaseAdapter<IndexLablesEntity.BookBean> {

    public BookcityAdapter(Context context, int layoutId, List<IndexLablesEntity.BookBean> data) {
        super(context, layoutId, data);
    }

    @Override
    public void getItemView(ViewHolder holder, IndexLablesEntity.BookBean bookBean) {
        holder.setText(R.id.classify_title_text, bookBean.getLabel())
                .setText(R.id.classify_num_text, ""+bookBean.getBook_count())
                .setImageURL(R.id.classify_bg, bookBean.getNew_image());

    }


}
