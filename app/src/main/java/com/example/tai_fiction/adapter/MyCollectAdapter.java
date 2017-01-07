package com.example.tai_fiction.adapter;

import android.content.Context;

import com.example.tai_fiction.R;
import com.example.tai_fiction.entity.MyCollectEntity;

import java.util.List;

/**
 * Created by Tai on 2016/4/14.
 */
public class MyCollectAdapter extends SimpleBaseAdapter<MyCollectEntity> {

    public MyCollectAdapter(Context context, int layoutId, List<MyCollectEntity> data) {
        super(context, layoutId, data);
    }

    @Override
    public void getItemView(ViewHolder holder, MyCollectEntity myCollectEntity) {
            holder.setText(R.id.mycollect_title_text,myCollectEntity.getTitle())
            .setText(R.id.mycollect_write_text,myCollectEntity.getAuthors())
            .setImageURL2(R.id.mycollect_cover_iv,myCollectEntity.getCover())
            .setText(R.id.mycollect_bookcount_text,myCollectEntity.getBookcount()+"人读过");
    }
}
