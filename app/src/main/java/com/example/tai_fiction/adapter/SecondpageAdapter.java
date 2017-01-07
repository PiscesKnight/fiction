package com.example.tai_fiction.adapter;

import android.content.Context;

import com.example.tai_fiction.R;
import com.example.tai_fiction.entity.BookIntroEntity;

import java.util.List;

/**
 * Created by 泰子 on 2015/10/20.
 */
public class SecondpageAdapter extends SimpleBaseAdapter<BookIntroEntity.ItemsBean> {


    public SecondpageAdapter(Context context, int layoutId, List<BookIntroEntity.ItemsBean> data) {
        super(context, layoutId, data);
    }

    @Override
    public void getItemView(ViewHolder holder, BookIntroEntity.ItemsBean bookIntroEntity) {
        holder.setText(R.id.recommend_write_text,bookIntroEntity.getAuthors())
        .setText(R.id.recommend_content_text,bookIntroEntity.getSummary())
        .setText(R.id.recommend_title_text,bookIntroEntity.getTitle())
        .setImageURL2(R.id.recommend_cover_iv,bookIntroEntity.getCover());
    }
}
